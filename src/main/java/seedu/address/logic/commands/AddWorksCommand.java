package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE_DATE;

import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.EmployeeId;
import seedu.address.model.person.Person;
import seedu.address.model.schedule.Date;
import seedu.address.model.schedule.DateComparator;
import seedu.address.model.schedule.EmployeeIdComparator;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.Type;


/**
 * Add work schedules to all observable employees on the address book.
 */
public class AddWorksCommand extends Command {

    public static final String COMMAND_WORD = "addWorks";
    public static final String COMMAND_ALIAS = "aw";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": schedule working schedule for all observable "
            + "employees in the list by specifying the date to work. "
            + "\nParameters: "
            + PREFIX_SCHEDULE_DATE + "[DD/MM/YYYY]  .... *You can specify more than 1 date prefix to schedule*"
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_SCHEDULE_DATE + "02/02/2019 ";

    public static final String MESSAGE_SUCCESS_SOME_ADDED = "New working schedules added for SOME of"
            + " the observable employees whom are not yet added date: %1$s";

    public static final String MESSAGE_SUCCESS_ALL_ADDED = "New working schedules added for ALL of"
            + " the observable employees whom are not yet added with date: %1$s";

    public static final String MESSAGE_NO_PERSON_OBSERVED =
            "No observable employees found in list! "
                    + "\nTry using %1$s/%2$s/%3$s commands get the employees you want to schedule work for.";

    public static final String MESSAGE_PERSON_ALL_ADDED_WORK = "Every observable employees in the list has "
            + "already been added with work on %1$s !";

    public static final String MESSAGE_PERSON_ALL_ADDED_WORK_NOT_ON_LEAVE = "Every observable employees "
            + "whom are not on leave in the list has already been added with work on %1$s !";

    public static final String MESSAGE_PERSON_ALL_HAS_LEAVE_SAME_DATE = "Unable to schedule work for the following "
            + "employees below whom are on leave:";

    public static final String MESSAGE_EMPLOYEE_ON_LEAVE = "Employee Id: %1$s Has leave on: %2$s";
    private final Set<Date> setOfDates = new HashSet<>();

    /**
     * @param date of the work to schedule
     */
    public AddWorksCommand(Set<Date> date) {
        requireAllNonNull(date);
        this.setOfDates.addAll(date);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        Type work = new Type(Type.WORK);
        Type leave = new Type(Type.LEAVE);
        Multimap<EmployeeId, Date> employeeIdMapToLeaves = TreeMultimap.create(
                new EmployeeIdComparator(), new DateComparator());
        boolean commit = false;

        //model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        if (model.getFilteredPersonList().size() == 0) {
            throw new CommandException(String.format(MESSAGE_NO_PERSON_OBSERVED,
                    FindCommand.COMMAND_WORD, ListCommand.COMMAND_WORD, FilterCommand.COMMAND_WORD));
        }

        for (Date date :setOfDates) {
            for (Person person : model.getFilteredPersonList()) {
                Schedule toAddSchedule = new Schedule(person.getEmployeeId(), work , date);
                Schedule hasLeaveSchedule = new Schedule(person.getEmployeeId(), leave , date);
                if (model.hasSchedule(hasLeaveSchedule)) {
                    employeeIdMapToLeaves.put(person.getEmployeeId(), date);
                } else if (!model.hasSchedule(toAddSchedule)) {
                    commit = true;
                    model.addSchedule(toAddSchedule);
                }
            }
        }
        String textFeedbackToUser = getUserInteractionFeedback(employeeIdMapToLeaves, commit, setOfDates);
        if (!commit) {
            throw new CommandException(textFeedbackToUser);
        }
        model.commitScheduleList();
        return new CommandResult(textFeedbackToUser);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddWorksCommand // instanceof handles nulls
                && setOfDates.equals(((AddWorksCommand) other).setOfDates));
    }

    private String getUserInteractionFeedback (Multimap<EmployeeId, Date> employeeIdMapToLeaves, Boolean commit,
                                               Set<Date> setOfDates) {
        String noneCommitted;
        StringBuilder someCommitted;
        String allCommitted;
        String textFeedbackToUser;

        //No schedule committed and no employees found with leave
        if ((!commit) && (employeeIdMapToLeaves.isEmpty())) {
            noneCommitted = String.format(MESSAGE_PERSON_ALL_ADDED_WORK, setOfDates);
            textFeedbackToUser = noneCommitted;

        //Not Committed and some employees found with leave
        } else if ((!commit) && (!employeeIdMapToLeaves.isEmpty())) {
            someCommitted = new StringBuilder();
            someCommitted.append(String.format(MESSAGE_PERSON_ALL_ADDED_WORK_NOT_ON_LEAVE, setOfDates) + "\n");
            someCommitted.append(MESSAGE_PERSON_ALL_HAS_LEAVE_SAME_DATE + "\n");
            for (EmployeeId employeeId : employeeIdMapToLeaves.keySet()) {
                someCommitted.append(
                        String.format(MESSAGE_EMPLOYEE_ON_LEAVE, employeeId, employeeIdMapToLeaves.get(employeeId)));
                someCommitted.append("\n");
            }
            textFeedbackToUser = someCommitted.toString();

        //Committed and some employees found with leave
        } else if ((commit) && (!employeeIdMapToLeaves.isEmpty())) {
            someCommitted = new StringBuilder(String.format(MESSAGE_SUCCESS_SOME_ADDED, setOfDates) + "\n");
            someCommitted.append(MESSAGE_PERSON_ALL_HAS_LEAVE_SAME_DATE + "\n");
            for (EmployeeId employeeId : employeeIdMapToLeaves.keySet()) {
                someCommitted.append(
                        String.format(MESSAGE_EMPLOYEE_ON_LEAVE, employeeId, employeeIdMapToLeaves.get(employeeId)));
                someCommitted.append("\n");
            }
            textFeedbackToUser = someCommitted.toString();

        } else {
            allCommitted = String.format(MESSAGE_SUCCESS_ALL_ADDED, setOfDates);
            textFeedbackToUser = allCommitted;

        }

        return textFeedbackToUser;
    }
}

