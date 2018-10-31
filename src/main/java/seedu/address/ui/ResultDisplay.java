package seedu.address.ui;

import java.util.Calendar;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(ResultDisplay.class);
    private static final String FXML = "ResultDisplay.fxml";

    private final StringProperty displayed;

    private String dayHourGreeting;
    private final String MORNING = "Good Morning";
    private final String AFTERNOON = "Good Morning";
    private final String EVENING = "Good Evening";
    private final String NIGHT = "Good Night";

    private final String GREETING_MESSAGE = " Admin! "
            + "\nWelcome to Centralised Human Resource System. "
            + "\nEnter a command to begin. Press F1 for help.";

    @FXML
    private TextArea resultDisplay;

    public ResultDisplay() {
        super(FXML);
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        if(timeOfDay >= 0 && timeOfDay < 12){
            dayHourGreeting = MORNING;
        }else if(timeOfDay >= 12 && timeOfDay < 16){
            dayHourGreeting = AFTERNOON;
        }else if(timeOfDay >= 16 && timeOfDay < 21){
            dayHourGreeting = EVENING;
        }else if(timeOfDay >= 21 && timeOfDay < 24){
            dayHourGreeting = NIGHT;
        }
        displayed = new SimpleStringProperty(dayHourGreeting + GREETING_MESSAGE);
        resultDisplay.textProperty().bind(displayed);
        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handleNewResultAvailableEvent(NewResultAvailableEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Platform.runLater(() -> displayed.setValue(event.message));
    }

}
