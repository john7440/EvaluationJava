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
import java.util.logging.Level;
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
    //----------------------------------------------------------------------------
    //main

    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        MainApp app = new MainApp();
        app.run();
    }

    //---------------------------------------------------------------------------

    /**
     * Main application loop
     */
    public void run() throws SQLException, ClassNotFoundException {
        System.out.println("---Welcome to Training Sales Application---");

        boolean running = true;

        while (running) {
            if (currentUser == null) {
                running = displayVisitorMenu();
            } else {
                running  = displayUserMenu();
            }
        }
        System.out.println("\nThank you for using our Application!");
        scanner.close();
    }

    //-------------------------------------------------------------
    //menus

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
            case 2:
                searchCoursesByKeyword();
                break;
            case 3:
                filterCoursesByType();
                break;
            case 4:
                createAccount();
                break;
            case 0:
                return false;
            default:
                System.out.println("Invalid option. Please try again.");
        }
        return true;
    }

    // ----------------------------------------------------------
    //business methods

    /**
     * View all available courses
     */
    private void viewAllCourses() throws SQLException, ClassNotFoundException {
        List<Course> courses = courseBusiness.getAllCourses();
        courseBusiness.displayCourseList(courses);
    }

    /**
     * Search courses by keyword
     */
    private void searchCoursesByKeyword() throws SQLException, ClassNotFoundException {
        System.out.print("\nEnter search keyword: ");
        String keyword = scanner.nextLine();

        List<Course> courses = courseBusiness.searchByKeyword(keyword);
        courseBusiness.displayCourseList(courses);
    }

    /**
     * Filter courses by type (PRESENTIEL or DISTANCIEL)
     */
    public void filterCoursesByType() throws SQLException, ClassNotFoundException {
        System.out.println("\nSelect course type:");
        System.out.println("1. PRESENTIEL");
        System.out.println("2. DISTANCIEL");
        System.out.print("Choose an option: ");

        int choice = readInt();
        String type = choice  == 1 ? "PRESENTIEL" : "DISTANCIEL";

        List<Course> courses = courseBusiness.filterByType(type);
        courseBusiness.displayCourseList(courses);
    }

    /**
     * Create a new user account
     */
    private void createAccount() throws SQLException, ClassNotFoundException {
        System.out.println("\n========== Create Account ==========");
        System.out.print("Enter login: ");
        String login = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = userBusiness.createAccount(login, password);

        if (user != null) {
            System.out.println("Account created successfully!");
            currentUser = user;
            currentCart = cartBusiness.getOrCreateCart(currentUser);
            LOGGER.log(Level.INFO, () ->"New account created and user logged in: " + login);
        } else {
            System.out.println("\nFailed to create account. Login may already exist.");
        }

    }

    //--------------------------------------------------------------------
    //verifications methods

    /**
     * Read integer input with error handling
     */
    private int readInt() {
        try {
            int value;
            value = Integer.parseInt(scanner.nextLine());
            return value;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return -1;
        }
    }

    /**
     * Read long input with error handling
     */
    private Long readLong() {
        try {
            return Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            return -1L;
        }
    }
}
