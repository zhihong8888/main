package seedu.address.model.schedule;

import java.util.Comparator;

import seedu.address.model.person.EmployeeId;

/**
 * Compares employee id in ascending order
 */
public class EmployeeIdComparator implements Comparator<EmployeeId> {

    /**
     * Compares employee id in ascending order
     */
    public int compare(EmployeeId o1, EmployeeId o2) {
        int s1 = Integer.parseInt(o1.value);
        int s2 = Integer.parseInt(o2.value);

        if (s1 == s2) {
            return 0;
        } else if (s1 > s2) {
            return 1;
        } else {
            return -1;
        }
    }
}
