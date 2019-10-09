import com.github.javafaker.Faker;
import java.sql.*;
import java.util.*;

class Generator {

    private Connection conn = null;

    void connect(){
        String jdbcUrl = "jdbc:postgresql://localhost:5432/Game_of_Thrones";
        String username = "admin";
        String password = "admin";
        try {
            conn = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Database connection");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void disconnect() {
        try {
            if (conn != null) {
                conn.close();
                System.out.println("Database disconnection");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int randInRange(int min, int max){
        Random rand = new Random();
        return min + rand.nextInt(max - min + 1);
    }

    private String randString(int length){
        String vowels = "аеиоуэюя";
        String consonants = "бвгджзклмнпрстфхцчшщ";
        Random rand = new Random();
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            if((length - i)%2 == 0) text[i] = consonants.charAt(rand.nextInt(consonants.length()));
            else text[i] = vowels.charAt(rand.nextInt(vowels.length()));
        }
        return new String(text);
    }

    private java.sql.Date randomDate(int min, int max) {
        int y = randInRange(min, max);
        int m = randInRange(0, 12);
        int d = randInRange(1, 31);
        Calendar cr = new GregorianCalendar();
        cr.set(y, m, d);
        return new java.sql.Date(cr.getTime().getTime());
    }

    private int insertTableCharacters(String name, int genus_id, int nation_id, int religion_id, int homeland_id) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("insert into characters (name, genus_id, nation_id, religion_id, homeland_id) values (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, name);
        ps.setInt(2, genus_id);
        ps.setInt(3, nation_id);;
        ps.setInt(4, religion_id);
        ps.setInt(5, homeland_id);
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        int id = rs.getInt(1);
        ps.close();
        return id;
    }

    private void updateTableCharacters(int id, int actor_id) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("update characters set actor_id = ? where id = ?");
        ps.setInt(1, actor_id);
        ps.setInt(2, id);
        ps.executeUpdate();
        ps.close();
    }

    private void insertTableAppearance_Of_Characters(int character_id, int season_id) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("insert into appearance_of_characters (character_id, season_id) values (?, ?)");
        ps.setInt(1, character_id);
        ps.setInt(2, season_id);
        ps.executeUpdate();
        ps.close();
    }

    private int insertTableGenera(String name, String coat_of_arms, String motto) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("insert into genera (name, coat_of_arms, motto) values (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, name);
        ps.setString(2, coat_of_arms);
        ps.setString(3, motto);
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        int id = rs.getInt(1);
        ps.close();
        return id;
    }

    private void updateTableGenera_1(int id, int head) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("update genera set head = ? where id = ?");
        ps.setInt(1, head);
        ps.setInt(2, id);
        ps.executeUpdate();
        ps.close();
    }

