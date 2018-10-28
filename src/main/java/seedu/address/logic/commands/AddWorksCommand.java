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
 * Add work schedules to all observable employees on the address book.
 */
public class AddWorksCommand extends Command {

    public static final String COMMAND_WORD = "addWorks";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": schedule working schedule for all observable "
            + "employees in the list by specifying the date to work. "
            + "Parameters: "
            + PREFIX_SCHEDULE_DATE + "[DD/MM/YYYY] "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_SCHEDULE_DATE + "02/02/2018 ";

    public static final String MESSAGE_SUCCESS = "New working schedules added for all observable "
            + "employees for date: %1$s";

    public static final String MESSAGE_NO_PERSON = "No observable employees found in list! Try to list/find/filter "
            + "the employees you want to schedule work for";

    public static final String MESSAGE_PERSON_ALL_ADDED_WORK = "Every observable employees in the list has been "
            + "added with work on %1$s !";

    private final Date date;

    /**
     * @param date of the work to schedule
     */
    public AddWorksCommand(Date date) {
        requireAllNonNull(date);
        this.date = date;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        Type work = new Type(Type.WORK);
        boolean commit = false;

        //model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        if (model.getFilteredPersonList().size() == 0) {
            throw new CommandException(MESSAGE_NO_PERSON);
        }

        for (Person person : model.getFilteredPersonList()) {
            Schedule toAddSchedule = new Schedule(person.getEmployeeId(), work , date);
            if (!model.hasSchedule(toAddSchedule)) {
                commit = true;
                model.addSchedule(toAddSchedule);
            }
        }

        if (!commit) {
            throw new CommandException(String.format(MESSAGE_PERSON_ALL_ADDED_WORK, date));
        }

        model.commitScheduleList();
        return new CommandResult(String.format(MESSAGE_SUCCESS, date));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddWorksCommand // instanceof handles nulls
                && date.equals(((AddWorksCommand) other).date));
    }
}

