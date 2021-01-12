package com.challenge.portfolio.service.impl;

import com.challenge.portfolio.provider.PortfolioProvider;
import com.challenge.portfolio.service.PortfolioAdvisorService;
import com.challenge.portfolio.service.Transaction;
import com.challenge.portfolio.vo.AdvisorObject;
import com.challenge.portfolio.vo.RecommendedChange;
import com.challenge.portfolio.vo.RequestAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PortfolioAdvisorServiceImpl implements PortfolioAdvisorService {

    private PortfolioProvider portfolioProvider;

    public PortfolioAdvisorServiceImpl(PortfolioProvider portfolioProvider){
        this.portfolioProvider = portfolioProvider;
    }

    /**
     *
     * @param risk preferense for the client
     * @return AdvisorObject based on risk
     */
    @Override
    public AdvisorObject getAdvisorObject(int risk){
        Integer[] portfolioArray = portfolioProvider.getRisk(risk);

        if(portfolioArray.length == 0){
            return new AdvisorObject();
        }
        AdvisorObject advisorObject = new AdvisorObject();
        advisorObject.setCategoryOne(portfolioArray[0]);
        advisorObject.setCategoryTwo(portfolioArray[1]);
        advisorObject.setCategoryThree(portfolioArray[2]);
        advisorObject.setCategoryFour(portfolioArray[3]);
        advisorObject.setCategoryFive(portfolioArray[4]);
        return advisorObject;
    }

    @Override
    public RecommendedChange getSuggestionAllocation(final RequestAdvisor requestAdvisor){
        Integer[] portfolioArray = portfolioProvider.getRisk(requestAdvisor.getCurrentRisk());

        if(portfolioArray.length == 0 || !isValidData(requestAdvisor) ){
            return new RecommendedChange();
        }
        double[] ideal = getIdealAmount(requestAdvisor, portfolioArray);
        List<Transaction> transactionsNeeded = getPortfolioRelocations(getPortfolioAmounts(requestAdvisor), ideal);
        return getRecommendedChange(transactionsNeeded, ideal);
    }

    protected RecommendedChange getRecommendedChange(List<Transaction> transactionsNeeded, double[] ideal ){
        AdvisorObject advisorObject = new AdvisorObject();
        advisorObject.setCategoryOne(ideal[0]);
        advisorObject.setCategoryTwo(ideal[1]);
        advisorObject.setCategoryThree(ideal[2]);
        advisorObject.setCategoryFour(ideal[3]);
        advisorObject.setCategoryFive(ideal[4]);
        RecommendedChange recommendedChange = new RecommendedChange();
        recommendedChange.setTransactionList(transactionsNeeded);
        recommendedChange.setAdvisorObject(advisorObject);
        return recommendedChange;
    }

    protected boolean isValidData(final RequestAdvisor requestAdvisor){
        return requestAdvisor.getCategoryOne() <=0 &&
        requestAdvisor.getCategoryTwo() <=0 &&
        requestAdvisor.getCategoryThree() <=0 &&
        requestAdvisor.getCategoryFour() <=0 &&
        requestAdvisor.getCategoryFive() <=0 ? false : true;
    }

    protected final double[] getIdealAmount(final RequestAdvisor requestAdvisor, final Integer[] portfolioArray){
        double totalBudget = requestAdvisor.getCategoryOne() +
                requestAdvisor.getCategoryTwo() +
                requestAdvisor.getCategoryThree() +
                requestAdvisor.getCategoryFour() +
                requestAdvisor.getCategoryFive();
        double[] ideal = new double[5];
        for(int idx=0; idx < 5; idx++) {
            ideal[idx] = totalBudget * portfolioArray[idx] / 100;
        }
        return ideal;
    }

    protected final double[] getPortfolioAmounts(final RequestAdvisor requestAdvisor){
        double[] doubles = new double[5];
        doubles[0] = requestAdvisor.getCategoryOne();
        doubles[1] = requestAdvisor.getCategoryTwo();
        doubles[2] = requestAdvisor.getCategoryThree();
        doubles[3] = requestAdvisor.getCategoryFour();
        doubles[4] = requestAdvisor.getCategoryFive();
        return doubles;
    }

    public List<Transaction> getPortfolioRelocations(double[] portfolioAmounts, double[] idealAmounts) {

        List<Transaction> relocations = new ArrayList<>();

        double[] diff = new double[portfolioAmounts.length];
        for (int i = 0; i < portfolioAmounts.length; i++) {
            diff[i] = portfolioAmounts[i] - idealAmounts[i];
        }

        int idx1 = 0;
        int idx2 = 1;

        while (idx2 < portfolioAmounts.length) {
            if (diff[idx1] < 0) {
                idx2 = findPositiveDiffIndex(diff, idx2);
                double amount = Math.min(diff[idx1] * -1, diff[idx2]);
                relocations.add(new Transaction(idx2, idx1, amount));
                diff[idx1] = roundAmount(diff[idx1] + amount);
                diff[idx2] = roundAmount(diff[idx2] - amount);
            } else if (diff[idx1] > 0) {
                idx2 = findNegativeDiffIndex(diff, idx2);
                double amount = Math.min(diff[idx1], diff[idx2] * -1);
                relocations.add(new Transaction(idx1, idx2, amount));
                diff[idx1] = roundAmount(diff[idx1] - amount);
                diff[idx2] = roundAmount(diff[idx2] + amount);
            }
            if (diff[idx1] == 0) {
                idx1++;
                if (idx1 == idx2) {
                    idx2++;
                }
            }
        }
        return relocations;
    }

    protected final int findPositiveDiffIndex(double[] diff, int idx) {
        while (idx < diff.length && diff[idx] <= 0) {
            idx++;
        }
        return idx;
    }

    protected final int findNegativeDiffIndex(double[] diff, int idx) {
        while (idx < diff.length && diff[idx] >= 0) {
            idx++;
        }
        return idx;
    }

    protected final double roundAmount(double amount) {
        return Math.round(amount * 10000d) / 10000d;
    }



}
