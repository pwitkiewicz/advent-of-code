package aoc.year2020.day18;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class Main {
    private static long evaluateOperation(long v1, long v2, char op) {
        if (op == '+') {
            return v1 + v2;
        }

        return v1 * v2;
    }

    private static int precedence(char op){
        if(op == '+')
            return 2;
        if(op == '*')
            return 1;
        return 0;
    }

    private static long evaluateExpression(String expression) {
        char[] input = expression.replaceAll(" ", "").toCharArray();

        Stack<Character> operators = new Stack<>();
        Stack<Long> values = new Stack<>();

        for (int i = 0; i < input.length; i++) {
            if (input[i] == '(') {
                operators.push(input[i]);
            } else if (input[i] >= '0' && input[i] <= '9') {
                long value = 0;
                while (i < input.length && input[i] >= '0' && input[i] <= '9') {
                    value = (value * 10) + (input[i] - '0');
                    i++;
                }
                values.push(value);
                i--;
            } else if (input[i] == ')') {
                while(operators.peek() != '(') {
                    long value2 = values.pop();
                    long value1 = values.pop();
                    char operator = operators.pop();
                    values.push(evaluateOperation(value1, value2, operator));
                }
                operators.pop();
            } else {
                //part 1
                //while (!operators.isEmpty() && operators.peek() != '(') {
                //part 2
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(input[i])) {
                    long value2 = values.pop();
                    long value1 = values.pop();
                    char operator = operators.pop();
                    values.push(evaluateOperation(value1, value2, operator));
                }
                operators.push(input[i]);
            }
        }

        while (!operators.isEmpty()) {
            long value2 = values.pop();
            long value1 = values.pop();
            char operator = operators.pop();
            values.push(evaluateOperation(value1, value2, operator));
        }

        return values.pop();
    }

    private static String[] readFile(String filename) {
        BufferedReader reader;
        ArrayList<String> input = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();

            while (line != null) {
                input.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return input.toArray(String[]::new);
    }

    public static void main(String[] args) {
        String[] input = readFile("input-day18.txt");
        long result = 0;
        for(String i : input) {
            result += evaluateExpression(i);
        }
        System.out.println(result);
    }
}
