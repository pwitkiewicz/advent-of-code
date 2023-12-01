package aoc.year2020.day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        BufferedReader reader;

        int[] treesCount = new int[5];
        int[] currentPos = new int[5];

        try {
            reader = new BufferedReader(new FileReader("input-day3.txt"));
            String line = reader.readLine();
            int lineLength = line.length();
            int lineNumber = 0;

            while(line != null) {

                for(int i = 0; i < currentPos.length-1; i++) {
                    if(currentPos[i] >= lineLength) {
                        currentPos[i] -= lineLength;
                    }

                    if(line.charAt(currentPos[i]) == '#') {
                        treesCount[i]++;
                    }
                }

                currentPos[0] += 1;
                currentPos[1] += 3;
                currentPos[2] += 5;
                currentPos[3] += 7;


                if(lineNumber % 2 == 0) {
                    if(currentPos[4] >= lineLength) {
                        currentPos[4] -= lineLength;
                    }

                    if(line.charAt(currentPos[4]) == '#') {
                        treesCount[4]++;
                    }

                    currentPos[4]++;
                }

                lineNumber++;
                line = reader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        long sum = 1;
        for(int trees : treesCount) {
            System.out.println(trees);
            sum *= trees;
        }

        System.out.println(sum);
    }
}
