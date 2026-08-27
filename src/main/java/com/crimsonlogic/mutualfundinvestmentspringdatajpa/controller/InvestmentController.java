package com.crimsonlogic.mutualfundinvestmentspringdatajpa.controller;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.request.InvestmentRequest;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.response.InvestmentResponse;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.ResourceNotFoundException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.financeactivity.Investment;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.interfaces.Payable;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.investment.I_InvestmentService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;
import java.util.stream.Collectors;


/**
 * REST controller that handles investment operations for the logged-in investor.
 */
@RestController
@RequestMapping("/api/investor/investments")
public class InvestmentController {

    private final I_InvestmentService investmentService;


    /**
     * Creates this controller with the dependencies required to handle its HTTP operations.
     *
     * @param investmentService value supplied to this endpoint
     */
    public InvestmentController(
            I_InvestmentService investmentService) {

        this.investmentService =
                investmentService;
    }



    /**
     * Handles this HTTP request and delegates processing to the service layer.
     *
     * @param request value supplied to this endpoint
     * @param httpRequest value supplied to this endpoint
     * @return HTTP response or data produced by the endpoint
     */
    @PostMapping
    public ResponseEntity<InvestmentResponse>
    createInvestment(
            @RequestBody InvestmentRequest request,
            HttpServletRequest httpRequest) {

        String investorId =
                getLoggedInInvestorId(
                        httpRequest
                );


        Payable paymentMethod =
                PaymentMethodFactory.create(
                        request.getPayment()
                );


        Investment investment =
                investmentService.startInvestment(
                        investorId,
                        request.getFundId(),
                        request.getAmount(),
                        request.getInvestmentYears(),
                        paymentMethod
                );


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        convertToResponse(
                                investment
                        )
                );
    }



    /**
     * Returns all investments belonging to the logged-in investor.
     *
     * @param httpRequest value supplied to this endpoint
     * @return HTTP response or data produced by the endpoint
     */
    @GetMapping
    public ResponseEntity<List<InvestmentResponse>>
    getMyInvestments(
            HttpServletRequest httpRequest) {

        String investorId =
                getLoggedInInvestorId(
                        httpRequest
                );


        List<Investment> investments =
                investmentService
                        .getInvestmentsByUser(
                                investorId
                        );


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
     * @param httpRequest value supplied to this endpoint
     * @return HTTP response or data produced by the endpoint
     */
    @GetMapping("/{investmentId}")
    public ResponseEntity<InvestmentResponse>
    getInvestmentById(
            @PathVariable String investmentId,
            HttpServletRequest httpRequest) {

        String investorId =
                getLoggedInInvestorId(
                        httpRequest
                );


        Investment investment =
                investmentService
                        .getInvestmentById(
                                investmentId
                        );



        if (investment.getInvestor() == null ||
                !investorId.equals(
                        investment
                                .getInvestor()
                                .getUserId()
                )) {

            throw new ResourceNotFoundException(
                    "Investment not found with id: "
                            + investmentId
            );
        }


        return ResponseEntity.ok(
                convertToResponse(
                        investment
                )
        );
    }



    private String getLoggedInInvestorId(
            HttpServletRequest request) {

        HttpSession session =
                request.getSession(false);


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
