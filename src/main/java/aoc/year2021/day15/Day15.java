package aoc.year2021.day15;

import aoc.utility.Reader;

import java.util.*;

public class Day15 {
    private static Optional<Edge> getEdge(final List<String> input,
                                          final Point start,
                                          final Point end) {
        try {
            var length = Character.getNumericValue(input.get(end.x()).charAt(end.y()));
            return Optional.of(new Edge(start, end, length));
        } catch (IndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    private static void parseInput(final List<String> input,
                                   final Map<Point, List<Edge>> adjacencyMap) {
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                var point = new Point(i, j);
                var edgeList = new ArrayList<Edge>();

                for (int k = -1; k <= 1; k += 2) {
                    getEdge(input, point, new Point(i + k, j)).ifPresent(edgeList::add);
                    getEdge(input, point, new Point(i, j + k)).ifPresent(edgeList::add);
                }

                adjacencyMap.put(point, edgeList);
            }
        }
    }

    private static void findPath(final Map<Point, List<Edge>> adjacencyMap,
                                 final Point end) {
        var start = new Point(0, 0);
        var visited = new HashSet<Point>();
        var distances = new HashMap<Point, Integer>();

        for (Point p : adjacencyMap.keySet()) {
            distances.put(p, Integer.MAX_VALUE);
        }

        var queue = new PriorityQueue<Point>(100, Comparator.comparingInt(distances::get));

        queue.add(start);
        distances.put(start, 0);

        while (!queue.isEmpty()) {
            Point current = queue.remove();

            if (!visited.contains(current)) {
                visited.add(current);
                if (current.equals(end)) {
                    System.out.println("DISTANCE: " + distances.get(end));
                }

                for (var edge : adjacencyMap.get(current)) {
                    var nextPoint = edge.end();

                    if (distances.get(current) + edge.length() < distances.get(nextPoint)) {
                        distances.put(nextPoint, distances.get(current) + edge.length());

                        queue.add(nextPoint);
                    }
                }
            }
        }
    }

    private static List<String> multiplyInput(final List<String> input,
                                              final int factor) {
        var temp = new ArrayList<String>();

        for (int i = 0; i < input.size(); i++) {
            var sb = new StringBuilder(input.get(i));
            for (int j = 1; j < factor; j++) {
                for (int k = 0; k < input.get(i).length(); k++) {
                    char c = input.get(i).charAt(k);
                    c += j;
                    if (c >= 58) c -= 9;
                    sb.append(c);
                }
            }
            temp.add(i, sb.toString());
        }

        var output = new ArrayList<>(temp);

        for (int i = 1; i < factor; i++) {
            for (String s : temp) {
                var chars = s.toCharArray();
                for (int k = 0; k < chars.length; k++) {
                    chars[k] += i;
                    if (chars[k] >= 58) chars[k] -= 9;
                }
                output.add(new String(chars));
            }
        }

        return output;
    }

    public static void main(String[] args) {
        var input = Reader.readLinesAsStrings("input");
        var end = new Point(input.size() - 1, input.get(0).length() - 1);
        var adjacencyMap = new HashMap<Point, List<Edge>>();

        input = multiplyInput(input, 5);
        parseInput(input, adjacencyMap);
        findPath(adjacencyMap, end);
    }
}
