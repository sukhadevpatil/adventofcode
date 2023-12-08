package com.app.adventofcode;

import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Day1_Part1 {

    public static void main(String[] args) throws URISyntaxException, IOException {
        List<String> lines = Utils.getFileLines("day1_part1.txt");

        AtomicReference<Long> sum = new AtomicReference<>(0l);
        lines.forEach(line -> {
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

        });

        System.out.println("Total :: " + sum);

    }
}
