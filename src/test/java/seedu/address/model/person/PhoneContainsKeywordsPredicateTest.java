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
        List<String> firstPredicateKeywordsList = Collections.singletonList("99334455");
        List<String> secondPredicateKeywordsList = Arrays.asList("99334466", "99334455");
        String firstPredicateKeyword = "99334455";
        String secondPredicateKeyword = "99334466";

        PhoneContainsKeywordsPredicate firstPredicateList =
                new PhoneContainsKeywordsPredicate(firstPredicateKeywordsList);
        PhoneContainsKeywordsPredicate secondPredicateList =
                new PhoneContainsKeywordsPredicate(secondPredicateKeywordsList);
        PhoneContainsKeywordsPredicate firstPredicate = new PhoneContainsKeywordsPredicate(firstPredicateKeyword);
        PhoneContainsKeywordsPredicate secondPredicate = new PhoneContainsKeywordsPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicateList.equals(firstPredicateList));
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PhoneContainsKeywordsPredicate firstPredicateListCopy =
                new PhoneContainsKeywordsPredicate(firstPredicateKeywordsList);
        assertTrue(firstPredicateList.equals(firstPredicateListCopy));
        PhoneContainsKeywordsPredicate firstPredicateCopy =
                new PhoneContainsKeywordsPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicateList.equals(1));
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicateList == null);
        assertFalse(firstPredicate == null);

        // different person -> returns false
        assertFalse(firstPredicateList.equals(secondPredicateList));
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_phoneContainsKeywords_returnsTrue() {
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
    public void test_phoneContainsKeyword_returnsTrue() {
        // One keyword
        PhoneContainsKeywordsPredicate predicate =
                new PhoneContainsKeywordsPredicate("99334455");
        assertTrue(predicate.test(new PersonBuilder().withPhone("99334455").build()));
    }

    @Test
    public void test_phoneDoesNotContainKeywords_returnsFalse() {
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

    @Test
    public void test_phoneDoesNotContainKeyword_returnsFalse() {
        // Zero keyword
        PhoneContainsKeywordsPredicate predicate = new PhoneContainsKeywordsPredicate("");
        assertFalse(predicate.test(new PersonBuilder().withPhone("99334455").build()));

        // Non-matching keyword
        predicate = new PhoneContainsKeywordsPredicate("99334466");
        assertFalse(predicate.test(new PersonBuilder().withPhone("99334455").build()));
    }
}
