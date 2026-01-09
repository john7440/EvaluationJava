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
                case 2 -> addNewCourse();
                case 6 -> {
                    System.out.println("Logging out from admin panel...");
                    running = false;
                }
                case 0 -> {
                    System.out.println("Thank you for using our Application! Goodbye!");
                    System.exit(0);
                }
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

    /**
     * Adds a new course
     */
    private void addNewCourse(){
        System.out.println("\n========== ADD NEW COURSE ==========");

        try {
            String name = InputHelper.readString("Course name: ");
            String description = InputHelper.readString("Course description: ");
            int duration = InputHelper.readInt("Course duration(in days): ");
            String type = InputHelper.readCourseType();
            double price = InputHelper.readDouble("Course price (in €): ");

            Course newCourse = courseBusiness.createCourse(admin, name, description, duration, type, price);
            System.out.println("\nCourse " + name + " created successfully!");
            System.out.println(courseBusiness.displayCourseDetails(newCourse));

        } catch (SecurityException e){
            System.out.println("Access denied! " + e.getMessage());
            LOGGER.log(Level.SEVERE, "Security error", e);
        } catch (IllegalArgumentException e){
            System.out.println("Validation Error: "+  e.getMessage());
        } catch (Exception e) {
            System.out.println("Error creating course: " + e.getMessage());
            LOGGER.log(Level.SEVERE, "Error creating course!");
        }
    }

    /**
     * Updates an existing course
     */
    private void updateCourse(){
        System.out.println("\n========== UPDATE COURSE ==========");

        try{
            long id = InputHelper.readLong("Course ID to update: ");

            Course existingCourse = courseBusiness.getCourseById(id);
            if (existingCourse == null){
                System.out.println("Course with ID: " + id + " not found!");
                return;
            }

            System.out.println("\nCurrent course information:");
            System.out.println(courseBusiness.displayCourseDetails(existingCourse));
            System.out.println("\nEnter new information (press Enter to keep the current value):");

            //TODO des read pour chaque type avec une option default dans InputHelper

        }
    }
}
