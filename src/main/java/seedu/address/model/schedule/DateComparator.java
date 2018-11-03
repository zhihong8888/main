package seedu.address.model.schedule;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.Comparator;

/**
 * Compares date in ascending order dd/MM/YYYY
 */
public class DateComparator implements Comparator<Date> {

    /**
     * Compares date in ascending order dd/MM/YYYY
     */
    public int compare(Date o1, Date o2) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate s1;
        LocalDate s2;
        s1 = LocalDate.parse(o1.value, formatter);
        s2 = LocalDate.parse(o2.value, formatter);
        return s1.compareTo(s2);
    }
}
