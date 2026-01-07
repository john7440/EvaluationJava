package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a client for whom orders are placed
 */
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @SuppressWarnings("unused")
    public List<Order> getOrders() {
        return orders;
    }

    @SuppressWarnings("unused")
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Client:" + firstName + " " + lastName + "\nemail: " + email + "\naddress: " + address + "\nphone:  " + phoneNumber;
    }
}
