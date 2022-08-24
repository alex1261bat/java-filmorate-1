CREATE TABLE IF NOT EXISTS MPA_ratings (
    id    BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name  VARCHAR(16) NOT NULL
);

CREATE TABLE IF NOT EXISTS films (
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    description  TEXT NOT NULL,
    release_date  DATE NOT NULL,
    duration     INTEGER NOT NULL,
    MPA_id       BIGINT REFERENCES MPA_ratings(id) ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS genres (
    id    BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name  VARCHAR(64) NOT NULL
);

CREATE TABLE IF NOT EXISTS films_genres (
    genre_id  BIGINT REFERENCES genres (id) ON DELETE CASCADE,
    film_id   BIGINT REFERENCES films (id) ON DELETE CASCADE,
    UNIQUE (genre_id, film_id)
);

CREATE TABLE IF NOT EXISTS users (
    id        BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    email     VARCHAR(64) UNIQUE NOT NULL,
    login     VARCHAR(32) UNIQUE NOT NULL,
    name      VARCHAR(255) NOT NULL,
    birthday  DATE
);

CREATE TABLE IF NOT EXISTS friendships (
    id         BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    user_id    BIGINT REFERENCES users (id) ON DELETE CASCADE,
    friend_id  BIGINT REFERENCES users (id) ON DELETE CASCADE,
    UNIQUE (user_id, friend_id)
);

CREATE TABLE IF NOT EXISTS likes_list (
    id         BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    user_id    BIGINT REFERENCES users (id) ON DELETE CASCADE,
    film_id    BIGINT REFERENCES films (id) ON DELETE CASCADE,
    UNIQUE (user_id, film_id)
);

CREATE TABLE IF NOT EXISTS reviews (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    content VARCHAR(255) NOT NULL,
    is_positive BOOLEAN NOT NULL,
    user_id BIGINT NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    film_id BIGINT NOT NULL REFERENCES films (id) ON DELETE CASCADE,
    UNIQUE (user_id, film_id)
);

CREATE TABLE IF NOT EXISTS review_likes (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    review_id BIGINT NOT NULL REFERENCES reviews (id) ON DELETE CASCADE,
    user_id BIGINT NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    UNIQUE (review_id, user_id)
);

CREATE TABLE IF NOT EXISTS review_dislikes (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    review_id BIGINT NOT NULL REFERENCES reviews (id) ON DELETE CASCADE,
    user_id BIGINT NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    UNIQUE (review_id, user_id)
);
