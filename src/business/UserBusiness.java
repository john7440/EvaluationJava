package business;

import dao.DaoFactory;
import dao.UserDao;

import java.io.IOException;
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
}
