package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE_DATE;

import java.util.Set;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.schedule.Date;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.Type;


/**
 * Deletes leave schedule to all observable employees on the address book.
 */
public class DeleteLeavesCommand extends Command {

    public static final String COMMAND_WORD = "deleteLeaves";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes leave schedules for all observable employees "
            + "in the list by specifying the date of leave to delete. "
            + "Parameters: "
            + PREFIX_SCHEDULE_DATE + "[DD/MM/YYYY] "
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_SCHEDULE_DATE + "02/02/2018 ";

    public static final String MESSAGE_SUCCESS = "Leaves deleted for all observable employees for date : %1$s";

    public static final String MESSAGE_NO_PERSON_FOUND = "No observable employees found in list! "
            + "Try to list/find/filter the employees you want to delete leaves for";

    public static final String MESSAGE_PERSON_ALL_DELETED_LEAVE = "Every observable employees in the list"
            + " does not have leave on %1$s !";

    private final Set<Date> setOfDates;

    /**
     * @param date of the leave to schedule
     */
    public DeleteLeavesCommand(Set<Date> date) {
        requireAllNonNull(date);
        this.setOfDates = date;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        Type leave = new Type(Type.LEAVE);
        boolean commit = false;

        if (model.getFilteredPersonList().size() == 0) {
            throw new CommandException(MESSAGE_NO_PERSON_FOUND);
        }
        for (Date date : setOfDates) {
            for (Person person : model.getFilteredPersonList()) {
                Schedule toDeleteSchedule = new Schedule(person.getEmployeeId(), leave , date);
                if (model.hasSchedule(toDeleteSchedule)) {
                    model.deleteSchedule(toDeleteSchedule);
                    commit = true;
                }
            }
        }

        if (!commit) {
            throw new CommandException(String.format(MESSAGE_PERSON_ALL_DELETED_LEAVE, setOfDates));
        }

        model.commitScheduleList();
        return new CommandResult(String.format(MESSAGE_SUCCESS, setOfDates));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteLeavesCommand // instanceof handles nulls
                && setOfDates.equals(((DeleteLeavesCommand) other).setOfDates));
    }
}

