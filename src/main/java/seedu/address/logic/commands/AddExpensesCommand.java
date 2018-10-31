package seedu.address.logic.commands;
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEEID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICAL_EXPENSES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MISCELLANEOUS_EXPENSES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRAVEL_EXPENSES;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EXPENSES;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.expenses.EmployeeIdExpensesContainsKeywordsPredicate;
import seedu.address.model.expenses.Expenses;
import seedu.address.model.expenses.ExpensesAmount;
import seedu.address.model.expenses.MedicalExpenses;
import seedu.address.model.expenses.MiscellaneousExpenses;
import seedu.address.model.expenses.TravelExpenses;
import seedu.address.model.person.EmployeeId;
import seedu.address.model.person.Person;

/**
 * Adds an expense to the Expenses List.
 */
public class AddExpensesCommand extends Command {
    public static final String COMMAND_WORD = "addExpenses";
    public static final String COMMAND_ALIAS = "ae";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Request Expenses. "
            + "Parameters: "
            + PREFIX_EMPLOYEEID + "EMPLOYEEID "
            + PREFIX_TRAVEL_EXPENSES + "TRAVELXPENSES "
            + PREFIX_MEDICAL_EXPENSES + "MEDICALEXPENSES "
            + PREFIX_MISCELLANEOUS_EXPENSES + "MISCELLANEOUSEXPENSES ";

    public static final String MESSAGE_SUCCESS = "Adding expenses requested.";
    public static final String MESSAGE_NEGATIVE_LEFTOVER = "Cannot have negative expenses leftover.";
    public static final String MESSAGE_NOT_EDITED = "Adding expenses not edited.";
    public static final String MESSAGE_EMPLOYEE_ID_NOT_FOUND = "Employee Id not found in address book";


    private Boolean isNegativeLeftover;
    private Person toCheckEmployeeId;
    private final Expenses toAddExpenses;
    private final EditExpensesDescriptor editExpensesDescriptor;

