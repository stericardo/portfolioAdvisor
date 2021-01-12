package com.challenge.portfolio.service;

public class Transaction {

    private int from;
    private int to;
    private double amount;

    public Transaction(int from, int to, double amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }
}
