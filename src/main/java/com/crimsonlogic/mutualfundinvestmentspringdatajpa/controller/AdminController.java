package com.crimsonlogic.mutualfundinvestmentspringdatajpa.controller;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.response.FundCategoryPerformanceResponse;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.response.InactiveInvestorResponse;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.response.InvestorPortfolioSummaryResponse;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.InvalidRequestException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.abstraction.MutualFund;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.user.Investor;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.holding.I_HoldingService;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.investor.I_InvestorService;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.mutualfund.I_MutualFundService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * REST controller for administrator dashboard, investor-account administration, and portfolio performance APIs.
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final I_MutualFundService mutualFundService;
    private final I_HoldingService
            holdingService;

    private final I_InvestorService
            investorService;



    /**
     * Creates this controller with the dependencies required to handle its HTTP operations.
     *
     * @param mutualFundService value supplied to this endpoint
     * @param holdingService value supplied to this endpoint
     * @param investorService value supplied to this endpoint
     */
    public AdminController(
            I_MutualFundService mutualFundService,
            I_HoldingService holdingService,
            I_InvestorService investorService) {

        this.mutualFundService =
                mutualFundService;

        this.holdingService =
                holdingService;

        this.investorService =
                investorService;
    }

    /**
     * Returns summary information required by the administrator dashboard.
     * @return HTTP response or data produced by the endpoint
     */
    @GetMapping("/dashboard")
    public Map<String, Object> dashboard() {
        List<MutualFund> funds = mutualFundService.getAllFunds();
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Admin dashboard");
        response.put("totalFunds", funds.size());
        return response;
    }

    /**
     * Returns all mutual funds for administrator use.
     * @return HTTP response or data produced by the endpoint
     */
    @GetMapping("/funds")
    public List<MutualFund> getFunds() {
        return mutualFundService.getAllFunds();
    }

    /**
     * Returns aggregated investment performance grouped by fund category.
     * @return HTTP response or data produced by the endpoint
     */
    @GetMapping(
            "/fund-category-performance"
    )
    public ResponseEntity<
                List<
                        FundCategoryPerformanceResponse
                        >>
    getFundCategoryPerformance() {

        return ResponseEntity.ok(
                holdingService
                        .getFundCategoryPerformance()
        );
    }

    /**
     * Deactivates the investor account identified by the path variable.
     *
     * @param investorId value supplied to this endpoint
     * @return HTTP response or data produced by the endpoint
     */
    @DeleteMapping(
            "/investors/{investorId}"
    )
    public ResponseEntity<
            Map<String, Object>>
    deactivateInvestor(
            @PathVariable
            String investorId) {


        boolean deactivated =
                investorService
                        .deactivateInvestor(
                                investorId
                        );


        Map<String, Object> response =
                new HashMap<>();


        response.put(
                "message",
                "Investor account deactivated successfully."
        );

        response.put(
                "investorId",
                investorId
        );

        response.put(
                "active",
                false
        );


        return ResponseEntity.ok(
                response
        );
    }

    /**
     * Returns portfolio summaries filtered by investor active status.
     *
     * @param active value supplied to this endpoint
     * @return HTTP response or data produced by the endpoint
     */
    @GetMapping(
            "/investor-portfolios/{active}"
    )
    public ResponseEntity<
            List<InvestorPortfolioSummaryResponse>>
    getInvestorPortfolioSummaries(
            @PathVariable
            int active) {


        if (active != 0 &&
                active != 1) {

            throw new InvalidRequestException(
                    "Active status must be either 1 or 0."
            );
        }


        boolean activeStatus =
                active == 1;


        return ResponseEntity.ok(
                holdingService
                        .getAllInvestorPortfolioSummaries(
                                activeStatus
                        )
        );
    }

    /**
     * Returns all inactive investors.
     * @return HTTP response or data produced by the endpoint
     */
    @GetMapping("/investors/inactive")
    public ResponseEntity<
            List<InactiveInvestorResponse>>
    getInactiveInvestors() {

        return ResponseEntity.ok(
                investorService
                        .getInactiveInvestors()
        );
    }

    /**
     * Reactivates the investor account identified by the path variable.
     *
     * @param investorId value supplied to this endpoint
     * @return HTTP response or data produced by the endpoint
     */
    @PostMapping(
            "/investors/{investorId}/activate"
    )
    public ResponseEntity<Map<String, Object>>
    activateInvestor(
            @PathVariable
            String investorId) {


        investorService.activateInvestor(
                investorId
        );


        Map<String, Object> response =
                new HashMap<>();


        response.put(
                "message",
                "Investor account activated successfully."
        );

        response.put(
                "investorId",
                investorId
        );

        response.put(
                "active",
                true
        );


        return ResponseEntity.ok(
                response
        );
    }
}
