package application;

import business.CartBusiness;
import business.CourseBusiness;
import business.OrderBusiness;
import business.UserBusiness;
import entity.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Main console application for training sales
 */
public class MainApp {
    private static final Logger LOGGER = Logger.getLogger(MainApp.class.getName());
    private static Scanner scanner = new Scanner(System.in);

    private UserBusiness userBusiness;
    private CourseBusiness courseBusiness;
    private CartBusiness cartBusiness;
    private OrderBusiness orderBusiness;

    private User currentUser;
    private Cart currentCart;

    public MainApp() throws IOException, ClassNotFoundException {
        this.userBusiness = new UserBusiness();
        this.courseBusiness = new CourseBusiness();
        this.cartBusiness = new CartBusiness();
        this.orderBusiness = new OrderBusiness();

    }

    public static void main(String[] args) {
        MainApp app = new MainApp();
        app.run();
    }

    /**
     * Main application loop
     */
    public void run(){
        System.out.println("---Welcome to Training Sales Application---");

        boolean running = true;

        while (running) {
            if (currentUser == null) {
                running = displayVistorMenu();
            } else {
                running  = displayUserMenu();
            }
        }
        System.out.println("\nThank you for using our Application!");
        scanner.close();
    }

    /**
     * Display menu for visitors (non-authenticated users)
     */
    private boolean displayVisitorMenu() throws SQLException, ClassNotFoundException {
        System.out.println("\n========== VISITOR MENU ==========");
        System.out.println("1. View all courses");
        System.out.println("2. Search courses by keyword");
        System.out.println("3. Filter courses by type");
        System.out.println("4. Create an account");
        System.out.println("5. Login");
        System.out.println("0. Exit");
        System.out.println("==================================");
        System.out.print("Choose an option: ");

        int choice = readInt();

        switch (choice) {
            case 1:
                viewAllCourses();
                break;
            case 0:
                return false;
            default:
                System.out.println("Invalid option. Please try again.");
        }
        return true;
    }

    /**
     * View all available courses
     */
    private void viewAllCourses() throws SQLException, ClassNotFoundException {
        List<Course> courses = courseBusiness.getAllCourses();
        courseBusiness.displayCourseList(courses);
    }

    /**
     * Read integer input with error handling
     */
    private int readInt() {
        try {
            int value = Integer.parseInt(scanner.nextLine());
            return value;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return -1;
        }
    }
}
