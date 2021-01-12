package com.challenge.portfolio.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

    @GetMapping()
    @Operation(
            summary = "HealthCheck",
            description = "Health Check - Microservice",
            responses = {
                    @ApiResponse(responseCode = "403",
                            description = "Forbidden resource",
                            content = {@Content(mediaType = "application/json")}),
                    @ApiResponse(responseCode = "200", description = "Success",
                            content = {@Content(mediaType = "application/json")})
            })
    public ResponseEntity<String> health() {
        return new ResponseEntity<>("HealthCheck! ", HttpStatus.OK);
    }

}
