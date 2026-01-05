package entity;

/**
 * Represents a training course
 */
public class Course {
    private Long id;
    private String name;
    private String description;
    private int duration;
    private String type;
    private double price;

    public  Course() {
    }

    public Course(String name, String description, int duration, String type, double price) {
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.type = type;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Course: " + name + "\nduration: " + duration + "min\ntype: " + type + "\nprice: " + price + "€";
    }
}
