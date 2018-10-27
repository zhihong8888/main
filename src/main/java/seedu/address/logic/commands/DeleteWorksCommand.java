package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE_DATE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.schedule.Date;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.Type;

/**
 * Deletes working schedule to all observable employees on the address book.
 */
public class DeleteWorksCommand extends Command {

    public static final String COMMAND_WORD = "deleteWorks";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes work schedules for all observable employees "
            + "in the list by specifying the date of work to delete. "
            + "Parameters: "
            + PREFIX_SCHEDULE_DATE + "[DD/MM/YYYY] "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_SCHEDULE_DATE + "02/02/2018 ";

    public static final String MESSAGE_SUCCESS = "Working schedule deleted for all observable "
            + "employees for date : %1$s";

    public static final String MESSAGE_NO_PERSON_FOUND = "No observable employees found in list! "
            + "Try to list/find/filter the employees you want to delete working schedules for";

    public static final String MESSAGE_PERSON_ALL_DELETED_WORK = "Every observable employees in the list"
            + " does not have working schedule on %1$s !";

    private final Date date;

    /**
     * @param date of the work to schedule
     */
    public DeleteWorksCommand(Date date) {
        requireAllNonNull(date);
        this.date = date;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        Type work = new Type(Type.WORK);
        boolean commit = false;

        if (model.getFilteredPersonList().size() == 0) {
            throw new CommandException(MESSAGE_NO_PERSON_FOUND);
        }

        for (Person person : model.getFilteredPersonList()) {
            Schedule toDeleteSchedule = new Schedule(person.getEmployeeId(), work , date);
            if (model.hasSchedule(toDeleteSchedule)) {
                commit = true;
                model.deleteSchedule(toDeleteSchedule);
            }
        }

        if (commit == false) {
            throw new CommandException(String.format(MESSAGE_PERSON_ALL_DELETED_WORK, date));
        }

        model.commitScheduleList();
        return new CommandResult(String.format(MESSAGE_SUCCESS, date));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteWorksCommand // instanceof handles nulls
                && date.equals(((DeleteWorksCommand) other).date));
    }
}

