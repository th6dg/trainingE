/*
What:
    + Một Stored Procedure là bao gồm các câu lệnh SQL,
    được lưu lại trong cơ sở dữ liệu.
    + Các lập trình viên chỉ cần gọi ra và thực thi.

What problem it solve:
    + Tái sử dụng code
    + Tăng hiệu năng
    + Tăng bảo mật

Procedure vs Function:
    + Function (RETURNS SETOF): SELECT
                                Được thiết kế để trả về dữ liệu,
                                có thể gọi trực tiếp bằng SELECT,
                                cú pháp đơn giản hơn để truy xuất tập kết quả.
                                Không thể commit

    + Procedure (CALL và Con trỏ): INSERT, UPDATE, DELETE
                                Chủ yếu dành cho các thao tác giao dịch,
                                yêu cầu xử lý con trỏ rõ ràng cho tập kết quả,
                                phức tạp hơn để truy xuất dữ liệu đơn giản.
                                Có thể commit
*/

CREATE or replace PROCEDURE AddCustomers
(in name text,
in age int,
in phone int,
in address_id int)
LANGUAGE plpgsql	-- procedure language
AS $BODY$
BEGIN
  	insert into customer(name, age, phone, address_id)
	values (name, age, phone, address_id);
END;
$BODY$;



create or replace procedure UpdateAgeCustomers(
in input_id INT,
in input_age int)
language plpgsql
as $$
begin
	update customer c
	set age = input_age
	where c.customer_id = input_id;
end;
$$



create or replace procedure DeleteUsersbyId(in input_id int)
language plpgsql
as $$
begin
	delete from customer
	where customer_id = input_id;
end;
$$



create or replace procedure DeleteUserByNameAndAge
(
in in_name text,
in in_age int
)
language plpgsql
as $$
begin
	delete from customer
	where name = in_name and age = in_age;
end;
$$

call DeleteUserByNameAndAge('diuong',18);
call UpdateAgeCustomers(4,99);

call DeleteUsersbyId(22);
call AddCustomers('diuong', 18,1223244,12);


DROP PROCEDURE updateagecustomers(integer,integer);




