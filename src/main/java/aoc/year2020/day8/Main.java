package aoc.year2020.day8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class Main {
    private static boolean checkInstruction(List<String> input, int j, HashSet<Integer> visitedInstructions, int accumulator) {
        HashSet<Integer> visited = new HashSet<>(visitedInstructions);
        visited.remove(j);
        List<String> instructions = new ArrayList<>(input);
        int i = j;
        int acc = accumulator;
        if(instructions.get(j).split("\\s+")[0].equals("nop")) {
            instructions.set(j, "jmp " + instructions.get(j).split("\\s+")[1]);
        } else
            instructions.set(j, "nop " + instructions.get(j).split("\\s+")[1]);

        while(!visited.contains(i)) {
            if(i == instructions.size()) {
                System.out.println("correct accumulator = " + acc);
                break;
            }
            String[] instruction = instructions.get(i).split("\\s+");
            visited.add(i);

            switch(instruction[0]) {
                case "nop":
                    i++;
                    break;
                case "jmp":
                    i += Integer.parseInt(instruction[1]);
                    break;
                case "acc":
                    acc += Integer.parseInt(instruction[1]);
                    i++;
                    break;
            }
        }

        return i == instructions.size();
    }

    public static void main(String[] args) {
        BufferedReader reader;
        int accumulator = 0;
        List<String> instructions = new ArrayList<>();

        try {
            reader = new BufferedReader(new FileReader("input-day8.txt"));
            String line = reader.readLine();

            while(line != null) {
                instructions.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        HashSet<Integer> visitedInstructions = new HashSet<>();

        int i = 0;

        while(!visitedInstructions.contains(i)) {
            String[] instruction = instructions.get(i).split("\\s+");
            visitedInstructions.add(i);

            switch(instruction[0]) {
                case "nop":
                    if(checkInstruction(instructions, i, visitedInstructions, accumulator)) {
                        System.out.println("change nop to jmp at " + i);
                    }
                    i++;
                    break;
                case "jmp":
                    if(checkInstruction(instructions, i, visitedInstructions, accumulator)) {
                        System.out.println("change jmp to nop at " + i);
                    }
                    i += Integer.parseInt(instruction[1]);
                    break;
                case "acc":
                    accumulator += Integer.parseInt(instruction[1]);
                    i++;
                    break;
            }
        }

        System.out.println("accumulator  = " + accumulator);
    }
}
