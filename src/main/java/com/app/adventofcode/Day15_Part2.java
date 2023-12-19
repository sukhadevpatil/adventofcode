package com.app.adventofcode;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Day15_Part2 {

    public static void main(String[] args) throws URISyntaxException, IOException {
        part2("day15_part1.txt");
    }

    private static void part2(String fileName) throws URISyntaxException, IOException {
        List<String> lines = Utils.getFileLines(fileName);

        Map<Integer, LinkedHashMap<String, Integer>> boxLenses = new HashMap<>();
        lines.forEach(row -> {
            String [] steps = row.split(",");

            for (String step : steps) {
                String [] labelLens = step.split("[=-]");
                String label = labelLens[0];
                char[] chars = label.toCharArray();
                int currentVal = 0;
                for(char c : chars) {
                    currentVal = calculateValue(String.valueOf(c), currentVal);
                }

                if(labelLens.length == 2) {
                    boxLenses.computeIfAbsent(currentVal, v -> new LinkedHashMap<>()).put(label, Integer.valueOf(labelLens[1]));
                } else if(boxLenses.containsKey(currentVal)) {
                    boxLenses.get(currentVal).remove(label);
                }
            }
        });

        Map<String, Integer> result = new HashMap<>();
        boxLenses.forEach((boxNo, lensFocal) -> {
            if(!lensFocal.isEmpty()) {
                List<String> labels = List.copyOf(lensFocal.keySet());

                for(String label : labels) {
                    int val = (boxNo + 1) * (labels.indexOf(label) + 1) * lensFocal.get(label) ;
                    result.put(label, val);
                }
            }
        });

        System.out.println(result.values().stream().mapToInt(Integer::valueOf).sum());
    }

    private static int calculateValue(String input, long prevVal) {
        int currentValue = input.hashCode();
        currentValue += prevVal;
        currentValue *= 17;
        currentValue %= 256;
        return currentValue;
    }
}
