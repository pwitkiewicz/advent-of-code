package aoc.year2021.day13;

import aoc.utility.Reader;

import java.util.*;

public class Day13 {
    private static Set<Point> parsePoints(final List<String> input) {
        final var points = new HashSet<Point>();

        input.stream().filter(s -> s.contains(","))
                .map(s -> s.split(","))
                .forEach(s -> {
                    var x = Integer.parseInt(s[0]);
                    var y = Integer.parseInt(s[1]);
                    points.add(new Point(x, y));
                });

        return points;
    }

    private static List<Line> parseFolds(final List<String> input) {
        final var foldLines = new ArrayList<Line>();

        input.stream().filter(s -> s.contains("="))
                .map(s -> s.split("="))
                .forEach(s -> {
                    var axis = s[0].split("\\s+")[2];
                    var placement = Integer.parseInt(s[1]);
                    switch (axis) {
                        case "x" -> foldLines.add(new Line(Axis.X, placement));
                        case "y" -> foldLines.add(new Line(Axis.Y, placement));
                    }
                });

        return foldLines;
    }

    private static Set<Point> fold(final Set<Point> points, final Line line) {
        final var foldedPoints = new HashSet<Point>();

        for (Point p : points) {
            switch (line.axis()) {
                case X -> {
                    if (p.x() > line.value()) {
                        var newX = p.x() - 2 * (p.x() - line.value());
                        foldedPoints.add(new Point(newX, p.y()));
                    }
                    else if(p.x() < line.value()) {
                        foldedPoints.add(p);
                    }
                }
                case Y -> {
                    if (p.y() > line.value()) {
                        var newY = p.y() - 2 * (p.y() - line.value());
                        foldedPoints.add(new Point(p.x(), newY));
                    }
                    else if(p.y() < line.value()) {
                        foldedPoints.add(p);
                    }
                }
            }
        }

        return foldedPoints;
    }

    private static void printPoints(final Set<Point> points) {
        var output = new char[10][50];

        for(Point p : points) {
            output[p.y()][p.x()] = 'X';
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 50; j++) {
                if(output[i][j] != 'X') {
                    output[i][j] = ' ';
                }
            }
            System.out.println(new String(output[i]));
        }
    }

    public static void main(String[] args) {
        var input = Reader.readLinesAsStrings("input");
        var points = parsePoints(input);
        var lines = parseFolds(input);

        for (Line l : lines) {
            points = fold(points, l);
        }

        printPoints(points);
    }
}
