package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class NameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordsList = Collections.singletonList("first");
        List<String> secondPredicateKeywordsList = Arrays.asList("first", "second");
        String firstPredicateKeyword = "first";
        String secondPredicateKeyword = "second";

        NameContainsKeywordsPredicate firstPredicateList =
                new NameContainsKeywordsPredicate(firstPredicateKeywordsList);
        NameContainsKeywordsPredicate secondPredicateList =
                new NameContainsKeywordsPredicate(secondPredicateKeywordsList);
        NameContainsKeywordsPredicate firstPredicate = new NameContainsKeywordsPredicate(firstPredicateKeyword);
        NameContainsKeywordsPredicate secondPredicate = new NameContainsKeywordsPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicateList.equals(firstPredicateList));
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsKeywordsPredicate firstPredicateListCopy =
                new NameContainsKeywordsPredicate(firstPredicateKeywordsList);
        assertTrue(firstPredicateList.equals(firstPredicateListCopy));
        NameContainsKeywordsPredicate firstPredicateCopy = new NameContainsKeywordsPredicate(firstPredicateKeyword);
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
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        NameContainsKeywordsPredicate predicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
        // Multiple keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameContainsKeyword_returnsTrue() {
        // One keyword
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate("Alice");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Mixed-case keyword
        predicate = new NameContainsKeywordsPredicate("AliCE");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void test_nameDoesNotContainKeyword_returnsFalse() {
        // Zero keyword
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate("");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsPredicate("Carol");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));
    }
}
