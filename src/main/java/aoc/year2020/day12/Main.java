package aoc.year2020.day12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


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

        return input.toArray(String[]::new);
    }

    public static void main(String[] args) {
        String[] input = readFile("input-day12.txt");

        int posHorizontal = 0;       // +east, -west
        int posVertical = 0;         // +north, -south,
        int waypointHorizontal = 10;
        int waypointVertical = 1;

        for (String s : input) {
            char command = s.charAt(0);
            int value = Integer.parseInt(s.substring(1));
            if (command == 'N') {
                waypointVertical += value;
            }
            if (command == 'S') {
                waypointVertical -= value;
            }
            if (command == 'E') {
                waypointHorizontal += value;
            }
            if (command == 'W') {
                waypointHorizontal -= value;
            }
            if (command == 'F') {
                posHorizontal += value * waypointHorizontal;
                posVertical += value * waypointVertical;
            }
            if (command == 'R') {
                for(int i = 0; i < value/90; i++) {
                    int temp= waypointVertical;
                    waypointVertical = -waypointHorizontal;
                    waypointHorizontal = temp;
                }
            }
            if (command == 'L') {
                for(int i = 0; i < value/90; i++) {
                    int temp = waypointVertical;
                    waypointVertical = waypointHorizontal;
                    waypointHorizontal = -temp;
                }
            }
        }
        System.out.println(Math.abs(posHorizontal)+Math.abs(posVertical));
    }
}
