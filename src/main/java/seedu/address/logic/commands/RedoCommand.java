package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SCHEDULES;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.StorageTypes;
import seedu.address.model.schedule.VersionedScheduleList;

/**
 * Reverts the {@code model}'s address book to its previously undone state.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redo success!";
    public static final String MESSAGE_FAILURE = "No more commands to redo!";

    private int loop_count;

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.getDeletedPersonUndoRedoLoop()) {
            loop_count = DeleteCommand.NUM_STORAGE_DELETES;
        } else {
            loop_count = 1;
        }

        while (loop_count > 0) {
            loop_count--;

            if (!model.canRedoStorage()) {
                throw new CommandException(MESSAGE_FAILURE);
            }
            StorageTypes storage = model.getNextCommitType();

            switch (storage) {
            case ADDRESS_BOOK:
                if (!model.canRedoAddressBook()) {
                    throw new CommandException(MESSAGE_FAILURE);
                }
                try {
                    model.redoAddressBook();
                    model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
                } catch (VersionedScheduleList.NoRedoableStateException e) {
                    throw new CommandException(MESSAGE_FAILURE);
                }
                break;

            case SCHEDULES_LIST:
                if (!model.canRedoScheduleList()) {
                    throw new CommandException(MESSAGE_FAILURE);
                }
                try {
                    model.redoScheduleList();
                    model.updateFilteredScheduleList(PREDICATE_SHOW_ALL_SCHEDULES);
                } catch (VersionedScheduleList.NoRedoableStateException e) {
                    throw new CommandException(MESSAGE_FAILURE);
                }
                break;

            default:
                break;
            }
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
