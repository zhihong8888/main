package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.schedule.ScheduleList;
import seedu.address.storage.schedule.XmlSerializableScheduleList;
import seedu.address.testutil.schedule.TypicalSchedules;


public class XmlSerializableScheduleListTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlSerializableScheduleListTest");
    private static final Path TYPICAL_SCHEDULES_FILE = TEST_DATA_FOLDER.resolve("typicalSchedulesScheduleList.xml");
    private static final Path INVALID_SCHEDULE_FILE = TEST_DATA_FOLDER.resolve("invalidScheduleScheduleList.xml");
    private static final Path DUPLICATE_SCHEDULE_FILE = TEST_DATA_FOLDER.resolve("duplicateScheduleScheduleList.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalSchedulesFile_success() throws Exception {
        XmlSerializableScheduleList dataFromFile = XmlUtil.getDataFromFile(TYPICAL_SCHEDULES_FILE,
                XmlSerializableScheduleList.class);
        ScheduleList scheduleListFromFile = dataFromFile.toModelType();
        ScheduleList typicalSchedulesScheduleList = TypicalSchedules.getTypicalScheduleList();
        assertEquals(scheduleListFromFile, typicalSchedulesScheduleList);
    }

    @Test
    public void toModelType_invalidScheduleList_throwsIllegalValueException() throws Exception {
        XmlSerializableScheduleList dataFromFile = XmlUtil.getDataFromFile(INVALID_SCHEDULE_FILE,
                XmlSerializableScheduleList.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        XmlSerializableScheduleList dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_SCHEDULE_FILE,
                XmlSerializableScheduleList.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableScheduleList.MESSAGE_DUPLICATE_SCHEDULE);
        dataFromFile.toModelType();
    }
}
