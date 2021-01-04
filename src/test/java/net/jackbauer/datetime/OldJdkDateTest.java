package net.jackbauer.datetime;

import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.spi.CalendarNameProvider;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OldJdkDateTest {
    @Test
    public void shouldGetAfterOneDay() {
        TimeZone utc = TimeZone.getTimeZone("UTC");
        Calendar calendar = Calendar.getInstance(utc);
        calendar.set(1582, Calendar.OCTOBER, 4);
        String pattern = "yyyy.MM.dd";
        String theDay = toString(calendar, pattern, utc);
        assertEquals("1582.10.04", theDay);

        calendar.add(Calendar.DATE, 1);
        String nextDay = toString(calendar, pattern, utc);
        assertEquals("1582.10.15", nextDay);
    }


    @Test
    public void shouldGetAfterOneHour() {
        TimeZone seoul = TimeZone.getTimeZone("Asia/Seoul");
        Calendar calendar = Calendar.getInstance(seoul);
        calendar.set(1988, Calendar.MAY, 7, 23, 0);
        String pattern = "yyyy.MM.dd HH:mm";
        String theTime = toString(calendar, pattern, seoul);
        assertEquals("1988.05.07 23:00", theTime);


        calendar.add(Calendar.HOUR_OF_DAY, 1);
        String after1Hour = toString(calendar, pattern, seoul);

        assertEquals(false, seoul.inDaylightTime(calendar.getTime()));
        assertEquals("1988.05.08 00:00", after1Hour);
    }

    @Test
    public void shouldGetAfterOneMinute() {
        TimeZone seoul = TimeZone.getTimeZone("Asia/Seoul");
        Calendar calendar = Calendar.getInstance(seoul);
        calendar.set(1961, Calendar.AUGUST, 9, 23, 59);
        String pattern = "yyyy.MM.dd HH:mm";
        String theTime = toString(calendar, pattern, seoul);
        assertEquals("1961.08.09 23:59", theTime);

        calendar.add(Calendar.MINUTE, 1);
        String after1Minute = toString(calendar, pattern, seoul);
        assertEquals("1961.08.10 00:30", after1Minute);
    }

    @Test
    public void shouldGetDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1999, 12, 31);
        assertEquals(2000, calendar.get(Calendar.YEAR));
        assertEquals(Calendar.JANUARY, calendar.get(Calendar.MONTH));
        assertEquals(31, calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    @SuppressWarnings("deprecation")
    public void shouldGetDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2014, Calendar.JANUARY, 1);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        assertEquals(Calendar.WEDNESDAY, dayOfWeek);
        assertEquals(4, dayOfWeek);
        Date theDate = calendar.getTime();
        assertEquals(3, theDate.getDay());
    }

    @Test
    public void shouldSetGmtWhenWrongTimeZoneId() {
        // "Asia/Seoul" 을 잘못 지정
        TimeZone zone = TimeZone.getTimeZone("Seoul/Asia");
        assertEquals("GMT", zone.getID());
    }

    private String toString(Calendar calendar, String pattern, TimeZone zone) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        format.setTimeZone(zone);
        return format.format(calendar.getTime());
    }
}
