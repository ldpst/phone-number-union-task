package com.ldpst;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Класс, содержащий методы для вывода групп
 */
public class GroupPrinter {
    /**
     * Метод, выводящий в поток системного вывода значения строк, разделенные по группам
     *
     * @param groups индексы строк, разделенные по группам
     * @param data   исходный набор строк
     */
    public static void printGroups(Map<Integer, List<Integer>> groups, List<String[]> data) {
        List<List<Integer>> groupList = groups.values().stream()
                .filter(group -> group.size() > 1)
                .sorted((a, b) -> Integer.compare(b.size(), a.size()))
                .toList();

        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("D:\\Files\\Projects\\java\\lng-task\\input.txt"))));
            bw.write("Number of groups whose size is greater than 1: " + groupList.size() + "\n");
            int index = 1;
            for (List<Integer> group : groupList) {
                bw.write("Group №" + (index++) + ". Size:" + group.size() + "\n");
                for (int i : group) {
                    bw.write("\t" + String.join(";", data.get(i)) + "\n");
                }
            }
        } catch (IOException e) {
            System.out.println("Couldn't write to output file");
            System.exit(1);
        }
    }
}
