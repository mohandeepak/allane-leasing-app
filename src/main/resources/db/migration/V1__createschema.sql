CREATE TABLE customer (
customer_id BIGINT PRIMARY KEY,
first_name VARCHAR(40),
last_name VARCHAR(40),
birth_date  DATE
);

CREATE TABLE vehicle (
vehicle_id BIGINT PRIMARY KEY,
brand VARCHAR(40),
model VARCHAR(40),
price  DOUBLE ,
vin VARCHAR(40),
year INT
);

CREATE TABLE contract (
contract_id BIGINT PRIMARY KEY,
contract_number INT,
monthly_rate DOUBLE,
customer_customer_id BIGINT,
vehicle_vehicle_id BIGINT,
FOREIGN KEY (customer_customer_id) REFERENCES customer (customer_id),
FOREIGN KEY (vehicle_vehicle_id ) REFERENCES vehicle (vehicle_id)
);

