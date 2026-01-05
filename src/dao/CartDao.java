package dao;

import config.DatabaseConfig;
import entity.Cart;
import entity.User;

import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Dao for Cart entity
 */
public class CartDao implements IDao<Cart> {
    private static final Logger LOGGER = Logger.getLogger(CartDao.class.getName());
    private static CartDao instance;
    private DatabaseConfig dbConfig;

    private CartDao() throws IOException, ClassNotFoundException {
        this.dbConfig = DatabaseConfig.getInstance();
    }

    public static CartDao getInstance() throws IOException, ClassNotFoundException {
        if (instance == null) {
            instance = new CartDao();
        }
        return instance;
    }

    @Override
    public Cart save(Cart cart) throws SQLException {
        String sql = "INSERT INTO cart(ca_createDate, id_User) VALUES (?, ?)";

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setTimestamp(1, new Timestamp(cart.getCreatedDate().getTime()));
            stmt.setLong(2, cart.getUser().getId());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    cart.setId(rs.getLong(1));
                    LOGGER.log(Level.INFO, "Cart created with ID: " + cart.getId());
                }
            }
            return cart;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error saving cart", e);
        }
    }

    @Override
    public Cart update(Cart entity) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public Cart findById(Long id) {
        return null;
    }

    @Override
    public List<Cart> findAll() throws SQLException {
        return List.of();
    }

    private Cart mapResultSetToCart(ResultSet rs) throws SQLException {
        Cart cart = new Cart();
        cart.setId(rs.getLong("id_Cart"));
        cart.setCreatedDate(rs.getTimestamp("ca_createdDate"));
        return cart;
    }

    public Cart findByUserId(Long userId) throws SQLException {
        String sql = "SELECT * FROM cart WHERE id_User = ?";

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Cart cart = mapResultSetToCart(rs);
                    cart.setCartLines(findCartLinesByCartId(cart.getId()));
                    return cart;
                }
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error finding cart by user id", e);
            }
            return null;
        }
    }
}
