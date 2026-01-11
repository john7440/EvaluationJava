package dao;

/**
 * Factory pattern for dao instances
 */
public class DaoFactory {

    private DaoFactory() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static UserDao getUserDao(){
        return UserDao.getInstance();
    }

    public static ClientDao getClientDao(){
        return new ClientDao();
    }

    public static CourseDao getCourseDao(){
        return CourseDao.getInstance();
    }

    public static CartDao getCartDao(){
        return new CartDao();
    }

    public static OrderDao getOrderDao(){
        return OrderDao.getInstance();
    }
}
