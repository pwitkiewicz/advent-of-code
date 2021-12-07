package aoc.day7;

import aoc.utility.Reader;

import java.util.List;
import java.util.stream.Collectors;

public class Day7 {
    private static int getSum(final List<Integer> heights, final int median) {
        var sum = 0;

        for (int i : heights) {
            var tempSum = 0;
            for (int j = 1; j <= Math.abs(median - i); j++) {
                tempSum += j;
            }
            sum += tempSum;
        }

        return sum;
    }

    private static int getMinSum(final int sum, final int median, final List<Integer> sortedHeights) {
        var newPosition = median + 1;
        var newSum = getSum(sortedHeights, newPosition);
        var minSum = Math.min(newSum, sum);

        while (newPosition - median < 150) {
            newPosition++;
            newSum = getSum(sortedHeights, newPosition);
            if(newSum < minSum) minSum = newSum;
        }

        return minSum;
    }

    public static void main(String[] args) {
        var heights = Reader.readCsvAsIntegers("input");
        var sortedHeights = heights.stream().sorted(Integer::compareTo).collect(Collectors.toList());
        var size = sortedHeights.size();
        var median = size % 2 != 0 ?
                sortedHeights.get((size + 1) / 2 - 1) :
                (sortedHeights.get(size / 2 - 1) + sortedHeights.get(size / 2)) / 2;

        var sum = getSum(sortedHeights, median);
        var minSum = getMinSum(sum, median, sortedHeights);

        System.out.printf("Sum %d at median position, minimum sum %d", sum, minSum);
    }
}
