import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Part14 {
    private static long[] memoryOne = new long[1000000];
    private static ArrayList<Long> memoryTwo = new ArrayList<>();

    private static class Mask {
        private String mask;
        private List<Integer> pointers;
        private List<Long> values;

        public Mask(String mask, List<Integer> pointers, List<Long> values) {
            this.mask = mask;
            this.pointers = pointers;
            this.values = values;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        String filename = "resources/part14.txt";
        List<Mask> masks = preProcessing(filename);
        long partOne = partOne(masks);
        System.out.println(partOne);
        long partTwo = partTwo(masks);
        System.out.println(partTwo);
    }

    public static List<Mask> preProcessing(String filename) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(filename));
        List<Mask> masks = new ArrayList<>();
        boolean first = true;
        String curr = "";
        while (sc.hasNextLine()) {

            List<Integer> pointers = new ArrayList<>();
            List<Long> values = new ArrayList<>();
            while (sc.hasNextLine()) {
                String x = sc.nextLine();
                if (x.contains("mask") && first) {
                    curr = x.split(" ")[2];
                    first = false;
                    break;
                } else if ((x.contains("mask") && !first)) {
                    masks.add(new Mask(curr, pointers, values));
                    curr = x.split(" ")[2];
                    break;
                }
                String[] split = x.split(" ");
                int pointer = Integer.parseInt(split[0].substring(4, split[0].length() - 1));
                long memoryValue = Long.parseLong(split[2]);
                memoryOne[pointer] = memoryValue;
                pointers.add(pointer);
                values.add(memoryValue);
                if (!sc.hasNextLine()) {
                    masks.add(new Mask(curr, pointers, values));
                }
            }
        }
        return masks;
    }

    public static long partOne(List<Mask> masks) {
        long totalSum = 0;
        for (Mask mask : masks) {
            String bitmask = mask.mask;
            for (int i = 0; i < mask.values.size(); i++) {
                long currValue = mask.values.get(i);
                long andMask = 0;
                long orMask = 0;
                for (int j = 0; j < bitmask.length(); j++) {
                    if (bitmask.charAt(j) == 'X') {
                        andMask += (1L << (bitmask.length() - 1 - j));
                    }
                    if (bitmask.charAt(j) == '1') {
                        orMask += (1L << (bitmask.length() - 1 - j));
                    }
                }
                long erase = currValue & andMask;
                long overwritten = erase | orMask;
                memoryOne[mask.pointers.get(i)] = overwritten;
            }
        }
        for (int i = 0; i < memoryOne.length; i++) {
            if (memoryOne[i] != 0) {
            }
            totalSum += memoryOne[i];
        }
        return totalSum;
    }

    public static long partTwo(List<Mask> masks) {
        long totalSum = 0;
        HashMap<Long, Integer> map = new HashMap<>();
        for (Mask mask : masks) {
            String bitmask = mask.mask;
            for (int i = 0; i < mask.values.size(); i++) {
                long currAddress = mask.pointers.get(i);
                long currValue = mask.values.get(i);
                StringBuilder leadingZeros = new StringBuilder();
                String value = Long.toBinaryString(currAddress);
                for (int j = 0; j < 36 - value.length(); j++) {
                    leadingZeros.append('0');
                }
                value = leadingZeros.toString() + value;

                StringBuilder result = new StringBuilder();
                for (int j = 0; j < bitmask.length(); j++) {
                    if (bitmask.charAt(j) == 'X') {
                        result.append('X');
                    } else if (bitmask.charAt(j) == '1') {
                        result.append('1');
                    } else {
                        result.append(value.charAt(j));
                    }
                }
                String res = result.toString();
                List<Long> addresses = getAllAddresses(res);
                for (long address : addresses) {
                    if (!map.containsKey(address)) {
                        map.put(address, memoryTwo.size());
                        memoryTwo.add(currValue);
                    } else {
                        int index = map.get(address);
                        memoryTwo.set(index, currValue);
                    }
                }
            }
        }
        for (int i = 0; i < memoryTwo.size(); i++) {
            totalSum += memoryTwo.get(i);
        }
        return totalSum;
    }


    public static List<Long> getAllAddresses(String input) {
        //base case
        if (!input.contains("X")) {
            ArrayList<Long> toAdd = new ArrayList<Long>();
            toAdd.add(Long.parseLong(input, 2));
            return toAdd;
        }
        List<Long> one = new ArrayList<>();
        List<Long> two = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {

            if (input.charAt(i) == 'X') {
                String x = replaceChar(input, '0', i);
                String y = replaceChar(input, '1', i);
                one = getAllAddresses(x);
                two = getAllAddresses(y);
                break;
            }
        }
        List<Long> all = new ArrayList<>();
        all.addAll(one);
        all.addAll(two);
        return all;
    }

    public static String replaceChar(String str, char ch, int index) {
        StringBuilder myString = new StringBuilder(str);
        myString.setCharAt(index, ch);
        return myString.toString();
    }

}
