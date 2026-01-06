package business;

import dao.DaoFactory;
import dao.UserDao;
import entity.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Business logic for User management
 */
public class UserBusiness {
    private static final Logger LOGGER = Logger.getLogger(UserBusiness.class.getName());
    private final UserDao userDao;

    public  UserBusiness() throws IOException, ClassNotFoundException {
        this.userDao = DaoFactory.getUserDao();
    }

    /**
     * Create a new user account
     * @param login user login
     * @param password user password
     * @return created user or null if login already exists
     */
    public User createAccount(String login, String password) throws SQLException {
        //check if login or password are not empty
        if (login == null || login.trim().isEmpty()) {
            LOGGER.log(Level.WARNING, "Login cannot be empty!");
            return null;
        }

        if (password == null || password.trim().isEmpty()) {
            LOGGER.log(Level.WARNING, "Password cannot be empty!");
            return null;
        }

        //check if this login is already taken
        User existingUser = userDao.findByLogin(login);
        if (existingUser != null) {
            LOGGER.log(Level.WARNING, () ->"Login already exists!" + login);
            return null;
        }

        //new user
        User user = new User(login, password);
        return userDao.save(user);
    }

    /**
     * Authenticate a user
     * @param login user login
     * @param password user password
     * @return authenticated user or null if credentials are invalid
     */
    public User authenticate(String login, String password) throws SQLException {
        if (login == null || password == null) {
            LOGGER.log(Level.WARNING, () ->"Authentication failed for login: " + login);
            return null;
        }
        User user = userDao.authenticate(login, password);

        if  (user == null) {
            LOGGER.log(Level.WARNING, "Invalid login or password!");
        } else {
            LOGGER.log(Level.INFO, () ->"User authenticated successfully: " + login);
        }
        return user;
    }
}
