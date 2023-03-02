-- liquibase formatted sql

-- changeset liquibase:1
create table customers
(
    customer_id    serial,
    last_name      varchar(100),
    first_name     varchar(100),
    middle_initial varchar(1),
    street         varchar(100),
    city           varchar(255),
    state          varchar(100),
    zip            varchar(20),
    phone          varchar(50),
    email          varchar(100) unique,
    primary key (customer_id)
)