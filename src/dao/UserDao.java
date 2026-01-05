package dao;

import config.DatabaseConfig;
import entity.User;

import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Dao for User entity
 */
public class UserDao implements IDao<User>{
    private static final Logger LOGGER = Logger.getLogger(UserDao.class.getName());
    private static UserDao instance;
    private final DatabaseConfig dbConfig;

    private UserDao() throws IOException, ClassNotFoundException {
        this.dbConfig = DatabaseConfig.getInstance();
    }

    public static UserDao getInstance() throws IOException, ClassNotFoundException {
        if (instance == null) {
            instance = new UserDao();
        }
        return instance;
    }

    @Override
    public User save(User user) throws SQLException {
        String sql = "INSERT INTO user(u_login, u_password) VALUES (?, ?)";

        try (Connection connect = dbConfig.getConnection();
             PreparedStatement statement = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs =  statement.getGeneratedKeys()){
                    if (rs.next()) {
                        user.setId(rs.getLong(1));
                        LOGGER.log(Level.INFO, "User saved with id " + user.getId());
                    }
                }
            }
            return user;

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error saving user", e);

        }
        return user;
    }

    @Override
    public User update(User entity) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public List<User> findAll() throws  SQLException {
        return List.of();
    }


}
