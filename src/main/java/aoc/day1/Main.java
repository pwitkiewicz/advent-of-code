package aoc.day1;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {
    private static List<Integer> readFile(String filename) {
        List<Integer> input = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename)) ){
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

    public static long countIncreases(List<Integer> input) {
        Iterator<Integer> iterator = input.iterator();
        Integer measurement = iterator.next();
        long increases = 0;

        while(iterator.hasNext()) {
            Integer currentMeasurement = iterator.next();
            increases += currentMeasurement > measurement ? 1 : 0;
            measurement = currentMeasurement;
        }

        return increases;
    }

    public static long countIncreasesByThree(List<Integer> input) {
        Integer[] inputArray = input.toArray(new Integer[0]);

        int index = 2;
        Measurement measurement = Measurement.of(inputArray, index);
        long increases = 0;

        while(index + 1 < inputArray.length) {
            index++;
            Measurement currentMeasurement = Measurement.of(inputArray, index);
            increases += currentMeasurement.getValue() > measurement.getValue() ? 1 : 0;
            measurement.setValue(currentMeasurement.getValue());
        }

        return increases;
    }

    public static void main(String[] args) {
        List<Integer> input = readFile("input");

        System.out.println(countIncreases(input));
        System.out.println(countIncreasesByThree(input));
    }
}

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
class Measurement {
    private long value;

    public static Measurement of(final Integer[] input, final int index) {
        return new Measurement(input[index-2] + input[index-1] + input[index]);
    }
}
