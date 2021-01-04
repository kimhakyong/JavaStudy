package net.jackbauer.datetime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import org.junit.Test;

public class OldJdkDateTest {
    @Test
    public void shouldGetAfterOneDay() {
        TimeZone utc = TimeZone.getTimeZone("UTC");
        Calendar calendar = Calendar.getInstance(utc);
        calendar.set(1582, Calendar.OCTOBER , 4);
        
        String pattern = "yyyy.MM.dd";
        String theDay = toString(calendar, pattern, utc);
        assertEquals(theDay, "1582.10.04");

//        calendar.add(Calendar.DATE, 1);
//        String nextDay = toString(calendar, pattern, utc);
//        assertEquals(nextDay, "1582.10.05");
    }

    @Test
    public void shouldGetAfterOneHour() {  
        TimeZone seoul = TimeZone.getTimeZone("Asia/Seoul");
        Calendar calendar = Calendar.getInstance(seoul);
        calendar.set(1988, Calendar.MAY , 7, 23, 0);
        
        String pattern = "yyyy.MM.dd HH:mm";
        String theTime = toString(calendar, pattern, seoul);
        assertEquals(theTime, "1988.05.07 23:00");

        calendar.add(Calendar.HOUR_OF_DAY, 1);
        String after1Hour = toString(calendar, pattern, seoul);
        assertTrue(seoul.inDaylightTime(calendar.getTime()));  
        assertEquals(after1Hour, "1988.05.08 00:00");
    }
    
	private String toString(Calendar calendar, String pattern, TimeZone zone) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        format.setTimeZone(zone);
        return format.format(calendar.getTime());
    }
}
