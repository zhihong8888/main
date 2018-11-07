package seedu.address.testutil.expenses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.expenses.Expenses;
import seedu.address.model.expenses.ExpensesList;

/**
 * A utility class containing a list of {@code schedule} objects to be used in tests.
 */
public class TypicalExpenses {

    public static final String VALID_EMPLOYEEID_ALICE = "000001";
    public static final String VALID_EXPENSES_AMOUNT_ALICE = "6000";
    public static final String VALID_TRAVEL_EXPENSES_ALICE = "1000";
    public static final String VALID_MEDICAL_EXPENSES_ALICE = "2000";
    public static final String VALID_MISCELLANEOUS_EXPENSES_ALICE = "3000";

    public static final Expenses ALICE_CLAIM = new ExpensesBuilder().withEmployeeId("000001")
            .withTravelExpenses("123").withMedicalExpenses("123").withMiscellaneousExpenses("123")
            .withExpensesAmount("123", "123", "123").build();
    public static final Expenses BENSON_CLAIM = new ExpensesBuilder().withEmployeeId("000002")
            .withTravelExpenses("123").withMedicalExpenses("123").withMiscellaneousExpenses("123")
            .withExpensesAmount("123", "123", "123").build();
    public static final Expenses CARL_CLAIM = new ExpensesBuilder().withEmployeeId("000003")
            .withTravelExpenses("123").withMedicalExpenses("123").withMiscellaneousExpenses("123")
            .withExpensesAmount("123", "123", "123").build();
    public static final Expenses DANIEL_CLAIM = new ExpensesBuilder().withEmployeeId("000004")
            .withTravelExpenses("123").withMedicalExpenses("123").withMiscellaneousExpenses("123")
            .withExpensesAmount("123", "123", "123").build();
    public static final Expenses ELLE_CLAIM = new ExpensesBuilder().withEmployeeId("000005")
            .withTravelExpenses("123").withMedicalExpenses("123").withMiscellaneousExpenses("123")
            .withExpensesAmount("123", "123", "123").build();
    public static final Expenses FIONA_CLAIM = new ExpensesBuilder().withEmployeeId("000006")
            .withTravelExpenses("123").withMedicalExpenses("123").withMiscellaneousExpenses("123")
            .withExpensesAmount("123", "123", "123").build();
    public static final Expenses GEORGE_CLAIM = new ExpensesBuilder().withEmployeeId("000007")
            .withTravelExpenses("123").withMedicalExpenses("123").withMiscellaneousExpenses("123")
            .withExpensesAmount("123", "123", "123").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Expenses AMY_CLAIM = new ExpensesBuilder().withEmployeeId("000010")
            .withTravelExpenses("123").withMedicalExpenses("123").withMiscellaneousExpenses("123")
            .withExpensesAmount("123", "123", "123").build();
    public static final Expenses BOB_CLAIM = new ExpensesBuilder().withEmployeeId("000011")
            .withTravelExpenses("123").withMedicalExpenses("123").withMiscellaneousExpenses("123")
            .withExpensesAmount("123", "123", "123").build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalExpenses() {} // prevents instantiation
    /**
     * Returns an {@code ScheduleList} with all the typical schedules.
     */
    public static ExpensesList getTypicalExpensesList() {
        ExpensesList el = new ExpensesList();
        for (Expenses expenses : getTypicalExpenses()) {
            el.addExpenses(expenses);
        }
        return el;
    }

    public static List<Expenses> getTypicalExpenses() {
        return new ArrayList<>(Arrays.asList(ALICE_CLAIM, BENSON_CLAIM, CARL_CLAIM, DANIEL_CLAIM,
                ELLE_CLAIM, FIONA_CLAIM, GEORGE_CLAIM));
    }

}
