/*
what:
    + CTE gần giống như Temporary Table nhưng
    chỉ dùng trong phạm vi của một truy vấn duy nhất.
what problem it solve:
    + CTE đặc biệt hữu ích cho các cấu trúc dữ liệu phân cấp hoặc dạng đồ thị và
    có thể nâng cao hiệu suất truy vấn trong một số hệ thống cơ sở dữ liệu
more infomation:
    + Là cách đặt tên tạm cho một truy vấn con.
    + Dễ đọc, dễ tái sử dụng trong cùng một câu lệnh.
    + Không tạo bảng thật cũng không lưu dữ liệu.
    + Khi không cần tái sử dụng sau đó.
    + Cần viết nhiều tầng truy vấn lồng nhau mà không bị rối.
*/



-- A. CTE cơ bản (Simple Queries)

--Tính tổng số lượng booking theo khách hàng
--→ Tạo CTE gom nhóm booking theo customer_id, rồi hiển thị cùng họ tên khách hàng.
select bc.name, count(bc.booking_id)
from booking_customer as bc
group by bc.name
order by count(bc.booking_id);


--Khách hàng chi tiêu nhiều nhất
--→ Trong CTE: tổng total_amount theo customer_id,
--sau đó truy vấn top 5 khách hàng có tổng cao nhất.
with booking_customer as (
	select p.total_money, b.payment_id, c.name
	from booking as b
	inner join customer as c on b.customer_id = c.customer_id
	inner join payment as p on b.payment_id = p.payment_id
)
select bc.name, sum(p.total_money)
from booking_customer as bc
join payment as p on bc.payment_id = p.payment_id
group by bc."name"
order by sum(p.total_money) desc
limit 5;


--Tổng doanh thu mỗi khách sạn
--→ CTE 1: tổng total_amount theo hotel_id.
--CTE 2: join với bảng hotel để lấy tên.
with revenue_hotel as (
	select h.hotel_name, p.total_money
	from booking as b
	join payment as p on b.payment_id = p.payment_id
	join room as r on b.room_id = r.room_id
	join hotel as h on h.hotel_id = r.hotel_id
)
select rh.hotel_name, sum(rh.total_money)
from revenue_hotel as rh
group by rh.hotel_name
order by sum desc;

--Phòng được đặt trong 30 ngày gần nhất
--→ CTE lọc các booking có checkin_date trong 30 ngày, sau đó join với room để xem thông tin phòng.
with room_booking as (
	select 	r.room_id, dr.from_Date
	from room as r
	join booking as b on b.room_id = r.room_id
	join date_range as dr on b.date_id = dr.date_id
	where dr.from_date > '2025-10-01'
)
select * from room_booking;


-- C. CTE cho báo cáo doanh thu / quản lý

--Doanh thu theo tháng trong năm nay
--→ CTE nhóm theo EXTRACT(MONTH FROM booking_date) rồi sắp xếp tăng dần theo tháng.


--Khách hàng VIP (chi tiêu > 10.000)
--→ CTE tổng hợp chi tiêu từng khách hàng,
--sau đó lọc điều kiện tổng chi tiêu > 10.000.
with booking_customer as (
	select c.name, sum(p.total_money)
	from booking as b
	join payment as p on b.payment_id = p.payment_id
	join customer as c on b.customer_id = c.customer_id
	group by c.name
)
select * from booking_customer
where sum > 5000000;


--Khách sạn có doanh thu thấp hơn trung bình toàn hệ thống
-- →CTE 1: tính doanh thu từng khách sạn.
--CTE 2: tính doanh thu trung bình toàn hệ thống.
--Kết hợp để lấy khách sạn có doanh thu thấp hơn trung bình.
--Khách sạn có doanh thu thấp hơn trung bình toàn hệ thống
with avg_avenue as (
	select avg(p.total_money)
	from payment as p
),
avg_avenue_hotel as (
	select h.hotel_name, avg(p.total_money)
	from booking as b
	join payment as p on b.payment_id = p.payment_id
	join room as r on r.room_id = b.room_id
	join hotel as h on r.hotel_id = h.hotel_id
	group by h.hotel_name
)
select aah.hotel_name, aah.avg
from avg_avenue_hotel as aah, avg_avenue as aa
where aah.avg > aa.avg;