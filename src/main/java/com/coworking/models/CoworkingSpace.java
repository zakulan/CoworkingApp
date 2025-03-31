package com.coworking.models;

import java.io.Serializable;
import java.util.Objects;

public class CoworkingSpace implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String type;
    private double price;

    public CoworkingSpace(Long id, String type, double price) {
        this.id = id;
        this.type = type;
        this.price = price;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoworkingSpace that = (CoworkingSpace) o;
        return Double.compare(that.price, price) == 0 &&
                Objects.equals(id, that.id) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, price);
    }

    @Override
    public String toString() {
        return "CoworkingSpace{id=" + id + ", type='" + type + "', price=" + price + "}";
    }
}