package aoc.year2021.day2;

import aoc.utility.Reader;

import java.util.List;

public class Day2 {
    private static void firstPart(List<String> input) {
        long horizontalPosition = 0L;
        long verticalPosition = 0L;

        for (String line : input) {
            String[] currentLine = line.split("\\s+");
            switch (currentLine[0]) {
                case "forward" -> horizontalPosition += Long.parseLong(currentLine[1]);
                case "up" -> verticalPosition -= Long.parseLong(currentLine[1]);
                case "down" -> verticalPosition += Long.parseLong(currentLine[1]);
            }
        }

        System.out.println(horizontalPosition * verticalPosition);
    }

    private static void secondPart(List<String> input) {
        long horizontalPosition = 0L;
        long verticalPosition = 0L;
        long aim = 0L;

        for (String line : input) {
            String[] currentLine = line.split("\\s+");
            switch (currentLine[0]) {
                case "forward" -> {
                    long value = Long.parseLong(currentLine[1]);
                    horizontalPosition += value;
                    verticalPosition += value * aim;
                }
                case "up" -> aim -= Long.parseLong(currentLine[1]);
                case "down" -> aim += Long.parseLong(currentLine[1]);
            }
        }

        System.out.println(horizontalPosition * verticalPosition);
    }

    public static void main(String[] args) {
        var lines = Reader.readLinesAsStrings("testinput");

        firstPart(lines);
        secondPart(lines);
    }
}
