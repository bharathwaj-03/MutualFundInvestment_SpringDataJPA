package com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.navhistory;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.nav.NAVHistory;

import java.util.List;

/**
 * Defines operations for retrieving NAV history information.
 * Implementations provide the business rules while controllers depend on this contract rather than concrete service classes.
 */

public interface I_NAVHistoryService {

    /**
     * Retrieves NAV history records belonging to the specified mutual fund.
     *
     * @param fundId mutual fund identifier
     * @return list of matching records or response objects
     */

    List<NAVHistory> getNAVHistoryByFundId(
            String fundId
    );

    /**
     * Retrieves all recorded NAV history entries.
     *
     * @return list of matching records or response objects
     */

    List<NAVHistory> getAllNAVHistory();
}