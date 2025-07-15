package com.ldpst;

import java.io.IOException;
import java.util.List;
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
        List<List<String>> groups = grouper.getGroups(validRows);
        printGroups(groups);

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

    /**
     * Метод, выводящий в поток системного вывода значения строк, разделенные по группам
     *
     * @param groups строки, разделенные по группам
     */
    private static void printGroups(List<List<String>> groups) {
        long size = groups.stream().filter(group -> group.size() > 1).count();
        System.out.println("Number of groups whose size is greater than 1: " + size);
        for (int i = 0; i < groups.size(); i++) {
            List<String> group = groups.get(i);
            if (group.size() > 1) {
                System.out.println("Group №" + (i + 1) + ". Size:" + group.size());
                System.out.println("\t" + String.join("\n\t", group));
            }
        }
    }


}
