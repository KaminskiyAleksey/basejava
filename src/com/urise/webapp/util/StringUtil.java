package com.urise.webapp.util;

public class StringUtil {
    public static String convertToEmptyIfNull(String str) {
        return str == null ? "" : str;
    }

    public static String convertToNullIfEmpty(String str) {
        return str.equals("") ? null : str;
    }
}
