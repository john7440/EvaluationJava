package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user account
 */
public class User {
    private Long id;
    private String login;
    private String password;
    private Cart cart;
    private List<Order> orders;

    public User() {
        this.orders = new ArrayList<>();
    }

    public User(String login, String password) {
        this();
        this.login = login;
        this.password = password;
    }

    // Getter and setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void addOrder(Order order){
        this.orders.add(order);
        order.setUser(this);
    }

    @Override
    public String toString() {
        return "User Id: " + id + "\n" + "Login: " + login;
    }

}
