CREATE TABLE IF NOT EXISTS client (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(200) NOT NULL CHECK (CHAR_LENGTH(name) >= 3 AND CHAR_LENGTH(name) <= 200)
);

CREATE TABLE IF NOT EXISTS planet (
    id VARCHAR(10) PRIMARY KEY CHECK (id ~ '^[A-Z0-9]+$'),
    name VARCHAR(500) NOT NULL CHECK (CHAR_LENGTH(name) >= 1 AND CHAR_LENGTH(name) <= 500)
);

CREATE TABLE IF NOT EXISTS ticket (
    id SERIAL PRIMARY KEY,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    client_id INTEGER REFERENCES client(id),
    from_planet_id VARCHAR(10) REFERENCES planet(id),
    to_planet_id VARCHAR(10) REFERENCES planet(id)
);
