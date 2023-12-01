package aoc.year2021.day3;

import aoc.utility.Reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day3 {
    private static Map<Character, Long> getFrequencies(List<String> input, int index) {
        return input.stream()
                .map(s -> s.charAt(index))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    private static char findMostCommonBit(List<String> input, int index) {
        var frequencies = getFrequencies(input, index);

        return frequencies.get('0') > frequencies.get('1') ? '0' : '1';
    }

    private static char findLeastCommonBit(List<String> input, int index) {
        var frequencies = getFrequencies(input, index);

        return frequencies.get('0') <= frequencies.get('1') ? '0' : '1';
    }

    private static String findGammaRate(List<String> input) {
        var length = input.get(0).length();
        var stringBuilder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            stringBuilder.append(findMostCommonBit(input, i));
        }

        return stringBuilder.toString();
    }

    private static String flipBits(String input) {
        var stringBuilder = new StringBuilder();
        for (char c : input.toCharArray()) {
            stringBuilder.append(c == '0' ? '1' : '0');
        }
        return stringBuilder.toString();
    }

    private static String filterList(List<String> input, boolean mostCommon) {
        List<String> tempList = new ArrayList<>(input);

        for (int i = 0; i < input.get(0).length(); i++) {
            final var index = i;
            final var commonBit = mostCommon ? findMostCommonBit(tempList, index) : findLeastCommonBit(tempList, index);
            tempList = tempList.stream()
                    .filter(s -> s.charAt(index) == commonBit)
                    .collect(Collectors.toList());

            if (tempList.size() == 1) break;
        }

        return tempList.get(0);
    }

    public static void main(String[] args) {
        var input = Reader.readLinesAsStrings("input");

        String gammaRateBinary = findGammaRate(input);
        String epsilonRateBinary = flipBits(gammaRateBinary);
        long gammaRate = Long.parseLong(gammaRateBinary, 2);
        long epsilonRate = Long.parseLong(epsilonRateBinary, 2);

        System.out.println(gammaRate * epsilonRate);

        String oxygenGeneratorRatingBinary = filterList(input, true);
        String CO2ScrubberRatingBinary = filterList(input, false);
        long oxygenGeneratorRating = Long.parseLong(oxygenGeneratorRatingBinary, 2);
        long CO2ScrubberRating = Long.parseLong(CO2ScrubberRatingBinary, 2);

        System.out.println(oxygenGeneratorRating * CO2ScrubberRating);
    }
}
