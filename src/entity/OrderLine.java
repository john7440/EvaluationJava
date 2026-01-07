package entity;

/**
 * Represents a line in an order
 */
public class OrderLine {
    private Long id;
    private int quantity;
    private double unitPrice;
    private Course course;

    public OrderLine(){
        this.quantity = 1;
    }

    public OrderLine(Course course, int quantity, double unitPrice) {
        this.course = course;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    //Getter and setter
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

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "Order line n°" + id + ":\nquantity: " + quantity + "\nunitPrice: " + unitPrice;
    }
}
