package application;

import business.CartBusiness;
import business.CourseBusiness;
import business.OrderBusiness;
import business.UserBusiness;
import entity.*;

import java.util.Scanner;
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
    public boolean handleVisitorMenu(int choice) {
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
                return false;
            default:
                System.out.println("Invalid option. Please try again.");
        }
        return true;
    }

    /**
     * Handle user menu choice
     */
    public boolean handleUserMenu(int choice) {
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
            case 0:
                return false;
            default:
                System.out.println("Invalid option. Please try again.");
        }
        return true;
    }

    //----------------------------------------------------------------------------
}
