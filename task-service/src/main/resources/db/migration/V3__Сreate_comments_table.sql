CREATE TABLE comments
(
    id          BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
    title       VARCHAR(255)                        NOT NULL,
    description VARCHAR(5000)                       NOT NULL,
    task_id     BIGINT                              NOT NULL,
    author_id   BIGINT                              NOT NULL,
    CONSTRAINT pk_comment PRIMARY KEY (id),
    CONSTRAINT fk_comment_task
        FOREIGN KEY (task_id)
            REFERENCES tasks (id)
            ON DELETE CASCADE,
    CONSTRAINT fk_comment_author
        FOREIGN KEY (author_id)
            REFERENCES users (id)
            ON DELETE CASCADE
);