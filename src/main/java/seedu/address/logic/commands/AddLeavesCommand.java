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
 * Adds leave schedule to all observable employees on the address book.
 */
public class AddLeavesCommand extends Command {

    public static final String COMMAND_WORD = "addLeaves";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": schedule leave for all observable employees "
            + "in the list by specifying the date of leave to take. "
            + "Parameters: "
            + PREFIX_SCHEDULE_DATE + "[DD/MM/YYYY] "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_SCHEDULE_DATE + "02/02/2018 ";

    public static final String MESSAGE_SUCCESS = "New leaves added for all observable employees for date: %1$s";

    public static final String MESSAGE_NO_PERSON = "No observable employees found in list! Try to list/find/filter "
            + "the employees you want to schedule leaves for";

    public static final String MESSAGE_PERSON_ALL_ADDED_LEAVE = "Every observable employees in the list has been "
            + "added with the leave on %1$s !";

    private final Date date;

    /**
     * @param date of the leave to schedule
     */
    public AddLeavesCommand(Date date) {
        requireAllNonNull(date);
        this.date = date;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        Type leave = new Type(Type.LEAVE);
        boolean commit = false;

        //model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        if (model.getFilteredPersonList().size() == 0) {
            throw new CommandException(MESSAGE_NO_PERSON);
        }

        for (Person person : model.getFilteredPersonList()) {
            Schedule toAddSchedule = new Schedule(person.getEmployeeId(), leave , date);
            if (!model.hasSchedule(toAddSchedule)) {
                model.addSchedule(toAddSchedule);
                commit = true;
            }
        }

        if (!commit) {
            throw new CommandException(String.format(MESSAGE_PERSON_ALL_ADDED_LEAVE, date));
        }

        model.commitScheduleList();
        return new CommandResult(String.format(MESSAGE_SUCCESS, date));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddLeavesCommand // instanceof handles nulls
                && date.equals(((AddLeavesCommand) other).date));
    }
}

