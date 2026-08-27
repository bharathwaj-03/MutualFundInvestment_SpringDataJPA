package com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.mutualfund;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.ResourceNotFoundException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.InvalidRequestException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.repository.MutualFundRepository;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.repository.NAVHistoryRepository;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.abstraction.MutualFund;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.nav.NAVHistory;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.utilities.DateUtil;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.utilities.IdGeneratorUtil;

import java.util.List;


/**
 * Provides mutual fund management operations and coordinates NAV updates with NAV history persistence.
 * The implementation coordinates business rules and delegates persistence to repository dependencies.
 */


public class MutualFundService implements I_MutualFundService {

    /**
     * Repository used for persistence and database queries required by this service.
     */

    private final MutualFundRepository mutualFundRepository;

    /**
     * Repository used for persistence and database queries required by this service.
     */

    private final NAVHistoryRepository navHistoryRepository;


    /**
     * Creates the service with its required dependencies.
     * Constructor injection makes required collaborators explicit and allows Spring configuration to supply them.
     *
     * @param mutualFundRepository mutualFundRepository dependency used by the service
     * @param navHistoryRepository navHistoryRepository dependency used by the service
     */


    public MutualFundService(
            MutualFundRepository mutualFundRepository,
            NAVHistoryRepository navHistoryRepository) {

        this.mutualFundRepository = mutualFundRepository;
        this.navHistoryRepository = navHistoryRepository;
    }

