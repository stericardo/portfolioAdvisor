package com.challenge.portfolio.controller;

import com.challenge.portfolio.service.PortfolioAdvisorService;
import com.challenge.portfolio.vo.AdvisorObject;
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
    public ResponseEntity<AdvisorObject> getRecommendation(int risk) {
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
        return new ResponseEntity<>(portfolioAdvisorService.getSuggestionAllocation(requestAdvisor), HttpStatus.OK);
    }

}
