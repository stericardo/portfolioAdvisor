package com.challenge.portfolio.vo;

import com.challenge.portfolio.service.Transaction;

import java.util.List;

public class RecommendedChange {

    private List<Transaction> transactionList;
    private double[] advisorAmounts;

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    public double[] getAdvisorAmounts() {
        return advisorAmounts;
    }

    public void setAdvisorAmounts(double[] advisorAmounts) {
        this.advisorAmounts = advisorAmounts;
    }
}
