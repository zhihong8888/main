package seedu.address.model.schedule;
import java.util.Objects;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Person's remark in the address book.
 * Guarantees: immutable; is always valid
 */
public class Schedule {

    // Data fields
    private final Status status;
    private final Date date;

    public Schedule(Date date, Status status) {
        requireAllNonNull(status, date);
        this.status = status;
        this.date = date;
    }

    public Status getStatus() {
        return status;
    }

    public Date getDate() {
        return date;
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Schedule)) {
            return false;
        }

        Schedule otherPerson = (Schedule) other;
        return otherPerson.getDate().equals(getDate())
                && otherPerson.getStatus().equals(getStatus());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(status, date);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Date: ")
                .append(getDate())
                .append(" Status: ")
                .append(getStatus());
        return builder.toString();
    }

}
