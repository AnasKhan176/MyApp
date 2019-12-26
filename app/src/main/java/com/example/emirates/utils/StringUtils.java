package com.example.emirates.utils;

import java.util.List;

public class StringUtils {
    public static boolean isNullOrBlank(String s) {
        return (s == null || s.trim().equals(""));
    }
    public static boolean isNullOrEmptyList(List arrayList) {
        return (arrayList == null || arrayList.isEmpty() || arrayList.size() == 0);
    }

}
