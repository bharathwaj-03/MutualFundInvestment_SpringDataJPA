package com.crimsonlogic.mutualfundinvestmentspringdatajpa.utilities;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Generates application identifiers for domain objects.
 *
 * Each identifier combines a business-specific prefix with a random numeric
 * value so different entity types can be recognized from their IDs.
 */
public class IdGeneratorUtil {

    /** Minimum numeric value used while generating an identifier. */
    private static final int MIN_VAL = 1;

    /** Maximum numeric value used while generating an identifier. */
    private static final int MAX_VAL = 1000000; // 10 Lakh

    /**
     * Creates an instance of the identifier utility.
     */
    public IdGeneratorUtil() {
    }

    /**
     * Generates the random numeric portion used by application identifiers.
     *
     * @return random number within the configured identifier range
     */
    private static int getRandomNumber() {
        return ThreadLocalRandom.current().nextInt(MIN_VAL, MAX_VAL + 1);
    }

    /**
     * Generates an investor identifier using the INV prefix.
     *
     * @return generated investor ID
     */
    public static String generateInvestorId() {
        return "INV" + getRandomNumber();
    }

    /**
     * Generates a mutual fund identifier using the FND prefix.
     *
     * @return generated fund ID
     */
    public static String generateFundId() {
        return "FND" + getRandomNumber();
    }

    /**
     * Generates a portfolio identifier using the PORT prefix.
     *
     * @return generated portfolio ID
     */
    public static String generatePortfolioId() {
        return "PORT" + getRandomNumber();
    }

    /**
     * Generates a transaction identifier using the TXN prefix.
     *
     * @return generated transaction ID
     */
    public static String generateTransactionId() {
        return "TXN" + getRandomNumber();
    }

    /**
     * Generates a holding identifier using the HLD prefix.
     *
     * @return generated holding ID
     */
    public static String generateHoldingId() {
        return "HLD" + getRandomNumber();
    }

    /**
     * Generates a SIP identifier using the SIP prefix.
     *
     * @return generated SIP ID
     */
    public static String generateSipId() {
        return "SIP" + getRandomNumber();
    }

    /**
     * Generates an investment identifier using the INVT prefix.
     *
     * @return generated investment ID
     */
    public static String generateInvestmentId() {
        return "INVT" + getRandomNumber();
    }

    /**
     * Generates a redemption identifier using the RED prefix.
     *
     * @return generated redemption ID
     */
    public static String generateRedemptiontId() {
        return "RED" + getRandomNumber();
    }

    /**
     * Generates a dividend identifier using the DIV prefix.
     *
     * @return generated dividend ID
     */
    public static String generateDividendId() {
        return "DIV" + getRandomNumber();
    }

    /**
     * Generates a NAV history identifier using the NAV prefix.
     *
     * @return generated NAV history ID
     */
    public static String generateNavHistoryId() {
        return "NAV" + getRandomNumber();
    }

    /**
     * Generates a nominee identifier using the NOM prefix.
     *
     * @return generated nominee ID
     */
    public static String generateNomineeId() {
        return "NOM" + getRandomNumber();
    }

    /**
     * Generates a payment identifier using the PAY prefix.
     *
     * @return generated payment ID
     */
    public static String generatePaymentId() {

        return "PAY"
                +  getRandomNumber();
    }
}
