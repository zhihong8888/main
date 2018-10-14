package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_BONUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEEID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SALARY;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import seedu.address.commons.core.index.Index;
import seedu.address.model.person.Bonus;
import seedu.address.model.person.Salary;
import seedu.address.model.person.tag.Tag;

import java.util.Optional;

/**
 *  Modify the salary and bonus of an employee's in CHRS
 */
public class ModifyPayCommand extends Command {

    public static final String COMMAND_WORD = "modifypay";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "Modify the employee's pay "
            + "selected by the employee's ID. "
            + "Existing salary will be updated based on the percentage and month(s) of bonus included.\n"
            + "Parameters: id/[EMPLOYEE ID] (must be of 6 positive digit) "
            + PREFIX_SALARY + "[SALARY % OF CHANGE]"
            + "AND/OR"
            + PREFIX_BONUS + "[MONTH(S) OF BONUS]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EMPLOYEEID + "123456 "
            + PREFIX_SALARY + "10 "
            + PREFIX_BONUS + "1.5";

    public static final String MESSAGE_MODIFIED_SUCCESS = "Employee's pay modified.";
    public static final String MESSAGE_NOT_MODIFIED = "Employee's pay not modified yet. "
            + PREFIX_EMPLOYEEID + "[EMPLOYEE ID] and min of 1 field" +
            PREFIX_SALARY + " AND/OR " + PREFIX_BONUS + " must be provided";

    private final Index index;
    private final ModSalaryDescriptor modSalaryDescriptor;

    /**
     *
     * @param index of the person in employee list to modify.
     * @param modSalaryDescriptor details to modify the person with.
     */

    public ModifyPayCommand(Index index, ModSalaryDescriptor modSalaryDescriptor){
        requireNonNull(index);
        requireNonNull(modSalaryDescriptor);

        this.index=index;
        this.modSalaryDescriptor= new ModSalaryDescriptor(modSalaryDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        throw new CommandException(MESSAGE_NOT_MODIFIED);
    }

    public static class ModSalaryDescriptor {
        private Salary salary;
        private Bonus bonus;

        public ModSalaryDescriptor() {}
        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */

        public ModSalaryDescriptor(ModSalaryDescriptor toCopy) {
            setSalary(toCopy.salary);
            setBonus(toCopy.bonus);
        }

        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(salary, bonus);
        }

        public void setSalary(Salary salary) { this.salary=salary; }

        public Optional<Salary> getSalary() { return Optional.ofNullable(salary); }

        public void setBonus(Bonus bonus) { this.bonus=bonus; }

        public Optional<Bonus> getBonus() { return Optional.ofNullable(bonus); }
    }
}
