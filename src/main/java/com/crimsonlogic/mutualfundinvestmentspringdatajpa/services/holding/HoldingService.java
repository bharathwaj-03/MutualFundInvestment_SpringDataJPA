package com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.holding;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.response.FundCategoryPerformanceResponse;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.response.InvestorPortfolioSummaryResponse;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.ResourceNotFoundException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.InvalidRequestException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.repository.HoldingRepository;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.portfolio.Holding;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.utilities.IdGeneratorUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * Provides business operations for portfolio holdings, including lookup, updates, deletion, and portfolio performance summaries.
 * The implementation coordinates business rules and delegates persistence to repository dependencies.
 */


public class HoldingService implements I_HoldingService {

    /**
     * Repository used for persistence and database queries required by this service.
     */

    private final HoldingRepository holdingRepository;


    /**
     * Creates the service with its required dependencies.
     * Constructor injection makes required collaborators explicit and allows Spring configuration to supply them.
     *
     * @param holdingRepository holdingRepository dependency used by the service
     */


    public HoldingService(HoldingRepository holdingRepository) {
        this.holdingRepository = holdingRepository;
    }

    /**
     * Creates a holding and generates a holding ID when one has not already been assigned.
     *
     * @param holding holding information
     * @return true when the operation succeeds; otherwise false
     */
    @Override
    public boolean createHolding(Holding holding) {

        try {

            if (holding == null) {
                return false;
            }

            if (holding.getHoldingId() == null ||
                    holding.getHoldingId().trim().isEmpty()) {

                holding.setHoldingId(
                        IdGeneratorUtil.generateHoldingId()
                );
            }

            holdingRepository.save(holding);

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

    /**
     * Retrieves a holding together with the related portfolio and mutual fund information.
     *
     * @param holdingId holding identifier
     * @return result of the business operation
     */
    @Override
    public Holding getHoldingById(
            String holdingId) {

        return holdingRepository
                .findByIdWithRelations(
                        holdingId
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Holding not found with id: "
                                        + holdingId
                        )
                );
    }

    /**
     * Retrieves all holdings belonging to the specified portfolio.
     *
     * @param portfolioId portfolio identifier
     * @return list of matching records or response objects
     */
    @Override
    public List<Holding> getHoldingsByPortfolio(
            String portfolioId) {

        if (portfolioId == null ||
                portfolioId.trim().isEmpty()) {

            throw new InvalidRequestException(
                    "Portfolio ID cannot be empty."
            );
        }


        return holdingRepository
                .findByPortfolioIdWithRelations(
                        portfolioId
                );
    }

    /**
     * Finds the holding for a specific portfolio and mutual fund combination.
     *
     * @param portfolioId portfolio identifier
     * @param fundId mutual fund identifier
     * @return result of the business operation
     */
    @Override
    public Holding getHoldingByPortfolioAndFund(
            String portfolioId,
            String fundId) {

        if (portfolioId == null ||
                portfolioId.trim().isEmpty()) {

            throw new InvalidRequestException(
                    "Portfolio ID cannot be empty."
            );
        }


        if (fundId == null ||
                fundId.trim().isEmpty()) {

            throw new InvalidRequestException(
                    "Fund ID cannot be empty."
            );
        }


        return holdingRepository
                .findByPortfolioAndFundWithRelations(
                        portfolioId,
                        fundId
                )
                .orElse(null);
    }

    /**
     * Persists changes made to an existing holding.
     *
     * @param holding holding information
     * @return true when the operation succeeds; otherwise false
     */
    @Override
    public boolean updateHolding(
            Holding holding) {

        try {

            holdingRepository.save(holding);

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

    /**
     * Deletes a holding when the specified holding ID exists.
     *
     * @param holdingId holding identifier
     * @return true when the operation succeeds; otherwise false
     */
    @Override
    public boolean deleteHolding(
            String holdingId) {

        try {

            if (!holdingRepository.existsById(holdingId)) {
                return false;
            }
            holdingRepository.deleteById(holdingId);

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

    /**
     * Retrieves all holdings available in the system.
     *
     * @return list of matching records or response objects
     */
    @Override
    public List<Holding> getAllHoldings() {

        return holdingRepository
                .findAllWithRelations();
    }

    /**
     * Retrieves holdings owned by the specified investor.
     *
     * @param investorId investor identifier
     * @return list of matching records or response objects
     */
    @Override
    public List<Holding> getHoldingsByInvestor(
            String investorId) {

        return holdingRepository
                .findByInvestorIdWithRelations(
                        investorId
                );
    }

    /**
     * Calculates category-level invested value, current value, profit or loss, and return information from holding data.
     *
     * @return list of matching records or response objects
     */
    @Override
    public List<FundCategoryPerformanceResponse>
    getFundCategoryPerformance() {

        List<Object[]> results =
                holdingRepository
                        .getFundCategoryPerformance();


        List<FundCategoryPerformanceResponse>
                responseList =
                new ArrayList<>();


        for (Object[] row : results) {

            String category =
                    (String) row[0];


            double totalInvested =
                    ((Number) row[1])
                            .doubleValue();


            double currentValue =
                    ((Number) row[2])
                            .doubleValue();


            FundCategoryPerformanceResponse
                    response =
                    new FundCategoryPerformanceResponse(
                            category,
                            totalInvested,
                            currentValue
                    );


            responseList.add(
                    response
            );
        }


        return responseList;
    }

    /**
     * Builds investor portfolio summaries grouped by mutual fund category and filtered by investor active status.
     *
     * @param active investor active-status filter
     * @return list of matching records or response objects
     */
    @Override
    public List<InvestorPortfolioSummaryResponse>
    getAllInvestorPortfolioSummaries(
            boolean active) {

        List<Object[]> rows =
                holdingRepository
                        .getInvestorPortfolioCategoryValues(
                                active
                        );


        Map<String,
                InvestorPortfolioSummaryResponse>
                investorMap =
                new LinkedHashMap<>();


        for (Object[] row : rows) {

            String investorId =
                    (String) row[0];

            String investorName =
                    (String) row[1];

            String category =
                    String.valueOf(
                            row[2]
                    );

            double categoryValue =
                    ((Number) row[3])
                            .doubleValue();


            InvestorPortfolioSummaryResponse response =
                    investorMap
                            .computeIfAbsent(
                                    investorId,
                                    id ->
                                            new InvestorPortfolioSummaryResponse(
                                                    investorId,
                                                    investorName,
                                                    0,
                                                    new LinkedHashMap<>()
                                            )
                            );


            response
                    .getCategoryValues()
                    .put(
                            category,
                            categoryValue
                    );


            response.setTotalPortfolioValue(
                    response
                            .getTotalPortfolioValue()
                            + categoryValue
            );
        }


        return new ArrayList<>(
                investorMap.values()
        );
    }
}