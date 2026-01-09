package application;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Utility class for handling user input
 * Provides validated input methods for console applications
 */
 public final class InputHelper {
    private static final Logger LOGGER = Logger.getLogger(InputHelper.class.getName());
    private static final Scanner scanner = new Scanner(System.in);

    private InputHelper() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Reads a string input from user
     */
    public static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

}
