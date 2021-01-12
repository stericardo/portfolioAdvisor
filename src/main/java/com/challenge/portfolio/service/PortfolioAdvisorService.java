package com.challenge.portfolio.service;

import com.challenge.portfolio.vo.AdvisorObject;
import com.challenge.portfolio.vo.RecommendedChange;
import com.challenge.portfolio.vo.RequestAdvisor;

public interface PortfolioAdvisorService {

    /**
     *
     * @param risk preferense for the client
     * @return AdvisorObject based on risk
     */
    AdvisorObject getAdvisorObject(int risk);

    /**
     *
     * @param requestAdvisor with allocation money
     * @return RecommendedChange with all steps
     */
    RecommendedChange getSuggestionAllocation(RequestAdvisor requestAdvisor);

}
