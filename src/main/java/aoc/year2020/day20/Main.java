package aoc.year2020.day20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    public static char[][] arrayFlipH(char[][] array) {
        char[][] flipped = new char[array.length][];

        for (int i = 0; i < array.length; i++) {
            flipped[i] = new char[array[i].length];
        }

        for (int i = 0; i < array[0].length; i++) {
            for (int j = 0; j < array.length; j++) {
                flipped[j][i] = array[array.length - j - 1][i];
            }
        }
        return flipped;
    }

    public static char[][] arrayFlipV(char[][] array) {
        char[][] flipped = new char[array.length][];

        for (int i = 0; i < array.length; i++) {
            flipped[i] = new char[array[i].length];
        }

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                flipped[i][j] = array[i][array[i].length - j - 1];
            }
        }

        return flipped;
    }

    public static char[][] arrayRotate90(char[][] array) {
        char[][] rotated = new char[array.length][];

        for (int i = 0; i < array.length; i++) {
            rotated[i] = new char[array[i].length];
        }

        for (int i = 0; i < array.length; ++i) {
            for (int j = 0; j < array.length; ++j) {
                rotated[i][j] = array[array.length - j - 1][i];
            }
        }

        return rotated;
    }

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
            char[][] flipped = arrayFlipH(array);
            return new Tile(num, flipped);
        }

        public Tile flipV() {
            char[][] flipped = arrayFlipV(array);
            return new Tile(num, flipped);
        }

        public Tile rotate() {
            return this.flipH().flipV();
        }

        public Tile rotate90() {
            char[][] rotated = arrayRotate90(array);
            return new Tile(num, rotated);
        }

        public Tile random() {
            Random rn = new Random();
            int num = rn.nextInt() % 4;
            if (num == 0) {
                return this.flipH();
            } else if (num == 1) {
                return this.flipV();
            } else if (num == 2) {
                return this.rotate90();
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

            // if loop not moving forward do a random operation on first tile
            if (usedTiles.size() == 1) {
                usedTiles.remove(startingTile);
                startingTile = startingTile.random();
                usedTiles.add(startingTile);
            }

            // if loop stuck in the middle rotate unused tiles by 90 degrees
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

    private static class Image {
        char[][] image;

        public Image(HashSet<Tile> tiles) {
            int size = (int) (Math.sqrt(tiles.size()) * 8);

            // init array
            image = new char[size][];
            for (int i = 0; i < image.length; i++) {
                image[i] = new char[size];
            }

            // find left upper corner tile and go from there
            int minX = Integer.MAX_VALUE;
            int maxY = Integer.MIN_VALUE;
            for (Tile t : tiles) {
                if (t.x <= minX) {
                    minX = t.x;
                }
                if (t.y >= maxY) {
                    maxY = t.y;
                }
            }

            int currentX = minX;
            int currentY = maxY;
            int imageX = 0;
            int imageY = 0;

            for (imageX = 0; imageX < tiles.size(); imageX++, currentX++) {
                currentY = maxY;
                for (imageY = 0; imageY < tiles.size(); imageY++, currentY--) {
                    for (Tile t : tiles) {
                        if (t.x == currentX && t.y == currentY) {
                            int k = 1;
                            for (int i = 8 * imageY; i < 8 + imageY * 8; i++) {
                                int l = 1;
                                for (int j = 8 * imageX; j < 8 + imageX * 8; j++) {
                                    image[i][j] = t.array[k][l++];
                                }
                                k++;
                            }
                        }
                    }
                }
            }
        }

        private void flipH() {
            image = arrayFlipH(image);
        }

        private void flipV() {
            image = arrayFlipV(image);
        }

        public void rotate() {
            flipH();
            flipV();
        }

        public void rotate90() {
            image = arrayRotate90(image);
        }

        public void random() {
            Random rn = new Random();
            int num = rn.nextInt() % 4;
            if (num == 0) {
                flipH();
            } else if (num == 1) {
                flipV();
            } else if (num == 2) {
                rotate90();
            } else {
                rotate();
            }
        }

        private boolean findPattern() {
            boolean foundAndReplaced = false;
            for (int i = 1; i < image.length-1; i++) {
                for (int j = 19; j < image.length; j++) {
                    if (image[i][j] == '#' && image[i - 1][j - 1] == '#' && image[i][j - 1] == '#'
                            && image[i][j - 2] == '#' && image[i + 1][j - 3] == '#'
                            && image[i + 1][j - 6] == '#' && image[i][j - 7] == '#' && image[i][j - 8] == '#' && image[i + 1][j - 9] == '#'
                            && image[i + 1][j - 12] == '#' && image[i][j - 13] == '#' && image[i][j - 14] == '#' && image[i + 1][j - 15] == '#'
                            && image[i + 1][j - 18] == '#' && image[i][j - 19] == '#') {
                        foundAndReplaced = true;
                        image[i][j] = 'O';
                        image[i - 1][j - 1] = 'O';
                        image[i][j - 1] = 'O';
                        image[i][j - 2] = 'O';
                        image[i + 1][j - 3] = 'O';
                        image[i + 1][j - 6] = 'O';
                        image[i][j - 7] = 'O';
                        image[i][j - 8] = 'O';
                        image[i + 1][j - 9] = 'O';
                        image[i + 1][j - 12] = 'O';
                        image[i][j - 13] = 'O';
                        image[i][j - 14] = 'O';
                        image[i + 1][j - 15] = 'O';
                        image[i + 1][j - 18] = 'O';
                        image[i][j - 19] = 'O';
                    }
                }
            }
            return foundAndReplaced;
        }

        private void findDragons() {
            while(!findPattern()) {
                random();
            }
        }

        public int roughness() {
            findDragons();
            int result = 0;
            for (int i = 0; i < image.length; i++) {
                for (int j = 0; j < image.length; j++) {
                    if (image[i][j] == '#') {
                        result++;
                    }
                }
            }
            return result;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < image.length; i++) {
                for (int j = 0; j < image.length; j++) {
                    sb.append(image[i][j]);
                }
                sb.append("\n");
            }
            return sb.toString();
        }
    }

    public static void main(String[] args) {
        List<Tile> tiles = readFile("input-day20.txt");

        // we give first tile position 0,0
        HashSet<Tile> solvedJigsaw = solveJigsaw(tiles);
        long resultPart1 = calculateAnswerPart1(solvedJigsaw);
        System.out.println(resultPart1);

        // part 2 - put images together
        Image img = new Image(solvedJigsaw);
        int resultPart2 = img.roughness();
        System.out.println(img);
        System.out.println(resultPart2);
    }
}
