package com.ldpst;

public class ConversionUtils {
    public static boolean convertString(String str) {
        if (str.length() >= 2 && str.charAt(0) == '"' && str.charAt(str.length() - 1) == '"') {
            for (int i = 1; i < str.length() - 1; i++) {
                if (!Character.isDigit(str.charAt(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
