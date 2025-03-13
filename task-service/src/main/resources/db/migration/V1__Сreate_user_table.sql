CREATE TABLE user
(
    id         BIGINT       NOT NULL,
    firstname  VARCHAR(255) NOT NULL,
    lastname   VARCHAR(255) NOT NULL,
    patronymic VARCHAR(255) NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);