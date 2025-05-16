CREATE TABLE users (
                          id BIGSERIAL PRIMARY KEY,
                          username VARCHAR(32) UNIQUE NOT NULL,
                          login VARCHAR(64) UNIQUE NOT NULL,
                          password VARCHAR(255) NOT NULL
);
