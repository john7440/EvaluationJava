package dao;

import config.DatabaseConfig;
import entity.Order;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

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
    public Order save(Order entity) throws SQLException {
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
