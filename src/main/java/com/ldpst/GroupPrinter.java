package com.ldpst;

import java.util.List;

/**
 * Класс, содержащий методы для вывода групп
 */
public class GroupPrinter {
    /**
     * Метод, выводящий в поток системного вывода значения строк, разделенные по группам
     *
     * @param groups строки, разделенные по группам
     */
    public static void printGroups(List<List<String>> groups) {
        long size = groups.stream().filter(group -> group.size() > 1).count();
        System.out.println("Number of groups whose size is greater than 1: " + size);
        int index = 1;
        for (List<String> group : groups) {
            if (group.size() > 1) {
                System.out.println("Group №" + (index++) + ". Size:" + group.size());
                group.forEach(s -> System.out.println("\t" + s));
            }
        }
    }
}
