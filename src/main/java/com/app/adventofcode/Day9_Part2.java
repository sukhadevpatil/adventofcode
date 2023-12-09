package com.app.adventofcode;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Day9_Part2 {

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
            AtomicLong firstElement = new AtomicLong(0);

            AtomicInteger counter = new AtomicInteger(1);
            for(int c = seriesList.size()-1; c > 0 ; c--) {
                List<Long> subItr = seriesList.get(c);

                if(counter.getAndIncrement() == 1) {
                    long val = seriesList.get(c-1).get(0) - subItr.get(0);
                    firstElement.set(val);
                } else {
                    firstElement.set(seriesList.get(c-1).get(0) - firstElement.get());
                }
            }

            seriesLastElements.put(key, firstElement.get());
        });

        System.out.println(seriesLastElements);
        System.out.println(finalData);


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
