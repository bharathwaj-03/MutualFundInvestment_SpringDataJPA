package com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.navhistory;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.InvalidRequestException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.nav.NAVHistory;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.repository.NAVHistoryRepository;

import java.util.List;


/**
 * Provides read operations for mutual fund NAV history records.
 * The implementation coordinates business rules and delegates persistence to repository dependencies.
 */


public class NAVHistoryService
        implements I_NAVHistoryService {

    /**
     * Repository used for persistence and database queries required by this service.
     */

    private final NAVHistoryRepository navHistoryRepository;

    /**
     * Creates the service with its required dependencies.
     * Constructor injection makes required collaborators explicit and allows Spring configuration to supply them.
     *
     * @param navHistoryRepository navHistoryRepository dependency used by the service
     */

    public NAVHistoryService(
            NAVHistoryRepository navHistoryRepository) {

        this.navHistoryRepository =
                navHistoryRepository;
    }

    /**
     * Retrieves NAV history records belonging to the specified mutual fund.
     *
     * @param fundId mutual fund identifier
     * @return list of matching records or response objects
     */
    @Override
    public List<NAVHistory> getNAVHistoryByFundId(
            String fundId) {

        try {

            if (fundId == null ||
                    fundId.trim().isEmpty()) {

                throw new InvalidRequestException(
                        "Fund ID cannot be empty."
                );
            }

            return navHistoryRepository
                    .findByFundIdWithMutualFund(
                            fundId
                    );


        } catch (InvalidRequestException e) {

            throw e;


        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to retrieve NAV history.",
                    e
            );
        }
    }

    /**
     * Retrieves all recorded NAV history entries.
     *
     * @return list of matching records or response objects
     */
    @Override
    public List<NAVHistory> getAllNAVHistory() {

        try {

            return navHistoryRepository
                    .findAllWithMutualFund();


        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to retrieve NAV history.",
                    e
            );
        }
    }
}