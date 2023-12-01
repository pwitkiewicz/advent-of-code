package aoc.year2021.day14;

import aoc.utility.Reader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day14 {
    private static Map<String, String> parseInput(final List<String> input) {
        var map = new HashMap<String, String>();

        input.stream().filter(s -> s.contains("->"))
                .map(s -> s.split(" -> "))
                .forEach(s -> map.put(s[0], s[1]));

        return map;
    }

    private static HashMap<String, Long> insertElements(final Map<String, Long> map,
                                                        final Map<String, String> dictionary,
                                                        final Map<Character, Long> letters) {
        var nextMap = new HashMap<String, Long>();

        for (var entry : map.entrySet()) {
            if (entry.getValue() > 0) {
                var newLetter = dictionary.get(entry.getKey());
                var count = entry.getValue();
                var left = entry.getKey().charAt(0);
                var right = entry.getKey().charAt(1);

                nextMap.merge(left + newLetter, count, Long::sum);
                nextMap.merge(newLetter + right, count, Long::sum);
                letters.merge(newLetter.charAt(0), count, Long::sum);
            }
        }

        return nextMap;
    }

    public static void main(String[] args) {
        var input = Reader.readLinesAsStrings("input");
        var template = input.get(0);
        var dictionary = parseInput(input);
        var pairs = new HashMap<String, Long>();
        var letters = new HashMap<Character, Long>();

        letters.put(template.charAt(0), 1L);
        for (int i = 1; i < template.length(); i++) {
            var substring = template.substring(i - 1, i + 1);
            pairs.merge(substring, 1L, Long::sum);
            letters.merge(template.charAt(i), 1L, Long::sum);
        }

        for (int i = 0; i < 40; i++) {
            pairs = insertElements(pairs, dictionary, letters);
        }

        var frequencies = letters.values()
                .stream()
                .sorted(Long::compareTo)
                .collect(Collectors.toList());

        System.out.println(frequencies.get(frequencies.size() -1) - frequencies.get(0));
    }
}
