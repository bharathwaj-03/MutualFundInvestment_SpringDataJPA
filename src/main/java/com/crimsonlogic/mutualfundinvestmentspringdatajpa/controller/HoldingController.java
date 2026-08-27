package com.crimsonlogic.mutualfundinvestmentspringdatajpa.controller;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.response.HoldingResponse;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.portfolio.Holding;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.holding.I_HoldingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller that exposes holding information for the logged-in investor.
 */
@RestController
@RequestMapping("/api/investor/holdings")
public class HoldingController {

    private final I_HoldingService holdingService;


    /**
     * Creates this controller with the dependencies required to handle its HTTP operations.
     *
     * @param holdingService value supplied to this endpoint
     */
    public HoldingController(
            I_HoldingService holdingService) {

        this.holdingService =
                holdingService;
    }


    /**
     * Returns holdings belonging to the investor stored in the current HTTP session.
     *
     * @param request value supplied to this endpoint
     * @return HTTP response or data produced by the endpoint
     */
    @GetMapping
    public ResponseEntity<List<HoldingResponse>>
    getMyHoldings(
            HttpServletRequest request) {

        HttpSession session =
                request.getSession(false);

        String investorId =
                (String) session.getAttribute(
                        "USER_ID"
                );


        List<Holding> holdings =
                holdingService
                        .getHoldingsByInvestor(
                                investorId
                        );


        List<HoldingResponse> response =
                holdings.stream()
                        .map(this::convertToResponse)
                        .collect(
                                Collectors.toList()
                        );


        return ResponseEntity.ok(
                response
        );
    }


    private HoldingResponse convertToResponse(
            Holding holding) {

        HoldingResponse response =
                new HoldingResponse();


        response.setHoldingId(
                holding.getHoldingId()
        );

        response.setUnitsOwned(
                holding.getUnitsOwned()
        );

        response.setInvestedAmount(
                holding.getInvestedAmount()
        );

        response.setAverageNav(
                holding.getAverageNav()
        );


        if (holding.getPortfolio() != null) {

            response.setPortfolioId(
                    holding.getPortfolio()
                            .getPortfolioId()
            );
        }


        if (holding.getMutualFund() != null) {

            response.setFundId(
                    holding.getMutualFund()
                            .getFundId()
            );

            response.setFundName(
                    holding.getMutualFund()
                            .getFundName()
            );

            response.setFundCategory(
                    holding.getMutualFund()
                            .getFundCategory()
            );


            double currentNav =
                    holding.getMutualFund()
                            .getNav();


            response.setCurrentNav(
                    currentNav
            );


            double currentValue =
                    holding.getUnitsOwned()
                            * currentNav;


            response.setCurrentValue(
                    currentValue
            );


            response.setProfitOrLoss(
                    currentValue
                            - holding.getInvestedAmount()
            );
        }


        return response;
    }
}
