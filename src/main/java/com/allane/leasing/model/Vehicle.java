package com.allane.leasing.model;

import javax.persistence.*;

@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private long vehicleId;

    private String brand;
    private String model;
    private String vin = "";
    private int year;
    private double price;

    public Vehicle() {
    }

    public Vehicle(long vehicleId, String brand, String model, String vin, int year, double price) {
        this.vehicleId = vehicleId;
        this.brand = brand;
        this.model = model;
        this.vin = vin;
        this.year = year;
        this.price = price;
    }

    public long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
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
