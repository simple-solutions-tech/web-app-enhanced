INSERT INTO department (name, description, created_at) VALUES
('Engineering', 'Software development', '2025-04-23 00:00:00'),
('HR', 'Human resources', '2025-04-23 00:00:00')
ON CONFLICT DO NOTHING;

INSERT INTO address (street_address_1, street_address_2, city, state, zip, zip_ext) VALUES
('100 Main Street', 'Apt 1', 'Citytown', 'NC', '28105', '1234'),
('1 Corporate Dr', null, 'Homeville', 'CA', '90210', null),
('9999 Country Road', null, 'Mountainton', 'WV', '24974', '9876')
ON CONFLICT DO NOTHING;

INSERT INTO employee (first_name, middle_name, last_name, salary, address_id, department_id, created_at) VALUES
('JOHN', 'A', 'JOE', 1200, 1, 1, '2025-04-23 00:00:00'),
('JANE', NULL, 'SMITH', 1250, 2, 1, '2025-04-23 00:00:00'),
('JACK', NULL, 'SMITH', 1500, 2, 1, '2025-04-23 00:00:00'),
('ALICE', 'B', 'JOHNSON', 200, 3, 2, '2025-04-23 00:00:00')
ON CONFLICT DO NOTHING;