package seedu.address.model.schedule;

import javafx.collections.ObservableList;
import seedu.address.model.schedule.Schedule;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyScheduleList {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Schedule> getScheduleList();

}