package com.challenge.portfolio.vo;

import java.util.Objects;

public class RequestAdvisor extends AdvisorObject{

    private int currentRisk;

    public int getCurrentRisk() {
        return currentRisk;
    }

    public void setCurrentRisk(int currentRisk) {
        this.currentRisk = currentRisk;
    }
}
