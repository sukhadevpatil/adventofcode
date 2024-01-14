package com.app.adventofcode;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparingInt;

public class Advent14 {
    public static final int CYCLES = 1000000000;

    List<Point> roundRockList = new ArrayList<>();
    List<Point> squareRockList = new ArrayList<>();

    Map<List<Point>, Long> cycleCache = new HashMap<>();
    Long period;

    char[][] map; // TODO - convert to using this array at some point

    public Long runP1(String file) throws URISyntaxException, IOException {
        List<String> list = getRocks(file);

        slideNorth();

        return roundRockList.stream()
                .mapToLong(value -> list.size() - value.row)
                .sum();
    }

    public static void main(String[] args) throws URISyntaxException, IOException {
        System.out.println(new Advent14().runP2("day14_part1.txt"));
    }

    public Long runP2(String file) throws URISyntaxException, IOException {
        List<String> list = getRocks(file);

        long cycleCount = 0;
        while (true) {
            cycle(list.size());
            cycleCount++;
            System.out.println("Cycle {} complete" + cycleCount);

            List<Point> points = new java.util.ArrayList<>(List.copyOf(roundRockList));
            points.sort(comparingInt(o -> o.row * 100 + o.col));

            if (cycleCache.containsKey(points)) {
                period = cycleCount - cycleCache.get(points);
                System.out.println("Period: {}" + period);
                break;
            }

            cycleCache.put(points, cycleCount);
        }

        long start = cycleCache.size() - period;
        long remainder = (CYCLES - start) % period;

        List<Point> key = cycleCache.entrySet().stream().filter(entry -> entry.getValue() == start + remainder).findFirst().orElseThrow().getKey();

        return key.stream()
                .mapToLong(value -> list.size() - value.row)
                .sum();
    }

    private void cycle(int listSize) {
        slideNorth();
        slideWest();
        slideSouth(listSize);
        slideEast(listSize);
    }

    private void slideNorth() {
        roundRockList.sort(comparingInt(o -> o.row));

        for (int rock = 0; rock < roundRockList.size(); rock++) {
            while (true) {
                Point current = roundRockList.get(rock);
                if (current.row == 0) {
                    break;
                }

                Point newPos = new Point(current.row - 1, current.col);

                if (!roundRockList.contains(newPos) && !squareRockList.contains(newPos)) {
                    roundRockList.remove(current);
                    roundRockList.add(rock, newPos);
                } else {
                    break;
                }
            }
        }
    }

    private void slideSouth(int listSize) {
        roundRockList.sort((o1, o2) -> Integer.compare(o2.row, o1.row));

        for (int rock = 0; rock < roundRockList.size(); rock++) {
            while (true) {
                Point current = roundRockList.get(rock);
                if (current.row == listSize - 1) {
                    break;
                }

                Point newPos = new Point(current.row + 1, current.col);

                if (!roundRockList.contains(newPos) && !squareRockList.contains(newPos)) {
                    roundRockList.remove(current);
                    roundRockList.add(rock, newPos);
                } else {
                    break;
                }
            }
        }
    }

    private void slideWest() {
        roundRockList.sort(comparingInt(o -> o.col));

        for (int rock = 0; rock < roundRockList.size(); rock++) {
            while (true) {
                Point current = roundRockList.get(rock);
                if (current.col == 0) {
                    break;
                }

                Point newPos = new Point(current.row, current.col - 1);

                if (!roundRockList.contains(newPos) && !squareRockList.contains(newPos)) {
                    roundRockList.remove(current);
                    roundRockList.add(rock, newPos);
                } else {
                    break;
                }
            }
        }
    }

    private void slideEast(int listSize) {
        roundRockList.sort((o1, o2) -> Integer.compare(o2.col, o1.col));

        for (int rock = 0; rock < roundRockList.size(); rock++) {
            while (true) {
                Point current = roundRockList.get(rock);
                if (current.col == listSize - 1) {
                    break;
                }

                Point newPos = new Point(current.row, current.col + 1);

                if (!roundRockList.contains(newPos) && !squareRockList.contains(newPos)) {
                    roundRockList.remove(current);
                    roundRockList.add(rock, newPos);
                } else {
                    break;
                }
            }
        }
    }

    private List<String> getRocks(String file) throws URISyntaxException, IOException {
        List<String> list = Utils.getFileLines(file);
        map = new char[list.size()][list.getFirst().length()];

        for (int row = 0; row < list.size(); row++) {
            for (int col = 0; col < list.getFirst().length(); col++) {
                map[row][col] = list.get(row).charAt(col);
                if (list.get(row).charAt(col) == 'O') {
                    roundRockList.add(new Point(row, col));
                } else if (list.get(row).charAt(col) == '#') {
                    squareRockList.add(new Point(row, col));
                }
            }
        }
        return list;
    }

    record Point(int row, int col) {
    }
}
