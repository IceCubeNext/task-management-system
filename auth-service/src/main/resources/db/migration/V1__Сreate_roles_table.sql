CREATE TABLE roles
(
    id        bigint GENERATED ALWAYS AS IDENTITY NOT NULL,
    role_name character varying(50) NOT NULL,
    created   timestamp without time zone,
    updated   timestamp without time zone,
    CONSTRAINT pk_role PRIMARY KEY (id)
);