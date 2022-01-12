create table card
(
    id     uuid    not null primary key,
    number integer not null
);

create table course
(
    id   uuid not null primary key,
    name varchar(255)
);

create table users
(
    id       uuid not null primary key,
    email    varchar(255),
    username varchar(255),
    card_id  uuid references card
);

create table course_user_course
(
    user_id   uuid not null      references users,
    course_id uuid not null   references course
);

create table role
(
    id       uuid not null  primary key,
    name     varchar(255),
    users_id uuid    references users
);