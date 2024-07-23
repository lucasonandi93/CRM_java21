-- Drop everything to start anew 
DROP TABLE IF EXISTS filrouge.orders cascade;
DROP TABLE IF EXISTS filrouge.estimates cascade;
DROP TABLE IF EXISTS filrouge.users cascade;
DROP TABLE IF EXISTS filrouge.user_action cascade;
DROP TABLE IF EXISTS filrouge.customers cascade;
DROP TABLE IF EXISTS filrouge.grants cascade;

DROP TYPE IF EXISTS enum_grant;


-- Function for columns 'updated_at'
CREATE OR REPLACE FUNCTION trigger_set_timestamp()
RETURNS TRIGGER AS '
BEGIN
  NEW.updated_at = NOW();
  RETURN NEW;
END
' LANGUAGE plpgsql;



-- Create enum types
CREATE TYPE enum_grant AS 
ENUM('RESPONSABLE', 'GESTIONNAIRE_DES_VENTES', 'SERVICE_CLIENT');


-- Create tables
CREATE TABLE filrouge.users (
    user_id SERIAL PRIMARY key not null,
    firstname VARCHAR(50),
    lastname VARCHAR(50),
    email VARCHAR(100),
    user_password VARCHAR(255),
    grant_name VARCHAR(50),
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE filrouge.user_action (
    id SERIAL PRIMARY KEY,
    user_id INT,
    method VARCHAR(10),
    action_realisee VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE filrouge.user_action
ADD CONSTRAINT fk_users_useraction FOREIGN KEY (user_id) REFERENCES filrouge.users(user_id);

CREATE TABLE filrouge.customers (
    customer_id SERIAL PRIMARY KEY NOT null,
    firstname VARCHAR(50),
    lastname VARCHAR(50),
    email VARCHAR(255),
    company VARCHAR(50),
    office_phone VARCHAR(20),
    mobile_phone VARCHAR(20),
    customer_status VARCHAR(30),
    guarantee BOOLEAN,
    customer_comment text,
    user_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_users_customers FOREIGN KEY (user_id) REFERENCES filrouge.users(user_id)
);

CREATE TABLE filrouge.estimates (
    estimate_id SERIAL PRIMARY KEY,
    estimate_label VARCHAR(50),
    number_of_days INT,
    average_daily_rate DECIMAL(10,4),
    tva DECIMAL(10,4),
    estimate_status VARCHAR(30),
    estimate_type VARCHAR(50),
    estimate_comment text,
    transfered BOOLEAN DEFAULT FALSE,
    user_id INT NOT NULL,
    customer_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_users_estimates FOREIGN KEY (user_id) REFERENCES filrouge.users(user_id),
    CONSTRAINT fk_customers_estimates FOREIGN KEY (customer_id) REFERENCES filrouge.customers(customer_id)
);

CREATE TABLE filrouge.orders (
    order_id SERIAL PRIMARY KEY,
    order_label VARCHAR(50),
    order_type VARCHAR(50),
    order_status VARCHAR(30),
    order_comment text,
    user_id INT NOT NULL,
    estimate_id INT NOT NULL, 
    customer_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_users_orders FOREIGN KEY (user_id) REFERENCES filrouge.users(user_id),
    CONSTRAINT fk_estimates_orders FOREIGN KEY (estimate_id) REFERENCES filrouge.estimates(estimate_id),
    CONSTRAINT fk_customers_orders FOREIGN KEY (customer_id) REFERENCES filrouge.customers(customer_id)
);


-- Create the triggers
CREATE TRIGGER set_timestamp_update_users
BEFORE UPDATE ON filrouge.users
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();

CREATE TRIGGER set_timestamp_update_customers
BEFORE UPDATE ON filrouge.customers
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();

CREATE TRIGGER set_timestamp_update_estimates
BEFORE UPDATE ON filrouge.estimates
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();

CREATE TRIGGER set_timestamp_update_orders
BEFORE UPDATE ON filrouge.orders
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();

