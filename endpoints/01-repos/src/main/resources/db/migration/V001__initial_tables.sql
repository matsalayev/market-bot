CREATE TYPE GENDER AS ENUM (
	'male',
	'female'
);

CREATE TYPE ROLE AS ENUM (
	'agent'
);

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
	full_name VARCHAR NOT NULL,
	gender GENDER NOT NULL,
	date_of_birth DATE NULL,
	document_number VARCHAR NULL,
	pinfl_number VARCHAR NULL,
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

CREATE TABLE telegram_bot_users (
  person_id UUID UNIQUE REFERENCES people (id) NOT NULL,
  chat_id BIGINT UNIQUE NOT NULL
);