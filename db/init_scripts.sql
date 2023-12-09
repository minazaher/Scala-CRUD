create extension hstore;
create schema test;
create table if not exists
test."users_table" ("user_id" NUMERIC NOT NULL PRIMARY KEY,
"name" VARCHAR NOT NULL,
"user_phone_number" NUMERIC);