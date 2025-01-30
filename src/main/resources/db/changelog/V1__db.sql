--changelog hezix:1
CREATE TABLE IF NOT EXISTS users
(
    id BIGSERIAL primary key ,
    username varchar(32) not null unique,
    email varchar(96) not null unique,
    password varchar(32) not null,
    description varchar(255) ,
    role varchar(16),
    created_at timestamp not null,
    created_by varchar(32)
);
--changelog hezix:2
CREATE TABLE IF NOT EXISTS company
(
    id BIGSERIAL primary key ,
    title varchar(32) not null,
    description varchar(255) ,
    type varchar(16),
    created_at timestamp not null,
    created_by varchar(32)
);