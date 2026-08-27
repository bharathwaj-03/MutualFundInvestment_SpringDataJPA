package com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.sip;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.ResourceNotFoundException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.InvalidRequestException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.repository.SIPRepository;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.abstraction.MutualFund;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.financeactivity.SIP;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.interfaces.Payable;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.payment.Payment;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.portfolio.Holding;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.portfolio.Portfolio;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.transaction.SIPTransaction;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.user.Investor;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.holding.I_HoldingService;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.investor.I_InvestorService;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.mutualfund.I_MutualFundService;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.payment.I_PaymentService;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.portfolio.I_PortfolioService;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.transaction.I_TransactionService;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.utilities.DateUtil;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.utilities.IdGeneratorUtil;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Coordinates systematic investment plan validation, creation, payment processing, transaction recording, holding updates, and SIP lifecycle operations.
 * The implementation coordinates business rules and delegates persistence to repository dependencies.
 */


public class SIPService implements I_SIPService {

    /**
     * Repository used for persistence and database queries required by this service.
     */

    private final SIPRepository sipRepository;

    /**
     * Collaborating service used to coordinate related business operations.
     */

    private I_InvestorService investorService;

    /**
     * Collaborating service used to coordinate related business operations.
     */

    private I_MutualFundService mutualFundService;

    /**
     * Collaborating service used to coordinate related business operations.
     */

    private I_HoldingService holdingService;

    /**
     * Collaborating service used to coordinate related business operations.
     */

    private I_PortfolioService portfolioService;

    /**
     * Collaborating service used to coordinate related business operations.
     */

    private I_TransactionService transactionService;

    /**
     * Collaborating service used to coordinate related business operations.
     */

    private I_PaymentService paymentService;

    /**
     * Creates the service with its required dependencies.
     * Constructor injection makes required collaborators explicit and allows Spring configuration to supply them.
     *
     * @param sipRepository sipRepository dependency used by the service
     * @param investorService investorService dependency used by the service
     * @param mutualFundService mutualFundService dependency used by the service
     * @param holdingService holdingService dependency used by the service
     * @param portfolioService portfolioService dependency used by the service
     * @param transactionService transactionService dependency used by the service
     * @param paymentService paymentService dependency used by the service
     */

    public SIPService(
            SIPRepository sipRepository,
            I_InvestorService investorService,
            I_MutualFundService mutualFundService,
            I_HoldingService holdingService,
            I_PortfolioService portfolioService,
            I_TransactionService transactionService,
            I_PaymentService paymentService) {

        this.sipRepository = sipRepository;
        this.investorService = investorService;
        this.mutualFundService = mutualFundService;
        this.holdingService = holdingService;
        this.portfolioService = portfolioService;
        this.transactionService = transactionService;
        this.paymentService = paymentService;
    }

    /**
     * Validates SIP input and returns field-specific validation messages without creating the SIP.
     *
     * @param fundId mutual fund identifier
     * @param monthlyAmount monthly SIP installment amount
     * @param investmentYears investment duration in years
     * @param startDate requested SIP start date
     * @param paymentType payment method type
     * @return map containing validation errors keyed by field name; empty when validation succeeds
     */

    public Map<String, String> validateSIP(
            String fundId,
            double monthlyAmount,
            int investmentYears,
            LocalDate startDate,
            String paymentType) {

        Map<String, String> errors =
                new HashMap<>();


        if (fundId == null ||
                fundId.trim().isEmpty()) {

            errors.put(
                    "fundId",
                    "Please select a mutual fund."
            );
        }


        if (monthlyAmount <= 0) {

            errors.put(
                    "monthlyAmount",
                    "Monthly SIP amount must be greater than 0."
            );
        }


        if (investmentYears <= 0) {

            errors.put(
                    "investmentYears",
                    "Please select a valid investment period."
            );
        }


        if (startDate == null) {

            errors.put(
                    "startDate",
                    "Please select a SIP start date."
            );
        }


        if (paymentType == null ||
                paymentType.trim().isEmpty()) {

            errors.put(
                    "paymentType",
                    "Please select a payment method."
            );
        }

        if (fundId != null &&
                !fundId.trim().isEmpty()) {

            MutualFund fund =
                    mutualFundService.getFundById(
                            fundId
                    );

            if (fund == null) {

                errors.put(
                        "fundId",
                        "Selected mutual fund does not exist."
                );

            } else if (
                    monthlyAmount <
                            fund.getMinimumInvestment()) {

                errors.put(
                        "monthlyAmount",
                        "Minimum monthly investment for "
                                + fund.getFundName()
                                + " is ₹"
                                + fund.getMinimumInvestment()
                );
            }
        }


        return errors;
    }

