package seedu.address.ui;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.CommandBoxHandle;
import javafx.scene.input.KeyCode;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddExpensesCommand;
import seedu.address.logic.commands.AddLeavesCommand;
import seedu.address.logic.commands.AddRecruitmentPostCommand;
import seedu.address.logic.commands.AddScheduleCommand;
import seedu.address.logic.commands.AddWorksCommand;
import seedu.address.logic.commands.CalculateLeavesCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ClearExpensesCommand;
import seedu.address.logic.commands.ClearRecruitmentPostCommand;
import seedu.address.logic.commands.ClearScheduleCommand;
import seedu.address.logic.commands.DeleteLeavesCommand;
import seedu.address.logic.commands.DeleteScheduleCommand;
import seedu.address.logic.commands.DeleteWorksCommand;
import seedu.address.logic.commands.EditRecruitmentPostCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ModifyAllPayCommand;
import seedu.address.logic.commands.ModifyPayCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemoveExpensesCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SelectExpensesCommand;
import seedu.address.logic.commands.SelectRecruitmentPostCommand;
import seedu.address.logic.commands.SelectScheduleCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class CommandBoxTest extends GuiUnitTest {

    private static final String COMMAND_THAT_SUCCEEDS = ListCommand.COMMAND_WORD;
    private static final String COMMAND_THAT_FAILS = "invalid command";

    private ArrayList<String> defaultStyleOfCommandBox;
    private ArrayList<String> errorStyleOfCommandBox;

    private CommandBoxHandle commandBoxHandle;

    @Before
    public void setUp() {
        Model model = new ModelManager();
        Logic logic = new LogicManager(model);

        CommandBox commandBox = new CommandBox(logic);
        commandBoxHandle = new CommandBoxHandle(getChildNode(commandBox.getRoot(),
                CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
        uiPartRule.setUiPart(commandBox);

        defaultStyleOfCommandBox = new ArrayList<>(commandBoxHandle.getStyleClass());

        errorStyleOfCommandBox = new ArrayList<>(defaultStyleOfCommandBox);
        errorStyleOfCommandBox.add(CommandBox.ERROR_STYLE_CLASS);
    }

    @Test
    public void commandBox_startingWithSuccessfulCommand() {
        assertBehaviorForSuccessfulCommand();
        assertBehaviorForFailedCommand();
    }

    @Test
    public void commandBox_startingWithFailedCommand() {
        assertBehaviorForFailedCommand();
        assertBehaviorForSuccessfulCommand();

        // verify that style is changed correctly even after multiple consecutive failed commands
        assertBehaviorForSuccessfulCommand();
        assertBehaviorForFailedCommand();
        assertBehaviorForFailedCommand();
    }

    @Test
    public void commandBox_handleKeyPress() {
        commandBoxHandle.run(COMMAND_THAT_FAILS);
        assertEquals(errorStyleOfCommandBox, commandBoxHandle.getStyleClass());
        guiRobot.push(KeyCode.ESCAPE);
        assertEquals(errorStyleOfCommandBox, commandBoxHandle.getStyleClass());

        guiRobot.push(KeyCode.A);
        assertEquals(defaultStyleOfCommandBox, commandBoxHandle.getStyleClass());
    }

    @Test
    public void handleKeyPress_startingWithUp() {
        // empty history
        assertInputHistory(KeyCode.UP, "");
        assertInputHistory(KeyCode.DOWN, "");

        // one command
        commandBoxHandle.run(COMMAND_THAT_SUCCEEDS);
        assertInputHistory(KeyCode.UP, COMMAND_THAT_SUCCEEDS);
        assertInputHistory(KeyCode.DOWN, "");

        // two commands (latest command is failure)
        commandBoxHandle.run(COMMAND_THAT_FAILS);
        assertInputHistory(KeyCode.UP, COMMAND_THAT_SUCCEEDS);
        assertInputHistory(KeyCode.UP, COMMAND_THAT_SUCCEEDS);
        assertInputHistory(KeyCode.DOWN, COMMAND_THAT_FAILS);
        assertInputHistory(KeyCode.DOWN, "");
        assertInputHistory(KeyCode.DOWN, "");
        assertInputHistory(KeyCode.UP, COMMAND_THAT_FAILS);

        // insert command in the middle of retrieving previous commands
        guiRobot.push(KeyCode.UP);
        String thirdCommand = "list";
        commandBoxHandle.run(thirdCommand);
        assertInputHistory(KeyCode.UP, thirdCommand);
        assertInputHistory(KeyCode.UP, COMMAND_THAT_FAILS);
        assertInputHistory(KeyCode.UP, COMMAND_THAT_SUCCEEDS);
        assertInputHistory(KeyCode.DOWN, COMMAND_THAT_FAILS);
        assertInputHistory(KeyCode.DOWN, thirdCommand);
        assertInputHistory(KeyCode.DOWN, "");
    }

    @Test
    public void handleKeyPress_startingWithDown() {
        // empty history
        assertInputHistory(KeyCode.DOWN, "");
        assertInputHistory(KeyCode.UP, "");

        // one command
        commandBoxHandle.run(COMMAND_THAT_SUCCEEDS);
        assertInputHistory(KeyCode.DOWN, "");
        assertInputHistory(KeyCode.UP, COMMAND_THAT_SUCCEEDS);

        // two commands
        commandBoxHandle.run(COMMAND_THAT_FAILS);
        assertInputHistory(KeyCode.DOWN, "");
        assertInputHistory(KeyCode.UP, COMMAND_THAT_FAILS);

        // insert command in the middle of retrieving previous commands
        guiRobot.push(KeyCode.UP);
        String thirdCommand = "list";
        commandBoxHandle.run(thirdCommand);
        assertInputHistory(KeyCode.DOWN, "");
        assertInputHistory(KeyCode.UP, thirdCommand);
    }

    /**
     * Runs a command that fails, then verifies that <br>
     *      - the text remains <br>
     *      - the command box's style is the same as {@code errorStyleOfCommandBox}.
     */
    private void assertBehaviorForFailedCommand() {
        commandBoxHandle.run(COMMAND_THAT_FAILS);
        assertEquals(COMMAND_THAT_FAILS, commandBoxHandle.getInput());
        assertEquals(errorStyleOfCommandBox, commandBoxHandle.getStyleClass());
    }

    /**
     * Runs a command that succeeds, then verifies that <br>
     *      - the text is cleared <br>
     *      - the command box's style is the same as {@code defaultStyleOfCommandBox}.
     */
    private void assertBehaviorForSuccessfulCommand() {
        commandBoxHandle.run(COMMAND_THAT_SUCCEEDS);
        assertEquals("", commandBoxHandle.getInput());
        assertEquals(defaultStyleOfCommandBox, commandBoxHandle.getStyleClass());
    }

    /**
     * Pushes {@code keycode} and checks that the input in the {@code commandBox} equals to {@code expectedCommand}.
     */
    private void assertInputHistory(KeyCode keycode, String expectedCommand) {
        guiRobot.push(keycode);
        assertEquals(expectedCommand, commandBoxHandle.getInput());
    }

    @Test
    public void menuBarRaisedEventDisplay() {
        MainWindow.handleAdd();
        guiRobot.pauseForHuman();
        assertEquals(AddCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());


        MainWindow.handleSelect();
        guiRobot.pauseForHuman();
        assertEquals(SelectCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.handleClear();
        guiRobot.pauseForHuman();
        assertEquals(ClearCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.handleFind();
        guiRobot.pauseForHuman();
        assertEquals(FindCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.handleFilter();
        guiRobot.pauseForHuman();
        assertEquals(FilterCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.handleModifyPay();
        guiRobot.pauseForHuman();
        assertEquals(ModifyPayCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.handleAllModifyPay();
        guiRobot.pauseForHuman();
        assertEquals(ModifyAllPayCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.handleAddSchedule();
        guiRobot.pauseForHuman();
        assertEquals(AddScheduleCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.handleDeleteSchedule();
        guiRobot.pauseForHuman();
        assertEquals(DeleteScheduleCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.handleAddWorks();
        guiRobot.pauseForHuman();
        assertEquals(AddWorksCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.handleDeleteWorks();
        guiRobot.pauseForHuman();
        assertEquals(DeleteWorksCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.handleAddLeaves();
        guiRobot.pauseForHuman();
        assertEquals(AddLeavesCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.handleDeleteLeaves();
        guiRobot.pauseForHuman();
        assertEquals(DeleteLeavesCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.handleCalculateLeaves();
        guiRobot.pauseForHuman();
        assertEquals(CalculateLeavesCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.handleSelectSchedule();
        guiRobot.pauseForHuman();
        assertEquals(SelectScheduleCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.handleClearSchedules();
        guiRobot.pauseForHuman();
        assertEquals(ClearScheduleCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.handleHistory();
        guiRobot.pauseForHuman();
        assertEquals(HistoryCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.handleUndo();
        guiRobot.pauseForHuman();
        assertEquals(UndoCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.handleRedo();
        guiRobot.pauseForHuman();
        assertEquals(RedoCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.handleAddExpenses();
        guiRobot.pauseForHuman();
        assertEquals(AddExpensesCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.handleDeleteExpenses();
        guiRobot.pauseForHuman();
        assertEquals(RemoveExpensesCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.handleClearExpenses();
        guiRobot.pauseForHuman();
        assertEquals(ClearExpensesCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.handleSelectExpenses();
        guiRobot.pauseForHuman();
        assertEquals(SelectExpensesCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.handleAddRecruitmentPost();
        guiRobot.pauseForHuman();
        assertEquals(AddRecruitmentPostCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.handleSelectRecruitment();
        guiRobot.pauseForHuman();
        assertEquals(SelectRecruitmentPostCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.handleEditRecruitment();
        guiRobot.pauseForHuman();
        assertEquals(EditRecruitmentPostCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.handleClearRecruitment();
        guiRobot.pauseForHuman();
        assertEquals(ClearRecruitmentPostCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

    }
}
