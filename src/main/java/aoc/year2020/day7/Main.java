package aoc.year2020.day7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;



public class Main {
    static HashMap<String, Boolean> goldMap = new HashMap<>();
    static HashMap<String, HashMap<String, Integer>> containerMap = new HashMap<>();

    private static String getCarryingBagName(String line) {
        String[] bag = line.split("contain")[0].trim().split("\\s+");
        String bagName = bag[0] + " " + bag[1];
        return bagName;
    }

    private static HashMap<String, Integer> getContains(String line) {
        HashMap<String, Integer> result = new HashMap<>();

        int numOfBags = line.split(",").length ;
        line = line.replaceAll(",", " ");
        String[] bagContains = line.split("contain")[1].trim().split("\\s+");

        for(int i = 0; i < numOfBags; i++) {
            int k = 1 + i*4;
            if(bagContains[i*4].equals("no")) {
                break;
            }
            int count = Integer.parseInt(bagContains[i*4]);
            String bagName = bagContains[k] + " " + bagContains[k+1];
            result.put(bagName, count);
        }

        return result;
    }

    private static boolean containsGold(String bagName) {
        if(goldMap.containsKey(bagName)) {
            if(goldMap.get(bagName))
                return true;
        }

        if(containerMap.containsKey(bagName)) {
            for(String color : containerMap.get(bagName).keySet()) {
                if(containsGold(color))
                    return true;
            }
        }

        return false;
    }

    public static int getNumOfBags(String bagName) {
        int result = 0;

        for(String key : containerMap.get(bagName).keySet()) {
            int value = containerMap.get(bagName).get(key);
            int inside = getNumOfBags(key);
            result += value + (value * inside);
        }

        return result;
    }

    public static void main(String[] args) {
        BufferedReader reader;
        ArrayList<String> input = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader("input-day7.txt"));
            String line = reader.readLine();

            while(line != null) {
                input.add(line);

                String bagName = getCarryingBagName(line);
                HashMap<String, Integer> bagContainer = getContains(line);

                containerMap.put(bagName, bagContainer);
                if(bagContainer.containsKey("shiny gold")) {
                    goldMap.put(bagName, true);
                } else {
                    goldMap.put(bagName, false);
                }

                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        int totalBags = 0;
        int inShinyGold = 0;

        for(String line : input) {
            String bagName = getCarryingBagName(line);

            if(containsGold(bagName)) {
                totalBags++;
            }
        }

        System.out.println("total bags: " + totalBags);

        inShinyGold += getNumOfBags("shiny gold");

        System.out.println("bags in shiny gold bag: " + inShinyGold);
    }
}
