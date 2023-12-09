package com.app.adventofcode;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Day9_Part1 {

    public static void main(String[] args) throws URISyntaxException, IOException {
        List<String> lines = Utils.getFileLines("day9_input.txt");

        Map<Integer, List<List<Long>>> finalData = new HashMap<>();
        Map<Integer, Long> seriesLastElements = new HashMap<>();
        AtomicInteger rowCounter = new AtomicInteger(1);
        lines.forEach(line -> {

            Long[] data = Arrays.stream(line.trim().split(" ")).map(String::trim).map(Long::valueOf).toArray(Long[]::new);

            List<Long> nextLevel = Arrays.stream(data).toList();
            finalData.computeIfAbsent(rowCounter.get(), v -> new ArrayList<>()).add(Arrays.stream(data).toList());

            do {
                nextLevel = getNextLevelDataDiff(nextLevel);
                finalData.computeIfAbsent(rowCounter.get(), v -> new ArrayList<>()).add(nextLevel);
            } while (new HashSet<>(nextLevel).size() != 1);

            rowCounter.getAndIncrement(); //move to next row counter increment
        });

        finalData.forEach((key, seriesList) -> {
            AtomicLong lastElement = new AtomicLong(0);
            for(List<Long> subList : seriesList) {
                lastElement.set(lastElement.get() + subList.get(subList.size()-1));
            }

            seriesLastElements.put(key, lastElement.get());
        });


        long result = seriesLastElements.values().stream().mapToLong(v -> v).sum();
        System.out.println("Result :: " + result);
    }

    private static List<Long> getNextLevelDataDiff(List<Long> data) {
        List<Long> nextLevel = new ArrayList<>();

        for (int i = 1; i < data.size(); i++) {
            nextLevel.add(data.get(i) - data.get(i - 1));
        }
        return nextLevel;
    }
}
