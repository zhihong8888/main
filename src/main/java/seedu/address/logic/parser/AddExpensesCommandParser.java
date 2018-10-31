package seedu.address.logic.parser;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEEID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPENSES_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICAL_EXPENSES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MISCELLANEOUS_EXPENSES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRAVEL_EXPENSES;


import java.util.stream.Stream;

import seedu.address.logic.commands.AddExpensesCommand;
import seedu.address.logic.commands.AddExpensesCommand.EditExpensesDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.expenses.Expenses;
import seedu.address.model.expenses.ExpensesAmount;
import seedu.address.model.expenses.MedicalExpenses;
import seedu.address.model.expenses.MiscellaneousExpenses;
import seedu.address.model.expenses.TravelExpenses;
import seedu.address.model.person.EmployeeId;

/**
 * Parses input arguments and creates a new AddExpensesCommand object
 */
public class AddExpensesCommandParser implements Parser<AddExpensesCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddExpensesCommand
     * and returns an AddExpensesCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddExpensesCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EMPLOYEEID, PREFIX_EXPENSES_AMOUNT, PREFIX_TRAVEL_EXPENSES,
                        PREFIX_MEDICAL_EXPENSES, PREFIX_MISCELLANEOUS_EXPENSES);
        if (!arePrefixesPresent(argMultimap, PREFIX_EMPLOYEEID, PREFIX_EXPENSES_AMOUNT, PREFIX_MEDICAL_EXPENSES,
                PREFIX_MISCELLANEOUS_EXPENSES) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddExpensesCommand.MESSAGE_USAGE));
        }
        EmployeeId employeeId = ParserUtil.parseEmployeeId(argMultimap.getValue
                (PREFIX_EMPLOYEEID).get());
        ExpensesAmount expensesAmount = ParserUtil.parseExpensesAmount(argMultimap.getValue(PREFIX_EXPENSES_AMOUNT)
                .get());
        TravelExpenses travelExpenses = ParserUtil.parseTravelExpenses(argMultimap.getValue(PREFIX_TRAVEL_EXPENSES)
                .get());
        MedicalExpenses medicalExpenses = ParserUtil.parseMedicalExpenses(argMultimap.getValue(PREFIX_MEDICAL_EXPENSES)
                .get());
        MiscellaneousExpenses miscellaneousExpenses =
                ParserUtil.parseMiscellaneousExpenses(argMultimap.getValue(PREFIX_MISCELLANEOUS_EXPENSES).get());
        Expenses expenses = new Expenses (employeeId, expensesAmount, travelExpenses, medicalExpenses,
                miscellaneousExpenses);

        EditExpensesDescriptor editExpensesDescriptor = new EditExpensesDescriptor();
        if (argMultimap.getValue(PREFIX_EXPENSES_AMOUNT).isPresent()) {
            editExpensesDescriptor.setExpensesAmount(ParserUtil.parseExpensesAmount(
                    argMultimap.getValue(PREFIX_EXPENSES_AMOUNT).get()));
        }
        if (!editExpensesDescriptor.isAnyFieldEdited()) {
            throw new ParseException(AddExpensesCommand.MESSAGE_NOT_EDITED);
        }
        return new AddExpensesCommand(expenses, editExpensesDescriptor);
    }
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
