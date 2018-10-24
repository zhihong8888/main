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
    private final WorkExp workExp;
    private final JobDescription jobDescription;

    public Recruitment (Post post, WorkExp workExp, JobDescription jobDescription) {
        requireAllNonNull(post, workExp, jobDescription);
        this.post = post;
        this.workExp = workExp;
        this.jobDescription = jobDescription;
    }

    public Post getPost() {
        return post;
    }
    public WorkExp getWorkExp() {
        return workExp;
    }
    public JobDescription getJobDescription() {
        return jobDescription;
    }

    /**
     * Returns true if both recruitmentPosts have the same identity and data fields.
     * This defines a stronger notion of equality between two recruitmentPosts.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Recruitment)) {
            return false;
        }

        Recruitment otherPerson = (Recruitment) other;
        return otherPerson.getPost().equals(getPost())
                && otherPerson.getWorkExp().equals(getWorkExp())
                && otherPerson.getJobDescription().equals(getJobDescription());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(post, workExp, jobDescription);
    }

    /**
     * Returns true if both recruitmentPosts of the same job position
     * have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two recruitmentPosts.
     */
    public boolean isSameRecruitment (Recruitment otherRecruitment) {
        if (otherRecruitment == this) {
            return true;
        }

        return otherRecruitment != null
                && otherRecruitment.getPost().equals(getPost())
                && otherRecruitment.getWorkExp().equals(getWorkExp())
                && otherRecruitment.getJobDescription().equals(getJobDescription());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Minimal Working Experience: ")
                .append(getWorkExp())
                .append("Job Description: ")
                .append(getJobDescription());
        return builder.toString();
    }

}
