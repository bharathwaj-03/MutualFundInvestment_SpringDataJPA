package com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .controller;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .dto.request.SIPRequest;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .dto.response.SIPResponse;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .exception.ResourceNotFoundException;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .model.financeactivity.SIP;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .model.interfaces.Payable;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .services.sip.I_SIPService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import javax.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;


/**
 * REST controller that handles SIP operations
 * for the logged-in investor.
 */
@RestController
@RequestMapping("/api/investor/sip")
public class SIPController {


    private final I_SIPService
            sipService;


    /**
     * Creates the controller with its required SIP service.
     *
     * @param sipService service used for SIP business operations
     */
    public SIPController(
            I_SIPService sipService) {

        this.sipService =
                sipService;
    }


    /**
     * Creates a SIP for the logged-in investor.
     *
     * Bean Validation checks the SIP request and nested
     * payment request before the service workflow executes.
     *
     * @param request validated SIP information
     * @param httpRequest current HTTP request
     * @return newly created SIP information
     */
    @PostMapping
    public ResponseEntity<SIPResponse>
    startSIP(

            @Valid
            @RequestBody
            SIPRequest request,

            HttpServletRequest httpRequest) {


        String investorId =
                getLoggedInInvestorId(
                        httpRequest
                );


        Payable paymentMethod =
                PaymentMethodFactory.create(
                        request.getPayment()
                );


        SIP sip =
                sipService
                        .startSIP(
                                investorId,
                                request.getFundId(),
                                request.getMonthlyAmount(),
                                request.getStartDate(),
                                request.getInvestmentYears(),
                                paymentMethod
                        );


        SIPResponse response =
                convertToResponse(
                        sip
                );


        return ResponseEntity
                .status(
                        HttpStatus.CREATED
                )
                .body(
                        response
                );
    }


    /**
     * Returns all SIPs belonging to the logged-in investor.
     *
     * @param httpRequest current HTTP request
     * @return investor SIP records
     */
    @GetMapping
    public ResponseEntity<List<SIPResponse>>
    getMySIPs(
            HttpServletRequest httpRequest) {


        String investorId =
                getLoggedInInvestorId(
                        httpRequest
                );


        List<SIP> sips =
                sipService
                        .getSIPsByUser(
                                investorId
                        );


        List<SIPResponse> response =
                sips
                        .stream()
                        .map(
                                this::convertToResponse
                        )
                        .collect(
                                Collectors.toList()
                        );


        return ResponseEntity.ok(
                response
        );
    }


    /**
     * Returns one SIP belonging to the logged-in investor.
     *
     * @param sipId SIP identifier
     * @param httpRequest current HTTP request
     * @return requested SIP
     */
    @GetMapping("/{sipId}")
    public ResponseEntity<SIPResponse>
    getSIPById(
            @PathVariable
            String sipId,

            HttpServletRequest httpRequest) {


        String investorId =
                getLoggedInInvestorId(
                        httpRequest
                );


        SIP sip =
                sipService
                        .getSIPById(
                                sipId
                        );


        if (sip.getInvestor() == null ||
                !investorId.equals(
                        sip
                                .getInvestor()
                                .getUserId()
                )) {


            throw new ResourceNotFoundException(
                    "SIP not found with id: "
                            + sipId
            );
        }


        return ResponseEntity.ok(
                convertToResponse(
                        sip
                )
        );
    }


    /**
     * Cancels a SIP belonging to the logged-in investor.
     *
     * @param sipId SIP identifier
     * @param httpRequest current HTTP request
     * @return updated SIP
     */
    @PatchMapping("/{sipId}/cancel")
    public ResponseEntity<SIPResponse>
    cancelSIP(
            @PathVariable
            String sipId,

            HttpServletRequest httpRequest) {


        String investorId =
                getLoggedInInvestorId(
                        httpRequest
                );


        SIP existing =
                sipService
                        .getSIPById(
                                sipId
                        );


        if (existing.getInvestor() == null ||
                !investorId.equals(
                        existing
                                .getInvestor()
                                .getUserId()
                )) {


            throw new ResourceNotFoundException(
                    "SIP not found with id: "
                            + sipId
            );
        }


        if (!sipService
                .cancelSIP(
                        sipId
                )) {


            throw new ResourceNotFoundException(
                    "Unable to cancel SIP with id: "
                            + sipId
            );
        }


        SIP updated =
                sipService
                        .getSIPById(
                                sipId
                        );


        return ResponseEntity.ok(
                convertToResponse(
                        updated
                )
        );
    }


    /**
     * Returns the investor ID stored in the active session.
     */
    private String getLoggedInInvestorId(
            HttpServletRequest request) {


        HttpSession session =
                request.getSession(
                        false
                );


        if (session == null) {

            throw new IllegalStateException(
                    "No active login session."
            );
        }


        Object userId =
                session.getAttribute(
                        "USER_ID"
                );


        if (userId == null) {

            throw new IllegalStateException(
                    "Investor is not logged in."
            );
        }


        return userId.toString();
    }


    /**
     * Converts a SIP entity into its API response DTO.
     */
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
                sip
                        .getAssetGainTotalInvestedYears()
        );


        response.setSipStatus(
                sip.getSipStatus()
        );


        if (sip.getInvestor()
                != null) {


            response.setInvestorId(
                    sip
                            .getInvestor()
                            .getUserId()
            );


            response.setInvestorName(
                    sip
                            .getInvestor()
                            .getName()
            );
        }


        if (sip.getMutualFund()
                != null) {


            response.setFundId(
                    sip
                            .getMutualFund()
                            .getFundId()
            );


            response.setFundName(
                    sip
                            .getMutualFund()
                            .getFundName()
            );
        }


        return response;
    }
}