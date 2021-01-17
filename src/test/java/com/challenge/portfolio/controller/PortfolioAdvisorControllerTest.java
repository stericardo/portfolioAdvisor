package com.challenge.portfolio.controller;

import com.challenge.portfolio.config.BaseTest;
import com.challenge.portfolio.service.PortfolioAdvisorService;
import com.challenge.portfolio.service.Transaction;
import com.challenge.portfolio.vo.RecommendedChange;
import com.challenge.portfolio.vo.RequestAdvisor;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

class PortfolioAdvisorControllerTest extends BaseTest {

    @Autowired
    PortfolioAdvisorService portfolioAdvisorService;

    @Test
    public void getRecommendation() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/getRecommendationRisk/").param("risk", "2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        double[] advisorObjectResponse = objectMapper.readValue(contentAsString, double[].class);
        double[] advisorObjectExpected = new double[]{0.70, 0.15, 0.15, 0, 0, 0};
        validateAmounts(advisorObjectExpected, advisorObjectResponse);
    }

    @Test
    public void getRecommendationRiskNotFoundException() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/getRecommendationRisk/").param("risk", "20")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        Map advisorObjectResponse = objectMapper.readValue(contentAsString, Map.class);
        Assert.assertEquals(true, "Risk Not Found".equals(advisorObjectResponse.get("message")));
    }


    @Test
    public void getRecommendationChange() throws Exception {
        RequestAdvisor requestAdvisor = getRequestAdvisor(2, new double[]{ 100, 250, 30, 20, 10});
        String jsonBody = objectMapper.writeValueAsString(requestAdvisor);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/getSuggestionAllocation/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andDo(print());
        MvcResult result = resultActions.andReturn();
        double[] expectedAmounts = new double[]{287.0, 61.5,61.5,0.0,0.0};
        String contentAsString = result.getResponse().getContentAsString();
        RecommendedChange recommendedChange = objectMapper.readValue(contentAsString, RecommendedChange.class);
        validateAmounts(expectedAmounts, recommendedChange.getAdvisorAmounts());
    }

    @Test
    public void getRecommendationChangeInvalidAmountsException() throws Exception {
        RequestAdvisor requestAdvisor = getRequestAdvisor(2, new double[]{ 0, 0, 0, 0, 0});
        String jsonBody = objectMapper.writeValueAsString(requestAdvisor);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/getSuggestionAllocation/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andDo(print());
        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        Map advisorObjectResponse = objectMapper.readValue(contentAsString, Map.class);
        Assert.assertEquals(true, "Not valid Amounts".equals(advisorObjectResponse.get("message")));
    }

    private RequestAdvisor getRequestAdvisor(int risk, double[] values) {
        RequestAdvisor requestAdvisor = new RequestAdvisor();
        requestAdvisor.setCurrentRisk(risk);
        requestAdvisor.setPorfolioAmounts(values);
        return requestAdvisor;
    }

    private void validateAmounts(double[] advisorObjectExpected, double[] advisorObjectResponse){
        Assert.assertEquals(0, Double.compare(advisorObjectExpected[0], advisorObjectResponse[0]));
        Assert.assertEquals(0, Double.compare(advisorObjectExpected[1], advisorObjectResponse[1]));
        Assert.assertEquals(0, Double.compare(advisorObjectExpected[2], advisorObjectResponse[2]));
        Assert.assertEquals(0, Double.compare(advisorObjectExpected[3], advisorObjectResponse[3]));
        Assert.assertEquals(0, Double.compare(advisorObjectExpected[4], advisorObjectResponse[4]));
    }

}