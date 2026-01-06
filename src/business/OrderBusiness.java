package business;

import dao.ClientDao;
import dao.DaoFactory;
import dao.OrderDao;
import entity.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderBusiness {
    private static final Logger LOGGER = Logger.getLogger(OrderBusiness.class.getName());
    private final OrderDao orderDao;
    private final ClientDao clientDao;
    private final CartBusiness cartBusiness;

    public OrderBusiness() throws IOException, ClassNotFoundException {
        this.orderDao = DaoFactory.getOrderDao();
        this.clientDao = DaoFactory.getClientDao();
        this.cartBusiness = new CartBusiness();
    }

    /**
     * Create order from cart
     * @param user current user
     * @param cart user's cart
     * @param client client information
     * @return created order or null if failed
     */
    public Order createOrder(User user, Cart cart, Client client) throws SQLException {
        if (user == null ||  cart == null || client == null) {
            LOGGER.log(Level.WARNING, "User,cart or client cannot be null");
            return null;
        }

        if (cartBusiness.isCartEmpty(cart)) {
                LOGGER.log(Level.WARNING, "Cannot create order: cart is empty!");
                return null;
        }
        
        //check if client exists
        Client existingClient = clientDao.findByEmail(client.getEmail());
        if (existingClient != null) {
            LOGGER.log(Level.WARNING, () ->"Using existing client: " +  existingClient);
        } else {
            clientDao.save(client);
            LOGGER.log(Level.WARNING, () ->"Client with email: " + client.getEmail() + " has been saved!");
        }

        Order order = new Order(user, client);

        //we convert cart lines to order lines
        for (CartLine cartLine : cart.getCartLines()) {
            OrderLine orderLine = new OrderLine(
                    order,
                    cartLine.getCourse(),
                    cartLine.getQuantity(),
                    cartLine.getCourse().getPrice()
            );
            order.addOrderLine(orderLine);
        }

        //Calculate the total amount
        order.calculateTotalAmount();

        //save the order
        order =  orderDao.save(order);

        //Clear cart after successful order
        if (order != null){
            cartBusiness.clearCart(cart);
            Order finalOrder = order;
            LOGGER.log(Level.INFO, () ->"Order created successfully: " + finalOrder.getId());
        }
        return order;
    }
}
