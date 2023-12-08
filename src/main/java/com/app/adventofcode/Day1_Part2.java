package com.app.adventofcode;

import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Day1_Part2 {

    public static void main(String[] args) throws URISyntaxException, IOException {

        List<String> lines = Utils.getFileLines("day1_part1.txt");
        List<String> finalList = new ArrayList<>();

        lines.forEach(line -> {
            String line2 = line.replaceAll("zero", "0")
                    .replaceAll("one", "o1e")
                    .replaceAll("two", "t2o")
                    .replaceAll("three", "t3e")
                    .replaceAll("four", "f4r")
                    .replaceAll("five", "f5e")
                    .replaceAll("six", "s6x")
                    .replaceAll("seven", "s7n")
                    .replaceAll("eight", "e8t")
                    .replaceAll("nine", "n9e");
            finalList.add(line2);
        });


        AtomicReference<Long> sum = new AtomicReference<>(0l);
        finalList.forEach(line -> {
            String firstDigit = "";
            String secondDigit = "";
            char [] characters = line.toCharArray();

            for(char val : characters) {
                if(Character.isDigit(val)) {
                    if(StringUtils.isEmpty(firstDigit)) {
                        firstDigit = String.valueOf(val);
                    } else {
                        secondDigit = String.valueOf(val);
                    }
                }
            }

            if(StringUtils.isEmpty(secondDigit)) {
                secondDigit = firstDigit;
            }

            String finalFirstDigit = firstDigit;
            String finalSecondDigit = secondDigit;
            sum.updateAndGet(v -> v + Long.parseLong(finalFirstDigit + finalSecondDigit));

            System.out.println(line + " :: " + finalFirstDigit + finalSecondDigit + " == " + sum.get());
        });
        System.out.println("Total :: " + sum);
    }
}
