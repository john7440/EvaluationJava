package dao;

import config.DatabaseConfig;
import entity.Client;

import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Dao  for Client entity
 * Singleton pattern is used intentionally
 */
@SuppressWarnings("java:S6548")
public class ClientDao implements IDao<Client> {
    private static final Logger LOGGER = Logger.getLogger(ClientDao.class.getName());
    private final DatabaseConfig dbConfig;

    private ClientDao(){
        this.dbConfig = DatabaseConfig.getInstance();
    }

    private static class SingletonHolder{
        private static final ClientDao INSTANCE = new ClientDao();
    }

    public static ClientDao getInstance(){
        return ClientDao.SingletonHolder.INSTANCE;
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

    public Client findByEmail(String email){
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
    public Client update(Client entity) {
        return null;
    }

    @Override
    public Client save(Client client) {
        String sql = "INSERT INTO Client (cl_firstName, cl_lastName, cl_email, cl_address, cl_phoneNumber) VALUES (?, ?, ?, ?, ?)";

        try(Connection connection = dbConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            statement.setString(1, client.getFirstName());
            statement.setString(2, client.getLastName());
            statement.setString(3, client.getEmail());
            statement.setString(4, client.getAddress());
            statement.setString(5, client.getPhoneNumber());

            statement.executeUpdate();

            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    client.setId(rs.getLong(1));
                    LOGGER.log(Level.INFO, () ->"Client created with ID: " + client.getId());
                }
            }
            return client;
        } catch (SQLException e){
            LOGGER.log(Level.SEVERE, "Error saving client", e);
        }
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
    public List<Client> findAll() {
        return List.of();
    }
}
