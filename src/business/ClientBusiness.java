package business;

import dao.ClientDao;

public class ClientBusiness {
    private final ClientDao clientDao;

    public ClientBusiness(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    // -------------------------------------
    // validation

    private static void validateName(String name, String fieldName) {
        if (name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException(fieldName +" cannot be empty/null!");
        }
        if (name.length() < 3 || name.length() > 50) {
            throw new IllegalArgumentException( fieldName + " must be between 3 and 50 characters!");
        }
        if (name.matches("^[a-zA-ZÀ-ÿ\\s'-]+$")){
            throw new IllegalArgumentException(fieldName + " cannot contain special characters or digits!");
        }
    }

    private static void validateEmail(String email) {
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    private static void validateAddress(String address) {
        if (address != null && (address.length() < 10 || address.length() > 255)) {
            throw new IllegalArgumentException("Address must be between 10 and 255 characters!");
        }
    }




}
