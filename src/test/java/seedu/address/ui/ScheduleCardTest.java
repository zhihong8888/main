package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysSchedule;

import org.junit.Test;

import guitests.guihandles.ScheduleCardHandle;
import seedu.address.model.schedule.Schedule;
import seedu.address.testutil.schedule.ScheduleBuilder;

public class ScheduleCardTest extends GuiUnitTest {

    @Test
    public void display() {
        Schedule schedule = new ScheduleBuilder().build();
        ScheduleCard scheduleCard = new ScheduleCard(schedule, 1);
        uiPartRule.setUiPart(scheduleCard);
        assertCardDisplay(scheduleCard, schedule, 1);

    }

    /**
     * Asserts that {@code scheduleCard} displays the details of {@code expectedSchedule} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(ScheduleCard scheduleCard, Schedule expectedSchedule, int expectedId) {
        guiRobot.pauseForHuman();

        ScheduleCardHandle scheduleCardHandle = new ScheduleCardHandle(scheduleCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", scheduleCardHandle.getId());

        // verify schedule details are displayed correctly
        assertCardDisplaysSchedule(expectedSchedule, scheduleCardHandle);
    }

}
