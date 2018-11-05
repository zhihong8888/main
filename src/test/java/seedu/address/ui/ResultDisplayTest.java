package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.GREETING_MESSAGE_NEWLINE;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.ui.MainWindow.COMMAND_USAGE;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.ResultDisplayHandle;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
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
import seedu.address.logic.commands.ModifyAllPayCommand;
import seedu.address.logic.commands.ModifyPayCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemoveExpensesCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SelectExpensesCommand;
import seedu.address.logic.commands.SelectRecruitmentPostCommand;
import seedu.address.logic.commands.SelectScheduleCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.addressbook.DayHourGreeting;

public class ResultDisplayTest extends GuiUnitTest {

    private static final NewResultAvailableEvent NEW_RESULT_EVENT_STUB = new NewResultAvailableEvent("Stub");

    private ResultDisplayHandle resultDisplayHandle;

    @Before
    public void setUp() {
        ResultDisplay resultDisplay = new ResultDisplay();
        uiPartRule.setUiPart(resultDisplay);

        resultDisplayHandle = new ResultDisplayHandle(getChildNode(resultDisplay.getRoot(),
                ResultDisplayHandle.RESULT_DISPLAY_ID));
    }

    @Test
    public void display() {
        // default result text
        DayHourGreeting greeting = new DayHourGreeting();
        guiRobot.pauseForHuman();
        assertEquals(greeting.getGreeting() + GREETING_MESSAGE_NEWLINE,
                resultDisplayHandle.getText());

        // new result received
        postNow(NEW_RESULT_EVENT_STUB);
        guiRobot.pauseForHuman();
        assertEquals(NEW_RESULT_EVENT_STUB.message, resultDisplayHandle.getText());
    }

    @Test
    public void menuBarRaisedEventDisplay() {
        MainWindow.raiseEvents(AddCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + AddCommand.MESSAGE_USAGE, resultDisplayHandle.getText());

        MainWindow.raiseEvents(SelectCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + SelectCommand.MESSAGE_USAGE, resultDisplayHandle.getText());

        MainWindow.raiseEvents(ClearCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + ClearCommand.MESSAGE_USAGE, resultDisplayHandle.getText());

        MainWindow.raiseEvents(FindCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + FindCommand.MESSAGE_USAGE, resultDisplayHandle.getText());

        MainWindow.raiseEvents(FilterCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + FilterCommand.MESSAGE_USAGE, resultDisplayHandle.getText());

        MainWindow.raiseEvents(ModifyPayCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + ModifyPayCommand.MESSAGE_USAGE, resultDisplayHandle.getText());

        MainWindow.raiseEvents(ModifyAllPayCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + ModifyAllPayCommand.MESSAGE_USAGE, resultDisplayHandle.getText());

        MainWindow.raiseEvents(AddScheduleCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + AddScheduleCommand.MESSAGE_USAGE, resultDisplayHandle.getText());

        MainWindow.raiseEvents(DeleteScheduleCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + DeleteScheduleCommand.MESSAGE_USAGE, resultDisplayHandle.getText());

        MainWindow.raiseEvents(AddWorksCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + AddWorksCommand.MESSAGE_USAGE, resultDisplayHandle.getText());

        MainWindow.raiseEvents(DeleteWorksCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + DeleteWorksCommand.MESSAGE_USAGE, resultDisplayHandle.getText());

        MainWindow.raiseEvents(AddLeavesCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + AddLeavesCommand.MESSAGE_USAGE, resultDisplayHandle.getText());

        MainWindow.raiseEvents(DeleteLeavesCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + DeleteLeavesCommand.MESSAGE_USAGE, resultDisplayHandle.getText());

        MainWindow.raiseEvents(CalculateLeavesCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + CalculateLeavesCommand.MESSAGE_USAGE, resultDisplayHandle.getText());

        MainWindow.raiseEvents(SelectScheduleCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + SelectScheduleCommand.MESSAGE_USAGE, resultDisplayHandle.getText());

        MainWindow.raiseEvents(ClearScheduleCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + ClearScheduleCommand.MESSAGE_USAGE, resultDisplayHandle.getText());

        MainWindow.raiseEvents(HistoryCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + HistoryCommand.MESSAGE_USAGE, resultDisplayHandle.getText());

        MainWindow.raiseEvents(UndoCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + UndoCommand.MESSAGE_USAGE, resultDisplayHandle.getText());

        MainWindow.raiseEvents(RedoCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + RedoCommand.MESSAGE_USAGE, resultDisplayHandle.getText());

        MainWindow.raiseEvents(AddExpensesCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + AddExpensesCommand.MESSAGE_USAGE, resultDisplayHandle.getText());

        MainWindow.raiseEvents(RemoveExpensesCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + RemoveExpensesCommand.MESSAGE_USAGE, resultDisplayHandle.getText());

        MainWindow.raiseEvents(ClearExpensesCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + ClearExpensesCommand.MESSAGE_USAGE, resultDisplayHandle.getText());

        MainWindow.raiseEvents(SelectExpensesCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + SelectExpensesCommand.MESSAGE_USAGE, resultDisplayHandle.getText());

        MainWindow.raiseEvents(AddRecruitmentPostCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + AddRecruitmentPostCommand.MESSAGE_USAGE2, resultDisplayHandle.getText());

        MainWindow.raiseEvents(SelectRecruitmentPostCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + SelectRecruitmentPostCommand.MESSAGE_USAGE,
                resultDisplayHandle.getText());

        MainWindow.raiseEvents(EditRecruitmentPostCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + EditRecruitmentPostCommand.MESSAGE_USAGE, resultDisplayHandle.getText());

        MainWindow.raiseEvents(ClearRecruitmentPostCommand.COMMAND_WORD);
        guiRobot.pauseForHumanLonger();
        assertEquals(COMMAND_USAGE + ClearRecruitmentPostCommand.MESSAGE_USAGE, resultDisplayHandle.getText());

    }
}
