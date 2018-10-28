package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Email} matches the keyword given.
 */
public class EmailContainsKeywordPredicate implements Predicate<Person> {
    private final String keyword;

    public EmailContainsKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Person person) {
        return keyword.equals(person.getEmail().value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailContainsKeywordPredicate // instanceof handles nulls
                && keyword.equals(((EmailContainsKeywordPredicate) other).keyword)); // state check
    }
}