    /**
     * Creates a SIP after validating investor, fund, payment, amount, start date, and investment period, and records the first installment effects.
     *
     * @param investorId investor identifier
     * @param fundId mutual fund identifier
     * @param monthlyAmount monthly SIP installment amount
     * @param startDate requested SIP start date
     * @param investmentYears investment duration in years
     * @param paymentMethod payment strategy used to execute the payment
     * @return result of the business operation
     */
    @Override
    // Transaction boundary: the following business operation must complete atomically.
    @Transactional
    public SIP startSIP(
            String investorId,
            String fundId,
            double monthlyAmount,
            LocalDate startDate,
            int investmentYears,  Payable paymentMethod) {

        Investor investor =
                investorService.getInvestorByUserId(
                        investorId
                );

        if (investor == null) {

            throw new ResourceNotFoundException(
                    "Investor not found with id: " + investorId
            );
        }
        boolean paymentSuccessful =
                paymentService.processPayment(
                        paymentMethod,
                        monthlyAmount
                );

        if (!paymentSuccessful) {

            throw new InvalidRequestException(
                    "Payment failed. SIP was not created."
            );
        }

        Payment payment =
                paymentService.savePayment(
                        investorId,
                        paymentMethod,
                        monthlyAmount
                );

        MutualFund mutualFund =
                mutualFundService.getFundById(
                        fundId
                );

        if (mutualFund == null) {

            throw new ResourceNotFoundException(
                    "Mutual fund not found with id: " + fundId
            );
        }

        if (monthlyAmount <= 0) {

            throw new InvalidRequestException(
                    "Monthly SIP amount must be greater than 0."
            );
        }


        if (monthlyAmount <
                mutualFund.getMinimumInvestment()) {

            throw new InvalidRequestException(
                    "Minimum SIP amount for "
                            + mutualFund.getFundName()
                            + " is ₹"
                            + mutualFund.getMinimumInvestment()
            );
        }


        if (monthlyAmount > 100000) {

            throw new InvalidRequestException(
                    "Maximum monthly SIP amount is ₹100000."
            );
        }

        if (investmentYears <= 0) {

            throw new InvalidRequestException(
                    "Investment period must be greater than 0 years."
            );
        }

        if (startDate == null) {

            throw new InvalidRequestException(
                    "SIP start date is required."
            );
        }


        if (startDate.isBefore(
                DateUtil.getCurrentDate().plusDays(1))) {

            throw new InvalidRequestException(
                    "SIP start date must be tomorrow or later."
            );
        }

        double unitsPurchased =
                monthlyAmount /
                        mutualFund.getNav();

        double annualGain =
                monthlyAmount
                        * mutualFund.getSipGainPerYear()
                        / 100.0;


        double totalGain =
                annualGain * investmentYears;

        SIP sip =
                new SIP();


        sip.setSipId(
                IdGeneratorUtil.generateSipId()
        );


        sip.setInvestor(
                investor
        );


        sip.setMutualFund(
                mutualFund
        );


        sip.setMonthlyAmount(
                monthlyAmount
        );


        sip.setUnitsPurchased(
                unitsPurchased
        );

        sip.setActivityDate(
                DateUtil.getCurrentDate()
        );


        sip.setStartDate(
                startDate
        );


        sip.setNextInstallmentDate(
                startDate.plusMonths(1)
        );


        sip.setInvestmentYears(
                investmentYears
        );


        sip.setAssetGainPerYear(
                annualGain
        );


        sip.setAssetGainTotalInvestedYears(
                totalGain
        );


        sip.setSipStatus(
                "ACTIVE"
        );

        sipRepository.save(sip);

        SIPTransaction transaction =
                new SIPTransaction();

        transaction.setTransactionId(
                IdGeneratorUtil.generateTransactionId()
        );

        transaction.setInvestor(
                investor
        );

        transaction.setMutualFund(
                mutualFund
        );

        transaction.setAmount(
                monthlyAmount
        );

        transaction.setTransactionType(
                "SIP"
        );

        transaction.setTransactionStatus(
                "SUCCESS"
        );
        transaction.setPayment(payment);

        transaction.setTransactionDateTime(
                java.time.LocalDateTime.now()
        );


        transaction.executeTransaction();

        if (!transactionService.addTransaction(
                transaction)) {

            throw new IllegalStateException(
                    "SIP transaction could not be recorded."
            );
        }

        Portfolio portfolio =
                portfolioService.getPortfolio(
                        investorId
                );


        if (portfolio == null) {

            throw new ResourceNotFoundException(
                    "Portfolio not found for investor: " + investorId
            );
        }

        Holding existingHolding =
                holdingService
                        .getHoldingByPortfolioAndFund(
                                portfolio.getPortfolioId(),
                                mutualFund.getFundId()
                        );

        if (existingHolding == null) {

            Holding holding =
                    new Holding();


            holding.setHoldingId(
                    IdGeneratorUtil.generateHoldingId()
            );


            holding.setPortfolio(
                    portfolio
            );


            holding.setMutualFund(
                    mutualFund
            );


            holding.setUnitsOwned(
                    unitsPurchased
            );


            holding.setInvestedAmount(
                    monthlyAmount
            );


            holding.setAverageNav(
                    mutualFund.getNav()
            );


            if (!holdingService.createHolding(
                    holding)) {

                throw new IllegalStateException(
                        "SIP holding could not be created."
                );
            }

        } else {

            double oldUnits =
                    existingHolding.getUnitsOwned();


            double oldAmount =
                    existingHolding.getInvestedAmount();


            double newUnits =
                    oldUnits + unitsPurchased;


            double newAmount =
                    oldAmount + monthlyAmount;


            double newAverageNav =
                    newAmount / newUnits;


            existingHolding.setUnitsOwned(
                    newUnits
            );


            existingHolding.setInvestedAmount(
                    newAmount
            );


            existingHolding.setAverageNav(
                    newAverageNav
            );


            if (!holdingService.updateHolding(
                    existingHolding)) {

                throw new IllegalStateException(
                        "SIP holding could not be updated."
                );
            }
        }

        portfolioService.updatePortfolioDate(
                portfolio
        );


        return sip;
    }

