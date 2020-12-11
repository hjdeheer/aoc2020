import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Part1 {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("resources/part1.txt");
        Scanner scanner = new Scanner(file);
        ArrayList<Integer> numbers = new ArrayList<>();
        while (scanner.hasNext()) {
            numbers.add(scanner.nextInt());
        }
        scanner.close();
        int oneStar = twoSum(numbers, 2020);
        int twoStar = threeSum(numbers, 2020);
        System.out.println("The answer: " + oneStar);
        System.out.println("The answer: " + twoStar);

    }

    public static int twoSum(ArrayList<Integer> n, int target) {
        HashSet<Integer> map = new HashSet<>();
        int firstPart = -1;
        for(int i = 0; i < n.size(); i++) {
            int curr = n.get(i);
            int inHash = target - curr;
            if(map.contains(curr)) {
                firstPart = curr;
                break;
            } else {
                map.add(inHash);
            }
        }
        int secondPart = target - firstPart;

        return firstPart * secondPart;
    }

    public static int threeSum(ArrayList<Integer> n, int target) {
        Collections.sort(n);
        for(int i = 0; i < n.size(); i++) {
            int curr = n.get(i);
            int left = i + 1;
            int right = n.size() - 1;
            while(left != right) {

                int currSum = curr + n.get(left) + n.get(right);
                if(currSum == target) {
                    return curr * n.get(left) * n.get(right);
                } else if (currSum < target){
                    left++;
                } else {
                    right--;
                }
            }
        }
        return -1;
   }
}
