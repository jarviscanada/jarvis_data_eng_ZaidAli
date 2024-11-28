# Project Description

This project consists of a collection of SQL queries designed to demonstrate various operations and techniques commonly used in database management. The queries cover a wide range of topics, including data selection, aggregation, string manipulation, and more advanced SQL features such as window functions and joins.

The queries are written for a hypothetical database related to a club or organization, where members book facilities such as tennis courts and other amenities. The queries involve retrieving member information, calculating booking statistics, and aggregating data related to bookings, memberships, and facility usage.

## Key Features:

- **Data Retrieval**: Queries for selecting member and facility data based on specific conditions such as dates, facility names, and booking details.
- **Aggregations**: Use of aggregate functions (e.g., `COUNT`, `SUM`, `MIN`, `MAX`) to calculate booking statistics, member recommendations, and other data.
- **Joins**: Several queries demonstrate joining tables to combine data from related entities, such as members and bookings.
- **String Manipulation**: Techniques for manipulating and formatting string data, such as concatenating names and filtering telephone numbers.
- **Advanced SQL**: Usage of window functions and subqueries for more complex data retrieval and analysis tasks.

This project serves as both a learning tool for SQL and as a foundation for more advanced database queries.

# SQL Queries and Explanations

## Basic Queries

### 1. Select facilities where the member cost is less than 1/50th of the monthly maintenance

