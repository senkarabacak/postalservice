CREATE DATABASE postdb;
\c postdb;

CREATE TABLE IF NOT EXISTS letter (
   id serial PRIMARY KEY,
   name VARCHAR(255) NOT NULL,
   country VARCHAR(255) NOT NULL,
   status VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS package (
   id serial PRIMARY KEY,
   name VARCHAR(255) NOT NULL,
   weight DOUBLE PRECISION NOT NULL,
   status VARCHAR(255) NOT NULL
);
