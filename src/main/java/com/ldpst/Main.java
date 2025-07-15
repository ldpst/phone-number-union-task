package com.ldpst;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

        Stream<String> lineStream = getInputStreamFromArgs(args);

        List<String[]> validRows = validateRows(lineStream);

        DSU dsu = new DSU(validRows.size());

        List<List<String>> groups = getGroups(dsu, validRows);

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
            System.exit(0);
        }

        String filepath = args[0].trim();
        Stream<String> lineStream = null;
        try {
            lineStream = DataStream.streamFrom(filepath);
        } catch (IOException e) {
            System.out.println("File not found");
            System.exit(0);
        }
        return lineStream;
    }

    /**
     * Считывает и валидирует массив строк, разделяя каждую строку по ';'. Если любая часть строки не валидна - строка пропускается
     *
     * @param lineStream поток ввода
     * @return валидные данные
     */
    private static List<String[]> validateRows(Stream<String> lineStream) {
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

    /**
     * Метод, разделяющий набор строк на группы
     *
     * @param dsu  система непересекающихся множеств
     * @param data набор строк
     * @return разделение строк на группы
     */
    private static List<List<String>> getGroups(DSU dsu, List<String[]> data) {
        makeUnions(dsu, data);

        Map<Integer, List<String>> result = new HashMap<>();
        for (int i = 0; i < data.size(); i++) {
            int root = dsu.find(i);
            result.computeIfAbsent(root, k -> new ArrayList<>()).add(String.join(";", data.get(i)));
        }

        List<List<String>> groups = new ArrayList<>(result.values());
        groups.sort((g1, g2) -> Integer.compare(g2.size(), g1.size()));

        return groups;
    }

    /**
     * Метод, объединяющий строки в группы, заполняя дерево в dsu
     *
     * @param dsu система непересекающихся множеств, которая будет заполнена
     * @param data набор строк для объединения
     */
    private static void makeUnions(DSU dsu, List<String[]> data) {
        Object2IntOpenHashMap<KeyPair> representativeMap = new Object2IntOpenHashMap<>();
        representativeMap.defaultReturnValue(-1);

        for (int row = 0; row < data.size(); row++) {
            String[] parts = data.get(row);
            for (int col = 0; col < parts.length; col++) {
                String value = parts[col];
                if (!value.equals("\"\"")) {
                    KeyPair key = new KeyPair(col, value.intern());
                    int rep = representativeMap.getInt(key);
                    if (rep == -1) {
                        representativeMap.put(key, row);
                    } else {
                        dsu.union(row, rep);
                    }
                }
            }
        }
    }
}
