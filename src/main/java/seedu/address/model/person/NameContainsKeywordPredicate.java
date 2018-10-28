package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Name} matches the keyword given.
 */
public class NameContainsKeywordPredicate implements Predicate<Person> {
    private final String keyword;

    public NameContainsKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Person person) {
        return keyword.equals(person.getName().fullName);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordPredicate // instanceof handles nulls
                && keyword.equals(((NameContainsKeywordPredicate) other).keyword)); // state check
    }
}
