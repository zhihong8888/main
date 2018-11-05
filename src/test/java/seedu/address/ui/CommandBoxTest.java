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
        MainWindow.raiseEvents(AddCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(AddCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.raiseEvents(SelectCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(SelectCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.raiseEvents(ClearCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(ClearCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.raiseEvents(FindCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(FindCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.raiseEvents(FilterCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(FilterCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.raiseEvents(ModifyPayCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(ModifyPayCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.raiseEvents(ModifyAllPayCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(ModifyAllPayCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.raiseEvents(AddScheduleCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(AddScheduleCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.raiseEvents(DeleteScheduleCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(DeleteScheduleCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.raiseEvents(AddWorksCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(AddWorksCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.raiseEvents(DeleteWorksCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(DeleteWorksCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.raiseEvents(AddLeavesCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(AddLeavesCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.raiseEvents(DeleteLeavesCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(DeleteLeavesCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.raiseEvents(CalculateLeavesCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(CalculateLeavesCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.raiseEvents(SelectScheduleCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(SelectScheduleCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.raiseEvents(ClearScheduleCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(ClearScheduleCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.raiseEvents(HistoryCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(HistoryCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.raiseEvents(UndoCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(UndoCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.raiseEvents(RedoCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(RedoCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.raiseEvents(AddExpensesCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(AddExpensesCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.raiseEvents(RemoveExpensesCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(RemoveExpensesCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.raiseEvents(ClearExpensesCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(ClearExpensesCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.raiseEvents(SelectExpensesCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(SelectExpensesCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.raiseEvents(AddRecruitmentPostCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(AddRecruitmentPostCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.raiseEvents(SelectRecruitmentPostCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(SelectRecruitmentPostCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.raiseEvents(EditRecruitmentPostCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(EditRecruitmentPostCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

        MainWindow.raiseEvents(ClearRecruitmentPostCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(ClearRecruitmentPostCommand.COMMAND_WORD + " ", commandBoxHandle.getInput());

    }
}
