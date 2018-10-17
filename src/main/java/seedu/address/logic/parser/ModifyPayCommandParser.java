package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BONUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SALARY;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ModifyPayCommand;
import seedu.address.logic.commands.ModifyPayCommand.ModSalaryDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class ModifyPayCommandParser implements Parser<ModifyPayCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ModifyPayCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SALARY, PREFIX_BONUS);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ModifyPayCommand.MESSAGE_USAGE), pe);
        }

        ModSalaryDescriptor modSalaryDescriptor = new ModSalaryDescriptor();
        if (argMultimap.getValue(PREFIX_BONUS).isPresent()) {
            modSalaryDescriptor.setBonus(ParserUtil.parseBonus(argMultimap.getValue(PREFIX_BONUS).get()));
        }
        if (argMultimap.getValue(PREFIX_SALARY).isPresent()) {
            modSalaryDescriptor.setSalary(ParserUtil.parseSalary(argMultimap.getValue(PREFIX_SALARY).get()));
        }
        if (!modSalaryDescriptor.isAnyFieldEdited()) {
            throw new ParseException(ModifyPayCommand.MESSAGE_NOT_MODIFIED);
        }

        return new ModifyPayCommand(index, modSalaryDescriptor);
    }
}
