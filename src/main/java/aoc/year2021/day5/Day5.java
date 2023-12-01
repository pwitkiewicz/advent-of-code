package aoc.year2021.day5;

import aoc.utility.Reader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day5 {
    private static List<Line> parseInput(final List<String> input) {
        final var output = new ArrayList<Line>();

        for (String s : input) {
            String[] temp = s.trim().split(" -> ");
            var points = new ArrayList<Integer>();
            for (int i = 0; i < 2; i++) {
                points.addAll(Arrays.stream(temp[i].split(",")).map(Integer::parseInt).collect(Collectors.toList()));
            }
            output.add(new Line(points.get(0), points.get(1), points.get(2), points.get(3)));
        }

        return output;
    }

    private static int countOverlappingLines(List<Line> input) {
        var counter = 0;

        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 1000; j++) {
                int overlapping = 0;
                for (Line line : input) {
                    overlapping += line.checkPoint(i, j) ? 1 : 0;
                }
                counter += overlapping > 1 ? 1 : 0;
            }
        }

        return counter;
    }

    public static void main(String[] args) {
        var input = Reader.readLinesAsStrings("input");
        var lines = parseInput(input)
                .stream()
                .filter(line -> line.isHorizontal() || line.isVertical() || line.isDiagonal())
                .collect(Collectors.toList());

        System.out.println(countOverlappingLines(lines));
    }
}
