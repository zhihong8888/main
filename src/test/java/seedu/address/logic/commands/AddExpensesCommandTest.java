package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.addressbook.ReadOnlyAddressBook;
import seedu.address.model.expenses.Expenses;
import seedu.address.model.expenses.ExpensesList;
import seedu.address.model.expenses.ReadOnlyExpensesList;
import seedu.address.model.person.Person;
import seedu.address.model.recruitment.ReadOnlyRecruitmentList;
import seedu.address.model.recruitment.Recruitment;
import seedu.address.model.schedule.ReadOnlyScheduleList;
import seedu.address.model.schedule.Schedule;
import seedu.address.testutil.expenses.ExpensesBuilder;

public class AddExpensesCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullExpenses_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddExpensesCommand(null);
    }

    @Test
    public void execute_expensesAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingExpensesAdded modelStub = new ModelStubAcceptingExpensesAdded();
        Expenses validExpenses = new ExpensesBuilder().build();

        CommandResult commandResult = new AddExpensesCommand(validExpenses).execute(modelStub, commandHistory);

        assertEquals(String.format(AddExpensesCommand.MESSAGE_SUCCESS, validExpenses), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validExpenses), modelStub.expensesAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateExpenses_throwsCommandException() throws Exception {
        Expenses validExpenses = new ExpensesBuilder().build();
        AddExpensesCommand addExpensesCommand = new AddExpensesCommand(validExpenses);
        ModelStub modelStub = new ModelStubWithExpenses(validExpenses);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddExpensesCommand.MESSAGE_DUPLICATE_EXPENSES);
        addExpensesCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Expenses alice = new ExpensesBuilder().withEmployeeId("000001").withExpensesAmount("123").build();
        Expenses bob = new ExpensesBuilder().withEmployeeId("000002").withExpensesAmount("321").build();
        AddExpensesCommand addAliceCommand = new AddExpensesCommand(alice);
        AddExpensesCommand addBobCommand = new AddExpensesCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddExpensesCommand addAliceCommandCopy = new AddExpensesCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {

        @Override
        public void addExpenses(Expenses expenses) {
            throw new AssertionError("This method should not be called."); }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be "
                    + "called.");
        }

        @Override
        public void addRecruitment(Recruitment recruitment) {
            throw new AssertionError("This method should not be "
                    + "called.");
        }

        @Override
        public void addSchedule(Schedule schedule) {
            throw new AssertionError("This method should not be "
                    + "called.");
        }


        //------------------------------------------------
        @Override
        public void resetAddressBookData(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetDataExpenses(ReadOnlyExpensesList newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetRecruitmentListData(ReadOnlyRecruitmentList newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetScheduleListData(ReadOnlyScheduleList newData) {
            throw new AssertionError("This method should not be called.");
        }

        //------------------------------------------------
        @Override
        public ReadOnlyExpensesList getExpensesList() {
            throw new AssertionError("This method should not be called."); }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should "
                    + "not be called.");
        }

        @Override
        public ReadOnlyRecruitmentList getRecruitmentList() {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public ReadOnlyScheduleList getScheduleList() {
            throw new AssertionError("This method should "
                    + "not be called.");
        }

        //------------------------------------------------
        @Override
        public boolean hasExpenses(Expenses expenses) {
            throw new AssertionError("This method should not be called."); }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should "
                    + "not be called.");
        }

        @Override
        public boolean hasRecruitment(Recruitment recruitment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasEmployeeId(Person person) {
            throw new AssertionError("This method "
                    + "should not be called.");
        }

        @Override
        public boolean hasSchedule(Schedule schedule) {
            throw new AssertionError("This method should "
                    + "not be called.");
        }

        //------------------------------------------------
        @Override
        public void deleteExpenses(Expenses target) {
            throw new AssertionError("This method should not be called."); }
        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should "
                    + "not be called.");
        }

        @Override
        public void deleteRecruitment(Recruitment target) {
            throw new AssertionError("This method should "
                    + "not be called.");
        }

        @Override
        public void deleteSchedule(Schedule target) {
            throw new AssertionError("This method should "
                    + "not be called.");
        }


        //------------------------------------------------
        @Override
        public void updateExpenses(Expenses target, Expenses editedExpenses) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updatePerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateRecruitment(Recruitment target, Recruitment editedRecruitment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateSchedule(Schedule target, Schedule editedSchedule) {
            throw new AssertionError("This method should not be called.");
        }


        //------------------------------------------------
        @Override
        public ObservableList<Expenses> getFilteredExpensesList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Recruitment> getFilteredRecruitmentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Schedule> getFilteredScheduleList() {
            throw new AssertionError("This method should not be called.");
        }

        //------------------------------------------------
        @Override
        public void updateFilteredExpensesList(Predicate<Expenses> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredRecruitmentList(Predicate<Recruitment> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredScheduleList(Predicate<Schedule> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        //------------------------------------------------
        @Override
        public boolean canUndoAddressBook() {
            throw new AssertionError("This method should "
                    + "not be called.");
        }

        @Override
        public boolean canUndoExpensesList() {
            throw new AssertionError("This method should "
                    + "not be called.");
        }

        @Override
        public boolean canUndoRecruitmentList() {
            throw new AssertionError("This method should "
                    + "not be called.");
        }

        @Override
        public boolean canUndoScheduleList() {
            throw new AssertionError("This method should "
                    + "not be called.");
        }

        //------------------------------------------------
        @Override
        public boolean canRedoAddressBook() {
            throw new AssertionError("This method should "
                    + "not be called.");
        }

        @Override
        public boolean canRedoExpensesList() {
            throw new AssertionError("This method should "
                    + "not be called.");
        }

        @Override
        public boolean canRedoRecruitmentList() {
            throw new AssertionError("This method should "
                    + "not be called.");
        }

        @Override
        public boolean canRedoScheduleList() {
            throw new AssertionError("This method should "
                    + "not be called.");
        }

        //------------------------------------------------
        @Override
        public void undoAddressBook() {
            throw new AssertionError("This method should "
                    + "not be called.");
        }

        @Override
        public void undoExpensesList() {
            throw new AssertionError("This method should "
                    + "not be called.");
        }

        @Override
        public void undoRecruitmentList() {
            throw new AssertionError("This method should "
                    + "not be called.");
        }

        @Override
        public void undoScheduleList() {
            throw new AssertionError("This method should "
                    + "not be called.");
        }

        //------------------------------------------------
        @Override
        public void redoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoExpensesList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoRecruitmentList() {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public void redoScheduleList() {
            throw new AssertionError("This method should not be called.");
        }

        //------------------------------------------------
        @Override
        public void commitAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitExpensesList() {
            throw new AssertionError("This method should not be called."); }

        @Override
        public void commitRecruitmentList() {
            throw new AssertionError("This method should "
                    + "not be called.");
        }

        @Override
        public void commitScheduleList() {
            throw new AssertionError("This method should not be called.");
        }

    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithExpenses extends ModelStub {
        private final Expenses expenses;

        ModelStubWithExpenses(Expenses expenses) {
            requireNonNull(expenses);
            this.expenses = expenses;
        }

        @Override
        public boolean hasExpenses(Expenses expenses) {
            requireNonNull(expenses);
            return this.expenses.isSameExpensesRequest(expenses);
        }
    }

    /**
     * A Model stub that always accept the expenses being added.
     */
    private class ModelStubAcceptingExpensesAdded extends ModelStub {
        final ArrayList<Expenses> expensesAdded = new ArrayList<>();

        @Override
        public boolean hasExpenses(Expenses expenses) {
            requireNonNull(expenses);
            return expensesAdded.stream().anyMatch(expenses::isSameExpensesRequest);
        }

        @Override
        public void addExpenses(Expenses expenses) {
            requireNonNull(expenses);
            expensesAdded.add(expenses);
        }

        @Override
        public void commitExpensesList() {
            // called by {@code AddExpensesCommand#execute()}
        }

        @Override
        public ReadOnlyExpensesList getExpensesList() {
            return new ExpensesList();
        }
    }

}
