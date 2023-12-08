package com.app.adventofcode;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Utils {

    public static List<String> getFileLines(String fileName) throws URISyntaxException, IOException {
        URL url = ClassLoader.getSystemResource(fileName);
        List<String> lines = Files.readAllLines(Path.of(url.toURI()));
        return Optional.of(lines).orElse(Collections.emptyList());
    }
}
