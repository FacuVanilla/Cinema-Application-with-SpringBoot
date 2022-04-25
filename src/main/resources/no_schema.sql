DROP TABLE IF EXISTS movies;

CREATE TABLE movies (
   id   BIGSERIAL PRIMARY KEY,
   title VARCHAR(255) NOT NULL,
   author VARCHAR(255) NOT NULL,
   category VARCHAR(255) NOT NULL,
   image_name VARCHAR(255)
);