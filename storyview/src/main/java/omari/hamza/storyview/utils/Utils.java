package omari.hamza.storyview.utils;

import java.util.Date;

public class Utils {

    public static String getDurationBetweenDates(Date d1, Date d2) {

        long diff = d1.getTime() - d2.getTime();
        long seconds = diff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        StringBuilder formattedDiff = new StringBuilder();
        if (days != 0) {
            return formattedDiff.append(Math.abs(days) + "d").toString();
        }
        if (hours != 0) {
            return formattedDiff.append(Math.abs(hours) + "h").toString();
        }
        if (minutes != 0) {
            return formattedDiff.append(Math.abs(minutes) + "m").toString();
        }
        if (seconds != 0) {
            return formattedDiff.append(Math.abs(seconds) + "s").toString();
        }

        return "";
    }

}