    private void updateTableGenera_2(int id, int heir) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("update genera set heir = ? where id = ?");
        ps.setInt(1, heir);
        ps.setInt(2, id);
        ps.executeUpdate();
        ps.close();
    }


    private int insertTableActors(String name, String surname, String gender, java.sql.Date DOB, int role_id) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("insert into actors (name, surname, gender, DOB, role_id) values (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, name);
        ps.setString(2, surname);
        ps.setString(3, gender);
        ps.setDate(4, DOB);
        ps.setInt(5, role_id);
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        int id = rs.getInt(1);
        ps.close();
        return id;
    }

    private int insertTableNations(String name, String status, String language, int religion_id, int spread_id) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("insert into nations (name, status, language, religion_id, spread_id) values (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, name);
        ps.setString(2, status);
        ps.setString(3, language);
        ps.setInt(4, religion_id);
        ps.setInt(5, spread_id);
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        int id = rs.getInt(1);
        ps.close();
        return id;
    }

    private void insertTableCharacteristics_Nations(int nation_id, int characteristic_id, String value) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("insert into characteristics_nations (nation_id, characteristic_id, value) values (?, ?, ?)");
        ps.setInt(1, nation_id);
        ps.setInt(2, characteristic_id);
        ps.setString(3, value);
        ps.executeUpdate();
        ps.close();
    }

    private void insertTableReligions(String name, String type, String clergy) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("insert into religions (name, type, clergy) values (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, name);
        ps.setString(2, type);
        ps.setString(3, clergy);
        ps.executeUpdate();
        ps.close();
    }

    private void insertTableLands(String name, String continent) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("insert into lands (name, continent) values (?, ?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, name);
        ps.setString(2, continent);
        ps.executeUpdate();
        ps.close();
    }

    private void insertTableCities_And_Castles(String name, String type, int land_id, int religion_id) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("insert into cities_and_castles (name, type, land_id, religion_id) values (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, name);
        ps.setString(2, type);
        ps.setInt(3, land_id);
        ps.setInt(4, religion_id);
        ps.executeUpdate();
        ps.close();
    }

    private void insertTableKillings(int victim, int killer, int season) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("insert into killings (victim, killer, season) values (?, ?, ?)");
        ps.setInt(1, victim);
        ps.setInt(2, killer);
        ps.setInt(3, season);
        ps.executeUpdate();
        ps.close();
    }

    private void insertTableCatalog_Coalitions(String name) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("insert into catalog_coalitions (name) values (?)");
        ps.setString(1, name);
        ps.executeUpdate();
        ps.close();
    }

    private void insertTableCoalitions_Composition(int coalitions_id, int genera_id) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("insert into coalitions_composition (coalitions_id, genera_id) values (?, ?)");
        ps.setInt(1, coalitions_id);
        ps.setInt(2, genera_id);
        ps.executeUpdate();
        ps.close();
    }

    private void insertTableChronology_Government(int chapter, int genera_id, int beginning_government, int end_government) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("insert into chronology_government (chapter, genera_id, beginning_government, end_government) values (?, ?, ?, ?)");
        ps.setInt(1, chapter);
        ps.setInt(2, genera_id);
        ps.setInt(3, beginning_government);
        ps.setInt(4, end_government);
        ps.executeUpdate();
        ps.close();
    }
    private void insertTableChronology_Government_head(int chapter, int genera_id, int beginning_government) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("insert into chronology_government (chapter, genera_id, beginning_government) values (?, ?, ?)");
        ps.setInt(1, chapter);
        ps.setInt(2, genera_id);
        ps.setInt(3, beginning_government);
        ps.executeUpdate();
        ps.close();
    }

    private void insertTableBattle(String name, int year, int place_id, int participant_1, int participant_2, int winner) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("insert into battle (name, year, place_id, participant_1, participant_2, winner) values (?, ?, ?, ?, ?, ?)");
        ps.setString(1, name);
        ps.setInt(2, year);
        ps.setInt(3, place_id);
        ps.setInt(4, participant_1);
        ps.setInt(5, participant_2);
        ps.setInt(6, winner);
        ps.executeUpdate();
        ps.close();
    }

    private int[] selectFromTables(String select) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(select, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = ps.executeQuery();
        List<Integer> mas = new ArrayList<>();
        int a;
        while (rs.next()) {
            a = rs.getInt("id");
            mas.add(a);
        }
        return mas.stream().mapToInt(i -> i).toArray();
    }

    private String selectGenera(String select) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(select, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getString(1);
    }

    int addToCharacters(int amount, boolean actors, int genera_id) throws SQLException {
        conn.setAutoCommit(false);
        int character_id = 0;
        try {
            Random rand = new Random();
            int[] genera = selectFromTables("SELECT id FROM genera");
            int[] nations = selectFromTables("SELECT id FROM nations");
            int[] religions = selectFromTables("SELECT id FROM religions");
            int[] cities_and_castles = selectFromTables("SELECT id FROM cities_and_castles");
            int nation_id, religion_id, homeland_id, actor_id;
            for (int i = 0; i < amount; i++){
                String name = randString(randInRange(3, 7));
                name = name.substring(0, 1).toUpperCase() + name.substring(1);
                int genus_id;
                if(genera_id != 0) genus_id = genera_id;
                else genus_id = genera[rand.nextInt(genera.length)];
                String surname = selectGenera("SELECT name FROM genera WHERE id =" + genus_id);
                name = name + " " + surname.substring(0, surname.length() - 1);
                nation_id = nations[rand.nextInt(nations.length)];
                religion_id = religions[rand.nextInt(religions.length)];
                homeland_id = cities_and_castles[rand.nextInt(cities_and_castles.length)];
                character_id = insertTableCharacters(name, genus_id, nation_id, religion_id, homeland_id);
                if(actors){
                    actor_id = addToActors(character_id);
                    updateTableCharacters(character_id, actor_id);
                }else if(rand.nextInt(2) == 1){
                    actor_id = addToActors(character_id);
                    updateTableCharacters(character_id, actor_id);
                }
                addToAppearance_Of_Characters(character_id);
            }
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            System.out.println("rollback");
            e.printStackTrace();
        }
        return character_id;
    }

    private void addToAppearance_Of_Characters(int character_id) throws SQLException {
        conn.setAutoCommit(false);
        try {
            int number_of_seasons = randInRange(1, 8);
            Set<Integer> generated = new HashSet<>();
            while (generated.size() < number_of_seasons) {
                generated.add(randInRange(1, 8));
            }
            int[] select_seasons = generated.stream().mapToInt(i -> i).toArray();
            for(int j = 0; j < number_of_seasons; j++){
                insertTableAppearance_Of_Characters(character_id, select_seasons[j]);
            }
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            System.out.println("rollback");
            e.printStackTrace();
        }
    }

    void addToGenera(int amount) throws SQLException {
        conn.setAutoCommit(false);
        try {
            String[] coat_of_arms_part1 = {"серебрянн", "бордов", "сер", "черн", "красн", "желт", "бел", "изумрудн", "ал", "пламенн"};
            String[] coat_of_arms_part2 = {"лев", "орел", "сокол", "тигр", "спрут", "гусь", "пион", "дуб", "окунь", "шмель", "паук", "конь"};
            String[] syllable2 = {"ранды","ионы","стеры","рки","онты","вуды","тоны","теоны"};
            String[] motto_part1 = {"Отчаянно", "Доблестно", "Смело", "Храбро", "Отважно", "Дерзко"};
            String[] motto_part2 = {"горим", "деремся", "умираем", "кричим", "страдаем", "стоим", "полыхаем", "бежим", "сражаемся", "живем"};
            Random rand = new Random();
            int genera_id;
            for (int i = 0; i < amount; i++){
                String name = randString(randInRange(1, 3)) + syllable2[rand.nextInt(8)];
                name = name.substring(0, 1).toUpperCase() + name.substring(1);
                String coat_of_arms = coat_of_arms_part1[rand.nextInt(10)] + "ый " + coat_of_arms_part2[rand.nextInt(12)] + " на " + coat_of_arms_part1[rand.nextInt(10)] + "ом фоне";
                coat_of_arms = coat_of_arms.substring(0, 1).toUpperCase() + coat_of_arms.substring(1);
                String  motto = null;
                if(rand.nextBoolean()) motto = motto_part1[rand.nextInt(6)] + " " + motto_part2[rand.nextInt(10)];
                genera_id = insertTableGenera(name, coat_of_arms, motto);
                if(rand.nextInt(2) == 1){
                    int head_id = addToCharacters(1, false, genera_id);
                    updateTableGenera_1(genera_id, head_id);
                    insertTableChronology_Government_head(head_id, genera_id, randInRange(270, 305));
                }
                if(rand.nextInt(2) == 1){
                    int heir_id = addToCharacters(1, false, genera_id);
                    updateTableGenera_2(genera_id, heir_id);
                }
            }
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            System.out.println("rollback");
            e.printStackTrace();
        }
    }

    private int addToActors(int role_id) throws SQLException {
        conn.setAutoCommit(false);
        int actor_id = 0;
        try {
            String[] gender = {"Женский", "Мужской"};
            Random rand = new Random();
            int type_gender = rand.nextInt(2);
            Faker faker = new Faker(new Locale("ru"));
            String name = faker.name().firstName();
            String surname = faker.name().lastName();
            java.sql.Date dob = randomDate(1950, 2010);
            actor_id = insertTableActors(name, surname, gender[type_gender], dob, role_id);
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            System.out.println("rollback");
            e.printStackTrace();
        }
        return actor_id;
    }

    void addToNations(int amount) throws SQLException {
        conn.setAutoCommit(false);
        try {
            int[] lands = selectFromTables("SELECT id FROM lands");
            int[] religion = selectFromTables("SELECT id FROM religions");
            String[] status = {"Активный", "На грани вымирания", "Вымерли"};
            String[] language = {"Общий", "Скрот", "Высокий валирийский язык", "Старый язык", "Низкий валирийский язык"};
            String[] syllable2 = {"рийцы","далы","анты","арцы","кийцы","нийцы","ряне","норцы","рнийцы"};
            Random rand = new Random();
            int spread_id, religion_id, nation_id;
            for (int i = 0; i < amount; i++){
                int num_status = rand.nextInt(3);
                int num_language = rand.nextInt(5);
                String name = randString(randInRange(1, 3)) + syllable2[rand.nextInt(9)];
                name = name.substring(0, 1).toUpperCase() + name.substring(1);
                spread_id = lands[rand.nextInt(lands.length)];
                religion_id = religion[rand.nextInt(religion.length)];
                nation_id = insertTableNations(name, status[num_status], language[num_language], religion_id, spread_id);
                addToCharacteristics_Nations(nation_id);
            }
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            System.out.println("rollback");
            e.printStackTrace();
        }
    }

    private void addToCharacteristics_Nations(int nation_id) throws SQLException {
        conn.setAutoCommit(false);
        try {
            Random rand = new Random();
            int number_of_characteristics = randInRange(1, 7);
            Set<Integer> generated = new HashSet<>();
            while (generated.size() < number_of_characteristics) {
                generated.add(randInRange(1, 7));
            }
            int[] select_characteristics = generated.stream().mapToInt(i -> i).toArray();
            String[][] values = {{"Светлый", "Темный", "Рыжий"},
                    {"Светлый", "Оливковый", "Темный"},
                    {"Голубой", "Темный", "Зеленый"},
                    {"Огромный", "Низкий", "Высокий"},
                    {"Тихий", "Высокий", "Низкий"},
                    {"Среднее", "Худощавое", "Коренастое"},
                    {"Руки разной длины", "Шесть пальцев на ногах", "Четыре пальца на руках"}};
            for(int j = 0; j < number_of_characteristics; j++){
                int characteristics = select_characteristics[j];
                insertTableCharacteristics_Nations(nation_id, characteristics, values[characteristics - 1][rand.nextInt(3)]);
            }
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            System.out.println("rollback");
            e.printStackTrace();
        }
    }

    void addToReligions(int amount) throws SQLException {
        conn.setAutoCommit(false);
        try {
            String[] typeReligions = {"Монотеизм", "Генотеизм", "Политеизм"};
            String[] syllable1 = {"Великий ","Священный ","Всевластный ","Владыка ","Могущественный "};
            String[] syllable2 = {"ера","вн","дес","люс","он","ол","сий","ус","тас","дар"};
            String[] typeClergy = {"Жрецы","Жены","Септоны","Хаджи","Кру"};
            Random rand = new Random();
            for (int i = 0; i < amount; i++){
                int type = rand.nextInt(3);
                String name = randString(randInRange(1, 3)) + syllable2[rand.nextInt(10)];
                name = syllable1[rand.nextInt(5)] + name.substring(0, 1).toUpperCase() + name.substring(1);
                String clergy = null;
                if(rand.nextInt(1) == 0) clergy = typeClergy[rand.nextInt(5)];
                insertTableReligions(name, typeReligions[type], clergy);
            }
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            System.out.println("rollback");
            e.printStackTrace();
        }
    }

    void addToLands(int amount) throws SQLException {
        conn.setAutoCommit(false);
        try {
            String[] continent = {"Вестерос", "Эссос", "Соторос"};
            String[] syllable2 = {"рни", "кси", "флей", "ркун", "вер", "ат", "рт", "ни", "шир", "ксар"};
            Random rand = new Random();
            for (int i = 0; i < amount; i++) {
                int name_continent = rand.nextInt(3);
                String name = randString(randInRange(1, 4)) + syllable2[rand.nextInt(10)];
                insertTableLands(name.substring(0, 1).toUpperCase() + name.substring(1), continent[name_continent]);
            }
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            System.out.println("rollback");
            e.printStackTrace();
        }
    }

    void addToCities_And_Castles(int amount) throws SQLException {
        conn.setAutoCommit(false);
        try {
            int[] lands = selectFromTables("SELECT id FROM lands");
            int[] religion = selectFromTables("SELECT id FROM religions");
            String[] type = {"Замок", "Город"};
            String[] syllable2 = {"вуд","пор","шай","хад","утон","монт","вос","тис","форт","холд"};
            Random rand = new Random();
            for (int i = 0; i < amount; i++){
                int num_type = rand.nextInt(2);
                String name = randString(randInRange(1, 4)) + syllable2[rand.nextInt(10)];
                int land_id, religion_id ;
                land_id = lands[rand.nextInt(lands.length)];
                religion_id = religion[rand.nextInt(religion.length)];
                insertTableCities_And_Castles(name.substring(0, 1).toUpperCase() + name.substring(1), type[num_type], land_id, religion_id);
            }
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            System.out.println("rollback");
            e.printStackTrace();
        }
    }

    void addToKillings(int amount) throws SQLException {
        conn.setAutoCommit(false);
        try {
            Random rand = new Random();
            int[] characters = selectFromTables("SELECT id FROM characters");
            for (int i = 0; i < amount; i++){
                int victim = addToCharacters(1, false, 0);
                int killer;
                killer = characters[rand.nextInt(characters.length)];
                int season = randInRange(1, 8);
                insertTableKillings(victim, killer, season);
            }
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            System.out.println("rollback");
            e.printStackTrace();
        }
    }

    void addToCatalog_Coalitions(int amount) throws SQLException {
        conn.setAutoCommit(false);
        int coalitions_id = 0;
        try {
            int[] genera = selectFromTables("SELECT id FROM genera");
            String name_last = selectGenera("SELECT MAX(id) FROM catalog_coalitions");
            if (name_last != null) coalitions_id = Integer.parseInt(name_last);
            for (int i = 0; i < amount; i++){
                coalitions_id++;
                String name = "Coalition" + coalitions_id;
                insertTableCatalog_Coalitions(name);
                addToCoalitions_Composition(coalitions_id, genera);
            }
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            System.out.println("rollback");
            e.printStackTrace();
        }
    }

    private void addToCoalitions_Composition(int coalitions_id, int[] genera) throws SQLException {
        conn.setAutoCommit(false);
        try {
            Random rand = new Random();
            int number_of_genera = randInRange(1, 10);
            Set<Integer> generated = new HashSet<>();
            while (generated.size() < number_of_genera) {
                generated.add(genera[rand.nextInt(genera.length)]);
            }
            int[] select_genera = generated.stream().mapToInt(i -> i).toArray();
            for (int j = 0; j < number_of_genera; j++){
                insertTableCoalitions_Composition(coalitions_id, select_genera[j]);
            }
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            System.out.println("rollback");
            e.printStackTrace();
        }
    }

    void addToChronology_Government(int amount) throws SQLException {
        conn.setAutoCommit(false);
        try {
            int[] genera = selectFromTables("SELECT id FROM genera");
            Random rand = new Random();
            int genera_id, chapter_id, begin, end;
            for (int i = 0; i < amount; i++){
                genera_id = genera[rand.nextInt(genera.length)];
                chapter_id = addToCharacters(1, false, genera_id);
                String last_date = selectGenera("SELECT MIN(beginning_government) FROM chronology_government WHERE genera_id = " + genera_id);
                if(last_date == null) end = randInRange(290, 300);
                else end = Integer.parseInt(last_date) - 1;
                begin = randInRange(end - 11, end - 1);
                insertTableChronology_Government(chapter_id, genera_id, begin, end);
            }
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            System.out.println("rollback");
            e.printStackTrace();
        }
    }

    void addToBattle(int amount) throws SQLException {
        conn.setAutoCommit(false);
        try {
            int[] catalog_coalitions = selectFromTables("SELECT id FROM catalog_coalitions");
            int[] cities_and_castles = selectFromTables("SELECT id FROM cities_and_castles");
            Random rand = new Random();
            int year, place_id, participant_1, winner, participant_2;
            for (int i = 0; i < amount; i++){
                year = rand.nextInt(300);
                place_id = cities_and_castles[rand.nextInt(cities_and_castles.length)];
                String name = "Битва за " + selectGenera("SELECT name FROM cities_and_castles WHERE id =" + place_id);
                participant_1 = catalog_coalitions[rand.nextInt(catalog_coalitions.length)];
                participant_2 = participant_1;
                while(participant_2 == participant_1) participant_2 = catalog_coalitions[rand.nextInt(catalog_coalitions.length)];
                if(rand.nextBoolean()) winner = participant_1;
                else winner = participant_2;
                insertTableBattle(name, year, place_id, participant_1, participant_2, winner);
            }
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            System.out.println("rollback");
            e.printStackTrace();
        }
    }
}