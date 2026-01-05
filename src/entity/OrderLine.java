package entity;

public class OrderLine {
    private Long id;
    private int quantity;
    private double unitPrice;
    private Order order;
    private Course course;

    public OrderLine(){
        this.quantity = 1;
    }

    public OrderLine(Order order, Course course, int quantity, double unitPrice) {
        this.order = order;
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
