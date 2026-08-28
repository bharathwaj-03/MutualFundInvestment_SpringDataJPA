package com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .controller;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .dto.response.RedemptionResponse;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .exception.InvalidRequestException;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .model.financeactivity.Redemption;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .services.redemption.I_RedemptionService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * REST controller that exposes redemption information
 * required by administrators.
 */
@RestController
@RequestMapping("/api/admin/redemptions")
public class AdminRedemptionController {

    private final I_RedemptionService
            redemptionService;


    /**
     * Creates the controller with its required service.
     *
     * @param redemptionService service used for redemption operations
     */
    public AdminRedemptionController(
            I_RedemptionService redemptionService) {

        this.redemptionService =
                redemptionService;
    }


    /**
     * Returns redemptions according to investor account status.
     *
     * 1 represents active investors.
     * 0 represents inactive investors.
     *
     * @param status investor account status represented as 0 or 1
     * @return matching redemption records
     */
    @GetMapping("/{status}")
    public ResponseEntity<
            List<RedemptionResponse>>
    getRedemptionsByInvestorStatus(
            @PathVariable
            int status) {


        if (status != 0 &&
                status != 1) {

            throw new InvalidRequestException(
                    "Status must be 0 for inactive investors "
                            + "or 1 for active investors."
            );
        }


        boolean active =
                status == 1;


        List<Redemption> redemptions =
                redemptionService
                        .getRedemptionsByInvestorStatus(
                                active
                        );


        List<RedemptionResponse> response =
                redemptions
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
     * Converts a redemption entity into its response DTO.
     */
    private RedemptionResponse
    convertToResponse(
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


        if (redemption.getInvestor()
                != null) {

            response.setInvestorId(
                    redemption
                            .getInvestor()
                            .getUserId()
            );


            response.setInvestorName(
                    redemption
                            .getInvestor()
                            .getName()
            );
        }


        if (redemption.getMutualFund()
                != null) {

            response.setFundId(
                    redemption
                            .getMutualFund()
                            .getFundId()
            );


            response.setFundName(
                    redemption
                            .getMutualFund()
                            .getFundName()
            );
        }


        if (redemption.getTransaction()
                != null) {

            response.setTransactionId(
                    redemption
                            .getTransaction()
                            .getTransactionId()
            );
        }


        return response;
    }
}