package dao;

import config.DatabaseConfig;
import entity.Cart;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

/**
 * Dao for Cart entity
 */
public class CartDao implements IDao<Cart> {
    private static final Logger LOGGER = Logger.getLogger(CartDao.class.getName());
    private static CartDao instance;
    private DatabaseConfig dbConfig;

    private CartDao() throws IOException, ClassNotFoundException {
        this.dbConfig = DatabaseConfig.getInstance();
    }

    public static CartDao getInstance() throws IOException, ClassNotFoundException {
        if (instance == null) {
            instance = new CartDao();
        }
        return instance;
    }

    @Override
    public Cart save(Cart entity) throws SQLException {
        return null;
    }

    @Override
    public Cart update(Cart entity) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public Cart findById(Long id) {
        return null;
    }

    @Override
    public List<Cart> findAll() throws SQLException {
        return List.of();
    }
}
