package net.jackbauer.datetime;

import org.joda.time.*;
import org.joda.time.chrono.GJChronology;
import org.joda.time.chrono.GregorianChronology;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JodaTimeTest {
    @Test
    public void shouldGetAfterOneDay() {
        Chronology chrono = GregorianChronology.getInstance();
        LocalDate theDay = new LocalDate(1582, 10, 4, chrono);
        String pattern = "yyyy.MM.dd";
        assertEquals("1582.10.04", theDay.toString(pattern));

        LocalDate nextDay = theDay.plusDays(1);
        assertEquals("1582.10.05", nextDay.toString(pattern));
    }

    @Test
    public void shouldGetAfterOneDayWidthGJChronology() {
        Chronology chrono = GJChronology.getInstance();
        LocalDate theDay = new LocalDate(1582, 10, 4, chrono);
        String pattern = "yyyy.MM.dd";
        assertEquals("1582.10.04", theDay.toString(pattern));

        LocalDate nextDay = theDay.plusDays(1);
        assertEquals("1582.10.15", nextDay.toString(pattern));
    }

    @Test
    public void shouldGetAfterOneHour() {
        DateTimeZone seoul = DateTimeZone.forID("Asia/Seoul");
        DateTime theTime = new DateTime(1988, 5, 8, 1, 0, seoul);
        String pattern = "yyyy.MM.dd HH:mm";
        assertEquals("1988.05.08 01:00", theTime.toString(pattern));
        assertTrue(seoul.isStandardOffset(theTime.getMillis()));

        DateTime after1Hour = theTime.plusHours(1);
        assertEquals("1988.05.08 03:00", after1Hour.toString(pattern));
        assertFalse(seoul.isStandardOffset(after1Hour.getMillis()));
    }

    @Test
    public void shouldGetAfterOneMinute() {
        DateTimeZone seoul = DateTimeZone.forID("Asia/Seoul");
        DateTime theTime = new DateTime(1961, 8, 9, 23, 59, seoul);
        String pattern = "yyyy.MM.dd HH:mm";
        assertEquals("1961.08.09 23:59", theTime.toString(pattern));

        DateTime after1Minute = theTime.plusMinutes(1);
        assertEquals("1961.08.10 00:30", after1Minute.toString(pattern));
    }

    @Test
    public void shouldGetAfterTwoSecond() {
        DateTimeZone utc = DateTimeZone.forID("UTC");
        DateTime theTime = new DateTime(2012, 6, 30, 23, 59, 59, utc);
        String pattern = "yyyy.MM.dd HH:mm:ss";
        assertEquals("2012.06.30 23:59:59", theTime.toString(pattern));

        DateTime after2Seconds = theTime.plusSeconds(2);
        assertEquals("2012.07.01 00:00:01", after2Seconds.toString(pattern));
    }

    @Test
    public void shouldGetDate() {
        LocalDate theDay = new LocalDate(1999, 12, 31);

        assertEquals(1999, theDay.getYear());
        assertEquals(12, theDay.getMonthOfYear());
        assertEquals(31, theDay.getDayOfMonth());
    }

    @Test
    public void shouldNotAcceptWrongMonth() {
        assertThrows(IllegalFieldValueException.class, () -> {
            new LocalDate(1999, 13, 31);
        });
    }

    @Test
    public void shouldGetDayOfWeek() {
        LocalDate theDay = new LocalDate(2014, 1, 1);

        int dayOfWeek = theDay.getDayOfWeek();
        assertEquals(DateTimeConstants.WEDNESDAY, dayOfWeek);
        assertEquals(3, dayOfWeek);
    }

    @Test
    public void shouldThrowExceptionWhenWrongTimeZoneId() {
        assertThrows(IllegalArgumentException.class, () -> {
            DateTimeZone.forID("Seoul/Asia");
        });
    }
}
