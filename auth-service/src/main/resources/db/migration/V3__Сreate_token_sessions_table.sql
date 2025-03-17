CREATE TABLE token_sessions
(
    id           bigint GENERATED ALWAYS AS IDENTITY NOT NULL,
    session_hash character varying(255)              NOT NULL,
    user_id      bigint                              NOT NULL,
    created      timestamp without time zone,
    updated      timestamp without time zone,
    CONSTRAINT pk_token_sessions PRIMARY KEY (id),
    CONSTRAINT fk_token_sessions_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE CASCADE
);