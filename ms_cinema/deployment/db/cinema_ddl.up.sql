-- 
-- DDL script of the cinema database
-- Author: Luis Guillermo Gómez Galeano
-- GitHub: https://github.com/luisgomez29
-- LinkedIn: http://www.linkedin.com/in/luis-guillermo-gomez-galeano
--
SET
    client_encoding TO 'UTF8';

CREATE EXTENSION IF NOT EXISTS unaccent;

CREATE SCHEMA IF NOT EXISTS schcined;

-- genre table
CREATE TABLE schcined.genre(
    id SERIAL PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    description VARCHAR(255)
);

-- country table
CREATE TABLE schcined.country(
    id SERIAL PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    description VARCHAR(255)
);

-- movie table
CREATE TABLE schcined.movie(
    id SERIAL PRIMARY KEY,
    genre_id INT REFERENCES schcined.genre(id),
    country INT REFERENCES schcined.country(id),
    name VARCHAR(100) NOT NULL,
    duration DECIMAL,
    description TEXT
);

-- actor table
CREATE TABLE schcined.actor(
    id SERIAL PRIMARY KEY,
    country INT REFERENCES schcined.country(id),
    firs_name VARCHAR(30) NOT NULL,
    last_name VARCHAR(30) NOT NULL,
    email VARCHAR(30) NOT NULL
);

-- character table
CREATE TABLE schcined.character(
    id SERIAL PRIMARY KEY,
    actor_id INT REFERENCES schcined.actor(id),
    movie_id INT REFERENCES schcined.movie(id),
    name VARCHAR(30) NOT NULL,
    description TEXT
);