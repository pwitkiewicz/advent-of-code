package aoc.year2020.day2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        BufferedReader reader;

        int validPasswordsPart1 = 0;
        int validPasswordsPart2 = 0;

        try {
            reader = new BufferedReader(new FileReader("input-day2.txt"));
            String line = reader.readLine();

            while(line != null) {
                int minVal = Integer.parseInt(line.split("-")[0]);
                int maxVal = Integer.parseInt(line.split("-")[1].split("\\s+")[0]);

                char letter = line.split(":")[0].charAt(line.split(":")[0].length()-1);

                String password = line.split("\\s+")[2];

                int frequency = 0;

                for(char l : password.toCharArray()) {
                    if(l == letter)
                        frequency++;
                }

                if(frequency >= minVal && frequency <= maxVal)
                    validPasswordsPart1++;

                if(password.charAt(minVal-1) == letter && password.charAt(maxVal-1) != letter)
                    validPasswordsPart2++;

                if(password.charAt(minVal-1) != letter && password.charAt(maxVal-1) == letter)
                    validPasswordsPart2++;


                line = reader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(validPasswordsPart1);
        System.out.println(validPasswordsPart2);

    }
}
