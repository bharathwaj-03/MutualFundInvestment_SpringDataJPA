package com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .services.investor;


import com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.request.InvestorRegistrationRequest;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.request.NomineeRegistrationRequest;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.AuthenticationException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.ResourceNotFoundException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.portfolio.Portfolio;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.user.Investor;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.user.Nominee;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.repository.InvestorRepository;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.repository.NomineeRepository;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.portfolio.I_PortfolioService;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.utilities.security.PasswordUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


class InvestorServiceTest {

    @Mock
    private InvestorRepository
            investorRepository;


    @Mock
    private NomineeRepository
            nomineeRepository;


    @Mock
    private I_PortfolioService
            portfolioService;


    private InvestorService
            investorService;


    @BeforeEach
    void setUp() {

        MockitoAnnotations
                .openMocks(this);


        investorService =
                new InvestorService(
                        investorRepository,
                        nomineeRepository,
                        portfolioService
                );
    }


    /**
     * Creates a valid registration request for service tests.
     *
     * Request-format validation itself is tested through
     * controller tests using @Valid.
     */
    private InvestorRegistrationRequest
    validRegistrationRequest() {

        InvestorRegistrationRequest request =
                new InvestorRegistrationRequest();


        request.setName(
                "Bharath Kumar"
        );

        request.setEmail(
                "bharath@example.com"
        );

        request.setPhoneNumber(
                "9876543210"
        );

        request.setPassword(
                "Password@1"
        );

        request.setPanNumber(
                "ABCDE1234F"
        );

        request.setAccountNumber(
                "123456789012"
        );

        request.setRiskProfile(
                "MODERATE"
        );


        NomineeRegistrationRequest nominee =
                new NomineeRegistrationRequest();

        nominee.setName(
                "Rahul Kumar"
        );

        nominee.setAge(
                30
        );

        nominee.setGender(
                "MALE"
        );

        nominee.setRelationship(
                "BROTHER"
        );

        nominee.setAccountNumber(
                "123456789012"
        );


        request.setNominee(
                nominee
        );


        return request;
    }


    @Test
    void shouldRegisterValidInvestor() {

        InvestorRegistrationRequest request =
                validRegistrationRequest();


        when(
                nomineeRepository
                        .save(
                                any(Nominee.class)
                        )
        )
                .thenAnswer(
                        invocation ->
                                invocation
                                        .getArgument(0)
                );


        when(
                investorRepository
                        .save(
                                any(Investor.class)
                        )
        )
                .thenAnswer(
                        invocation ->
                                invocation
                                        .getArgument(0)
                );


        when(
                portfolioService
                        .createPortfolio(
                                anyString()
                        )
        )
                .thenReturn(
                        new Portfolio()
                );


        Investor registeredInvestor =
                investorService
                        .registerInvestor(
                                request
                        );


        assertNotNull(
                registeredInvestor
        );


        assertNotNull(
                registeredInvestor
                        .getUserId()
        );


        assertEquals(
                "INVESTOR",
                registeredInvestor
                        .getUserRole()
        );


        assertTrue(
                registeredInvestor
                        .isActive()
        );


        assertNotNull(
                registeredInvestor
                        .getRegistrationDate()
        );


        assertNotNull(
                registeredInvestor
                        .getNominee()
        );


        assertNotNull(
                registeredInvestor
                        .getNominee()
                        .getNomineeId()
        );


        /*
         * Service normalizes names before persistence.
         */
        assertEquals(
                "BHARATH KUMAR",
                registeredInvestor
                        .getName()
        );


        assertEquals(
                "RAHUL KUMAR",
                registeredInvestor
                        .getNominee()
                        .getName()
        );


        /*
         * Stored password must be hashed rather than kept
         * as the plain request password.
         */
        assertNotEquals(
                "Password@1",
                registeredInvestor
                        .getPassword()
        );


        assertTrue(
                PasswordUtil
                        .verifyPassword(
                                "Password@1",
                                registeredInvestor
                                        .getPassword()
                        )
        );


        /*
         * PAN and account numbers are encrypted before persistence.
         */
        assertNotEquals(
                "ABCDE1234F",
                registeredInvestor
                        .getPanNumber()
        );


        assertNotEquals(
                "123456789012",
                registeredInvestor
                        .getAccountNumber()
        );


        assertNotEquals(
                "123456789012",
                registeredInvestor
                        .getNominee()
                        .getAccountNumber()
        );


        verify(
                nomineeRepository,
                times(1)
        )
                .save(
                        registeredInvestor
                                .getNominee()
                );


        verify(
                investorRepository,
                times(1)
        )
                .save(
                        registeredInvestor
                );


        verify(
                portfolioService,
                times(1)
        )
                .createPortfolio(
                        registeredInvestor
                                .getUserId()
                );
    }


