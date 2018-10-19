package seedu.address.logic.parser;

import seedu.address.logic.commands.AddRecruitmentPostCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.recruitment.Post;
import seedu.address.model.recruitment.Recruitment;

/**
 * Parses input arguments and creates a new AddRecruitmentPostCommand object
 */
public class AddRecruitmentPostCommandParser implements Parser<AddRecruitmentPostCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddRecruitmentPostCommand
     * and returns an AddRecruitmentPostCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddRecruitmentPostCommand parse(String args) throws ParseException {

        Post post = ParserUtil.parsePost(args);

        Recruitment recruitment = new Recruitment(post);

        return new AddRecruitmentPostCommand(recruitment);
    }

}
