package com.ldpst;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DataLoader {
    /**
     * Считывает строки из файла по пути
     *
     * @param filepath путь
     * @return считанные строки
     * @throws IOException отсутствие файла по заданному пути
     */
    public static List<String> loadFrom(String filepath) throws IOException {
        Path path = Paths.get(filepath);
        return Files.readAllLines(path);
    }
}
