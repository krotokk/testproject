package testproject;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by kirill on 02.05.17.
 */

@Entity
public class carEntity {
    @Id
    private String id;
    private String model;
    private String color;
    private int year;
    private boolean enable;
    private double price;

    protected carEntity(){}

    public carEntity(String id, int year, String model, String color, double price) {
        this.id = id;
        this.model = model;
        this.color = color;
        this.year = year;
        this.price = price;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
