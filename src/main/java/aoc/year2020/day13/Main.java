package aoc.year2020.day13;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class Main {
    private static int[] readFilePart1(String fileName) {
        BufferedReader reader;
        ArrayList<Integer> input = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            input.add(Integer.parseInt(line));

            line = reader.readLine();
            String[] buses = line.split(",");
            for (String b : buses) {
                if (!b.equals("x")) {
                    input.add(Integer.parseInt(b));
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return input.stream().mapToInt(i -> i).toArray();
    }

    private static HashMap<Integer, Integer> readFilePart2(String fileName) {
        BufferedReader reader;
        HashMap<Integer, Integer> input = new HashMap<>();
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            line = reader.readLine();
            String[] buses = line.split(",");
            int i = 0;
            for (String b : buses) {
                if (!b.equals("x")) {
                    input.put(i, Integer.parseInt(b));
                }
                i++;
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return input;
    }

    private static int part1(int[] input) {
        int minWaitingTime = Integer.MAX_VALUE;
        int busNum = 0;
        int time = input[0];

        for (int i = 1; i < input.length; i++) {
            int temp = time / input[i];
            int nextBus = input[i] * (temp + 1);
            int waitingTime = nextBus - time;
            if (waitingTime < minWaitingTime) {
                minWaitingTime = waitingTime;
                busNum = input[i];
            }
        }

        return busNum * minWaitingTime;
    }

    private static long part2(HashMap<Integer, Integer> inputMap) {
        int[] keys = new int[inputMap.size()];
        int i = 0;
        for (int key : inputMap.keySet()) {
            keys[i++] = key;
        }
        Arrays.sort(keys);

        long answer = inputMap.get(0);
        long period = inputMap.get(0);

        for (int j = 1; j < keys.length; j++) {
            int value = inputMap.get(keys[j]);
            while ((answer + keys[j]) % value != 0) {
                answer += period;
            }
            period *= value;
        }

        return answer;
    }

    public static void main(String[] args) {
        int answer1 = part1(readFilePart1("input-day13.txt"));
        System.out.println(answer1);

        long answer2 = part2(readFilePart2("input-day13.txt"));
        System.out.println(answer2);
    }
}
