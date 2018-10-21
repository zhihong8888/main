package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_BONUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SALARY;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Bonus;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Department;
import seedu.address.model.person.Email;
import seedu.address.model.person.EmployeeId;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Position;
import seedu.address.model.person.Salary;
import seedu.address.model.person.tag.Tag;


/**
 *  Modify the salary and bonus of an employee's in CHRS
 */
public class ModifyPayCommand extends Command {

    public static final String COMMAND_WORD = "modifyPay";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Modify the pay of the employee "
            + "identified by the index used in the displayed list. "
            + "Existing salary will be updated based on the user input.\n"
            + "Parameters: index "
            + PREFIX_SALARY + "[NEW SALARY]"
            + " AND/OR "
            + PREFIX_BONUS + "[NEW BONUS]\n"
            + "Example: " + COMMAND_WORD + " "
            + "1 "
            + PREFIX_SALARY + "3000 "
            + PREFIX_BONUS + "4500";

    public static final String MESSAGE_MODIFIED_SUCCESS = "Employee's pay modified.";
    public static final String MESSAGE_NOT_MODIFIED = "Employee's pay not modified yet. "
            + "[INDEX] and min of 1 field "
            + PREFIX_SALARY
            + " AND/OR "
            + PREFIX_BONUS
            + " must be provided";

    private final Index index;
    private final ModSalaryDescriptor modSalaryDescriptor;

    /**
     *
     * @param index of the person in employee list to modify.
     * @param modSalaryDescriptor details to modify the person with.
     */

    public ModifyPayCommand(Index index, ModSalaryDescriptor modSalaryDescriptor) {
        requireNonNull(index);
        requireNonNull(modSalaryDescriptor);

        this.index = index;
        this.modSalaryDescriptor = new ModSalaryDescriptor(modSalaryDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person salaryToModify = lastShownList.get(index.getZeroBased());
        Person modifiedPerson = createModifiedPerson(salaryToModify, modSalaryDescriptor);

        model.updatePerson(salaryToModify, modifiedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_MODIFIED_SUCCESS, modifiedPerson));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code modSalaryDescriptor}.
     */
    private static Person createModifiedPerson(Person personToEdit, ModSalaryDescriptor modSalaryDescriptor) {
        assert personToEdit != null;

        EmployeeId updatedEmployeeId = personToEdit.getEmployeeId();
        Name updatedName = personToEdit.getName();
        DateOfBirth updatedDateOfBirth = personToEdit.getDateOfBirth();
        Phone updatedPhone = personToEdit.getPhone();
        Email updatedEmail = personToEdit.getEmail();
        Department updatedDepartment = personToEdit.getDepartment();
        Position updatedPosition = personToEdit.getPosition();
        Address updatedAddress = personToEdit.getAddress();
        Salary updatedSalary = modSalaryDescriptor.getSalary().orElse(personToEdit.getSalary());
        Bonus updatedBonus = modSalaryDescriptor.getBonus().orElse(personToEdit.getBonus());
        Set<Tag> updatedTags = personToEdit.getTags();


        return new Person(updatedEmployeeId, updatedName, updatedDateOfBirth, updatedPhone, updatedEmail,
                updatedDepartment, updatedPosition, updatedAddress, updatedSalary, updatedBonus, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        ModifyPayCommand m = (ModifyPayCommand) other;
        return index.equals(m.index)
                && modSalaryDescriptor.equals(m.modSalaryDescriptor);
    }

    /**
     * Stores the details to modify the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
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

        public void setSalary(Salary salary) {
            this.salary = salary;
        }

        public Optional<Salary> getSalary() {
            return Optional.ofNullable(salary);
        }

        public void setBonus(Bonus bonus) {
            this.bonus = bonus;
        }

        public Optional<Bonus> getBonus() {
            return Optional.ofNullable(bonus);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof ModSalaryDescriptor)) {
                return false;
            }

            // state check
            ModSalaryDescriptor e = (ModSalaryDescriptor) other;

            return getSalary().equals(e.getSalary())
                    && getBonus().equals(e.getBonus());
        }
    }
}
