package com.app.adventofcode;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Day18_Part1 {

    private static String HASH = "#";
    public static void main(String[] args) throws URISyntaxException, IOException {
        part1("Day18_input.txt");
    }

    private static void part1(String fileName) throws URISyntaxException, IOException {
        List<String> lines = Utils.getFileLines(fileName);
        String[][] arr = new String[lines.size()][lines.size()];

        for(int i = 0; i < arr.length; i++) {
            for(int j = 0; j < arr.length; j++) {
                arr[i][j]=".";;
            }
        }

        List<Map<String, Integer>> blocks = new ArrayList<>();
        lines.forEach(rec -> {
            String [] values = rec.split(" ");
            Map<String, Integer> map = new HashMap<>();
            map.put(values[0], Integer.valueOf(values[1]));
            blocks.add(map);
        });

        System.out.println(blocks);

        processData(arr, blocks);

        fillUpGaps(arr);

        AtomicInteger count = new AtomicInteger(0);
        Arrays.asList(arr).forEach(val -> {
            Arrays.asList(val).forEach(v -> {
                if(v.equals(HASH)) {
                    count.getAndIncrement();
                }
                System.out.print(v);
            });
            System.out.println();
        });

        System.out.println("Total trench :: " + count.get());

    }

    private static void fillUpGaps(String[][] arr) {
        Map<Integer, List<Integer>> rows = new HashMap<>();
        for(int i = 0; i < arr.length; i++) {
            for(int j = 0; j < arr.length; j++) {
                String block = arr[i][j];
                if(block.equals(HASH)) {
                    rows.computeIfAbsent(i, v -> new ArrayList<>()).add(j);
                }
            }
        }

        rows.forEach((r, colArr) -> {
            for(int i = colArr.getFirst(); i <= colArr.getLast(); i++) {
                arr[r][i] = HASH;
            }
        });
    }

    private static void processData(String[][] arr, List<Map<String, Integer>> blocks) {
        int startPosRow = 0;
        int startPosCol = 0;
        if(blocks.size() > 50) {
            startPosRow = blocks.size()/2;
            startPosCol = blocks.size()/2;
        }

        arr[startPosRow][startPosCol] = HASH;
        AtomicInteger row = new AtomicInteger(startPosRow);
        AtomicInteger col = new AtomicInteger(startPosCol);
        for(Map<String, Integer> block : blocks) {
            block.forEach((direction, movement) -> {
                if(direction.equals("R")) {
                    for(int k = 1; k <= movement; k++) {
                        arr[row.get()][col.get() + k] = HASH;
                    }
                    row.set(row.get());
                    col.set(col.get() + movement);
                } else if(direction.equals("D")) {
                    for(int k = 1; k <= movement; k++) {
                        arr[row.get() + k][col.get()] = HASH;
                    }
                    row.set(row.get() + movement);
                    col.set(col.get());
                } else if(direction.equals("L")) {
                    for(int k = 1; k <= movement; k++) {
                        arr[row.get()][col.get()  - k] = HASH;
                    }
                    row.set(row.get());
                    col.set(col.get() - movement);
                } else if(direction.equals("U")) {
                    for(int k = 1; k <= movement; k++) {
                        arr[row.get() - k][col.get()] = HASH;
                    }
                    row.set(row.get() - movement);
                    col.set(col.get());
                }
            });
        }
    }
}
