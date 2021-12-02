package aoc.day2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day2 {
    private static void firstPart(String filename) {
        long horizontalPosition = 0L;
        long verticalPosition = 0L;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine();

            while (line != null) {
                String[] currentLine = line.split("\\s+");
                switch (currentLine[0]) {
                    case "forward"  -> horizontalPosition += Long.parseLong(currentLine[1]);
                    case "up"       -> verticalPosition -= Long.parseLong(currentLine[1]);
                    case "down"     -> verticalPosition += Long.parseLong(currentLine[1]);
                }
                line = reader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(horizontalPosition*verticalPosition);
    }

    private static void secondPart(String filename) {
        long horizontalPosition = 0L;
        long verticalPosition = 0L;
        long aim = 0L;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine();

            while (line != null) {
                String[] currentLine = line.split("\\s+");
                switch (currentLine[0]) {
                    case "forward"  -> {
                        long value = Long.parseLong(currentLine[1]);
                        horizontalPosition += value;
                        verticalPosition += value*aim;
                    }
                    case "up"       -> aim -= Long.parseLong(currentLine[1]);
                    case "down"     -> aim += Long.parseLong(currentLine[1]);
                }
                line = reader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(horizontalPosition*verticalPosition);
    }

    public static void main(String[] args) {
        firstPart("input");
        secondPart("input");
    }
}