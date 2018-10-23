package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EXPENSES;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SCHEDULES;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.StorageTypes;
import seedu.address.model.expenses.VersionedExpensesList;
import seedu.address.model.schedule.VersionedScheduleList;

/**
 * Reverts the {@code model}'s address book to its previous state.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        boolean undoCommandCommit = false;

        StorageTypes storage = model.getLastCommitType();

        switch(storage) {
        case EXPENSES_LIST:
            if (model.canUndoExpensesList()) {
                try {
                    model.undoExpensesList();
                    model.updateFilteredExpensesList(PREDICATE_SHOW_ALL_EXPENSES);
                } catch (VersionedExpensesList.NoRedoableStateException e) {
                    throw new CommandException(MESSAGE_FAILURE);
                }
                undoCommandCommit = true;
            }
            break;

        case SCHEDULES_LIST:
            if (model.canUndoScheduleList()) {
                try {
                    model.undoScheduleList();
                    model.updateFilteredScheduleList(PREDICATE_SHOW_ALL_SCHEDULES);
                } catch (VersionedScheduleList.NoRedoableStateException e) {
                    throw new CommandException(MESSAGE_FAILURE);
                }
                undoCommandCommit = true;
            }
            break;

        case ADDRESS_BOOK:
            if (model.canUndoAddressBook()) {
                try {
                    model.undoAddressBook();
                    model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
                } catch (VersionedScheduleList.NoRedoableStateException e) {
                    throw new CommandException(MESSAGE_FAILURE);
                }
                undoCommandCommit = true;
            }
            break;

        default:
            break;
        }

        /*
         * Commands above must be a success, otherwise there must be no more commands to undo.
         */
        if (!undoCommandCommit) {
            throw new CommandException(MESSAGE_FAILURE);
        }


        return new CommandResult(MESSAGE_SUCCESS);
    }
}
