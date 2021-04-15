CREATE TABLE high_scores (
	id serial,
    gamer_name VARCHAR(128) NOT NULL,
	score INTEGER NOT NULL,
    created TIMESTAMP NOT NULL,
    game_id INTEGER REFERENCES games,
	PRIMARY KEY(id)
);

create index high_scores_game_id_idx on high_scores (game_id);