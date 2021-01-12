package com.challenge.portfolio.controller;

import com.challenge.portfolio.config.BaseTest;
import com.challenge.portfolio.service.PortfolioAdvisorService;
import com.challenge.portfolio.vo.AdvisorObject;
import com.challenge.portfolio.vo.RecommendedChange;
import com.challenge.portfolio.vo.RequestAdvisor;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

class PortfolioAdvisorControllerTest extends BaseTest {

    @Autowired
    PortfolioAdvisorService portfolioAdvisorService;

    @Test
    public void getRecommendationChange() throws Exception {
        RequestAdvisor requestAdvisor = getRequestAdvisor(new double[]{2, 100, 250, 30, 20, 10});
        String jsonBody = objectMapper.writeValueAsString(requestAdvisor);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/getSuggestionAllocation/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andDo(print());
        MvcResult result = resultActions.andReturn();
        RecommendedChange recommendedChangeExpected = getRecommendedChanged();
        String contentAsString = result.getResponse().getContentAsString();
        RecommendedChange recommendedChange = objectMapper.readValue(contentAsString, RecommendedChange.class);
        Assert.assertEquals(true, recommendedChangeExpected.getAdvisorObject().equals(recommendedChange.getAdvisorObject()));
    }

    private AdvisorObject getAdvisorObject(double[] values) {
        AdvisorObject requestAdvisor = new AdvisorObject();
        requestAdvisor.setCategoryOne(values[0]);
        requestAdvisor.setCategoryTwo(values[1]);
        requestAdvisor.setCategoryThree(values[2]);
        requestAdvisor.setCategoryFour(values[3]);
        requestAdvisor.setCategoryFive(values[4]);
        return requestAdvisor;
    }

    private RequestAdvisor getRequestAdvisor(double[] values) {
        RequestAdvisor requestAdvisor = new RequestAdvisor();
        requestAdvisor.setCurrentRisk((int) values[0]);
        requestAdvisor.setCategoryOne(values[1]);
        requestAdvisor.setCategoryTwo(values[2]);
        requestAdvisor.setCategoryThree(values[3]);
        requestAdvisor.setCategoryFour(values[4]);
        requestAdvisor.setCategoryFive(values[5]);
        return requestAdvisor;
    }

    private RecommendedChange getRecommendedChanged() throws Exception {
        String contentExpected = "{\"transactionList\":[{\"from\":1,\"to\":0,\"amount\":187.0}," +
                "{\"from\":1,\"to\":2,\"amount\":1.5},{\"from\":3,\"to\":2,\"amount\":20.0}," +
                "{\"from\":4,\"to\":2,\"amount\":10.0}],\"advisorObject\":" +
                "{\"categoryOne\":287.0,\"categoryTwo\":61.5,\"categoryThree\":61.5,\"categoryFour\":0.0,\"categoryFive\":0.0}}";
        return objectMapper.readValue(contentExpected, RecommendedChange.class);
    }

}