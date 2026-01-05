package entity;

public class CartLine {
    private Long id;
    private int quantity;
    private Cart cart;
    private Course course;


    public CartLine() {
        this.quantity = 1;
    }
}
