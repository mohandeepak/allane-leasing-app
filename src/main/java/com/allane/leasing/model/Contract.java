package com.allane.leasing.model;

import javax.persistence.*;

@Table
@Entity
public class Contract {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    @Column(name = "contract_id")
    private long contractId;
    private int contractNumber;
    private double monthlyRate;

    @ManyToOne
    private Customer customer;

    @OneToOne
    private Vehicle vehicle;

    public Contract() {
    }

    public Contract(long contractId, int contractNumber, double monthlyRate, Customer customer, Vehicle vehicle) {
        this.contractId = contractId;
        this.contractNumber = contractNumber;
        this.monthlyRate = monthlyRate;
        this.customer = customer;
        this.vehicle = vehicle;
    }

    public long getContractId() {
        return contractId;
    }

    public void setContractId(long contractId) {
        this.contractId = contractId;
    }

    public int getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(int contractNumber) {
        this.contractNumber = contractNumber;
    }

    public double getMonthlyRate() {
        return monthlyRate;
    }

    public void setMonthlyRate(double monthlyRate) {
        this.monthlyRate = monthlyRate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
