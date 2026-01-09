package application;

import java.sql.SQLException;
import java.util.Scanner;


/**
 * Main console application for training sales
 */
public class MainApp {

    //-------------------------
    // users for tests:
    // admin : admin123
    // testuser : password
    // john.doe : test2026

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        MenuHandler menuHandler = new MenuHandler(scanner);

        MenuDisplay.displayWelcome();

        boolean running = true;

        while (running) {
            if (!menuHandler.isUserLoggedIn()){
                MenuDisplay.displayVisitorMenu();
                int choice = readInt(scanner);
                running = menuHandler.handleVisitorMenu(choice);
            } else {
                MenuDisplay.displayUserMenu(menuHandler.getCurrentUsername());
                int choice = readInt(scanner);
                running = menuHandler.handleUserMenu(choice);
            }
        }

        MenuDisplay.displayGoodbye();
        scanner.close();
    }

    private static int readInt(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a number!");
            return -1;
        }
    }
}




