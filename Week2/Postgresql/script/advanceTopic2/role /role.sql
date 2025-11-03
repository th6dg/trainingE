/*
What:
    Phân quyền (authorization / privilege management) trong PostgreSQL
    là cơ chế kiểm soát người dùng nào được phép làm gì trong database.

what problem it solve:
    + Security

Key word:
    + GRANT: give specific privileges (SELECT, UPDATE, INSERT, DELETE)
    + REVOKE: remove these privileges

Best practice:
    1. Least Privilege Principle
    This principle states that a user should have the minimum levels of access necessary and nothing more. For large systems, this could require a good deal of planning.

    2. Regular Updates
    Always keep SQL Server patched and updated to gain the benefit of the most recent security updates.

    3. Complex and Secure Passwords
    Passwords should be complex and frequently changed. Alongside the use of GRANT and REVOKE, this is the front line of defense.

    4. Limiting Remote Access
    If remote connections to the SQL server are not necessary, it is best to disable it.

    5. Avoid Using SQL Server Admin Account
    You should avoid using the SQL Server admin account for regular database operations to limit security risk.

    6. Encrypt Communication
    To protect against data sniffing, all communication between SQL Server and applications should be encrypted.

    7. Database Backups
    Regular database backups are crucial for data integrity if there happens to be a data loss.

    8. Monitoring and Auditing
    Regularly monitor and audit your database operations to keep track of who does what in your database.

    9. Regular Vulnerability Scanning
    Use a vulnerability scanner to assess the security posture of your SQL.

    10. SQL Injection
    SQL injection can be reduced by using parameterized queries or prepared statements.
*/





-- Xem thông tin role
\du+

-- Tạo role mới
CREATE ROLE duy;
create role duy2 login password 'Abc123';

-- thêm quyền để role (hay user) có thể kết nối vào 1 database nào đó,
GRANT CONNECT ON DATABASE postgres TO duy2;

-- tạo các đối tượng (bảng, index, view…) trên 1 schema.
GRANT USAGE, CREATE ON SCHEMA public TO duy2;


-- Gán quyền cho role (hay user)
GRANT INSERT ON CUSTOMER TO duy2;
GRANT SELECT ON CUSTOMER TO duy2 WITH GRANT OPTION;

-- Thu hồi quyền
REVOKE ALL PRIVILEGES ON CUSTOMER FROM duy2;

-- Thay đổi password cho Role:
Alter role duy3 password 'Abcd1234';

-- Đưa role lên thành superuser
alter role duy3 superuser;

-- Inherit: grant quyền cho role khác
alter role duy3 inherit;
-- Noinherit: không thể grant quyền
alter role duy3 noinherit;

-- chuyển sở hữu những bảng cho 1 role khác
REASSIGN OWNED BY duy3 to postgres;
DROP OWNED BY duy3;

-- Xóa bỏ role
drop role duy3;
