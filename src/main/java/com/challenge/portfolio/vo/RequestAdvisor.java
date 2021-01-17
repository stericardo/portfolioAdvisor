package com.challenge.portfolio.vo;

public class RequestAdvisor {

    private int currentRisk;
    private double[] porfolioAmounts;

    public int getCurrentRisk() {
        return currentRisk;
    }

    public void setCurrentRisk(int currentRisk) {
        this.currentRisk = currentRisk;
    }

    public double[] getPorfolioAmounts() {
        return porfolioAmounts;
    }

    public void setPorfolioAmounts(double[] porfolioAmounts) {
        this.porfolioAmounts = porfolioAmounts;
    }
}
