package aoc.utility;

import lombok.experimental.UtilityClass;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class Reader {
    public static List<Integer> readCsvAsIntegers(String filename) {
        String input;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            input = reader.readLine();
            return Arrays.stream(input.trim().split(",")).map(Integer::parseInt).collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    public static List<Integer> readLinesAsIntegers(String filename) {
        List<Integer> input = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine();

            while (line != null) {
                input.add(Integer.parseInt(line));
                line = reader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return input;
    }

    public static List<String> readLinesAsStrings(String filename) {
        List<String> input = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine();

            while (line != null) {
                input.add(line);
                line = reader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return input;
    }
}
