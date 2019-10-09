import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException {

        Scanner type_generator = new Scanner(System.in);

        System.out.println("Generator data for DB Game_of_Thrones\n");
        System.out.println("Insert the number:\n" +
                "1 - add to the table characters \n" +
                "2 - add to the table genera \n" +
                "3 - add to the table actors \n" +
                "4 - add to the table nations \n" +
                "5 - add to the table religions \n" +
                "6 - add to the table lands \n" +
                "7 - add to the table cities_and_castles \n" +
                "8 - add to the table killings \n" +
                "9 - add to the tables catalog_coalitions \n" +
                "10 - add to the tables chronology_government \n" +
                "11 - add to the tables battle \n" +
                "0 - exit\n"
        );
        int type_numb = type_validation(type_generator);
        if (type_numb == 0) System.exit(0);
        else {
            Generator generator = new Generator();
            generator.connect();
            Scanner amount_data = new Scanner(System.in);
            System.out.println("Insert amount of data:");
            switch (type_numb) {
                case 1:
                    generator.addToCharacters(amount_validation(amount_data), false, 0);
                    break;
                case 2:
                    generator.addToGenera(amount_validation(amount_data));
                    break;
                case 3:
                    generator.addToCharacters(amount_validation(amount_data), true, 0);
                    break;
                case 4:
                    generator.addToNations(amount_validation(amount_data));
                    break;
                case 5:
                    generator.addToReligions(amount_validation(amount_data));
                    break;
                case 6:
                    generator.addToLands(amount_validation(amount_data));
                    break;
                case 7:
                    generator.addToCities_And_Castles(amount_validation(amount_data));
                    break;
                case 8:
                    generator.addToKillings(amount_validation(amount_data));
                    break;
                case 9:
                    generator.addToCatalog_Coalitions(amount_validation(amount_data));
                    break;
                case 10:
                    generator.addToChronology_Government(amount_validation(amount_data));
                    break;
                case 11:
                    generator.addToBattle(amount_validation(amount_data));
                    break;
            }
            generator.disconnect();
            amount_data.close();
        }
        type_generator.close();
    }

    private static int type_validation(Scanner scan){
        int number = -1;
        if (scan.hasNextInt()) {
            number = scan.nextInt();
            if (number > 11 || number < 0) {
                System.out.printf("Generation type set incorrectly");
                System.exit(20);
            }
        } else {
            System.out.println("Input data is not number");
            System.exit(10);
        }
        return number;
    }

    private static int amount_validation(Scanner scan){
        int number = -1;
        if (scan.hasNextInt()) {
            number = scan.nextInt();
            if (number < 0) {
                System.out.printf("Negative number not allowed");
                System.exit(30);
            }
        } else {
            System.out.println("Input data is not number");
            System.exit(10);
        }
        return number;
    }
}
