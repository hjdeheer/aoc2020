import Util.Pair;
import Util.ReadInputUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Part11 {
    private static char[][] grid;

    public static void main(String[] args) throws IOException {
        String filename = "resources/part11.txt";
        grid = ReadInputUtil.readToGrid(filename);
        int partOne = partOne();
        grid = ReadInputUtil.readToGrid(filename);
        int partTwo = partTwo();

        System.out.println("Amount of seats occupied: " + partOne);
        System.out.println("Amount of seats occupied (part 2): " + partTwo);

    }


    public static int partOne() {
        int counter = 0;
        while (true) {
            List<Pair> toOccupy = new ArrayList<>();
            List<Pair> toEmpty = new ArrayList<>();
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    int adjacent = nAdjacent(i, j);
                    if (grid[i][j] == 'L' && adjacent == 0) {
                        toOccupy.add(new Pair(i, j));
                    }
                    if (grid[i][j] == '#' && adjacent >= 4) {
                        toEmpty.add(new Pair(i, j));
                    }
                }
            }
            for (Pair pair : toOccupy) {
                grid[pair.getX()][pair.getY()] = '#';
            }
            for (Pair pair : toEmpty) {
                grid[pair.getX()][pair.getY()] = 'L';
            }
            if (toOccupy.size() + toEmpty.size() == 0) {
                break;
            }
        }
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '#') {
                    counter++;
                }
            }
        }
        return counter;

    }

    public static int partTwo() {
        int counter = 0;
        while (true) {
            List<Pair> toOccupy = new ArrayList<>();
            List<Pair> toEmpty = new ArrayList<>();
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    int adjacent = nDirections(i, j);
                    if (grid[i][j] == 'L' && adjacent == 0) {
                        toOccupy.add(new Pair(i, j));
                    }
                    if (grid[i][j] == '#' && adjacent >= 5) {
                        toEmpty.add(new Pair(i, j));
                    }
                }
            }
            for (Pair pair : toOccupy) {
                grid[pair.getX()][pair.getY()] = '#';
            }
            for (Pair pair : toEmpty) {
                grid[pair.getX()][pair.getY()] = 'L';
            }
            if (toOccupy.size() + toEmpty.size() == 0) {
                break;
            }
        }
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '#') {
                    counter++;
                }
            }
        }
        return counter;

    }

    public static int nAdjacent(int row, int column) {
        int count = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = column - 1; j <= column + 1; j++) {
                //If i and j are in range of the grid
                if ((i >= 0 && i < grid.length) && (j >= 0 && j < grid[i].length) && (row != i || column != j)) {
                    //If i and j is occupied
                    if (grid[i][j] == '#') {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public static int nDirections(int row, int column) {
        int count = 0;
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {-1, 1}, {1, -1}, {-1, -1}};
        for (int[] direction : directions) {
            int i = row + direction[0];
            int j = column + direction[1];
            while ((i >= 0 && i < grid.length) && (j >= 0 && j < grid[i].length)) {
                if (grid[i][j] == '#') {
                    count++;
                    break;
                }
                if (grid[i][j] == 'L') {
                    break;
                }

                i += direction[0];
                j += direction[1];
            }
        }
        return count;
    }

}
