package com.day20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    private static class Tile {
        int num;
        int x;
        int y;
        char[][] array;

        public Tile(int n, char[][] arr) {
            num = n;
            array = arr;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Tile tile = (Tile) o;
            return num == tile.num;
        }

        @Override
        public int hashCode() {
            return Objects.hash(num);
        }

        public int fit(Tile t) {
            if (String.valueOf(t.array[t.array.length - 1]).equals(String.valueOf(array[0]))) {
                return 1;
            }

            if (String.valueOf(t.array[0]).equals(String.valueOf(array[array.length - 1]))) {
                return 3;
            }

            boolean fitsRight = true;
            boolean fitsLeft = true;

            for (int i = 0; i < array.length; i++) {
                if (array[i][0] != t.array[i][t.array[i].length - 1]) {
                    fitsLeft = false;
                }
                if (array[i][array.length - 1] != t.array[i][0]) {
                    fitsRight = false;
                }
            }

            if (fitsRight) {
                return 2;
            }

            if (fitsLeft) {
                return 4;
            }

            return 0;
        }

        public Tile flipH() {
            char[][] flipped = new char[array.length][];

            for (int i = 0; i < array.length; i++) {
                flipped[i] = new char[array[i].length];
            }

            for (int i = 0; i < array[0].length; i++) {
                for (int j = 0; j < array.length; j++) {
                    flipped[j][i] = array[array.length - j - 1][i];
                }
            }
            return new Tile(num, flipped);
        }

        public Tile flipV() {
            char[][] flipped = new char[array.length][];

            for (int i = 0; i < array.length; i++) {
                flipped[i] = new char[array[i].length];
            }

            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array[i].length; j++) {
                    flipped[i][j] = array[i][array[i].length - j - 1];
                }
            }

            return new Tile(num, flipped);
        }

        public Tile rotate() {
            return this.flipH().flipV();
        }

        public Tile rotate90() {
            char[][] rotated = new char[array.length][];

            for (int i = 0; i < array.length; i++) {
                rotated[i] = new char[array[i].length];
            }

            for (int i = 0; i < array.length; ++i) {
                for (int j = 0; j < array.length; ++j) {
                    rotated[i][j] = array[array.length - j - 1][i];
                }
            }

            return new Tile(num, rotated);
        }

        public Tile random() {
            Random rn = new Random();
            int num = rn.nextInt() % 3;
            if (num == 0) {
                return this.flipH();
            } else if (num == 1) {
                return this.flipV();
            } else {
                return this.rotate();
            }
        }
    }

    public static List<Tile> readFile(String filename) {
        List<Tile> input = new ArrayList<>();

        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();

            while (line != null) {
                int num = Integer.parseInt(line.split(" ")[1].replaceAll(":", ""));
                char[][] arr = new char[10][];
                int i = 0;

                line = reader.readLine();

                while (line != null && !line.equals("")) {
                    arr[i++] = line.toCharArray();
                    line = reader.readLine();
                }

                input.add(new Tile(num, arr));
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return input;
    }

    private static HashSet<Tile> solveJigsaw(List<Tile> tiles) {
        Tile startingTile = tiles.get(0);
        startingTile.x = 0;
        startingTile.y = 0;

        HashSet<Tile> usedTiles = new HashSet<>();
        HashSet<Tile> candidates = new HashSet<>(tiles);
        candidates.remove(startingTile);
        usedTiles.add(startingTile);

        while (usedTiles.size() != tiles.size()) {
            int currentSize = usedTiles.size();
            loop:
            for (Tile t : usedTiles) {
                for (Tile candidate : candidates) {
                    if (usedTiles.contains(candidate)) {
                        continue;
                    }
                    int fit = t.fit(candidate);
                    int fitV = t.fit(candidate.flipV());
                    int fitH = t.fit(candidate.flipH());
                    int fitR180 = t.fit(candidate.rotate());

                    if (fitV != 0) {
                        candidate = candidate.flipV();
                        fit = fitV;
                    }
                    if (fitH != 0) {
                        candidate = candidate.flipH();
                        fit = fitH;
                    }
                    if (fitR180 != 0) {
                        candidate = candidate.rotate();
                        fit = fitR180;
                    }
                    if (fit != 0) {
                        if (fit == 1) {
                            candidate.x = t.x;
                            candidate.y = t.y + 1;
                        }
                        if (fit == 2) {
                            candidate.x = t.x + 1;
                            candidate.y = t.y;
                        }
                        if (fit == 3) {
                            candidate.x = t.x;
                            candidate.y = t.y - 1;
                        }
                        if (fit == 4) {
                            candidate.x = t.x - 1;
                            candidate.y = t.y;
                        }
                        usedTiles.add(candidate);
                        candidates.remove(candidate);
                        break loop;
                    }
                }
            }

            if (usedTiles.size() == 1) {
                usedTiles.remove(startingTile);
                startingTile = startingTile.random();
                usedTiles.add(startingTile);
            }

            if (currentSize == usedTiles.size()) {
                HashSet<Tile> rotatedCandidates = new HashSet<>();
                for (Tile t : candidates) {
                    rotatedCandidates.add(t.rotate90());
                }
                candidates = rotatedCandidates;
            }
        }

        return usedTiles;
    }

    private static long calculateAnswerPart1(HashSet<Tile> tiles) {
        int minX = 0;
        int minY = 0;
        int maxX = 0;
        int maxY = 0;

        for (Tile t : tiles) {
            if (t.x <= minX) {
                minX = t.x;
            }
            if (t.x >= maxX) {
                maxX = t.x;
            }
            if (t.y >= maxY) {
                maxY = t.y;
            }
            if (t.y <= minY) {
                minY = t.y;
            }
        }

        long result = 1;

        for (Tile t : tiles) {
            if (t.x == minX && t.y == minY) {
                result *= t.num;
            }
            if (t.x == maxX && t.y == minY) {
                result *= t.num;
            }
            if (t.x == minX && t.y == maxY) {
                result *= t.num;
            }
            if (t.x == maxX && t.y == maxY) {
                result *= t.num;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        List<Tile> tiles = readFile("input-day20.txt");

        // we give first tile position 0,0
        HashSet<Tile> solvedJigsaw = solveJigsaw(tiles);
        long result = calculateAnswerPart1(solvedJigsaw);
        System.out.println(result);


    }
}
