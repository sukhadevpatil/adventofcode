package com.app.adventofcode;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static com.app.adventofcode.Utils.calculateSurface;
import static java.lang.Integer.parseInt;

public class Advent18 {

    public static void main(String[] args) throws URISyntaxException, IOException {
        System.out.println(new Advent18().runP2("Day18_input.txt"));
    }

    public Long runP1(String file) throws URISyntaxException, IOException {
        return extractPointsAndCalculateArea(file, Advent18::p1Input);
    }

    public Long runP2(String file) throws URISyntaxException, IOException {
        return extractPointsAndCalculateArea(file, Advent18::p2Input);
    }

    private long extractPointsAndCalculateArea(String file, Function<String[], Result> coordinateExtractor) throws URISyntaxException, IOException {
        List<Utils.Point> points = new ArrayList<>(Collections.singleton(new Utils.Point(0, 0)));
        List<String> lines = Utils.getFileLines(file);
        lines.forEach(line -> {
            Result result = coordinateExtractor.apply(line.split(" "));
            Utils.Point nextPoint = getNextPoint(points.getLast(), result.direction(), result.digSize);
            points.add(nextPoint);
        });

        return calculateSurface(points).area();
    }

    private static Result p1Input(String[] split) {
        Direction direction = Direction.valueOf(split[0]);
        int digSize = parseInt(split[1]);
        return new Result(direction, digSize);
    }

    private static Result p2Input(String[] split) {
        String hex = split[2];
        int lastDigit = parseInt(hex.substring(hex.length() - 2, hex.length() - 1));

        Direction direction = Direction.values()[lastDigit];
        int digSize = parseInt(hex.substring(2, hex.length() - 2), 16);

        return new Result(direction, digSize);
    }

    private static Utils.Point getNextPoint(Utils.Point last, Direction direction, int digSize) {
        switch (direction) {
            case U -> {
                return new Utils.Point(last.row() - digSize, last.col());
            }
            case D -> {
                return new Utils.Point(last.row() + digSize, last.col());
            }
            case L -> {
                return new Utils.Point(last.row(), last.col() - digSize);
            }
            case R -> {
                return new Utils.Point(last.row(), last.col() + digSize);
            }
        }
        return null;
    }

    private record Result(Direction direction, int digSize) {
    }

    enum Direction {
        R, D, L, U
    }
}
