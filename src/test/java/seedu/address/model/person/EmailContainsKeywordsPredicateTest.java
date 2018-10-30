package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class EmailContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordsList = Collections.singletonList("first@example.com");
        List<String> secondPredicateKeywordsList = Arrays.asList("first@example.com", "second@example.com");
        String firstPredicateKeyword = "first@exam.com";
        String secondPredicateKeyword = "second@exam.com";

        EmailContainsKeywordsPredicate firstPredicateList =
                new EmailContainsKeywordsPredicate(firstPredicateKeywordsList);
        EmailContainsKeywordsPredicate secondPredicateList =
                new EmailContainsKeywordsPredicate(secondPredicateKeywordsList);
        EmailContainsKeywordsPredicate firstPredicate = new EmailContainsKeywordsPredicate(firstPredicateKeyword);
        EmailContainsKeywordsPredicate secondPredicate = new EmailContainsKeywordsPredicate(secondPredicateKeyword);


        // same object -> returns true
        assertTrue(firstPredicateList.equals(firstPredicateList));
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EmailContainsKeywordsPredicate firstPredicateListCopy =
                new EmailContainsKeywordsPredicate(firstPredicateKeywordsList);
        assertTrue(firstPredicateList.equals(firstPredicateListCopy));
        EmailContainsKeywordsPredicate firstPredicateCopy = new EmailContainsKeywordsPredicate(firstPredicateKeyword);
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
    public void test_emailContainsKeywords_returnsTrue() {
        // One keyword
        EmailContainsKeywordsPredicate predicate =
                new EmailContainsKeywordsPredicate(Collections.singletonList("Alice@example.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("Alice@example.com").build()));

        // Multiple keywords
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("Alice@example.com", "Bob@example.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("Alice@example.com").build()));

        // Only one matching keyword
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("Bob@example.com", "Carol@example.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("Carol@example.com").build()));

        // Mixed-case keywords
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("aLIce@example.com", "bOB@example.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("Alice@example.com").build()));
    }

    @Test
    public void test_emailContainsKeyword_returnsTrue() {
        // One keyword
        EmailContainsKeywordsPredicate predicate = new EmailContainsKeywordsPredicate("Alice@example.com");
        assertTrue(predicate.test(new PersonBuilder().withEmail("Alice@example.com").build()));

        // Mixed-case keyword
        predicate = new EmailContainsKeywordsPredicate("ALIce@examPLE.cOm");
        assertTrue(predicate.test(new PersonBuilder().withEmail("Alice@example.com").build()));
    }

    @Test
    public void test_emailDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        EmailContainsKeywordsPredicate predicate = new EmailContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withEmail("Alice@example.com").build()));

        // Non-matching keyword
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("Carol@example.com"));
        assertFalse(predicate.test(new PersonBuilder().withEmail("Alice@example.com").build()));

        // Keywords match phone, name and address, but does not match email
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("12345", "Alice", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void test_emailDoesNotContainKeyword_returnsFalse() {
        // Zero keyword
        EmailContainsKeywordsPredicate predicate = new EmailContainsKeywordsPredicate("");
        assertFalse(predicate.test(new PersonBuilder().withEmail("Alice@example.com").build()));

        // Non-matching keyword
        predicate = new EmailContainsKeywordsPredicate("Carol@example.com");
        assertFalse(predicate.test(new PersonBuilder().withEmail("Alice@example.com").build()));
    }
}
