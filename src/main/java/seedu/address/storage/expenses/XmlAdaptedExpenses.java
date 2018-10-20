package seedu.address.storage.expenses;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.expenses.Expenses;
import seedu.address.model.expenses.ExpensesAmount;
import seedu.address.model.person.EmployeeId;

/**
 * JAXB-friendly version of the Expenses.
 */
public class XmlAdaptedExpenses {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Expenses's %s field is missing!";

    @XmlElement(required = true)
    private String id;
    @XmlElement(required = true)
    private String expensesAmount;

    /**
     * Constructs an XmlAdaptedExpenses.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedExpenses() {}

    /**
     * Constructs an {@code XmlAdaptedExpenses} with the given expenses details.
     */
    public XmlAdaptedExpenses(String id, String expensesAmount) {
        this.id = id;
        this.expensesAmount = expensesAmount;
    }

    /**
     * Converts a given expenses into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedExpenses
     */
    public XmlAdaptedExpenses(Expenses source) {
        id = source.getEmployeeId().value;
        expensesAmount = source.getExpensesAmount().expensesAmount;
    }

    /**
     * Converts this jaxb-friendly adapted expenses object into the model's Expenses object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted expenses
     */
    public Expenses toModelType() throws IllegalValueException {
        if (id == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EmployeeId.class.getSimpleName()));
        }
        if (!EmployeeId.isValidEmployeeId(id)) {
            throw new IllegalValueException(EmployeeId.MESSAGE_EMPLOYEEID_CONSTRAINTS);
        }
        final EmployeeId modelEmployeeId = new EmployeeId(id);

        if (expensesAmount == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ExpensesAmount.class.getSimpleName()));
        }
        if (!ExpensesAmount.isValidExpensesAmount(expensesAmount)) {
            throw new IllegalValueException(ExpensesAmount.MESSAGE_EXPENSES_AMOUNT_CONSTRAINTS);
        }
        final ExpensesAmount modelExpensesAmount = new ExpensesAmount(expensesAmount);
        return new Expenses(modelEmployeeId, modelExpensesAmount);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedExpenses)) {
            return false;
        }

        XmlAdaptedExpenses otherExpenses = (XmlAdaptedExpenses) other;
        return Objects.equals(id, otherExpenses.id)
                && Objects.equals(expensesAmount, otherExpenses.expensesAmount);
    }
}
