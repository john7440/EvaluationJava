package application;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for handling user input
 * Provides validated input methods for console applications
 */
 public final class InputHelper {
    private static final Logger LOGGER = Logger.getLogger(InputHelper.class.getName());
    private static final Scanner scanner = new Scanner(System.in);

    private InputHelper() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Reads a string input from user
     */
    public static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    /**
     * Reads an integer input from user with validation
     * @param prompt Message to display
     * @return Valid integer input
     */
    public static int readInt(String prompt) {
        while(true){
            try{
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch(NumberFormatException e){
                System.out.println("Invalid input! Please enter a valid integer!");
                LOGGER.log(Level.WARNING, "Invalid integer input", e);
            }
        }
    }

    /**
     * Reads course type input
     * @return "in-person" or "remote"
     */
    public static String readCourseType() {
        while(true){
            System.out.println("Course type:");
            System.out.println("1. In-person");
            System.out.println("2. Remote");
            int choice = readInt("Select type (1 or 2): ");

            if(choice == 1) return  "IN-PERSON";
            if(choice == 2) return "REMOTE";

            System.out.println("Invalid choice! Please select 1 or 2!");
        }
    }

    /**
     * Reads a double input from user with validation
     * @param prompt Message to display
     * @return Valid double input
     */
    public static double readDouble(String prompt) {
        while(true){
            try{
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Double.parseDouble(input);
            }  catch(NumberFormatException e){
                System.out.println("Invalid input! Please enter a valid number!");
                LOGGER.log(Level.WARNING, "Invalid double input", e);
            }
        }
    }

    /**
     * Reads a boolean input (yes/no)
     * @param prompt Message to display
     * @return true for yes, false for no
     */
    public static boolean readBoolean(String prompt){
        while(true){
            String input = readString(prompt + " (yes/no): ").toLowerCase();

            if(input.equals("yes") || input.equals("y")){
                return true;
            }
            if (input.equals("no") || input.equals("n")) {
                return false;
            }
            System.out.println("Invalid input! Please enter 'yes' or 'no'!");
        }
    }

}
