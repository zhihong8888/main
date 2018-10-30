package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.EmployeeId;
import seedu.address.model.person.EmployeeIdContainsKeywordsPredicate;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all person(s) whose name(s) matches the input "
            + "(case-insensitive) or find the person whose employee ID matches the input and displays them as a list "
            + "with index number.\nParameters: NAME OR EMPLOYEEID\n"
            + "Example: " + COMMAND_WORD + " Alex yeoh\n" + "Example: " + COMMAND_WORD + " 000001";

    public static final String MESSAGE_VALID_INPUT = "Please enter either a valid name or valid employeeId\n"
            + Name.MESSAGE_NAME_CONSTRAINTS + "\n"
            + EmployeeId.MESSAGE_EMPLOYEEID_CONSTRAINTS;

    private final NameContainsKeywordsPredicate namePredicate;
    private final EmployeeIdContainsKeywordsPredicate employeeIdPredicate;
    private boolean isInputName;
    private boolean isInputEmployeeId;

    public FindCommand(NameContainsKeywordsPredicate namePredicate,
                       EmployeeIdContainsKeywordsPredicate employeeIdPredicate) {
        this.namePredicate = namePredicate;
        this.employeeIdPredicate = employeeIdPredicate;
    }

    public void setIsInputName(boolean isInputName) {
        this.isInputName = isInputName;
    }

    public void setIsInputEmployeeId(boolean isInputEmployeeId) {
        this.isInputEmployeeId = isInputEmployeeId;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        if (isInputName && !isInputEmployeeId) {
            model.updateFilteredPersonList(namePredicate);
        } else if (!isInputName && isInputEmployeeId) {
            model.updateFilteredPersonList(employeeIdPredicate);
        }

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && (namePredicate.equals(((FindCommand) other).namePredicate)
                || employeeIdPredicate.equals(((FindCommand) other).employeeIdPredicate))); // state check
    }
}
