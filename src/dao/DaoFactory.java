package dao;

import java.io.IOException;

/**
 * Factory pattern for dao instances
 */
public class DaoFactory {

    public static UserDao getUserDao() throws IOException, ClassNotFoundException {
        return UserDao.getInstance();
    }

    public static ClientDao getClientDao() throws IOException, ClassNotFoundException {
        return ClientDao.getInstance();
    }

    public static CourseDao getCourseDao() throws IOException, ClassNotFoundException {
        return CourseDao.getInstance();
    }

    public static CartDao getCartDao() throws IOException, ClassNotFoundException {
        return CartDao.getInstance();
    }

    public static OrderDao getOrderDao() throws IOException, ClassNotFoundException {
        return OrderDao.getInstance();
    }
}
