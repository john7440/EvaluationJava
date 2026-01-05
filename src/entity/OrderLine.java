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

}
