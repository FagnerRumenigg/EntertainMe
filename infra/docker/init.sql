CREATE entertainme-dabase;

CREATE TABLE users (
 id SERIAL  PRIMARY KEY,
 nome varchar(100) not null,
 email varchar(100) NOT NULL,
 password varchar(100) NOT NULL,
 role varchar(15) NOT NULL
);

CREATE TABLE anime (
    id_anime SERIAL PRIMARY KEY,
    jikan_id INTEGER NOT NULL,
    titulo VARCHAR(100) NOT NULL,
    fonte_origem VARCHAR(100) NOT NULL,
    situacao_atual VARCHAR(50) NOT NULL,
    sinopse VARCHAR(5000) NOT NULL,
    quantidade_episodios INTEGER ,
    ano_lancamento INTEGER,
    demografias TEXT[],
    estudios TEXT[],
    generos TEXT[]
);
