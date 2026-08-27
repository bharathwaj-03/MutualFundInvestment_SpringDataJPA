package com.crimsonlogic.mutualfundinvestmentspringdatajpa.controller;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.request.FundRequest;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.request.NavUpdateRequest;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.response.NavHistoryResponse;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.abstraction.MutualFund;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.fund.DebtFund;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.fund.EquityFund;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.fund.HybridFund;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.nav.NAVHistory;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.mutualfund.I_MutualFundService;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.navhistory.I_NAVHistoryService;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.utilities.IdGeneratorUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * REST controller that exposes administrator APIs for mutual fund and NAV management.
 */
@RestController
@RequestMapping("/api/admin/funds")
public class MutualFundController {

    private final I_MutualFundService mutualFundService;
    private final I_NAVHistoryService navHistoryService;

    /**
     * Creates this controller with the dependencies required to handle its HTTP operations.
     *
     * @param mutualFundService value supplied to this endpoint
     * @param navHistoryService value supplied to this endpoint
     */
    public MutualFundController(
            I_MutualFundService mutualFundService,
            I_NAVHistoryService navHistoryService) {

        this.mutualFundService = mutualFundService;
        this.navHistoryService = navHistoryService;
    }


    /**
     * Creates a new mutual fund from the administrator request.
     *
     * @param request value supplied to this endpoint
     * @return HTTP response or data produced by the endpoint
     */
    @PostMapping
    public ResponseEntity<MutualFund> addFund(
            @RequestBody FundRequest request) {

        MutualFund fund =
                createFund(
                        request.getFundCategory()
                );

        fund.setFundId(
                IdGeneratorUtil.generateFundId()
        );

        copyRequest(
                request,
                fund
        );

        mutualFundService.addFund(
                fund
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(fund);
    }

    /**
     * Returns one mutual fund identified by its fund ID for administrator use.
     *
     * @param fundId value supplied to this endpoint
     * @return HTTP response or data produced by the endpoint
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

        if (fund == null) {

            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity.ok(
                fund
        );
    }


    /**
     * Updates the editable details of an existing mutual fund.
     *
     * @param fundId value supplied to this endpoint
     * @param request value supplied to this endpoint
     * @return HTTP response or data produced by the endpoint
     */
    @PutMapping("/{fundId}")
    public ResponseEntity<MutualFund> updateFund(
            @PathVariable String fundId,
            @RequestBody FundRequest request) {

        MutualFund existing =
                mutualFundService
                        .getFundById(
                                fundId
                        );

        if (existing == null) {

            return ResponseEntity
                    .notFound()
                    .build();
        }

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
     * Deletes an existing mutual fund identified by its fund ID.
     *
     * @param fundId value supplied to this endpoint
     * @return HTTP response or data produced by the endpoint
     */
    @DeleteMapping("/{fundId}")
    public ResponseEntity<Void> deleteFund(
            @PathVariable String fundId) {

        if (mutualFundService
                .getFundById(fundId) == null) {

            return ResponseEntity
                    .notFound()
                    .build();
        }

        mutualFundService
                .deleteFund(
                        fundId
                );

        return ResponseEntity
                .noContent()
                .build();
    }


    /**
     * Updates a fund NAV and records the administrator responsible for the change.
     *
     * @param fundId value supplied to this endpoint
     * @param request value supplied to this endpoint
     * @return HTTP response or data produced by the endpoint
     */
    @PatchMapping("/{fundId}/nav")
    public ResponseEntity<MutualFund> updateNAV(
            @PathVariable String fundId,
            @RequestBody NavUpdateRequest request) {

        if (mutualFundService
                .getFundById(fundId) == null) {

            return ResponseEntity
                    .notFound()
                    .build();
        }

        mutualFundService.updateNAV(
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
     * Returns NAV-change history for all mutual funds.
     * @return HTTP response or data produced by the endpoint
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
                        .map(this::convertToResponse)
                        .toList();

        return ResponseEntity.ok(
                response
        );
    }


    /**
     * Returns NAV-change history for a specific mutual fund.
     *
     * @param fundId value supplied to this endpoint
     * @return HTTP response or data produced by the endpoint
     */
    @GetMapping("/{fundId}/nav-history")
    public ResponseEntity<
            List<NavHistoryResponse>>
    getNavHistoryByFund(
            @PathVariable String fundId) {

        List<NAVHistory> histories =
                navHistoryService
                        .getNAVHistoryByFundId(
                                fundId
                        );

        List<NavHistoryResponse> response =
                histories
                        .stream()
                        .map(this::convertToResponse)
                        .toList();

        return ResponseEntity.ok(
                response
        );
    }


    private MutualFund createFund(
            String category) {

        if (category == null) {

            throw new IllegalArgumentException(
                    "Fund category is required."
            );
        }

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


    private void copyRequest(
            FundRequest request,
            MutualFund fund) {

        fund.setFundCode(
                request.getFundCode()
        );

        fund.setFundName(
                request.getFundName()
        );

        fund.setFundHouse(
                request.getFundHouse()
        );

        fund.setRiskLevel(
                request.getRiskLevel()
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


    private NavHistoryResponse convertToResponse(
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

        if (history.getMutualFund() != null) {

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
