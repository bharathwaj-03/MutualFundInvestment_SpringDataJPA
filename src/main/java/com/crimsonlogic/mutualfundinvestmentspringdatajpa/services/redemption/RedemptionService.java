package com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.redemption;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.ResourceNotFoundException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.InsufficientUnitsException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.InvalidRequestException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.repository.RedemptionRepository;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.abstraction.MutualFund;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.financeactivity.Redemption;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.portfolio.Holding;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.portfolio.Portfolio;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.transaction.RedeemTransaction;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.holding.I_HoldingService;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.mutualfund.I_MutualFundService;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.portfolio.I_PortfolioService;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.transaction.I_TransactionService;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.abstraction.Transaction;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.utilities.DateUtil;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.utilities.IdGeneratorUtil;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Coordinates mutual fund redemption validation, brokerage calculation, transaction recording, holding updates, and portfolio maintenance.
 * The implementation coordinates business rules and delegates persistence to repository dependencies.
 */


public class RedemptionService
        implements I_RedemptionService {

    private static final double BROKERAGE_PER_UNIT = 5.0;

    /**
     * Repository used for persistence and database queries required by this service.
     */

    private final RedemptionRepository redemptionRepository;

    /**
     * Collaborating service used to coordinate related business operations.
     */

    private I_HoldingService holdingService;

    /**
     * Collaborating service used to coordinate related business operations.
     */

    private I_MutualFundService mutualFundService;

    /**
     * Collaborating service used to coordinate related business operations.
     */

    private I_PortfolioService portfolioService;

    /**
     * Collaborating service used to coordinate related business operations.
     */

    private I_TransactionService transactionService;


    /**
     * Creates the service with its required dependencies.
     * Constructor injection makes required collaborators explicit and allows Spring configuration to supply them.
     *
     * @param redemptionRepository redemptionRepository dependency used by the service
     * @param holdingService holdingService dependency used by the service
     * @param mutualFundService mutualFundService dependency used by the service
     * @param portfolioService portfolioService dependency used by the service
     * @param transactionService transactionService dependency used by the service
     */


    public RedemptionService(
            RedemptionRepository redemptionRepository,
            I_HoldingService holdingService,
            I_MutualFundService mutualFundService,
            I_PortfolioService portfolioService,
            I_TransactionService transactionService) {

        this.redemptionRepository = redemptionRepository;
        this.holdingService = holdingService;
        this.mutualFundService = mutualFundService;
        this.portfolioService = portfolioService;
        this.transactionService = transactionService;
    }

    /**
     * Retrieves holdings available for redemption by the specified investor.
     *
     * @param investorId investor identifier
     * @return list of matching records or response objects
     */
    @Override
    public List<Holding> getInvestorHoldings(
            String investorId) {

        if (investorId == null ||
                investorId.trim().isEmpty()) {

            throw new InvalidRequestException(
                    "Investor ID cannot be empty."
            );
        }

        return holdingService
                .getHoldingsByInvestor(
                        investorId
                );
    }

    /**
     * Calculates redemption values for the requested units without completing the full redemption persistence workflow.
     *
     * @param investorId investor identifier
     * @param holdingId holding identifier
     * @param units number of mutual fund units to redeem
     * @return result of the business operation
     * @throws InsufficientUnitsException when the related business rule cannot be satisfied
     */
    @Override
    public Redemption calculateRedemption(
            String investorId,
            String holdingId,
            double units)
            throws InsufficientUnitsException {

        Holding holding =
                validateHolding(
                        investorId,
                        holdingId,
                        units
                );

        MutualFund fund =
                mutualFundService.getFundById(
                        holding.getMutualFund()
                                .getFundId()
                );

        double currentNAV =
                fund.getNav();

        double grossAmount =
                units * currentNAV;

        double brokerage =
                units * BROKERAGE_PER_UNIT;

        double amountReceived =
                grossAmount - brokerage;

        if (amountReceived < 0) {

            throw new InvalidRequestException(
                    "Redemption amount cannot be negative."
            );
        }

        Redemption redemption =
                new Redemption();

        redemption.setInvestor(holding.getPortfolio().getInvestor());
        redemption.setMutualFund(fund);

        redemption.setUnitsRedeemed(
                units
        );

        redemption.setNavAtRedemption(
                currentNAV
        );

        redemption.setGrossAmount(
                grossAmount
        );

        redemption.setBrokerageCharges(
                brokerage
        );

        redemption.setAmountReceived(
                amountReceived
        );

        redemption.setAmount(
                amountReceived
        );

        return redemption;
    }

    /**
     * Executes a redemption after validating ownership and available units, then records the resulting transaction and holding changes.
     *
     * @param investorId investor identifier
     * @param holdingId holding identifier
     * @param units number of mutual fund units to redeem
     * @return result of the business operation
     * @throws InsufficientUnitsException when the related business rule cannot be satisfied
     */
    @Override
    // Transaction boundary: the following business operation must complete atomically.
    @Transactional
    public Redemption redeemUnits(
            String investorId,
            String holdingId,
            double units)
            throws InsufficientUnitsException {

        Holding holding =
                validateHolding(
                        investorId,
                        holdingId,
                        units
                );

        MutualFund fund =
                mutualFundService.getFundById(
                        holding.getMutualFund()
                                .getFundId()
                );


        double currentNAV =
                fund.getNav();

        double grossAmount =
                units * currentNAV;

        double brokerage =
                units * BROKERAGE_PER_UNIT;

        double amountReceived =
                grossAmount - brokerage;


        if (amountReceived < 0) {

            throw new InvalidRequestException(
                    "Redemption amount cannot be negative."
            );
        }


        String transactionId =
                IdGeneratorUtil
                        .generateTransactionId();


        Transaction transaction =
                new RedeemTransaction();

        transaction.setTransactionId(
                transactionId
        );

        transaction.setInvestor(
                holding.getPortfolio()
                        .getInvestor()
        );

        transaction.setMutualFund(
                fund
        );

        transaction.setAmount(
                amountReceived
        );

        transaction.setTransactionType(
                "REDEEM"
        );

        transaction.setTransactionStatus(
                "SUCCESS"
        );

        transaction.setTransactionDateTime(
                java.time.LocalDateTime.now()
        );

        transaction.setPayment(
                null
        );


        boolean transactionAdded =
                transactionService
                        .addTransaction(
                                transaction
                        );


        if (!transactionAdded) {

            throw new RuntimeException(
                    "Unable to create redemption transaction."
            );
        }


        Redemption redemption =
                new Redemption();


        redemption.setRedemptionId(
                IdGeneratorUtil
                        .generateRedemptiontId()
        );

        redemption.setInvestor(
                holding.getPortfolio()
                        .getInvestor()
        );

        redemption.setMutualFund(
                fund
        );

        redemption.setTransaction(
                transaction
        );

        redemption.setUnitsRedeemed(
                units
        );

        redemption.setNavAtRedemption(
                currentNAV
        );

        redemption.setGrossAmount(
                grossAmount
        );

        redemption.setBrokerageCharges(
                brokerage
        );

        redemption.setAmountReceived(
                amountReceived
        );

        redemption.setAmount(
                amountReceived
        );

        redemption.setActivityDate(
                DateUtil.getCurrentDate()
        );


        redemptionRepository.save(
                redemption
        );


        double remainingUnits =
                holding.getUnitsOwned()
                        - units;


        double remainingInvestedAmount =
                holding.getInvestedAmount()
                        - (
                        units
                                * holding.getAverageNav()
                );


        if (remainingUnits <= 0.000001) {

            holdingService.deleteHolding(
                    holding.getHoldingId()
            );

        } else {

            holding.setUnitsOwned(
                    remainingUnits
            );

            holding.setInvestedAmount(
                    Math.max(
                            0,
                            remainingInvestedAmount
                    )
            );

            holdingService.updateHolding(
                    holding
            );
        }


        Portfolio portfolio =
                holding.getPortfolio();


        if (portfolio != null) {

            portfolioService
                    .updatePortfolioDate(
                            portfolio
                    );
        }


        return redemption;
    }

    /**
     * Validates that a holding belongs to the investor and contains enough units for the requested redemption.
     *
     * @param investorId investor identifier
     * @param holdingId holding identifier
     * @param units number of mutual fund units to redeem
     * @return result of the business operation
     * @throws InsufficientUnitsException when the related business rule cannot be satisfied
     */

    private Holding validateHolding(
            String investorId,
            String holdingId,
            double units)
            throws InsufficientUnitsException {

        if (investorId == null ||
                investorId.trim().isEmpty()) {

            throw new InvalidRequestException(
                    "Investor ID cannot be empty."
            );
        }


        if (holdingId == null ||
                holdingId.trim().isEmpty()) {

            throw new InvalidRequestException(
                    "Holding ID cannot be empty."
            );
        }

        if (units <= 0) {

            throw new InvalidRequestException(
                    "Units to redeem must be greater than 0."
            );
        }


        Holding holding =
                holdingService.getHoldingById(
                        holdingId
                );


        if (holding == null) {

            throw new ResourceNotFoundException(
                    "Holding not found."
            );
        }

        if (holding.getPortfolio() == null ||
                holding.getPortfolio()
                        .getInvestor() == null ||
                !investorId.equals(
                        holding.getPortfolio()
                                .getInvestor()
                                .getUserId()
                )) {

            throw new InvalidRequestException(
                    "This holding does not belong to you."
            );
        }

        if (units >
                holding.getUnitsOwned()) {

            throw new InsufficientUnitsException(
                    "Insufficient units. You can redeem a maximum of "
                            + holding.getUnitsOwned()
                            + " units."
            );
        }


        return holding;
    }

    /**
     * Retrieves redemption records associated with the specified investor.
     *
     * @param investorId investor identifier
     * @return list of matching records or response objects
     */
    @Override
    public List<Redemption> getRedemptionsByUser(
            String investorId) {

        return redemptionRepository
                .findByInvestorIdWithRelations(
                        investorId
                );
    }


    /**
     * Retrieves a redemption by its unique redemption ID.
     *
     * @param redemptionId redemption identifier
     * @return result of the business operation
     */
    @Override
    public Redemption getRedemptionById(
            String redemptionId) {

        return redemptionRepository
                .findByIdWithRelations(
                        redemptionId
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Redemption not found with id: "
                                        + redemptionId
                        )
                );
    }

    /**
     * Retrieves redemptions belonging to investors
     * with the requested active status.
     *
     * @param active investor account status
     * @return matching redemption records
     */
    @Override
    public List<Redemption>
    getRedemptionsByInvestorStatus(
            boolean active) {

        return redemptionRepository
                .findByInvestorActiveWithRelations(
                        active
                );
    }
}