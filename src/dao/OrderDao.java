package dao;

import config.DatabaseConfig;
import entity.Order;
import entity.OrderLine;

import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Dao for Order entity
 */
public class OrderDao implements IDao<Order>{
    private static final Logger LOGGER = Logger.getLogger(OrderDao.class.getName());
    private static OrderDao instance;
    private DatabaseConfig dbConfig;

    private OrderDao() throws IOException, ClassNotFoundException {
        this.dbConfig = DatabaseConfig.getInstance();
    }

    public static OrderDao getInstance() throws IOException, ClassNotFoundException {
        if (instance == null) {
            instance = new OrderDao();
        }
        return instance;
    }

    @Override
    public Order save(Order order) throws SQLException {
        Connection connection = null;
        try{
            connection = dbConfig.getConnection();
            connection.setAutoCommit(false);

            //Order
            String orderSql = "INSERT INTO order (o_orderDate, o_totalAmount, id_User, id_Client) VALUES (?, ?, ?, ?)";
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
                    statement.executeUpdate();

                    try (ResultSet rs = statement.getGeneratedKeys()) {
                        if (rs.next()) {
                            ol.setId(rs.getLong(1));
                        }
                    }
                }
            }
            connection.commit();
            LOGGER.log(Level.INFO, "Order saved with ID: " + order.getId());
            return order;
        }  catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
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

    @Override
    public Order update(Order entity) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public Order findById(Long id) {
        return null;
    }

    @Override
    public List<Order> findAll() throws SQLException {
        return List.of();
    }
}
