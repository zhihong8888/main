package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEPARTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Department;
import seedu.address.model.person.DepartmentContainsKeywordsPredicate;
import seedu.address.model.person.Position;
import seedu.address.model.person.PositionContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser {

    private static final List<String> ACCEPTED_ORDERS = new ArrayList<>(Arrays.asList(FilterCommand.ASCENDING,
            FilterCommand.DESCENDING));
    private static final String DEPARTMENT_KEYWORD_VALIDATION_REGEX = "[A-Za-z ]{1,30}";
    private static final String POSITION_KEYWORD_VALIDATION_REGEX = "[A-Za-z ]{1,30}";
    private static final int INDEX_ONE = 0;

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns an FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        requireNonNull(args);

        String trimmedArgs = args.trim();
        String sortOrder = trimmedArgs.split("\\s")[INDEX_ONE];
        String[] departmentKeywords = new String[]{""};
        String[] positionKeywords = new String[]{""};
        FilterCommand filterCommand = new FilterCommand(new DepartmentContainsKeywordsPredicate(Arrays
                .asList(departmentKeywords)), new PositionContainsKeywordsPredicate(Arrays.asList(positionKeywords)),
                sortOrder);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DEPARTMENT, PREFIX_POSITION);

        if (trimmedArgs.isEmpty() || (!argMultimap.getValue(PREFIX_DEPARTMENT).isPresent()
                && !argMultimap.getValue(PREFIX_POSITION).isPresent()) || !didPrefixesAppearOnlyOnce(trimmedArgs)
                || !ACCEPTED_ORDERS.contains(sortOrder)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_DEPARTMENT).isPresent()
                && !processDepartmentKeywords(argMultimap, filterCommand)) {
            throw new ParseException(Department.MESSAGE_DEPARTMENT_KEYWORD_CONSTRAINTS);
        }

        if (argMultimap.getValue(PREFIX_POSITION).isPresent()
                && !processPositionKeywords(argMultimap, filterCommand)) {
            throw new ParseException(Position.MESSAGE_POSITION_KEYWORD_CONSTRAINTS);
        }

        return filterCommand;
    }

    /**
     * Process department keywords that are to be searched
     */
    public boolean processDepartmentKeywords(ArgumentMultimap argMultimap, FilterCommand command) {
        String trimmedDepartment;
        String[] departmentKeywords = new String[]{""};

        if (!argMultimap.getValue(PREFIX_DEPARTMENT).isPresent()) {
            command.setIsDepartmentPrefixPresent(false);
        } else if (argMultimap.getValue(PREFIX_DEPARTMENT).isPresent()) {
            trimmedDepartment = (argMultimap.getValue(PREFIX_DEPARTMENT).get().trim());
            departmentKeywords = trimmedDepartment.split("\\s+");
        }

        return validityCheckForDepartments(command, departmentKeywords);
    }

    /**
     * Validity check for departments and setting the predicate of filter command
     */
    public boolean validityCheckForDepartments(FilterCommand command, String[] keywords) {
        if (!areDepartmentKeywordsValid(keywords)) {
            return false;
        }

        command.setIsDepartmentPrefixPresent(true);
        command.setDepartmentPredicate(new DepartmentContainsKeywordsPredicate(Arrays.asList(keywords)));
        return true;
    }

    /**
     * Checks whether given keywords are valid department(s)
     */
    public boolean areDepartmentKeywordsValid(String[] keywords) {
        for (String keyword: keywords) {
            if (!keyword.matches(DEPARTMENT_KEYWORD_VALIDATION_REGEX)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Process position keywords that are to be searched
     */
    public boolean processPositionKeywords(ArgumentMultimap argMultimap, FilterCommand command) {
        String trimmedPosition;
        String[] positionKeywords = new String[]{""};

        if (!argMultimap.getValue(PREFIX_POSITION).isPresent()) {
            command.setIsPositionPrefixPresent(false);
        } else if (argMultimap.getValue(PREFIX_POSITION).isPresent()) {
            trimmedPosition = (argMultimap.getValue(PREFIX_POSITION).get().trim());
            positionKeywords = trimmedPosition.split("\\s+");
        }

        return validityCheckForPositions(command, positionKeywords);
    }

    /**
     * Validity check for positions and setting the predicate of filter command
     */
    public boolean validityCheckForPositions(FilterCommand command, String[] keywords) {
        if (!arePositionKeywordsValid(keywords)) {
            return false;
        }

        command.setIsPositionPrefixPresent(true);
        command.setPositionPredicate(new PositionContainsKeywordsPredicate(Arrays.asList(keywords)));
        return true;
    }

    /**
     * Checks whether given keywords are valid position(s)
     */
    public boolean arePositionKeywordsValid(String[] keywords) {
        for (String keyword: keywords) {
            if (!keyword.matches(POSITION_KEYWORD_VALIDATION_REGEX)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check whether prefixes except tag's prefix appeared more than once within the argument
     */
    public boolean didPrefixesAppearOnlyOnce(String argument) {
        String departmentPrefix = " " + PREFIX_DEPARTMENT.toString();
        String positionPrefix = " " + PREFIX_POSITION.toString();

        return argument.indexOf(departmentPrefix) == argument.lastIndexOf(departmentPrefix)
                && argument.indexOf(positionPrefix) == argument.lastIndexOf(positionPrefix);
    }
}
