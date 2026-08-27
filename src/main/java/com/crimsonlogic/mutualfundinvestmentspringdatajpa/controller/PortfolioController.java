package com.crimsonlogic.mutualfundinvestmentspringdatajpa.controller;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.response.PortfolioResponse;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.portfolio.Portfolio;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.portfolio.I_PortfolioService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * REST controller that exposes investor portfolio information.
 */
@RestController
@RequestMapping("/api/portfolios")
public class PortfolioController {

    private final I_PortfolioService portfolioService;


    /**
     * Creates this controller with the dependencies required to handle its HTTP operations.
     *
     * @param portfolioService value supplied to this endpoint
     */
    public PortfolioController(
            I_PortfolioService portfolioService) {

        this.portfolioService =
                portfolioService;
    }



    /**
     * Returns an investor portfolio together with its calculated current value.
     *
     * @param investorId value supplied to this endpoint
     * @return HTTP response or data produced by the endpoint
     */
    @GetMapping("/investor/{investorId}")
    public ResponseEntity<PortfolioResponse>
    getPortfolio(
            @PathVariable String investorId) {

        Portfolio portfolio =
                portfolioService.getPortfolio(
                        investorId
                );


        double currentValue =
                portfolioService
                        .calculatePortfolioValue(
                                investorId
                        );


        PortfolioResponse response =
                new PortfolioResponse();


        response.setPortfolioId(
                portfolio.getPortfolioId()
        );


        response.setLastActivityDate(
                portfolio.getLastActivityDate()
        );


        response.setCurrentValue(
                currentValue
        );


        if (portfolio.getInvestor() != null) {

            response.setInvestorId(
                    portfolio.getInvestor()
                            .getUserId()
            );

            response.setInvestorName(
                    portfolio.getInvestor()
                            .getName()
            );
        }


        return ResponseEntity.ok(
                response
        );
    }
}
