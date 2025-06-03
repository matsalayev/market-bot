CREATE TYPE GENDER AS ENUM ('male', 'female');

CREATE TYPE ROLE AS ENUM ('agent');

CREATE TABLE assets (
  id UUID PRIMARY KEY,
  created_at TIMESTAMP WITH TIME ZONE NOT NULL,
  s3_key VARCHAR NOT NULL,
  file_name VARCHAR NULL,
  content_type VARCHAR NULL
);

CREATE TABLE people (
	id UUID PRIMARY KEY,
	created_at TIMESTAMP WITH TIME ZONE NOT NULL,
	first_name VARCHAR NOT NULL,
	last_name VARCHAR NULL,
	gender GENDER NOT NULL,
	date_of_birth DATE NULL,
	updated_at TIMESTAMP WITH TIME ZONE NULL,
	deleted_at TIMESTAMP WITH TIME ZONE NULL
);

CREATE TABLE users (
  id UUID PRIMARY KEY REFERENCES people (id),
  created_at TIMESTAMP WITH TIME ZONE NOT NULL,
  role ROLE NOT NULL,
  phone VARCHAR NOT NULL UNIQUE,
  asset_id UUID NULL REFERENCES assets (id),
  password VARCHAR NULL,
  updated_at TIMESTAMP WITH TIME ZONE NULL,
  deleted_at TIMESTAMP WITH TIME ZONE NULL
);

CREATE TABLE telegram_users (
  person_id UUID UNIQUE REFERENCES people (id) NOT NULL,
  chat_id BIGINT UNIQUE NOT NULL,

  UNIQUE(person_id, chat_id)
);

CREATE TABLE markets (
  id UUID PRIMARY KEY,
  name VARCHAR NOT NULL,
  latitude FLOAT8 NOT NULL,
  longitude FLOAT8 NOT NULL,
  deleted_at TIMESTAMP WITH TIME ZONE NULL
);

CREATE TYPE MONEY_TYPE AS ENUM ('uzs', 'usd', 'eur', 'rub');

CREATE TABLE products (
  id UUID PRIMARY KEY,
  name VARCHAR NOT NULL,
  price FLOAT4 NOT NULL,
  money MONEY_TYPE NOT NULL,
  asset_id UUID NULL REFERENCES assets (id),
  market_id UUID NOT NULL REFERENCES markets (id),
  deleted_at TIMESTAMP WITH TIME ZONE NULL
);

CREATE TYPE ORDER_TYPE AS ENUM ('purchased', 'sold');

CREATE TABLE orders (
  id UUID PRIMARY KEY,
  created_at TIMESTAMP WITH TIME ZONE NOT NULL,
  created_by UUID NULL REFERENCES users (id),
  product_id UUID NOT NULL REFERENCES products (id),
  amount FLOAT4 NOT NULL,
  price FLOAT4 NOT NULL,
  money MONEY_TYPE NOT NULL,
  order_type ORDER_TYPE NOT NULL,
  market_id UUID NOT NULL REFERENCES markets (id)
);

CREATE VIEW product_summary AS
SELECT
  p.id,
  p.name,
  p.asset_id,
  COALESCE(SUM(CASE WHEN o.order_type = 'purchased' THEN o.amount ELSE 0 END), 0)
    - COALESCE(SUM(CASE WHEN o.order_type = 'sold' THEN o.amount ELSE 0 END), 0)
    AS amount,
  p.price,
  p.money,
  p.market_id
FROM products p
LEFT JOIN orders o ON p.id = o.product_id
WHERE p.deleted_at IS NULL
GROUP BY p.id, p.name, p.asset_id, p.price;
