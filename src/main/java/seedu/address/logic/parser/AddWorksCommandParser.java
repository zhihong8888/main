package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.AddWorksCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import seedu.address.model.schedule.Date;

/**
 * Parses input arguments and creates a new {@code AddLeavesCommand} object
 */
public class AddWorksCommandParser implements Parser<AddWorksCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddLeavesCommand
     * and returns an AddLeavesCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public static final String MESSAGE_COMMAND_USAGE_FORMAT = "\nExample: addWorks 02/02/2018";

    /**
     * Parses the given {@code String} of arguments in the context of the AddLeavesCommand
     * and returns an AddLeavesCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddWorksCommand parse(String args) throws ParseException {
        try {
            Date date = ParserUtil.parseDate(args);
            return new AddWorksCommand(date);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, Date.MESSAGE_DATE_CONSTRAINTS_DEFAULT)
                            + MESSAGE_COMMAND_USAGE_FORMAT, pe);
        }
    }

}