    public AddExpensesCommand(Expenses expenses, EditExpensesDescriptor editExpensesDescriptor) {
        Expenses toAddFormatExpenses;
        ExpensesAmount formattedExpenses = null;
        TravelExpenses formattedTravelExpenses = null;
        MedicalExpenses formattedMedicalExpenses = null;
        MiscellaneousExpenses formattedMiscellaneousExpenses = null;
        requireNonNull(expenses);
        requireNonNull(editExpensesDescriptor);

        toAddFormatExpenses = expenses;
        NumberFormat formatter = new DecimalFormat("#0.00");
        String formatExpenses = toAddFormatExpenses.getExpensesAmount().toString();
        String formatTravelExpenses = toAddFormatExpenses.getTravelExpenses().toString();
        String formatMedicalExpenses = toAddFormatExpenses.getMedicalExpenses().toString();
        String formatMiscellaneousExpenses = toAddFormatExpenses.getMiscellaneousExpenses().toString();
        try {
            formattedTravelExpenses = ParserUtil.parseTravelExpenses(
                    String.valueOf(formatter.format(Double.parseDouble(formatTravelExpenses))));
        } catch (ParseException peTra) {
            peTra.printStackTrace();
        }
        try {
            formattedMedicalExpenses = ParserUtil.parseMedicalExpenses(
                    String.valueOf(formatter.format(Double.parseDouble(formatMedicalExpenses))));
        } catch (ParseException peMed) {
            peMed.printStackTrace();
        }
        try {
            formattedMiscellaneousExpenses = ParserUtil.parseMiscellaneousExpenses(
                    String.valueOf(formatter.format(Double.parseDouble(formatMiscellaneousExpenses))));
        } catch (ParseException peMisc) {
            peMisc.printStackTrace();
        }
        try {
            formattedExpenses = ParserUtil.parseExpensesAmount(
                    String.valueOf(formatter.format(Double.parseDouble(formatExpenses))));
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        EmployeeId addEmployeeId = toAddFormatExpenses.getEmployeeId();
        toAddExpenses = new Expenses (addEmployeeId, formattedExpenses, formattedTravelExpenses,
                formattedMedicalExpenses, formattedMiscellaneousExpenses);
        toCheckEmployeeId = new Person(expenses.getEmployeeId());
        this.editExpensesDescriptor = new EditExpensesDescriptor(editExpensesDescriptor);
        isNegativeLeftover = false;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        String messageToShow = "";
        if (!model.hasEmployeeId(toCheckEmployeeId)) {
            throw new CommandException(MESSAGE_EMPLOYEE_ID_NOT_FOUND);
        } else if (!model.hasExpenses(toAddExpenses)) {
            if (Double.parseDouble(toAddExpenses.getExpensesAmount().toString()) < 0) {
                messageToShow = MESSAGE_NEGATIVE_LEFTOVER;
            } else if (Double.parseDouble(toAddExpenses.getTravelExpenses().toString()) < 0) {
                messageToShow = MESSAGE_NEGATIVE_LEFTOVER;
            } else if (Double.parseDouble(toAddExpenses.getMedicalExpenses().toString()) < 0) {
                messageToShow = MESSAGE_NEGATIVE_LEFTOVER;
            } else if (Double.parseDouble(toAddExpenses.getMiscellaneousExpenses().toString()) < 0) {
                messageToShow = MESSAGE_NEGATIVE_LEFTOVER;
            } else if (Double.parseDouble(toAddExpenses.getExpensesAmount().toString()) >= 0
                    && Double.parseDouble(toAddExpenses.getTravelExpenses().toString()) >= 0
                    && Double.parseDouble(toAddExpenses.getMedicalExpenses().toString()) >= 0
                    && Double.parseDouble(toAddExpenses.getMiscellaneousExpenses().toString()) >= 0) {
                model.addExpenses(toAddExpenses);
                model.commitExpensesList();
                messageToShow = MESSAGE_SUCCESS;
            }
        } else if (model.hasExpenses(toAddExpenses)) {
            EmployeeIdExpensesContainsKeywordsPredicate predicatEmployeeId;
            List<String> employeeIdList = new ArrayList<>();
            List<Expenses> lastShownListExpenses;

            employeeIdList.add(toCheckEmployeeId.getEmployeeId().value);
            predicatEmployeeId = new EmployeeIdExpensesContainsKeywordsPredicate(employeeIdList);

            model.updateFilteredExpensesList(predicatEmployeeId);
            lastShownListExpenses = model.getFilteredExpensesList();

            Expenses expensesToEdit = lastShownListExpenses.get(0);
            Expenses editedExpenses = createEditedExpenses(expensesToEdit, editExpensesDescriptor);

            if (getIsNegativeLeftover()) {
                messageToShow = MESSAGE_NEGATIVE_LEFTOVER;
            } else if (!getIsNegativeLeftover()) {
                messageToShow = MESSAGE_SUCCESS;
                model.updateExpenses(expensesToEdit, editedExpenses);
                model.commitExpensesList();
            }
            model.updateFilteredExpensesList(PREDICATE_SHOW_ALL_EXPENSES);
        }
        return new CommandResult(String.format(messageToShow, toAddExpenses));
    }

    /**
     * Creates and returns a {@code Expenses} with the details of {@code expensesToEdit}
     * edited with {@code editExpensesDescriptor}.
     */
    private Expenses createEditedExpenses(Expenses expensesToEdit, EditExpensesDescriptor
            editExpensesDescriptor) {
        assert expensesToEdit != null;
        ExpensesAmount updatedExpensesAmount = null;
        TravelExpenses updatedTravelExpenses = null;
        MedicalExpenses updatedMedicalExpenses = null;
        MiscellaneousExpenses updatedMiscellaneousExpenses = null;

        EmployeeId updatedEmployeeId = expensesToEdit.getEmployeeId();
        try {
            updatedExpensesAmount = ParserUtil.parseExpensesAmount(modifyExpensesAmount(expensesToEdit,
                    editExpensesDescriptor));
            updatedTravelExpenses = ParserUtil.parseTravelExpenses(modifyTravelExpenses(expensesToEdit,
                    editExpensesDescriptor));
            updatedMedicalExpenses = ParserUtil.parseMedicalExpenses(modifyMedicalExpenses(expensesToEdit,
                    editExpensesDescriptor));
            updatedMiscellaneousExpenses = ParserUtil.parseMiscellaneousExpenses(modifyMiscellaneousExpenses(
                    expensesToEdit, editExpensesDescriptor));
        } catch (ParseException pe) {
            pe.printStackTrace();
        }

        return new Expenses(updatedEmployeeId, updatedExpensesAmount, updatedTravelExpenses, updatedMedicalExpenses,
                updatedMiscellaneousExpenses);
    }

    /**
     * Creates and returns a new String of Expenses with the details of {@code expensesToEdit}
     * edited with {@code editExpensesDescriptor}.
     */
    private String modifyExpensesAmount (Expenses expensesToEdit, EditExpensesDescriptor
            editExpensesDescriptor) {
        NumberFormat formatter = new DecimalFormat("#0.00");
        String newExpensesAmount = expensesToEdit.getExpensesAmount().toString();
        double updateExpensesAmount = Double.parseDouble(newExpensesAmount);
        String change = editExpensesDescriptor.getExpensesAmount().toString().replaceAll("[^0-9.-]",
                "");
        updateExpensesAmount += Double.parseDouble(change);
        if (updateExpensesAmount < 0) {
            setIsNegativeLeftover(true);
        } else if (updateExpensesAmount >= 0) {
            newExpensesAmount = String.valueOf(formatter.format(updateExpensesAmount));
        }
        return newExpensesAmount;
    }

    /**
     * Creates and returns a new String of Expenses with the details of {@code expensesToEdit}
     * edited with {@code editExpensesDescriptor}.
     */
    private String modifyTravelExpenses (Expenses expensesToEdit, EditExpensesDescriptor
            editExpensesDescriptor) {
        NumberFormat formatter = new DecimalFormat("#0.00");
        String newTravelExpenses = expensesToEdit.getTravelExpenses().toString();
        double updateTravelExpenses = Double.parseDouble(newTravelExpenses);
        String change = editExpensesDescriptor.getTravelExpenses().toString().replaceAll("[^0-9.-]",
                "");
        updateTravelExpenses += Double.parseDouble(change);
        if (updateTravelExpenses < 0) {
            setIsNegativeLeftover(true);
        } else if (updateTravelExpenses >= 0) {
            newTravelExpenses = String.valueOf(formatter.format(updateTravelExpenses));
        }
        return newTravelExpenses;
    }

    /**
     * Creates and returns a new String of Expenses with the details of {@code expensesToEdit}
     * edited with {@code editExpensesDescriptor}.
     */
    private String modifyMedicalExpenses (Expenses expensesToEdit, EditExpensesDescriptor
            editExpensesDescriptor) {
        NumberFormat formatter = new DecimalFormat("#0.00");
        String newMedicalExpenses = expensesToEdit.getMedicalExpenses().toString();
        double updateMedicalExpenses = Double.parseDouble(newMedicalExpenses);
        String change = editExpensesDescriptor.getMedicalExpenses().toString().replaceAll("[^0-9.-]",
                "");
        updateMedicalExpenses += Double.parseDouble(change);
        if (updateMedicalExpenses < 0) {
            setIsNegativeLeftover(true);
        } else if (updateMedicalExpenses >= 0) {
            newMedicalExpenses = String.valueOf(formatter.format(updateMedicalExpenses));
        }
        return newMedicalExpenses;
    }

    /**
     * Creates and returns a new String of Expenses with the details of {@code expensesToEdit}
     * edited with {@code editExpensesDescriptor}.
     */
    private String modifyMiscellaneousExpenses (Expenses expensesToEdit, EditExpensesDescriptor
            editExpensesDescriptor) {
        NumberFormat formatter = new DecimalFormat("#0.00");
        String newMiscellaneousExpenses = expensesToEdit.getMiscellaneousExpenses().toString();
        double updateMiscellaneousExpenses = Double.parseDouble(newMiscellaneousExpenses);
        String change = editExpensesDescriptor.getMiscellaneousExpenses().toString().replaceAll("[^0-9.-]",
                "");
        updateMiscellaneousExpenses += Double.parseDouble(change);
        if (updateMiscellaneousExpenses < 0) {
            setIsNegativeLeftover(true);
        } else if (updateMiscellaneousExpenses >= 0) {
            newMiscellaneousExpenses = String.valueOf(formatter.format(updateMiscellaneousExpenses));
        }
        return newMiscellaneousExpenses;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddExpensesCommand // instanceof handles nulls
                && toAddExpenses.equals(((AddExpensesCommand) other).toAddExpenses));
    }

