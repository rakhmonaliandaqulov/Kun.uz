CREATE TABLE if not exists book
(
    id          serial,
    description varchar(255) DEFAULT NULL,
    title       varchar(255) DEFAULT NULL,
    PRIMARY KEY (id)
);