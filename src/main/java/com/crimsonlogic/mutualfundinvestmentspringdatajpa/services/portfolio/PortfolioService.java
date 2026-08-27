package com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.portfolio;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.ResourceNotFoundException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.repository.InvestorRepository;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.repository.PortfolioRepository;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.portfolio.Portfolio;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.user.Investor;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.portfolio.I_PortfolioService;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.utilities.DateUtil;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.utilities.IdGeneratorUtil;


/**
 * Provides portfolio creation, retrieval, valuation, and last-updated date maintenance for investors.
 * The implementation coordinates business rules and delegates persistence to repository dependencies.
 */


public class PortfolioService implements I_PortfolioService {

    /**
     * Repository used for persistence and database queries required by this service.
     */

    private final PortfolioRepository portfolioRepository;
    /**
     * Repository used for persistence and database queries required by this service.
     */
    private final InvestorRepository investorRepository;

    /**
     * Creates the service with its required dependencies.
     * Constructor injection makes required collaborators explicit and allows Spring configuration to supply them.
     *
     * @param portfolioRepository portfolioRepository dependency used by the service
     * @param investorRepository investorRepository dependency used by the service
     */

    public PortfolioService(PortfolioRepository portfolioRepository,
                            InvestorRepository investorRepository) {
        this.portfolioRepository = portfolioRepository;
        this.investorRepository = investorRepository;
    }

    /**
     * Creates a portfolio for the specified investor when one does not already exist.
     *
     * @param userId user identifier
     * @return result of the business operation
     */
    @Override
    public Portfolio createPortfolio(String userId) {

        
        Portfolio existingPortfolio =
                portfolioRepository.findByInvestor_UserId(userId).orElse(null);

        if (existingPortfolio != null) {
            return existingPortfolio;
        }

        Investor investor =
                investorRepository.findById(userId).orElse(null);

        if (investor == null) {
            return null;
        }

        Portfolio portfolio = new Portfolio();

        portfolio.setPortfolioId(
                IdGeneratorUtil.generatePortfolioId()
        );

        portfolio.setInvestor(investor);

        portfolio.setLastActivityDate(
                DateUtil.getCurrentDate()
        );

        portfolioRepository.save(portfolio);

        return portfolio;
    }


    /**
     * Retrieves the portfolio associated with the specified investor.
     *
     * @param investorId investor identifier
     * @return result of the business operation
     */
    @Override
    public Portfolio getPortfolio(
            String investorId) {

        return portfolioRepository
                .findByInvestorIdWithInvestor(
                        investorId
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Portfolio not found for investor: "
                                        + investorId
                        )
                );
    }

    /**
     * Updates the portfolio maintenance date after portfolio-related activity.
     *
     * @param portfolio portfolio information
     */
    @Override
    public void updatePortfolioDate(
            Portfolio portfolio) {

        portfolio.setLastActivityDate(
                DateUtil.getCurrentDate()
        );

        portfolioRepository.save(portfolio);
    }


    /**
     * Calculates the current total value of an investor portfolio from its holdings.
     *
     * @param investorId investor identifier
     * @return calculated numeric value
     */
    @Override
    public double calculatePortfolioValue(
            String investorId) {

        Portfolio portfolio = portfolioRepository.findByInvestor_UserId(investorId).orElse(null);
        if (portfolio == null) {
            return 0.0;
        }
        return portfolioRepository.calculatePortfolioValue(portfolio.getPortfolioId());
    }
}