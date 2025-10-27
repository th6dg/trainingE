## Cách làm bài 4, concurrent 

### Cách truyền thống:
+ Cách 1: Tạo mới mỗi thread tại mỗi transaction 
-> Sử dụng synchonize/volatile để ngăn chặn race condition 
+ Cách 2: Sử dụng pattern producer-consumer 
-> Sử dụng thread communication thay vì synchonized 

### Cách làm hiện đại (packgae concurrent java 8+)
+ Tạo thread pool/executor service/future để thư viện tự quản lí thread 
-> Tái sử dụng thread, nhanh hơn tạo thread thủ công 
+ Sử dụng Lock thay vì synchonized -> tăng tính linh hoạt 

### Một số cách làm khác 