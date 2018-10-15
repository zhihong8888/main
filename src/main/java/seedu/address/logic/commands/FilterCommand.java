package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEPARTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;

import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.DepartmentContainsKeywordsPredicate;
import seedu.address.model.person.PositionContainsKeywordsPredicate;

/**
 * Filters and lists all persons in address book whose department and/or position contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters all persons whose department or position "
            + "contain any of the specified keywords (case-insensitive) and displays them as a list with index "
            + "numbers.\nParameters: " + PREFIX_DEPARTMENT + "DEPARTMENT " + PREFIX_POSITION + "POSITION\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_DEPARTMENT + "human resource";

    private final DepartmentContainsKeywordsPredicate departmentPredicate;
    private final PositionContainsKeywordsPredicate positionPredicate;
    private final Predicate departmentAndPositionPredicates;
    private boolean isDepartmentPrefixPresent;
    private boolean isPositionPrefixPresent;

    public FilterCommand(DepartmentContainsKeywordsPredicate departmentPredicate,
            PositionContainsKeywordsPredicate positionPredicate) {
        this.departmentPredicate = departmentPredicate;
        this.positionPredicate = positionPredicate;
        departmentAndPositionPredicates = departmentPredicate.and(positionPredicate);
    }

    public void setIsDepartmentPrefixPresent (boolean isDepartmentPrefixPresent) {
        this.isDepartmentPrefixPresent = isDepartmentPrefixPresent;
    }

    public void setIsPositionPrefixPresent (boolean isPositionPrefixPresent) {
        this.isPositionPrefixPresent = isPositionPrefixPresent;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        if (isDepartmentPrefixPresent && !isPositionPrefixPresent) {
            model.updateFilteredPersonList(departmentPredicate);
        } else if (isPositionPrefixPresent && !isDepartmentPrefixPresent) {
            model.updateFilteredPersonList(positionPredicate);
        } else if (isDepartmentPrefixPresent && isPositionPrefixPresent) {
            model.updateFilteredPersonList(departmentAndPositionPredicates);
        }
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterCommand // instanceof handles nulls
                && (departmentAndPositionPredicates.equals(((FilterCommand) other)
                .departmentAndPositionPredicates))
                || (departmentPredicate.equals(((FilterCommand) other).departmentPredicate))
                || (positionPredicate.equals(((FilterCommand) other).positionPredicate))); // state check
    }
}
