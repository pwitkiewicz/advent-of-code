package aoc.year2021.day9;

import aoc.utility.Reader;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

public class Day9 {
    private static Optional<Point> getPointWithValue(final List<String> input, final Point point) {
        try {
            var value = Character.getNumericValue(input.get(point.x()).charAt(point.y()));
            return Optional.of(new Point(point.x(), point.y(), value));
        } catch (IndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    private static boolean isLowPoint(final List<String> input, final Point point) {
        final var adjacentPointValues = new ArrayList<Integer>();

        for (int i = -1; i <= 1; i += 2) {
            getPointWithValue(input, new Point(point.x() + i, point.y(), -1)).
                    ifPresent(p -> adjacentPointValues.add(p.value()));

            getPointWithValue(input, new Point(point.x(), point.y() + i, -1))
                    .ifPresent(p -> adjacentPointValues.add(p.value()));
        }

        return adjacentPointValues.stream().noneMatch(t -> t <= point.value());
    }

    private static int findSumOfLowPoints(final List<String> input) {
        final var sum = new ArrayList<Integer>();

        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                final var point = new Point(i, j, Character.getNumericValue(input.get(i).charAt(j)));
                if (isLowPoint(input, point)) {
                    sum.add(point.value() + 1);
                }
            }
        }

        return sum.stream().reduce(Integer::sum).orElseThrow();
    }

    private static void enqueuePoint(final List<String> input,
                                     final Queue<Point> queue,
                                     final Set<Point> visited,
                                     final Point point) {
        getPointWithValue(input, point).ifPresent(p -> {
            if (p.value() != 9 && !visited.contains(p)) {
                queue.add(p);
            }
        });
    }

    private static long findBasinSize(final List<String> input, final Set<Point> visited, final Point startingPoint) {
        var queue = new LinkedList<Point>();
        var size = 0L;
        queue.add(startingPoint);

        while (!queue.isEmpty()) {
            var point = queue.pop();

            if (!visited.contains(point)) {
                size++;
                visited.add(point);

                for (int i = -1; i <= 1; i += 2) {
                    enqueuePoint(input, queue, visited, new Point(point.x() + i, point.y(), -1));
                    enqueuePoint(input, queue, visited, new Point(point.x(), point.y() + i, -1));
                }
            }
        }

        return size;
    }

    private static long findMultiplyOfThreeLargestBasins(final List<String> input) {
        Set<Point> visited = new HashSet<>();
        var sizes = new ArrayList<Long>();

        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                var point = new Point(i, j, Character.getNumericValue(input.get(i).charAt(j)));
                if (!visited.contains(point) && point.value() != 9) {
                    sizes.add(findBasinSize(input, visited, point));
                }
            }
        }

        var biggestSizes = sizes.stream()
                .sorted(Comparator.comparingLong(Long::longValue).reversed())
                .limit(3)
                .collect(Collectors.toList());

        var multiply = 1L;
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
