BEGIN TRANSACTION;

CREATE TABLE user_account (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE team (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE user_team (
    id BIGSERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    team_id INTEGER NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user_account(id) ON DELETE CASCADE,
    FOREIGN KEY (team_id) REFERENCES team(id) ON DELETE CASCADE
);

CREATE TABLE document (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    word_count INTEGER NOT NULL,
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id INTEGER NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user_account(id) ON DELETE CASCADE
);

COMMIT;