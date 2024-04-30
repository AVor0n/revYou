CREATE TABLE IF NOT EXISTS cohort
(
    cohort_id            SERIAL PRIMARY KEY,
    cohort_number        INTEGER NOT NULL,
    education_start_date DATE,
    education_end_date   DATE,
    mm_channel_link      VARCHAR(1000),
    study_direction      VARCHAR(10)
);

CREATE TABLE IF NOT EXISTS user_account
(
    user_id         SERIAL PRIMARY KEY,
    username        VARCHAR(50)  NOT NULL UNIQUE,
    name            VARCHAR(50),
    surname         VARCHAR(50),
    email           VARCHAR(100) NOT NULL UNIQUE,
    password        VARCHAR(100) NOT NULL,
    gitlab_username VARCHAR(1000) UNIQUE,
    mm_username     VARCHAR(1000) UNIQUE,
    status          VARCHAR(20)  NOT NULL,
    role            VARCHAR(10)  NOT NULL,
    cohort_id       INTEGER,
    FOREIGN KEY (cohort_id) REFERENCES cohort (cohort_id)
);


CREATE TABLE IF NOT EXISTS lecture
(
    lecture_id        SERIAL PRIMARY KEY,
    name              VARCHAR(100) NOT NULL,
    lecture_date      TIMESTAMP,
    zoom_link         VARCHAR(1000),
    presentation_link VARCHAR(1000),
    lector_id         INTEGER,
    FOREIGN KEY (lector_id) REFERENCES user_account (user_id)
);

CREATE TABLE IF NOT EXISTS lecture_cohort
(
    lecture_id INTEGER NOT NULL,
    cohort_id  INTEGER NOT NULL,
    PRIMARY KEY (lecture_id, cohort_id),
    FOREIGN KEY (lecture_id) REFERENCES lecture (lecture_id),
    FOREIGN KEY (cohort_id) REFERENCES cohort (cohort_id)
);

CREATE TABLE IF NOT EXISTS homework
(
    homework_id         SERIAL PRIMARY KEY,
    repository_link     VARCHAR(1000) NOT NULL,
    name                VARCHAR(100)  NOT NULL,
    topic               VARCHAR(100)  NOT NULL,
    description         VARCHAR(500),
    start_date          TIMESTAMP     NOT NULL DEFAULT NOW(),
    completion_deadline TIMESTAMP     NOT NULL,
    review_duration     INTEGER       NOT NULL,
    lecture_id          INTEGER,
    author_id           INTEGER,
    FOREIGN KEY (lecture_id) REFERENCES lecture (lecture_id),
    FOREIGN KEY (author_id) REFERENCES user_account (user_id)
);

CREATE TABLE IF NOT EXISTS solution
(
    solution_id   SERIAL PRIMARY KEY,
    status        VARCHAR(20),
    repository    VARCHAR(255) NOT NULL,
    branch        VARCHAR(255) NOT NULL,
    approve_score INTEGER      NOT NULL,
    review_score  INTEGER      NOT NULL,
    branch_link   VARCHAR(500) NOT NULL UNIQUE,
    homework_id   INTEGER      NOT NULL,
    student_id    INTEGER      NOT NULL,
    FOREIGN KEY (homework_id) REFERENCES homework (homework_id) ON DELETE CASCADE,
    FOREIGN KEY (student_id) REFERENCES user_account (user_id),
    UNIQUE (homework_id, student_id)
);

