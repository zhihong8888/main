package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.events.ui.NewMenuBarCmdClickedEvent;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.logic.Logic;
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
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteLeavesCommand;
import seedu.address.logic.commands.DeleteRecruitmentPostCommand;
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
import seedu.address.model.UserPrefs;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    public static final String COMMAND_USAGE = "[Command usage] ";
    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private BrowserPanel browserPanel;
    private ExpensesListPanel expensesListPanel;
    private PersonListPanel personListPanel;
    private ScheduleListPanel scheduleListPanel;
    private RecruitmentListPanel recruitmentListPanel;
    private Config config;
    private UserPrefs prefs;
    private HelpWindow helpWindow;

    //@FXML
    //private StackPane browserPlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane expensesListPanelPlaceholder;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane scheduleListPanelPlaceholder;

    @FXML
    private StackPane recruitmentListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    public MainWindow(Stage primaryStage, Config config, UserPrefs prefs, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        this.config = config;
        this.prefs = prefs;

        // Configure the UI
        setTitle(config.getAppTitle());
        setWindowDefaultSize(prefs);

        setAccelerators();
        registerAsAnEventHandler(this);

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        //browserPanel = new BrowserPanel();
        //browserPlaceholder.getChildren().add(browserPanel.getRoot());

        expensesListPanel = new ExpensesListPanel(logic.getFilteredExpensesList());
        expensesListPanelPlaceholder.getChildren().add(expensesListPanel.getRoot());

        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        scheduleListPanel = new ScheduleListPanel(logic.getFilteredScheduleList());
        scheduleListPanelPlaceholder.getChildren().add(scheduleListPanel.getRoot());

        recruitmentListPanel = new RecruitmentListPanel(logic.getFilteredRecruitmentList());
        recruitmentListPanelPlaceholder.getChildren().add(recruitmentListPanel.getRoot());

        ResultDisplay resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(prefs.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(logic);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    void hide() {
        primaryStage.hide();
    }

    private void setTitle(String appTitle) {
        primaryStage.setTitle(appTitle);
    }

    /**
     * Sets the default size based on user preferences.
     */
    private void setWindowDefaultSize(UserPrefs prefs) {
        primaryStage.setHeight(prefs.getGuiSettings().getWindowHeight());
        primaryStage.setWidth(prefs.getGuiSettings().getWindowWidth());
        if (prefs.getGuiSettings().getWindowCoordinates() != null) {
            primaryStage.setX(prefs.getGuiSettings().getWindowCoordinates().getX());
            primaryStage.setY(prefs.getGuiSettings().getWindowCoordinates().getY());
        }
    }

    /**
     * Returns the current size and the position of the main Window.
     */
    GuiSettings getCurrentGuiSetting() {
        return new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    //------------------------------------------Person Related Menu ---------------------------------------------//

    /**
     * CHRS related commands
     */
    @FXML
    public void handleAdd() {
        raiseEvents(AddCommand.COMMAND_WORD);
    }

    /**
     * CHRS related commands
     */
    @FXML
    public void handleSelect() {
        raiseEvents(SelectCommand.COMMAND_WORD);
    }

    /**
     * CHRS related commands
     */
    @FXML
    public void handleClear() {
        raiseEvents(ClearCommand.COMMAND_WORD);
    }

    /**
     * CHRS related commands
     */
    @FXML
    public void handleList() {
        raiseEvents(ListCommand.COMMAND_WORD);
    }

    /**
     * CHRS related commands
     */
    @FXML
    public void handleDelete() {
        raiseEvents(DeleteCommand.COMMAND_WORD);
    }

    /**
     * CHRS related commands
     */
    @FXML
    public void handleFind() {
        raiseEvents(FindCommand.COMMAND_WORD);
    }

    /**
     * CHRS related commands
     */
    @FXML
    public void handleFilter() {
        raiseEvents(FilterCommand.COMMAND_WORD);
    }

    /**
     * CHRS related commands
     */
    @FXML
    public void handleModifyPay() {
        raiseEvents(ModifyPayCommand.COMMAND_WORD);
    }

    /**
     * CHRS related commands
     */
    @FXML
    public void handleAllModifyPay() {
        raiseEvents(ModifyAllPayCommand.COMMAND_WORD);
    }

    /**
     * CHRS related commands
     */
    @FXML
    public void handleAddSchedule() {
        raiseEvents(AddScheduleCommand.COMMAND_WORD);
    }

    /**
     * CHRS related commands
     */
    @FXML
    public void handleDeleteSchedule() {
        raiseEvents(DeleteScheduleCommand.COMMAND_WORD);
    }

    /**
     * CHRS related commands
     */
    @FXML
    public void handleAddWorks() {
        raiseEvents(AddWorksCommand.COMMAND_WORD);
    }

    /**
     * CHRS related commands
     */
    @FXML
    public void handleDeleteWorks() {
        raiseEvents(DeleteWorksCommand.COMMAND_WORD);
    }

    /**
     * CHRS related commands
     */
    @FXML
    public void handleAddLeaves() {
        raiseEvents(AddLeavesCommand.COMMAND_WORD);
    }

    /**
     * CHRS related commands
     */
    @FXML
    public void handleDeleteLeaves() {
        raiseEvents(DeleteLeavesCommand.COMMAND_WORD);
    }

    /**
     * CHRS related commands
     */
    @FXML
    public void handleCalculateLeaves() {
        raiseEvents(CalculateLeavesCommand.COMMAND_WORD);
    }


    /**
     * CHRS related commands
     */
    @FXML
    public void handleSelectSchedule() {
        raiseEvents(SelectScheduleCommand.COMMAND_WORD);
    }

    /**
     * CHRS related commands
     */
    @FXML
    public void handleClearSchedules() {
        raiseEvents(ClearScheduleCommand.COMMAND_WORD);
    }

    /**
     * CHRS related commands
     */
    @FXML
    public void handleHistory() {
        raiseEvents(HistoryCommand.COMMAND_WORD);
    }

    /**
     * CHRS related commands
     */
    @FXML
    public void handleUndo() {
        raiseEvents(UndoCommand.COMMAND_WORD);
    }

    /**
     * CHRS related commands
     */
    @FXML
    public void handleRedo() {
        raiseEvents(RedoCommand.COMMAND_WORD);
    }

    /**
     * CHRS related commands
     */
    @FXML
    public void handleAddExpenses() {
        raiseEvents(AddExpensesCommand.COMMAND_WORD);
    }

    /**
     * CHRS related commands
     */
    @FXML
    public void handleDeleteExpenses() {
        raiseEvents(RemoveExpensesCommand.COMMAND_WORD);
    }

    /**
     * CHRS related commands
     */
    @FXML
    public void handleClearExpenses() {
        raiseEvents(ClearExpensesCommand.COMMAND_WORD);
    }

    /**
     * CHRS related commands
     */
    @FXML
    public void handleSelectExpenses() {
        raiseEvents(SelectExpensesCommand.COMMAND_WORD);
    }

    /**
     * CHRS related commands
     */
    @FXML
    public void handleAddRecruitmentPost() {
        raiseEvents(AddRecruitmentPostCommand.COMMAND_WORD);
    }

    /**
     * CHRS related commands
     */
    @FXML
    public void handleDeleteRecruitmentPost() {
        raiseEvents(DeleteRecruitmentPostCommand.COMMAND_WORD);
    }

    /**
     * CHRS related commands
     */
    @FXML
    public void handleSelectRecruitment() {
        raiseEvents(SelectRecruitmentPostCommand.COMMAND_WORD);
    }

    /**
     * CHRS related commands
     */
    @FXML
    public void handleEditRecruitment() {
        raiseEvents(EditRecruitmentPostCommand.COMMAND_WORD);

    }

    /**
     * CHRS related commands
     */
    @FXML
    public void handleClearRecruitment() {
        raiseEvents(ClearRecruitmentPostCommand.COMMAND_WORD);
    }

    /**
     * Raise events after menu bar clicked.
     */
    public static void raiseEvents(String command) {
        switch(command) {
        case AddCommand.COMMAND_WORD:
            raise(new NewMenuBarCmdClickedEvent(AddCommand.COMMAND_WORD + " "));
            raise(new NewResultAvailableEvent(COMMAND_USAGE + AddCommand.MESSAGE_USAGE));
            break;
        case SelectCommand.COMMAND_WORD:
            raise(new NewMenuBarCmdClickedEvent(SelectCommand.COMMAND_WORD + " "));
            raise(new NewResultAvailableEvent(COMMAND_USAGE + SelectCommand.MESSAGE_USAGE));
            break;
        case ClearCommand.COMMAND_WORD:
            raise(new NewMenuBarCmdClickedEvent(ClearCommand.COMMAND_WORD + " "));
            raise(new NewResultAvailableEvent(COMMAND_USAGE + ClearCommand.MESSAGE_USAGE));
            break;
        case ListCommand.COMMAND_WORD:
            raise(new NewMenuBarCmdClickedEvent(ListCommand.COMMAND_WORD + " "));
            raise(new NewResultAvailableEvent(COMMAND_USAGE + ListCommand.MESSAGE_USAGE));
            break;
        case DeleteCommand.COMMAND_WORD:
            raise(new NewMenuBarCmdClickedEvent(DeleteCommand.COMMAND_WORD + " "));
            raise(new NewResultAvailableEvent(COMMAND_USAGE + DeleteCommand.MESSAGE_USAGE));
            break;
        case FindCommand.COMMAND_WORD:
            raise(new NewMenuBarCmdClickedEvent(FindCommand.COMMAND_WORD + " "));
            raise(new NewResultAvailableEvent(COMMAND_USAGE + FindCommand.MESSAGE_USAGE));
            break;
        case FilterCommand.COMMAND_WORD:
            raise(new NewMenuBarCmdClickedEvent(FilterCommand.COMMAND_WORD + " "));
            raise(new NewResultAvailableEvent(COMMAND_USAGE + FilterCommand.MESSAGE_USAGE));
            break;
        case ModifyPayCommand.COMMAND_WORD:
            raise(new NewMenuBarCmdClickedEvent(ModifyPayCommand.COMMAND_WORD + " "));
            raise(new NewResultAvailableEvent(COMMAND_USAGE + ModifyPayCommand.MESSAGE_USAGE));
            break;
        case ModifyAllPayCommand.COMMAND_WORD:
            raise(new NewMenuBarCmdClickedEvent(ModifyAllPayCommand.COMMAND_WORD + " "));
            raise(new NewResultAvailableEvent(COMMAND_USAGE + ModifyAllPayCommand.MESSAGE_USAGE));
            break;
        case AddScheduleCommand.COMMAND_WORD:
            raise(new NewMenuBarCmdClickedEvent(AddScheduleCommand.COMMAND_WORD + " "));
            raise(new NewResultAvailableEvent(COMMAND_USAGE + AddScheduleCommand.MESSAGE_USAGE));
            break;
        case DeleteScheduleCommand.COMMAND_WORD:
            raise(new NewMenuBarCmdClickedEvent(DeleteScheduleCommand.COMMAND_WORD + " "));
            raise(new NewResultAvailableEvent(COMMAND_USAGE + DeleteScheduleCommand.MESSAGE_USAGE));
            break;
        case AddWorksCommand.COMMAND_WORD:
            raise(new NewMenuBarCmdClickedEvent(AddWorksCommand.COMMAND_WORD + " "));
            raise(new NewResultAvailableEvent(COMMAND_USAGE + AddWorksCommand.MESSAGE_USAGE));
            break;
        case DeleteWorksCommand.COMMAND_WORD:
            raise(new NewMenuBarCmdClickedEvent(DeleteWorksCommand.COMMAND_WORD + " "));
            raise(new NewResultAvailableEvent(COMMAND_USAGE + DeleteWorksCommand.MESSAGE_USAGE));
            break;
        case AddLeavesCommand.COMMAND_WORD:
            raise(new NewMenuBarCmdClickedEvent(AddLeavesCommand.COMMAND_WORD + " "));
            raise(new NewResultAvailableEvent(COMMAND_USAGE + AddLeavesCommand.MESSAGE_USAGE));
            break;
        case DeleteLeavesCommand.COMMAND_WORD:
            raise(new NewMenuBarCmdClickedEvent(DeleteLeavesCommand.COMMAND_WORD + " "));
            raise(new NewResultAvailableEvent(COMMAND_USAGE + DeleteLeavesCommand.MESSAGE_USAGE));
            break;
        case CalculateLeavesCommand.COMMAND_WORD:
            raise(new NewMenuBarCmdClickedEvent(CalculateLeavesCommand.COMMAND_WORD + " "));
            raise(new NewResultAvailableEvent(COMMAND_USAGE + CalculateLeavesCommand.MESSAGE_USAGE));
            break;
        case SelectScheduleCommand.COMMAND_WORD:
            raise(new NewMenuBarCmdClickedEvent(SelectScheduleCommand.COMMAND_WORD + " "));
            raise(new NewResultAvailableEvent(COMMAND_USAGE + SelectScheduleCommand.MESSAGE_USAGE));
            break;
        case ClearScheduleCommand.COMMAND_WORD:
            raise(new NewMenuBarCmdClickedEvent(ClearScheduleCommand.COMMAND_WORD + " "));
            raise(new NewResultAvailableEvent(COMMAND_USAGE + ClearScheduleCommand.MESSAGE_USAGE));
            break;
        case HistoryCommand.COMMAND_WORD:
            raise(new NewMenuBarCmdClickedEvent(HistoryCommand.COMMAND_WORD + " "));
            raise(new NewResultAvailableEvent(COMMAND_USAGE + HistoryCommand.MESSAGE_USAGE));
            break;
        case UndoCommand.COMMAND_WORD:
            raise(new NewMenuBarCmdClickedEvent(UndoCommand.COMMAND_WORD + " "));
            raise(new NewResultAvailableEvent(COMMAND_USAGE + UndoCommand.MESSAGE_USAGE));
            break;
        case RedoCommand.COMMAND_WORD:
            raise(new NewMenuBarCmdClickedEvent(RedoCommand.COMMAND_WORD + " "));
            raise(new NewResultAvailableEvent(COMMAND_USAGE + RedoCommand.MESSAGE_USAGE));
            break;
        case AddExpensesCommand.COMMAND_WORD:
            raise(new NewMenuBarCmdClickedEvent(AddExpensesCommand.COMMAND_WORD + " "));
            raise(new NewResultAvailableEvent(COMMAND_USAGE + AddExpensesCommand.MESSAGE_USAGE));
            break;
        case RemoveExpensesCommand.COMMAND_WORD:
            raise(new NewMenuBarCmdClickedEvent(RemoveExpensesCommand.COMMAND_WORD + " "));
            raise(new NewResultAvailableEvent(COMMAND_USAGE + RemoveExpensesCommand.MESSAGE_USAGE));
            break;
        case ClearExpensesCommand.COMMAND_WORD:
            raise(new NewMenuBarCmdClickedEvent(ClearExpensesCommand.COMMAND_WORD + " "));
            raise(new NewResultAvailableEvent(COMMAND_USAGE + ClearExpensesCommand.MESSAGE_USAGE));
            break;
        case SelectExpensesCommand.COMMAND_WORD:
            raise(new NewMenuBarCmdClickedEvent(SelectExpensesCommand.COMMAND_WORD + " "));
            raise(new NewResultAvailableEvent(COMMAND_USAGE + SelectExpensesCommand.MESSAGE_USAGE));
            break;
        case AddRecruitmentPostCommand.COMMAND_WORD:
            raise(new NewMenuBarCmdClickedEvent(AddRecruitmentPostCommand.COMMAND_WORD + " "));
            raise(new NewResultAvailableEvent(COMMAND_USAGE + AddRecruitmentPostCommand.MESSAGE_USAGE2));
            break;
        case DeleteRecruitmentPostCommand.COMMAND_WORD:
            raise(new NewMenuBarCmdClickedEvent(DeleteRecruitmentPostCommand.COMMAND_WORD + " "));
            raise(new NewResultAvailableEvent(COMMAND_USAGE + DeleteRecruitmentPostCommand.MESSAGE_USAGE));
            break;
        case SelectRecruitmentPostCommand.COMMAND_WORD:
            raise(new NewMenuBarCmdClickedEvent(SelectRecruitmentPostCommand.COMMAND_WORD + " "));
            raise(new NewResultAvailableEvent(COMMAND_USAGE + SelectRecruitmentPostCommand.MESSAGE_USAGE));
            break;
        case EditRecruitmentPostCommand.COMMAND_WORD:
            raise(new NewMenuBarCmdClickedEvent(EditRecruitmentPostCommand.COMMAND_WORD + " "));
            raise(new NewResultAvailableEvent(COMMAND_USAGE + EditRecruitmentPostCommand.MESSAGE_USAGE));
            break;
        case ClearRecruitmentPostCommand.COMMAND_WORD:
            raise(new NewMenuBarCmdClickedEvent(ClearRecruitmentPostCommand.COMMAND_WORD + " "));
            raise(new NewResultAvailableEvent(COMMAND_USAGE + ClearRecruitmentPostCommand.MESSAGE_USAGE));
            break;
        default:
            break;
        }
    }



    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
    }

    public ExpensesListPanel getExpensesListPanel() {
        return expensesListPanel;
    }

    public PersonListPanel getPersonListPanel() {
        return personListPanel;
    }

    public ScheduleListPanel getScheduleListPanel() {
        return scheduleListPanel;
    }

    /*
    void releaseResources() {
        browserPanel.freeResources();
    }
    */

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleHelp();
    }
}
