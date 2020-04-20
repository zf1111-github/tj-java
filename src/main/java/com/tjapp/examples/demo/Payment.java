package com.tjapp.examples.demo;

public class Payment {

    private String Id;

    private double amount;

    public String getId() {
        return Id;
    }

    public Payment setId(String id) {
        Id = id;
        return this;
    }

    public double getAmount() {
        return amount;
    }

    public Payment setAmount(double amount) {
        this.amount = amount;
        return this;
    }
}
