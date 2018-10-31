package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEEID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE_YEAR;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.EmployeeId;
import seedu.address.model.person.Person;
import seedu.address.model.schedule.EmployeeIdScheduleContainsKeywordsPredicate;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.Type;
import seedu.address.model.schedule.Year;

/**
 * Calculate leaves of schedule to a employee on the address book.
 */
public class CalculateLeavesCommand extends Command {

    public static final String COMMAND_WORD = "calculateLeaves";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Calculate total leaves schedule for the year "
            + "by specifying the Employee number and year. "
            + "\nParameters: "
            + PREFIX_EMPLOYEEID + "[6digit] "
            + PREFIX_SCHEDULE_YEAR + "[YYYY]"
            + "\nExample: "
            + COMMAND_WORD + " "
            + PREFIX_EMPLOYEEID + "000001 "
            + PREFIX_SCHEDULE_YEAR + "2019";

    public static final String MESSAGE_SUCCESS = "Number of leaves scheduled for Employee %1$s year %2$s is: %3$s.";

    public static final String MESSAGE_NO_SCHEDULE_FOUND = "No Schedule Found for the employee!";
    public static final String MESSAGE_EMPLOYEE_ID_NOT_FOUND = "Employee Id not found in system!";

    private final Year year;
    private final EmployeeId employeeId;
    private Person toCheckEmployeeId;

    /**
     * Calculate leaves
     */
    public CalculateLeavesCommand(EmployeeId id, Year year) {
        requireAllNonNull(id);
        requireAllNonNull(year);
        this.employeeId = id;
        this.year = year;
        toCheckEmployeeId = new Person(id);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        int numLeaves = 0;

        if (!model.hasEmployeeId(toCheckEmployeeId)) {
            throw new CommandException(MESSAGE_EMPLOYEE_ID_NOT_FOUND);
        }

        List<String> employeeIdList = new ArrayList<>();
        employeeIdList.add(employeeId.value);
        EmployeeIdScheduleContainsKeywordsPredicate employeeIdPredicate =
                new EmployeeIdScheduleContainsKeywordsPredicate(employeeIdList);
        model.updateFilteredScheduleList(employeeIdPredicate);

        if (model.getFilteredScheduleList().size() == 0) {
            throw new CommandException(MESSAGE_NO_SCHEDULE_FOUND);
        }

        for (Schedule schedule : model.getFilteredScheduleList()) {
            if (schedule.getScheduleYear().equals(year.toString())
                    && schedule.getType().toString().equals(Type.LEAVE)) {
                numLeaves++;
            }
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, employeeId, year, numLeaves));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CalculateLeavesCommand // instanceof handles nulls
                && year.equals(((CalculateLeavesCommand) other).year)
                && employeeId.equals(((CalculateLeavesCommand) other).employeeId));
    }
}

