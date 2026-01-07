package dao;

import entity.*;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Utility class for mapping ResultSet to entities
 */
public final class ResultSetMapper {

    private ResultSetMapper() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Map ResultSet to Course object
     * @param rs ResultSet containing course data
     * @return Course object
     * @throws SQLException if database access error occurs
     */
    public static Course mapToCourse(ResultSet rs) throws SQLException {
        Course course = new Course();
        course.setId(rs.getLong("id_Course"));
        course.setName(rs.getString("co_name"));
        course.setDescription(rs.getString("co_description"));
        course.setDuration(rs.getInt("co_duration"));
        course.setType(rs.getString("co_type"));
        course.setPrice(rs.getDouble("co_price"));
        return course;
    }

    /**
     * Map ResultSet to User object
     */
    public static User mapToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id_User"));
        user.setLogin(rs.getString("u_login"));
        user.setPassword(rs.getString("u_password"));
        return user;
    }

    /**
     * Map ResultSet to Client object
     */
    public static Client mapToClient(ResultSet rs) throws SQLException {
        Client client = new Client();
        client.setId(rs.getLong("id_Client"));
        client.setFirstName(rs.getString("cl_firstName"));
        client.setLastName(rs.getString("cl_lastName"));
        client.setEmail(rs.getString("cl_email"));
        client.setAddress(rs.getString("cl_address"));
        client.setPhoneNumber(rs.getString("cl_phoneNumber"));
        return client;
    }

    /**
     * Map ResultSet to Cart object
     */
    public static Cart mapToCart(ResultSet rs) throws SQLException {
        Cart cart = new Cart();
        cart.setId(rs.getLong("id_Cart"));
        cart.setCreatedDate(rs.getTimestamp("ca_createdDate"));
        return cart;
    }

    /**
     * Map ResultSet to Order object with User and Client
     */
    public static Order mapToOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setId(rs.getLong("id_Order"));
        order.setOrderDate(rs.getTimestamp("o_orderDate"));
        order.setTotalAmount(rs.getDouble("o_totalAmount"));

        //user
        User user = new User();
        user.setId(rs.getLong("id_User"));
        user.setLogin(rs.getString("u_login"));
        order.setUser(user);

        //client
        Client client = new Client();
        client.setId(rs.getLong("id_Client"));
        client.setFirstName(rs.getString("cl_firstName"));
        client.setLastName(rs.getString("cl_lastName"));
        client.setEmail(rs.getString("cl_email"));
        order.setClient(client);

        return order;
    }

}
