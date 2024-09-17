CREATE TABLE users (
    id_user        SERIAL       PRIMARY KEY,
    name           VARCHAR(100) NOT NULL,
    email          VARCHAR(100) NOT NULL,
    password       VARCHAR(256) NOT NULL,
    role           VARCHAR(15)  NOT NULL,
    register_date  TIMESTAMP	not null,
    login_date	   TIMESTAMP,
    CONSTRAINT unique_email UNIQUE (email)
);

CREATE TABLE studio (
    id_studio      SERIAL       PRIMARY KEY,
    name           VARCHAR(50) NOT NULL
);

CREATE TABLE demographic (
    id_demographic SERIAL       PRIMARY KEY,
    name           VARCHAR(50) NOT NULL
);

CREATE TABLE genre (
    id_genre       SERIAL       PRIMARY KEY,
    name           VARCHAR(50) NOT NULL
);

CREATE TABLE theme (
    id_theme       SERIAL       PRIMARY KEY,
    name           VARCHAR(50) NOT NULL
);

CREATE TABLE anime (
    id_anime       SERIAL       PRIMARY KEY,
    id_jikan       INTEGER      NOT NULL,
    title          VARCHAR(200) NOT NULL,
    source         VARCHAR(100) NOT NULL,
    status         VARCHAR(50)  NOT NULL,
    age_rating     VARCHAR(50),
    synopsys       VARCHAR(5000),
    episodes       INTEGER,
    release_year   INTEGER
);

CREATE TABLE custom_anime_user (
    id_anime       INTEGER      NOT NULL,
    id_user        INTEGER      NOT NULL,
    id_demographic INTEGER      NOT NULL,
    id_studio      INTEGER      NOT NULL,
    id_genre       INTEGER      NOT NULL,
    custom_title   VARCHAR(200) NOT NULL,
    custom_synopsys VARCHAR(5000),
    PRIMARY KEY (id_anime, id_user),
    FOREIGN KEY (id_demographic) REFERENCES demographic(id_demographic),
    FOREIGN KEY (id_studio)      REFERENCES studio(id_studio),
    FOREIGN KEY (id_genre)       REFERENCES genre(id_genre)
);

CREATE TABLE anime_demographic (
    id_anime       INTEGER      NOT NULL,
    id_demographic INTEGER      NOT NULL,
    PRIMARY KEY (id_anime, id_demographic),
    FOREIGN KEY (id_anime) REFERENCES anime(id_anime),
    FOREIGN KEY (id_demographic) REFERENCES demographic(id_demographic)
);

CREATE TABLE anime_genre (
    id_anime       INTEGER      NOT NULL,
    id_genre       INTEGER      NOT NULL,
    PRIMARY KEY (id_anime, id_genre),
    FOREIGN KEY (id_anime) REFERENCES anime(id_anime),
    FOREIGN KEY (id_genre) REFERENCES genre(id_genre)
);

CREATE TABLE anime_studio (
    id_anime       INTEGER      NOT NULL,
    id_studio      INTEGER      NOT NULL,
    PRIMARY KEY (id_anime, id_studio),
    FOREIGN KEY (id_anime) REFERENCES anime(id_anime),
    FOREIGN KEY (id_studio) REFERENCES studio(id_studio)
);

CREATE TABLE anime_theme (
    id_anime       INTEGER      NOT NULL,
    id_theme       INTEGER      NOT NULL,
    PRIMARY KEY (id_anime, id_theme),
    FOREIGN KEY (id_anime) REFERENCES anime(id_anime),
    FOREIGN KEY (id_theme) REFERENCES theme(id_theme)
);

CREATE TABLE anime_user_interactions (
    id_anime        INTEGER      NOT NULL,
    id_user         INTEGER      NOT NULL,
    rating_score    SMALLINT     NOT NULL, -- 1 don't liked / 2 liked / 3 loved
    no_interest     BOOLEAN,
    watched         BOOLEAN,
    interaction_date TIMESTAMP,
    PRIMARY KEY (id_anime, id_user),
    FOREIGN KEY (id_anime) REFERENCES anime(id_anime),
    FOREIGN KEY (id_user) REFERENCES users(id_user)
);

