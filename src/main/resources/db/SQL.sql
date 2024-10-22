-- INSERTIONS


INSERT INTO taxi_company.statuses (title) VALUES ('Regular'), ('VIP');

INSERT INTO taxi_company.trip_statuses (title) VALUES 
    ('Requested'), 
    ('Accepted'), 
    ('Arrived'), 
    ('In progress'), 
    ('Completed'), 
    ('Cancelled');


INSERT INTO taxi_company.payment_methods (title, description) VALUES 
    ('Cash', 'You have paid in cash'), 
    ('Card', 'You have paid by card');


INSERT INTO taxi_company.promotions (promotion_code, description, discount_percentage, expiration_date, is_active)
VALUES
    ('PROMO10', '10% off on all rides', 10.00, '2024-12-31 23:59:59', 1),
    ('SUMMER25', '25% off during summer season', 25.00, '2024-08-31 23:59:59', 0),
    ('WELCOME5', '5% off for new customers', 5.00, '2025-01-01 23:59:59', 1),
    ('LOYALTY20', '20% off for loyal customers', 20.00, '2024-11-30 23:59:59', 1),
    ('SPRING15', '15% off on spring rides', 15.00, '2024-05-31 23:59:59', 0);

INSERT INTO taxi_company.vehicles (model, year, plate_number) VALUES
    ('Toyota Prius', 2020, 'ABC123');

INSERT INTO taxi_company.vehicles (model, year, plate_number) VALUES
    ('Honda Civic', 2019, 'XYZ789');

INSERT INTO taxi_company.vehicles (model, year, plate_number) VALUES
    ('Tesla Model 3', 2021, 'TES123');

INSERT INTO taxi_company.vehicles (model, year, plate_number) VALUES
    ('Ford Escape', 2018, 'FOR456');

INSERT INTO taxi_company.vehicles (model, year, plate_number) VALUES
    ('Chevrolet Malibu', 2022, 'CHE789');


INSERT INTO taxi_company.vehicle_maintenances (maintenance_date, description, vehicle_id)
VALUES
    ('2024-10-01 10:30:00', 'Oil change and tire rotation', 1),
    ('2024-09-15 14:00:00', 'Brake pad replacement', 2),
    ('2024-08-20 09:00:00', 'Battery replacement', 3),
    ('2024-07-05 11:00:00', 'Transmission check', 4),
    ('2024-06-25 15:30:00', 'Air conditioning repair', 5);


INSERT INTO taxi_company.drivers (license_number, vehicle_id, first_name, last_name, rating)
VALUES
    ('XA123456', 1, 'Gleb', 'Ivanov', 4.8),
    ('NS789101', 2, 'Arthur', 'Morgan', 4.6),
    ('AG112131', 3, 'Joel', 'Miller', 4.9),
    ('ML415161', 4, 'John', 'Marston', 4.7),
    ('TK718192', 5, 'Geralt', 'Rivia', 4.5);


INSERT INTO taxi_company.customer_statuses (vip_status_change_date, vip_expiration_date, status_id)
VALUES
    (NULL, NULL, 1),
    (NULL, NULL, 1),
    ('2024-10-01 10:00:00', '2024-12-31 23:59:59', 2),
    ('2024-10-01 10:00:00', '2024-12-31 23:59:59', 2);


INSERT INTO taxi_company.customers (first_name, last_name, customer_status_id)
VALUES
    ('Bobby', 'Brown', 1),
    ('Charlie', 'Charlson', 2),
    ('Smith', 'Smooth', 3),
    ('Diana', 'Door', 4);


INSERT INTO taxi_company.start_locations (city, street) VALUES
    ('New York', '123 First St'),
    ('Los Angeles', '789 Second St'),
    ('Kyiv', '654 Third St'),
    ('Warsaw', '135 Fourth St');


INSERT INTO taxi_company.end_locations (city, street) VALUES
    ('New York', '456 First St'),
    ('Los Angeles', '321 Second St'),
    ('Kyiv', '987 Third St'),
    ('Warsaw', '246 Fourth St');


INSERT INTO taxi_company.payments (payment_method_id, amount, date) VALUES
    (1, 20.00, '2024-10-08 08:30:00'),
    (2, 15.00, '2024-10-08 09:30:00'), 
    (1, 22.00, '2024-10-08 10:45:00'),
    (2, 18.00, '2024-10-08 11:30:00');


INSERT INTO taxi_company.trips (duration, distance, payment_id, trip_status_id, end_location_id, start_location_id, customer_id, driver_id) VALUES
    (30.0, 10.5, 1, 1, 1, 1, 1, 1),
    (45.0, 15.0, 2, 2, 2, 2, 2, 2),
    (20.0, 8.0, 3, 3, 3, 3, 3, 3),
    (50.0, 12.5, 4, 4, 4, 4, 4, 4);
    

-- ALTER TABLE

ALTER TABLE taxi_company.customers
ADD COLUMN email VARCHAR(20) AFTER last_name;

ALTER TABLE taxi_company.customers
MODIFY COLUMN email VARCHAR(50);

