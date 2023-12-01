package aoc.year2021.day4;

import lombok.Data;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
public class BoardRow {
    private Set<Integer> numbers;
    private Set<Integer> markedNumbers;

    public static BoardRow of(final Collection<Integer> numbers) {
        BoardRow row = new BoardRow();
        row.numbers = new HashSet<>(numbers);
        row.markedNumbers = new HashSet<>();
        return row;
    }

    public boolean markNumber(final int number) {
        if (numbers.contains(number)) {
            markedNumbers.add(number);
            return numbers.equals(markedNumbers);
        }

        return false;
    }

    public Set<Integer> getUnmarkedNumbers() {
        numbers.removeAll(markedNumbers);
        return numbers;
    }
}
