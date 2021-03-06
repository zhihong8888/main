package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE_DATE;
import static seedu.address.model.schedule.Date.MESSAGE_DATE_OF_SCHEDULE_BEFORE_TODAY_DATE;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddWorksCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import seedu.address.model.schedule.Date;


/**
 * Parses input arguments and creates a new {@code AddWorksCommand} object
 */
public class AddWorksCommandParser implements Parser<AddWorksCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddWorksCommand
     * and returns an AddWorksCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddWorksCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SCHEDULE_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_SCHEDULE_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddWorksCommand.MESSAGE_USAGE));
        }

        Set<Date> dateSet = ParserUtil.parseDates(argMultimap.getAllValues(PREFIX_SCHEDULE_DATE));

        Set<Date> datePastSet = new HashSet<>();
        for (Date date: dateSet) {
            if (Date.isBeforeTodayDate(date.value)) {
                datePastSet.add(date);
            }
        }
        if (!datePastSet.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_DATE_OF_SCHEDULE_BEFORE_TODAY_DATE,
                    datePastSet, Date.todayDate()));
        }

        return new AddWorksCommand(dateSet);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}



