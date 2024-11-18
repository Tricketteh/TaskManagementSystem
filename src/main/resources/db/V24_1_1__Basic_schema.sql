CREATE TABLE IF NOT EXISTS "users"
(
    id       BIGSERIAL PRIMARY KEY,
    username VARCHAR(50)  UNIQUE  NOT NULL,
    email    VARCHAR(135) UNIQUE NOT NULL,
    password VARCHAR(250)        NOT NULL,
    role     VARCHAR(50)         NOT NULL
);

CREATE TABLE IF NOT EXISTS tasks
(
    id          BIGSERIAL PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    status      VARCHAR(50)  NOT NULL,
    priority    VARCHAR(50)  NOT NULL,
    author_id   BIGINT REFERENCES "users" (id),
    assignee_id BIGINT REFERENCES "users" (id)
);

CREATE TABLE IF NOT EXISTS comments
(
    id        BIGSERIAL PRIMARY KEY,
    text      TEXT NOT NULL,
    author_id BIGINT REFERENCES "users" (id),
    task_id   BIGINT REFERENCES "tasks" (id)
);