    public void setIsNegativeLeftover(Boolean negativeLeftover) {
        isNegativeLeftover = negativeLeftover;
    }

    public boolean getIsNegativeLeftover() {
        return isNegativeLeftover;
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditExpensesDescriptor {
        private ExpensesAmount expensesAmount;
        private TravelExpenses travelExpenses;
        private MedicalExpenses medicalExpenses;
        private MiscellaneousExpenses miscellaneousExpenses;

        public EditExpensesDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditExpensesDescriptor(EditExpensesDescriptor toCopy) {
            setExpensesAmount(toCopy.expensesAmount);
            setTravelExpenses(toCopy.travelExpenses);
            setMedicalExpenses(toCopy.medicalExpenses);
            setMiscellaneousExpenses(toCopy.miscellaneousExpenses);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(expensesAmount, travelExpenses, medicalExpenses, miscellaneousExpenses);
        }

        public void setExpensesAmount(ExpensesAmount expensesAmount) {
            this.expensesAmount = expensesAmount;
        }

        public Optional<ExpensesAmount> getExpensesAmount() {
            return Optional.ofNullable(expensesAmount);
        }

        public void setTravelExpenses(TravelExpenses travelExpenses) {
            this.travelExpenses = travelExpenses;
        }

        public Optional<TravelExpenses> getTravelExpenses() {
            return Optional.ofNullable(travelExpenses);
        }

        public void setMedicalExpenses(MedicalExpenses medicalExpenses) {
            this.medicalExpenses = medicalExpenses;
        }

        public Optional<MedicalExpenses> getMedicalExpenses() {
            return Optional.ofNullable(medicalExpenses);
        }

        public void setMiscellaneousExpenses(MiscellaneousExpenses miscellaneousExpenses) {
            this.miscellaneousExpenses = miscellaneousExpenses;
        }

        public Optional<MiscellaneousExpenses> getMiscellaneousExpenses() {
            return Optional.ofNullable(miscellaneousExpenses);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditExpensesDescriptor)) {
                return false;
            }

            // state check
            EditExpensesDescriptor e = (EditExpensesDescriptor) other;

            return getExpensesAmount().equals(e.getExpensesAmount())
                    && getTravelExpenses().equals(e.getTravelExpenses())
                    && getMedicalExpenses().equals(e.getMedicalExpenses())
                    && getMiscellaneousExpenses().equals(e.getMiscellaneousExpenses());
        }
    }
}

