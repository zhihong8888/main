package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_OVERLOAD_PREFIX_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DATE_SCHEDULE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMPLOYEEID_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMPLOYEEID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SCHEDULE_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SCHEDULE_TYPE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TYPE_SCHEDULE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMPLOYEEID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.schedule.TypicalSchedules.BOB;

import org.junit.Test;

import seedu.address.logic.commands.AddScheduleCommand;
import seedu.address.model.person.EmployeeId;
import seedu.address.model.schedule.Date;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.Type;
import seedu.address.testutil.schedule.ScheduleBuilder;

public class AddScheduleCommandParserTest {
    private AddScheduleCommandParser parser = new AddScheduleCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Schedule expectedSchedule = new ScheduleBuilder(BOB).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + EMPLOYEEID_DESC_BOB
                + TYPE_SCHEDULE_DESC_BOB + DATE_SCHEDULE_DESC_BOB, new AddScheduleCommand(expectedSchedule));
    }

    @Test
    public void parse_multipleFields_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_OVERLOAD_PREFIX_FORMAT,
                AddScheduleCommand.MESSAGE_USAGE);

        // multiple employeeIds
        assertParseFailure(parser, PREAMBLE_WHITESPACE + EMPLOYEEID_DESC_BOB + EMPLOYEEID_DESC_BOB
                + TYPE_SCHEDULE_DESC_BOB + DATE_SCHEDULE_DESC_BOB, expectedMessage);

        // multiple schedule types
        assertParseFailure(parser, PREAMBLE_WHITESPACE + EMPLOYEEID_DESC_BOB + EMPLOYEEID_DESC_BOB
                + TYPE_SCHEDULE_DESC_BOB + TYPE_SCHEDULE_DESC_BOB + DATE_SCHEDULE_DESC_BOB, expectedMessage);

        // multiple schedule types
        assertParseFailure(parser, PREAMBLE_WHITESPACE + EMPLOYEEID_DESC_BOB + EMPLOYEEID_DESC_BOB
                + TYPE_SCHEDULE_DESC_BOB + DATE_SCHEDULE_DESC_BOB + DATE_SCHEDULE_DESC_BOB, expectedMessage);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddScheduleCommand.MESSAGE_USAGE);

        // missing employeeid prefix
        assertParseFailure(parser, VALID_EMPLOYEEID_BOB
                + TYPE_SCHEDULE_DESC_BOB + DATE_SCHEDULE_DESC_BOB, expectedMessage);

        // missing type prefix
        assertParseFailure(parser, EMPLOYEEID_DESC_BOB
                + VALID_TYPE_BOB + DATE_SCHEDULE_DESC_BOB, expectedMessage);

        // missing date prefix
        assertParseFailure(parser, EMPLOYEEID_DESC_BOB
                + TYPE_SCHEDULE_DESC_BOB + VALID_DATE_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid emplyoeeid
        assertParseFailure(parser, INVALID_EMPLOYEEID_DESC
                + TYPE_SCHEDULE_DESC_BOB + DATE_SCHEDULE_DESC_BOB, EmployeeId.MESSAGE_EMPLOYEEID_CONSTRAINTS);

        // invalid type
        assertParseFailure(parser, EMPLOYEEID_DESC_BOB
                + INVALID_SCHEDULE_TYPE_DESC + DATE_SCHEDULE_DESC_BOB, Type.MESSAGE_TYPE_CONSTRAINTS);

        // invalid type
        assertParseFailure(parser, EMPLOYEEID_DESC_BOB
                + TYPE_SCHEDULE_DESC_BOB + INVALID_SCHEDULE_DATE_DESC, Date.MESSAGE_DATE_CONSTRAINTS_DEFAULT);
    }

}
