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
    private UserDao userDao;

    public  UserBusiness() throws IOException, ClassNotFoundException {
        this.userDao = DaoFactory.getUserDao();
    }

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
            LOGGER.log(Level.WARNING, "Login already exists!");
            return null;
        }

        //new user
        User user = new User(login, password);
        return userDao.save(user);
    }
}