    /**
     * Persists a newly created mutual fund.
     *
     * @param fund mutual fund information
     */
    @Override
    public void addFund(MutualFund fund) {

        try {

            if (fund == null) {

                throw new InvalidRequestException(
                        "Mutual Fund details cannot be empty."
                );
            }

            if (fund.getFundName() == null ||
                    fund.getFundName().trim().isEmpty()) {

                throw new InvalidRequestException(
                        "Fund name cannot be empty."
                );
            }

            if (fund.getNav() <= 0) {

                throw new InvalidRequestException(
                        "NAV must be greater than 0."
                );
            }

            if (fund.getMinimumInvestment() <= 0) {

                throw new InvalidRequestException(
                        "Minimum investment must be greater than 0."
                );
            }

            if (fund.getFundId() == null ||
                    fund.getFundId().trim().isEmpty()) {

                fund.setFundId(
                        IdGeneratorUtil.generateFundId()
                );
            }

            mutualFundRepository.save(fund);

        } catch (InvalidRequestException e) {

            throw e;

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to add mutual fund. Please try again."
            );
        }
    }

    /**
     * Persists changes made to an existing mutual fund.
     *
     * @param fund mutual fund information
     */
    @Override
    public void updateFund(
            MutualFund fund) {

        try {

            if (fund == null ||
                    fund.getFundId() == null ||
                    fund.getFundId().trim().isEmpty()) {

                throw new InvalidRequestException(
                        "Invalid mutual fund details."
                );
            }

            MutualFund existingFund =
                    mutualFundRepository.findById(fund.getFundId()).orElse(null);

            if (existingFund == null) {

                throw new ResourceNotFoundException(
                        "Mutual Fund not found."
                );
            }

            if (fund.getNav() <= 0) {

                throw new InvalidRequestException(
                        "NAV must be greater than 0."
                );
            }

            if (fund.getMinimumInvestment() <= 0) {

                throw new InvalidRequestException(
                        "Minimum investment must be greater than 0."
                );
            }

            mutualFundRepository.save(fund);

        } catch (ResourceNotFoundException e) {

            throw e;

        } catch (InvalidRequestException e) {

            throw e;

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to update mutual fund. Please try again."
            );
        }
    }

    /**
     * Deletes the mutual fund identified by the supplied fund ID.
     *
     * @param fundId mutual fund identifier
     */
    @Override
    public void deleteFund(
            String fundId) {

        try {

            if (fundId == null ||
                    fundId.trim().isEmpty()) {

                throw new InvalidRequestException(
                        "Fund ID cannot be empty."
                );
            }

            MutualFund fund =
                    mutualFundRepository.findById(fundId).orElse(null);

            if (fund == null) {

                throw new ResourceNotFoundException(
                        "Mutual Fund not found."
                );
            }

            mutualFundRepository.deleteById(fundId);

        } catch (ResourceNotFoundException e) {

            throw e;

        } catch (InvalidRequestException e) {

            throw e;

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to delete mutual fund. Please try again."
            );
        }
    }

    /**
     * Retrieves a mutual fund by its unique fund ID.
     *
     * @param fundId mutual fund identifier
     * @return result of the business operation
     */
    @Override
    public MutualFund getFundById(
            String fundId) {

        try {

            if (fundId == null ||
                    fundId.trim().isEmpty()) {

                throw new InvalidRequestException(
                        "Fund ID cannot be empty."
                );
            }

            MutualFund fund =
                    mutualFundRepository.findById(fundId).orElse(null);

            if (fund == null) {

                throw new ResourceNotFoundException(
                        "Mutual Fund not found."
                );
            }

            return fund;

        } catch (ResourceNotFoundException e) {

            throw e;

        } catch (InvalidRequestException e) {

            throw e;

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to retrieve mutual fund."
            );
        }
    }

    /**
     * Retrieves a mutual fund by its fund name.
     *
     * @param fundName mutual fund name
     * @return result of the business operation
     */
    @Override
    public MutualFund getFundByName(
            String fundName) {

        try {

            if (fundName == null ||
                    fundName.trim().isEmpty()) {

                throw new InvalidRequestException(
                        "Fund name cannot be empty."
                );
            }

            MutualFund fund =
                    mutualFundRepository.findByFundName(fundName).orElse(null);

            if (fund == null) {

                throw new ResourceNotFoundException(
                        "Mutual Fund not found."
                );
            }

            return fund;

        } catch (ResourceNotFoundException e) {

            throw e;

        } catch (InvalidRequestException e) {

            throw e;

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to search mutual fund."
            );
        }
    }

    /**
     * Retrieves mutual funds belonging to the supplied fund category.
     *
     * @param category fund category used for filtering
     * @return list of matching records or response objects
     */
    @Override
    public List<MutualFund> getFundsByCategory(
            String category) {

        try {

            if (category == null ||
                    category.trim().isEmpty()) {

                throw new InvalidRequestException(
                        "Fund category cannot be empty."
                );
            }

            return mutualFundRepository.findByFundCategoryOrderByFundNameAsc(category);

        } catch (InvalidRequestException e) {

            throw e;

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to retrieve funds."
            );
        }
    }

    /**
     * Retrieves all mutual funds.
     *
     * @return list of matching records or response objects
     */
    @Override
    public List<MutualFund> getAllFunds() {

        try {

            return mutualFundRepository.findAllByOrderByFundCategoryAscFundNameAsc();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to retrieve mutual funds."
            );
        }
    }

    /**
     * Updates the current NAV of a fund and records the NAV change for audit/history purposes.
     *
     * @param fundId mutual fund identifier
     * @param newNAV new NAV value to apply
     * @param adminId administrator identifier
     */
    @Override
    public void updateNAV(
            String fundId,
            double newNAV,
            String adminId) {

        try {

            if (fundId == null ||
                    fundId.trim().isEmpty()) {

                throw new InvalidRequestException(
                        "Fund ID cannot be empty."
                );
            }

            MutualFund mutualFund =
                    mutualFundRepository.findById(fundId).orElse(null);

            if (mutualFund == null) {

                throw new ResourceNotFoundException(
                        "Mutual Fund not found."
                );
            }

            if (newNAV <= 0) {

                throw new InvalidRequestException(
                        "NAV must be greater than 0."
                );
            }

            double oldNAV =
                    mutualFund.getNav();

            if (Double.compare(
                    oldNAV,
                    newNAV) == 0) {

                throw new InvalidRequestException(
                        "New NAV is same as current NAV."
                );
            }

            mutualFund.setNav(newNAV);

            mutualFundRepository.save(mutualFund);

            NAVHistory history =
                    new NAVHistory();

            history.setHistoryId(
                    IdGeneratorUtil
                            .generateNavHistoryId()
            );

            history.setMutualFund(
                    mutualFund
            );

            history.setOldNav(oldNAV);

            history.setNewNav(newNAV);

            history.setChangeDate(
                    DateUtil.getCurrentDate()
            );

            history.setChangedBy(adminId);

            navHistoryRepository.save(history);

        } catch (ResourceNotFoundException e) {

            throw e;

        } catch (InvalidRequestException e) {

            throw e;

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to update NAV. Please try again."
            );
        }
    }

    /**
     * Returns the current NAV of the specified mutual fund.
     *
     * @param fundId mutual fund identifier
     * @return calculated numeric value
     */
    @Override
    public double getCurrentNav(
            String fundId) {

        try {

            if (fundId == null ||
                    fundId.trim().isEmpty()) {

                throw new InvalidRequestException(
                        "Fund ID cannot be empty."
                );
            }

            MutualFund fund =
                    mutualFundRepository.findById(fundId).orElse(null);

            if (fund == null) {

                throw new ResourceNotFoundException(
                        "Mutual Fund not found."
                );
            }

            return fund.getNav();

        } catch (ResourceNotFoundException e) {

            throw e;

        } catch (InvalidRequestException e) {

            throw e;

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to retrieve current NAV."
            );
        }
    }
}