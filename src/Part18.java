import Util.Pair;
import Util.ReadInputUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Part18 {

    public static void main(String[] args) throws FileNotFoundException {
        String filename = "resources/part18.txt";
        List<String> input = ReadInputUtil.readToString(new Scanner(new File(filename)));
        long partOne = partOne(input);
        System.out.println("Total sum of all operations: " + partOne);
        long partTwo = partTwo(input);
        System.out.println("Total sum of all operations: " + partTwo);
    }

    public static long partOne(List<String> input) {
        long sum = 0;
        for (String str : input) {
            sum += evaluatePartOne(str);
        }
        return sum;
    }

    public static long partTwo(List<String> input) {
        long sum = 0;
        for (String str : input) {
            sum += evaluatePartTwo(str);
        }
        return sum;
    }

    public static long evaluatePartOne(String str) {
        //Recursively analyse all
        while (str.contains("(")) {
            Pair bounds = bounds(str);
            String toEval = str.substring(bounds.getX() + 1, bounds.getY());
            long evaluated = evaluatePartOne(toEval);
            str = str.substring(0, bounds.getX()) + String.valueOf(evaluated) + str.substring(bounds.getY() + 1);
        }

        //Evaluate linearly from left to right
        while (str.contains("*") || str.contains("+")) {
            String[] split = str.split(" ");
            str = evaluate(str, split[1].charAt(0));
        }
        return Long.parseLong(str);
    }

    public static long evaluatePartTwo(String str) {
        //Recursively analyse all
        while (str.contains("(")) {
            Pair bounds = bounds(str);
            String toEval = str.substring(bounds.getX() + 1, bounds.getY());
            long evaluated = evaluatePartTwo(toEval);
            str = str.substring(0, bounds.getX()) + String.valueOf(evaluated) + str.substring(bounds.getY() + 1);
        }

        while (str.contains("+")) {
            str = evaluate(str, '+');
        }
        while (str.contains("*")) {
            str = evaluate(str, '*');
        }
        return Long.parseLong(str);
    }


    public static Pair bounds(String str) {
        int currDepth = 0;
        int maxDepth = 0;
        int leftIndex = 0;
        int rightIndex = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '(') {
                currDepth++;
                if (currDepth >= maxDepth) {
                    maxDepth = currDepth;
                    leftIndex = i;
                }
            } else if (str.charAt(i) == ')') {
                if (currDepth == maxDepth) {
                    rightIndex = i;
                }
                currDepth--;
            }
        }
        return new Pair(leftIndex, rightIndex);
    }

    public static String evaluate(String str, char operation) {
        int indexOfPlus = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == operation) {
                indexOfPlus = i;
                break;
            }
        }
        int left = indexOfPlus - 2;
        int right = indexOfPlus + 2;

        while (left >= 0 && str.charAt(left) != ' ') {
            left--;
        }
        while (right < str.length() && str.charAt(right) != ' ') {
            right++;
        }
        left++;
        String currPlus = str.substring(left, right);
        String[] split = currPlus.split(" ");
        long value = 0;
        switch (operation) {
            case '+':
                value = Long.valueOf(split[0]) + Long.valueOf(split[2]);
                break;
            case '*':
                value = Long.valueOf(split[0]) * Long.valueOf(split[2]);
                break;
            default:
                break;
        }
        return str.substring(0, left) + String.valueOf(value) + str.substring(right);

    }
}
