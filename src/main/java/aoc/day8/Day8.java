package aoc.day8;

import aoc.utility.Reader;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Day8 {
    private static List<String> getOutputValues(final List<String> input) {
        return input.stream()
                .map(s -> s.split(" \\| ")[1])
                .map(s -> Arrays.stream(s.split("\\s+")).collect(Collectors.toList()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private static long countUniqueDigits(List<String> input) {
        return input.stream()
                .filter(s -> s.length() == 2 || s.length() == 3 || s.length() == 4 || s.length() == 7)
                .count();
    }

    public static void main(String[] args) {
        var input = Reader.readLinesAsStrings("input");
        // part 1
        var outputValues = getOutputValues(input);
        System.out.println(countUniqueDigits(outputValues));

        // part 2
        var sum = 0L;
        for(String s : input) {
            var display = SevenSegmentDisplay.solvePuzzle(s);
            sum += display.calculateOutputValue();
        }
        System.out.println(sum);
    }
}
