package com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .services.mutualfund;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .exception.InvalidRequestException;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .exception.ResourceNotFoundException;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .model.abstraction.MutualFund;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .model.nav.NAVHistory;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .repository.MutualFundRepository;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .repository.NAVHistoryRepository;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .utilities.DateUtil;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .utilities.IdGeneratorUtil;

import java.util.List;


/**
 * Provides business operations for mutual fund management.
 *
 * The service coordinates persistence operations and applies
 * mutual-fund-specific business rules such as fund existence
 * checks and NAV change validation.
 */
public class MutualFundService
        implements I_MutualFundService {


    /**
     * Repository used to persist and retrieve mutual funds.
     */
    private final MutualFundRepository
            mutualFundRepository;


    /**
     * Repository used to store NAV change history.
     */
    private final NAVHistoryRepository
            navHistoryRepository;


    /**
     * Creates the service with its required repository dependencies.
     *
     * @param mutualFundRepository repository used for mutual fund persistence
     * @param navHistoryRepository repository used for NAV history persistence
     */
    public MutualFundService(
            MutualFundRepository mutualFundRepository,
            NAVHistoryRepository navHistoryRepository) {

        this.mutualFundRepository =
                mutualFundRepository;

        this.navHistoryRepository =
                navHistoryRepository;
    }


    /**
     * Persists a newly created mutual fund.
     *
     * Request-format validation is performed by Bean Validation
     * before the controller invokes this service.
     *
     * @param fund mutual fund to persist
     */
    @Override
    public void addFund(
            MutualFund fund) {


        if (fund == null) {

            throw new InvalidRequestException(
                    "Mutual Fund details cannot be empty."
            );
        }


        if (fund.getFundId() == null ||
                fund.getFundId()
                        .trim()
                        .isEmpty()) {

            fund.setFundId(
                    IdGeneratorUtil
                            .generateFundId()
            );
        }


        mutualFundRepository.save(
                fund
        );
    }


    /**
     * Updates an existing mutual fund.
     *
     * The fund must already exist before its updated values
     * can be persisted.
     *
     * @param fund mutual fund containing updated information
     */
    @Override
    public void updateFund(
            MutualFund fund) {


        if (fund == null ||
                fund.getFundId() == null ||
                fund.getFundId()
                        .trim()
                        .isEmpty()) {

            throw new InvalidRequestException(
                    "Invalid mutual fund details."
            );
        }


        MutualFund existingFund =
                mutualFundRepository
                        .findById(
                                fund.getFundId()
                        )
                        .orElseThrow(
                                () ->
                                        new ResourceNotFoundException(
                                                "Mutual Fund not found."
                                        )
                        );


        mutualFundRepository.save(
                fund
        );
    }


    /**
     * Deletes an existing mutual fund.
     *
     * The fund is verified before the delete operation is executed.
     *
     * @param fundId identifier of the fund to delete
     */
    @Override
    public void deleteFund(
            String fundId) {


        if (fundId == null ||
                fundId
                        .trim()
                        .isEmpty()) {

            throw new InvalidRequestException(
                    "Fund ID cannot be empty."
            );
        }


        MutualFund fund =
                mutualFundRepository
                        .findById(
                                fundId
                        )
                        .orElseThrow(
                                () ->
                                        new ResourceNotFoundException(
                                                "Mutual Fund not found."
                                        )
                        );


        mutualFundRepository
                .deleteById(
                        fund.getFundId()
                );
    }


    /**
     * Retrieves a mutual fund by its identifier.
     *
     * @param fundId mutual fund identifier
     * @return matching mutual fund
     * @throws ResourceNotFoundException when the fund does not exist
     */
    @Override
    public MutualFund getFundById(
            String fundId) {


        if (fundId == null ||
                fundId
                        .trim()
                        .isEmpty()) {

            throw new InvalidRequestException(
                    "Fund ID cannot be empty."
            );
        }


        return mutualFundRepository
                .findById(
                        fundId
                )
                .orElseThrow(
                        () ->
                                new ResourceNotFoundException(
                                        "Mutual Fund not found."
                                )
                );
    }


    /**
     * Retrieves a mutual fund by its fund name.
     *
     * @param fundName fund name to search for
     * @return matching mutual fund
     */
    @Override
    public MutualFund getFundByName(
            String fundName) {


        if (fundName == null ||
                fundName
                        .trim()
                        .isEmpty()) {

            throw new InvalidRequestException(
                    "Fund name cannot be empty."
            );
        }


        return mutualFundRepository
                .findByFundName(
                        fundName
                )
                .orElseThrow(
                        () ->
                                new ResourceNotFoundException(
                                        "Mutual Fund not found."
                                )
                );
    }


    /**
     * Retrieves mutual funds belonging to the requested category.
     *
     * @param category fund category
     * @return funds belonging to the category
     */
    @Override
    public List<MutualFund>
    getFundsByCategory(
            String category) {


        if (category == null ||
                category
                        .trim()
                        .isEmpty()) {

            throw new InvalidRequestException(
                    "Fund category cannot be empty."
            );
        }


        return mutualFundRepository
                .findByFundCategoryOrderByFundNameAsc(
                        category
                );
    }


    /**
     * Retrieves all mutual funds ordered by category
     * and fund name.
     *
     * @return all mutual funds
     */
    @Override
    public List<MutualFund>
    getAllFunds() {


        return mutualFundRepository
                .findAllByOrderByFundCategoryAscFundNameAsc();
    }


    /**
     * Updates the NAV of a mutual fund and stores the
     * previous and new NAV values in NAV history.
     *
     * The new NAV must differ from the current NAV.
     *
     * @param fundId mutual fund identifier
     * @param newNAV new NAV value
     * @param adminId administrator performing the update
     */
    @Override
    public void updateNAV(
            String fundId,
            double newNAV,
            String adminId) {


        if (fundId == null ||
                fundId
                        .trim()
                        .isEmpty()) {

            throw new InvalidRequestException(
                    "Fund ID cannot be empty."
            );
        }


        MutualFund mutualFund =
                mutualFundRepository
                        .findById(
                                fundId
                        )
                        .orElseThrow(
                                () ->
                                        new ResourceNotFoundException(
                                                "Mutual Fund not found."
                                        )
                        );


        /*
         * This remains a service-layer business rule because
         * it compares the new NAV with persisted fund data.
         */
        double oldNAV =
                mutualFund.getNav();


        if (Double.compare(
                oldNAV,
                newNAV) == 0) {

            throw new InvalidRequestException(
                    "New NAV is same as current NAV."
            );
        }


        mutualFund.setNav(
                newNAV
        );


        mutualFundRepository.save(
                mutualFund
        );


        NAVHistory history =
                new NAVHistory();


        history.setHistoryId(
                IdGeneratorUtil
                        .generateNavHistoryId()
        );


        history.setMutualFund(
                mutualFund
        );


        history.setOldNav(
                oldNAV
        );


        history.setNewNav(
                newNAV
        );


        history.setChangeDate(
                DateUtil.getCurrentDate()
        );


        history.setChangedBy(
                adminId
        );


        navHistoryRepository.save(
                history
        );
    }


    /**
     * Returns the current NAV of the requested mutual fund.
     *
     * @param fundId mutual fund identifier
     * @return current NAV
     */
    @Override
    public double getCurrentNav(
            String fundId) {


        if (fundId == null ||
                fundId
                        .trim()
                        .isEmpty()) {

            throw new InvalidRequestException(
                    "Fund ID cannot be empty."
            );
        }


        MutualFund fund =
                mutualFundRepository
                        .findById(
                                fundId
                        )
                        .orElseThrow(
                                () ->
                                        new ResourceNotFoundException(
                                                "Mutual Fund not found."
                                        )
                        );


        return fund.getNav();
    }
}