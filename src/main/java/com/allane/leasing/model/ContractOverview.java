package com.allane.leasing.model;

public class ContractOverview {

    private int contractNumber;
    private String firstName;
    private String lastName;
    private String brand;
    private String model;
    private int year;
    private String vin;
    private double monthlyRate;
    private double price;

    public ContractOverview() {
    }

    public ContractOverview(int contractNumber, String firstName, String lastName, String brand, String model, int year, String vin, double monthlyRate, double price) {
        this.contractNumber = contractNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.vin = vin;
        this.monthlyRate = monthlyRate;
        this.price = price;
    }

    public int getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(int contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public double getMonthlyRate() {
        return monthlyRate;
    }

    public void setMonthlyRate(double monthlyRate) {
        this.monthlyRate = monthlyRate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
