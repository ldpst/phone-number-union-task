package com.ldpst;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Класс, содержащий методы получения потока ввода из файла
 */
public class DataStream {
    /**
     * Получает поток ввода из файла
     *
     * @param filepath путь
     * @return поток ввода
     * @throws IOException отсутствие файла по заданному пути
     */
    public static Stream<String> streamFrom(String filepath) throws IOException {
        Path path = Paths.get(filepath);
        return Files.lines(path);
    }
}
