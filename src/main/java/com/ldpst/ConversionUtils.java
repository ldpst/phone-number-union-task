package com.ldpst;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для преобразования типов по формату
 */
public class ConversionUtils {
    /**
     * Преобразует строку в кавычках в число
     *
     * @param str строка
     * @return преобразованное число
     */
    public static Long strToLong(String str) {
        if (str.length() >= 2 && str.charAt(0) == '"' && str.charAt(str.length() - 1) == '"') {
            String inner = str.substring(1, str.length() - 1);
            try {
                return Long.parseLong(inner);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    public static List<Long> ArrayToLong(String[] str) {
        List<Long> list = new ArrayList<Long>();
        for (String s : str) {
            list.add(strToLong(s));
        }
        return list;
    }
}
