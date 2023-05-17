package ru.yandex.yandexlavka.util;

import java.time.LocalDate;
import java.time.LocalTime;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.HOURS;

public class DateTimeUtil {

    public static int hoursBetween(LocalTime startTime, LocalTime endTime) {
        return (int) HOURS.between(startTime.atDate(LocalDate.ofYearDay(1,1)),
                endTime.atDate(LocalDate.ofYearDay(1,1)).plusMinutes(1));
    }
    public static int daysBetween(LocalDate startDate, LocalDate endDate) {
        return (int) DAYS.between(startDate, endDate);
    }
}
