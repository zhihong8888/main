package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.recruitment.RecruitmentList;

/**
 * Clears the recruitment list.
 */
public class ClearRecruitmentPostCommand extends Command {

    public static final String COMMAND_WORD = "clearRecruitmentPost";
    public static final String COMMAND_ALIAS = "crp";
    public static final String MESSAGE_SUCCESS = "Recruitment list has been cleared!";
    public static final String MESSAGE_FAILURE_CLEARED = "Recruitment list is empty! All recruitment posts are cleared";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Clears the Recruitment List\n"
            + "Example: " + COMMAND_WORD;


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        model.updateFilteredRecruitmentList(model.PREDICATE_SHOW_ALL_RECRUITMENT);
        if (model.getFilteredRecruitmentList().size() > 0) {
            model.resetRecruitmentListData(new RecruitmentList());
            model.commitRecruitmentPostList();
        } else {
            throw new CommandException(MESSAGE_FAILURE_CLEARED);
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
