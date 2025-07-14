package com.ldpst;

/**
 * Класс, реализующий систему непересекающихся множеств (DSU)
 */
public class DSU {
    private final int[] parent;
    private final int[] rank;

    public DSU(int n) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;
    }

    /**
     * Метод для поиска родителя (корня) множества, в котором состоит x
     *
     * @param x значение, для которого осуществляется поиск
     * @return номер родителя (корня)
     */
    public Integer find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    /**
     * Метод, объединяющий две вершины (и всё множество, в котором она состоит) в одно множество
     *
     * @param a первая вершина для объединения
     * @param b вторая вершина для объединения
     */
    public void union(int a, int b) {
        int ra = find(a);
        int rb = find(b);
        if (ra == rb) return;
        if (rank[ra] < rank[rb]) {
            parent[ra] = rb;
        } else if (rank[ra] > rank[rb]) {
            parent[rb] = ra;
        } else {
            parent[rb] = ra;
            rank[ra]++;
        }
    }
}
