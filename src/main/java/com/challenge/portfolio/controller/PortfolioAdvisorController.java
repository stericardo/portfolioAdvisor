package com.challenge.portfolio.controller;

import com.challenge.portfolio.exceptions.InvalidAmountsException;
import com.challenge.portfolio.exceptions.RiskNotFoundException;
import com.challenge.portfolio.service.PortfolioAdvisorService;
import com.challenge.portfolio.vo.RecommendedChange;
import com.challenge.portfolio.vo.RequestAdvisor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PortfolioAdvisorController {

    @Autowired
    private PortfolioAdvisorService portfolioAdvisorService;

    @GetMapping("/getRecommendationRisk")
    @Operation(
            summary = "Get Risk",
            description = "Get Risk - Recommendation Portfolio based on client preference",
            responses = {
                    @ApiResponse(responseCode = "403",
                            description = "Forbidden resource",
                            content = {@Content(mediaType = "application/json")}),
                    @ApiResponse(responseCode = "200", description = "Success",
                            content = {@Content(mediaType = "application/json")})
            })
    public ResponseEntity<double[]> getRecommendation(int risk) {
        return new ResponseEntity<>(portfolioAdvisorService.getAdvisorObject(risk), HttpStatus.OK);
    }

    @PostMapping("/getSuggestionAllocation")
    @Operation(
            summary = "Get Risk",
            description = "Get Risk - Recommendation Portfolio based on client preference",
            responses = {
                    @ApiResponse(responseCode = "403",
                            description = "Forbidden resource",
                            content = {@Content(mediaType = "application/json")}),
                    @ApiResponse(responseCode = "200", description = "Success",
                            content = {@Content(mediaType = "application/json")})
            })
    public ResponseEntity<RecommendedChange> getRecommendationChange(@RequestBody RequestAdvisor requestAdvisor) {
        if(isValidData(requestAdvisor)) {
            return new ResponseEntity<>(portfolioAdvisorService.getSuggestionAllocation(requestAdvisor), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    protected boolean isValidData(final RequestAdvisor requestAdvisor){
        if( requestAdvisor.getPorfolioAmounts()[0] <=0 &&
                requestAdvisor.getPorfolioAmounts()[1] <=0 &&
                requestAdvisor.getPorfolioAmounts()[2] <=0 &&
                requestAdvisor.getPorfolioAmounts()[3] <=0 &&
                requestAdvisor.getPorfolioAmounts()[4] <=0 ){
            throw new InvalidAmountsException("Controller - Portfolio risk= " + requestAdvisor.getCurrentRisk());
        }
        return true;
    }

}
