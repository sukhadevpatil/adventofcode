package com.app.adventofcode;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Dat14_Part1 {

    public static void main(String[] args) throws URISyntaxException, IOException {
        part1("day14_part1.txt");
    }

    private static void part1(String fileName) throws URISyntaxException, IOException {
        List<String> lines = Utils.getFileLines(fileName);
        String[][] arr = new String[lines.size()][lines.getFirst().toCharArray().length];
        AtomicInteger rowCounter = new AtomicInteger(0);
        lines.forEach(row -> {
            char[] characters = row.toCharArray();
            String[] rowObjects = new String[characters.length];

            for (int i = 0; i < characters.length; i++) {
                rowObjects[i] = String.valueOf(characters[i]);
            }

            arr[rowCounter.getAndIncrement()] = rowObjects;
        });

        for(int rows = 0; rows < arr.length-1; rows++) {
            // Loop through all rows
            for (int i = 0; i < arr.length-1; i++) {
                String [] rowArr = arr[i];
                // Loop through all elements of current row
                for (int j = 0; j < rowArr.length; j++) {

                    String currentPlace = arr[i][j];
                    String nextPlace = arr[i+1][j];

                    if(currentPlace.equals(".") && nextPlace.equals("O")) {
                        arr[i][j] = nextPlace;
                        arr[i+1][j] = ".";
                    }
                }
            }
        }

        Arrays.asList(arr).forEach(val -> {
            Arrays.stream(val).forEach(System.out::print);
            System.out.println();
        });

        AtomicLong sum = new AtomicLong(0);
        AtomicInteger rowNo = new AtomicInteger(arr.length);
        Arrays.asList(arr).forEach(val -> {
            AtomicInteger stoneCount = new AtomicInteger(0);
            Arrays.stream(val).forEach(v -> {
                if(v.equals("O")) {
                    stoneCount.getAndIncrement();
                }
            });

            long rowDec = rowNo.getAndDecrement();
            if(stoneCount.get() > 0) {
                sum.set(sum.get() + ((long) stoneCount.get() * rowDec));
            }
        });

        System.out.println(sum.get());
    }
}