CREATE TABLE IF NOT EXISTS solution_attempt
(
    solution_attempt_id SERIAL PRIMARY KEY,
    commit_id           VARCHAR(500) NOT NULL,
    created_at          TIMESTAMP    NOT NULL DEFAULT NOW(),
    solution_id         INTEGER      NOT NULL,
    FOREIGN KEY (solution_id) REFERENCES solution (solution_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS review
(
    review_id   SERIAL PRIMARY KEY,
    student_id  INTEGER NOT NULL,
    reviewer_id INTEGER,
    status      VARCHAR(20),
    solution_id INTEGER NOT NULL,
    FOREIGN KEY (student_id) REFERENCES user_account (user_id) ON DELETE CASCADE,
    FOREIGN KEY (reviewer_id) REFERENCES user_account (user_id),
    FOREIGN KEY (solution_id) REFERENCES solution (solution_id)
);

CREATE TABLE IF NOT EXISTS review_attempt
(
    review_attempt_id   SERIAL PRIMARY KEY,
    review_id           INTEGER NOT NULL,
    solution_attempt_id INTEGER NOT NULL,
    created_at          TIMESTAMP DEFAULT NOW(),
    finished_at         TIMESTAMP,
    resolution          VARCHAR(500),
    FOREIGN KEY (review_id) REFERENCES review (review_id) ON DELETE CASCADE,
    FOREIGN KEY (solution_attempt_id) REFERENCES solution_attempt (solution_attempt_id)
);

CREATE TABLE IF NOT EXISTS thread
(
    thread_id    SERIAL PRIMARY KEY,
    review_id    INTEGER       NOT NULL,
    file_path    VARCHAR(1000) NOT NULL,
    start_line   INTEGER       NOT NULL,
    start_symbol INTEGER       NOT NULL,
    end_line     INTEGER       NOT NULL,
    end_symbol   INTEGER       NOT NULL,
    FOREIGN KEY (review_id) REFERENCES review (review_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS comment
(
    comment_id SERIAL PRIMARY KEY,
    thread_id  INTEGER NOT NULL,
    author_id  INTEGER NOT NULL,
    content    VARCHAR(255),
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP,
    FOREIGN KEY (thread_id) REFERENCES thread (thread_id) ON DELETE CASCADE,
    FOREIGN KEY (author_id) REFERENCES user_account (user_id)
);


-- Вставка данных в таблицу cohort
INSERT INTO cohort (cohort_number, education_start_date, education_end_date, mm_channel_link, study_direction)
VALUES (1, '2023-09-01', '2024-06-30', 'https://mattermost.com/frontend1', 'FRONT'),
       (2, '2023-09-15', '2024-07-15', 'https://mattermost.com/backend1', 'BACK');

-- Вставка данных в таблицу user_account
INSERT INTO user_account (username, name, surname, email, password, gitlab_username, mm_username, status, role,
                          cohort_id)
VALUES ('username_1', 'Иван', 'Иванов', 'ivanov@example.com',
        '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i',
        'username_1', 'ivanov',
        'ACTIVE', 'STUDENT', 1),
       ('username_2', 'Мария', 'Петрова', 'petrova@example.com',
        '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i',
        'username_2', 'petrova',
        'DISABLED', 'STUDENT', 2),
       ('username_3', 'Антон', 'Сидоров', 'sidorov@example.com',
        '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i',
        'username_3', 'sidorov',
        'ACTIVE', 'TEACHER', NULL),
       ('username_4', 'Елена', 'Козлова', 'kozlova@example.com',
        '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i',
        'username_4', 'kozlova',
        'ACTIVE', 'STUDENT', 1),
       ('username_5', 'Геннадий', 'Горин', 'gorin@example.com',
        '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i',
        'username_5', 'gorin',
        'ACTIVE', 'TEACHER', NULL);

-- Вставка данных в таблицу lecture
INSERT INTO lecture (name, lecture_date, zoom_link, presentation_link, lector_id)
VALUES ('React', '2023-10-05 09:00:00', 'https://zoom.us/git1', 'https://slides.com/git1', 3),
       ('Git', '2023-10-10 10:00:00', 'https://zoom.us/git2', 'https://slides.com/git2', 5),
       ('Kafka', '2023-10-15 13:00:00', 'https://zoom.us/kafka', 'https://slides.com/kafka', 5);

-- Вставка данных в таблицу lecture_cohort
INSERT INTO lecture_cohort (lecture_id, cohort_id)
VALUES (1, 1),
       (2, 1),
       (2, 2),
       (3, 2);

-- Вставка данных в таблицу homework
INSERT INTO homework (repository_link, name, topic, description, start_date, completion_deadline, review_duration,
                      lecture_id, author_id)
VALUES ('https://gitlab.com/homework1', 'Домашка по гиту', 'Git',
        'Создайте пул реквест в свой репозиторий и сделайте необходимые мерджи', '2023-10-05 23:59:59',
        '2023-10-12 23:59:59', 24, 2, 5),
       ('https://gitlab.com/homework2', 'Домашка по реакту', 'React',
        'Напишите компоненты, сделайте лендинг', '2023-10-10 23:59:59', '2023-10-17 23:59:59', 48, 1, 3),
       ('https://gitlab.com/homework3', 'Домашка по кафке', 'Kafka',
        'Напишите реализацию AtLeastOnce', '2023-10-10 23:59:59', '2023-10-17 23:59:59', 48, 3, 5);
