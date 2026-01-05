package entity;

/**
 * Represents a line in a shopping cart
 */
public class CartLine {
    private Long id;
    private int quantity;
    private Cart cart;
    private Course course;


    public CartLine() {
        this.quantity = 1;
    }

    public CartLine(Cart cart, Course course, int quantity) {
        this.cart = cart;
        this.course = course;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString(){
        return "Cartline n°" + id + ":\nquantity: " + quantity +"\ncourse: " + course;
    }
}