    @Test
    void shouldAuthenticateInvestorWithCorrectPassword() {

        Investor investor =
                new Investor();


        investor.setUserId(
                "INV001"
        );


        investor.setActive(
                true
        );


        investor.setPassword(
                PasswordUtil
                        .hashPassword(
                                "Password@1"
                        )
        );


        when(
                investorRepository
                        .findById(
                                "INV001"
                        )
        )
                .thenReturn(
                        Optional.of(
                                investor
                        )
                );


        Investor authenticated =
                investorService
                        .authenticateInvestor(
                                "INV001",
                                "Password@1"
                        );


        assertSame(
                investor,
                authenticated
        );


        verify(
                investorRepository
        )
                .findById(
                        "INV001"
                );
    }


    @Test
    void shouldThrowAuthenticationExceptionForWrongInvestorPassword() {

        Investor investor =
                new Investor();


        investor.setUserId(
                "INV001"
        );


        investor.setActive(
                true
        );


        investor.setPassword(
                PasswordUtil
                        .hashPassword(
                                "Password@1"
                        )
        );


        when(
                investorRepository
                        .findById(
                                "INV001"
                        )
        )
                .thenReturn(
                        Optional.of(
                                investor
                        )
                );


        assertThrows(
                AuthenticationException.class,
                () ->
                        investorService
                                .authenticateInvestor(
                                        "INV001",
                                        "Wrong@1"
                                )
        );
    }


    @Test
    void shouldThrowAuthenticationExceptionWhenInvestorIdDoesNotExist() {

        when(
                investorRepository
                        .findById(
                                "INV404"
                        )
        )
                .thenReturn(
                        Optional.empty()
                );


        assertThrows(
                AuthenticationException.class,
                () ->
                        investorService
                                .authenticateInvestor(
                                        "INV404",
                                        "Password@1"
                                )
        );
    }


    @Test
    void shouldThrowWhenInvestorDoesNotExist() {

        when(
                investorRepository
                        .findById(
                                "INV404"
                        )
        )
                .thenReturn(
                        Optional.empty()
                );


        assertThrows(
                ResourceNotFoundException.class,
                () ->
                        investorService
                                .getInvestorByUserId(
                                        "INV404"
                                )
        );
    }


    @Test
    void shouldReturnInvestorById() {

        Investor investor =
                new Investor();

        investor.setUserId(
                "INV001"
        );


        when(
                investorRepository
                        .findById(
                                "INV001"
                        )
        )
                .thenReturn(
                        Optional.of(
                                investor
                        )
                );


        Investor result =
                investorService
                        .getInvestorByUserId(
                                "INV001"
                        );


        assertSame(
                investor,
                result
        );
    }


    @Test
    void shouldRejectInactiveInvestorLogin() {

        Investor investor =
                new Investor();


        investor.setUserId(
                "INV001"
        );


        investor.setActive(
                false
        );


        investor.setPassword(
                PasswordUtil
                        .hashPassword(
                                "Password@1"
                        )
        );


        when(
                investorRepository
                        .findById(
                                "INV001"
                        )
        )
                .thenReturn(
                        Optional.of(
                                investor
                        )
                );


        AuthenticationException exception =
                assertThrows(
                        AuthenticationException.class,
                        () ->
                                investorService
                                        .authenticateInvestor(
                                                "INV001",
                                                "Password@1"
                                        )
                );


        assertEquals(
                "Admin deactivated your account. "
                        + "Please register yourself again.",
                exception.getMessage()
        );
    }


    @Test
    void shouldRejectEmptyInvestorIdDuringAuthentication() {

        AuthenticationException exception =
                assertThrows(
                        AuthenticationException.class,
                        () ->
                                investorService
                                        .authenticateInvestor(
                                                "",
                                                "Password@1"
                                        )
                );


        assertEquals(
                "Investor ID cannot be empty.",
                exception.getMessage()
        );


        verify(
                investorRepository,
                never()
        )
                .findById(
                        anyString()
                );
    }
}