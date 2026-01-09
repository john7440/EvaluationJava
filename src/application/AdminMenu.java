package application;

import business.CourseBusiness;
import entity.Course;
import entity.User;

import java.util.List;
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
            displayMenuOptions();

            int choice = InputHelper.readInt("Enter your choice: ");

            switch (choice) {
                case 1 -> viewAllCourses();
                default -> System.out.println("Invalid choice! Please try again!");
            }
        }
    }

    /**
     * Displays menu options
     */
    private void displayMenuOptions() {
        System.out.println("\n========== ADMIN MENU ==========");
        System.out.println("1. View all courses");
        System.out.println("2. Add a new course");
        System.out.println("3. Update an existing course");
        System.out.println("4. Delete an existing course");
        System.out.println("5. View course details");
        System.out.println("6. Logout");
        System.out.println("0. Exit");
        System.out.println("==================================");
    }

    /**
     * View all available courses
     */
    private void viewAllCourses(){
        List<Course> courses = courseBusiness.getAllCourses();
        courseBusiness.displayCourseList(courses);
    }
}
