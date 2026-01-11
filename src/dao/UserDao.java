package dao;

import config.DatabaseConfig;
import entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Dao for User entity
 */

public class UserDao implements IDao<User>{
    private static final Logger LOGGER = Logger.getLogger(UserDao.class.getName());
    private final DatabaseConfig dbConfig;

    public UserDao() {
        this.dbConfig = DatabaseConfig.getInstance();
    }

    //--------------------------------------------------------
    // methods
    
    @Override
    public User save(User user) {
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
                        LOGGER.log(Level.INFO, () ->"User saved with id " + user.getId());
                    }
                }
            }
            return user;

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error saving user", e);

        }
        return user;
    }

    public User findByLogin(String login){
        String sql = "SELECT u.* FROM user u WHERE u_login = ?";

        try (Connection connect = dbConfig.getConnection() ;
        PreparedStatement statement = connect.prepareStatement(sql)){

            statement.setString(1, login);
            try (ResultSet rs = statement.executeQuery()){
                if (rs.next()) {
                    return ResultSetMapper.mapToUser(rs);
                }
            }
        } catch (SQLException e){
            LOGGER.log(Level.SEVERE, "Error finding user by login", e);
        }
        return null;
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
    public List<User> findAll(){
        String sql = "SELECT u.* FROM user u";
        List<User> users = new ArrayList<>();

        try (Connection connect = dbConfig.getConnection();
        Statement statement = connect.createStatement();
        ResultSet rs = statement.executeQuery(sql)){
            while (rs.next()) {
                users.add(ResultSetMapper.mapToUser(rs));
            }
        } catch (SQLException e){
            LOGGER.log(Level.SEVERE, "Error finding all users", e);
        }
        return users;
    }

    //auth
    public User authenticate(String login, String password){
        String sql = "SELECT u.* FROM user u WHERE u_login = ? AND u_password = ?";

        try (Connection connect = dbConfig.getConnection();
             PreparedStatement statement = connect.prepareStatement(sql)) {

            statement.setString(1, login);
            statement.setString(2, password);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    LOGGER.log(Level.INFO, () ->"User authenticated:" + login);
                    return ResultSetMapper.mapToUser(rs);
                }
            }

        } catch (SQLException e){
            LOGGER.log(Level.SEVERE, "Error authenticating user", e);
        }
        return null;
    }

}

