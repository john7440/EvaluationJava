package application;

/**
 * Handles menu display
 */
public final class MenuDisplay {

    private MenuDisplay() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static void displayWelcome() {
        System.out.println("================================================");
        System.out.println("   Welcome to Training Sales Application");
        System.out.println("================================================\n");
    }

    public static void displayVisitorMenu() {
        System.out.println("\n========== VISITOR MENU ==========");
        System.out.println("1. View all courses");
        System.out.println("2. Search courses by keyword");
        System.out.println("3. Filter courses by type");
        System.out.println("4. Create an account");
        System.out.println("5. Login");
        System.out.println("0. Exit");
        System.out.println("==================================");
        System.out.print("Choose an option: ");
    }

    public static void displayUserMenu(String username) {
        System.out.println("\n========== USER MENU (Logged in as: " + username + ") ==========");
        System.out.println("1. View all courses");
        System.out.println("2. Search courses by keyword");
        System.out.println("3. Filter courses by type");
        System.out.println("4. View cart");
        System.out.println("5. Add course to cart");
        System.out.println("6. Remove course from cart");
        System.out.println("7. Place order");
        System.out.println("8. View my orders");
        System.out.println("9. Logout");
        System.out.println("0. Exit");
        System.out.println("====================================================================");
        System.out.print("Choose an option: ");
    }

    public static void displayGoodbye() {
        System.out.println("\nThank you for using Training Sales Application!");
    }
}
