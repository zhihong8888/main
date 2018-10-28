package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class EmailContainsKeywordPredicateTest {

    @Test
    public void equals() {
        String firstPredicateKeyword = "Alex@example.com";
        String secondPredicateKeyword = "Bernice@example.com";

        EmailContainsKeywordPredicate firstPredicate =
                new EmailContainsKeywordPredicate(firstPredicateKeyword);
        EmailContainsKeywordPredicate secondPredicate =
                new EmailContainsKeywordPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EmailContainsKeywordPredicate firstPredicateCopy =
                new EmailContainsKeywordPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse("hello@example.com".equals(firstPredicate));

        // null -> returns false
        assertFalse(firstPredicate == null);

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeyword_returnsTrue() {
        // One keyword
        EmailContainsKeywordPredicate predicate =
                new EmailContainsKeywordPredicate("Alex@example.com");
        assertTrue(predicate.test(new PersonBuilder().withEmail("Alex@example.com").build()));
    }

    @Test
    public void test_employeeIdDoesNotContainKeyword_returnsFalse() {
        // Zero keyword
        EmailContainsKeywordPredicate predicate =
                new EmailContainsKeywordPredicate("");
        assertFalse(predicate.test(new PersonBuilder().withEmail("Alice@example.com").build()));

        // Non-matching keyword
        predicate = new EmailContainsKeywordPredicate("Alex@example.com");
        assertFalse(predicate.test(new PersonBuilder().withEmail("Alice@example.com").build()));

        // Keywords match name but does not match email
        predicate = new EmailContainsKeywordPredicate("Alice");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withEmail("Alice@example.com").build()));
    }

}
