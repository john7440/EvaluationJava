package application;

import business.*;
import entity.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles menu operations and user interactions
 */
public class MenuHandler {
    private static final Logger LOGGER = Logger.getLogger(MenuHandler.class.getName());
    private final Scanner scanner;

    private final UserBusiness userBusiness;
    private final CourseBusiness courseBusiness;
    private final CartBusiness cartBusiness;
    private final OrderBusiness orderBusiness;

    private User currentUser;
    private Cart currentCart;

    public MenuHandler(Scanner scanner) {
        this.scanner = scanner;
        this.userBusiness = new UserBusiness();
        this.courseBusiness = new CourseBusiness();
        this.cartBusiness = new CartBusiness();
        this.orderBusiness = new OrderBusiness();
    }

    public boolean isUserLoggedIn() {
        return currentUser != null;
    }

    public String getCurrentUsername() {
        return currentUser != null ? currentUser.getLogin() : "";
    }

    //-------------------------------------------------------------------------
    //menus

    /**
     * Handle visitor menu choice
     */
    public boolean handleVisitorMenu(int choice) throws SQLException {
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
            case 5:
                login();
                break;
            case 0:
                InputHelper.close();
                return false;
            default:
                System.out.println("Invalid option. Please try again.");
        }
        return true;
    }

    /**
     * Handle user menu choice
     */
    public boolean handleUserMenu(int choice) throws SQLException {
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
                viewCart();
                break;
            case 5:
                addCourseToCart();
                break;
            case 6:
                removeCourseFromCart();
                break;
            case 7:
                placeOrder();
                break;
            case 8:
                viewMyOrders();
                break;
            case 9:
                logout();
                break;
            case 10:
                openAdminPanel();
                break;
            case 0:
                InputHelper.close();
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
    private void viewAllCourses(){
        List<Course> courses = courseBusiness.getAllCourses();
        courseBusiness.displayCourseList(courses);
    }

    /**
     * Search courses by keyword
     */
    private void searchCoursesByKeyword() {
        System.out.print("\nEnter search keyword: ");
        String keyword = scanner.nextLine();

        List<Course> courses = courseBusiness.searchByKeyword(keyword);
        courseBusiness.displayCourseList(courses);
    }

    /**
     * Filter courses by type (PRESENTIEL or DISTANCIEL)
     */
    public void filterCoursesByType() {
        System.out.println("\nSelect course type:");
        System.out.println("1. IN-PERSON");
        System.out.println("2. REMOTE");
        System.out.print("Enter your choice: ");

        int choice = readInt();
        String type = choice  == 1 ? "IN-PERSON" : "REMOTE";

        List<Course> courses = courseBusiness.filterByType(type);
        courseBusiness.displayCourseList(courses);
    }

    /**
     * Create a new user account
     */
    private void createAccount() throws SQLException {
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

    /**
     * Login with existing account
     */
    private void login() throws SQLException{
        System.out.println("\n========== Login ==========");
        System.out.print("Enter login: ");
        String login = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = userBusiness.authenticate(login, password);

        if (user != null) {
            System.out.println("\nLogin successful! Welcome " + login);
            currentUser = user;
            currentCart = cartBusiness.getOrCreateCart(currentUser);
            LOGGER.log(Level.INFO, () ->"User logged in: " + login);
        } else {
            System.out.println("\nInvalid login or password! Please try again.");
        }
    }

    /**
     * Logout current user
     */
    private void logout()  {
        if (currentUser != null) {
            LOGGER.log(Level.INFO, () ->"User logged out: " + currentUser.getLogin());
        }
        currentUser = null;
        currentCart = null;
        System.out.println("\nYou have been logged out!");
    }

    /**
     * Opens administrator panel
     */
    private void openAdminPanel() {
        if (currentUser != null && currentUser.isAdmin()) {
        AdminMenu adminMenu = new AdminMenu(currentUser);
        adminMenu.displayMenu();
        } else {
            System.out.println("\nAccess denied! Administrator privileges required!");
            LOGGER.log(Level.WARNING, "Unauthorized access attempt to admin panel");
        }
    }

    //-------------------------------
    //managing cart

    /**
     * View current cart
     */
    private void viewCart() throws SQLException {
        currentCart = cartBusiness.getOrCreateCart(currentUser);
        cartBusiness.displayCart(currentCart);
    }

    /**
     * Add a course to cart
     */
    private void addCourseToCart() throws SQLException {
        viewAllCourses();

        System.out.print("\nEnter course ID to add to cart: ");
        Long courseId = readLong();

        System.out.print("Enter quantity: ");
        int quantity = readInt();

        if (currentCart == null) {
            currentCart = cartBusiness.getOrCreateCart(currentUser);
        }

        boolean added = cartBusiness.addCourseToCart(currentCart, courseId, quantity);

        if (added){
            System.out.println("\nCourse added to cart successfully!");
            currentCart = cartBusiness.getOrCreateCart(currentUser);
        } else {
            System.out.println("\nFailed to add course to cart! Please try again");
        }
    }

    /**
     * Remove a course from cart
     */
    private void removeCourseFromCart() throws SQLException{
        viewCart();

        if (cartBusiness.isCartEmpty(currentCart)) {
            return;
        }

        System.out.print("\nEnter cart line ID to remove: ");
        Long cartLineId = readLong();

        boolean removed = cartBusiness.removeCourseFromCart(cartLineId);

        if (removed){
            System.out.println("\nCourse removed from cart successfully!");
            currentCart = cartBusiness.getOrCreateCart(currentUser);
        } else {
            System.out.println("\nFailed to remove course from cart! Please try again");
        }
    }

    //--------------------------------------------------
    //managing orders

    /**
     * Place an order
     */
    private void placeOrder() throws SQLException{
        currentCart = cartBusiness.getOrCreateCart(currentUser);

        if (cartBusiness.isCartEmpty(currentCart)) {
            System.out.println("\nYour cart is empty! Add courses before placing an order!");
            return;
        }

        cartBusiness.displayCart(currentCart);

        //create client
        System.out.println("\n========== Client Information ==========");
        System.out.print("First name: ");
        String firstName = scanner.nextLine();

        System.out.print("Last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Address: ");
        String address = scanner.nextLine();

        System.out.print("Phone number: ");
        String phoneNumber = scanner.nextLine();

        Client client = new Client(firstName, lastName, email, address, phoneNumber);

        Order order = orderBusiness.createOrder(currentUser, currentCart, client);

        if (order != null) {
            System.out.println("\nOrder placed successfully!");
            orderBusiness.displayOrderDetails(order);
            currentCart = cartBusiness.getOrCreateCart(currentUser);
        } else {
            System.out.println("\nFailed to place order. Please try again.");
        }
    }

    /**
     * View user's orders
     */
    private void viewMyOrders(){
        List<Order> orders = orderBusiness.findOrdersByUserId(currentUser.getId());

        if (orders == null || orders.isEmpty()) {
            System.out.println("You have no orders yet.\n");
            return;
        }

        orderBusiness.displayOrderList(orders);

        System.out.print("\nEnter order ID to view details (or 0 to go back): ");
        Long orderId = readLong();

        if (orderId != null && orderId > 0) {
            Order order = orderBusiness.findById(orderId);
            if (order != null) {
                orderBusiness.displayOrderDetails(order);
            } else {
                System.out.println("\nOrder not found.");
            }
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
