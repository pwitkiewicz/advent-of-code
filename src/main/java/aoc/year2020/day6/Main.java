package aoc.year2020.day6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        BufferedReader reader;
        int totalSize = 0;

        try {
            reader = new BufferedReader(new FileReader("input-day6.txt"));
            String line = reader.readLine();

            while (line != null) {
                String answers = line;

                HashSet<Character> ans1 = new HashSet<>();

                for(char letter : answers.toCharArray()) {
                    ans1.add(letter);
                }

                line = reader.readLine();
                while (line != null && !line.equals("")) {
                    answers = line;

                    HashSet<Character> ans2 = new HashSet<>();

                    for(char letter : answers.toCharArray()) {
                        ans2.add(letter);
                    }

                    ans1.retainAll(ans2);

                    line = reader.readLine();
                }

                totalSize += ans1.size();

                line = reader.readLine();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(totalSize);
    }
}
