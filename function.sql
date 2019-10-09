-- 1. Для каждого дома вывести максимальную серию побед и поражений с учетом коалиций.
CREATE OR REPLACE FUNCTION series_vict_defeats() RETURNS TABLE (genera_id integer, genera_name varchar, victories integer, defeats integer) AS 
$$
DECLARE
	b record;
	d record;
	total_vic integer;
	total_def integer;
	local_vic integer;
	local_def integer;
BEGIN
FOR b IN
	SELECT id, name 
	FROM genera
LOOP
	total_vic := 0;
	total_def := 0;
	local_vic := 0;
	local_def := 0;
	FOR d IN
		SELECT year, list_coalitions.coalitions_id, winner
		FROM battle
		JOIN (SELECT coalitions_id
			  FROM coalitions_composition
			  WHERE coalitions_composition.genera_id = b.id) AS list_coalitions
		ON participant_2 = list_coalitions.coalitions_id OR participant_1 = list_coalitions.coalitions_id
		ORDER BY year
	LOOP
		IF d.coalitions_id = d.winner THEN 
			local_vic := local_vic + 1;
			local_def := 0; 
		ELSE
			local_def := local_def + 1;
			local_vic := 0;
		END IF;
		IF total_vic < local_vic THEN 
			total_vic := local_vic;
		END IF;
		IF total_def < local_def THEN 
			total_def := local_def;
		END IF;
	END LOOP;
	genera_id := b.id;
	genera_name := b.name;
	victories := total_vic;
	defeats := total_def;
	RETURN NEXT;
END LOOP;
RETURN;
END;
$$ LANGUAGE plpgsql;

--2. Вывести дома, которые хотя бы раз участвовали в сражениях в коалиции с другим домом и против него.
CREATE OR REPLACE FUNCTION house_traitors() RETURNS TABLE (gen_id integer, genera_name varchar) AS 
$$
DECLARE
	b record;
	allies_genera integer[];
	opponents_genera integer[];
BEGIN
FOR b IN
	SELECT id, name 
	FROM genera
LOOP
	SELECT array_agg(genera_id) into allies_genera
	FROM coalitions_composition
	JOIN (SELECT coalitions_id
		FROM coalitions_composition 
		WHERE coalitions_composition.genera_id = b.id) AS allies_coalitions
	ON allies_coalitions.coalitions_id = coalitions_composition.coalitions_id;
	
	SELECT array_agg(genera_id) into opponents_genera
	FROM coalitions_composition
	JOIN (SELECT participant_2 
		FROM battle
		JOIN (SELECT coalitions_id
			FROM coalitions_composition 
			WHERE coalitions_composition.genera_id = b.id) AS allies_coalitions
		ON participant_1 = allies_coalitions.coalitions_id
		UNION ALL 
		SELECT participant_1
		FROM battle
		JOIN (SELECT coalitions_id
			FROM coalitions_composition 
			WHERE coalitions_composition.genera_id = b.id) AS allies_coalitions
		ON participant_2 = allies_coalitions.coalitions_id) AS opponents_coalitions
	ON opponents_coalitions.participant_2 = coalitions_composition.coalitions_id;
	
	IF opponents_genera && allies_genera THEN
		gen_id := b.id;
		genera_name := b.name;
		RETURN NEXT;
	END IF;
END LOOP;
RETURN;
END;
$$ LANGUAGE plpgsql;