package aoc.year2020.day11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;


public class Main {
    private static char[][] readFile(String fileName) {
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

        char[][] result = new char[input.size()][];
        for (int i = 0; i < input.size(); i++) {
            result[i] = input.get(i).toCharArray();
        }

        return result;
    }

    private static char[] getSymbols(char[][] input, int x, int y) {
        char[] result = new char[8];

        for(int i = 0; i < 3; i++) {
            int posX = x-1;
            int posY = y-1+i;
            try {
                result[i] = input[posX][posY];
                while(result[i] == '.' && i == 0) {
                    result[i] = input[--posX][--posY];
                }
                while(result[i] == '.' && i == 1) {
                    result[i] = input[--posX][posY];
                }
                while(result[i] == '.' && i == 2) {
                    result[i] = input[--posX][++posY];
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                result[i] = '.';
            }
        }

        try {
            int posX = x;
            int posY = y-1;
            result[3] = input[posX][posY];
            while(result[3] == '.') {
                result[3] = input[posX][--posY];
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            result[3] = '.';
        }

        try {
            int posX = x;
            int posY = y+1;
            result[4] = input[posX][posY];
            while(result[4] == '.') {
                result[4] = input[posX][++posY];
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            result[4] = '.';
        }

        int j = 5;
        for(int i = 0; i < 3; i++) {
            int posX = x+1;
            int posY = y-1+i;
            try {
                result[j] = input[posX][posY];
                while(result[j] == '.' && i == 0) {
                    result[j] = input[++posX][--posY];
                }
                while(result[j] == '.' && i == 1) {
                    result[j] = input[++posX][posY];
                }
                while(result[j] == '.' && i == 2) {
                    result[j] = input[++posX][++posY];
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                result[j] = '.';
            }
            j++;
        }

        return result;
    }

    private static int occupySeats(char[][] input) {
        char[] symbols = new char[8];
        int currentOccupation = 0;

        char[][] initialInput = new char[input.length][];
        for(int i = 0; i < input.length; i++) {
            initialInput[i] = input[i].clone();
        }

        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                if(input[i][j] == '#' || input[i][j] == 'L') {
                    int neighbours = 0;
                    symbols = getSymbols(initialInput, i, j);

                    for(char symbol : symbols) {
                        if(symbol == '#') {
                            neighbours++;
                        }
                    }

                    if(neighbours == 0 && input[i][j] == 'L') {
                        input[i][j] = '#';
                    }

                    if(neighbours >= 5 && input[i][j] == '#') {
                        input[i][j] = 'L';
                    }

                    if(input[i][j] == '#') {
                        currentOccupation++;
                    }
                }
            }
        }

        return currentOccupation;
    }

    public static void main(String[] args) {
        char[][] input = readFile("input-day11.txt");

        int occupation = 0;
        while(true) {
            int newOccupation = occupySeats(input);

            if(newOccupation == occupation) {
                System.out.println(occupation);
                return;
            }
            occupation = newOccupation;
        }
    }
}
