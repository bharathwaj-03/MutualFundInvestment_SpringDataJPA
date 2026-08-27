package com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.investor;


import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.AuthenticationException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.InvalidRequestException;
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

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class  InvestorServiceTest {

    @Mock
    private InvestorRepository investorRepository;

    @Mock
    private NomineeRepository nomineeRepository;

    @Mock
    private I_PortfolioService portfolioService;

    private InvestorService investorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        investorService = new InvestorService(
                investorRepository,
                nomineeRepository,
                portfolioService
        );
    }

    private Investor validInvestor() {

        Investor investor =
                new Investor();

        investor.setName(
                "Bharath Kumar"
        );

        investor.setEmail(
                "bharath@example.com"
        );

        investor.setPhoneNumber(
                "9876543210"
        );

        investor.setPassword(
                "Password@1"
        );

        investor.setAge(
                25
        );

        investor.setPanNumber(
                "ABCDE1234F"
        );

        investor.setAccountNumber(
                "12345678901298989"
        );

        investor.setRiskProfile(
                "MODERATE"
        );


        Nominee nominee =
                new Nominee();

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
                "12345678901298988"
        );


        investor.setNominee(
                nominee
        );


        return investor;
    }

    @Test
    void shouldValidateCorrectInvestor() {

        Investor investor =
                validInvestor();

        Map<String, String> errors =
                investorService
                        .validateInvestor(
                                investor
                        );


        System.out.println(
                "VALIDATION ERRORS = "
                        + errors
        );


        assertTrue(
                errors.isEmpty(),
                "Validation errors found: "
                        + errors
        );
    }

    @Test
    void shouldReturnValidationErrorsForInvalidInvestor() {
        Investor investor = validInvestor();
        investor.setEmail("invalid-email");

        Map<String, String> errors =
                investorService.validateInvestor(investor);

        assertTrue(errors.containsKey("email"));
    }

    @Test
    void shouldRegisterValidInvestor() {
        Investor investor = validInvestor();

        when(portfolioService.createPortfolio(anyString()))
                .thenReturn(new Portfolio());

        assertTrue(investorService.registerInvestor(investor));

        assertEquals("INVESTOR", investor.getUserRole());
        assertTrue(investor.isActive());
        assertNotNull(investor.getUserId());
        assertNotNull(investor.getNominee().getNomineeId());

        verify(nomineeRepository).save(investor.getNominee());
        verify(investorRepository).save(investor);
        verify(portfolioService).createPortfolio(investor.getUserId());
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
                PasswordUtil.hashPassword(
                        "Password@1"
                )
        );


        when(
                investorRepository.findById(
                        "INV001"
                )
        )
                .thenReturn(
                        Optional.of(
                                investor
                        )
                );


        assertSame(
                investor,
                investorService
                        .authenticateInvestor(
                                "INV001",
                                "Password@1"
                        )
        );
    }
    @Test
    void shouldThrowAuthenticationExceptionForWrongInvestorPassword() {

        Investor investor =
                new Investor();

        investor.setUserId("INV001");
        investor.setActive(true);

        investor.setPassword(
                PasswordUtil.hashPassword(
                        "Password@1"
                )
        );


        when(
                investorRepository.findById("INV001")
        )
                .thenReturn(
                        Optional.of(investor)
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
    void shouldThrowWhenInvestorDoesNotExist() {
        when(investorRepository.findById("INV404"))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> investorService.getInvestorByUserId("INV404")
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
                PasswordUtil.hashPassword(
                        "Password@1"
                )
        );


        when(
                investorRepository.findById(
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
}
