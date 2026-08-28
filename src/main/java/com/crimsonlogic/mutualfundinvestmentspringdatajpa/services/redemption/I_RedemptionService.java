package com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.redemption;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.InsufficientUnitsException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.financeactivity.Redemption;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.portfolio.Holding;

import java.util.List;

/**
 * Defines business operations for validating, executing, and retrieving mutual fund redemptions.
 * Implementations provide the business rules while controllers depend on this contract rather than concrete service classes.
 */

public interface I_RedemptionService {

    /**
     * Retrieves holdings available for redemption by the specified investor.
     *
     * @param investorId investor identifier
     * @return list of matching records or response objects
     */

    List<Holding> getInvestorHoldings(
            String investorId
    );

    /**
     * Calculates redemption values for the requested units without completing the full redemption persistence workflow.
     *
     * @param investorId investor identifier
     * @param holdingId holding identifier
     * @param units number of mutual fund units to redeem
     * @return result of the business operation
     * @throws InsufficientUnitsException when the related business rule cannot be satisfied
     */

    Redemption calculateRedemption(
            String investorId,
            String holdingId,
            double units
    ) throws InsufficientUnitsException;

    /**
     * Executes a redemption after validating ownership and available units, then records the resulting transaction and holding changes.
     *
     * @param investorId investor identifier
     * @param holdingId holding identifier
     * @param units number of mutual fund units to redeem
     * @return result of the business operation
     * @throws InsufficientUnitsException when the related business rule cannot be satisfied
     */

    Redemption redeemUnits(
            String investorId,
            String holdingId,
            double units
    ) throws InsufficientUnitsException;

    /**
     * Retrieves redemption records associated with the specified investor.
     *
     * @param investorId investor identifier
     * @return list of matching records or response objects
     */

    List<Redemption> getRedemptionsByUser(
            String investorId
    );

    /**
     * Retrieves a redemption by its unique redemption ID.
     *
     * @param redemptionId redemption identifier
     * @return result of the business operation
     */

    Redemption getRedemptionById(
            String redemptionId
    );

    /**
     * Retrieves redemption records according to
     * investor account status.
     *
     * @param active true for active investors and
     *               false for inactive investors
     * @return matching redemption records
     */
    List<Redemption>
    getRedemptionsByInvestorStatus(
            boolean active
    );
}