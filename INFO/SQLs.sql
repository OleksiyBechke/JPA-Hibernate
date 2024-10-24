CREATE DATABASE studenthomeworkdb;

CREATE TABLE IF NOT EXISTS student
( id BIGINT PRIMARY KEY AUTO_INCREMENT,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS homework
( id BIGINT PRIMARY KEY AUTO_INCREMENT,
  description TEXT NOT NULL,
  deadline DATE NOT NULL,
  mark INT,
  student_id BIGINT,
  CONSTRAINT fk_student
        FOREIGN KEY (student_id) REFERENCES student (id)
            ON DELETE CASCADE
);
