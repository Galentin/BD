START TRANSACTION;

DROP TABLE IF EXISTS
	battle,
	catalog_coalitions,
	coalitions_composition,
	chronology_government
;

CREATE TABLE IF NOT EXISTS battle (
	id SERIAL NOT NULL PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	year INT NULL,
	place_id INT NULL,
	participant_1 INT NOT NULL,
	participant_2 INT NOT NULL,
	winner INT NOT NULL
);

CREATE TABLE IF NOT EXISTS coalitions_composition (
	coalitions_id INT NOT NULL,
	genera_id INT NOT NULL
);

CREATE TABLE IF NOT EXISTS catalog_coalitions (
	id SERIAL NOT NULL PRIMARY KEY,
	name VARCHAR(255) NOT NULL 
);

CREATE TABLE IF NOT EXISTS chronology_government (
	id SERIAL NOT NULL PRIMARY KEY,
	chapter INT NOT NULL,
	genera_id INT NOT NULL,
	beginning_government INT NULL,
	end_government INT NULL
);

ALTER TABLE coalitions_composition
	ADD FOREIGN KEY (coalitions_id) REFERENCES catalog_coalitions (id),
	ADD FOREIGN KEY (genera_id) REFERENCES genera (id)
;	

ALTER TABLE battle
	ADD FOREIGN KEY (place_id) REFERENCES cities_and_castles (id),
	ADD FOREIGN KEY (participant_1) REFERENCES catalog_coalitions (id),
	ADD FOREIGN KEY (participant_2) REFERENCES catalog_coalitions (id),
	ADD FOREIGN KEY (winner) REFERENCES catalog_coalitions (id)
;	

ALTER TABLE chronology_government
	ADD FOREIGN KEY (chapter) REFERENCES characters (id),
	ADD FOREIGN KEY (genera_id) REFERENCES genera (id)
;

COMMIT;