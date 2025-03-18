CREATE TABLE users
(
    id         BIGINT       NOT NULL,
    login      VARCHAR(255) NOT NULL UNIQUE,
    firstname  VARCHAR(255) NOT NULL,
    lastname   VARCHAR(255) NOT NULL,
    patronymic VARCHAR(255),
    CONSTRAINT pk_user PRIMARY KEY (id)
);