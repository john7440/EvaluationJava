package business;

import dao.ClientDao;
import dao.DaoFactory;
import dao.OrderDao;
import entity.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
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

    /**
     * Find order by ID
     * @param orderId order ID
     * @return order or null if not found
     */
    public Order findById(Long orderId) {
        return orderDao.findById(orderId);
    }

    /**
     * Find all orders for a user
     * @param userId user ID
     * @return list of user's orders
     */
    public List<Order> findOrdersByUserId(Long userId) throws SQLException {
        return orderDao.findByUserId(userId);
    }

    /**
     * Display order details
     * @param order order to display
     */
    public void displayOrderDetails(Order order) {
        if (order == null) {
            System.out.println("Cannot display order details: order is null");
            return;
        }

        System.out.println("\n========== Order Confirmation ==========");
        System.out.println("Order ID: " + order.getId());
        System.out.println("Date: " + order.getOrderDate());
        System.out.println("Client: " + order.getClient().getFirstName() + " " + order.getClient().getLastName());
        System.out.println("Email: " + order.getClient().getEmail());
        System.out.println("----------------------------------------");
        System.out.println("Items:");

        for (OrderLine line : order.getOrderLines()) {
            Course course = line.getCourse();
            double lineTotal = line.getUnitPrice() * line.getQuantity();
            System.out.printf("  %-30s | Qty: %2d | Unit: %8.2f € | Total: %8.2f €%n",
                    course.getName(),
                    line.getQuantity(),
                    line.getUnitPrice(),
                    lineTotal);
        }
        System.out.println("----------------------------------------");
        System.out.printf("TOTAL: %.2f €%n", order.getTotalAmount());
        System.out.println("========================================\n");
    }

    /**
     * Display list of orders
     * @param orders list of orders to display
     */
    public void displayOrderList(List<Order> orders) {
        if (orders == null || orders.isEmpty()) {
            System.out.println("No orders found");
            return;
        }

        System.out.println("\n========== Your Orders ==========");
        for (Order order : orders) {
            System.out.printf("Order #%-5d | Date: %s | Client: %-20s | Total: %8.2f €%n",
                    order.getId(),
                    order.getOrderDate(),
                    order.getClient().getFirstName() + " " + order.getClient().getLastName(),
                    order.getTotalAmount());
        }
        System.out.println("=================================\n");
    }
}
