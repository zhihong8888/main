package seedu.address.logic.parser;

import org.junit.Test;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ModifyPayCommand;
import seedu.address.logic.commands.ModifyPayCommand.ModSalaryDescriptor;
import seedu.address.model.person.Bonus;
import seedu.address.model.person.Salary;
import seedu.address.testutil.ModSalaryDescriptorBuilder;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.BONUS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.BONUS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.BONUS_PARSER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BONUS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SALARY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.SALARY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SALARY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BONUS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BONUS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEPARTMENT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PARSER_BONUS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSITION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SALARY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SALARY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

public class ModifyPayCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ModifyPayCommand.MESSAGE_USAGE);

    private ModifyPayCommandParser parser = new ModifyPayCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_SALARY_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", ModifyPayCommand.MESSAGE_NOT_MODIFIED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_SALARY_DESC, Salary.MESSAGE_SALARY_CONSTRAINTS); // invalid salary
        assertParseFailure(parser, "1" + INVALID_BONUS_DESC, Bonus.MESSAGE_BONUS_CONSTRAINTS); // invalid bonus

        // invalid salary followed by valid bonus
        assertParseFailure(parser, "1" + INVALID_SALARY_DESC + BONUS_DESC_AMY,
                Salary.MESSAGE_SALARY_CONSTRAINTS);

        // valid salary followed by invalid bonus.
        assertParseFailure(parser, "1" + SALARY_DESC_AMY + INVALID_BONUS_DESC,
                Bonus.MESSAGE_BONUS_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_SALARY_DESC + INVALID_BONUS_DESC,
                Salary.MESSAGE_SALARY_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + SALARY_DESC_BOB + BONUS_PARSER_DESC_AMY;

        ModSalaryDescriptor descriptor = new ModSalaryDescriptorBuilder().withSalary(VALID_SALARY_BOB)
                .withBonus(VALID_PARSER_BONUS_AMY).build();
        ModifyPayCommand expectedCommand = new ModifyPayCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // salary
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + SALARY_DESC_AMY;
        ModSalaryDescriptor descriptor = new ModSalaryDescriptorBuilder().withSalary(VALID_SALARY_AMY).build();
        ModifyPayCommand expectedCommand = new ModifyPayCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // bonus
        userInput = targetIndex.getOneBased() + BONUS_PARSER_DESC_AMY;
        descriptor = new ModSalaryDescriptorBuilder().withBonus(VALID_PARSER_BONUS_AMY).build();
        expectedCommand = new ModifyPayCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + SALARY_DESC_AMY + BONUS_DESC_AMY
                + SALARY_DESC_AMY + BONUS_DESC_AMY + SALARY_DESC_BOB + BONUS_DESC_BOB;

        ModSalaryDescriptor descriptor = new ModSalaryDescriptorBuilder().withSalary(VALID_SALARY_BOB)
                .withBonus(VALID_BONUS_BOB).build();
        ModifyPayCommand expectedCommand = new ModifyPayCommand(targetIndex, descriptor);

        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }
}
