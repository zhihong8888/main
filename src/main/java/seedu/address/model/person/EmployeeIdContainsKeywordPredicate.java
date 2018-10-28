package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code EmployeeId} matches the keyword given.
 */
public class EmployeeIdContainsKeywordPredicate implements Predicate<Person> {
    private final String keyword;

    public EmployeeIdContainsKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Person person) {
        return keyword.equals(person.getEmployeeId().value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmployeeIdContainsKeywordPredicate // instanceof handles nulls
                && keyword.equals(((EmployeeIdContainsKeywordPredicate) other).keyword)); // state check
    }

}
