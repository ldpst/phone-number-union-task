package com.ldpst;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DataLoader {
    private final String filepath;
    public DataLoader(String filepath) {
        this.filepath = filepath;
    }

    public void load() throws IOException {
        Path path = Paths.get(filepath);
        List<String> lines = Files.readAllLines(path);
        for (String line : lines) {
            String[] parts = line.split(";");

        }
    }
}
