/*
What:
    + View là bảng ảo(virtual table).
    + Không lưu dữ liệu thật, mà chỉ lưu cấu trúc truy vấn.

What problem it solve:
    + Ẩn logic phức tạp, làm đơn giản query
    + Hạn chế người dùng truy cập dữ liệu nhạy cảm.

Use case:
    + Cần tái sử dụng truy vấn nhiều lần, chia sẻ cho người khác

Type of view:
    Simple View: Chế độ xem chỉ dựa trên một bảng duy nhất,
                không chứa mệnh đề GROUP BY và bất kỳ hàm nào.
    Complex View: Chế độ xem dựa trên nhiều bảng,
                chứa mệnh đề GROUP BY và các hàm.
    Inline View: Chế độ xem dựa trên một truy vấn con trong mệnh đề FROM,
                truy vấn con đó tạo ra một bảng tạm thời (temporary table) và đơn giản hóa truy vấn phức tạp.
    Materialized View: Chế độ xem lưu trữ cả định nghĩa lẫn dữ liệu.
                      Nó tạo ra các bản sao dữ liệu bằng cách lưu trữ vật lý.
*/
-- A. View về Khách hàng (Customer-focused)
create view info_customer as
select c.name, c.age, c.phone, a.country, a.province, p.payment_id
from customer c
inner join address as a on c.address_id = a.address_id
inner join booking as b on c.customer_id = b.customer_id
inner join payment as p on b.payment_id = p.payment_id
order by c.customer_id;

create view country_booking as
select l.country, l.province, b.payment_id
from booking as b
join room as r on b.room_id = r.room_id
join hotel as h on r.hotel_id = h.hotel_id
join location as l on h.location_id = l.location_id;
--Danh sách khách hàng cơ bản
--→ Hiển thị customer_id, first_name, last_name, ẩn email và số điện thoại.
select distinct ic.name, ic.age, ic.phone
from info_customer as ic
order by ic.name;


--Khách hàng có lượt đặt phòng nhiều nhất
--→ Đếm số booking của từng khách hàng, sắp xếp giảm dần.
select ic.name, count(ic.payment_id)
from info_customer as ic
group by ic.name
order by count(ic.payment_id) desc;


--Tổng chi tiêu của từng khách hàng
--→ Tổng total_amount của tất cả booking mà khách hàng đã đặt.
select ic.name, sum(p.total_money)
from info_customer as ic
join payment as p on ic.payment_id = p.payment_id
group by ic.name
order by sum(p.total_money) desc;


--Khách hàng chưa từng để lại review
--→ Liệt kê khách hàng không có bản ghi trong bảng review.


--Khách hàng ở thành phố nào nhiều nhất
--→ Dựa trên bảng address hoặc location để đếm số khách hàng theo thành phố.
select cb.province, count(cb.payment_id)
from country_booking as cb
group by cb.province;

-- C. View về Thanh toán và Doanh thu (Finance / Payment)
create view booking_hotel as
select p.total_money, h.hotel_name, b.customer_id
from booking as b
join payment as p on b.payment_id = p.payment_id
join room as r on b.room_id = r.room_id
join hotel as h on r.hotel_id = h.hotel_id;



select bh.hotel_name, sum(bh.total_money)
from booking_hotel as bh
group by bh.hotel_name
order by sum(total_money) desc;
--Doanh thu theo khách sạn
--→ Tổng SUM(total_amount) cho mỗi khách sạn.

--Thống kê theo phương thức thanh toán
--→ Tổng số giao dịch và tổng tiền theo payment_method.


select l.country, sum(bh.total_money)
from booking_hotel as bh
inner join hotel as h on bh.hotel_name = h.hotel_name
inner join location as l on h.location_id = l.location_id
group by l.country
order by sum(bh.total_money) desc;
--Doanh thu theo quốc gia
--→ Dựa trên location.country, tổng hợp doanh thu theo từng quốc gia.


select sum(p.total_money)
from booking as b
join payment as p on b.payment_id = p.payment_id
join date_range as dr on b.date_id = dr.date_id
where dr.from_date > '2025-09-30' and dr.to_date <'2025-11-01';
--Tổng doanh thu từng tháng
--→ Dựa trên payment_date hoặc booking_date, nhóm theo tháng/năm.