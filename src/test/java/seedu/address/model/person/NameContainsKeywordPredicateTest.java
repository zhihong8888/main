package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class NameContainsKeywordPredicateTest {

    @Test
    public void equals() {
        String firstPredicateKeyword = "Alex";
        String secondPredicateKeyword = "Bernice";

        NameContainsKeywordPredicate firstPredicate =
                new NameContainsKeywordPredicate(firstPredicateKeyword);
        NameContainsKeywordPredicate secondPredicate =
                new NameContainsKeywordPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsKeywordPredicate firstPredicateCopy =
                new NameContainsKeywordPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse("hello".equals(firstPredicate));

        // null -> returns false
        assertFalse(firstPredicate == null);

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeyword_returnsTrue() {
        // One keyword
        NameContainsKeywordPredicate predicate =
                new NameContainsKeywordPredicate("Alex");
        assertTrue(predicate.test(new PersonBuilder().withName("Alex").build()));
    }

    @Test
    public void test_employeeIdDoesNotContainKeyword_returnsFalse() {
        // Zero keyword
        NameContainsKeywordPredicate predicate =
                new NameContainsKeywordPredicate("");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordPredicate("Alex");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Keywords match department but does not match name
        predicate = new NameContainsKeywordPredicate("Finance");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withDepartment("Finance").build()));
    }

}
