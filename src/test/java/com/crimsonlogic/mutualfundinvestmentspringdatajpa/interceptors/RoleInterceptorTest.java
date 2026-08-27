package com.crimsonlogic.mutualfundinvestmentspringdatajpa.interceptors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import static org.junit.jupiter.api.Assertions.*;

class RoleInterceptorTest {

    private RoleInterceptor interceptor;

    private MockHttpServletRequest request;

    private MockHttpServletResponse response;


    @BeforeEach
    void setUp() {

        interceptor =
                new RoleInterceptor();

        request =
                new MockHttpServletRequest();

        response =
                new MockHttpServletResponse();

        request.setContextPath(
                "/MutualFundInvestment_SpringDataJPA-1.0-SNAPSHOT"
        );
    }


    // =========================================================
    // HELPER METHOD
    // =========================================================

    private void loginAs(
            String role) {

        MockHttpSession session =
                new MockHttpSession();

        session.setAttribute(
                "ROLE",
                role
        );

        request.setSession(
                session
        );
    }


    // =========================================================
    // NO LOGIN
    // =========================================================

    @Test
    void shouldReturn401WhenUserIsNotLoggedIn()
            throws Exception {

        request.setMethod(
                "GET"
        );

        request.setRequestURI(
                "/MutualFundInvestment_SpringDataJPA-1.0-SNAPSHOT" +
                "/api/portfolios/investor/INV001"
        );


        boolean allowed =
                interceptor.preHandle(
                        request,
                        response,
                        new Object()
                );


        assertFalse(
                allowed
        );

        assertEquals(
                401,
                response.getStatus()
        );

        assertEquals(
                "application/json",
                response.getContentType()
        );

        assertTrue(
                response
                        .getContentAsString()
                        .contains(
                                "\"error\":\"Unauthorized\""
                        )
        );

        assertTrue(
                response
                        .getContentAsString()
                        .contains(
                                "Please login before accessing this resource."
                        )
        );
    }


    // =========================================================
    // ADMIN ROUTES
    // =========================================================

    @Test
    void shouldAllowAdminToAccessAdminRoute()
            throws Exception {

        loginAs(
                "ADMIN"
        );

        request.setMethod(
                "GET"
        );

        request.setRequestURI(
                "/MutualFundInvestment_SpringDataJPA-1.0-SNAPSHOT" +
                "/api/admin/investments"
        );


        boolean allowed =
                interceptor.preHandle(
                        request,
                        response,
                        new Object()
                );


        assertTrue(
                allowed
        );
    }


    @Test
    void shouldReturn403WhenInvestorAccessesAdminRoute()
            throws Exception {

        loginAs(
                "INVESTOR"
        );

        request.setMethod(
                "GET"
        );

        request.setRequestURI(
                "/MutualFundInvestment_SpringDataJPA-1.0-SNAPSHOT" +
                "/api/admin/investments"
        );


        boolean allowed =
                interceptor.preHandle(
                        request,
                        response,
                        new Object()
                );


        assertFalse(
                allowed
        );

        assertEquals(
                403,
                response.getStatus()
        );

        assertTrue(
                response
                        .getContentAsString()
                        .contains(
                                "Admin access only"
                        )
        );
    }


    // =========================================================
    // INVESTOR PROFILE ROUTES
    // =========================================================

    @Test
    void shouldAllowInvestorToAccessInvestorProfileRoute()
            throws Exception {

        loginAs(
                "INVESTOR"
        );

        request.setMethod(
                "GET"
        );

        request.setRequestURI(
                "/MutualFundInvestment_SpringDataJPA-1.0-SNAPSHOT" +
                "/api/investors/INV001"
        );


        assertTrue(
                interceptor.preHandle(
                        request,
                        response,
                        new Object()
                )
        );
    }


    @Test
    void shouldReturn403WhenAdminAccessesInvestorProfileRoute()
            throws Exception {

        loginAs(
                "ADMIN"
        );

        request.setMethod(
                "GET"
        );

        request.setRequestURI(
                "/MutualFundInvestment_SpringDataJPA-1.0-SNAPSHOT" +
                "/api/investors/INV001"
        );


        boolean allowed =
                interceptor.preHandle(
                        request,
                        response,
                        new Object()
                );


        assertFalse(
                allowed
        );

        assertEquals(
                403,
                response.getStatus()
        );

        assertTrue(
                response
                        .getContentAsString()
                        .contains(
                                "Investor access only"
                        )
        );
    }


    // =========================================================
    // LEGACY INVESTMENT ROUTE
    // =========================================================

    @Test
    void shouldAllowInvestorToAccessLegacyInvestmentRoute()
            throws Exception {

        loginAs(
                "INVESTOR"
        );

        request.setMethod(
                "GET"
        );

        request.setRequestURI(
                "/MutualFundInvestment_SpringDataJPA-1.0-SNAPSHOT" +
                "/api/investments"
        );


        assertTrue(
                interceptor.preHandle(
                        request,
                        response,
                        new Object()
                )
        );
    }


    @Test
    void shouldReturn403WhenAdminAccessesLegacyInvestmentRoute()
            throws Exception {

        loginAs(
                "ADMIN"
        );

        request.setMethod(
                "GET"
        );

        request.setRequestURI(
                "/MutualFundInvestment_SpringDataJPA-1.0-SNAPSHOT" +
                "/api/investments"
        );


        assertFalse(
                interceptor.preHandle(
                        request,
                        response,
                        new Object()
                )
        );

        assertEquals(
                403,
                response.getStatus()
        );
    }


    // =========================================================
    // PORTFOLIO ROUTES
    // =========================================================

