package com.mjhylkema.alarmmate.utils;

import androidx.room.TypeConverter;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class StringConverter {


    @TypeConverter
    public static String calendarToTime(Calendar calendar)
    {
        SimpleDateFormat formatter = new SimpleDateFormat ("hh:mm aa");
        return formatter.format(calendar.getTime());
    }

    private static final String[] daysOfWeek =
            {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

    @TypeConverter
    public static String daysOfWeekAsString(boolean[] activeDays)
    {
        // Build string based on which indices are true in activeDays
        StringBuilder builder = new StringBuilder();
        int activeCount = 0;
        for (int i = 0; i < 7; i++) {
            if (activeDays[i]) {
                String formattedDay = daysOfWeek[i].substring(0, 3) + ", ";
                builder.append(formattedDay);
                activeCount++;
            }
        }

        if (activeCount == 7) {
            return "Everyday";
        } else if (activeCount == 0) {
            return "Not Active";
        }

        boolean satInArray = activeDays[6]; // "Saturday" in activeDays
        boolean sunInArray = activeDays[0]; // "Sunday" in activeDays

        if (satInArray && sunInArray && activeCount == 2) {
            return "Weekends";
        } else if (!satInArray && !sunInArray && activeCount == 5) {
            return "Weekdays";
        }

        if (builder.length() > 1) {
            builder.setLength(builder.length() - 2);
        }

        return builder.toString();
    }
}
