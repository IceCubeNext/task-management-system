CREATE TABLE tasks
(
    id          BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
    title       VARCHAR(255)                        NOT NULL,
    description VARCHAR(5000)                       NOT NULL,
    status      VARCHAR(255)                        NOT NULL,
    priority    VARCHAR(255)                        NOT NULL,
    author_id      BIGINT                        NOT NULL,
    performer_id   BIGINT                        NOT NULL,
    CONSTRAINT pk_task PRIMARY KEY (id),
    CONSTRAINT fk_task_author
        FOREIGN KEY (author_id)
            REFERENCES users (id)
            ON DELETE CASCADE,
    CONSTRAINT fk_task_performer
        FOREIGN KEY (performer_id)
            REFERENCES users (id)
            ON DELETE CASCADE
);