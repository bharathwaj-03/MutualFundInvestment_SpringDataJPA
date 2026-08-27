package com.crimsonlogic.mutualfundinvestmentspringdatajpa.controller;



import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .model.abstraction.MutualFund;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .services.mutualfund.I_MutualFundService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * REST controller that exposes read-only mutual fund APIs for investors.
 */
@RestController
@RequestMapping("/api/investor/funds")
public class InvestorFundController {

    private final I_MutualFundService
            mutualFundService;


    /**
     * Creates this controller with the dependencies required to handle its HTTP operations.
     *
     * @param mutualFundService value supplied to this endpoint
     */
    public InvestorFundController(
            I_MutualFundService
                    mutualFundService) {

        this.mutualFundService =
                mutualFundService;
    }



    /**
     * Returns all mutual funds, optionally filtered by category.
     *
     * @param category value supplied to this endpoint
     * @return HTTP response or data produced by the endpoint
     */
    @GetMapping
    public List<MutualFund>
    getAllFunds(
            @RequestParam(
                    required = false
            )
            String category) {


        if (category != null &&
                !category
                        .trim()
                        .isEmpty()) {

            return mutualFundService
                    .getFundsByCategory(
                            category
                    );
        }


        return mutualFundService
                .getAllFunds();
    }



    /**
     * Returns one mutual fund identified by its fund ID.
     *
     * @param fundId value supplied to this endpoint
     * @return HTTP response or data produced by the endpoint
     */
    @GetMapping("/{fundId}")
    public ResponseEntity<MutualFund>
    getFund(
            @PathVariable
            String fundId) {


        MutualFund fund =
                mutualFundService
                        .getFundById(
                                fundId
                        );


        if (fund == null) {

            return ResponseEntity
                    .notFound()
                    .build();
        }


        return ResponseEntity.ok(
                fund
        );
    }
}