ALTER TABLE taxi_company.customers
ADD CONSTRAINT unique_email UNIQUE (email);

ALTER TABLE taxi_company.customers
DROP INDEX unique_email;

ALTER TABLE taxi_company.customers
DROP COLUMN email;

-- UPDATE

UPDATE taxi_company.vehicles
SET model = 'New Toyota', year = 2024, plate_number = 'CAM123'
WHERE vehicle_id = 1;

-- Here I need to change safety level to update the data
SET SQL_SAFE_UPDATES = 0;

UPDATE taxi_company.drivers
SET rating = 4.9
WHERE license_number = 'XA123456';


UPDATE taxi_company.customers
SET first_name = 'Gleb', last_name = 'Brown'
WHERE customer_status_id = 1;

UPDATE taxi_company.payments
SET amount = 25.00
WHERE payment_id = 1;

UPDATE taxi_company.trips
SET trip_status_id = 3
WHERE trip_id = 1;

UPDATE taxi_company.promotions
SET is_active = 0
WHERE promotion_code = 'SUMMER25';

UPDATE taxi_company.vehicle_maintenances
SET description = 'Everything is all right'
WHERE maintenance_date = '2024-10-01 10:30:00';

UPDATE taxi_company.payments
SET payment_method_id = 2
WHERE payment_id = 1;


UPDATE taxi_company.start_locations
SET street = '456 Updated Street'
WHERE city = 'New York';


UPDATE taxi_company.end_locations
SET city = 'Chicago', street = 'New Street Hello'
WHERE city = 'Los Angeles';

-- DELETE
-- I did it as a transaction, so that I wouldn't really delete something

START TRANSACTION;

BEGIN;

DELETE FROM taxi_company.customers c WHERE c.customer_id = 4;

DELETE FROM taxi_company.vehicles v WHERE v.model = 'Toyota Prius';

DELETE FROM taxi_company.drivers d WHERE d.driver_id = 3;

DELETE FROM taxi_company.statuses s WHERE s.title = 'VIP';

DELETE FROM taxi_company.promotions p WHERE p.promotion_code = 'PROMO10';

DELETE FROM taxi_company.payments p WHERE p.payment_id = 3;

DELETE FROM taxi_company.trips t WHERE t.duration > 35;

DELETE FROM taxi_company.promotions p WHERE p.discount_percentage < 15;

DELETE FROM taxi_company.promotions p WHERE p.is_active = 0;

DELETE FROM taxi_company.vehicle_maintenances m WHERE m.maintenance_date = '2024-06-25 15:30:00';


ROLLBACK;

-- BIG_STATEMENT

SELECT 
    c.customer_id AS customer_id,
    CONCAT(c.first_name, ' ', c.last_name) AS customer_name,
    t.trip_id AS trip_id,
    t.duration AS trip_duration,
    t.distance AS trip_distance,
    d.driver_id AS driver_id,
    CONCAT(d.first_name, ' ', d.last_name) AS driver_name,
    p.amount AS payment_amount,
    e.city AS end_city,
    e.street AS end_street
FROM taxi_company.customers c
JOIN taxi_company.trips t ON c.customer_id = t.customer_id
JOIN taxi_company.drivers d ON t.driver_id = d.driver_id
JOIN taxi_company.payments p ON t.payment_id = p.payment_id
JOIN taxi_company.end_locations e ON t.end_location_id = e.end_location_id;

-- JOINS

SELECT 
    c.customer_id,
    CONCAT(c.first_name, ' ', c.last_name) AS customer_name,
    d.driver_id,
    CONCAT(d.first_name, ' ', d.last_name) AS driver_name
FROM taxi_company.customers c
INNER JOIN taxi_company.trips t ON c.customer_id = t.customer_id
INNER JOIN taxi_company.drivers d ON t.driver_id = d.driver_id;


SELECT 
    c.customer_id,
    CONCAT(c.first_name, ' ', c.last_name) AS customer_name,
    t.trip_id,
    t.duration AS trip_duration
FROM taxi_company.customers c
LEFT JOIN taxi_company.trips t ON c.customer_id = t.customer_id;

SELECT 
    d.driver_id,
    CONCAT(d.first_name, ' ', d.last_name) AS driver_name,
    t.trip_id,
    t.duration
FROM taxi_company.drivers d
RIGHT JOIN taxi_company.trips t ON d.driver_id = t.driver_id;


SELECT 
    c.customer_id,
    CONCAT(c.first_name, ' ', c.last_name) AS customer_name,
    p.payment_id,
    p.amount
FROM taxi_company.customers c
LEFT JOIN taxi_company.payments p ON c.customer_id = p.payment_method_id;

SELECT 
    t.trip_id,
    CONCAT(c.first_name, ' ', c.last_name) AS customer_name,
    CONCAT(d.first_name, ' ', d.last_name) AS driver_name,
    t.duration AS trip_duration,
    t.distance AS trip_distance,
    p.amount AS payment_amount,
    ts.title AS trip_status
