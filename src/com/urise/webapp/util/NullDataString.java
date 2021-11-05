package com.urise.webapp.util;

import java.time.LocalDate;

import static com.urise.webapp.util.DateUtil.NOW;

public class NullDataString {
    public static String writeNull(String str) {
        if (str == null) {
            return "";
        } else {
            return str;
        }
    }

    public static String readNull(String str) {
        if (str.equals("")) {
            return null;
        } else {
            return str;
        }
    }
}
