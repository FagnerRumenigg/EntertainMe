CREATE TABLE users (
     id_user         SERIAL          PRIMARY KEY,
     name       VARCHAR(100)    NOT NULL,
     email      VARCHAR(100)    NOT NULL,
     password   VARCHAR(100)    NOT NULL,
     role       VARCHAR(15)     NOT NULL
);

CREATE TABLE anime (
    id_anime        SERIAL          PRIMARY KEY,
    jikan_id        INTEGER         NOT NULL,
    title           VARCHAR(200)    NOT NULL,
    source          VARCHAR(100)    NOT NULL,
    status          VARCHAR(50)     NOT NULL,
    synopsys        VARCHAR(5000),
    episodes        INTEGER,
    release_year    INTEGER,
    demographics    TEXT[],
    studios         TEXT[],
    genres          TEXT[]
);

CREATE TABLE address (
    id_address  SERIAL          PRIMARY KEY,
    id_user     INT             NOT NULL,
    street      VARCHAR(100)    NOT NULL,
    city        VARCHAR(100)    NOT NULL,
    estate      VARCHAR(50)     NOT NULL,
    country     VARCHAR(50)     NOT NULL,
    cep         VARCHAR(20)     NOT NULL,

    FOREIGN KEY (id_user) REFERENCES users(id_user)
);