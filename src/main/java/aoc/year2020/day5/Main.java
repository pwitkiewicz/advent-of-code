package aoc.year2020.day5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        BufferedReader reader;
        int maxID=0;
        ArrayList<Integer> seats = new ArrayList<>();

        try {
            reader = new BufferedReader(new FileReader("input-day5.txt"));
            String seat = reader.readLine();

            while(seat != null) {

                int minC = 0;
                int maxC = 127;

                int minR = 0;
                int maxR = 7;

                for(char letter : seat.toCharArray()) {
                    if(letter == 'F') {
                        maxC -= (maxC - minC + 1)/2;
                    }
                    if(letter == 'B') {
                        minC += (maxC - minC + 1)/2;
                    }

                    if(letter == 'R') {
                        minR += (maxR - minR +1)/2;
                    }
                    if(letter == 'L') {
                        maxR -= (maxR - minR +1)/2;
                    }
                }

                int ID = minC * 8 + maxR;

                seats.add(ID);

                if(ID > maxID)
                    maxID = ID;

                seat = reader.readLine();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println(maxID + "\n");

        Collections.sort(seats);

        for(int i = 0; i < seats.size()-1; i++) {
            if(seats.get(i+1)-seats.get(i) > 1) {
                System.out.println(seats.get(i)+1);
            }
        }
    }
}
