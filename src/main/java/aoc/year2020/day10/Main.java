package aoc.year2020.day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    private static int[] readFile(String fileName) {
        BufferedReader reader;
        ArrayList<Integer> input = new ArrayList<>();

        input.add(0);
        int max = 0;

        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();

            while (line != null) {
                int num = Integer.parseInt(line);
                input.add(num);
                if(num > max) {
                    max = num;
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        input.add(max+3);
        return input.stream().mapToInt(i -> i).toArray();
    }

    private static int countDiffs(int[] input) {
        int ones = 0;
        int threes = 0;

        for (int i = 0; i < input.length-1; i++) {
            if(input[i+1] - input[i] == 1) {
                ones++;
            }
            if(input[i+1] - input[i] == 3) {
                threes++;
            }
        }

        return ones*threes;
    }

    public static long countArrangements(int[] input) {
        long[] result = new long[input.length];

        result[0] = 1;

        for(int i = 1; i < input.length; i++) {
            result[i] = 0;
            int j = i-1;
            while(j >= 0 && input[i] - input[j] <= 3) {
                result[i] += result[j];
                j--;
            }
        }

        return result[input.length-1];
    }

    public static void main(String[] args) {
        int[] input = readFile("input-day10.txt");

        Arrays.sort(input);

        System.out.println(countDiffs(input));
        System.out.println(countArrangements(input));

    }
}
