/*
What:
    + Trigger là 1 procedure tự động gọi khi sự kiện xảy ra
    + Ví dụ: Before/After INSERT, DELETE, UPDATE

What problem it solve:
    + Tự động hóa các task thủ công
    + ví dụ: ghi log mỗi khi thêm/sửa/xóa dữ liệu
             ngăn chặn dữ liệu không hợp lệ
             cập nhật bảng liên quan, đảm bảo dữ liệu khớp nhau.
*/

create table customer_log(
	log_id BIGSERIAL PRIMARY key,
	customer_id BIGINT,
	action text,
	action_time TIMESTAMP);

-- create function

CREATE OR REPLACE FUNCTION log_customer_insert()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO customer_log(customer_id, action, action_time)
    VALUES (NEW.customer_id, 'Action on customer table', NOW());
    RETURN NEW;
END;
$$;

CREATE TRIGGER trg_log_insert
AFTER insert on  customer	-- PostgreSQL không hỗ trợ trigger cho SELECT
FOR EACH ROW
EXECUTE FUNCTION log_customer_insert();

DROP FUNCTION IF EXISTS UpdateAndGetCustomer(VARCHAR, INT, BIGINT, BIGINT);


create or replace function UpdateAndGetCustomer1
(name_input varchar(255), age_input int, phone_input bigint, address_id_input bigint)
returns table(name_customer varchar(255), phone_customer bigint)
language plpgsql
as $$
begin
	-- INSERT và trả ngay dữ liệu vừa chèn
    RETURN QUERY
    INSERT INTO customer (name, age, phone, address_id)
    VALUES (name_input, age_input, phone_input, address_id_input)
    RETURNING name, phone;
end;
$$;

select * from UpdateAndGetCustomer1('d',1,123,10);



insert into customer(name, age, phone, address_id)
values ('1', 2, 3, 4);

select * from customer_log;
select * from UpdateAndGetCustomer('d',1,123,10);

SELECT column_name FROM information_schema.columns WHERE table_name = 'customer';
