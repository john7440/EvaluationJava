package business;

import dao.CartDao;
import dao.CourseDao;
import dao.DaoFactory;

import java.io.IOException;
import java.util.logging.Logger;

public class CartBusiness {
    private static final Logger LOGGER = Logger.getLogger(CartBusiness.class.getName());
    private CartDao cartDao;
    private CourseDao courseDao;

    public CartBusiness() throws IOException, ClassNotFoundException {
        this.cartDao = DaoFactory.getCartDao();
        this.courseDao = DaoFactory.getCourseDao();
    }
}
