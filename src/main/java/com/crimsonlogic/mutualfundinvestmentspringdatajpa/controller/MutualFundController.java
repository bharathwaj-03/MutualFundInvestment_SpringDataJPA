package com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .controller;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .dto.request.FundRequest;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .dto.request.NavUpdateRequest;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .dto.response.NavHistoryResponse;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .model.abstraction.MutualFund;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .model.fund.DebtFund;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .model.fund.EquityFund;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .model.fund.HybridFund;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .model.nav.NAVHistory;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .services.mutualfund.I_MutualFundService;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .services.navhistory.I_NAVHistoryService;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .utilities.IdGeneratorUtil;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;


/**
 * REST controller that exposes administrator APIs
 * for mutual fund and NAV management.
 */
@RestController
@RequestMapping("/api/admin/funds")
public class MutualFundController {

    private final I_MutualFundService
            mutualFundService;

    private final I_NAVHistoryService
            navHistoryService;


    public MutualFundController(
            I_MutualFundService mutualFundService,
            I_NAVHistoryService navHistoryService) {

        this.mutualFundService =
                mutualFundService;

        this.navHistoryService =
                navHistoryService;
    }


    /**
     * Creates a new mutual fund.
     *
     * Bean Validation checks the request before fund
     * creation and persistence are performed.
     */
    @PostMapping
    public ResponseEntity<MutualFund>
    addFund(

            @Valid
            @RequestBody
            FundRequest request) {


        MutualFund fund =
                createFund(
                        request.getFundCategory()
                );


        fund.setFundId(
                IdGeneratorUtil
                        .generateFundId()
        );


        copyRequest(
                request,
                fund
        );


        mutualFundService
                .addFund(
                        fund
                );


        return ResponseEntity
                .status(
                        HttpStatus.CREATED
                )
                .body(
                        fund
                );
    }


    /**
     * Returns one mutual fund identified by its fund ID.
     */
    @GetMapping("/{fundId}")
    public ResponseEntity<MutualFund>
    getFundById(
            @PathVariable
            String fundId) {


        MutualFund fund =
                mutualFundService
                        .getFundById(
                                fundId
                        );


        return ResponseEntity.ok(
                fund
        );
    }


    /**
     * Updates editable information for an existing mutual fund.
     *
     * Bean Validation verifies the supplied fund information
     * before the update operation executes.
     */
    @PutMapping("/{fundId}")
    public ResponseEntity<MutualFund>
    updateFund(
            @PathVariable
            String fundId,

            @Valid
            @RequestBody
            FundRequest request) {


        MutualFund existing =
                mutualFundService
                        .getFundById(
                                fundId
                        );


        copyRequest(
                request,
                existing
        );


        mutualFundService
                .updateFund(
                        existing
                );


        return ResponseEntity.ok(
                existing
        );
    }


    /**
     * Deletes the requested mutual fund.
     *
     * DELETE does not require @Valid because no request DTO
     * is received by this endpoint.
     */
    @DeleteMapping("/{fundId}")
    public ResponseEntity<Void>
    deleteFund(
            @PathVariable
            String fundId) {


        mutualFundService
                .deleteFund(
                        fundId
                );


        return ResponseEntity
                .noContent()
                .build();
    }


    /**
     * Updates the NAV of an existing mutual fund.
     *
     * Bean Validation checks the NAV value and administrator
     * identifier before the service is called.
     */
    @PatchMapping("/{fundId}/nav")
    public ResponseEntity<MutualFund>
    updateNAV(
            @PathVariable
            String fundId,

            @Valid
            @RequestBody
            NavUpdateRequest request) {


        mutualFundService
                .updateNAV(
                        fundId,
                        request.getNewNav(),
                        request.getAdminId()
                );


        return ResponseEntity.ok(
                mutualFundService
                        .getFundById(
                                fundId
                        )
        );
    }


    /**
     * Returns NAV history for all mutual funds.
     */
    @GetMapping("/nav-history")
    public ResponseEntity<
            List<NavHistoryResponse>>
    getAllNavHistory() {


        List<NAVHistory> histories =
                navHistoryService
                        .getAllNAVHistory();


        List<NavHistoryResponse> response =
                histories
                        .stream()
                        .map(
                                this::convertToResponse
                        )
                        .toList();


        return ResponseEntity.ok(
                response
        );
    }


    /**
     * Returns NAV history for a specific mutual fund.
     */
    @GetMapping("/{fundId}/nav-history")
    public ResponseEntity<
            List<NavHistoryResponse>>
    getNavHistoryByFund(
            @PathVariable
            String fundId) {


        List<NAVHistory> histories =
                navHistoryService
                        .getNAVHistoryByFundId(
                                fundId
                        );


        List<NavHistoryResponse> response =
                histories
                        .stream()
                        .map(
                                this::convertToResponse
                        )
                        .toList();


        return ResponseEntity.ok(
                response
        );
    }


    /**
     * Creates the concrete fund subtype matching the
     * supplied fund category.
     */
    private MutualFund createFund(
            String category) {


        if ("Equity Fund"
                .equalsIgnoreCase(category)
                ||
                "EQUITY"
                        .equalsIgnoreCase(category)) {

            return new EquityFund();
        }


        if ("Debt Fund"
                .equalsIgnoreCase(category)
                ||
                "DEBT"
                        .equalsIgnoreCase(category)) {

            return new DebtFund();
        }


        if ("Hybrid Fund"
                .equalsIgnoreCase(category)
                ||
                "HYBRID"
                        .equalsIgnoreCase(category)) {

            return new HybridFund();
        }


        throw new IllegalArgumentException(
                "Invalid fund category: "
                        + category
        );
    }


    /**
     * Copies validated request values into the mutual fund entity.
     */
    private void copyRequest(
            FundRequest request,
            MutualFund fund) {


        fund.setFundCode(
                request.getFundCode()
        );


        fund.setFundName(
                request
                        .getFundName()
                        .trim()
        );


        fund.setFundHouse(
                request
                        .getFundHouse()
                        .trim()
        );


        fund.setRiskLevel(
                request
                        .getRiskLevel()
                        .toUpperCase()
        );


        fund.setNav(
                request.getNav()
        );


        fund.setMinimumInvestment(
                request.getMinimumInvestment()
        );


        fund.setSipGainPerYear(
                request.getSipGainPerYear()
        );


        fund.setLumpSumGainPerYear(
                request.getLumpSumGainPerYear()
        );
    }


    /**
     * Converts a NAV history entity into its API response DTO.
     */
    private NavHistoryResponse
    convertToResponse(
            NAVHistory history) {


        NavHistoryResponse response =
                new NavHistoryResponse();


        response.setHistoryId(
                history.getHistoryId()
        );


        response.setOldNav(
                history.getOldNav()
        );


        response.setNewNav(
                history.getNewNav()
        );


        response.setChangeDate(
                history.getChangeDate()
        );


        response.setChangedBy(
                history.getChangedBy()
        );


        if (history.getMutualFund()
                != null) {

            response.setFundId(
                    history
                            .getMutualFund()
                            .getFundId()
            );


            response.setFundName(
                    history
                            .getMutualFund()
                            .getFundName()
            );
        }


        return response;
    }
}