    @Test
    void shouldAllowInvestorToAccessPortfolioRoute()
            throws Exception {

        loginAs(
                "INVESTOR"
        );

        request.setMethod(
                "GET"
        );

        request.setRequestURI(
                "/MutualFundInvestment_SpringDataJPA-1.0-SNAPSHOT" +
                "/api/portfolios/investor/INV001"
        );


        assertTrue(
                interceptor.preHandle(
                        request,
                        response,
                        new Object()
                )
        );
    }


    @Test
    void shouldReturn403WhenAdminAccessesPortfolioRoute()
            throws Exception {

        loginAs(
                "ADMIN"
        );

        request.setMethod(
                "GET"
        );

        request.setRequestURI(
                "/MutualFundInvestment_SpringDataJPA-1.0-SNAPSHOT" +
                "/api/portfolios/investor/INV001"
        );


        assertFalse(
                interceptor.preHandle(
                        request,
                        response,
                        new Object()
                )
        );

        assertEquals(
                403,
                response.getStatus()
        );
    }


    // =========================================================
    // REDEMPTION ROUTES
    // =========================================================

    @Test
    void shouldAllowInvestorToAccessRedemptionRoute()
            throws Exception {

        loginAs(
                "INVESTOR"
        );

        request.setMethod(
                "POST"
        );

        request.setRequestURI(
                "/MutualFundInvestment_SpringDataJPA-1.0-SNAPSHOT" +
                "/api/redemptions/calculate"
        );


        assertTrue(
                interceptor.preHandle(
                        request,
                        response,
                        new Object()
                )
        );
    }


    @Test
    void shouldReturn403WhenAdminAccessesRedemptionRoute()
            throws Exception {

        loginAs(
                "ADMIN"
        );

        request.setMethod(
                "POST"
        );

        request.setRequestURI(
                "/MutualFundInvestment_SpringDataJPA-1.0-SNAPSHOT" +
                "/api/redemptions/calculate"
        );


        assertFalse(
                interceptor.preHandle(
                        request,
                        response,
                        new Object()
                )
        );

        assertEquals(
                403,
                response.getStatus()
        );
    }


    // =========================================================
    // CASE INSENSITIVE ROLE
    // =========================================================

    @Test
    void shouldTreatRoleComparisonAsCaseInsensitive()
            throws Exception {

        loginAs(
                "investor"
        );

        request.setMethod(
                "GET"
        );

        request.setRequestURI(
                "/MutualFundInvestment_SpringDataJPA-1.0-SNAPSHOT" +
                "/api/portfolios/investor/INV001"
        );


        assertTrue(
                interceptor.preHandle(
                        request,
                        response,
                        new Object()
                )
        );
    }


    // =========================================================
    // RESPONSE JSON
    // =========================================================

    @Test
    void forbiddenResponseShouldContainStatusMessageAndPath()
            throws Exception {

        loginAs(
                "ADMIN"
        );

        request.setMethod(
                "GET"
        );

        request.setRequestURI(
                "/MutualFundInvestment_SpringDataJPA-1.0-SNAPSHOT" +
                "/api/portfolios/investor/INV001"
        );


        interceptor.preHandle(
                request,
                response,
                new Object()
        );


        String json =
                response.getContentAsString();


        assertTrue(
                json.contains(
                        "\"status\":403"
                )
        );

        assertTrue(
                json.contains(
                        "\"error\":\"Forbidden\""
                )
        );

        assertTrue(
                json.contains(
                        "\"message\":\"Access denied. Investor access only.\""
                )
        );

        assertTrue(
                json.contains(
                        "\"path\":\"/api/portfolios/investor/INV001\""
                )
        );

        assertTrue(
                json.contains(
                        "\"timestamp\":\""
                )
        );
    }


    // =========================================================
    // IMPORTANT CURRENT ROUTE GAP
    // =========================================================

    /*
     * These two tests describe the SECURITY BEHAVIOUR
     * your current REST design expects.
     *
     * With the uploaded RoleInterceptor.java they will FAIL,
     * because the interceptor checks:
     *
     *     /api/investments
     *     /api/sips
     *
     * but your newer controller routes use:
     *
     *     /api/investor/investments
     *     /api/investor/sip
     *
     * Keep these tests. Fix RoleInterceptor so they pass.
     */

    @Test
    void adminShouldNotAccessNewInvestorInvestmentRoute()
            throws Exception {

        loginAs(
                "ADMIN"
        );

        request.setMethod(
                "GET"
        );

        request.setRequestURI(
                "/MutualFundInvestment_SpringDataJPA-1.0-SNAPSHOT" +
                "/api/investor/investments"
        );


        boolean allowed =
                interceptor.preHandle(
                        request,
                        response,
                        new Object()
                );


        assertFalse(
                allowed,
                "ADMIN must not be allowed to access " +
                "/api/investor/investments"
        );

        assertEquals(
                403,
                response.getStatus()
        );
    }


    @Test
    void adminShouldNotAccessNewInvestorSipRoute()
            throws Exception {

        loginAs(
                "ADMIN"
        );

        request.setMethod(
                "GET"
        );

        request.setRequestURI(
                "/MutualFundInvestment_SpringDataJPA-1.0-SNAPSHOT" +
                "/api/investor/sip"
        );


        boolean allowed =
                interceptor.preHandle(
                        request,
                        response,
                        new Object()
                );


        assertFalse(
                allowed,
                "ADMIN must not be allowed to access " +
                "/api/investor/sip"
        );

        assertEquals(
                403,
                response.getStatus()
        );
    }
}
