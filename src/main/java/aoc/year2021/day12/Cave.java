package aoc.year2021.day12;

import java.util.HashSet;
import java.util.Set;


record Cave(String address, Set<String> adjacentCaves, CaveSize size) {

    public static Cave of(final String address) {
        var size = address.equals(address.toLowerCase()) ? CaveSize.SMALL : CaveSize.BIG;
        return new Cave(address, new HashSet<>(), size);
    }

    public void addAdjacent(final String cave) {
        adjacentCaves.add(cave);
    }
}
