package com.ldpst;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Основной класс запуска
 */
public class Main {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        List<String[]> validRows = loadAndValidateRows(args);

        DSU dsu = new DSU(validRows.size());
        Grouper grouper = new Grouper(dsu);
        Map<Integer, List<Integer>> groups = grouper.getGroups(validRows);

        System.out.println("Groups were found in " + (System.currentTimeMillis() - startTime) + "ms");
        GroupPrinter.printGroups(groups, validRows);

        String runTime = System.currentTimeMillis() - startTime + "ms";
        System.out.println("Program ended in " + runTime);
    }

    /**
     * Проверяет, есть ли в аргументах запуска путь к файлу и получает его поток ввода
     *
     * @param args аргументы запуска
     * @return поток ввода
     */
    private static Stream<String> getInputStreamFromArgs(String[] args) {
        if (args.length < 1) {
            System.out.println("Add absolute path to input file");
            System.exit(1);
        }
        try {
            return DataStream.streamFrom(args[0].trim());
        } catch (IOException e) {
            System.out.println("Cant open file: " + e.getMessage());
            System.exit(1);
        }
        return Stream.empty();
    }

    /**
     * Считывает и валидирует массив строк из файла, разделяя каждую строку по ';'. Если любая часть строки не валидна - строка пропускается
     *
     * @param args аргументы запуска программы
     * @return валидные данные
     */
    private static List<String[]> loadAndValidateRows(String[] args) {
        Stream<String> lineStream = getInputStreamFromArgs(args);
        return lineStream
                .map(line -> line.split(";"))
                .filter(ValidationUtils::validateArrayOfStr)
                .collect(Collectors.toList());
    }
}
