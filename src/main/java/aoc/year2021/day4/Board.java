package aoc.year2021.day4;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Board {
    private List<BoardRow> rows;
    private boolean won;

    public static Board of(final List<String> input) {
        Board board = new Board();
        board.setRows(new ArrayList<>());
        board.won = false;

        var verticalRows = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < input.size(); i++) {
            verticalRows.add(new ArrayList<>());
        }

        for (String s : input) {
            var list = Arrays.stream(s.trim().split("\\s+"))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            board.getRows().add(BoardRow.of(list));
            for (int j = 0; j < list.size(); j++) {
                verticalRows.get(j).add(list.get(j));
            }
        }

        for (var row : verticalRows) {
            board.getRows().add(BoardRow.of(row));
        }

        return board;
    }

    public boolean markNumber(final int number) {
        for (BoardRow row : rows) {
            if (row.markNumber(number)) {
                won = true;
            }
        }

        return won;
    }

    public int getScore() {
        return rows.stream()
                .map(BoardRow::getUnmarkedNumbers)
                .flatMap(Collection::stream)
                .reduce(0, Integer::sum) / 2;
    }
}
