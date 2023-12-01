package aoc.year2021.day4;

import aoc.utility.Reader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day4 {
    private static List<Integer> parseDrawnNumbers(final String input) {
        return Arrays.stream(input.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private static List<Board> parseBoards(final List<String> input) {
        final var boardsList = new ArrayList<Board>();

        for (int i = 6; i < input.size(); i += 6) {
            boardsList.add(Board.of(input.subList(i - 4, i + 1)));
        }

        return boardsList;
    }

    private static void playBingo(final List<Integer> drawnNumbers, final List<Board> boardsList) {
        for (int number : drawnNumbers) {
            for (int i = 0; i < boardsList.size(); i++) {
                var currentBoard = boardsList.get(i);

                if (!currentBoard.isWon() && currentBoard.markNumber(number)) {
                    var score = boardsList.get(i).getScore();
                    var string = String.format("winning board %d with score %d", i + 1, score * number);
                    System.out.println(string);
                }
            }
        }
    }

    public static void main(String[] args) {
        var input = Reader.readLinesAsStrings("input");
        var drawnNumbers = parseDrawnNumbers(input.get(0));
        var boardsList = parseBoards(input);

        playBingo(drawnNumbers, boardsList);
    }
}
