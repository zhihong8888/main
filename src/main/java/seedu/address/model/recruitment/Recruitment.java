package seedu.address.model.recruitment;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a recruitment list in the address book.
 * Guarantees: immutable; is always valid
 */
public class Recruitment {

    // Data fields
    private final Post post;

    public Recruitment (Post post) {
        requireAllNonNull(post);
        this.post = post;
    }

    public Post getPost() {
        return post;
    }

    /**
     * Returns true if both posts have the same identity and data fields.
     * This defines a stronger notion of equality between two posts.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Post)) {
            return false;
        }

        Recruitment otherPerson = (Recruitment) other;
        return otherPerson.getPost().equals(getPost());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(post);
    }

    /**
     * Returns true if both recruitmentPosts of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two posts.
     */
    public boolean isSameRecruitment (Recruitment otherRecruitment) {
        if (otherRecruitment == this) {
            return true;
        }

        return otherRecruitment != null
                && otherRecruitment.getPost().equals(getPost());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Post: ")
                .append(getPost());
        return builder.toString();
    }

}
