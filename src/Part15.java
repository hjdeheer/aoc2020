import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Part15 {


    public static void main(String[] args) throws FileNotFoundException {
        String filename = "resources/part15.txt";

        String[] starting = preProcessing(filename);
        long partOne = partOne(starting);
        System.out.println("The 2020th number spoken: " + partOne);
        long partTwo = partTwo(starting);
        System.out.println("The 30000000th number spoken: " + partTwo);

    }

    public static String[] preProcessing(String filename) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(filename));
        String curr = sc.nextLine();
        String[] split = curr.split(",");
        return split;
    }

    /**
     * Brute force way of implementing this sequence.
     * Time complexity O(n^2) since we have an outer loop that goes through the array once and an
     * inner loop that keeps track of the latest element seen.
     *
     * @param split input string
     * @return 2020th element of the sequence
     */
    public static long partOne(String[] split) {
        long[] numbers = new long[2020];
        for (int i = 0; i < split.length; i++) {
            numbers[i] = Long.parseLong(split[i]);
        }
        for (int i = split.length; i < numbers.length; i++) {
            int lastIndex = -1;
            long last = numbers[i - 1];
            for (int j = 0; j <= i - 2; j++) {
                if (last == numbers[j]) {
                    lastIndex = j;
                }
            }
            if (lastIndex == -1) {
                numbers[i] = 0;
            } else {
                numbers[i] = (i - 1) - lastIndex;
            }
        }
        return numbers[2019];
    }


    /**
     * Optimized way of implementing by using a map.
     * Time complexity O(n) since we have an outer loop that goes through the array once.
     * HashMap operations like put(), get() and contains() are on average O(1) so constant time.
     *
     * @param split input string
     * @return 30000000th element of the sequence
     */
    public static long partTwo(String[] split) {
        HashMap<Long, Integer> map = new HashMap<>();
        long[] numbers = new long[30000000];
        for (int i = 0; i < split.length; i++) {
            numbers[i] = Long.parseLong(split[i]);
            map.put(numbers[i], i);
        }
        for (int i = split.length; i < numbers.length; i++) {
            long last = numbers[i - 1];
            if (map.containsKey(last)) {
                int lastIndexFromMap = map.get(last);
                numbers[i] = (i - 1) - lastIndexFromMap;
            } else {
                numbers[i] = 0;
            }
            map.put(last, i - 1);
        }
        return numbers[29999999];
    }

}
