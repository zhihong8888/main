package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.recruitment.Recruitment;
import seedu.address.model.recruitment.RecruitmentList;
import seedu.address.testutil.recruitment.RecruitmentBuilder;

/**
 * Returns an {@code ScheduleList} with all the typical schedules.
 */
public class TypicalRecruitments {

    public static final String VALID_JOB_POSITION = "IT Manager";
    public static final String VALID_MINIMUM_EXPERIENCE = "5";
    public static final String VALID_JOB_DESCRIPTION = "To maintain IT related equipment in the company";

    public static final Recruitment RECRUITMENT_EXAMPLE = new RecruitmentBuilder().withPost("Network Engineer")
            .withWorkExp("3").withJobDescription("Ensure all networking devices are in working condition").build();

    private TypicalRecruitments() {} // prevents instantiation

    public static RecruitmentList getTypicalRecruitmentList() {
        RecruitmentList rl = new RecruitmentList();
        for (Recruitment recruitment : getTypicalRecruitments()) {
            rl.addRecruitment(recruitment);
        }
        return rl;
    }

    public static List<Recruitment> getTypicalRecruitments() {
        return new ArrayList<>(Arrays.asList(RECRUITMENT_EXAMPLE));
    }
}
