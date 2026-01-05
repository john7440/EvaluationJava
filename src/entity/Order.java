package entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private Long id;
    private Date orderDate;
    private double totalAmount;
    private User user;
    private Client client;
    private List<OrderLine> orderLines;

    public Order() {
        this.orderDate = new Date();
        this.orderLines = new ArrayList<>();
    }

    public Order(User user, Client client) {
        this();
        this.user = user;
        this.client = client;
    }

    //Getter and setter
    
}
