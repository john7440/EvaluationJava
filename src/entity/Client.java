package entity;

import java.util.ArrayList;
import java.util.List;

public class Client {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String phoneNumber;
    private List<Order> orders;

    public Client(){
        this.orders = new ArrayList<>();
    }

    public Client(String firstName, String lastName, String email, String address, String phoneNumber) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}
