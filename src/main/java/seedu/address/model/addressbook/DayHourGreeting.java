package seedu.address.model.addressbook;

import java.util.Calendar;

public class DayHourGreeting {

    private final String MORNING = "Good Morning";
    private final String AFTERNOON = "Good Morning";
    private final String EVENING = "Good Evening";
    private final String NIGHT = "Good Night";

    public static String dayHourGreeting;

    public DayHourGreeting () {
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
    }

    public String getGreeting() {
        return dayHourGreeting;
    }
}
