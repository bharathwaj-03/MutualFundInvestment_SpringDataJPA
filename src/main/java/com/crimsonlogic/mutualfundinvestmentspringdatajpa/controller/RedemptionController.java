package com.crimsonlogic.mutualfundinvestmentspringdatajpa.controller;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.request.RedemptionRequest;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.response.RedemptionResponse;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.InsufficientUnitsException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.InvalidUnitsException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.financeactivity.Redemption;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.portfolio.Holding;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.redemption.I_RedemptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * REST controller that handles redemption operations and redemption-history APIs.
 */
@RestController
@RequestMapping("/api/redemptions")
public class RedemptionController {

    private final I_RedemptionService redemptionService;

    /**
     * Creates this controller with the dependencies required to handle its HTTP operations.
     *
     * @param redemptionService value supplied to this endpoint
     */
    public RedemptionController(I_RedemptionService redemptionService) {
        this.redemptionService = redemptionService;
    }

    /**
     * Returns holdings associated with the supplied investor ID.
     *
     * @param investorId value supplied to this endpoint
     * @return HTTP response or data produced by the endpoint
     */
    @GetMapping("/investor/{investorId}/holdings")
    public List<Holding> getInvestorHoldings(@PathVariable String investorId) {
        return redemptionService.getInvestorHoldings(investorId);
    }

    /**
     * Handles this HTTP request and delegates processing to the service layer.
     *
     * @param request value supplied to this endpoint
     * @param httpRequest value supplied to this endpoint
     * @return HTTP response or data produced by the endpoint
     */
    @PostMapping("/calculate")
    public ResponseEntity<RedemptionResponse>
    calculate(
          @Valid  @RequestBody RedemptionRequest request,
            HttpServletRequest httpRequest)
            throws InsufficientUnitsException {

        HttpSession session =
                httpRequest.getSession(false);

        String investorId =
                (String) session.getAttribute(
                        "USER_ID"
                );


        Redemption redemption =
                redemptionService
                        .calculateRedemption(
                                investorId,
                                request.getHoldingId(),
                                request.getUnits()
                        );


        return ResponseEntity.ok(
                convertToResponse(
                        redemption
                )
        );
    }

    /**
     * Processes a redemption request and returns completed redemption details.
     *
     * @param request value supplied to this endpoint
     * @return HTTP response or data produced by the endpoint
     */
    @PostMapping
    public ResponseEntity<RedemptionResponse> redeem(
      @Valid @RequestBody RedemptionRequest request)
            throws InvalidUnitsException {

        Redemption redemption =
                redemptionService.redeemUnits(
                        request.getInvestorId(),
                        request.getHoldingId(),
                        request.getUnits()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        convertToResponse(
                                redemption
                        )
                );
    }

    /**
     * Handles this HTTP request and delegates processing to the service layer.
     *
     * @param redemptionId value supplied to this endpoint
     * @return HTTP response or data produced by the endpoint
     */
    @GetMapping("/{redemptionId}")
    public ResponseEntity<RedemptionResponse>
    getRedemption(
            @PathVariable String redemptionId) {

        Redemption redemption =
                redemptionService
                        .getRedemptionById(
                                redemptionId
                        );


        return ResponseEntity.ok(
                convertToResponse(
                        redemption
                )
        );
    }

    /**
     * Handles this HTTP request and delegates processing to the service layer.
     *
     * @param investorId value supplied to this endpoint
     * @return HTTP response or data produced by the endpoint
     */
    @GetMapping("/investor/{investorId}")
    public ResponseEntity<List<RedemptionResponse>>
    getInvestorRedemptions(
            @PathVariable String investorId) {

        List<Redemption> redemptions =
                redemptionService
                        .getRedemptionsByUser(
                                investorId
                        );


        List<RedemptionResponse> response =
                redemptions.stream()
                        .map(this::convertToResponse)
                        .toList();


        return ResponseEntity.ok(
                response
        );
    }
    private RedemptionResponse convertToResponse(
            Redemption redemption) {

        RedemptionResponse response =
                new RedemptionResponse();


        response.setRedemptionId(
                redemption.getRedemptionId()
        );

        response.setUnitsRedeemed(
                redemption.getUnitsRedeemed()
        );

        response.setNavAtRedemption(
                redemption.getNavAtRedemption()
        );

        response.setGrossAmount(
                redemption.getGrossAmount()
        );

        response.setBrokerageCharges(
                redemption.getBrokerageCharges()
        );

        response.setAmountReceived(
                redemption.getAmountReceived()
        );

        response.setActivityDate(
                redemption.getActivityDate()
        );


        if (redemption.getInvestor() != null) {

            response.setInvestorId(
                    redemption.getInvestor()
                            .getUserId()
            );

            response.setInvestorName(
                    redemption.getInvestor()
                            .getName()
            );
        }


        if (redemption.getMutualFund() != null) {

            response.setFundId(
                    redemption.getMutualFund()
                            .getFundId()
            );

            response.setFundName(
                    redemption.getMutualFund()
                            .getFundName()
            );
        }


        if (redemption.getTransaction() != null) {

            response.setTransactionId(
                    redemption.getTransaction()
                            .getTransactionId()
            );
        }


        return response;
    }
}
