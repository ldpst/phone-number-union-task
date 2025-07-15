package com.ldpst;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Основной класс запуска
 */
public class Main {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        List<String> lines = readLinesFromArgs(args);

        List<String[]> validRows = validateRows(lines);

        DSU dsu = new DSU(validRows.size());

        List<List<String>> groups = getGroups(dsu, validRows);

        printGroups(groups);

        String runTime = System.currentTimeMillis() - startTime + "ms";
        System.out.println("Program ended in " + runTime);
    }

    /**
     * Проверяет, есть ли в аргументах запуска путь к файлу и считывает из него значение
     *
     * @param args аргументы запуска
     * @return считанный массив строк
     */
    private static List<String> readLinesFromArgs(String[] args) {
        if (args.length < 1) {
            System.out.println("Add absolute path to input file");
            System.exit(0);
        }

        String filepath = args[0].trim();
        List<String> lines = new ArrayList<>();
        try {
            lines = DataLoader.loadFrom(filepath);
        } catch (IOException e) {
            System.out.println("File not found");
            System.exit(0);
        }
        return lines;
    }

    /**
     * Валидирует массив строк, разделяя каждую строку по ';'. Если любая часть строки не валидна - строка пропускается
     *
     * @param rows массив строк для валидации
     * @return валидные данные
     */
    private static List<String[]> validateRows(List<String> rows) {
        List<String[]> validRows = new ArrayList<>();   // чтобы в будущем лишний раз не использовать split
        // принимает String[]
        for (String line : rows) {
            String[] parts = line.split(";");
            if (ValidationUtils.validateArrayOfStr(parts)) {
                validRows.add(parts);
            }
        }
        return validRows;
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
        Object2ObjectOpenHashMap<KeyPair, IntList> unions = new Object2ObjectOpenHashMap<>();

        for (int row = 0; row < data.size(); row++) {
            String[] parts = data.get(row);
            for (int col = 0; col < parts.length; col++) {
                String value = parts[col];
                if (!value.equals("\"\"")) {
                    KeyPair key = new KeyPair(col, value);
                    if (unions.containsKey(key)) {
                        for (Integer unionElement : unions.get(key)) {
                            dsu.union(row, unionElement);
                        }
                        unions.get(key).add(row);
                    } else {
                        IntList list = new IntArrayList();
                        list.add(row);
                        unions.put(key, list);
                    }
                }
            }
        }
        unions.clear();
    }
}
