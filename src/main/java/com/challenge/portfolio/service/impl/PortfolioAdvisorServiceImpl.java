package com.challenge.portfolio.service.impl;

import com.challenge.portfolio.exceptions.RiskNotFoundException;
import com.challenge.portfolio.provider.PortfolioProvider;
import com.challenge.portfolio.service.PortfolioAdvisorService;
import com.challenge.portfolio.service.Transaction;
import com.challenge.portfolio.vo.RecommendedChange;
import com.challenge.portfolio.vo.RequestAdvisor;
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
    public double[] getAdvisorObject(int risk){
        double[] portfolioArray = portfolioProvider.getRisk(risk);

        if(portfolioArray.length == 0){
            throw new RiskNotFoundException("PortfolioAdvisorService - Risk requested = " + risk);
        }
        return portfolioArray;
    }

    @Override
    public RecommendedChange getSuggestionAllocation(final RequestAdvisor requestAdvisor){
        double[] portfolioArray = portfolioProvider.getRisk(requestAdvisor.getCurrentRisk());

        if( portfolioArray.length == 0 ){
            throw new RiskNotFoundException("PortfolioAdvisorService - Client Risk = " + requestAdvisor.getCurrentRisk());
        }
        double[] ideal = getIdealAmount(requestAdvisor, portfolioArray);
        List<Transaction> transactionsNeeded = getPortfolioRelocations(requestAdvisor.getPorfolioAmounts(), ideal);
        return getRecommendedChange(transactionsNeeded, ideal);
    }

    protected RecommendedChange getRecommendedChange(List<Transaction> transactionsNeeded, double[] ideal ){
        double[] advisorAmounts = new double[5];
        RecommendedChange recommendedChange = new RecommendedChange();
        recommendedChange.setTransactionList(transactionsNeeded);
        recommendedChange.setAdvisorAmounts(ideal);
        return recommendedChange;
    }

    protected final double[] getIdealAmount(final RequestAdvisor requestAdvisor, final double[] portfolioArray){
        double totalBudget = 0;
        for(int idx=0; idx < requestAdvisor.getPorfolioAmounts().length; idx++){
            totalBudget += requestAdvisor.getPorfolioAmounts()[idx];
        }

        double[] ideal = new double[5];
        for(int idx=0; idx < 5; idx++) {
            ideal[idx] = totalBudget * portfolioArray[idx] ;
        }
        return ideal;
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
