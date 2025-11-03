/*
What:
    + Là bảng tạm thời được tạo ra trong phiên làm việc (SESSION) hoặc trong 1 TRANSACTION.
    + Dữ liệu chỉ tồn tại trong thời gian kết nối hoặc transaction đó
    khi session đóng, bảng bị xóa tự động.

What problem it solve:
    + Cần lưu kết quả trung gian tạm thời, nhiều bước xử lý
*/

select * from booking;
select * from date_range;

-- Dùng bảng tạm để lưu booking tháng hiện tại,
CREATE temporary TABLE booking_year AS
SELECT b.booking_id, b.date_id, b.payment_id, b.customer_id, dr.from_date, h.hotel_name
FROM booking AS b
INNER JOIN date_range AS dr ON b.date_id = dr.date_id
join room as r on r.room_id = b.room_id
join hotel as h on h.hotel_id = r.hotel_id
WHERE dr.from_date > '2025-01-01';


-- uc1: thống kê booking, tổng doanh thu của từng khách sạn trong tháng hiện tại.
select by.hotel_name, sum(p.total_money)
from booking_year as by
join payment as p on p.payment_id  = by.payment_id
group by by.hotel_name
order by sum desc;

-- uc2: Top 5 khách hàng có tổng chi tiêu cao nhất trong năm.
select by.customer_id, by.payment_id, by.from_date, p.total_money
from booking_year as by
join payment as p on by.payment_id = p.payment_id
order by p.total_money
limit 5;

-- uc3: So sánh doanh thu trung bình ngày cuối tuần và ngày trong tuần.
-- tạo bảng temp.
create temporary table hotel_value_finalMonth as
select bye.hotel_name, bye.from_date, sum(p.total_money)
from booking_year as bye
join payment as p on p.payment_id = bye.payment_id
where bye.from_date > '2025-10-15'
group by bye.hotel_name, bye.from_date;

create temporary table hotel_value_normalMonth as
select bye.hotel_name, bye.from_date, sum(p.total_money)
from booking_year as bye
join payment as p on p.payment_id = bye.payment_id
where bye.from_date < '2025-10-15'
group by bye.hotel_name, bye.from_date;

select hotel_name, avg(sum)
from hotel_value_normalMonth
group by hotel_name
order by avg(sum) desc;

select hotel_name, avg(sum)
from hotel_value_finalMonth
group by hotel_name
order by avg(sum) desc;

-- uc4: Số lượng booking theo thành phố.
select l.province, sum(p.total_money)
from booking_year as b
join hotel as h on b.hotel_name = h.hotel_name
join location as l on l.location_id = h.location_id
join payment as p on b.payment_id = p.payment_id
group by l.province;

-- uc5: Doanh thu trung bình mỗi khách sạn.
select avg(p.total_money), by.hotel_name
from booking_year as by
join payment as p on by.payment_id = p.payment_id
group by by.hotel_name;

-- uc6: Tạo bảng tạm chứa khách hàng hoạt động, tìm ra khách vip
create temporary table not_active_user as
select c.customer_id
from customer as c
where c.customer_id not in(
select distinct c.customer_id
from booking as b
inner join customer as c on b.customer_id = c.customer_id
inner join date_range  as dr on b.date_id = dr.date_id
where dr.from_date > '2025-10-20'
order by c.customer_id);