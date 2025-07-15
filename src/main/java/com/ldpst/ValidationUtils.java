package com.ldpst;

/**
 * Класс, содержащий методы для валидации строковых значений
 */
public class ValidationUtils {
    /**
     * Проверяет, соответствует ли строка формату ^"[+-]?\d*"$
     *
     * @param str строка для проверки
     * @return результат проверки
     */
    public static boolean validateStr(String str) {
        if (str.length() >= 2 && str.charAt(0) == '"' && str.charAt(str.length() - 1) == '"') {
            String inner = str.substring(1, str.length() - 1);
            if (inner.isEmpty()) return true;
            for (int i = 0; i < inner.length(); i++) {
                if (inner.charAt(i) == '"') return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Проверяет, соответствуют ли строки массива формату ^"[+-]?\d*"$
     *
     * @param arr массив строк для проверки
     * @return результат проверки
     */
    public static boolean validateArrayOfStr(String[] arr) {
        if (arr.length == 0) return false;
        for (String part : arr) {
            if (!validateStr(part)) {
                return false;
            }
        }
        return true;
    }
}
