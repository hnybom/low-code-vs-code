CREATE TABLE games (
	id SERIAL,
	version INTEGER,
	name VARCHAR(1024) NOT NULL,
    genre VARCHAR(128) NOT NULL,
	platform VARCHAR(64) NOT NULL,
	publisher VARCHAR(128),
	rating VARCHAR(2),
	release_year INTEGER NOT NULL,
	review_score INTEGER NOT NULL,
	PRIMARY KEY(id)
)