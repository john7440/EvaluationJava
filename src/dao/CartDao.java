package dao;

import config.DatabaseConfig;
import entity.Cart;
import entity.CartLine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Dao for Cart entity
 * Singleton pattern is used intentionally
 */
@SuppressWarnings("java:S6548")
public class CartDao implements IDao<Cart> {
    private static final Logger LOGGER = Logger.getLogger(CartDao.class.getName());
    private final DatabaseConfig dbConfig;

    private CartDao(){
        this.dbConfig = DatabaseConfig.getInstance();
    }

    private static class SingletonHolder{
        private static final CartDao INSTANCE = new CartDao();
    }

    public static CartDao getInstance(){
        return SingletonHolder.INSTANCE;
    }

    //------------------------------------------
    //methods

    @Override
    public Cart save(Cart cart){
        String sql = "INSERT INTO cart(ca_createdDate, id_User) VALUES (?, ?)";

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setTimestamp(1, new Timestamp(cart.getCreatedDate().getTime()));
            stmt.setLong(2, cart.getUser().getId());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    cart.setId(rs.getLong(1));
                    LOGGER.log(Level.INFO, () ->"Cart created with ID: " + cart.getId());
                }
            }
            return cart;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error saving cart", e);
        }
        return null;
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
    public List<Cart> findAll() {
        return List.of();
    }

    public Cart findByUserId(Long userId) throws SQLException {
        String sql = "SELECT c.*  FROM cart c WHERE id_User = ?";

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Cart cart = ResultSetMapper.mapToCart(rs);
                    cart.setCartLines(findCartLinesByCartId(cart.getId()));
                    return cart;
                }
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error finding cart by user id", e);
            }
            return null;
        }
    }

    public void addCartLine(CartLine cartLine) {
        String sql = "INSERT INTO cartLine (car_quantity, id_Cart, id_Course) VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE car_quantity = car_quantity + ?";

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, cartLine.getQuantity());
            stmt.setLong(2, cartLine.getCart().getId());
            stmt.setLong(3, cartLine.getCourse().getId());
            stmt.setInt(4, cartLine.getQuantity());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    cartLine.setId(rs.getLong(1));
                }
            }
            LOGGER.log(Level.INFO, "CartLine added/updated");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding cart line", e);
        }
    }

    public boolean removeCartLine(Long cartLineId) {
        String sql = "DELETE FROM CartLine WHERE id_CartLine = ?";

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, cartLineId);
            int rowsAffected = stmt.executeUpdate();
            LOGGER.log(Level.INFO, () ->"CartLine removed: " + cartLineId);
            return rowsAffected > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error removing cart line", e);

        }
        return false;
    }

    public boolean clearCart(Long cartId) {
        String sql = "DELETE FROM CartLine WHERE id_Cart = ?";

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, cartId);
            stmt.executeUpdate();
            LOGGER.log(Level.INFO, () -> "Cart cleared: " + cartId);
            return true;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error clearing cart", e);
        }
        return false;
    }

    private List<CartLine> findCartLinesByCartId(Long cartId) {
        String sql = "SELECT cl.*, c.* FROM CartLine cl " +
                "JOIN Course c ON cl.id_Course = c.id_Course " +
                "WHERE cl.id_Cart = ?";
        List<CartLine> cartLines = new ArrayList<>();

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, cartId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    cartLines.add(ResultSetMapper.mapToCartLine(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding cart lines", e);
        }
        return cartLines;
    }
}
