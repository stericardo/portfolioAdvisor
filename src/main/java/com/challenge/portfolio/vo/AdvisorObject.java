package com.challenge.portfolio.vo;

import java.util.Objects;

public class AdvisorObject {

    private double categoryOne;
    private double categoryTwo;
    private double categoryThree;
    private double categoryFour;
    private double categoryFive;

    public double getCategoryOne() {
        return categoryOne;
    }

    public void setCategoryOne(double categoryOne) {
        this.categoryOne = categoryOne;
    }

    public double getCategoryTwo() {
        return categoryTwo;
    }

    public void setCategoryTwo(double categoryTwo) {
        this.categoryTwo = categoryTwo;
    }

    public double getCategoryThree() {
        return categoryThree;
    }

    public void setCategoryThree(double categoryThree) {
        this.categoryThree = categoryThree;
    }

    public double getCategoryFour() {
        return categoryFour;
    }

    public void setCategoryFour(double categoryFour) {
        this.categoryFour = categoryFour;
    }

    public double getCategoryFive() {
        return categoryFive;
    }

    public void setCategoryFive(double categoryFive) {
        this.categoryFive = categoryFive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdvisorObject that = (AdvisorObject) o;
        return Double.compare(that.categoryOne, categoryOne) == 0 &&
                Double.compare(that.categoryTwo, categoryTwo) == 0 &&
                Double.compare(that.categoryThree, categoryThree) == 0 &&
                Double.compare(that.categoryFour, categoryFour) == 0 &&
                Double.compare(that.categoryFive, categoryFive) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryOne, categoryTwo, categoryThree, categoryFour, categoryFive);
    }
}
