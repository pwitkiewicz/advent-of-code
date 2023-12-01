package aoc.year2020.day1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {

        HashMap<Integer, Integer> nums = new HashMap<>();
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("input.txt"));
            String line = reader.readLine();

            while(line != null) {
                int num = Integer.parseInt(line);

                if(nums.containsValue(num)) {
                    for(int value : nums.keySet()) {
                        if(nums.get(value) == num) {
                            System.out.println(num*value);
                        }
                    }
                }

                if(!nums.containsKey(num)) {
                    nums.put(num, 2020-num);
                }

                line = reader.readLine();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int num1 : nums.keySet()) {
            for(int num2 : nums.keySet()) {
                int num3 = 2020 - (num1 + num2);
                if(num3 < 0) {
                    break;
                }
                if(nums.keySet().contains(num3)) {
                    System.out.println(num1*num2*num3);
                    return;
                }
            }
        }
    }
}
