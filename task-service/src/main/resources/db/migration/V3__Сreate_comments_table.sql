CREATE TABLE comments
(
    id          BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
    title       VARCHAR(255)                        NOT NULL,
    description VARCHAR(5000)                       NOT NULL,
    CONSTRAINT pk_comment PRIMARY KEY (id)
);