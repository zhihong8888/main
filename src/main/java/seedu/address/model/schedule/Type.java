package seedu.address.model.schedule;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's remark in the address book.
 * Guarantees: immutable; is always valid
 */
public class Type {

    public static final String MESSAGE_TYPE_CONSTRAINTS =
            "TYPE should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the Type must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String TYPE_VALIDATION_REGEX = "(^WORK$|^LEAVE$)";


    public final String value;
    public Type(String type) {
        requireNonNull(type);
        checkArgument(isValidType(type.toUpperCase()), MESSAGE_TYPE_CONSTRAINTS);
        value = type.toUpperCase();
    }
    @Override
    public String toString() {
        return value;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidType(String test) {
        return test.toUpperCase().matches(TYPE_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Type // instanceof handles nulls
                && value.equals(((Type) other).value)); // state check
    }
    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
