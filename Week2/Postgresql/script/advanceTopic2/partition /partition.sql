/*                                  PARTITION
Problem in database:
    + Cách lấy dữ liệu
    + Kết nối app-database
    + Nhiều người dùng - multi version concurrent control

Cách lấy dữ liệu:
    + Block (or pages)
    + Giảm số block: Index, Partition, Sharding, Replication (Master/Slave)

Kết nối app-database: --> Connection pool
    + Database connection: RAM + CPU + File Description (Database server, Backend server)
    + Network connection: băng thông
    1. Tạo kết nối với database
    2. Thiết lập kết nối (xác thực)
    3. Gửi & nhận SQL
    4. Đóng kết nối (giải phóng tài nguyên)

MVCC: Thay vì khóa dữ liệu, database tạo nhiều “phiên bản” (version) của cùng một row.
    + process vaccum

======================================================================================================
What:
    Chia nhỏ table lớn -> nhiều table con

    Bảng Phân vùng (Partitioned Table - Bảng Cha):
            Đây là bảng "ảo" xác định sơ đồ phân vùng
            và các cột được sử dụng làm khóa phân vùng. Bản thân bảng này không lưu trữ bất kỳ dữ liệu nào.

    Phân vùng (Partitions - Bảng Con):
            Đây là các bảng thông thường lưu trữ dữ liệu thực tế.
            Mỗi phân vùng được liên kết với bảng cha và lưu trữ một tập hợp con dữ liệu
            dựa trên ranh giới phân vùng đã xác định của nó.

    Khóa Phân vùng (Partition Key):
            Cột hoặc biểu thức được sử dụng
            để xác định một hàng thuộc phân vùng nào.

What problem it solve:
    + Large table

Key word:
    + partitioning by range
    + Partition by list
    + Partition by hash
    -> Partition pruning

*/

-- create partition
create table address_partition (
	address_id BIGSERIAL not null,
	country varchar(255) not null,
	province varchar(255) not null,
	city varchar(255) not null, 
	street varchar(255) not null
) partition by range (province);

DROP TABLE IF EXISTS address_0;
create table address_0 partition of address_partition
	for values from ('Busan') to ('Province 1');

DROP TABLE IF EXISTS address_500;
create table address_500 partition of address_partition
	for values from ('Province 1') to ('Province 501');

DROP TABLE IF EXISTS address_1000;
create table address_1000 partition of address_partition
	for values from ('Province 501') to ('Province 999');

DROP TABLE IF EXISTS address_1;
create table address_1 partition of address_partition
	for values from ('Province 999') to ('Victoria 1');


explain INSERT INTO address_partition
SELECT * FROM address;

select * from address_partition
where province = 'Province 500';

select * from address
where province = 'Province 669';
