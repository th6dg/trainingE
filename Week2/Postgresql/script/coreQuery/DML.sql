-- uc1: Tạo đơn đặt phòng mới(insert)
--Hệ thống tạo một bản ghi thanh toán (payment) mới, ghi lại tổng tiền.
--Cơ sở dữ liệu tự sinh payment_id và trả về (dùng RETURNING).
--Hệ thống sử dụng payment_id này để tạo bản ghi booking tương ứng — gắn khách hàng, phòng, và thời gian ở.
--Sau khi đặt xong, trạng thái phòng (room_status) được chuyển sang BOOKED.
insert into payment(total_money)
values (500000)
returning payment_id;
select * from payment where total_money = 500000;
insert into booking(date_id, room_id, customer_id)
values (100, 21, 18);
select * from booking where payment_id = 121;

truncate
-- uc2: Khách hàng checkout, UPDATE phòng chuyển trạng thái từ BOOKED → CLEANING.
update room
set room_status = 'BOOKED'
where room_id < 10;

CREATE TEMP TABLE temp_room AS (
SELECT *
FROM room
ORDER BY room_id);

CREATE view view_room_desc AS (
SELECT *
FROM room
ORDER BY room_id desc );

SELECT * FROM temp_room;

select * from view_room_desc;