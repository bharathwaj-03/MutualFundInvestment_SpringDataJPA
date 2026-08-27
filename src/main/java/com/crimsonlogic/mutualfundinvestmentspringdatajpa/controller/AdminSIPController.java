package com.crimsonlogic.mutualfundinvestmentspringdatajpa.controller;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.response.SIPResponse;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.financeactivity.SIP;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.sip.I_SIPService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


/**
 * REST controller that exposes administrator APIs for viewing SIP records.
 */
@RestController
@RequestMapping("/api/admin/sip")
public class AdminSIPController {

    private final I_SIPService sipService;


    /**
     * Creates this controller with the dependencies required to handle its HTTP operations.
     *
     * @param sipService value supplied to this endpoint
     */
    public AdminSIPController(
            I_SIPService sipService) {

        this.sipService =
                sipService;
    }



    /**
     * Returns all SIP records for administrator review.
     * @return HTTP response or data produced by the endpoint
     */
    @GetMapping
    public ResponseEntity<List<SIPResponse>>
    getAllSIPs() {

        List<SIP> sips =
                sipService.getAllSIPs();


        List<SIPResponse> response =
                sips.stream()
                        .map(this::convertToResponse)
                        .collect(
                                Collectors.toList()
                        );


        return ResponseEntity.ok(
                response
        );
    }



    /**
     * Returns one SIP identified by its SIP ID.
     *
     * @param sipId value supplied to this endpoint
     * @return HTTP response or data produced by the endpoint
     */
    @GetMapping("/{sipId}")
    public ResponseEntity<SIPResponse>
    getSIPById(
            @PathVariable String sipId) {

        SIP sip =
                sipService.getSIPById(
                        sipId
                );


        return ResponseEntity.ok(
                convertToResponse(
                        sip
                )
        );
    }



    private SIPResponse convertToResponse(
            SIP sip) {

        SIPResponse response =
                new SIPResponse();

        response.setSipId(
                sip.getSipId()
        );

        response.setMonthlyAmount(
                sip.getMonthlyAmount()
        );

        response.setUnitsPurchased(
                sip.getUnitsPurchased()
        );

        response.setActivityDate(
                sip.getActivityDate()
        );

        response.setStartDate(
                sip.getStartDate()
        );

        response.setNextInstallmentDate(
                sip.getNextInstallmentDate()
        );

        response.setInvestmentYears(
                sip.getInvestmentYears()
        );

        response.setAssetGainPerYear(
                sip.getAssetGainPerYear()
        );

        response.setAssetGainTotalInvestedYears(
                sip.getAssetGainTotalInvestedYears()
        );

        response.setSipStatus(
                sip.getSipStatus()
        );


        if (sip.getInvestor() != null) {

            response.setInvestorId(
                    sip.getInvestor()
                            .getUserId()
            );

            response.setInvestorName(
                    sip.getInvestor()
                            .getName()
            );
        }


        if (sip.getMutualFund() != null) {

            response.setFundId(
                    sip.getMutualFund()
                            .getFundId()
            );

            response.setFundName(
                    sip.getMutualFund()
                            .getFundName()
            );
        }


        return response;
    }
}
