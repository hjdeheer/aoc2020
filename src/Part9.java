import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Part9 {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("resources/part9.txt");
        Scanner sc = new Scanner(file);
        List<Long> numbers = preProcessing(sc);
        sc.close();
        Long invalid = partOne(numbers);
        Long set = partTwo(numbers, invalid);
        System.out.println(invalid);
        System.out.println(set);

    }

    public static List<Long> preProcessing(Scanner sc) {
        ArrayList<Long> numbers = new ArrayList<>();
        while (sc.hasNext()) {
            numbers.add(sc.nextLong());
        }
        return numbers;
    }

    public static boolean twoSum(List<Long> n, Long target, int lower, int higher) {
        HashSet<Long> map = new HashSet<>();
        int firstPart = -1;
        for (int i = lower; i < higher; i++) {
            Long curr = n.get(i);
            Long inHash = target - curr;
            if (map.contains(curr)) {
                //firstPart = curr;
                return true;
            } else {
                map.add(inHash);
            }
        }
        return false;
    }

    public static Long partOne(List<Long> numbers) {
        int lowerRange = 0;
        int higherRange = 0;
        int counter = 0;
        for (int i = 25; i < numbers.size(); i++) {
            lowerRange = i - 25;
            higherRange = i;
            if (!twoSum(numbers, numbers.get(i), lowerRange, higherRange)) {
                return numbers.get(i);
            }
        }
        //Not found
        return (long) -1;
    }

    public static Long partTwo(List<Long> numbers, Long invalid) {
        for (int i = 0; i < numbers.size(); i++) {
            Long curr = numbers.get(i);
            Long add = curr;
            ArrayList<Long> range = new ArrayList<>();
            range.add(curr);
            for (int j = i + 1; j < numbers.size(); j++) {
                add += numbers.get(j);
                range.add(numbers.get(j));
                if (add.equals(invalid)) {
                    Collections.sort(range);
                    return range.get(0) + range.get(range.size() - 1);
                } if(add > invalid) {
                    break;
                }
            }
        }
        //Range not found
        return (long) -1;
    }
}
