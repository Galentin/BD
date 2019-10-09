--1.При убийстве персонажа убирать на него ссылку, как на главу дома, если он таковым является.

DROP TRIGGER IF EXISTS delete_head_genera ON killings;

CREATE OR REPLACE FUNCTION delete_head_genera() RETURNS TRIGGER AS $delete_head_genera$
	BEGIN
		UPDATE genera set head = NULL where head = NEW.victim;
		RETURN NULL; 
    END;
$delete_head_genera$ LANGUAGE plpgsql;

CREATE TRIGGER delete_head_genera
AFTER INSERT OR UPDATE ON killings
    FOR EACH ROW EXECUTE PROCEDURE delete_head_genera();

--2.Проверять на корректность данные по появлению персонажа в каком либо сезоне: 
--  если он был убит до начала сезона - не добавлять появление.

DROP TRIGGER IF EXISTS season_check ON appearance_of_characters;
 
CREATE OR REPLACE FUNCTION season_check() RETURNS TRIGGER AS $season_check$
	BEGIN
		IF NEW.season_id > (SELECT season FROM killings WHERE victim = NEW.character_id) THEN
			RETURN OLD;
		ELSE 
			RETURN NEW;
        END IF;
		RETURN NULL;
    END;
$season_check$ LANGUAGE plpgsql;

CREATE TRIGGER season_check
BEFORE INSERT OR UPDATE ON appearance_of_characters
    FOR EACH ROW EXECUTE PROCEDURE season_check();

-- BASIC TASKS
-- 1. Автоматическое заполнение ключевого поля персонажа

DROP TRIGGER IF EXISTS characters_autoID ON characters;

CREATE OR REPLACE FUNCTION characters_autoID() RETURNS TRIGGER AS
$$
BEGIN
    new.id = (SELECT max(id) FROM characters) + 1;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER characters_autoID
BEFORE INSERT ON characters 
	FOR EACH ROW EXECUTE PROCEDURE characters_autoID();
	
-- 2. Контроль целостности данных в подчиненной таблице при удалении/изменении записей в главной таблице

DROP TRIGGER IF EXISTS data_consistency ON nations;

CREATE OR REPLACE FUNCTION data_consistency() RETURNS TRIGGER AS
$$
BEGIN
	IF TG_OP = 'UPDATE' THEN
		UPDATE characteristics_nations SET nation_id = NEW.id WHERE nation_id = OLD.id;
		RETURN NEW;
	END IF;
	IF TG_OP = 'DELETE' THEN
		DELETE FROM characteristics_nations WHERE nation_id = OLD.id;
		RETURN OLD;
	END IF;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER data_consistency
BEFORE UPDATE OR DELETE ON nations
	FOR EACH ROW EXECUTE PROCEDURE data_consistency();