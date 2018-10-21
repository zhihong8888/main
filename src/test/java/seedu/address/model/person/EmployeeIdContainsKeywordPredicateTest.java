package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class EmployeeIdContainsKeywordPredicateTest {

    @Test
    public void equals() {
        String firstPredicateKeyword = "000001";
        String secondPredicateKeyword = "000002";

        EmployeeIdContainsKeywordPredicate firstPredicate =
                new EmployeeIdContainsKeywordPredicate(firstPredicateKeyword);
        EmployeeIdContainsKeywordPredicate secondPredicate =
                new EmployeeIdContainsKeywordPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EmployeeIdContainsKeywordPredicate firstPredicateCopy =
                new EmployeeIdContainsKeywordPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals("hello"));

        // null -> returns false
        assertFalse(firstPredicate == null);

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_employeeIdContainsKeyword_returnsTrue() {
        // One keyword
        EmployeeIdContainsKeywordPredicate predicate =
                new EmployeeIdContainsKeywordPredicate("000001");
        assertTrue(predicate.test(new PersonBuilder().withEmployeeId("000001").build()));
    }

    @Test
    public void test_employeeIdDoesNotContainKeyword_returnsFalse() {
        // Zero keyword
        EmployeeIdContainsKeywordPredicate predicate =
                new EmployeeIdContainsKeywordPredicate("");
        assertFalse(predicate.test(new PersonBuilder().withEmployeeId("000001").build()));

        // Non-matching keyword
        predicate = new EmployeeIdContainsKeywordPredicate("000003");
        assertFalse(predicate.test(new PersonBuilder().withEmployeeId("000001").build()));

        // Keywords match phone but does not match employeeId
        predicate = new EmployeeIdContainsKeywordPredicate("123456");
        assertFalse(predicate.test(new PersonBuilder().withEmployeeId("000001").withPhone("123456").build()));
    }
}
