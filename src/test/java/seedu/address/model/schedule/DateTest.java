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
    public void isValidDate() {
        // null date of birth
        Assert.assertThrows(NullPointerException.class, () -> Date.isValidScheduleDate(null));

        // invalid dates
        assertFalse(Date.isValidScheduleDate("")); // empty string
        assertFalse(Date.isValidScheduleDate(" ")); // spaces only
        assertFalse(Date.isValidScheduleDate("a/12/2018")); // day is invalid
        assertFalse(Date.isValidScheduleDate("01/a/2018")); // month is invalid
        assertFalse(Date.isValidScheduleDate("01/12/aaaa")); // year is invalid

        assertFalse(Date.isValidScheduleDate("32/12/2018")); // more than 31 days [out of range]
        assertFalse(Date.isValidScheduleDate("0/12/2018")); // 0 days [out of range]
        assertFalse(Date.isValidScheduleDate("01/13/2018")); // more than 12 months [out of range]
        assertFalse(Date.isValidScheduleDate("01/0/2018")); // 0 months [out of range]
        assertFalse(Date.isValidScheduleDate("01/12/2100")); // more than 2099 years [out of range]
        assertFalse(Date.isValidScheduleDate("01/12/1999")); // less than 2000 years [out of range]

        assertFalse(Date.isValidScheduleDate(" 01/01/2018")); // spaces within date
        assertFalse(Date.isValidScheduleDate("01 /01/2018")); // spaces within date
        assertFalse(Date.isValidScheduleDate("01/ 01/2018")); // spaces within date
        assertFalse(Date.isValidScheduleDate("01/01 /2018")); // spaces within date
        assertFalse(Date.isValidScheduleDate("01/01/ 2018")); // spaces within date
        assertFalse(Date.isValidScheduleDate("01 /01/2018 ")); // spaces within date

        assertFalse(Date.isValidScheduleDate("29/2/2017")); //non-leap year does not have 29 days in feb
        assertFalse(Date.isValidScheduleDate("30/02/2020")); //leap year does not have only 30 days in feb
        assertFalse(Date.checkValidDate("2020", "02", "30"));


        assertFalse(Date.isValidScheduleDate("31/04/2020")); //april, june, sep, nov does not have 31 days
        assertFalse(Date.isValidScheduleDate("31/06/2020")); //april, june, sep, nov does not have 31 days
        assertFalse(Date.isValidScheduleDate("31/09/2020")); //april, june, sep, nov does not have 31 days
        assertFalse(Date.isValidScheduleDate("31/11/2020")); //april, june, sep, nov does not have 31 days
        assertFalse(Date.checkValidDate("2020", "11", "31"));

        assertFalse(Date.isValidScheduleDate("32/01/2031")); //jan, mar, may, jul, aug, oct, dec have 32 days
        assertFalse(Date.isValidScheduleDate("32/03/2031")); //jan, mar, may, jul, aug, oct, dec have 32 days
        assertFalse(Date.isValidScheduleDate("32/05/2031")); //jan, mar, may, jul, aug, oct, dec have 32 days
        assertFalse(Date.isValidScheduleDate("32/07/2031")); //jan, mar, may, jul, aug, oct, dec have 32 days
        assertFalse(Date.isValidScheduleDate("32/08/2031")); //jan, mar, may, jul, aug, oct, dec have 32 days
        assertFalse(Date.isValidScheduleDate("32/10/2031")); //jan, mar, may, jul, aug, oct, dec have 32 days
        assertFalse(Date.isValidScheduleDate("32/12/2031")); //jan, mar, may, jul, aug, oct, dec have 32 days

        // valid dates
        assertTrue(Date.isValidScheduleDate("1/1/2099")); //omitting leading 0 is a valid date
        assertTrue(Date.isValidScheduleDate("01/01/2099")); //including 1 leading 0 is a valid date

        assertTrue(Date.isValidScheduleDate("29/2/2048")); //leap year has 29 days in feb
        assertTrue(Date.isValidScheduleDate("28/02/2021")); //non-leap year has only 28 days in feb

        assertTrue(Date.isValidScheduleDate("30/04/2020")); //april, june, sep, nov have 30 days
        assertTrue(Date.isValidScheduleDate("30/06/2020")); //april, june, sep, nov have 30 days
        assertTrue(Date.isValidScheduleDate("30/09/2020")); //april, june, sep, nov have 30 days
        assertTrue(Date.isValidScheduleDate("30/11/2020")); //april, june, sep, nov have 30 days

        assertTrue(Date.isValidScheduleDate("31/01/2031")); //jan, mar, may, jul, aug, oct, dec have 31 days
        assertTrue(Date.isValidScheduleDate("31/03/2031")); //jan, mar, may, jul, aug, oct, dec have 31 days
        assertTrue(Date.isValidScheduleDate("31/05/2031")); //jan, mar, may, jul, aug, oct, dec have 31 days
        assertTrue(Date.isValidScheduleDate("31/07/2031")); //jan, mar, may, jul, aug, oct, dec have 31 days
        assertTrue(Date.isValidScheduleDate("31/08/2031")); //jan, mar, may, jul, aug, oct, dec have 31 days
        assertTrue(Date.isValidScheduleDate("31/10/2031")); //jan, mar, may, jul, aug, oct, dec have 31 days
        assertTrue(Date.isValidScheduleDate("31/12/2031")); //jan, mar, may, jul, aug, oct, dec have 31 days
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
