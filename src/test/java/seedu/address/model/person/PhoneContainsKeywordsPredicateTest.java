package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class PhoneContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("99334455");
        List<String> secondPredicateKeywordList = Arrays.asList("99334466", "99334455");

        PhoneContainsKeywordsPredicate firstPredicate =
                new PhoneContainsKeywordsPredicate(firstPredicateKeywordList);
        PhoneContainsKeywordsPredicate secondPredicate =
                new PhoneContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PhoneContainsKeywordsPredicate firstPredicateCopy =
                new PhoneContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate == null);

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_employeeIdContainsKeywords_returnsTrue() {
        // One keyword
        PhoneContainsKeywordsPredicate predicate =
                new PhoneContainsKeywordsPredicate(Collections.singletonList("99334455"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("99334455").build()));

        // Multiple keywords
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("99334455", "99334466"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("99334455").build()));

        // Only one matching keyword
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("99334466", "99334477"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("99334466").build()));
    }

    @Test
    public void test_employeeIdDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PhoneContainsKeywordsPredicate predicate =
                new PhoneContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withPhone("99334455").build()));

        // Non-matching keyword
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("99334466"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("99334455").build()));

        // Keywords match employee Id, email and address, but does not match phone
        predicate = new PhoneContainsKeywordsPredicate(Arrays
                .asList("000001", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withEmployeeId("000001").withPhone("99334455")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }
}