CREATE TYPE estate AS ENUM (
    'AC', -- Acre
    'AL', -- Alagoas
    'AP', -- Amapá
    'AM', -- Amazonas
    'BA', -- Bahia
    'CE', -- Ceará
    'DF', -- Distrito Federal
    'ES', -- Espírito Santo
    'GO', -- Goiás
    'MA', -- Maranhão
    'MT', -- Mato Grosso
    'MS', -- Mato Grosso do Sul
    'MG', -- Minas Gerais
    'PA', -- Pará
    'PB', -- Paraíba
    'PR', -- Paraná
    'PE', -- Pernambuco
    'PI', -- Piauí
    'RJ', -- Rio de Janeiro
    'RN', -- Rio Grande do Norte
    'RS', -- Rio Grande do Sul
    'RO', -- Rondônia
    'RR', -- Roraima
    'SC', -- Santa Catarina
    'SP', -- São Paulo
    'SE', -- Sergipe
    'TO'  -- Tocantins
);

CREATE TABLE address (
    id_address     SERIAL       PRIMARY KEY,
    id_user        INTEGER      NOT NULL,
    street         VARCHAR(100) NOT NULL,
    city           VARCHAR(100) NOT NULL,
    estate         estate       NOT NULL,
    country        VARCHAR(50)  NOT NULL DEFAULT 'Brasil',
    cep            VARCHAR(20)  NOT NULL,
    FOREIGN KEY (id_user) REFERENCES users(id_user)
);

CREATE TABLE user_preference_demographic (
    id_user        INTEGER      NOT NULL,
    id_demographic INTEGER      NOT NULL,
    PRIMARY KEY (id_user, id_demographic),
    FOREIGN KEY (id_user) REFERENCES users(id_user),
    FOREIGN KEY (id_demographic) REFERENCES demographic(id_demographic)
);

CREATE TABLE user_preference_genre (
    id_user        INTEGER      NOT NULL,
    id_genre       INTEGER      NOT NULL,
    PRIMARY KEY (id_user, id_genre),
    FOREIGN KEY (id_user) REFERENCES users(id_user),
    FOREIGN KEY (id_genre) REFERENCES genre(id_genre)
);

CREATE TABLE user_preference_studio (
    id_user        INTEGER      NOT NULL,
    id_studio      INTEGER      NOT NULL,
    PRIMARY KEY (id_user, id_studio),
    FOREIGN KEY (id_user) REFERENCES users(id_user),
    FOREIGN KEY (id_studio) REFERENCES studio(id_studio)
);

CREATE TABLE user_preference_theme (
    id_user        INTEGER      NOT NULL,
    id_theme       INTEGER      NOT NULL,
    PRIMARY KEY (id_user, id_theme),
    FOREIGN KEY (id_user) REFERENCES users(id_user),
    FOREIGN KEY (id_theme) REFERENCES theme(id_theme)
);

CREATE TABLE total_rating_users (
    id_anime       INTEGER      NOT NULL,
    total_rating   DECIMAL      NOT NULL DEFAULT 0,
    total_votes    INTEGER      NOT NULL DEFAULT 0,
    last_updated   TIMESTAMP,
    PRIMARY KEY (id_anime),
    FOREIGN KEY (id_anime) REFERENCES anime(id_anime)
);

CREATE TABLE anime_images (
    id_anime        INTEGER      NOT NULL,
    image_url       VARCHAR,
    small_image_url VARCHAR,
    large_image_url VARCHAR,
    PRIMARY KEY (id_anime),
    FOREIGN KEY (id_anime) REFERENCES anime(id_anime)
);

CREATE TABLE anime_streaming (
    id_anime_streaming 	SERIAL     PRIMARY KEY,
    id_anime           	INTEGER    NOT NULL,
    name              	VARCHAR,
    url               	VARCHAR,
    FOREIGN KEY (id_anime) REFERENCES anime(id_anime)
);

CREATE INDEX idx_anime_title ON anime(title);
CREATE INDEX idx_studio_title ON studio(name);
CREATE INDEX idx_demographic_title ON demographic(name);
CREATE INDEX idx_genre_title ON genre(name);
CREATE INDEX idx_email ON users(email);