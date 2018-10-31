package seedu.address.model.expenses;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Miscellaneous Expenses in the expenses list.
 * Guarantees: immutable; is valid as declared in {@link #isValidMiscellaneousExpenses(String)}
 */
public class MiscellaneousExpenses {

    public static final String MESSAGE_MISCELLANEOUS_EXPENSES_CONSTRAINTS =
            "Miscellaneous Expenses should only contain numbers, and it should be at least 1 digits long";
    public static final String EMPLOYEE_MISCELLANEOUS_EXPENSES_VALIDATION_REGEX = "-?[0-9]+(.[0-9]{2})?";

    public final String miscellaneousExpenses;

    /**
     * Constructs a {@code ExpensesAmount}.
     *
     * @param miscellaneousExpenses A valid Expenses.
     */
    public MiscellaneousExpenses(String miscellaneousExpenses) {
        requireNonNull(miscellaneousExpenses);
        checkArgument(isValidMiscellaneousExpenses(miscellaneousExpenses), MESSAGE_MISCELLANEOUS_EXPENSES_CONSTRAINTS);
        this.miscellaneousExpenses = miscellaneousExpenses;
    }

    /**
     * Returns true if a given string is a valid Expenses Amount.
     */
    public static boolean isValidMiscellaneousExpenses(String test) {
        return test.matches(EMPLOYEE_MISCELLANEOUS_EXPENSES_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return miscellaneousExpenses;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MiscellaneousExpenses // instanceof handles nulls
                && miscellaneousExpenses.equals(((MiscellaneousExpenses) other).miscellaneousExpenses)); // state check
    }

    @Override
    public int hashCode() {
        return miscellaneousExpenses.hashCode();
    }

}
