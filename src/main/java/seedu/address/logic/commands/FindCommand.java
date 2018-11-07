package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.expenses.EmployeeIdExpensesContainsKeywordsPredicate;
import seedu.address.model.person.EmployeeId;
import seedu.address.model.person.EmployeeIdContainsKeywordsPredicate;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.schedule.EmployeeIdScheduleContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all person(s) whose name(s) contains the input "
            + "(case-insensitive) or find the person whose employee ID matches the input and displays them as a list "
            + "with index number.\nParameters: NAME OR EMPLOYEEID\n"
            + "Example: " + COMMAND_WORD + " Alex yeoh\n" + "Example: " + COMMAND_WORD + " 000001";

    public static final String MESSAGE_VALID_INPUT = "Please enter either a valid Name or valid Employee ID\n"
            + "Valid Name: " + Name.MESSAGE_NAME_CONSTRAINTS + "\n"
            + "Valid Employee Id: " + EmployeeId.MESSAGE_EMPLOYEEID_CONSTRAINTS;

    private final String keyword;
    private EmployeeIdContainsKeywordsPredicate employeeIdPredicate;
    private NameContainsKeywordsPredicate namePredicate;
    private boolean isInputName;
    private boolean isInputEmployeeId;

    public FindCommand(String keyword) {
        this.keyword = keyword;
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
            namePredicate = generateNamesPredicate(model, keyword);
            model.updateFilteredPersonList(namePredicate);
        } else if (!isInputName && isInputEmployeeId) {
            employeeIdPredicate = new EmployeeIdContainsKeywordsPredicate(keyword);
            model.updateFilteredPersonList(employeeIdPredicate);
        }

        EmployeeIdExpensesContainsKeywordsPredicate expensesPredicate = generateEmployeeIdExpensesPredicate(model);
        EmployeeIdScheduleContainsKeywordsPredicate schedulePredicate = generateEmployeeIdSchedulePredicate(model);
        model.updateFilteredExpensesList(expensesPredicate);
        model.updateFilteredScheduleList(schedulePredicate);

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && keyword.equals(((FindCommand) other).keyword)); // state check
    }

    /**
     * Generates a predicate that holds the employee ids of matched names or employee id via find command
     * for expenses list to be updated
     */
    public EmployeeIdExpensesContainsKeywordsPredicate generateEmployeeIdExpensesPredicate(Model model) {
        List<Person> getFilteredList = model.getFilteredPersonList();
        List<String> matchedEmployeeIds = new ArrayList<>();

        for (Person person : getFilteredList) {
            matchedEmployeeIds.add(person.getEmployeeId().value);
        }

        return new EmployeeIdExpensesContainsKeywordsPredicate(matchedEmployeeIds);
    }

    /**
     * Generates a predicate that holds the employee ids of matched names or employee id via find command
     * for schedule list to be updated
     */
    public EmployeeIdScheduleContainsKeywordsPredicate generateEmployeeIdSchedulePredicate(Model model) {
        List<Person> getFilteredList = model.getFilteredPersonList();
        List<String> matchedEmployeeIds = new ArrayList<>();

        for (Person person : getFilteredList) {
            matchedEmployeeIds.add(person.getEmployeeId().value);
        }

        return new EmployeeIdScheduleContainsKeywordsPredicate(matchedEmployeeIds);
    }

    /**
     * Generate a predicate that holds true to all the names that matches the keyword
     */
    public NameContainsKeywordsPredicate generateNamesPredicate (Model model, String keyword) {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        List<Person> getFullList = model.getFilteredPersonList();
        List<String> matchingNames = new ArrayList<>();

        for (Person name : getFullList) {
            if (name.getName().fullName.toLowerCase().contains(keyword.toLowerCase())) {
                matchingNames.add(name.getName().fullName);
            }
        }

        return new NameContainsKeywordsPredicate(matchingNames);
    }
}
