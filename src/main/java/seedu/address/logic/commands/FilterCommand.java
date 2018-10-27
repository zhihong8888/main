package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEPARTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.DepartmentContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PositionContainsKeywordsPredicate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Filters and lists all persons in address book whose department and/or position contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters all persons whose department or position "
            + "contain any of the specified keywords (case-insensitive) and displays them as a sorted list in either "
            + "ascending or descending order with index numbers.\nParameters: ORDER " + PREFIX_DEPARTMENT
            + "DEPARTMENT AND/OR " + PREFIX_POSITION + "POSITION\n"
            + "Example: " + "dsc " + COMMAND_WORD + " " + PREFIX_DEPARTMENT + "human resource";

    public static final String ASCENDING = "asc";
    public static final String DESCENDING = "dsc";

    private final String sortOrder;
    private DepartmentContainsKeywordsPredicate departmentPredicate;
    private PositionContainsKeywordsPredicate positionPredicate;

    private boolean isDepartmentPrefixPresent;
    private boolean isPositionPrefixPresent;

    public FilterCommand(DepartmentContainsKeywordsPredicate departmentPredicate,
            PositionContainsKeywordsPredicate positionPredicate, String sortOrder) {
        this.departmentPredicate = departmentPredicate;
        this.positionPredicate = positionPredicate;
        this.sortOrder = sortOrder;
    }

    public void setIsDepartmentPrefixPresent(boolean isDepartmentPrefixPresent) {
        this.isDepartmentPrefixPresent = isDepartmentPrefixPresent;
    }

    public void setIsPositionPrefixPresent(boolean isPositionPrefixPresent) {
        this.isPositionPrefixPresent = isPositionPrefixPresent;
    }

    public void setDepartmentPredicate(DepartmentContainsKeywordsPredicate departmentPredicate) {
        this.departmentPredicate = departmentPredicate;
    }

    public void setPositionPredicate(PositionContainsKeywordsPredicate positionPredicate) {
        this.positionPredicate = positionPredicate;
    }

    public String listAvailableDepartments(Model model) {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        List<Person> getFullList = model.getFilteredPersonList();
        Set<String> allDepartments = new HashSet<>();

        for (Person department : getFullList) {
            allDepartments.add(department.getDepartment().toString());
        }

        String availableDepartments = String.join(", ", allDepartments);

        return availableDepartments;
    }

    public String listAvailablePositions(Model model) {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        List<Person> getFullList = model.getFilteredPersonList();
        Set<String> allPositions = new HashSet<>();

        for (Person position : getFullList) {
            allPositions.add(position.getPosition().toString());
        }

        String availablePositions = String.join(", ", allPositions);

        return availablePositions;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        String allAvailableDepartments = "Available Departments: " + listAvailableDepartments(model);
        String allAvailablePositions = "Available Positions: " + listAvailablePositions(model);

        if (isDepartmentPrefixPresent && !isPositionPrefixPresent) {
            model.updateFilteredPersonList(departmentPredicate, sortOrder);
        } else if (isPositionPrefixPresent && !isDepartmentPrefixPresent) {
            model.updateFilteredPersonList(positionPredicate, sortOrder);
        } else if (isDepartmentPrefixPresent && isPositionPrefixPresent) {
            model.updateFilteredPersonList(departmentPredicate.and(positionPredicate), sortOrder);
        }

        if (model.getFilteredPersonList().isEmpty() && isDepartmentPrefixPresent && !isPositionPrefixPresent) {
            return new CommandResult((String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW,
                    model.getFilteredPersonList().size())) + "\n" + allAvailableDepartments);
        } else if (model.getFilteredPersonList().isEmpty() && !isDepartmentPrefixPresent && isPositionPrefixPresent) {
            return new CommandResult((String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW,
                    model.getFilteredPersonList().size())) + "\n" + allAvailablePositions);
        } else if (model.getFilteredPersonList().isEmpty() && isDepartmentPrefixPresent && isPositionPrefixPresent) {
            return new CommandResult((String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW,
                    model.getFilteredPersonList().size())) + "\n" + allAvailableDepartments + "\n" +
                    allAvailablePositions);
        }

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterCommand // instanceof handles nulls
                && (departmentPredicate.and(positionPredicate).equals(((FilterCommand) other)
                .departmentPredicate.and(positionPredicate))
                || (departmentPredicate.equals(((FilterCommand) other).departmentPredicate))
                || (positionPredicate.equals(((FilterCommand) other).positionPredicate)))); // state check
    }
}
