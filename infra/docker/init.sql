CREATE TABLE users (
     id         SERIAL          PRIMARY KEY,
     name       VARCHAR(100)    NOT NULL,
     email      VARCHAR(100)    NOT NULL,
     password   VARCHAR(100)    NOT NULL,
     role       VARCHAR(15)     NOT NULL
);

CREATE TABLE anime (
    id_anime        SERIAL          PRIMARY KEY,
    jikan_id        INTEGER         NOT NULL,
    title           VARCHAR(100)    NOT NULL,
    source          VARCHAR(100)    NOT NULL,
    status          VARCHAR(50)     NOT NULL,
    synopsys        VARCHAR(5000)   NOT NULL,
    episodes        INTEGER ,
    release_year    INTEGER,
    demographics    TEXT[],
    studios         TEXT[],
    genres          TEXT[]
);