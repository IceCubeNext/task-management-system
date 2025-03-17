CREATE TABLE users
(
    id            bigint GENERATED ALWAYS AS IDENTITY NOT NULL,
    firstname     character varying(30)               NOT NULL,
    lastname      character varying(30)               NOT NULL,
    patronymic    character varying(30)               NOT NULL,
    user_login    character varying(30)               NOT NULL UNIQUE,
    user_password character varying(255)              NOT NULL,
    user_status   character varying(30)               NOT NULL,
    created       timestamp without time zone,
    updated       timestamp without time zone,
    CONSTRAINT pk_user PRIMARY KEY (id)
);