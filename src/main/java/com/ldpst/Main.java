package com.ldpst;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
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
        List<String[]> validRows = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(";");
            if (ValidationUtils.validateArrayOfStr(parts)) {
                validRows.add(parts);
            }
        }

        List<List<Long>> data = new ArrayList<>();
        for (String[] row : validRows) {
            data.add(ConversionUtils.ArrayToLong(row));
        }

        DSU dsu = new DSU(data.size());

        List<List<List<Long>>> groups = getGroups(dsu, data);
        long size = groups.stream().filter(group -> group.size() > 1).count();
        System.out.println("Number of groups whose size is greater than 1: " + size);
        for (int i = 0; i < groups.size(); i++) {
            List<List<Long>> group = groups.get(i);
            if (group.size() > 1) {
                System.out.println("Group №" + (i + 1) + ". Size:" + group.size());
                for (List<Long> line : group) {
                    System.out.print("\t" + "\"" + line.get(0) + "\"");
                    for (int j = 1; j < line.size(); j++) {
                        System.out.print(";" + (line.get(j) == null ? "\"\"" : "\"" + line.get(j) + "\""));
                    }
                    System.out.println();
                }
            }
        }
    }

    private static List<List<List<Long>>> getGroups(DSU dsu, List<List<Long>> data) {
        makeUnions(dsu, data);

        Map<Integer, List<List<Long>>> result = new HashMap<>();
        for (int i = 0; i < data.size(); i++) {
            int root = dsu.find(i);
            result.computeIfAbsent(root, k -> new ArrayList<>()).add(data.get(i));
        }

        List<List<List<Long>>> groups = new ArrayList<>(result.values());
        groups.sort((g1, g2) -> Integer.compare(g2.size(), g1.size()));

        return groups;
    }

    private static void makeUnions(DSU dsu, List<List<Long>> data) {
        Map<KeyPair, List<Integer>> unions = new HashMap<>(); // Размер массива = 1000000. Можно обойтись без Long

        for (int row = 0; row < data.size(); row++) {
            List<Long> parts = data.get(row);
            for (int col = 0; col < parts.size(); col++) {
                Long value = parts.get(col);
                if (value != null) {
                    KeyPair key = new KeyPair(col, value);
                    if (unions.containsKey(key)) {
                        for (Integer unionElement : unions.get(key)) {
                            dsu.union(row, unionElement);
                        }
                        unions.get(key).add(row);
                    } else {
                        List<Integer> list = new ArrayList<>();
                        list.add(row);
                        unions.put(key, list);
                    }
                }
            }
        }
    }
}
