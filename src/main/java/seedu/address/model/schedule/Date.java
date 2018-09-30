package seedu.address.model.schedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Date {

    public static final String DATE_FORMAT = "dd-MM-yyyy";
    public static final String MESSAGE_DATE_CONSTRAINTS = "Invalid Date or wrong format. Dates must be in dd-MM-yyyy";

    public final String date;

    public Date(String date){
        requireNonNull(date);

        checkArgument(isValidDate(date), MESSAGE_DATE_CONSTRAINTS);

        this.date = date;
    }

    public static boolean isValidDate(String dateToValidate) throws DateTimeException, NumberFormatException {

        if (dateToValidate == null) {
            return false;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        dateFormat.setLenient(false);

        try {
            // if not valid, it will throw ParseException
            java.util.Date date = dateFormat.parse(dateToValidate);

        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return date;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && date.equals(((Date) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

}
