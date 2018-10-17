package seedu.address.testutil;

import seedu.address.model.recruitment.Recruitment;
import seedu.address.model.recruitment.RecruitmentList;

import java.util.ArrayList;
import java.util.List;

public class TypicalRecruitments {

    private TypicalRecruitments() {} // prevents instantiation
    /**
     * Returns an {@code ScheduleList} with all the typical schedules.
     */
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
