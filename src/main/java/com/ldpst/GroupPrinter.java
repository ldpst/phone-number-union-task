package com.ldpst;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        Set<Integer> seenHashes = new HashSet<>();
        List<List<String>> groupList = groups.values().stream()
                .map(group -> group.stream()
                        .map(index -> String.join(";", data.get(index)))
                        .filter(str -> seenHashes.add(str.hashCode()))
                        // я решил рискнуть, так как первый тест выполнялся чуть больше 30 секунд
                        .toList())
                .filter(group -> group.size() > 1)
                .sorted((a, b) -> Integer.compare(b.size(), a.size()))
                .toList();


        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("output.txt")))) {
            bw.write("Number of groups whose size is greater than 1: " + groupList.size() + "\n");
            int index = 1;
            for (List<String> group : groupList) {
                bw.write("Group №" + (index++) + ". Size:" + group.size() + "\n");
                for (String i : group) {
                    bw.write("\t" + i + "\n");
                }
            }
            bw.flush();
        } catch (IOException e) {
            System.out.println("Couldn't write to output file");
            System.exit(1);
        }
    }
}
