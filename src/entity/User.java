package entity;

import java.util.ArrayList;
import java.util.List;

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

}
