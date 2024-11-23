--basics
SELECT facid, name, membercost, monthlymaintenance
FROM cd.facilities
WHERE membercost > 0 
  AND membercost < (monthlymaintenance / 50);

SELECT facid, name, membercost, monthlymaintenance
FROM cd.facilities
WHERE name LIKE '%Tennis%';

SELECT facid, name, membercost, monthlymaintenance FROM cd.facilities WHERE facid IN (1, 5);

SELECT memid, surname, firstname, joindate FROM cd.members WHERE joindate > '2012-09-01';

SELECT surname FROM cd.members UNION SELECT name FROM cd.facilities;

SELECT b.starttime FROM cd.bookings b JOIN cd.members m ON b.memid = m.memid WHERE m.firstname = 'David' AND m.surname = 'Farrell';

SELECT b.starttime, f.name FROM cd.bookings b JOIN cd.facilities f ON b.facid = f.facid WHERE f.name LIKE '%Tennis%' AND DATE(b.starttime) = '2012-09-21' ORDER BY b.starttime;

SELECT m.memid, m.surname, m.firstname, r.firstname AS recommended_firstname, r.surname AS recommended_surname 
FROM cd.members m JOIN cd.members r ON m.recommendedby = r.memid ORDER BY m.surname, m.firstname;

SELECT DISTINCT r.memid, r.surname, r.firstname 
FROM cd.members r 
JOIN cd.members m ON m.recommendedby = r.memid 
ORDER BY r.surname, r.firstname;

--Aggregate functions
SELECT m.memid, m.firstname, m.surname, COUNT(r.memid) AS recommendation_count 
FROM cd.members m 
LEFT JOIN cd.members r ON m.memid = r.recommendedby 
GROUP BY m.memid, m.firstname, m.surname 
ORDER BY m.memid;

SELECT f.facid, SUM(b.slots) AS total_slots 
FROM cd.bookings b 
LEFT JOIN cd.facilities f ON b.facid = f.facid 
GROUP BY f.facid 
ORDER BY f.facid;

SELECT f.facid, SUM(b.slots) AS total_slots 
FROM cd.bookings b 
LEFT JOIN cd.facilities f ON b.facid = f.facid 
WHERE b.starttime >= '2012-09-01' AND b.starttime < '2012-10-01' 
GROUP BY f.facid 
ORDER BY total_slots DESC;

SELECT 
    f.facid, 
    EXTRACT(MONTH FROM b.starttime) AS month, 
    SUM(b.slots) AS total_slots 
FROM 
    cd.bookings b 
LEFT JOIN 
    cd.facilities f ON b.facid = f.facid 
WHERE 
    EXTRACT(YEAR FROM b.starttime) = 2012 
GROUP BY 
    f.facid, EXTRACT(MONTH FROM b.starttime) 
ORDER BY 
    f.facid, month;

SELECT COUNT(DISTINCT b.memid) AS total_members 
FROM cd.bookings b;


SELECT 
    m.memid, 
    m.firstname, 
    m.surname, 
    MIN(b.starttime) AS first_booking 
FROM 
    cd.members m 
JOIN 
    cd.bookings b ON m.memid = b.memid 
WHERE 
    b.starttime > '2012-09-01' 
GROUP BY 
    m.memid 
ORDER BY 
    m.memid;


SELECT 
    ROW_NUMBER() OVER (ORDER BY joindate) AS member_number, --Assigns a unique, sequential number to each row based on the order of the join date (joindate).
    memid,
    firstname || ' ' || surname AS member_name,
    joindate
FROM 
    cd.members
ORDER BY 
    member_number;


SELECT 
    facid, 
    SUM(slots) AS total
FROM 
    cd.bookings
GROUP BY 
    facid
HAVING 
    SUM(slots) = (SELECT MAX(total_slots) FROM (SELECT SUM(slots) AS total_slots FROM cd.bookings GROUP BY facid) AS subquery);

--string
SELECT CONCAT(surname, ', ', firstname) AS full_name FROM cd.members;

SELECT memid, telephone FROM cd.members WHERE telephone LIKE '%(%' ORDER BY memid;

SELECT LEFT(surname, 1) AS initial_letter, COUNT(*) AS member_count 
FROM cd.members 
GROUP BY LEFT(surname, 1) 
ORDER BY initial_letter;

