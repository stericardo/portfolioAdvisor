package com.challenge.portfolio.controller;

import com.challenge.portfolio.config.BaseTest;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

class HealthControllerTest extends BaseTest {

    @Test
    public void test() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/health/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
        MvcResult result = resultActions.andReturn();
        char[] contentAsChar= result.getResponse().getContentAsString().trim().toCharArray();
        char[] expected = "HealthCheck!".toCharArray();
        Assert.assertEquals( true, Arrays.equals(contentAsChar, expected));
    }

}