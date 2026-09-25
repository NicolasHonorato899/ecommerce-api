DROP TABLE IF EXISTS payments CASCADE;
DROP TABLE IF EXISTS order_items CASCADE;
DROP TABLE IF EXISTS orders CASCADE;
DROP TABLE IF EXISTS cart_items CASCADE;
DROP TABLE IF EXISTS carts CASCADE;
DROP TABLE IF EXISTS products CASCADE;
DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE users(
	email varchar PRIMARY KEY,
	password varchar,
	name varchar,
	address varchar,
	role varchar DEFAULT 'USER'
);

CREATE TABLE products(
	product_id varchar PRIMARY KEY,
	name varchar,
	description text,
	price float,
	stock integer
);

CREATE TABLE carts(
	cart_id varchar PRIMARY KEY,
	status varchar,
	created_at timestamp,
	updated_at timestamp,
	user_email varchar REFERENCES users(email)
);

CREATE TABLE cart_items(
	id varchar PRIMARY KEY,
	cart_id varchar REFERENCES carts(cart_id),
	product_id varchar REFERENCES products(product_id),
	quantity integer
);

CREATE TABLE orders(
	order_id varchar PRIMARY KEY,
	user_email varchar REFERENCES users(email),
	status varchar,
	amount float,
	created_at timestamp
);

CREATE TABLE order_items(
	id varchar PRIMARY KEY,
	order_id varchar REFERENCES orders(order_id),
	product_id varchar REFERENCES products(product_id),
	quantity integer,
	unit_price float
);

CREATE TABLE payments(
	payment_id varchar PRIMARY KEY,
	order_id varchar REFERENCES orders(order_id),
	amount float,
	currency varchar,
	status varchar,
	payment_method varchar,
	gateway varchar,
	gateway_transaction_id varchar UNIQUE,
	created_at timestamp,
	updated_at timestamp
);
