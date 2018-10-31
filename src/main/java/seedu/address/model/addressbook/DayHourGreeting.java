package seedu.address.model.addressbook;

import java.util.Calendar;

/**
 * DayHourGreeting is used to get the greeting message based on current time.
 * E.g Good Morning, afternoon, evening, night
 */
public class DayHourGreeting {

    private static final String AFTERNOON = "Good Morning";
    private static final String EVENING = "Good Evening";
    private static final String MORNING = "Good Morning";
    private static final String NIGHT = "Good Night";

    private String dayHourGreeting;

    /**
     * Time of the day based on Operating system clock, sets the greeting message based on current time.
     */
    public DayHourGreeting () {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        if (timeOfDay >= 0 && timeOfDay < 12) {
            dayHourGreeting = MORNING;
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            dayHourGreeting = AFTERNOON;
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            dayHourGreeting = EVENING;
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            dayHourGreeting = NIGHT;
        }
    }

    /**
     * Returns greeting to user.
     */
    public String getGreeting() {
        return dayHourGreeting;
    }
}
