package aoc.year2021.day6;

import aoc.utility.Reader;

import java.util.*;
import java.util.stream.Collectors;

public class Day6 {
    private static Map<Integer, Long> initMap() {
        var map = new HashMap<Integer, Long>();
        for (int i = 0; i < 10; i++) {
            map.put(i, 0L);
        }
        return map;
    }
    
    private static List<Integer> parseCommaDelimitedIntegers() {
        var input = Reader.readLinesAsStrings("input");

        return Arrays.stream(input.get(0).trim().split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private static void nextDay(Map<Integer, Long> ages) {
        for (int i = 0; i < 9; i++) {
            ages.put(i, ages.get(i+1));
        }
        ages.put(9, 0L);
    }

    private static void handleZeros(Map<Integer, Long> ages) {
        ages.put(9, ages.get(0));
        ages.put(7, ages.get(7) + ages.get(0));
    }

    public static void main(String[] args) {
        var agesToCountMap = initMap();
        var initialAges = parseCommaDelimitedIntegers();

        for(int age : initialAges) {
            agesToCountMap.put(age, agesToCountMap.get(age) + 1);
        }

        for (int i = 0; i < 256; i++) {
            handleZeros(agesToCountMap);
            nextDay(agesToCountMap);
        }

        long size = agesToCountMap.values().stream().reduce(Long::sum).get();
        System.out.println(size);
    }
}
