/*
Вывести персонажей, которые появлялись не во всех сехонах; 
список отсортировать по номеру сезона, когда персонаж появился впервые
*/
SELECT MIN(season_id) AS season_id, character_id
		FROM appearance_of_characters 
		GROUP BY character_id 
		HAVING COUNT(character_id) < 8
		ORDER BY season_id;

/* 
Вывести дома, у которых было 
не менее 5 глав за все время учета 
*/
SELECT  genera.id, genera.name
		FROM (
				SELECT genera_id
						FROM chronology_government
						GROUP BY genera_id
						HAVING COUNT(genera_id) >= 5
		)  AS select_house, genera
		WHERE select_house.genera_id = genera.id;
/*
Вывести дома, которые никогда не проигрывали в сражениях 
(в т.ч. в составе коалиций)
*/
SELECT genera.id, genera.name
		FROM genera
		LEFT JOIN (SELECT coalitions_composition.genera_id 
						FROM (
								SELECT participant_2 AS coalitions_id 
										FROM battle
										WHERE participant_2 != winner
										UNION 
								SELECT participant_1 AS coalitions_id
										FROM battle
										WHERE participant_1 != winner			
						) AS losers_coalitions, coalitions_composition
						WHERE losers_coalitions.coalitions_id = coalitions_composition.coalitions_id
						GROUP BY coalitions_composition.genera_id) AS list_genera		
			ON genera.id = list_genera.genera_id 
			WHERE list_genera.genera_id  is NULL
		