    /**
     * Retrieves a SIP by its unique SIP ID.
     *
     * @param sipId SIP identifier
     * @return result of the business operation
     */
    @Override
    public SIP getSIPById(
            String sipId) {

        return sipRepository
                .findByIdWithRelations(
                        sipId
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "SIP not found with id: "
                                        + sipId
                        )
                );
    }

    /**
     * Retrieves all SIP records belonging to the specified investor.
     *
     * @param userId user identifier
     * @return list of matching records or response objects
     */
    @Override
    public List<SIP> getSIPsByUser(
            String userId) {

        return sipRepository
                .findByInvestorIdWithRelations(
                        userId
                );
    }

    /**
     * Retrieves all SIP records.
     *
     * @return list of matching records or response objects
     */
    @Override
    public List<SIP> getAllSIPs() {

        return sipRepository
                .findAllWithRelations();
    }

    /**
     * Persists changes made to an existing SIP.
     *
     * @param sip SIP information
     * @return true when the operation succeeds; otherwise false
     */
    @Override
    public boolean updateSIP(
            SIP sip) {

        try {

            if (sip == null ||
                    sip.getSipId() == null) {

                return false;
            }

            sipRepository.save(sip);

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

    /**
     * Cancels the SIP identified by the supplied SIP ID.
     *
     * @param sipId SIP identifier
     * @return true when the operation succeeds; otherwise false
     */
    @Override
    // Transaction boundary: the following business operation must complete atomically.
    @Transactional
    public boolean cancelSIP(
            String sipId) {

        try {

            SIP sip =
                    sipRepository.findById(sipId).orElse(null);

            if (sip == null) {
                return false;
            }


            if ("CANCELLED".equalsIgnoreCase(
                    sip.getSipStatus())) {

                return false;
            }


            sip.setSipStatus(
                    "CANCELLED"
            );


            sipRepository.save(sip);


            return true;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }
}