create table users
(
    id uuid not null primary key,
    email    varchar(255),
    username varchar(255)
);

create table role
(
    id bigint not null primary key,
    users_id uuid references users
);