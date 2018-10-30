package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE_DATE;

import java.util.HashSet;
import java.util.Set;

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
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_SCHEDULE_DATE + "02/02/2019 ";

    public static final String MESSAGE_SUCCESS = "New working schedules added for all observable "
            + "employees for those whom are not yet added date: %1$s";

    public static final String MESSAGE_NO_PERSON = "No observable employees found in list! Try to list/find/filter "
            + "the employees you want to schedule work for";

    public static final String MESSAGE_PERSON_ALL_ADDED_WORK = "Every observable employees in the list has been "
            + "added with work on %1$s !";

    private final Set<Date> setOfDates = new HashSet<>();

    /**
     * @param date of the work to schedule
     */
    public AddWorksCommand(Set<Date> date) {
        requireAllNonNull(date);
        this.setOfDates.addAll(date);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        Type work = new Type(Type.WORK);
        boolean commit = false;

        //model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        if (model.getFilteredPersonList().size() == 0) {
            throw new CommandException(MESSAGE_NO_PERSON);
        }

        for (Date date :setOfDates) {
            for (Person person : model.getFilteredPersonList()) {
                Schedule toAddSchedule = new Schedule(person.getEmployeeId(), work , date);
                if (!model.hasSchedule(toAddSchedule)) {
                    commit = true;
                    model.addSchedule(toAddSchedule);
                }
            }
        }

        if (!commit) {
            throw new CommandException(String.format(MESSAGE_PERSON_ALL_ADDED_WORK, setOfDates));
        }

        model.commitScheduleList();
        return new CommandResult(String.format(MESSAGE_SUCCESS, setOfDates));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddWorksCommand // instanceof handles nulls
                && setOfDates.equals(((AddWorksCommand) other).setOfDates));
    }
}

