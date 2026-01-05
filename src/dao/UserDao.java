package dao;

import config.DatabaseConfig;
import entity.User;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Dao for User entity
 */
public class UserDao implements IDao<User>{
    private static final Logger LOGGER = Logger.getLogger(UserDao.class.getName());
    private static UserDao instance;
    private DatabaseConfig dbConfig;

    private UserDao() throws IOException, ClassNotFoundException {
        this.dbConfig = DatabaseConfig.getInstance();
    }

}
