package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEEID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE_TYPE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.Type;

/**
 * Adds a schedule to a employee on the address book.
 */
public class AddScheduleCommand extends Command {

    public static final String COMMAND_WORD = "addSchedule";
    public static final String COMMAND_ALIAS = "as";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": schedule work "
            + "by specifying the Employee number, date and type. "
            + "\nParameters: "
            + PREFIX_EMPLOYEEID + "EMPLOYEEID "
            + PREFIX_SCHEDULE_DATE + "DD/MM/YYYY "
            + PREFIX_SCHEDULE_TYPE + "[WORK/LEAVE] \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EMPLOYEEID + "000001 "
            + PREFIX_SCHEDULE_DATE + "02/02/2019 "
            + PREFIX_SCHEDULE_TYPE + "LEAVE";

    public static final String MESSAGE_SUCCESS = "New schedule added: %1$s";
    public static final String MESSAGE_DUPLICATE_SCHEDULE = "This schedule already exists in the address book";
    public static final String MESSAGE_HAS_WORK = "This employee has work scheduled on same date!";
    public static final String MESSAGE_HAS_LEAVE = "This employee has leave scheduled on same date!";
    public static final String MESSAGE_EMPLOYEE_ID_NOT_FOUND = "Employee Id not found in address book";

    private Person toCheckEmployeeId;
    private final Schedule toAddSchedule;

    /**
     * @param schedule of the employee to be added to
     */
    public AddScheduleCommand(Schedule schedule) {
        requireAllNonNull(schedule);
        this.toAddSchedule = schedule;
        toCheckEmployeeId = new Person(schedule.getEmployeeId());
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        Type type = toAddSchedule.getType();
        Type work = new Type(Type.WORK);
        Type leave = new Type(Type.LEAVE);

        if (!model.hasEmployeeId(toCheckEmployeeId)) {
            throw new CommandException(MESSAGE_EMPLOYEE_ID_NOT_FOUND);

        } else if (model.hasSchedule(toAddSchedule)) {
            throw new CommandException(MESSAGE_DUPLICATE_SCHEDULE);

        } else if (type == work) {
            Schedule toCheckSchedule = new Schedule(toAddSchedule.getEmployeeId(), leave,
                    toAddSchedule.getScheduleDate());
            if (model.hasSchedule(toCheckSchedule)) {
                throw new CommandException(MESSAGE_HAS_LEAVE);
            }

        } else if (type == leave) {
            Schedule toCheckSchedule = new Schedule(toAddSchedule.getEmployeeId(), work,
                    toAddSchedule.getScheduleDate());
            if (model.hasSchedule(toCheckSchedule)) {
                throw new CommandException(MESSAGE_HAS_WORK);
            }
        }

        model.addSchedule(toAddSchedule);
        model.commitScheduleList();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAddSchedule));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddScheduleCommand // instanceof handles nulls
                && toAddSchedule.equals(((AddScheduleCommand) other).toAddSchedule));
    }
}

