package seedu.address.testutil.expenses;

import seedu.address.model.expenses.Expenses;
import seedu.address.model.expenses.ExpensesAmount;
import seedu.address.model.person.EmployeeId;
/**
 * A utility class to help with building Expenses objects.
 */
public class ExpensesBuilder {

    public static final String DEFAULT_EMPLOYEEID = "000001";
    public static final String DEFAULT_EXPENSESAMOUNT = "123123";

    private EmployeeId employeeId;
    private ExpensesAmount expensesAmount;

    public ExpensesBuilder() {
        employeeId = new EmployeeId(DEFAULT_EMPLOYEEID);
        expensesAmount = new ExpensesAmount(DEFAULT_EXPENSESAMOUNT);
    }

    /**
     * Initializes the ExpensesBuilder with the data of {@code expensesToCopy}.
     */
    public ExpensesBuilder(Expenses expensesToCopy) {
        employeeId = expensesToCopy.getEmployeeId();
        expensesAmount = expensesToCopy.getExpensesAmount();
    }

    /**
     * Sets the {@code EmployeeId} of the {@code Person} that we are building.
     */
    public ExpensesBuilder withEmployeeId(String employeeId) {
        this.employeeId = new EmployeeId(employeeId);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public ExpensesBuilder withExpensesAmount(String expensesAmount) {
        this.expensesAmount = new ExpensesAmount(expensesAmount);
        return this;
    }


    /**
     * Builds (@code Person) with required employee's variables
     */
    public Expenses build() {
        return new Expenses(employeeId, expensesAmount);
    }

}
