CREATE SCHEMA IF NOT EXISTS company_schema;

ALTER TABLE IF EXISTS company_schema.employee ADD COLUMN IF NOT EXISTS photo_url VARCHAR(500);

SET search_path TO company_schema;

CREATE TABLE IF NOT EXISTS department (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL UNIQUE,
  description VARCHAR(255),
  created_at TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS address (
  id BIGSERIAL PRIMARY KEY,
  street_address_1 VARCHAR(255) NOT NULL,
  street_address_2 VARCHAR(255),
  city VARCHAR(255) NOT NULL,
  state VARCHAR(2) NOT NULL,
  zip VARCHAR(5) NOT NULL,
  zip_ext VARCHAR(4)
);

CREATE TABLE IF NOT EXISTS employee (
  id BIGSERIAL PRIMARY KEY,
  first_name VARCHAR(255) NOT NULL,
  middle_name VARCHAR(255),
  last_name VARCHAR(255) NOT NULL,
  salary INTEGER NOT NULL,
  address_id BIGINT,
  department_id BIGINT NOT NULL,
  created_at TIMESTAMP NOT NULL,
  FOREIGN KEY (address_id) REFERENCES address(id),
  FOREIGN KEY (department_id) REFERENCES department(id)
    -- ... Many other columns
);

CREATE TABLE sales_region (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,  
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE employee_sales_region (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    FOREIGN KEY (employee_id) REFERENCES employee(id),
    FOREIGN KEY (sales_region_id) REFERENCES sales_region(id)
);
