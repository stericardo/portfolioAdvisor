package com.challenge.portfolio.provider;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class PortfolioProvider {

    private Map<Integer, Integer[]> portfolioData;

    @PostConstruct
    public void initData(){
        portfolioData = new HashMap<>();
        portfolioData.put(1,new Integer[]{80,20,0,0,0});
        portfolioData.put(2,new Integer[]{70,15,15,0,0});
        portfolioData.put(3,new Integer[]{60,15,15,10,0});
        portfolioData.put(4,new Integer[]{50,20,20,10,0});
        portfolioData.put(5,new Integer[]{40,20,20,20,0});
        portfolioData.put(6,new Integer[]{35,25,5,30,5});
        portfolioData.put(7,new Integer[]{20,25,25,25,5});
        portfolioData.put(8,new Integer[]{10,20,40,20,10});
        portfolioData.put(9,new Integer[]{5,15,40,25,15});
        portfolioData.put(10,new Integer[]{0,5,25,30,40});
    }

    public Integer[] getRisk(int risk){
        if(portfolioData.containsKey(risk)){
            return portfolioData.get(risk);
        }
        return new Integer[0];
    }

}