FROM taxi_company.trips t
INNER JOIN taxi_company.customers c ON t.customer_id = c.customer_id
INNER JOIN taxi_company.drivers d ON t.driver_id = d.driver_id
INNER JOIN taxi_company.payments p ON t.payment_id = p.payment_id
INNER JOIN taxi_company.trip_statuses ts ON t.trip_status_id = ts.trip_status_id;


-- GROUP

SELECT 
    s.title, 
    COUNT(c.customer_id) AS customer_count
FROM taxi_company.customer_statuses cs
JOIN taxi_company.customers c ON cs.customer_status_id = c.customer_status_id
JOIN taxi_company.statuses s ON cs.status_id = s.status_id
GROUP BY s.title;

SELECT 
    CONCAT(d.first_name, ' ', d.last_name) AS driver_name, 
    COUNT(t.trip_id) AS total_trips
FROM taxi_company.drivers d
LEFT JOIN taxi_company.trips t ON d.driver_id = t.driver_id
GROUP BY driver_name;


SELECT 
    CONCAT(c.first_name, ' ', c.last_name) AS customer_name, 
    SUM(p.amount) AS total_payments
FROM taxi_company.customers c
JOIN taxi_company.trips t ON c.customer_id = t.customer_id
JOIN taxi_company.payments p ON t.payment_id = p.payment_id
GROUP BY customer_name;

SELECT  
    ts.title AS trip_status, 
    SUM(p.amount) AS total_earnings
FROM taxi_company.trips t
JOIN taxi_company.trip_statuses ts ON t.trip_status_id = ts.trip_status_id
JOIN taxi_company.payments p ON t.payment_id = p.payment_id
GROUP BY trip_status;

SELECT 
    CONCAT(c.first_name, ' ', c.last_name) AS customer_name, 
    MAX(p.amount) AS max_payment
FROM taxi_company.customers c
JOIN taxi_company.trips t ON c.customer_id = t.customer_id
JOIN taxi_company.payments p ON t.payment_id = p.payment_id
GROUP BY customer_name;

SELECT 
    CONCAT(d.first_name, ' ', d.last_name) AS driver_name, 
    SUM(t.distance) AS total_distance
FROM taxi_company.drivers d
JOIN taxi_company.trips t ON d.driver_id = t.driver_id
GROUP BY driver_name;

SELECT 
    CONCAT(d.first_name, ' ', d.last_name) AS driver_name, 
    AVG(t.duration) AS average_duration
FROM taxi_company.drivers d
JOIN taxi_company.trips t ON d.driver_id = t.driver_id
GROUP BY driver_name;



-- GROUP_HAVING

SELECT 
    d.driver_id, 
    CONCAT(d.first_name, ' ', d.last_name) AS driver_name, 
    COUNT(t.trip_id) AS total_trips
FROM taxi_company.drivers d
LEFT JOIN taxi_company.trips t ON d.driver_id = t.driver_id
GROUP BY d.driver_id
HAVING total_trips > 0;


SELECT 
    c.customer_id, 
    CONCAT(c.first_name, ' ', c.last_name) AS customer_name, 
    SUM(p.amount) AS total_payments
FROM taxi_company.customers c
JOIN taxi_company.trips t ON c.customer_id = t.customer_id
JOIN taxi_company.payments p ON t.payment_id = p.payment_id
GROUP BY c.customer_id
HAVING total_payments > 0;

SELECT 
    cs.status_id, 
    s.title AS status_title, 
    COUNT(c.customer_id) AS customer_count
FROM taxi_company.customer_statuses cs
JOIN taxi_company.customers c ON cs.customer_status_id = c.customer_status_id
JOIN taxi_company.statuses s ON cs.status_id = s.status_id
GROUP BY cs.status_id
HAVING customer_count > 1; 

SELECT 
    d.driver_id, 
    CONCAT(d.first_name, ' ', d.last_name) AS driver_name, 
    SUM(t.distance) AS total_distance
FROM taxi_company.drivers d
JOIN taxi_company.trips t ON d.driver_id = t.driver_id
GROUP BY d.driver_id
HAVING total_distance > 0;

SELECT 
    ts.trip_status_id, 
    ts.title AS trip_status, 
    AVG(t.duration) AS average_duration
FROM taxi_company.trips t
JOIN taxi_company.trip_statuses ts ON t.trip_status_id = ts.trip_status_id
GROUP BY ts.trip_status_id
HAVING average_duration > 30;


SELECT 
    d.driver_id, 
    CONCAT(d.first_name, ' ', d.last_name) AS driver_name, 
    SUM(p.amount) AS total_earnings
FROM taxi_company.drivers d
JOIN taxi_company.trips t ON d.driver_id = t.driver_id
JOIN taxi_company.payments p ON t.payment_id = p.payment_id
GROUP BY d.driver_id
HAVING total_earnings > 20;

SELECT 
    ts.trip_status_id, 
    ts.title AS trip_status, 
    MAX(t.duration) AS max_duration
FROM taxi_company.trips t
JOIN taxi_company.trip_statuses ts ON t.trip_status_id = ts.trip_status_id
GROUP BY ts.trip_status_id
HAVING max_duration > 60;









