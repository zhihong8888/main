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
    public static final Recruitment RECRUITMENT_EXAMPLE1 = new RecruitmentBuilder().withPost("Accountants")
                .withWorkExp("5").withJobDescription("To perform audits and financial statement analysis").build();
    public static final Recruitment RECRUITMENT_EXAMPLE2 = new RecruitmentBuilder().withPost("Purchaser")
            .withWorkExp("2").withJobDescription("To handle company's purchase orders").build();

    //Manually added recruitment post examples
    public static final Recruitment RECRUITMENT_EXAMPLE3 = new RecruitmentBuilder().withPost("Finance Manager")
            .withWorkExp("8").withJobDescription("To ensure the financial health for the organisation").build();
    public static final Recruitment RECRUITMENT_EXAMPLE4 = new RecruitmentBuilder().withPost("Admin Manager")
            .withWorkExp("5").withJobDescription("To ensure the general enquiry in the company").build();

    private TypicalRecruitments() {} // prevents instantiation

    public static RecruitmentList getTypicalRecruitmentList() {
        RecruitmentList rl = new RecruitmentList();
        for (Recruitment recruitment : getTypicalRecruitments()) {
            rl.addRecruitment(recruitment);
        }
        return rl;
    }

    public static List<Recruitment> getTypicalRecruitments() {
        return new ArrayList<>(Arrays.asList(RECRUITMENT_EXAMPLE, RECRUITMENT_EXAMPLE1, RECRUITMENT_EXAMPLE2));
    }
}
