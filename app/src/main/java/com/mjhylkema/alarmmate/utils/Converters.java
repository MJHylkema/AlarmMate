package com.mjhylkema.alarmmate.utils;

import androidx.room.TypeConverter;

import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class Converters {

    @TypeConverter
    public static Calendar calendarFromLong(long value) {
        if (value == 0)
            return null;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(value);
        return calendar;
    }

    @TypeConverter
    public static long calendarToLong(Calendar calendar) {
        return calendar == null ? null : calendar.getTimeInMillis();
    }

    @TypeConverter
    public static LatLng latLngFromString(String value) {
        String[] splitValue = value.split(":");
        return new LatLng(
                Double.parseDouble(splitValue[0]),
                Double.parseDouble(splitValue[1])
        );
    }

    @TypeConverter
    public static String latLngToString(LatLng latLng) {
        return latLng.latitude + ":" + latLng.longitude;
    }

    @TypeConverter
    public static boolean[] booleanArrayFromString(String value) {
        boolean[] arr = new boolean[7];
        if (value.equals("")) {
            return arr;
        }

        List<String> list = new ArrayList<>(Arrays.asList(value.split(",")));

        int i = 0;
        for (String item : list) {
            arr[i] = Boolean.parseBoolean(item);
            i++;
        }
        return arr;
    }

    @TypeConverter
    public static String booleanArrayToString(boolean[] arr) {
        StringBuilder builder = new StringBuilder();
        if (arr == null) {
            return "";
        }
        for (boolean item : arr) {
            builder.append(item + ",");
        }
        if (builder.length() > 0) { // cut off trailing comma
            builder.setLength(builder.length() - 1);
        }
        return builder.toString();
    }

    @TypeConverter
    public static String calendarToTime(Calendar calendar)
    {
        SimpleDateFormat formatter = new SimpleDateFormat ("hh:mm aa");
        return formatter.format(calendar.getTime());
    }
}
