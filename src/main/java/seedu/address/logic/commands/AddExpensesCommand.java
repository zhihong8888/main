package seedu.address.logic.commands;
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEEID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPENSES_AMOUNT;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.expenses.Expenses;

/**
 * Adds an expense to the Expenses List.
 */
public class AddExpensesCommand extends Command {
    public static final String COMMAND_WORD = "addExpenses";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Request Expenses. "
            + "Parameters: "
            + PREFIX_EMPLOYEEID + "EMPLOYEEID "
            + PREFIX_EXPENSES_AMOUNT + "EXPENSESAMOUNT";
    public static final String MESSAGE_SUCCESS = "Adding expenses requested.";
    public static final String MESSAGE_DUPLICATE_EXPENSES = "Expenses list contains duplicate expenses";
    private final Expenses toAddExpenses;
    public AddExpensesCommand(Expenses expenses) {
        requireNonNull(expenses);
        toAddExpenses = expenses;
    }
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (model.hasExpenses(toAddExpenses)) {
            throw new CommandException(MESSAGE_DUPLICATE_EXPENSES);
        }
        model.addExpenses(toAddExpenses);
        model.commitExpensesList();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAddExpenses));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddExpensesCommand // instanceof handles nulls
                && toAddExpenses.equals(((AddExpensesCommand) other).toAddExpenses));
    }
}
