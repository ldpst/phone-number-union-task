package com.ldpst;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Класс для заполнения системы непересекающихся множеств (dsu)
 */
public class Grouper {
    private final DSU dsu;

    public Grouper(DSU dsu) {
        this.dsu = dsu;
    }

    /**
     * Метод, объединяющий строки в группы, заполняя дерево в dsu
     *
     * @param data набор строк для объединения
     * @return индексы строк, разделенные по группам
     */
    public Map<Integer, List<Integer>> getGroups(List<String[]> data) {
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
        return buildGroups(data);
    }

    /**
     * Метод, разделяющий набор строк на группы
     *
     * @param data набор строк
     * @return индексы строк, разделенные по группам
     */
    private Map<Integer, List<Integer>> buildGroups(List<String[]> data) {
        Map<Integer, List<Integer>> grouped = new HashMap<>();
        for (int i = 0; i < data.size(); i++) {
            int root = dsu.find(i);
            grouped.computeIfAbsent(root, k -> new ArrayList<>()).add(i);
        }
        return grouped;
    }
}
