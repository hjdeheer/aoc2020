import Util.Point3D;
import Util.Point4D;
import Util.ReadInputUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Part17 {

    private static class PointComparator implements Comparator<Point3D> {

        @Override
        public int compare(Point3D p1, Point3D p2) {
            return p1.getZ() - p2.getZ();
        }
    }

    public static void main(String[] args) throws IOException {
        String filename = "resources/part17.txt";
        char[][] grid = ReadInputUtil.readToGrid(filename);
        int partOne = partOne(grid);
        System.out.println("Active cubes in 3D-space: " + partOne);

        int partTwo = partTwo(grid);
        System.out.println("Active cubes in 4D-space: " + partTwo);
    }

    public static int partOne(char[][] grid) {
        List<Point3D> activeCubes = new ArrayList<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                char cube = grid[i][j];
                if (cube == '#')
                    activeCubes.add(new Point3D(j, i, 0));
            }
        }
        for (int i = 0; i < 6; i++) {
            activeCubes = step3D(activeCubes);
        }
          return activeCubes.size();
    }

    public static int partTwo(char[][] grid) {
        List<Point4D> activeCubes = new ArrayList<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                char cube = grid[i][j];
                if (cube == '#')
                    activeCubes.add(new Point4D(j, i, 0, 0));
            }
        }
        for (int i = 0; i < 6; i++) {
            activeCubes = step4D(activeCubes);
        }
         return activeCubes.size();
    }

    public static List<Point3D> step3D(List<Point3D> activeCubes) {
        List<Point3D> newPoints = new ArrayList<>();
        int lowX = 0;
        int highX = 0;
        int lowY = 0;
        int highY = 0;
        int lowZ = 0;
        int highZ = 0;
        for (Point3D point : activeCubes) {
            lowX = Math.min(point.getX(), lowX);
            lowY = Math.min(point.getY(), lowY);
            lowZ = Math.min(point.getZ(), lowZ);
            highX = Math.max(point.getX(), highX);
            highY = Math.max(point.getY(), highY);
            highZ = Math.max(point.getZ(), highZ);
        }

        for (int i = lowX - 1; i <= highX + 1; i++) {
            for (int j = lowY - 1; j <= highY + 1; j++) {
                for (int k = lowZ - 1; k <= highZ + 1; k++) {
                    Point3D currPoint = new Point3D(i, j, k);
                    int activeNeighbours = nActives3D(currPoint, activeCubes);
                    boolean contains = activeCubes.contains(currPoint);
                    //if it is an active cube
                    if (contains && (activeNeighbours == 2 || activeNeighbours == 3)) {
                        newPoints.add(currPoint);
                    }
                    //if its an inactive cube
                    if (!contains && activeNeighbours == 3) {
                        newPoints.add(currPoint);
                    }
                }
            }
        }
        return newPoints;

    }


    public static List<Point4D> step4D(List<Point4D> activeCubes) {
        List<Point4D> newPoints = new ArrayList<>();
        int lowX = 0;
        int highX = 0;
        int lowY = 0;
        int highY = 0;
        int lowZ = 0;
        int highZ = 0;
        int lowW = 0;
        int highW = 0;
        for (Point4D point : activeCubes) {
            lowX = Math.min(point.getX(), lowX);
            lowY = Math.min(point.getY(), lowY);
            lowZ = Math.min(point.getZ(), lowZ);
            lowW = Math.min(point.getW(), lowW);
            highX = Math.max(point.getX(), highX);
            highY = Math.max(point.getY(), highY);
            highZ = Math.max(point.getZ(), highZ);
            highW = Math.max(point.getW(), highW);

        }

        for (int i = lowX - 1; i <= highX + 1; i++) {
            for (int j = lowY - 1; j <= highY + 1; j++) {
                for (int k = lowZ - 1; k <= highZ + 1; k++) {
                    for (int l = lowW - 1; l <= highW + 1; l++) {
                        Point4D currPoint = new Point4D(i, j, k, l);
                        int activeNeighbours = nActives4D(currPoint, activeCubes);
                        boolean contains = activeCubes.contains(currPoint);
                        //if it is an active cube
                        if (contains && (activeNeighbours == 2 || activeNeighbours == 3)) {
                            newPoints.add(currPoint);
                        }
                        //if its an inactive cube
                        if (!contains && activeNeighbours == 3) {
                            newPoints.add(currPoint);
                        }
                    }
                }
            }
        }
        return newPoints;

    }

    public static int nActives3D(Point3D point, List<Point3D> activeCubes) {
        int count = 0;
        for (int i = point.getX() - 1; i <= point.getX() + 1; i++) {
            for (int j = point.getY() - 1; j <= point.getY() + 1; j++) {
                for (int k = point.getZ() - 1; k <= point.getZ() + 1; k++) {
                    Point3D toCompare = new Point3D(i, j, k);
                    if (!toCompare.equals(point) && activeCubes.contains(toCompare))
                        count++;
                }
            }
        }

        return count;
    }

    public static int nActives4D(Point4D point, List<Point4D> activeCubes) {
        int count = 0;
        for (int i = point.getX() - 1; i <= point.getX() + 1; i++) {
            for (int j = point.getY() - 1; j <= point.getY() + 1; j++) {
                for (int k = point.getZ() - 1; k <= point.getZ() + 1; k++) {
                    for (int l = point.getW() - 1; l <= point.getW() + 1; l++) {
                        Point4D toCompare = new Point4D(i, j, k, l);
                        if (!toCompare.equals(point) && activeCubes.contains(toCompare))
                            count++;
                    }
                }
            }
        }

        return count;
    }


}
