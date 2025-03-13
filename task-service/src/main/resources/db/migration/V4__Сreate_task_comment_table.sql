CREATE TABLE task_comment
(
    id         BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
    task_id    BIGINT                              NOT NULL,
    comment_id BIGINT                              NOT NULL,
    CONSTRAINT pk_task_comment PRIMARY KEY (id),
    CONSTRAINT fk_task_comment_task
        FOREIGN KEY (task_id)
            REFERENCES task (id)
            ON DELETE CASCADE,
    CONSTRAINT fk_task_comment_comment
        FOREIGN KEY (comment_id)
            REFERENCES comment (id)
            ON DELETE CASCADE
);