package aoc.day9;

import aoc.utility.Reader;

import java.util.*;
import java.util.stream.Collectors;

public class Day9 {
    private static final int OUT_OF_BOUNDS_VALUE = 9;

    private static int tryGetPoint(final List<String> input,
                                   final int x,
                                   final int y,
                                   final int diffx,
                                   final int diffy) {
        try {
            return Character.getNumericValue(input.get(x + diffx).charAt(y + diffy));
        } catch (IndexOutOfBoundsException e) {
            return OUT_OF_BOUNDS_VALUE;
        }
    }

    private static boolean isLowPoint(final List<String> input, final int x, final int y) {
        var adjacentPoints = new ArrayList<Integer>();
        for (int i = -1; i <= 1; i += 2) {
            adjacentPoints.add(tryGetPoint(input, x, y, i, 0));
            adjacentPoints.add(tryGetPoint(input, x, y, 0, i));
        }

        var point = Character.getNumericValue(input.get(x).charAt(y));
        return adjacentPoints.stream().noneMatch(t -> t <= point);
    }

    private static int findSumOfLowPoints(final List<String> input) {
        var sum = new ArrayList<Integer>();

        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                if (isLowPoint(input, i, j)) {
                    var point = input.get(i).charAt(j);
                    sum.add(Character.getNumericValue(point) + 1);
                }
            }
        }

        return sum.stream().reduce(Integer::sum).orElseThrow();
    }

    private static void enqueuePoint(final List<String> input, final Queue<Integer> queue, final boolean[][] visited, final int x, final int y,
                                     final int diffx, final int diffy) {
        var currentx = x + diffx;
        var currenty = y + diffy;

        var temp = tryGetPoint(input, x, y, diffx, diffy);

        if (temp != OUT_OF_BOUNDS_VALUE && !visited[currentx][currenty]) {
            queue.add((currentx) * 1000 + currenty * 10);
        }
    }

    private static long findBasinSize(final List<String> input, final boolean[][] visited, int x, int y) {
        var queue = new LinkedList<Integer>();
        var point = x * 1000 + y * 10;
        var size = 0L;
        queue.add(point);

        while (!queue.isEmpty()) {
            var currentPoint = queue.pop();
            int currentX = currentPoint / 1000;
            int currentY = (currentPoint - currentX * 1000) / 10;

            if (visited[currentX][currentY]) continue;

            var value = Character.valueOf(input.get(currentX).charAt(currentY));

            visited[currentX][currentY] = true;

            size++;

            for (int i = -1; i <= 1; i += 2) {
                enqueuePoint(input, queue, visited, currentX, currentY, i, 0);
                enqueuePoint(input, queue, visited, currentX, currentY, 0, i);
            }
        }

        return size;
    }

    private static long findMultiplyOfThreeLargestBasins(final List<String> input) {
        boolean[][] visited = new boolean[input.size()][input.get(0).length()];
        var sizes = new ArrayList<Long>();

        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                if (!visited[i][j] && Character.getNumericValue(input.get(i).charAt(j)) != 9) {
                    sizes.add(findBasinSize(input, visited, i, j));
                }
            }
        }

        var biggestSizes = sizes.stream()
                .sorted(Comparator.comparingLong(Long::longValue).reversed())
                .limit(3)
                .collect(Collectors.toList());

        var multiply = 1;
        for (long value : biggestSizes) {
            multiply *= value;
        }
        return multiply;
    }

    public static void main(String[] args) {
        var input = Reader.readLinesAsStrings("input");
        var sum = findSumOfLowPoints(input);

        System.out.println(sum);
        System.out.println(findMultiplyOfThreeLargestBasins(input));
    }
}
