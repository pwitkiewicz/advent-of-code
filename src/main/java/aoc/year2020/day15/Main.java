package aoc.year2020.day15;

import java.util.*;

public class Main {
    private static HashMap<Integer, LinkedList<Integer>> createMap(int[] input) {
        HashMap<Integer, LinkedList<Integer>> result = new HashMap<>();

        for(int i = 0; i < input.length; i++) {
            LinkedList<Integer> list = new LinkedList<>();
            list.addFirst(i+1);
            result.put(input[i], list);
        }

        return result;
    }

    private static int takeTurns(HashMap<Integer, LinkedList<Integer>> map, int lastNumber, int startingTurn, int endTurn) {
        for (int i = startingTurn+1; i <= endTurn ; i++) {
            int currentNumber = 0;

            if(map.get(lastNumber).size() != 1) {
                currentNumber = map.get(lastNumber).get(0) - map.get(lastNumber).get(1);
            }

            if(map.containsKey(currentNumber)) {
                map.get(currentNumber).addFirst(i);
            } else {
                LinkedList<Integer> list = new LinkedList<>();
                list.addFirst(i);
                map.put(currentNumber, list);
            }

            if(i == endTurn) {
                return currentNumber;
            }

            lastNumber = currentNumber;
        }

        return 0;
    }

    public static void main(String[] args) {
        int[] startingNumbers = Arrays.stream("1,17,0,10,18,11,6".split(",")).mapToInt(Integer::parseInt).toArray();
        HashMap<Integer, LinkedList<Integer>> numMap = createMap(startingNumbers);
        int lastNumber = startingNumbers[startingNumbers.length-1];
        int turn = numMap.size();

        int answer = takeTurns(numMap, lastNumber, turn, 30000000);
        System.out.println(answer);
    }
}
