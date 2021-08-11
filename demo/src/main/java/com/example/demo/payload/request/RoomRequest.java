package com.example.demo.payload.request;

public class RoomRequest {

    private double area;
    private double price;
    private String type;
    private String name;
    private String description;
    private int capacity;
    private String utilities;


    public RoomRequest() {
    }

    public RoomRequest(double area, double price, String type, String name, String description, int capacity, String utilities) {
        this.area = area;
        this.price = price;
        this.type = type;
        this.name = name;
        this.description = description;
        this.capacity = capacity;
        this.utilities = utilities;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getUtilities() {
        return utilities;
    }

    public void setUtilities(String utilities) {
        this.utilities = utilities;
    }
}
