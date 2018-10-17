package seedu.address.testutil;
import java.util.ArrayList;
import java.util.List;

import seedu.address.model.recruitment.Recruitment;
import seedu.address.model.recruitment.RecruitmentList;

/**
 * Returns an {@code ScheduleList} with all the typical schedules.
 */
public class TypicalRecruitments {

    private TypicalRecruitments() {} // prevents instantiation

    public static RecruitmentList getTypicalRecruitmentList() {
        RecruitmentList rl = new RecruitmentList();
        for (Recruitment recruitment : getTypicalRecruitments()) {
            rl.addRecruitment(recruitment);
        }
        return rl;
    }

    public static List<Recruitment> getTypicalRecruitments() {
        return new ArrayList<>();
    }
}
