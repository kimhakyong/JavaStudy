package net.jackbauer.time;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;

// Duration : 두 시간 사이의 간격을 초나 나노 초 단위로 표시
// Period : 두 날짜 사이의 간격을 년/월/일 단위로 표시
// ChronoUnit : 특정 시간 단위의 길이
public class DurationPeriod {
    public static void main(String[] args) {
        duration();
        period();
        chronoUnit();
    }

    private static void chronoUnit() {
        System.out.println("##### ChronoUnit");
        LocalDate startDate = LocalDate.of(1939, 9, 1);
        LocalDate endDate = LocalDate.of(1945, 9, 2);

        long months = ChronoUnit.MONTHS.between(startDate, endDate);
        long weeks = ChronoUnit.WEEKS.between(startDate, endDate);
        long days = ChronoUnit.DAYS.between(startDate, endDate);

        System.out.println("Months : " + months);
        System.out.println("Weeks : " + weeks);
        System.out.println("Days : " + days);

        LocalTime startTime = LocalTime.of(10, 35, 40);
        LocalTime endTime = LocalTime.of(10, 36, 50, 800);

        long hours = ChronoUnit.HOURS.between(startTime, endTime);
        long minutes = ChronoUnit.MINUTES.between(startTime, endTime);
        long seconds = ChronoUnit.SECONDS.between(startTime, endTime);

        System.out.println("Hours: " + hours);
        System.out.println("Minutes: " + minutes);
        System.out.println("Seconds: " + seconds);
    }

    public static void period() {
        System.out.println("##### Period");
        LocalDate startDate = LocalDate.of(1939, 9, 1);
        LocalDate endDate = LocalDate.of(1945, 9, 2);

        Period period = Period.between(startDate, endDate);

        System.out.println(period.getYears());
        System.out.println(period.getMonths());
        System.out.println(period.getDays());

        Period period2 = Period.of(6, 0, 1);

        System.out.println(period2.getYears());
        System.out.println(period2.getMonths());
        System.out.println(period2.getDays());

        Period period3 = Period.parse("P6Y2D");

        System.out.println(period3);
        System.out.println(period2.getYears());
        System.out.println(period2.getMonths());
        System.out.println(period2.getDays());
    }

    public static void duration() {
        System.out.println("##### Duration");
        LocalTime start = LocalTime.of(10, 35, 40);
        LocalTime end = LocalTime.of(10, 36, 50, 800);

        Duration duration = Duration.between(start, end);

        System.out.println("Seconds : " + duration.getSeconds());
        System.out.println("Nano Seconds : " + duration.getNano());

        Duration ofMinutes = Duration.ofMinutes(1);
        System.out.println(ofMinutes.getSeconds());

        Duration ofHours = Duration.ofHours(1);
        System.out.println(ofHours.getSeconds());

        Duration ofDays = Duration.ofDays(1);
        System.out.println(ofDays.getSeconds());

        Duration duration2 = Duration.parse("PT10H36M50.008S");
        System.out.println(duration2);
        System.out.printf("Seconds: %d, Nano Seconds: %d\n", duration2.getSeconds(), duration2.getNano());
    }
}