```sql

SELECT

  facid,

  name,

  membercost,

  monthlymaintenance

FROM

  cd.facilities

WHERE

  membercost > 0

  AND membercost < (monthlymaintenance / 50);

Explanation: This query selects the facid, name, membercost, and monthlymaintenance of facilities where the membercost is less than 1/50th of the monthlymaintenance.



2. Select facilities with names containing "Tennis"

sql

Copy code

SELECT

  facid,

  name,

  membercost,

  monthlymaintenance

FROM

  cd.facilities

WHERE

  name LIKE '%Tennis%';

Explanation: This query retrieves the facilities that have "Tennis" in their name.



3. Select specific facilities by facid

sql

Copy code

SELECT

  facid,

  name,

  membercost,

  monthlymaintenance

FROM

  cd.facilities

WHERE

  facid IN (1, 5);

Explanation: This query retrieves details of facilities with facid 1 and 5.



4. Select members who joined after a specific date

sql

Copy code

SELECT

  memid,

  surname,

  firstname,

  joindate

FROM

  cd.members

WHERE

  joindate > '2012-09-01';

Explanation: This query retrieves the memid, surname, firstname, and joindate of members who joined after September 1, 2012.



5. Union of member surnames and facility names

sql

Copy code

SELECT

  surname

FROM

  cd.members

UNION

SELECT

  name

FROM

  cd.facilities;

Explanation: This query returns a combined list of member surnames and facility names without duplicates.



6. Select booking times for a specific member

sql

Copy code

SELECT

  b.starttime

FROM

  cd.bookings b

  JOIN cd.members m ON b.memid = m.memid

WHERE

  m.firstname = 'David'

  AND m.surname = 'Farrell';

Explanation: This query retrieves the booking start times for the member "David Farrell."



7. Select tennis bookings on a specific date

sql

Copy code

SELECT

  b.starttime,

  f.name

FROM

  cd.bookings b

  JOIN cd.facilities f ON b.facid = f.facid

WHERE

  f.name LIKE '%Tennis%'

  AND DATE(b.starttime) = '2012-09-21'

ORDER BY

  b.starttime;

Explanation: This query retrieves the start times of tennis bookings on September 21, 2012, ordered by the start time.



8. Members and their recommenders

sql

Copy code

SELECT

  m.memid,

  m.surname,

  m.firstname,

  r.firstname AS recommended_firstname,

  r.surname AS recommended_surname

FROM

  cd.members m

  JOIN cd.members r ON m.recommendedby = r.memid

ORDER BY

  m.surname,

  m.firstname;

Explanation: This query retrieves members along with the names of their recommenders.



9. Distinct members who recommended others

sql

Copy code

SELECT

  DISTINCT r.memid,

  r.surname,

  r.firstname

FROM

  cd.members r

  JOIN cd.members m ON m.recommendedby = r.memid

ORDER BY

  r.surname,

  r.firstname;

Explanation: This query lists distinct members who have recommended others.



Aggregate Functions

10. Count of recommendations by each member

sql

Copy code

SELECT

  m.memid,

  m.firstname,

  m.surname,

  COUNT(r.memid) AS recommendation_count

FROM

  cd.members m

  LEFT JOIN cd.members r ON m.memid = r.recommendedby

GROUP BY

  m.memid,

  m.firstname,

  m.surname

ORDER BY

  m.memid;

Explanation: This query counts how many members each person has recommended.



11. Total slots booked for each facility

sql

Copy code

SELECT

  f.facid,

  SUM(b.slots) AS total_slots

FROM

  cd.bookings b

  LEFT JOIN cd.facilities f ON b.facid = f.facid

GROUP BY

  f.facid

ORDER BY

  f.facid;

Explanation: This query calculates the total number of slots booked for each facility.



12. Total slots booked in a specific month

sql

Copy code

SELECT

  f.facid,

  SUM(b.slots) AS total_slots

FROM

  cd.bookings b

  LEFT JOIN cd.facilities f ON b.facid = f.facid

WHERE

  b.starttime >= '2012-09-01'

  AND b.starttime < '2012-10-01'

GROUP BY

  f.facid

ORDER BY

  total_slots DESC;

Explanation: This query calculates the total number of slots booked in September 2012, ordered by the total number of slots in descending order.



13. Total slots booked per month for each facility

sql

Copy code

SELECT

  f.facid,

  EXTRACT(MONTH FROM b.starttime) AS month,

  SUM(b.slots) AS total_slots

FROM

  cd.bookings b

  LEFT JOIN cd.facilities f ON b.facid = f.facid

WHERE

  EXTRACT(YEAR FROM b.starttime) = 2012

GROUP BY

  f.facid,

  EXTRACT(MONTH FROM b.starttime)

ORDER BY

  f.facid,

  month;

Explanation: This query calculates the total number of slots booked per month for each facility in 2012.



14. Count of distinct members who made bookings

sql

Copy code

SELECT

  COUNT(DISTINCT b.memid) AS total_members

FROM

  cd.bookings b;

Explanation: This query counts the total number of distinct members who have made bookings.



15. First booking date for each member after a specific date

sql

Copy code

SELECT

  m.memid,

  m.firstname,

  m.surname,

  MIN(b.starttime) AS first_booking

FROM

  cd.members m

  JOIN cd.bookings b ON m.memid = b.memid

WHERE

  b.starttime > '2012-09-01'

GROUP BY

  m.memid

ORDER BY

  m.memid;

Explanation: This query retrieves the first booking date for each member after September 1, 2012.



16. Assign row numbers to members based on join date

sql

Copy code

SELECT

  ROW_NUMBER() OVER (ORDER BY joindate) AS member_number,

  memid,

  firstname || ' ' || surname AS member_name,

  joindate

FROM

  cd.members

ORDER BY

  member_number;

Explanation: This query assigns a unique, sequential number to each member based on their join date.



17. Facility with the maximum total slots booked

sql

Copy code

SELECT

  facid,

  SUM(slots) AS total

FROM

  cd.bookings

GROUP BY

  facid

HAVING

  SUM(slots) = (

    SELECT

      MAX(total_slots)

    FROM

      (

        SELECT

          SUM(slots) AS total_slots

        FROM

          cd.bookings

        GROUP BY

          facid

      ) AS subquery

  );

Explanation: This query retrieves the facility with the maximum number of total slots booked.



String Manipulation

18. Concatenate surname and firstname

sql

Copy code

SELECT

  CONCAT(surname, ', ', firstname) AS full_name

FROM

  cd.members;

Explanation: This query concatenates the surname and firstname columns to create a full_name.



19. Select members with telephone numbers containing parentheses

sql

Copy code

SELECT

  memid,

  telephone

FROM

  cd.members

WHERE

  telephone LIKE '%(%'

ORDER BY

  memid;

Explanation: This query retrieves members who have telephone numbers with parentheses.



20. Count of members by initial letter of surname

sql

Copy code

SELECT

  LEFT(surname, 1) AS initial_letter,

  COUNT(*) AS member_count

FROM

  cd.members

GROUP BY

  LEFT(surname, 1)

ORDER BY

  initial_letter;

Explanation: This query counts the number of members whose surname starts with each letter of the alphabet.

```
