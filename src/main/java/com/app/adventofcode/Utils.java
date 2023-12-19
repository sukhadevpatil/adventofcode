package com.app.adventofcode;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.lang.Math.abs;

public class Utils {

    public static List<String> getFileLines(String fileName) throws URISyntaxException, IOException {
        URL url = ClassLoader.getSystemResource(fileName);
        List<String> lines = Files.readAllLines(Path.of(url.toURI()));
        return Optional.of(lines).orElse(Collections.emptyList());
    }

    public record Point(long row, long col) {

        long distanceTo(Point other) {
            return abs(row - other.row) + abs(col - other.col);
        }
    }

    public static Surface calculateSurface(List<? extends Point> points) {
        long perimeter = 0L;
        long shoelaceArea = 0L;

        // https://en.m.wikipedia.org/wiki/Shoelace_formula
        for (int i = 0; i < points.size() - 1; i++) {
            Point current = points.get(i);
            Point next = points.get(i + 1);

            perimeter += current.distanceTo(next);
            shoelaceArea += ((current.row() * next.col()) - (next.row() * current.col()));
        }

        Point last = points.getLast();
        Point first = points.getFirst();

        perimeter += last.distanceTo(first);
        shoelaceArea += ((last.row() * first.col()) - (first.row() * last.col()));
        shoelaceArea = abs(shoelaceArea) / 2L;

        // https://en.m.wikipedia.org/wiki/Pick%27s_theorem
        long insidePoints = shoelaceArea + 1 - perimeter / 2;
        long area = insidePoints + perimeter;

        return new Surface(perimeter, area, insidePoints, shoelaceArea, new ArrayList<>(points));
    }

    public record Surface(long perimeter, long area, long insidePoints, long shoelaceArea, List<Point> points) {
    }
}
