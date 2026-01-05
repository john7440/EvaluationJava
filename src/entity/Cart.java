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

}
