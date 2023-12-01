package aoc.year2021.day11;

import aoc.utility.Reader;

public class Day11 {
    private static int[][] parseInput(String filename) {
        var input = Reader.readLinesAsStrings(filename);
        int[][] levels = new int[input.size()][];

        for (int i = 0; i < input.size(); i++) {
            var chars = input.get(i).toCharArray();
            levels[i] = new int[chars.length];
            for (int j = 0; j < chars.length; j++) {
                levels[i][j] = Character.getNumericValue(chars[j]);
            }
        }

        return levels;
    }

    private static void increaseLevels(final int[][] levels) {
        for (int i = 0; i < levels.length; i++) {
            for (int j = 0; j < levels[i].length; j++) {
                levels[i][j] += 1;
            }
        }
    }

    private static void tryIncrease(final int[][] levels, final boolean[][] hasFlashed, final int x, final int y) {
        try {
            levels[x][y] = levels[x][y] + 1;
            if(levels[x][y] > 9 && !hasFlashed[x][y]) {
                flash(levels, hasFlashed, x, y);
            }
        } catch (IndexOutOfBoundsException e) {
            // out of bounds, do nothing
        }
    }

    private static void flash(final int[][] levels, final boolean[][] hasFlashed, final int x, final int y) {
        hasFlashed[x][y] = true;

        for (int i = -1; i <= 1; i += 2) {
            tryIncrease(levels, hasFlashed, x + i, y);
            tryIncrease(levels, hasFlashed, x, y + i);
            tryIncrease(levels, hasFlashed, x + i, y + i);
        }
        tryIncrease(levels, hasFlashed, x + 1, y - 1);
        tryIncrease(levels, hasFlashed, x - 1, y + 1);
    }

    private static int doStep(final int[][] levels) {
        var hasFlashed = new boolean[levels.length][levels[0].length];
        var count = 0;
        increaseLevels(levels);

        for (int i = 0; i < levels.length; i++) {
            for (int j = 0; j < levels[i].length; j++) {
                if(levels[i][j] > 9 && !hasFlashed[i][j]) {
                    flash(levels, hasFlashed, i, j);
                }
            }
        }

        for (int i = 0; i < levels.length; i++) {
            for (int j = 0; j < levels[i].length; j++) {
                if(hasFlashed[i][j]) {
                    levels[i][j] = 0;
                    count += 1;
                }
            }
        }

        return count;
    }

    public static void main(String[] args) {
        var levels = parseInput("input");
        var sum = 0;
        var steps = 0;

        while(true) {
            var howManyFlashed = doStep(levels);

            if(steps < 100) {
                sum += howManyFlashed;
            }

            steps++;

            if(howManyFlashed == 100) {
                System.out.printf("Synchronized at %d steps%n", steps);
                break;
            }
        }

        System.out.printf("After 100 steps there was %d flashes", sum);
    }
}
