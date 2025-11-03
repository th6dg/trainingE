/*
What:
    All SQL success, or
    All SQL failed
What problem it solve:
    Sự nhất quán về dữ liệu
    Atomic - Consistency - Isolation - Durability
Key word:
    BEGIN
    COMMIT
    ROLLBACK
    SAVE POINT
*/

/*
    Khi bạn BEGIN;, PostgreSQL tạo một “snapshot” (ảnh chụp logic của dữ liệu) riêng cho transaction đó.
	Khi bạn UPDATE, bản ghi mới (phiên bản mới của dòng dữ liệu) được ghi vào “phiên làm việc” (transaction workspace).
	Khi bạn SELECT trong cùng transaction,
	bạn thấy bản ghi mà chính bạn vừa sửa — dù nó chưa được commit với thế giới bên ngoài.
	-> Trong một transaction, bạn nhìn thấy các thay đổi của chính mình,
    nhưng các session khác thì chưa thấy gì.
 */
begin;
update test_transaction
set money = money + 50000
where test_id = 1;
select * from test_transaction;
rollback;

select * from test_transaction;