package application;

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
}
