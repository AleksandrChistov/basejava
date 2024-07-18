package ru.javawebinar.basejava.utils;

public class StringUtil {
    public static String getStrOrNull(String str) {
        return str == null || str.isEmpty() ? null : str;
    }
}
