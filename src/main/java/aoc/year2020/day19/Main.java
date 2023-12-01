package aoc.year2020.day19;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Main {
    private static class Rule {
        int number;
        List<int[]> recipes;
        List<String> values;

        public Rule(int n, String r) {
            number = n;
            if (r.contains("a")) {
                recipes = null;
                values = new ArrayList<>();
                values.add("a");
            } else if (r.contains("b")) {
                recipes = null;
                values = new ArrayList<>();
                values.add("b");
            } else {
                values = null;
                recipes = new ArrayList<>();

                String[] temp = r.split(" ");
                List<Integer> recipe = new ArrayList<>();

                for (String s : temp) {
                    if (s.equals("|")) {
                        recipes.add(recipe.stream().mapToInt(j -> j).toArray());
                        recipe.clear();
                    } else {
                        recipe.add(Integer.parseInt(s));
                    }
                }

                recipes.add(recipe.stream().mapToInt(j -> j).toArray());
            }
        }

        public List<String> getValues(HashMap<Integer, Rule> ruleMap) {
            if (values == null) {
                initValues(ruleMap);
            }
            return values;
        }

        public void initValues(HashMap<Integer, Rule> ruleMap) {
            values = new ArrayList<>();

            for (int[] recipe : recipes) {
                if (recipe.length == 1) {
                    values.addAll(ruleMap.get(recipe[0]).getValues(ruleMap));
                } else if (recipe.length == 2) {
                    List<String> firstRule = ruleMap.get(recipe[0]).getValues(ruleMap);
                    List<String> secondRule = ruleMap.get(recipe[1]).getValues(ruleMap);

                    for (String s1 : firstRule) {
                        for (String s2 : secondRule) {
                            StringBuilder sb = new StringBuilder(s1);
                            sb.append(s2);
                            values.add(sb.toString());
                        }
                    }
                } else if (recipe.length == 3) {
                    List<String> firstRule = ruleMap.get(recipe[0]).getValues(ruleMap);
                    List<String> secondRule = ruleMap.get(recipe[1]).getValues(ruleMap);
                    List<String> thirdRule = ruleMap.get(recipe[2]).getValues(ruleMap);

                    for (String s1 : firstRule) {
                        for (String s2 : secondRule) {
                            for (String s3 : thirdRule) {
                                StringBuilder sb = new StringBuilder(s1);
                                sb.append(s2);
                                sb.append(s3);
                                values.add(sb.toString());
                            }
                        }
                    }
                }
            }
        }
    }

    private static HashMap<Integer, Rule> readRules(String filename) {
        BufferedReader reader;
        HashMap<Integer, Rule> input = new HashMap<>();

        try {
            reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();

            while (!line.equals("")) {
                int number = Integer.parseInt(line.split(": ")[0]);
                Rule temp = new Rule(number, line.split(": ")[1]);
                input.put(number, temp);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return input;
    }

    private static List<String> readMessages(String filename) {
        BufferedReader reader;
        List<String> input = new ArrayList<>();

        try {
            reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();

            while (!line.equals("")) {
                line = reader.readLine();
            }

            line = reader.readLine();

            while (line != null) {
                input.add(line);
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return input;
    }

    public static void main(String[] args) {
        HashMap<Integer, Rule> rules = readRules("test1-day19.txt");
        List<String> messages = readMessages("test1-day19.txt");

        HashSet<String> ruleZero = new HashSet<>(rules.get(0).getValues(rules));

        int result = 0;

        for (String m : messages) {
            if (ruleZero.contains(m)) {
                result++;
            }
        }

        System.out.println(result);
    }
}
