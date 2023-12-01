package aoc.year2020.day16;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class Main {
    private static class Field {
        public String name;
        int min1;
        int min2;
        int max1;
        int max2;

        Field(String s, int m1, int m2, int m3, int m4) {
            name = s;
            min1 = m1;
            max1 = m2;
            min2 = m3;
            max2 = m4;
        }

        boolean isValid(int num) {
            return (num >= min1 && num <= max1) || (num >= min2 && num <= max2);
        }
    }

    private static List<Field> fields = new ArrayList<>();
    private static int[] myTicket;
    private static List<int[]> otherTickets = new ArrayList<>();


    private static void readFile(String filename) {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();

            // part 1 - reading fields
            while (!line.equals("")) {
                String[] values = line.split(": ")[1].split(" or ");
                int val1 = Integer.parseInt(values[0].split("-")[0]);
                int val2 = Integer.parseInt(values[0].split("-")[1]);
                int val3 = Integer.parseInt(values[1].split("-")[0]);
                int val4 = Integer.parseInt(values[1].split("-")[1]);
                Field temp = new Field(line.split(": ")[0], val1, val2, val3, val4);
                fields.add(temp);
                line = reader.readLine();
            }

            // reading my ticket
            reader.readLine();
            line = reader.readLine();
            myTicket = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();

            reader.readLine();
            reader.readLine();
            line = reader.readLine();

            // reading other tickets
            while (line != null) {
                int[] temp = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();
                otherTickets.add(temp);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getErrorRate() {
        int result = 0;

        for (int[] arr : otherTickets) {
            loop:
            for (int val : arr) {
                for (Field f : fields) {
                    if (f.isValid(val)) {
                        continue loop;
                    }
                }
                result += val;
            }
        }

        return result;
    }

    private static void discardTickets() {
        ArrayList<int[]> validTickets = new ArrayList<>();
        for (int[] arr : otherTickets) {
            boolean validTicket = true;
            loop:
            for (int val : arr) {
                for (Field f : fields) {
                    if (f.isValid(val)) {
                        continue loop;
                    }
                }
                validTicket = false;
            }
            if (validTicket) {
                validTickets.add(arr);
            }
        }

        otherTickets = validTickets;
    }

    private static ArrayList<String> assignLabels() {
        ArrayList<String> result = new ArrayList<>();
        HashMap<String, Set<Integer>> candidates = new HashMap<>();
        Set<Integer> positions = new HashSet<>();
        for (int i = 0; i < otherTickets.get(0).length; i++) {
            result.add(null);
            positions.add(i);
        }

        for (Field f : fields) {
            candidates.put(f.name, new HashSet<>(positions));
        }

        while (result.stream().anyMatch(Objects::isNull)) {
            for (Field f : fields) {
                for (int[] ticket : otherTickets) {
                    for (int i = 0; i < ticket.length; i++) {
                        if (!f.isValid(ticket[i])) {
                            candidates.get(f.name).remove(i);

                            if (candidates.get(f.name).size() == 1) {
                                int position = new ArrayList<>(candidates.get(f.name)).get(0);
                                result.set(position, f.name);

                                for (Map.Entry<String, Set<Integer>> e : candidates.entrySet()) {
                                    if (e.getValue() == candidates.get(f.name)) {
                                        continue;
                                    }

                                    e.getValue().remove(position);
                                    if (e.getValue().size() == 1) {
                                        result.set(new ArrayList<>(e.getValue()).get(0), e.getKey());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        readFile("input-day16.txt");

        int errorRate = getErrorRate();
        System.out.println(errorRate);
        discardTickets();

        List<String> labels = assignLabels();

        long result = 1;
        for (int i = 0; i < labels.size(); i++) {
            if(labels.get(i).contains("departure")) {
                result *= myTicket[i];
            }
        }

        System.out.println(result);
    }
}
