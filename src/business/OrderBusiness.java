package business;

import dao.ClientDao;
import dao.DaoFactory;
import dao.OrderDao;

import java.io.IOException;
import java.util.logging.Logger;

public class OrderBusiness {
    private static final Logger LOGGER = Logger.getLogger(OrderBusiness.class.getName());
    private OrderDao orderDao;
    private ClientDao clientDao;
    private CartBusiness cartBusiness;

    public OrderBusiness() throws IOException, ClassNotFoundException {
        this.orderDao = DaoFactory.getOrderDao();
        this.clientDao = DaoFactory.getClientDao();
        this.cartBusiness = new CartBusiness();
    }
}
