package business;

import dao.CartDao;
import dao.CourseDao;
import dao.DaoFactory;
import entity.Cart;
import entity.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Business logic for Cart management
 */
public class CartBusiness {
    private static final Logger LOGGER = Logger.getLogger(CartBusiness.class.getName());
    private final CartDao cartDao;
    private CourseDao courseDao;

    public CartBusiness() throws IOException, ClassNotFoundException {
        this.cartDao = DaoFactory.getCartDao();
        this.courseDao = DaoFactory.getCourseDao();
    }

    /**
     * Get or create cart for a user
     * @param user current user
     * @return user's cart
     */
    public Cart getOrCreateCart(User user) throws SQLException {
        if (user == null) {
            LOGGER.log(Level.WARNING, "User cannot be null");
            return null;
        }

        Cart cart = cartDao.findByUserId(user.getId());

        if (cart == null) {
            cart = new Cart(user);
            cart = cartDao.save(cart);
            LOGGER.log(Level.INFO, () -> "New cart created for user: " + user.getLogin());
        }
        return cart;
    }

}
