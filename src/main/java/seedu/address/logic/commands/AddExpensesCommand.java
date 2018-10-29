package seedu.address.logic.commands;
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEEID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPENSES_AMOUNT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EXPENSES;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.expenses.EmployeeIdExpensesContainsKeywordsPredicate;
import seedu.address.model.expenses.Expenses;
import seedu.address.model.expenses.ExpensesAmount;
import seedu.address.model.person.EmployeeId;
import seedu.address.model.person.Person;

/**
 * Adds an expense to the Expenses List.
 */
public class AddExpensesCommand extends Command {
    public static final String COMMAND_WORD = "addExpenses";
    public static final String COMMAND_ALIAS = "ae";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Request Expenses. "
            + "Parameters: "
            + PREFIX_EMPLOYEEID + "EMPLOYEEID "
            + PREFIX_EXPENSES_AMOUNT + "EXPENSESAMOUNT";

    public static final String MESSAGE_SUCCESS = "Adding expenses requested.";
    public static final String MESSAGE_NOT_EDITED = "Adding expenses not edited.";
    public static final String MESSAGE_DUPLICATE_EXPENSES = "Expenses list contains duplicate expenses";
    public static final String MESSAGE_EMPLOYEE_ID_NOT_FOUND = "Employee Id not found in address book";

    private Person toCheckEmployeeId;
    private final Expenses toAddExpenses;
    private final EditExpensesDescriptor editExpensesDescriptor;

    public AddExpensesCommand(Expenses expenses, EditExpensesDescriptor editExpensesDescriptor) {
        requireNonNull(expenses);
        requireNonNull(editExpensesDescriptor);

        toAddExpenses = expenses;
        toCheckEmployeeId = new Person(expenses.getEmployeeId());
        this.editExpensesDescriptor = new EditExpensesDescriptor(editExpensesDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (!model.hasEmployeeId(toCheckEmployeeId)) {
            throw new CommandException(MESSAGE_EMPLOYEE_ID_NOT_FOUND);
        } else if (!model.hasExpenses(toAddExpenses)) {
            model.addExpenses(toAddExpenses);
            model.commitExpensesList();
        } else if (model.hasExpenses(toAddExpenses)) {
            EmployeeIdExpensesContainsKeywordsPredicate predicatEmployeeId;
            List<String> employeeIdList = new ArrayList<>();
            List<Expenses> lastShownListExpenses;

            employeeIdList.add(toCheckEmployeeId.getEmployeeId().value);
            predicatEmployeeId = new EmployeeIdExpensesContainsKeywordsPredicate(employeeIdList);

            model.updateFilteredExpensesList(predicatEmployeeId);
            lastShownListExpenses = model.getFilteredExpensesList();

            Expenses expensesToEdit = lastShownListExpenses.get(0);
            Expenses editedExpenses = createEditedExpenses(expensesToEdit, editExpensesDescriptor);


            model.updateExpenses(expensesToEdit, editedExpenses);
            model.updateFilteredExpensesList(PREDICATE_SHOW_ALL_EXPENSES);

            model.commitExpensesList();
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAddExpenses));
    }

    /**
     * Creates and returns a {@code Expenses} with the details of {@code expensesToEdit}
     * edited with {@code editExpensesDescriptor}.
     */
    private static Expenses createEditedExpenses(Expenses expensesToEdit, EditExpensesDescriptor
            editExpensesDescriptor) {
        assert expensesToEdit != null;
        ExpensesAmount updatedExpensesAmount = null;

        EmployeeId updatedEmployeeId = expensesToEdit.getEmployeeId();
        try {
            updatedExpensesAmount = ParserUtil.parseExpensesAmount(modifyExpensesAmount(expensesToEdit,
                    editExpensesDescriptor));
            System.out.println(updatedExpensesAmount);
        } catch (ParseException pe) {
            pe.printStackTrace();
            System.out.println(999);
        }

        return new Expenses(updatedEmployeeId, updatedExpensesAmount);
    }

    /**
     * Creates and returns a new String of Expenses with the details of {@code expensesToEdit}
     * edited with {@code editExpensesDescriptor}.
     */
    private static String modifyExpensesAmount (Expenses expensesToEdit, EditExpensesDescriptor
            editExpensesDescriptor) {
        NumberFormat formatter = new DecimalFormat("#0.00");
        String newExpensesAmount = expensesToEdit.getExpensesAmount().toString();
        double updateExpensesAmount = Double.parseDouble(newExpensesAmount);
        String change = editExpensesDescriptor.getExpensesAmount().toString().replaceAll("[^0-9.-]",
                "");
        System.out.println(updateExpensesAmount);
        System.out.println(change);
        updateExpensesAmount += Double.parseDouble(change);
        newExpensesAmount = String.valueOf(formatter.format(updateExpensesAmount));
        System.out.println(newExpensesAmount);
        return newExpensesAmount;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddExpensesCommand // instanceof handles nulls
                && toAddExpenses.equals(((AddExpensesCommand) other).toAddExpenses));
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditExpensesDescriptor {
        private ExpensesAmount expensesAmount;

        public EditExpensesDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditExpensesDescriptor(EditExpensesDescriptor toCopy) {
            setExpensesAmount(toCopy.expensesAmount);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(expensesAmount);
        }

        public void setExpensesAmount(ExpensesAmount expensesAmount) {
            this.expensesAmount = expensesAmount;
        }

        public Optional<ExpensesAmount> getExpensesAmount() {
            return Optional.ofNullable(expensesAmount);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditExpensesDescriptor)) {
                return false;
            }

            // state check
            EditExpensesDescriptor e = (EditExpensesDescriptor) other;

            return getExpensesAmount().equals(e.getExpensesAmount());
        }
    }
}

