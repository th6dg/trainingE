-- UC1: List all rooms that are currently available in each hotel.
select r.room_id, r.room_status, h.hotel_name
from room as r
left join hotel as h on r.hotel_id = h.hotel_id
where r.room_status = 'AVAILABLE';

-- UC2: Show the number of rooms per room type (SINGLE, DOUBLE, FAMILY) for every hotel.
-- GROUP BY được dùng để nhóm các hàng (rows) có giá trị giống nhau trong một hoặc nhiều cột lại với nhau
select sum(r.room_id), r.room_type, h.hotel_name
from room as r
left join hotel as h on r.hotel_id = h.hotel_id
group by r.room_type, h.hotel_id;

-- UC3: Find hotels that have no available rooms.
-- NOT IN (value does not belong to this set)
select r.room_id, h.hotel_name
from room as r
left join hotel as h on r.hotel_id = h.hotel_id
where h.hotel_id not in (
    select r1.hotel_id
    from room as r1
    where r1.room_status = 'AVAILABLE'
);


-- UC4: Display the average price of rooms by hotel and room type.
select avg(r.price), r.room_type, h.hotel_name
from room as r
left join hotel as h on r.hotel_id = h.hotel_id
group by (r.room_type, h.hotel_name);

-- UC5: Identify rooms that haven’t been booked in the last 30 days.

-- UC6: Get a list of hotels located in the same country or province.
-- JOIN = INNER JOIN
SELECT
    h1.hotel_name AS hotel_1,
    h2.hotel_name AS hotel_2,
    l1.country,
    l1.province
FROM hotel h1
JOIN location l1 ON h1.location_id = l1.location_id
JOIN hotel h2 ON h1.hotel_id < h2.hotel_id
JOIN location l2 ON h2.location_id = l2.location_id
WHERE
    l1.country = l2.country
    OR l1.province = l2.province
ORDER BY l1.country, l1.province;

-- UC7: Show hotels that have at least one room under maintenance.
-- HAVING: WHERE + aggregate function
select h.hotel_name, r.room_id
from hotel as h
inner join room as r on h.hotel_id = r.hotel_id
where r.room_status = 'MAINTENANCE'
group by (h.hotel_name, r.room_id)
having (count(r.room_type) > 0);

-- UC8: Find the top 5 customers who have spent the most money overall.
-- Limit: giới hạn số lượng
select sum(p.total_money),c.customer_id, c.name
from customer c
inner join booking b on b.customer_id = c.customer_id
inner join payment p on b.payment_id = p.payment_id
group by (c.customer_id, c.name)
order by sum(p.total_money) desc
limit 5;

-- UC9: List all bookings for a given customer, including hotel name, dates, and total payment.
select h.hotel_name , dr.from_date, dr.to_date, sum(p.total_money) as totalPayment
from booking b
inner join room as r on b.room_id = r.room_id
inner join hotel as h on r.hotel_id = h.hotel_id
inner join date_range as dr on b.date_id = dr.date_id
inner join payment as p on b.payment_id = p.payment_id
group by (h.hotel_name, dr.from_date, dr.to_date);

-- UC10:Find customers who have booked more than 3 times in the past 6 months.

-- uc11: Show customers who have never made a booking.
select c.customer_id, c.name
from customer as c
where c.customer_id not in(
    select c.customer_id
    from customer c
    inner join booking as b on b.customer_id = c.customer_id
);


-- uc12: Find customers who stayed in multiple different province.
select c.customer_id, l.province, b.booking_id
from booking as b
inner join customer as c on b.customer_id = c.customer_id
inner join room as r on b.room_id = r.room_id
inner join hotel as h on r.hotel_id = h.hotel_id
inner join location as l on h.location_id = l.location_id
order by c.customer_id;


-- uc13: Identify the most loyal customer for each hotel (by total bookings).
WITH booking_counts AS (
    SELECT
        h.hotel_id,
        h.hotel_name,
        c.customer_id,
        COUNT(*) AS total_bookings,
        RANK() OVER (PARTITION BY h.hotel_id ORDER BY COUNT(*) DESC) AS rank_in_hotel
    FROM booking b
    INNER JOIN customer c ON b.customer_id = c.customer_id
    INNER JOIN room r ON b.room_id = r.room_id
    INNER JOIN hotel h ON r.hotel_id = h.hotel_id
    GROUP BY h.hotel_id, h.hotel_name, c.customer_id
)
SELECT hotel_id, hotel_name, customer_id, total_bookings
FROM booking_counts
WHERE rank_in_hotel = 1
ORDER BY hotel_id;

-- uc14: Calculate total revenue per hotel.
select h.hotel_id, sum(p.total_money)
from booking b
inner join payment as p on b.payment_id = p.payment_id
inner join room as r on b.room_id = r.room_id
inner join hotel as h on r.hotel_id = h.hotel_id
group by h.hotel_id
order by h.hotel_id;

-- uc15: Show average booking value by province.
select l.country, l.province, avg(p.total_money)
from booking as b
inner join payment as p on b.payment_id = p.payment_id
inner join room as r on b.room_id = r.room_id
inner join hotel as h on h.hotel_id = r.hotel_id
inner join location as l on h.location_id = l.location_id
group by l.province, l.country;

-- uc16: Identify hotels with revenue below the overall average.

-- uc17: Display the total amount spent by each customer.
select c.customer_id, avg(p.total_money)
from booking as b
inner join payment as p on b.payment_id = p.payment_id
inner join customer as c on b.customer_id = c.customer_id
group  by c.customer_id
order by c.customer_id;


-- uc18: Determine which month of the year has the highest total revenue.
select d.date_id, sum(p.total_money)
from booking as b
inner join payment as p on b.payment_id = p.payment_id
inner join date_range as d on d.date_id = b.date_id
group  by d.date_id
order by d.date_id;


-- uc19: Find the most expensive room ever booked.
select r.room_id, max(p.total_money)
from booking as b
inner join payment as p on b.payment_id = p.payment_id
inner join room as r on b.room_id = r.room_id
group by r.room_id
order by max(p.total_money) desc
limit 1;

-- uc20: Calculate the average review rating per hotel.

-- uc21: Find the highest-rated room in each hotel.

-- uc22: Identify hotels that received only 5-star reviews.

-- uc23: Find hotels with no reviews at all.

-- uc24: Show the difference between a hotel’s average rating and the overall system average.

-- uc25: Check which rooms are currently occupied (based on today’s date).

-- uc26: Find overlapping bookings for the same room (double booking detection).

-- uc27: Calculate the average stay length per hotel.

-- uc28: Identify date ranges where all rooms were fully booked.

-- uc29: Show how many rooms were booked in each month of the year.

-- uc30: Determine the percentage of time each room has been occupied (occupancy rate).

-- uc31: List rooms under maintenance or cleaning status.

-- uc32: Find hotels that haven’t updated room prices in over 6 months.

-- uc33: Show the users (from user table) who created the most new bookings (if tracking system users).

-- uc34: Identify any inconsistent data — e.g., bookings without payments or customers without addresses.

-- uc35: Generate a daily report summarizing total bookings, total revenue, and top 3 cities.