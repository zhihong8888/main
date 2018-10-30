package seedu.address.model.person;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Person> {
    private List<String> keywords = new ArrayList<>();
    private String keyword = "";

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }
    public NameContainsKeywordsPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Person person) {
        if (!keyword.isEmpty()) {
            return keyword.equalsIgnoreCase(person.getName().fullName);
        }

        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (!keyword.isEmpty()) {
            return other == this // short circuit if same object
                    || (other instanceof NameContainsKeywordsPredicate // instanceof handles null
                    && keyword.equals(((NameContainsKeywordsPredicate) other).keyword)); // state check
        }

        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
