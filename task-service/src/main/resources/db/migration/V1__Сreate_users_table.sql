CREATE TABLE users
(
    id         BIGINT       NOT NULL,
    firstname  VARCHAR(255) NOT NULL,
    lastname   VARCHAR(255) NOT NULL,
    patronymic VARCHAR(255),
    CONSTRAINT pk_user PRIMARY KEY (id)
);