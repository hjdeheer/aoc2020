import Util.ReadInputUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Part10 {


    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("resources/part10.txt");
        Scanner sc = new Scanner(file);
        List<Integer> numbers = ReadInputUtil.readToInt(sc);

        System.out.println("The number of 1-jolt differences multiplied by the number of 3-jolt differences is: " + partOne(numbers));
        System.out.println("The number of distinct arrangements: " + partTwo(numbers));


        //Testing
        File file2 = new File("resources/part10test.txt");
        Scanner sc2 = new Scanner(file2);
        List<Integer> numbers2 = ReadInputUtil.readToInt(sc2);
        long answer = partTwo(numbers2);
        assert (answer == 19208);
    }

    public static int partOne(List<Integer> numbers) {
        Collections.sort(numbers);
        int startJolt = 0;
        int oneDiff = 0;
        int twoDiff = 0;
        int threeDiff = 0;
        for (int i = 0; i < numbers.size(); i++) {
            int curr = numbers.get(i);
            int diff = curr - startJolt;
            if (diff == 1) {
                oneDiff++;
            }
            if (diff == 2) {
                twoDiff++;
            }
            if (diff == 3) {
                threeDiff++;
            }
            startJolt = curr;
        }
        threeDiff++;
        return oneDiff *threeDiff;
    }



    public static long partTwo(List<Integer> numbers) {
        Collections.sort(numbers);
        System.out.println(numbers);
        long[] mem = new long[numbers.size()];

        mem[0] = 1;
        if(numbers.get(1) - 3 <= 0) {
            mem[1] = mem[0] + 1;
        } else {
            mem[1] = 1;
        }
        if(numbers.get(2) - 3 <= 0) {
            mem[2] = mem[1] * 2;
        } else if (numbers.get(2) - 3 <= numbers.get(0)) {
            mem[2] = mem[1] + 1;
        } else {
            mem[2] = mem[1];
        }
        for(int i = 3; i < numbers.size(); i++) {
            long curr = numbers.get(i);
            if(curr - 3 == numbers.get(i-3)) {
                mem[i] = mem[i-1] + mem[i-2] + mem[i-3];
            } else if(curr - 3 <= numbers.get(i - 2)) {
                mem[i] = mem[i-1] + mem[i-2];
            } else {
                mem[i] = mem[i-1];
            }
        }
        return mem[numbers.size() - 1];
    }


}


