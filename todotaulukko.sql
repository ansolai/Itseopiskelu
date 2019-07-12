BEGIN;

SET client_encoding = 'UTF-8';

DROP TABLE IF EXISTS todotaulukko CASCADE;

CREATE TABLE todotaulukko (
id SERIAL PRIMARY KEY,
task varchar(255) NOT NULL);