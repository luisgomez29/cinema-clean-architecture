-- 
-- DDL script of the cinema database
-- Author: Luis Guillermo Gómez Galeano
-- GitHub: https://github.com/luisgomez29
-- LinkedIn: http://www.linkedin.com/in/luis-guillermo-gomez-galeano
--

SET client_encoding TO 'UTF8';

CREATE EXTENSION IF NOT EXISTS unaccent;

CREATE SCHEMA IF NOT EXISTS schcined;

-- genre table
CREATE TABLE schcined.genre(
    id SERIAL PRIMARY KEY,
    name VARCHAR(30) UNIQUE NOT NULL,
    description TEXT
);

-- movie table
CREATE TABLE schcined.movie(
    id SERIAL PRIMARY KEY,
    genre_id INT REFERENCES schcined.genre(id),
    country varchar(30),
    name VARCHAR(100) UNIQUE NOT NULL,
    duration DECIMAL NOT NULL,
    description TEXT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);

-- https://bucket4j.com/8.14.0/toc.html#bucket4j-postgresql
CREATE TABLE IF NOT EXISTS schcined.bucket(id BIGINT PRIMARY KEY, state BYTEA);

