package ru.javawebinar.basejava.utils;

import java.time.LocalDate;
import java.time.Month;

public class DateUtil {
    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static LocalDate now() {
        return LocalDate.now();
    }
}
