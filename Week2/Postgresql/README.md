# POSTGRESQL
![img.png](img.png)
## BASIC
![img_7.png](img_7.png)
## ADVANCE
![img_2.png](img_2.png)
## CORE SQL
![img_6.png](img_6.png)

## How it work, in view of CS
[InnoDB]<br>
&emsp;↓<br>
os_file_read(page_buf, fd, offset, 16384)   ← gọi hàm này<br>
&emsp;↓<br>
[pread() system call trong Linux]<br>
&emsp;↓<br>
[Kernel Mode: VFS Layer (Virtual File System)]<br>
&emsp;↓<br>
[File System: ext4 / xfs / zfs]<br>
&emsp;↓<br>
[Block Layer & I/O Scheduler]<br>
&emsp;↓<br>
[Device Driver (SSD/HDD)]<br>
&emsp;↓<br>
[Ổ đĩa thật sự đọc dữ liệu từ sector, trả về RAM]<br>

Database chỉ “ra lệnh”:<br>
Đọc 16KB ở offset 16384 * 1000 trong file data.ibd<br>
MySQL nói: “OS ơi, đọc giúp tao 16 KB từ file này tại offset X”.<br>

