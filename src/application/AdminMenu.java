package application;

import business.CourseBusiness;
import entity.User;

import java.util.Scanner;
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
}
