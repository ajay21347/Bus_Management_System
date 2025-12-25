package util;

public class TimeUtil {

    public static String toHoursMinutes(int totalMinutes) {
        int hours = totalMinutes / 60;
        int minutes = totalMinutes % 60;

        if (hours == 0) {
            return minutes + " min";
        }
        return hours + " hr " + minutes + " min";
    }
}
