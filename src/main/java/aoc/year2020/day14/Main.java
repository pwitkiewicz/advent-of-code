package aoc.year2020.day14;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class Main {
    private static String[] readFile(String fileName) {
        BufferedReader reader;
        ArrayList<String> input = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();

            while (line != null) {
                input.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < input.size(); i++) {
            input.set(i, input.get(i).replace("mem[", ""));
            input.set(i, input.get(i).replace("] ", " "));
            input.set(i, input.get(i).replaceAll(" ", ""));
        }

        return input.toArray(String[]::new);
    }

    private static long applyMaskPart1(String mask, String number) {
        StringBuilder sb = new StringBuilder();

        StringBuilder sbMask = new StringBuilder(mask);
        sbMask.reverse();
        mask = sbMask.toString();

        StringBuilder sbNumber = new StringBuilder(number);
        sbNumber.reverse();
        number = sbNumber.toString();

        for (int i = 0; i < mask.length(); i++) {
            if (i < number.length()) {
                if (mask.charAt(i) == 'X') {
                    sb.append(number.charAt(i));
                } else {
                    sb.append(mask.charAt(i));
                }
            } else {
                if (mask.charAt(i) == 'X') {
                    sb.append("0");
                } else {
                    sb.append(mask.charAt(i));
                }
            }
        }

        return Long.parseLong(sb.reverse().toString(), 2);
    }

    private static void solvePart1(String[] input) {
        HashMap<Integer, Long> result = new HashMap<>();

        String mask = "";
        String number;

        for (String s : input) {
            String[] entry = s.split("=");
            if (entry[0].equals("mask")) {
                mask = entry[1];
            } else {
                int index = Integer.parseInt(entry[0]);
                number = Integer.toBinaryString(Integer.parseInt(entry[1]));
                long value = applyMaskPart1(mask, number);
                result.put(index, value);
            }
        }

        long sum = 0;

        for (int key : result.keySet()) {
            sum += result.get(key);
        }

        System.out.println(sum);
    }


    private static ArrayList<Long> applyMaskPart2(String mask, String number, int index) {
        ArrayList<Long> result = new ArrayList<>();

        if (index == mask.length()) {
            result.add(Long.parseLong(number, 2));
            return result;
        }

        if (mask.charAt(index) == '1') {
            number = number.substring(0, index) + mask.charAt(index) + number.substring(index + 1);
            return applyMaskPart2(mask, number, index + 1);
        } else if (mask.charAt(index) == 'X') {
            number = number.substring(0, index) + "1" + number.substring(index + 1);
            ArrayList<Long> one = applyMaskPart2(mask, number, index + 1);

            number = number.substring(0, index) + "0" + number.substring(index + 1);
            ArrayList<Long> two = applyMaskPart2(mask, number, index + 1);

            one.addAll(two);
            return one;
        }

        return applyMaskPart2(mask, number, index + 1);
    }

    private static void solvePart2(String[] input) {
        HashMap<Long, Long> result = new HashMap<>();

        String mask = "";
        String indexBin;

        for (String s : input) {
            String[] entry = s.split("=");
            if (entry[0].equals("mask")) {
                mask = entry[1];
            } else {
                int index = Integer.parseInt(entry[0]);
                long value = Long.parseLong(entry[1]);

                indexBin = Integer.toBinaryString(index);

                while (indexBin.length() != mask.length()) {
                    indexBin = "0" + indexBin;
                }

                ArrayList<Long> indexes = applyMaskPart2(mask, indexBin, 0);

                for (long idx : indexes) {
                    result.put(idx, value);
                }
            }
        }

        long sum = 0;

        for (long key : result.keySet()) {
            sum += result.get(key);
        }

        System.out.println(sum);
    }

    public static void main(String[] args) {
        String[] input = readFile("input-day14.txt");
        solvePart1(input);
        solvePart2(input);
    }
}
