package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ModifyAllPayCommand;
import seedu.address.logic.commands.ModifyAllPayCommand.ModSalaryDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BONUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SALARY;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class ModifyAllPayCommandParser implements Parser<ModifyAllPayCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ModifyAllPayCommand parse(String args) throws ParseException {
        final String defaultIndex = "1";
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SALARY, PREFIX_BONUS);

        Index index = ParserUtil.parseIndex(defaultIndex);


        ModSalaryDescriptor modSalaryDescriptor = new ModSalaryDescriptor();
        if (argMultimap.getValue(PREFIX_BONUS).isPresent()) {
            modSalaryDescriptor.setBonus(ParserUtil.parseBonus(argMultimap.getValue(PREFIX_BONUS).get()));
        }

        if (argMultimap.getValue(PREFIX_SALARY).isPresent()) {
            modSalaryDescriptor.setSalary(ParserUtil.parseSalary(argMultimap.getValue(PREFIX_SALARY).get()));
        }
        if (!modSalaryDescriptor.isAnyFieldEdited()) {
            throw new ParseException(ModifyAllPayCommand.MESSAGE_NOT_MODIFIED);
        }

        return new ModifyAllPayCommand(index, modSalaryDescriptor);
    }
}
