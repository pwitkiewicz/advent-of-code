package aoc.day12;

import aoc.utility.Reader;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day12 {
    private final static Set<List<String>> PATHS = new HashSet<>();

    private static void addCave(final String address1, final String address2, final Map<String, Cave> caves) {
        if (caves.containsKey(address1)) {
            caves.get(address1).addAdjacent(address2);
        } else {
            final var cave = Cave.of(address1);
            cave.addAdjacent(address2);
            caves.put(address1, cave);
        }
    }

    private static Map<String, Cave> parseInput(final String filename) {
        final var caves = new HashMap<String, Cave>();
        final var input = Reader.readLinesAsStrings(filename);

        for (String s : input) {
            var addresses = s.split("-");
            addCave(addresses[0], addresses[1], caves);
            addCave(addresses[1], addresses[0], caves);
        }

        return caves;
    }

    private static boolean hasAnySmallCaveBeenVisitedTwice(final List<String> path, final Map<String, Cave> caves) {
        return path.stream()
                .filter(c -> caves.get(c).size() == CaveSize.SMALL)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .values()
                .stream()
                .anyMatch(x -> x == 2);
    }

    private static void findPath(final List<String> path, final Map<String, Cave> caves) {
        final var cave = caves.get(path.get(path.size() - 1));

        if (cave.address().equals("end")) {
            PATHS.add(path);
            return;
        }

        for (String c : cave.adjacentCaves()) {
            if (c.equals("start")) continue;
            final var currentCave = caves.get(c);

            if (currentCave.size() == CaveSize.BIG ||
                    currentCave.size() == CaveSize.SMALL && (!path.contains(c) || !hasAnySmallCaveBeenVisitedTwice(path, caves))) {
                var currentPath = new ArrayList<>(path);
                currentPath.add(c);
                findPath(currentPath, caves);
            }
        }
    }

    private static int findAllPaths(final Map<String, Cave> caves) {
        final var startingPoint = caves.get("start");
        final var currentPath = new ArrayList<String>();
        currentPath.add("start");

        for (String cave : startingPoint.adjacentCaves()) {
            final var path = new ArrayList<>(currentPath);
            path.add(cave);
            findPath(path, caves);
        }

        return PATHS.size();
    }

    public static void main(String[] args) {
        final var caves = parseInput("input");
        final var paths = findAllPaths(caves);

        System.out.println(paths);
    }
}