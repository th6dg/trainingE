/*
What:
    + Function là một khối SQL/PLpgSQL được lưu trong database.
    + Trả về 1 giá trị, bảng, hoặc composite type.
    + SELECT, INSERT, UPDATE, DELETE
    + Không thể commit

What problem it solve:
    + Abstract SELECT query into single function
*/

DROP FUNCTION getallcustomers();

CREATE or replace function GetAllCustomers()
RETURNS TABLE(id BIGINT, name_out VARCHAR(255))
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
    SELECT customer_id, name FROM customer;
END;
$$;


create or replace function UpdateAndQueryHotel
(name_input varchar(255),
name_input2 varchar(255)
)
returns table(id_out bigint, name_out varchar(255))
language plpgsql
as $$
begin
	update hotel
	set hotel_name = name_input2
	where hotel_name = name_input;

	return QUERY
	select hotel_id, hotel_name
	from hotel
	where hotel_id <20;
end;
$$;

select * from UpdateAndQueryHotel('Danang Seaview', 'Danang hotel');
create or replace function UpdateAndQueryCustomers
(id_input bigint,
name_customer_input varchar(255),
phone_customer_input int)
returns table(name_customer varchar(255), phone_customer bigint)
language plpgsql
as $$
begin
	update customer
	set name = name_customer_input, phone = phone_customer_input
	where customer_id = id_input;

	RETURN QUERY
	select name, phone from customer
	where customer_id > id_input -1 and customer_id < id_input+10;
end;
$$;

-- Gọi function
SELECT * FROM GetAllCustomers();
