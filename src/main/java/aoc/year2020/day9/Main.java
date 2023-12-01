package aoc.year2020.day9;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;


public class Main {
    private static long[] readFile(String fileName) {
        BufferedReader reader;
        ArrayList<Long> input = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();

            while (line != null) {
                input.add(Long.parseLong(line));
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return input.stream().mapToLong(i -> i).toArray();
    }

    private static boolean checkNumber(long num, HashSet<Long> keys) {
        for (long key : keys) {
            if (num - key != key && keys.contains(num - key)) {
                return true;
            }
        }
        return false;
    }

    private static long[] findContigousRange(long[] input, long num) {
        int sum = 0;
        int lastPosition = 0;
        for (int i = 0; i < input.length; i++) {
            while (sum > num) {
                sum -= input[lastPosition++];
            }

            if (sum < num) {
                sum += input[i];
            }

            if (sum == num) {
                System.out.println("range from " + lastPosition + " to " + i);
                return Arrays.copyOfRange(input, lastPosition, i);
            }
        }

        return null;
    }

    public static void main(String[] args) {
        long[] input = readFile("input-day9.txt");
        HashSet<Long> keys = new HashSet<>();

        for (int i = 0; i < 25; i++) {
            keys.add(input[i]);
        }

        long num = 0;

        for (int i = 25; i < input.length; i++) {
            if (!checkNumber(input[i], keys)) {
                num = input[i];
                break;
            }
            keys.remove(input[i - 25]);
            keys.add(input[i]);
        }

        System.out.println(num);

        long[] range = findContigousRange(input, num);

        long min, max;
        min = max = range[0];

        for(long val : range) {
            if(val < min) {
                min = val;
            }
            if(val > max) {
                max = val;
            }
        }

        System.out.println("min " + min + " max " + max + " sum " + (min+max));
    }
}
