/*          INDEX

Database:      Đọc page(block) số 1234 (offset) trong file 12345.rel
               Hệ điều hành sẽ dịch sang địa chỉ vật lý thật.
Key - Pointer: (key=1234) - (file_id=1663/12345.rel, page=128, offset=17)
OS:            Physical Block

->  Khi nói “truy cập block” trong ngữ cảnh database (query, index scan, full scan,...)
    thì “block” ở đây là tầng logic — tức là database page,
    không phải block vật lý của OS hay ổ đĩa.

What: Ví dụ về index
    Pointer:   "Record này nằm trong file dữ liệu #3,
                cụ thể là page thứ 150 (8 KB/page), và là row thứ 12 trong page đó".

    " OS ơi, đọc 8 KB từ vị trí 12,288,000 trong file này."
    -> OS tra filesystem -> biết những byte đó nằm ở block logic nào của ổ đĩa.
    -> Disk controller đọc đúng các block vật lý đó và trả về cho OS.

    No index: " OS ơi, đọc hết file này"
*/