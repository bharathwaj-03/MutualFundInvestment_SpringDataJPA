package com.crimsonlogic.mutualfundinvestmentspringdatajpa.controller;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.request.InvestorRegistrationRequest;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.request.LoginRequest;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.user.Admin;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.user.Investor;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.admin.I_AdminService;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.investor.I_InvestorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.Map;



/**
 * REST controller for authentication, investor registration, and logout operations.
 */
@RestController
@RequestMapping("/api/auth")
public class UserLoginController {

    private final I_AdminService adminService;

    private final I_InvestorService investorService;


    /**
     * Creates this controller with the dependencies required to handle its HTTP operations.
     *
     * @param adminService value supplied to this endpoint
     * @param investorService value supplied to this endpoint
     */
    public UserLoginController(
            I_AdminService adminService,
            I_InvestorService investorService) {

        this.adminService = adminService;

        this.investorService = investorService;
    }



    /**
     * Authenticates an administrator and stores identity and role in the HTTP session.
     *
     * @param request value supplied to this endpoint
     * @param httpRequest value supplied to this endpoint
     * @return HTTP response or data produced by the endpoint
     */
    @PostMapping("/admin/login")
    public ResponseEntity<Map<String, Object>> adminLogin(
            @Valid   @RequestBody LoginRequest request,
            HttpServletRequest httpRequest) {

        boolean authenticated =
                adminService.authenticateAdmin(
                        request.getUserId(),
                        request.getPassword()
                );


        if (!authenticated) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(
                            message(
                                    "Invalid Admin ID or Password."
                            )
                    );
        }


        Admin admin =
                adminService.getAdminByUserId(
                        request.getUserId()
                );



        HttpSession session =
                httpRequest.getSession();


        session.setAttribute(
                "USER_ID",
                admin.getUserId()
        );


        session.setAttribute(
                "ROLE",
                "ADMIN"
        );



        Map<String, Object> response =
                message(
                        "Admin login successful."
                );


        response.put(
                "userId",
                admin.getUserId()
        );


        response.put(
                "role",
                "ADMIN"
        );


        return ResponseEntity.ok(response);
    }



    /**
     * Authenticates an investor and stores identity and role in the HTTP session.
     *
     * @param request value supplied to this endpoint
     * @param httpRequest value supplied to this endpoint
     * @return HTTP response or data produced by the endpoint
     */
    @PostMapping("/investor/login")
    public ResponseEntity<Map<String, Object>> investorLogin(
         @Valid   @RequestBody LoginRequest request,
            HttpServletRequest httpRequest) {

        Investor investor =
                investorService.authenticateInvestor(
                        request.getUserId(),
                        request.getPassword()
                );


        if (investor == null) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(
                            message(
                                    "Invalid Investor ID or Password."
                            )
                    );
        }



        HttpSession session =
                httpRequest.getSession();


        session.setAttribute(
                "USER_ID",
                investor.getUserId()
        );


        session.setAttribute(
                "ROLE",
                "INVESTOR"
        );



        Map<String, Object> response =
                message(
                        "Investor login successful."
                );


        response.put(
                "userId",
                investor.getUserId()
        );


        response.put(
                "name",
                investor.getName()
        );


        response.put(
                "role",
                "INVESTOR"
        );


        return ResponseEntity.ok(response);
    }



    /**
     * Validates and registers a new investor.
     *
     * @param investor value supplied to this endpoint
     * @param request value supplied to this endpoint
     * @return HTTP response or data produced by the endpoint
     */
    /**
     * Registers a new investor.
     *
     * Bean Validation verifies the registration request before
     * the service operation is executed.
     *
     * @param request investor registration information
     * @return HTTP 201 response when registration succeeds
     */
    @PostMapping("/investor/register")
    public ResponseEntity<
            Map<String, Object>>
    registerInvestor(

            @Valid
            @RequestBody
            InvestorRegistrationRequest request) {


        Investor investor =
                investorService
                        .registerInvestor(
                                request
                        );


        Map<String, Object> response =
                message(
                        "Investor registered successfully."
                );


        response.put(
                "userId",
                investor.getUserId()
        );


        return ResponseEntity
                .status(
                        HttpStatus.CREATED
                )
                .body(response);
    }



    /**
     * Invalidates the current HTTP session and returns a logout confirmation.
     *
     * @param request value supplied to this endpoint
     * @return HTTP response or data produced by the endpoint
     */
    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(
            HttpServletRequest request) {

        HttpSession session =
                request.getSession(false);


        if (session != null) {

            session.invalidate();
        }


        return ResponseEntity.ok(
                message(
                        "Logout successful."
                )
        );
    }



    private Map<String, Object> message(
            String value) {

        Map<String, Object> response =
                new LinkedHashMap<>();


        response.put(
                "message",
                value
        );


        return response;
    }
}
