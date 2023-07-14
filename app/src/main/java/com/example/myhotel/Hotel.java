package com.example.myhotel;

import java.io.Serializable;

public class Hotel implements Serializable {
    private String name;
    private String location;
    private String description;
    private double rating;
    private int roomCapacity;
    private double price;
    private boolean availability;
    private double latitude;
    private double longitude;


    public Hotel(String name, String location, String description, double rating, int roomCapacity, double price, boolean availability) {
        this.name = name;
        this.location = location;
        this.description = description;
        this.rating = rating;
        this.roomCapacity = roomCapacity;
        this.price = price;
        this.availability = availability;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public double getRating() {
        return rating;
    }

    public int getRoomCapacity() {
        return roomCapacity;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailability() {
        return availability;
    }

    public char[] getCapacity() {
        int capacity = 100;
        String capacityString = String.valueOf(capacity);
        char[] capacityChars = capacityString.toCharArray();
        return capacityChars;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
