package fr.pandorica.utils;

public class Timer {

    public static String getTimerToString(Long time) {
        Long remainder = time;
        Long day = remainder / 86400;
        Long year = day / 365;
        Long hour = remainder / 3600 % 24;
        Long minutes = remainder / 60 % 60;
        Long seconds = remainder % 60;
        String yrs = (year < 10 ? "0" : "") + year;
        String dys = ((day % 365) < 10 ? "00" : ((day % 365) < 100 ? "0" : "")) + (day % 365);
        String hrs = (hour < 10 ? "0" : "") + hour;
        String mins = (minutes < 10 ? "0" : "") + minutes;
        String secs = (seconds < 10 ? "0" : "") + seconds;
        return "§a" + yrs + "•" + dys + "•" + hrs + "•" + mins + "•" + secs;
    }
}
