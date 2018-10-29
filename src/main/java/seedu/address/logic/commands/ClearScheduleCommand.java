package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelTypes;
import seedu.address.model.schedule.ScheduleList;

/**
 * Clears the schedule list.
 */
public class ClearScheduleCommand extends Command {

    public static final String COMMAND_WORD = "clearSchedule";
    public static final String MESSAGE_SUCCESS = "Schedule list has been cleared!";
    public static final String MESSAGE_FAILURE_CLEARED = "Schedule list is empty! All Schedules are cleared";


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        
        model.updateFilteredScheduleList(model.PREDICATE_SHOW_ALL_SCHEDULES);
        if (model.getFilteredScheduleList().size() > 0) {
            model.resetScheduleListData(new ScheduleList());
            model.commitScheduleList();
        } else {
            throw new CommandException(MESSAGE_FAILURE_CLEARED);
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
