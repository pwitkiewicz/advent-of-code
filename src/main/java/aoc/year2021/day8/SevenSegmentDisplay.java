package aoc.year2021.day8;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Data
public class SevenSegmentDisplay {
    private char signalA;
    private char signalB;
    private char signalC;
    private char signalD;
    private char signalE;
    private char signalF;
    private char signalG;

    private List<Set<Character>> digits;
    private List<String> output;

    //  aaaaaaa
    // b       c
    // b       c
    //  ddddddd
    // e       f
    // e       f
    //  ggggggg

    public long calculateOutputValue() {
        var stringBuilder = new StringBuilder();

        for (String s : output) {
            var set = stringToCharSet(s);
            for (int i = 0; i < 10; i++) {
                if(set.equals(digits.get(i))) {
                    stringBuilder.append((char) (i + '0'));
                    break;
                }
            }
        }

        return Long.parseLong(stringBuilder.toString());
    }

    public static SevenSegmentDisplay solvePuzzle(final String input) {
        var result = new SevenSegmentDisplay();
        List<String> signals = Arrays.stream(input.split(" \\| ")[0].split("\\s+")).collect(Collectors.toList());
        result.output = Arrays.stream(input.split(" \\| ")[1].split("\\s+")).collect(Collectors.toList());

        signals.addAll(result.output);
        //first get obvious digits
        result.digits = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            result.digits.add(new HashSet<>());
        }

        result.digits.set(1, findDigit(signals, 2));
        result.digits.set(4, findDigit(signals, 4));
        result.digits.set(7, findDigit(signals, 3));
        result.digits.set(8, findDigit(signals, 7));

        // A = 7 - 1
        result.signalA = result.findSignal(7, 1, '0');
        result.findZero(signals);
        // D = 8 - 0
        result.signalD = result.findSignal(8, 0, '0');
        // B = 4 - 1 - D
        result.signalB = result.findSignal(4, 1, result.signalD);
        result.findNine(signals);
        // G = 9 - 4 - A
        result.signalG = result.findSignal(9, 4, result.signalA);
        result.findSix(signals);
        // E = 6 - 9
        result.signalE = result.findSignal(6, 9, '0');
        // C = 1 - 6
        result.signalC = result.findSignal(1, 6, '0');
        result.findSignalF();
        result.findTwo();
        result.findThree();
        result.findFive();

        return result;
    }

    private char findSignal(final int digit1, final int digit2, final char removeSignal) {
        var signal = new HashSet<>(digits.get(digit1));
        signal.removeAll(digits.get(digit2));
        if(removeSignal != '0') {
            signal.remove(removeSignal);
        }
        return signal.iterator().next();
    }

    // F = 1 - C
    private void findSignalF() {
        var signal = new HashSet<>(digits.get(1));
        signal.remove(signalC);
        signalF = signal.iterator().next();
    }

    /* zero have:
     *      - signal of length 6
     *      - two common symbols with digit 1
     *      - three common symbols with digit 4
     */
    private void findZero(final List<String> signals) {
        var possibleZeros = signals.stream().filter(s -> s.length() == 6).collect(Collectors.toList());

        for (String s : possibleZeros) {
            var possibleZero = stringToCharSet(s);

            if (intersectionOfSets(possibleZero, digits.get(1)).size() == 2 && intersectionOfSets(possibleZero, digits.get(4)).size() == 3) {
                digits.set(0, possibleZero);
                break;
            }
        }
    }

    private void findTwo() {
        digits.set(2, Set.of(signalA, signalC, signalD, signalE, signalG));
    }

    private void findThree() {
        digits.set(3, Set.of(signalA, signalC, signalD, signalF, signalG));
    }

    private void findFive() {
        digits.set(5, Set.of(signalA, signalB, signalD, signalF, signalG));
    }

    // remove all zeros and nines to get six
    private void findSix(final List<String> signals) {
        var sixString = signals.stream().filter(s-> s.length() == 6).filter(s -> {
            var set = stringToCharSet(s);
            return !set.equals(digits.get(9)) && !set.equals(digits.get(0));
        }).findFirst().orElseThrow();

        digits.set(6, stringToCharSet(sixString));
    }

    /* nine have:
     *  -6 segments
     *  -4 common signals with four
     */
    private void findNine(final List<String> signals) {
        var possibleNines = signals.stream().filter(s -> s.length() == 6).collect(Collectors.toList());

        for (String s : possibleNines) {
            var possibleNine = stringToCharSet(s);
            if (intersectionOfSets(possibleNine, digits.get(4)).size() == 4) {
                digits.set(9, possibleNine);
                break;
            }
        }
    }

    private static Set<Character> findDigit(final List<String> signals, final int numberOfSegments) {
        var string = signals.stream().filter(s -> s.length() == numberOfSegments).findFirst().orElseThrow();
        return stringToCharSet(string);
    }

    private static Set<Character> stringToCharSet(final String s) {
        return s.chars().mapToObj(c -> (char) c).collect(Collectors.toSet());
    }

    private static Set<Character> intersectionOfSets(final Set<Character> left, final Set<Character> right) {
        var intersection = new HashSet<>(left);
        intersection.retainAll(right);
        return intersection;
    }
}
