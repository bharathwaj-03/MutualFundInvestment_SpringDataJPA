package com.crimsonlogic.mutualfundinvestmentspringdatajpa.controller;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.response.InvestmentResponse;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.financeactivity.Investment;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.investment.I_InvestmentService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


/**
 * REST controller that exposes administrator APIs for viewing investment records.
 */
@RestController
@RequestMapping("/api/admin/investments")
public class AdminInvestmentController {

    private final I_InvestmentService investmentService;


    /**
     * Creates this controller with the dependencies required to handle its HTTP operations.
     *
     * @param investmentService value supplied to this endpoint
     */
    public AdminInvestmentController(
            I_InvestmentService investmentService) {

        this.investmentService =
                investmentService;
    }



    /**
     * Returns all investment records for administrator review.
     * @return HTTP response or data produced by the endpoint
     */
    @GetMapping
    public ResponseEntity<List<InvestmentResponse>>
    getAllInvestments() {

        List<Investment> investments =
                investmentService
                        .getAllInvestments();


        List<InvestmentResponse> response =
                investments
                        .stream()
                        .map(this::convertToResponse)
                        .collect(
                                Collectors.toList()
                        );


        return ResponseEntity.ok(
                response
        );
    }



    /**
     * Returns one investment identified by its investment ID.
     *
     * @param investmentId value supplied to this endpoint
     * @return HTTP response or data produced by the endpoint
     */
    @GetMapping("/{investmentId}")
    public ResponseEntity<InvestmentResponse>
    getInvestmentById(
            @PathVariable String investmentId) {

        Investment investment =
                investmentService
                        .getInvestmentById(
                                investmentId
                        );


        return ResponseEntity.ok(
                convertToResponse(
                        investment
                )
        );
    }



    private InvestmentResponse convertToResponse(
            Investment investment) {

        InvestmentResponse response =
                new InvestmentResponse();

        response.setInvestmentId(
                investment.getInvestmentId()
        );

        response.setAmount(
                investment.getAmount()
        );

        response.setUnitsPurchased(
                investment.getUnitsPurchased()
        );

        response.setActivityDate(
                investment.getActivityDate()
        );

        response.setInvestmentYears(
                investment.getInvestmentYears()
        );

        response.setAssetGainPerYear(
                investment.getAssetGainPerYear()
        );

        response.setAssetGainTotalInvestedYears(
                investment
                        .getAssetGainTotalInvestedYears()
        );

        if (investment.getInvestor() != null) {

            response.setInvestorId(
                    investment
                            .getInvestor()
                            .getUserId()
            );

            response.setInvestorName(
                    investment
                            .getInvestor()
                            .getName()
            );
        }

        if (investment.getMutualFund() != null) {

            response.setFundId(
                    investment
                            .getMutualFund()
                            .getFundId()
            );

            response.setFundName(
                    investment
                            .getMutualFund()
                            .getFundName()
            );
        }

        return response;
    }
}
