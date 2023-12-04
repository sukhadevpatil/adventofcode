package com.app.adventofcode;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdventDay4ElfsPileOfScratchcards {

    public static void main(String[] args) throws URISyntaxException, IOException {
        URL url = ClassLoader.getSystemResource("elfsPileScratchCardsInput.txt");

        List<String> lines = Files.readAllLines(Path.of(url.toURI()));

        Map<String, Integer> cardWinNum = new HashMap<>();

        lines.forEach(line -> {
            String [] lineArr = line.trim().split(":");

            String [] cardArr = lineArr[0].trim().replace("  ", " ").split(" ");
            String cardNum = cardArr[cardArr.length -1];

            String [] combNumbersArr = lineArr[1].trim().replace("  ", " ").split("\\|");

            String [] expectedNums = combNumbersArr[0].trim().replace("  ", " ").split(" ");
            List<String> expectedList = Arrays.asList(expectedNums);

            String [] inputNums = combNumbersArr[1].trim().replace("  ", " ").split(" ");
            List<String> inputList = Arrays.asList(inputNums);

            inputList.forEach(val -> {
                if(expectedList.contains(val)) {
                    if(cardWinNum.containsKey(cardNum)) {
                        cardWinNum.put(cardNum, cardWinNum.get(cardNum) * 2);
                    } else {
                        cardWinNum.put(cardNum, 1);
                    }
                }
            });
        });
        System.out.println(cardWinNum);

        System.out.println(cardWinNum.values().stream().mapToInt(Integer::intValue).sum());
    }
}
