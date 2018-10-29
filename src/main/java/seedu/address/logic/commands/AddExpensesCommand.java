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
    public static final String MESSAGE_NEGATIVE_LEFTOVER = "Cannot have negative expenses leftover.";
    public static final String MESSAGE_NOT_EDITED = "Adding expenses not edited.";
    public static final String MESSAGE_EMPLOYEE_ID_NOT_FOUND = "Employee Id not found in address book";


    private Boolean isNegativeLeftover;
    private Person toCheckEmployeeId;
    private final Expenses toAddExpenses;
    private final EditExpensesDescriptor editExpensesDescriptor;

    public AddExpensesCommand(Expenses expenses, EditExpensesDescriptor editExpensesDescriptor) {
        requireNonNull(expenses);
        requireNonNull(editExpensesDescriptor);

        toAddExpenses = expenses;
        toCheckEmployeeId = new Person(expenses.getEmployeeId());
        this.editExpensesDescriptor = new EditExpensesDescriptor(editExpensesDescriptor);
        isNegativeLeftover = false;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        String messageToShow = "";
        if (!model.hasEmployeeId(toCheckEmployeeId)) {
            throw new CommandException(MESSAGE_EMPLOYEE_ID_NOT_FOUND);
        } else if (!model.hasExpenses(toAddExpenses)) {
            if (Double.parseDouble(toAddExpenses.getExpensesAmount().toString()) < 0) {
                messageToShow = MESSAGE_NEGATIVE_LEFTOVER;
                System.out.println(Double.parseDouble(toAddExpenses.getExpensesAmount().toString()));
            } else if (Double.parseDouble(toAddExpenses.getExpensesAmount().toString()) >= 0) {
                model.addExpenses(toAddExpenses);
                model.commitExpensesList();
                messageToShow = MESSAGE_SUCCESS;
                System.out.println(Double.parseDouble(toAddExpenses.getExpensesAmount().toString()));
            }
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

            if (getIsNegativeLeftover()) {
                messageToShow = MESSAGE_NEGATIVE_LEFTOVER;
            } else if (!getIsNegativeLeftover()) {
                messageToShow = MESSAGE_SUCCESS;
                model.updateExpenses(expensesToEdit, editedExpenses);
                model.commitExpensesList();
            }
            model.updateFilteredExpensesList(PREDICATE_SHOW_ALL_EXPENSES);
        }
        return new CommandResult(String.format(messageToShow, toAddExpenses));
    }

    /**
     * Creates and returns a {@code Expenses} with the details of {@code expensesToEdit}
     * edited with {@code editExpensesDescriptor}.
     */
    private Expenses createEditedExpenses(Expenses expensesToEdit, EditExpensesDescriptor
            editExpensesDescriptor) {
        assert expensesToEdit != null;
        ExpensesAmount updatedExpensesAmount = null;

        EmployeeId updatedEmployeeId = expensesToEdit.getEmployeeId();
        try {
            updatedExpensesAmount = ParserUtil.parseExpensesAmount(modifyExpensesAmount(expensesToEdit,
                    editExpensesDescriptor));
        } catch (ParseException pe) {
            pe.printStackTrace();
        }

        return new Expenses(updatedEmployeeId, updatedExpensesAmount);
    }

    /**
     * Creates and returns a new String of Expenses with the details of {@code expensesToEdit}
     * edited with {@code editExpensesDescriptor}.
     */
    private String modifyExpensesAmount (Expenses expensesToEdit, EditExpensesDescriptor
            editExpensesDescriptor) {
        NumberFormat formatter = new DecimalFormat("#0.00");
        String newExpensesAmount = expensesToEdit.getExpensesAmount().toString();
        double updateExpensesAmount = Double.parseDouble(newExpensesAmount);
        String change = editExpensesDescriptor.getExpensesAmount().toString().replaceAll("[^0-9.-]",
                "");
        updateExpensesAmount += Double.parseDouble(change);
        if (updateExpensesAmount < 0) {
            setIsNegativeLeftover(true);
        } else if (updateExpensesAmount >= 0) {
            newExpensesAmount = String.valueOf(formatter.format(updateExpensesAmount));
        }
        return newExpensesAmount;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddExpensesCommand // instanceof handles nulls
                && toAddExpenses.equals(((AddExpensesCommand) other).toAddExpenses));
    }

    public void setIsNegativeLeftover(Boolean negativeLeftover) {
        isNegativeLeftover = negativeLeftover;
    }

    public boolean getIsNegativeLeftover() {
        return isNegativeLeftover;
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

