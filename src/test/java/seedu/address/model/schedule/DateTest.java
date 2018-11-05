package seedu.address.model.schedule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String date = "";
        Assert.assertThrows(NumberFormatException.class, () -> new Date(date));
    }

    @Test
    public void isValidScheduleDate_Null_NullPointerExceptionThrown() {
        Assert.assertThrows(NullPointerException.class, () -> Date.isValidScheduleDate(null));
    }

    @Test
    public void isValidScheduleDate_ValidDay_ReturnTrue() {
        //Day boundaries 1 to 31 or 1 to 30
        assertTrue(Date.isValidScheduleDate("1/4/2050"));
        assertTrue(Date.isValidScheduleDate("30/4/2050")); //april, june, sep, nov have 30 days
        assertTrue(Date.isValidScheduleDate("31/5/2050")); //jan, mar, may, jul, aug, oct, dec have 31 days
    }

    @Test
    public void isValidScheduleDate_InvalidDay_ReturnFalse() {
        //Day boundaries
        assertFalse(Date.isValidScheduleDate("0/4/2050"));
        assertFalse(Date.isValidScheduleDate("31/4/2050")); //april, june, sep, nov no 31 days
        assertFalse(Date.isValidScheduleDate("32/5/2050")); //jan, mar, may, jul, aug, oct, dec no 32 days
        assertFalse(Date.isValidScheduleDate("99/5/2050")); //jan, mar, may, jul, aug, oct, dec no 32 days
    }

    @Test
    public void isValidScheduleDate_ValidMonth_ReturnTrue() {
        //Month boundaries 1 to 12
        assertTrue(Date.isValidScheduleDate("1/1/2050"));
        assertTrue(Date.isValidScheduleDate("1/12/2050"));
    }

    @Test
    public void isValidScheduleDate_InvalidMonth_ReturnFalse() {
        //Month boundaries
        assertFalse(Date.isValidScheduleDate("1/0/2050"));
        assertFalse(Date.isValidScheduleDate("1/13/2050"));
        assertFalse(Date.isValidScheduleDate("1/99/2050"));
    }

    @Test
    public void isValidScheduleDate_ValidYear_ReturnTrue() {
        //Year boundaries 2000 to 2099
        assertTrue(Date.isValidScheduleDate("1/1/2000"));
        assertTrue(Date.isValidScheduleDate("1/12/2099"));
    }

    @Test
    public void isValidScheduleDate_InvalidYear_ReturnFalse() {
        //Year boundaries
        assertFalse(Date.isValidScheduleDate("1/0/0000"));
        assertFalse(Date.isValidScheduleDate("1/0/1999"));
        assertFalse(Date.isValidScheduleDate("1/13/2100"));
        assertFalse(Date.isValidScheduleDate("1/13/9999"));
    }


    @Test
    public void dateConstraintsErrorToString_validString_correctStringRepresentation() {
        Date expected = new Date("09/09/2099");
        expected.setDateConstraintsError("expected string");
        assertEquals("expected string", expected.getDateConstraintsError());
    }

    @Test
    public void dateToString_validString_correctStringRepresentation() {
        Date expected = new Date("09/09/2099");
        assertEquals("09/09/2099", expected.toString());
    }

    @Test
    public void typeComparable_validType_hashCodeIsCorrect() {
        Date expected = new Date("09/09/2099");
        assertEquals("09/09/2099".hashCode(), expected.hashCode());

    }

}
