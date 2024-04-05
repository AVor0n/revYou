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
    user_id     SERIAL PRIMARY KEY,
    username    VARCHAR(50)  NOT NULL UNIQUE,
    name        VARCHAR(50),
    surname     VARCHAR(50),
    email       VARCHAR(100) NOT NULL UNIQUE,
    password    VARCHAR(100) NOT NULL,
    gitlab_link VARCHAR(1000) UNIQUE,
    mm_link     VARCHAR(1000) UNIQUE,
    status      VARCHAR(20)  NOT NULL,
    role        VARCHAR(10)  NOT NULL,
    cohort_id   INTEGER,
    FOREIGN KEY (cohort_id) REFERENCES cohort (cohort_id)
);


CREATE TABLE IF NOT EXISTS lecture
(
    lecture_id        SERIAL PRIMARY KEY,
    title             VARCHAR(100) NOT NULL,
    lecture_date      TIMESTAMP,
    zoom_link         VARCHAR(1000),
    presentation_link VARCHAR(1000),
    teacher_id        INTEGER,
    FOREIGN KEY (teacher_id) REFERENCES user_account (user_id)
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
    repository_link     VARCHAR(1000),
    name                VARCHAR(100) NOT NULL,
    topic               VARCHAR(100) NOT NULL,
    description         VARCHAR(500),
    start_date          TIMESTAMP    NOT NULL DEFAULT NOW(),
    completion_deadline TIMESTAMP    NOT NULL,
    review_duration     INTEGER      NOT NULL,
    lecture_id          INTEGER,
    FOREIGN KEY (lecture_id) REFERENCES lecture (lecture_id)
);

-- Вставка данных в таблицу cohort
INSERT INTO cohort (cohort_number, education_start_date, education_end_date, mm_channel_link, study_direction)
VALUES (1, '2023-09-01', '2024-06-30', 'https://mattermost.com/frontend1', 'FRONT'),
       (2, '2023-09-15', '2024-07-15', 'https://mattermost.com/backend1', 'BACK');

-- Вставка данных в таблицу user_account
INSERT INTO user_account (username, name, surname, email, password, gitlab_link, mm_link, status, role, cohort_id)
VALUES ('username_1', 'Иван', 'Иванов', 'ivanov@example.com', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i',
        'https://gitlab.com/ivanov', 'https://mattermost.com/ivanov',
        'ACTIVE', 'STUDENT', 1),
       ('username_2', 'Мария', 'Петрова', 'petrova@example.com', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i',
        'https://gitlab.com/petrova', 'https://mattermost.com/petrova',
        'DISABLED', 'STUDENT', 2),
       ('username_3', 'Антон', 'Сидоров', 'sidorov@example.com', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i',
        'https://gitlab.com/sidorov', 'https://mattermost.com/sidorov',
        'ACTIVE', 'TEACHER', NULL),
       ('username_4', 'Елена', 'Козлова', 'kozlova@example.com', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i',
        'https://gitlab.com/kozlova', 'https://mattermost.com/kozlova',
        'ACTIVE', 'STUDENT', 1),
       ('username_5', 'Геннадий', 'Горин', 'gorin@example.com', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i',
        'https://gitlab.com/gorin', 'https://mattermost.com/gorin',
        'ACTIVE', 'TEACHER', 2);

-- Вставка данных в таблицу lecture
INSERT INTO lecture (title, lecture_date, zoom_link, presentation_link, teacher_id)
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
INSERT INTO homework (repository_link, name, topic, description, start_date, completion_deadline, review_duration, lecture_id)
VALUES ('https://gitlab.com/homework1', 'Домашка по гиту', 'Git',
        'Создайте пул реквест в свой репозиторий и сделайте необходимые мерджи', '2023-10-05 23:59:59', '2023-10-12 23:59:59', 24, 2),
       ('https://gitlab.com/homework2', 'Домашка по реакту', 'React',
        'Напишите компоненты, сделайте лендинг', '2023-10-10 23:59:59', '2023-10-17 23:59:59', 48, 1),
       ('https://gitlab.com/homework3', 'Домашка по кафке', 'Kafka',
        'Напишите реализацию AtLeastOnce', '2023-10-10 23:59:59', '2023-10-17 23:59:59', 48, 3);
