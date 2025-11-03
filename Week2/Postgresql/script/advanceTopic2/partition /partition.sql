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