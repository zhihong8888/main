package seedu.address.model.schedule;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Schedule's Date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateOfBirth(String)}
 */

/**
 * Represents a Person's Date of Birth in the address book.
 * Guarantees: immutable; is valid as declared in {@link #Date(String)}
 */
public class Date {
    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Date should only be in the format of DD/MM/YYYY and it should not be blank";
    public static final String DATE_VALIDATION_REGEX = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";

    public final String value;

    /**
     * Constructs a {@code dateOfBirth}.
     *
     * @param date A valid date of birth.
     */

    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_DATE_CONSTRAINTS);
        value = date;
    }

    /**
     * Returns true if a given string is a valid date of birth.
     */
    public static boolean isValidDate(String test) {
        if(test.matches(DATE_VALIDATION_REGEX)) {

            return true;
        }
        return false;
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
