package business;

import dao.CartDao;
import dao.CourseDao;
import dao.DaoFactory;
import entity.Cart;
import entity.CartLine;
import entity.Course;
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

    /**
     * Add a course to cart
     * @param cart user's cart
     * @param courseId course ID to add
     * @param quantity quantity to add
     * @return true if added successfully
     */
    public boolean addCourseToCart(Cart cart,Long courseId, int quantity) {
        if (cart == null) {
            LOGGER.log(Level.WARNING, "Cart cannot be null");
            return false;
        }

        if (quantity < 0) {
            LOGGER.log(Level.WARNING, "Quantity must be positive");
            return false;
        }

        Course course = courseDao.findById(courseId);
        if (course == null) {
            LOGGER.log(Level.WARNING, () -> "Course not found " +  courseId);
            return false;
        }

        CartLine cartLine = new CartLine(cart, course, quantity);
        cartDao.addCartLine(cartLine);

        LOGGER.log(Level.INFO, ()-> "Course added to cart: " + course.getName());
        return true;
    }

    /**
     * Remove a course from cart
     * @param cartLineId cart line ID to remove
     * @return true if removed successfully
     */
    public boolean removeCourseFromCart(Long cartLineId) {
        if (cartLineId == null) {
            LOGGER.log(Level.WARNING, "Cart line id cannot be null");
            return false;
        }

        boolean removed = cartDao.removeCartLine(cartLineId);

        if (removed) {
            LOGGER.log(Level.INFO, () -> "Course removed from cart: " + cartLineId);
        }
        return removed;
    }

    /**
     * Clear all items from cart
     * @param cart cart to clear
     * @return true if cleared successfully
     */
    public boolean clearCart(Cart cart) {
        if (cart == null) {
            LOGGER.log(Level.WARNING, "Cart cannot be null");
            return false;
        }

        boolean cleared = cartDao.clearCart(cart.getId());
        if (cleared) {
            LOGGER.log(Level.INFO, () -> "Cart cleared!");
        }
        return cleared;
    }

}
