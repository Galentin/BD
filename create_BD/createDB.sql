-- Database: Game_of_Thrones

-- DROP DATABASE "Game_of_Thrones";

START TRANSACTION;

DROP TABLE IF EXISTS
	characters,
	actors,
	cities_and_castles,
	lands,
	religions,
	catalog_of_characteristics,
	characteristics_nations,
	nations,
	genera,
	killings,
	appearance_of_characters,
	catalog_of_seasons
CASCADE;

CREATE TABLE IF NOT EXISTS characters (
	id SERIAL NOT NULL PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	genus_id INT, 
	nation_id INT,
	religion_id INT,
	homeland_id INT, 
	actor_id INT
);

CREATE TABLE IF NOT EXISTS actors (
	id SERIAL NOT NULL PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	surname VARCHAR(255) NOT NULL,
	gender VARCHAR(255) NOT NULL,
	DOB date NOT NULL,
	role_id INT NOT NULL
);

CREATE TABLE IF NOT EXISTS cities_and_castles (
	id SERIAL NOT NULL PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	type VARCHAR(255) NOT NULL,
	land_id INT NOT NULL,
	religion_id INT
);

CREATE TABLE IF NOT EXISTS lands (
	id SERIAL NOT NULL PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	continent VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS religions (
	id SERIAL NOT NULL PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	type VARCHAR(255),
	clergy VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS catalog_of_characteristics (
	id SERIAL NOT NULL PRIMARY KEY,
	characteristic VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS characteristics_nations (
	nation_id INT NOT NULL,
	characteristic_id INT NOT NULL,
	value VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS nations (
	id SERIAL NOT NULL PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	status VARCHAR(255) NOT NULL,
	language VARCHAR(255),
	religion_id INT,
	spread_id INT
);

CREATE TABLE IF NOT EXISTS genera (
	id SERIAL NOT NULL PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	coat_of_arms TEXT NOT NULL, 
	motto TEXT,
	head INT,
	heir INT
);

CREATE TABLE IF NOT EXISTS killings (
	victim INT NOT NULL,
	killer INT,
	season INT NOT NULL
);

CREATE TABLE IF NOT EXISTS appearance_of_characters (
	character_id INT NOT NULL,
	season_id INT NOT NULL
);

CREATE TABLE IF NOT EXISTS catalog_of_seasons (
	id SERIAL NOT NULL PRIMARY KEY,
	season INT NOT NULL
);

ALTER TABLE characters
	ADD FOREIGN KEY (genus_id) REFERENCES genera (id)
	DEFERRABLE
  	INITIALLY DEFERRED,
	ADD FOREIGN KEY (nation_id) REFERENCES nations (id),
	ADD FOREIGN KEY (religion_id) REFERENCES religions (id),
	ADD FOREIGN KEY (homeland_id) REFERENCES cities_and_castles (id),
	ADD FOREIGN KEY (actor_id) REFERENCES actors (id)
	DEFERRABLE
  	INITIALLY DEFERRED
;	

ALTER TABLE actors
	ADD FOREIGN KEY (role_id) REFERENCES characters (id)
;	

ALTER TABLE cities_and_castles
	ADD FOREIGN KEY (land_id) REFERENCES lands (id),
	ADD FOREIGN KEY (religion_id) REFERENCES religions (id)
;	

ALTER TABLE characteristics_nations
	ADD FOREIGN KEY (nation_id) REFERENCES nations (id),
	ADD FOREIGN KEY (characteristic_id) REFERENCES catalog_of_characteristics (id)
;	

ALTER TABLE nations
	ADD FOREIGN KEY (religion_id) REFERENCES religions (id),
	ADD FOREIGN KEY (spread_id) REFERENCES lands (id)
;	

ALTER TABLE genera
	ADD FOREIGN KEY (head) REFERENCES characters (id),
	ADD FOREIGN KEY (heir) REFERENCES characters (id)
;	

ALTER TABLE killings
	ADD FOREIGN KEY (victim) REFERENCES characters (id),
	ADD FOREIGN KEY (killer) REFERENCES characters (id),
	ADD FOREIGN KEY (season) REFERENCES catalog_of_seasons (id)
;	

ALTER TABLE appearance_of_characters
	ADD FOREIGN KEY (character_id) REFERENCES characters (id),
	ADD FOREIGN KEY (season_id) REFERENCES catalog_of_seasons (id)
;	

COMMIT;