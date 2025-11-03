# Part 1
## Các thành phần DBMS
+ Query Processor
+ Store Manager
+ Disk
## Mô hình 3 lớp 
+ View level
+ Logic level
+ Physical level
## E-R
+ Tập thực thể 
+ Tập thuộc tính 
+ Tập liên kết

## Basic Data Type
+ tinyint, smallint, mediumint, int, bigint
+ float, double, decimal
+ char, varchar, text
+ Date, Datetime, Timestamp

## Ràng buộc(constraint)
+ Primary Key
+ Foreign Key
+ Not Null
+ Check
+ Default 
+ Creat Index

## Funtion
+ Math Function
+ Date Function
+ String Function

## Tập hợp 
+ Union, Union All 
+ Intersect 
+ Except 
+ Any / All 
+ Exists 

## Thứ tự thực hiện 
__FROM__&emsp;&emsp;------>&emsp;&emsp;__WHERE__&emsp;&emsp;------>&emsp;&emsp;__GROUP BY__&emsp;&emsp;
------>&emsp;&emsp;__COUNT(*)__&emsp;&emsp;------>&emsp;&emsp;__HAVING__&emsp;&emsp;------>&emsp;&emsp;__SELECT__&emsp;&emsp;------>
&emsp;&emsp;__ORDER BY__&emsp;&emsp;------>&emsp;&emsp;__LIMIT__

## Subquery

## Temporary Table / View / CTE

+ CREATE __TEMPORATY__ TABLE temps_users AS (...) &emsp;&emsp;------>&emsp;&emsp; "Lưu bảng tạm thời, data / session"
+ CREATE __VIEW__ view_users AS (...)&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;------>&emsp;&emsp; "Lưu logic truy vấn, not data"
+ __WITH__ ... __AS__ (...) ... &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;------>&emsp;&emsp; "Lưu bảng tạm thời, data / query"

## Window function 