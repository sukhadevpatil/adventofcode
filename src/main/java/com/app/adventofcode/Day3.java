package com.app.adventofcode;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Day3 {

    public static void main(String[] args) throws URISyntaxException, IOException {
        List<String> lines = Utils.getFileLines("day3_input.txt");

        AtomicLong result = new AtomicLong(0);
        lines.forEach(line -> {
            String[] values = line.trim().replaceAll("\\*", ".").replaceAll("#", ".").replaceAll("\\+", ".").replaceAll("\\$", ".").split("\\.");

            for (String s : values) {
                String value = s.trim();
                if (Strings.isNotBlank(value)) {

                    if (StringUtils.isNumeric(value)) {
                        result.set(result.get() + Long.parseLong(value));
                        System.out.println("===" + value);
                    }
                }
            }
            System.out.println("Result :: " + result.get());
        });
    }
}
