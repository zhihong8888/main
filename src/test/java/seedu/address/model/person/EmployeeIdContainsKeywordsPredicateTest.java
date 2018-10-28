package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class EmployeeIdContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("000001");
        List<String> secondPredicateKeywordList = Arrays.asList("000001", "000002");

        EmployeeIdContainsKeywordsPredicate firstPredicate =
                new EmployeeIdContainsKeywordsPredicate(firstPredicateKeywordList);
        EmployeeIdContainsKeywordsPredicate secondPredicate =
                new EmployeeIdContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EmployeeIdContainsKeywordsPredicate firstPredicateCopy =
                new EmployeeIdContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_employeeIdContainsKeywords_returnsTrue() {
        // One keyword
        EmployeeIdContainsKeywordsPredicate predicate =
                new EmployeeIdContainsKeywordsPredicate(Collections.singletonList("000001"));
        assertTrue(predicate.test(new PersonBuilder().withEmployeeId("000001").build()));

        // Multiple keywords
        predicate = new EmployeeIdContainsKeywordsPredicate(Arrays.asList("000001", "000002"));
        assertTrue(predicate.test(new PersonBuilder().withEmployeeId("000002").build()));

        // Only one matching keyword
        predicate = new EmployeeIdContainsKeywordsPredicate(Arrays.asList("000002", "000003"));
        assertTrue(predicate.test(new PersonBuilder().withEmployeeId("000002").build()));
    }

    @Test
    public void test_employeeIdDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        EmployeeIdContainsKeywordsPredicate predicate =
                new EmployeeIdContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withEmployeeId("000001").build()));

        // Non-matching keyword
        predicate = new EmployeeIdContainsKeywordsPredicate(Arrays.asList("000002"));
        assertFalse(predicate.test(new PersonBuilder().withEmployeeId("000001").build()));

        // Keywords match phone, email and address, but does not match employee Id
        predicate = new EmployeeIdContainsKeywordsPredicate(Arrays
                .asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withEmployeeId("000001").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }
}
