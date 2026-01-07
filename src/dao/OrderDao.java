package dao;

import config.DatabaseConfig;
import entity.*;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Dao for Order entity
 * Singleton pattern is intentionally used
 */
@SuppressWarnings("java:S6548")
public class OrderDao implements IDao<Order>{
    private static final Logger LOGGER = Logger.getLogger(OrderDao.class.getName());
    private final DatabaseConfig dbConfig;

    private OrderDao(){
        this.dbConfig = DatabaseConfig.getInstance();
    }

    private static class SingletonHolder {
        private static final OrderDao INSTANCE = new OrderDao();
    }

    public static OrderDao getInstance(){
        return SingletonHolder.INSTANCE;
    }
    //--------------------------------------------------
    //methods

    @Override
    public Order save(Order order) {
        Connection connection = null;
        try{
            connection = dbConfig.getConnection();
            connection.setAutoCommit(false);

            //Order
            String orderSql = "INSERT INTO `order` (o_orderDate, o_totalAmount, id_User, id_Client) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(orderSql,  Statement.RETURN_GENERATED_KEYS)) {
                statement.setTimestamp(1, new Timestamp(order.getOrderDate().getTime()));
                statement.setDouble(2, order.getTotalAmount());
                statement.setLong(3, order.getUser().getId());
                statement.setLong(4, order.getClient().getId());

                statement.executeUpdate();

                try (ResultSet rs = statement.getGeneratedKeys()) {
                    if (rs.next()) {
                        order.setId(rs.getLong(1));
                    }
                }
            }

            // OrderLines
            String linesSql = "INSERT INTO OrderLine (ol_quantity, ol_unitPrice, id_Order, id_Course) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(linesSql, Statement.RETURN_GENERATED_KEYS)) {
                for (OrderLine ol : order.getOrderLines()) {
                    statement.setInt(1, ol.getQuantity());
                    statement.setDouble(2, ol.getUnitPrice());
                    statement.setLong(3, order.getId());
                    statement.setLong(4, ol.getCourse().getId());
                    statement.addBatch();

                    int[] results = statement.executeBatch();

                    try (ResultSet rs = statement.getGeneratedKeys()) {
                        int index = 0;
                        while(rs.next() && index < order.getOrderLines().size()){
                            order.getOrderLines().get(index).setId(rs.getLong(1));
                            index++;
                        }
                    }
                    LOGGER.log(Level.INFO, () -> results.length + " order lines inserted");
                }
            }
            connection.commit();
            LOGGER.log(Level.INFO, () ->"Order saved with ID: " + order.getId());
            return order;
        }  catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                    LOGGER.log(Level.WARNING, "Transaction rolled back");
                } catch (SQLException ex) {
                    LOGGER.log(Level.SEVERE, "Could not rollback transaction", ex);
                }
            }
            LOGGER.log(Level.SEVERE, "Error saving order", e);
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                }  catch (SQLException e) {
                    LOGGER.log(Level.SEVERE, "Could not close connection", e);
                }
            }
        }
        return null;
    }

    //---------------------------------------------------------

    public List<Order> findByUserId(Long userId){
        String sql = "SELECT o.*, u.*, c.* FROM `Order` o " +
                "JOIN User u ON o.id_User = u.id_User " +
                "JOIN Client c ON o.id_Client = c.id_Client " +
                "WHERE o.id_User = ?";
        List<Order> orders = new ArrayList<>();

        try( Connection connect = dbConfig.getConnection();
             PreparedStatement statement = connect.prepareStatement(sql)){

            statement.setLong(1, userId);
            try (ResultSet rs = statement.executeQuery()){
                while (rs.next()) {
                    Order order = mapResultSetToOrder(rs);
                    order.setOrderLines(findOrderLinesByOrderId(order.getId()));
                    orders.add(order);
                }
            }
            LOGGER.log(Level.INFO, () ->"Found " + orders.size() + " orders for user: " + userId);
        } catch (SQLException e){
            LOGGER.log(Level.SEVERE, "Error finding orders by user ID", e);
        }
        return orders;
    }

    //--------------------------------------------------------------

    private List<OrderLine> findOrderLinesByOrderId(Long orderId) {
        String sql = "SELECT ol.*, c.* FROM OrderLine ol " +
                "JOIN Course c ON ol.id_Course = c.id_Course " +
                "WHERE ol.id_Order = ?";
        List<OrderLine> orderLines = new ArrayList<>();

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    OrderLine orderLine = new OrderLine();
                    orderLine.setId(rs.getLong("id_OrderLine"));
                    orderLine.setQuantity(rs.getInt("ol_quantity"));
                    orderLine.setUnitPrice(rs.getDouble("ol_unitPrice"));

                    Course course = ResultSetMapper.mapToCourse(rs);

                    orderLine.setCourse(course);
                    orderLines.add(orderLine);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding order lines", e);
        }
        return orderLines;
    }

    //------------------------------------------------------------------

    private Order mapResultSetToOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setId(rs.getLong("id_Order"));
        order.setOrderDate(rs.getTimestamp("o_orderDate"));
        order.setTotalAmount(rs.getDouble("o_totalAmount"));

        User user = new User();
        user.setId(rs.getLong("id_User"));
        user.setLogin(rs.getString("u_login"));
        order.setUser(user);

        Client client = new Client();
        client.setId(rs.getLong("id_Client"));
        client.setFirstName(rs.getString("cl_firstName"));
        client.setLastName(rs.getString("cl_lastName"));
        client.setEmail(rs.getString("cl_email"));
        order.setClient(client);

        return order;
    }


    @Override
    public Order update(Order entity) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    //-----------------------------------------------------------

    @Override
    public Order findById(Long id) {
        String sql = "SELECT o.*, u.*, c.* FROM `Order` o " +
                "JOIN User u ON o.id_User = u.id_User " +
                "JOIN Client c ON o.id_Client = c.id_Client " +
                "WHERE o.id_Order = ?";

        try (Connection connection = dbConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()){
                if (rs.next()){
                    Order order = mapResultSetToOrder(rs);
                    order.setOrderLines(findOrderLinesByOrderId(order.getId()));
                    return order;
                }
            }
        } catch (SQLException e){
            LOGGER.log(Level.SEVERE, "Error finding order by ID", e);
        }
        return null;
    }

    @Override
    public List<Order> findAll() {
        return List.of();
    }
}
