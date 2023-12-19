package com.app.adventofcode;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Day15_Part1 {

    public static void main(String[] args) throws URISyntaxException, IOException {

        part1("day15_part1.txt");
    }

    private static void part1(String fileName) throws URISyntaxException, IOException {
        List<String> lines = Utils.getFileLines(fileName);
        AtomicLong total = new AtomicLong(0);
        lines.forEach(row -> {
            String [] step = row.split(",");

            for (String s : step) {
                char[] chars = s.toCharArray();
                long currentVal = 0;
                for(char c : chars) {
                    currentVal = calculateValue(String.valueOf(c), currentVal);
                }

                total.set(total.get() + currentVal);
            }
        });

        System.out.println(total);
    }

    private static long calculateValue(String input, long prevVal) {
        long currentValue = input.hashCode();
        currentValue += prevVal;
        currentValue *= 17;
        currentValue %= 256;
        return currentValue;
    }
}
