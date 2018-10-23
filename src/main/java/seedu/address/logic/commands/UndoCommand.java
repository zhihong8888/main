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
 * Reverts the {@code model}'s address book to its previous state.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";

    private int loopCount;

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.getDeletedPersonUndoRedoLoop()) {
            loopCount = DeleteCommand.NUM_STORAGE_DELETES;
        } else {
            loopCount = 1;
        }

        while (loopCount > 0) {
            loopCount--;

            if (!model.canUndoStorage()) {
                throw new CommandException(MESSAGE_FAILURE);
            }
            StorageTypes storage = model.getLastCommitType();

            switch(storage) {
            case SCHEDULES_LIST:
                if (!model.canUndoScheduleList()) {
                    throw new CommandException(MESSAGE_FAILURE);
                }
                try {
                    model.undoScheduleList();
                    model.updateFilteredScheduleList(PREDICATE_SHOW_ALL_SCHEDULES);
                } catch (VersionedScheduleList.NoRedoableStateException e) {
                    throw new CommandException(MESSAGE_FAILURE);
                }
                break;

            case ADDRESS_BOOK:
                if (!model.canUndoAddressBook()) {
                    throw new CommandException(MESSAGE_FAILURE);
                }
                try {
                    model.undoAddressBook();
                    model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
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
