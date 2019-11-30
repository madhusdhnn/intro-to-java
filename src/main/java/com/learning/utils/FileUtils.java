package com.learning.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Function;
import java.util.stream.Stream;

public class FileUtils {

    private FileUtils() {
    }

    public static <T> T readFile(Path filePath, Function<Stream<String>, T> handler) {
        try (Stream<String> lines = Files.lines(filePath)) {
            return handler.apply(lines);
        } catch (IOException ex) {
            throw new RuntimeException(String.format("Error while reading file - %s", filePath.toString()));
        }
    }

}
