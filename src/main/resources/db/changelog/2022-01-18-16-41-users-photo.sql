create table users_photo
(
    id       uuid not null primary key,
    photo_id     varchar(255),
    users_id uuid    references users
);