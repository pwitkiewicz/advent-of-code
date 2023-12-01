package aoc.year2021.day10;

import aoc.utility.Reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class Day10 {
    private final static List<String> completions = new ArrayList<>();

    private static long scoreTable(final char c) {
        var score = 0L;

        switch (c) {
            case ')' -> score = 3L;
            case ']' -> score = 57L;
            case '}' -> score = 1197L;
            case '>' -> score = 25137L;
        }

        return score;
    }

    private static void findCompletions(final Stack<Character> stack) {
        var stringBuilder = new StringBuilder();

        while (!stack.isEmpty()) {
            var c = stack.pop();
            switch (c) {
                case '(' -> stringBuilder.append(')');
                case '[' -> stringBuilder.append(']');
                case '{' -> stringBuilder.append('}');
                case '<' -> stringBuilder.append('>');
            }
        }

        completions.add(stringBuilder.toString());
    }

    private static char findIncorrectCharacter(final String input) {
        var stack = new Stack<Character>();

        for (char c : input.toCharArray()) {
            if (c == '(' || c == '[' || c == '{' || c == '<') {
                stack.push(c);
                continue;
            }
            var openingBracket = stack.pop();

            if (!(openingBracket == '(' && c == ')' ||
                    openingBracket == '[' && c == ']' ||
                    openingBracket == '{' && c == '}' ||
                    openingBracket == '<' && c == '>')) {
                return c;
            }
        }

        findCompletions(stack);
        return '.';
    }

    private static List<Long> completionScores() {
        var scores = new ArrayList<Long>();

        for (String s : completions) {
            var score = 0L;

            for (char c : s.toCharArray()) {
                score *= 5;
                switch (c) {
                    case ')' -> score += 1L;
                    case ']' -> score += 2L;
                    case '}' -> score += 3L;
                    case '>' -> score += 4L;
                }
            }

            scores.add(score);
        }

        return scores.stream().sorted(Long::compareTo).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        var input = Reader.readLinesAsStrings("input");
        var sum = 0L;

        for (String s : input) {
            sum += scoreTable(findIncorrectCharacter(s));
        }

        var scores = completionScores();

        System.out.println(sum);
        System.out.println(scores.get((scores.size() - 1) / 2));
    }
}
