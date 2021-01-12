package com.challenge.portfolio.vo;

import com.challenge.portfolio.service.Transaction;

import java.util.List;

public class RecommendedChange {

    private List<Transaction> transactionList;
    private AdvisorObject advisorObject;

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    public AdvisorObject getAdvisorObject() {
        return advisorObject;
    }

    public void setAdvisorObject(AdvisorObject advisorObject) {
        this.advisorObject = advisorObject;
    }
}
