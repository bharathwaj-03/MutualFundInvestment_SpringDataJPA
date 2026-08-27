package com.crimsonlogic.mutualfundinvestmentspringdatajpa.controller;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.request.InvestorProfileUpdateRequest;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.response.InvestorProfileResponse;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.investor.I_InvestorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * REST controller that exposes investor profile retrieval and update APIs.
 */
@RestController
@RequestMapping("/api/investors")
public class InvestorProfileController {

    private final I_InvestorService investorService;

    /**
     * Creates this controller with the dependencies required to handle its HTTP operations.
     *
     * @param investorService value supplied to this endpoint
     */
    public InvestorProfileController(I_InvestorService investorService) {
        this.investorService = investorService;
    }

    /**
     * Returns investor profile information for the supplied investor ID.
     *
     * @param investorId value supplied to this endpoint
     * @return HTTP response or data produced by the endpoint
     */
    @GetMapping("/{investorId}")
    public ResponseEntity<
            InvestorProfileResponse>
    getInvestorProfile(
            @PathVariable
            String investorId) {

        return ResponseEntity.ok(
                investorService
                        .getInvestorProfile(
                                investorId
                        )
        );
    }

    /**
     * Updates profile information for the requested identifier.
     *
     * @param investorId value supplied to this endpoint
     * @param investor value supplied to this endpoint
     * @return HTTP response or data produced by the endpoint
     */
    @PutMapping("/{investorId}")
    public ResponseEntity<
            InvestorProfileResponse>
    updateProfile(
            @PathVariable
            String investorId,

            @Valid
            @RequestBody
            InvestorProfileUpdateRequest request) {


        return ResponseEntity.ok(
                investorService
                        .updateInvestorProfile(
                                investorId,
                                request
                        )
        );
    }
}
