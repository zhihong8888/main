package seedu.address.testutil.expenses;

import seedu.address.model.expenses.Expenses;
import seedu.address.model.expenses.ExpensesAmount;
import seedu.address.model.expenses.MedicalExpenses;
import seedu.address.model.expenses.MiscellaneousExpenses;
import seedu.address.model.expenses.TravelExpenses;
import seedu.address.model.person.EmployeeId;
/**
 * A utility class to help with building Expenses objects.
 */
public class ExpensesBuilder {

    public static final String DEFAULT_EMPLOYEEID = "000001";
    public static final String DEFAULT_EXPENSESAMOUNT = "123123";
    public static final String DEFAULT_TRAVELEXPENSES = "123123";
    public static final String DEFAULT_MEDICALEXPENSES = "123123";
    public static final String DEFAULT_MISCELLANEOUSEXPENSES = "123123";

    private EmployeeId employeeId;
    private ExpensesAmount expensesAmount;
    private TravelExpenses travelExpenses;
    private MedicalExpenses medicalExpenses;
    private MiscellaneousExpenses miscellaneousExpenses;

    public ExpensesBuilder() {
        employeeId = new EmployeeId(DEFAULT_EMPLOYEEID);
        expensesAmount = new ExpensesAmount(DEFAULT_EXPENSESAMOUNT);
        travelExpenses = new TravelExpenses(DEFAULT_TRAVELEXPENSES);
        medicalExpenses = new MedicalExpenses(DEFAULT_MEDICALEXPENSES);
        miscellaneousExpenses = new MiscellaneousExpenses(DEFAULT_MISCELLANEOUSEXPENSES);
    }

    /**
     * Initializes the ExpensesBuilder with the data of {@code expensesToCopy}.
     */
    public ExpensesBuilder(Expenses expensesToCopy) {
        employeeId = expensesToCopy.getEmployeeId();
        expensesAmount = expensesToCopy.getExpensesAmount();
        travelExpenses = expensesToCopy.getTravelExpenses();
        medicalExpenses = expensesToCopy.getMedicalExpenses();
        miscellaneousExpenses = expensesToCopy.getMiscellaneousExpenses();
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
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public ExpensesBuilder withTravelExpenses(String travelExpenses) {
        this.travelExpenses = new TravelExpenses(travelExpenses);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public ExpensesBuilder withMedicalExpenses(String medicalExpenses) {
        this.medicalExpenses = new MedicalExpenses(medicalExpenses);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public ExpensesBuilder withMiscellaneousExpenses(String miscellaneousExpenses) {
        this.miscellaneousExpenses = new MiscellaneousExpenses(miscellaneousExpenses);
        return this;
    }

    /**
     * Builds (@code Person) with required employee's variables
     */
    public Expenses build() {
        return new Expenses(employeeId, expensesAmount, travelExpenses, medicalExpenses, miscellaneousExpenses);
    }
}
