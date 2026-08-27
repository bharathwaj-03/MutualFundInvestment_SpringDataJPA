package com.crimsonlogic.mutualfundinvestmentspringdatajpa.utilities;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Provides reusable date and time values for application operations.
 *
 * The utility centralizes creation of current and future dates so service
 * classes can obtain consistent date values without duplicating date logic.
 */
public class DateUtil {

    /**
     * Creates an instance of the date utility.
     */
    public DateUtil() {
    }

    /**
     * Returns the current system date without time information.
     *
     * @return current local date
     */
    public static LocalDate getCurrentDate() {


        return LocalDate.now();
    }

    /**
     * Returns the current system date and time.
     *
     * @return current local date and time
     */
    public static LocalDateTime getCurrentDateTime() {



        return LocalDateTime.now();
    }

    /**
     * Returns the date exactly one month after the current system date.
     *
     * This value can be used for operations such as calculating the next
     * monthly schedule date.
     *
     * @return local date one month from the current date
     */
    public static LocalDate getNextMonthDate() {


        return LocalDate.now().plusMonths(1);
    }
}
