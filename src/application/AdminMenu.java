package application;

import business.CourseBusiness;
import entity.User;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Administrator menu for course management
 */
public class AdminMenu {
    private static final Logger LOGGER = Logger.getLogger(AdminMenu.class.getName());
    private final CourseBusiness courseBusiness;
    private final Scanner scanner;
    private final User admin;

    public AdminMenu(User admin) {
        this.admin = admin;
        this.courseBusiness = new CourseBusiness();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays and handles admin menu
     */
    public void displayMenu() {
        if (!admin.isAdmin()) {
            System.out.println("Access denied! Administrator privileges required!");
            LOGGER.log(Level.WARNING, "Non-admin user attempted to access admin menu");
            return;
        }

        boolean running = true;
        while (running) {
            //TODO displayMenuOptions();

            int choice = InputHelper.readInt("Enter your choice: ");

            switch (choice) {
                default -> System.out.println("Invalid choice! Please try again!");
            }
        }
    }
}
