package seedu.address.model.expenses;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.person.EmployeeId;

import java.util.Objects;

/**
 * Represents an Expenses of an employee in the expensesList.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Expenses {

    // Identity fields
    private final EmployeeId id;

    // Data fields
    private final ExpensesAmount expensesAmount;

    /**
     * Every field must be present and not null.
     */
    public Expenses(EmployeeId id, ExpensesAmount expensesAmount) {
        requireAllNonNull(id, expensesAmount);
        this.id = id;
        this.expensesAmount = expensesAmount;
    }

    public EmployeeId getEmployeeId() {
        return id;
    }

    public ExpensesAmount getExpensesAmount() {
        return expensesAmount;
    }

    /**
     * Returns true if both persons of the same id have expenses that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameExpensesRequest(Expenses otherExpenses) {
        if (otherExpenses == this) {
            return true;
        }

        return otherExpenses != null
                && otherExpenses.getEmployeeId().equals(getEmployeeId());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Expenses)) {
            return false;
        }

        Expenses otherExpenses = (Expenses) other;
        return otherExpenses.getEmployeeId().equals(getEmployeeId());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(id, expensesAmount);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getEmployeeId())
                .append(" Expenses Amount: ")
                .append(getExpensesAmount());
        return builder.toString();
    }

}
