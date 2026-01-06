package dao;

import config.DatabaseConfig;
import entity.Client;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
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

    private Client mapResultSetToClient(ResultSet rs) throws SQLException {
        Client client = new Client();
        client.setId(rs.getLong("id_Client"));
        client.setFirstName(rs.getString("cl_firstName"));
        client.setLastName(rs.getString("cl_lastName"));
        client.setEmail(rs.getString("cl_email"));
        client.setAddress(rs.getString("cl_address"));
        client.setPhoneNumber(rs.getString("cl_phoneNumber"));
        return client;
    }

    public Client findByEmail(String email) throws SQLException {
        String sql = "select c.* from client c where cl_email = ?";

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()){
                if (rs.next()) {
                    return mapResultSetToClient(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding client by email", e);
        }
        return null;
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
