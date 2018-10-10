package seedu.address.model.schedule;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;

/**
 * Represents a Schedule's Date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateOfBirth(String)}
 */

/**
 * Represents a Person's Date of Birth in the address book.
 * Guarantees: immutable; is valid as declared in {@link #Date(String)}
 */
public class Date {
    public static final String DATE_VALIDATION_REGEX = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((20)\\d\\d)";

    public static final String MESSAGE_DATE_CONSTRAINTS_DEFAULT =
            "Date should only be in the format of DD/MM/YYYY, it should not be blank and within 2000 to 2099";

    private static final Logger logger = LogsCenter.getLogger(Date.class);

    private static final String MESSAGE_DATE_INVALID_FEB_DATE =
            "29, 30 and 31 are invalid dates of February";
    private static final String MESSAGE_DATE_INVALID_FEB_DATE_LEAP_YEAR =
            "30 and 31 are invalid dates of February on a leap year";

    private static final String MESSAGE_DATE_INVALID_MONTH_DATE =
            "april, june, sep, nov does not have 31 days";

    private static String dateConstraintsError = MESSAGE_DATE_CONSTRAINTS_DEFAULT;

    public final String value;

    /**
     * Constructs a {@code dateOfBirth}.
     *
     * @param date A valid date of birth.
     */

    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), dateConstraintsError);
        value = date;
    }


    /**
     * Returns true if a given string is a valid date of birth.
     */
    public static boolean isValidDate(String test) {
        String day;
        String month;
        String year;

        if (test.matches(DATE_VALIDATION_REGEX)) {
            String[] date = test.split("/");

            day = date[0];
            month = date[1];
            year = date[2];

            return checkValidDate(year, month, day);
        }
        return false;
    }

    /**
     * Check if date is a valid date on the Calendar.
     * @param year
     * @param month
     * @param day
     */

    public static boolean checkValidDate (String year, String month, String day) {
        boolean isLeapYear = ((Integer.valueOf(year) % 4 == 0) &&
                (Integer.valueOf(year) % 100 != 0) || (Integer.valueOf(year) % 400 == 0));


        if (month.equals("02")) {
            if ((isLeapYear) &&
                    ((day.equals("30")) || (day.equals("31")))) {
                dateConstraintsError = MESSAGE_DATE_INVALID_FEB_DATE_LEAP_YEAR;
                return false;       //29 Feb is a valid leap year. 30, 31 is invalid.
            } else if ((day.equals("29")) || (day.equals("30")) || (day.equals("31"))) {
                dateConstraintsError = MESSAGE_DATE_INVALID_FEB_DATE;
                return false;       //29,30,31 Feb is a invalid in non-leap year
            }
        }

        if ((day.equals("31")) &&
            ((month.equals("04")) || (month.equals("06")) || (month.equals("09")) || (month.equals("11")))) {
            dateConstraintsError = MESSAGE_DATE_INVALID_MONTH_DATE;
            return false; // april, june, sep, nov does not have 31 days
        }
        dateConstraintsError = MESSAGE_DATE_CONSTRAINTS_DEFAULT;
        return true;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && value.equals(((Date) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
