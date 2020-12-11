import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Part5 {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("resources/part5.txt");
        Scanner sc = new Scanner(file);

        Scanner sc2 = new Scanner(file);
        int partOne = partOne(sc);
        System.out.println("Part 1: " + partOne);
        int partTwo = partTwo(sc2);
        System.out.println("Part 2: " + partTwo);
    }

    static class Pair {
        int x;
        int y;
        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }


        @Override
        public String toString() {
            return "Pair{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    static class PairComparator implements Comparator<Pair> {
        @Override
        public int compare(Pair p1, Pair p2) {
            int x = p1.x - p2.x;
            if(x == 0){
                return p1.y - p2.y;
            }
            return x;
        }


    }

    public static int partOne(Scanner sc) {
        int highest = 0;
        while(sc.hasNextLine()) {
            String line = sc.nextLine();
            String lowerUpper = line.substring(0, 7);
            String leftRight = line.substring(7);
            int n1 = lowerUpper.length();
            int n2 = leftRight.length();
            int low = 0;
            int row = 0;
            int column = 0;
            int high = (int) (Math.pow(2, n1) - 1);
            for(int i = 0; i <= n1; i++) {
                if(low == high) {
                    row = low;
                    break;
                }
                row = (low + high) / 2;
                if(lowerUpper.charAt(i) == 'F') {
                    high = row;
                } else {
                    low = row + 1;
                }
            }
            low = 0;
            high = (int) (Math.pow(2, n2) - 1);
            for(int i = 0; i <= n2; i++) {
                if(low == high) {
                    column = low;
                    break;
                }
                column = (low + high) / 2;
                if(leftRight.charAt(i) == 'L') {
                    high = column;
                } else {
                    low = column + 1;
                }
            }
            int seatID = row * 8 + column;
            highest = Math.max(highest, seatID);
        }
        return highest;
    }


    public static int partTwo(Scanner sc) {
        ArrayList<Pair> pairs = new ArrayList<>();
        while(sc.hasNextLine()) {
            String line = sc.nextLine();
            String lowerUpper = line.substring(0, 7);
            String leftRight = line.substring(7);
            int n1 = lowerUpper.length();
            int n2 = leftRight.length();
            int low = 0;
            int row = 0;
            int column = 0;
            int high = (int) (Math.pow(2, n1) - 1);
            for(int i = 0; i <= n1; i++) {
                if(low == high) {
                    row = low;
                    break;
                }
                row = (low + high) / 2;
                if(lowerUpper.charAt(i) == 'F') {
                    high = row;
                } else {
                    low = row + 1;
                }
            }
            low = 0;
            high = (int) (Math.pow(2, n2) - 1);
            for(int i = 0; i <= n2; i++) {
                if(low == high) {
                    column = low;
                    break;
                }
                column = (low + high) / 2;
                if(leftRight.charAt(i) == 'L') {
                    high = column;
                } else {
                    low = column + 1;
                }
            }
            pairs.add(new Pair(row, column));
        }

        Collections.sort(pairs, new PairComparator());

        for(int i = 0; i < pairs.size() - 1; i++) {
            Pair curr = pairs.get(i);
            Pair next = pairs.get(i + 1);
            int shouldBeNext = (curr.y + 1) % 8;
            if(shouldBeNext != (next.y % 8)) {
                int column = shouldBeNext;
                int row = curr.x;
                if(column == 0) {
                    row++;
                }
                return row * 8 + column;
            }
        }
        //Not found
        return -1;
    }
}
