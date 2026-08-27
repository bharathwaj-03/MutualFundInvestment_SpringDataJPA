package com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.investment;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.PaymentFailedException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.ResourceNotFoundException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.InvalidRequestException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.repository.InvestmentRepository;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.abstraction.MutualFund;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.abstraction.Transaction;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.financeactivity.Investment;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.interfaces.Payable;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.payment.Payment;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.portfolio.Holding;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.portfolio.Portfolio;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.transaction.BuyTransaction;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.user.Investor;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.holding.I_HoldingService;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.mutualfund.I_MutualFundService;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.payment.I_PaymentService;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.portfolio.I_PortfolioService;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.transaction.I_TransactionService;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.investor.I_InvestorService;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.utilities.DateUtil;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.utilities.IdGeneratorUtil;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * Coordinates lump-sum mutual fund investments, including validation, payment processing, transaction creation, holding updates, and portfolio maintenance.
 * The implementation coordinates business rules and delegates persistence to repository dependencies.
 */


public class InvestmentService
        implements I_InvestmentService {

    /**
     * Repository used for persistence and database queries required by this service.
     */

    private final InvestmentRepository investmentRepository;

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

    private I_PaymentService paymentService;

    /**
     * Collaborating service used to coordinate related business operations.
     */

    private I_TransactionService transactionService;

    /**
     * Collaborating service used to coordinate related business operations.
     */

    private I_HoldingService holdingService;

    /**
     * Collaborating service used to coordinate related business operations.
     */

    private I_PortfolioService portfolioService;


    /**
     * Creates the service with its required dependencies.
     * Constructor injection makes required collaborators explicit and allows Spring configuration to supply them.
     *
     * @param investmentRepository investmentRepository dependency used by the service
     * @param investorService investorService dependency used by the service
     * @param mutualFundService mutualFundService dependency used by the service
     * @param paymentService paymentService dependency used by the service
     * @param transactionService transactionService dependency used by the service
     * @param holdingService holdingService dependency used by the service
     * @param portfolioService portfolioService dependency used by the service
     */


    public InvestmentService(
            InvestmentRepository investmentRepository,
            I_InvestorService investorService,
            I_MutualFundService mutualFundService,
            I_PaymentService paymentService,
            I_TransactionService transactionService,
            I_HoldingService holdingService,
            I_PortfolioService portfolioService) {

        this.investmentRepository = investmentRepository;
        this.investorService = investorService;
        this.mutualFundService = mutualFundService;
        this.paymentService = paymentService;
        this.transactionService = transactionService;
        this.holdingService = holdingService;
        this.portfolioService = portfolioService;
    }

    /**
     * Validates investment input and returns field-specific validation messages without starting an investment.
     *
     * @param fundId mutual fund identifier
     * @param amount monetary amount for the operation
     * @param investmentYears investment duration in years
     * @param paymentType payment method type
     * @return map containing validation errors keyed by field name; empty when validation succeeds
     */

    public Map<String, String> validateInvestment(
            String fundId,
            double amount,
            int investmentYears,
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

        
        if (amount <= 0) {

            errors.put(
                    "amount",
                    "Investment amount must be greater than 0."
            );
        }

        
        if (investmentYears <= 0) {

            errors.put(
                    "investmentYears",
                    "Please select a valid investment period."
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

            if (fund != null &&
                    amount < fund.getMinimumInvestment()) {

                errors.put(
                        "amount",
                        "Minimum investment for "
                                + fund.getFundName()
                                + " is ₹"
                                + fund.getMinimumInvestment()
                );
            }
        }

        return errors;
    }

    /**
     * Executes the complete lump-sum investment workflow after validating investor, fund, amount, payment, and investment period.
     *
     * @param investorId investor identifier
     * @param fundId mutual fund identifier
     * @param amount monetary amount for the operation
     * @param investmentYears investment duration in years
     * @param paymentMethod payment strategy used to execute the payment
     * @return result of the business operation
     */
    @Override
    // Transaction boundary: the following business operation must complete atomically.
    @Transactional
    public Investment startInvestment(
            String investorId,
            String fundId,
            double amount,
            int investmentYears,
            Payable paymentMethod) {

        if (amount <= 0) {

            throw new InvalidRequestException(
                    "Investment amount must be greater than 0."
            );
        }

        if (investmentYears <= 0) {

            throw new InvalidRequestException(
                    "Investment period must be greater than 0 years."
            );
        }

        Investor investor =
                investorService.getInvestorByUserId(
                        investorId
                );

        if (investor == null) {

            throw new ResourceNotFoundException(
                    "Investor not found with id: " + investorId
            );
        }

        MutualFund mutualFund =
                mutualFundService.getFundById(
                        fundId
                );

        if (mutualFund == null) {

            throw new ResourceNotFoundException(
                    "Mutual fund not found with id: " + fundId
            );
        }

        if (amount < mutualFund.getMinimumInvestment()) {

            throw new InvalidRequestException(
                    "Minimum investment for "
                            + mutualFund.getFundName()
                            + " is ₹"
                            + mutualFund.getMinimumInvestment()
            );
        }

        double unitsPurchased =
                amount / mutualFund.getNav();

        double annualGain =
                amount
                        * mutualFund.getLumpSumGainPerYear()
                        / 100.0;

        double totalGain =
                annualGain * investmentYears;


        boolean paymentSuccessful =
                paymentService.processPayment(
                        paymentMethod,
                        amount
                );

        if (!paymentSuccessful) {

            throw new PaymentFailedException(
                    "Payment failed. Investment was not created."
            );
        }

        Payment payment =
                paymentService.savePayment(
                        investorId,
                        paymentMethod,
                        amount
                );

        Investment investment =
                new Investment();

        investment.setInvestmentId(
                IdGeneratorUtil.generateInvestmentId()
        );

        investment.setInvestor(investor);

        investment.setMutualFund(mutualFund);

        investment.setAmount(amount);

        investment.setUnitsPurchased(
                unitsPurchased
        );

        investment.setActivityDate(
                DateUtil.getCurrentDate()
        );

        investment.setInvestmentYears(
                investmentYears
        );

        investment.setAssetGainPerYear(
                annualGain
        );

        investment.setAssetGainTotalInvestedYears(
                totalGain
        );

        investmentRepository.save(investment);

        Transaction transaction =
                new BuyTransaction();

        transaction.setTransactionId(
                IdGeneratorUtil.generateTransactionId()
        );

        transaction.setInvestor(investor);

        transaction.setMutualFund(mutualFund);

        transaction.setPayment(payment);

        transaction.setAmount(amount);

        transaction.setTransactionType(
                "LUMP_SUM"
        );

        transaction.setTransactionStatus(
                "SUCCESS"
        );

        transaction.setTransactionDateTime(
                java.time.LocalDateTime.now()
        );

        transaction.executeTransaction();


        if (!transactionService.addTransaction(
                transaction)) {

            throw new IllegalStateException(
                    "Transaction could not be recorded."
            );
        }

        Portfolio portfolio =
                portfolioService.getPortfolio(
                        investorId
                );

        if (portfolio == null) {

            throw new IllegalStateException(
                    "Investor portfolio not found."
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

            holding.setPortfolio(portfolio);

            holding.setMutualFund(mutualFund);

            holding.setUnitsOwned(
                    unitsPurchased
            );

            holding.setInvestedAmount(
                    amount
            );

            holding.setAverageNav(
                    mutualFund.getNav()
            );

            if (!holdingService.createHolding(holding)) {

                throw new IllegalStateException(
                        "Holding could not be created."
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
                    oldAmount + amount;

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
                        "Holding could not be updated."
                );
            }
        }

        portfolioService.updatePortfolioDate(
                portfolio
        );


        return investment;
    }

    /**
     * Retrieves an investment by its unique investment ID.
     *
     * @param investmentId investment identifier
     * @return result of the business operation
     */
    @Override
    public Investment getInvestmentById(
            String investmentId) {

        return investmentRepository
                .findByIdWithRelations(
                        investmentId
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Investment not found with id: "
                                        + investmentId
                        )
                );
    }

    /**
     * Retrieves all investments associated with the specified investor.
     *
     * @param investorId investor identifier
     * @return list of matching records or response objects
     */
    @Override
    public List<Investment> getInvestmentsByUser(
            String investorId) {

        return investmentRepository
                .findByInvestorIdWithRelations(
                        investorId
                );
    }

    /**
     * Retrieves all investment records.
     *
     * @return list of matching records or response objects
     */
    @Override
    public List<Investment> getAllInvestments() {

        return investmentRepository
                .findAllWithRelations();
    }
}