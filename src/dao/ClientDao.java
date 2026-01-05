package dao;

import config.DatabaseConfig;
import entity.Client;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

/**
 * Dao  for Client entity
 */
public class ClientDao implements IDao<Client> {
    private static final Logger LOGGER = Logger.getLogger(ClientDao.class.getName());
    private static ClientDao instance;
    private DatabaseConfig dbConfig;

    private ClientDao() throws IOException, ClassNotFoundException {
        this.dbConfig = DatabaseConfig.getInstance();
    }

    public static ClientDao getInstance() throws IOException, ClassNotFoundException {
        if (instance == null) {
            instance = new ClientDao();
        }
        return instance;
    }
    
    @Override
    public Client save(Client entity) throws SQLException {
        return null;
    }

    @Override
    public Client update(Client entity) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public Client findById(Long id) {
        return null;
    }

    @Override
    public List<Client> findAll() throws SQLException {
        return List.of();
    }
}
