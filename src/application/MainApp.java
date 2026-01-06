package application;

import business.CartBusiness;
import business.CourseBusiness;
import business.OrderBusiness;
import business.UserBusiness;
import entity.*;

import java.io.IOException;
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
}
