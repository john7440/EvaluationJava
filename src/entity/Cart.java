package entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Cart {
    private Long id;
    private Date createdDate;
    private User user;
    private List<CartLine> cartLines;

    public Cart() {
        this.createdDate = new Date();
        this.cartLines = new ArrayList<>();
    }

    public Cart(User user) {
        this();
        this.user = user;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartLine> getCartLines() {
        return cartLines;
    }

    public void setCartLines(List<CartLine> cartLines) {
        this.cartLines = cartLines;
    }
}
