import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Part3 {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("resources/part3.txt");
        Scanner sc = new Scanner(file);
        ArrayList<String> paths = new ArrayList<>();
        while (sc.hasNextLine()) {
            paths.add(sc.nextLine());
        }

        sc.close();
        int one = oneStar(paths);
        System.out.println("Part 1: " + one);


        long x = twoStar(paths, 1, 1);
        long y = twoStar(paths, 3, 1);
        long z = twoStar(paths, 5, 1);
        long k = twoStar(paths, 7, 1);
        long l = twoStar(paths, 1, 2);
        System.out.println("Part 2: " + x * y * z * k * l);
    }


    public static int oneStar(ArrayList<String> paths) {
        int lineSize = paths.get(0).length();
        int currRight = 3;
        int trees = 0;
        for(int i = 1; i < paths.size(); i++) {
            char curr = paths.get(i).charAt(currRight);
            if(curr == '#') {
                trees++;
            }
            currRight  = currRight + 3;
            if(currRight >= lineSize) {
                currRight = currRight % lineSize;
            }
        }

        return trees;

    }

    public static long twoStar(ArrayList<String> paths, int right, int down) {
        int lineSize = paths.get(0).length();
        int currRight = right;
        long trees = 0;
        for(int i = down; i < paths.size(); i += down) {
            char curr = paths.get(i).charAt(currRight);
            if(curr == '#') {
                trees++;
            }
            currRight  = currRight + right;
            if(currRight >= lineSize) {
                currRight = currRight % lineSize;
            }
        }

        return trees;

    }
}
