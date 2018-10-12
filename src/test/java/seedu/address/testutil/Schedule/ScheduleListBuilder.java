package seedu.address.testutil.Schedule;

import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.ScheduleList;

/**
 * A utility class to help with building ScheduleList objects.
 * Example usage: <br>
 *     {@code ScheduleList sl = new ScheduleListBuilder().withSchedule("John_work", "Doe_work").build();}
 */
public class ScheduleListBuilder {
    private ScheduleList scheduleList;

    public ScheduleListBuilder() {
        scheduleList = new ScheduleList();
    }

    public ScheduleListBuilder(ScheduleList scheduleList) {
        this.scheduleList = scheduleList;
    }

    /**
     * Adds a new {@code Schedule} to the {@code ScheduleList} that we are building.
     */
    public ScheduleListBuilder withSchedule(Schedule schedule) {
        scheduleList.addSchedule(schedule);
        return this;
    }

    public ScheduleList build() {
        return scheduleList;
    }
}
