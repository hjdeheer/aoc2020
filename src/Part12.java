import Util.Navigation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Part12 {

    public static void main(String[] args) throws IOException {
        String filename = "resources/part12.txt";
        List<Navigation> navigations = preProcessing(filename);
        int partOne = partOne(navigations);
        System.out.println("Manhattan distance between start and endpoint: " + partOne);

        int partTwo = partTwo(navigations);
        System.out.println("Manhattan distance between start and endpoint: " + partTwo);

    }

    public static List<Navigation> preProcessing(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        List<Navigation> navigations = new ArrayList<>();
        String curr;
        while ((curr = br.readLine()) != null) {
            char c = curr.charAt(0);
            int value = Integer.parseInt(curr.substring(1));
            navigations.add(new Navigation(c, value));
        }
        return navigations;
    }

    public static int partOne(List<Navigation> navs) {
        int east = 0;
        int north = 0;
        int angle = 0;
        for (Navigation nav : navs) {
            char currChar = nav.getAction();
            int currValue = nav.getValue();
            switch (currChar) {
                case 'N':
                    north += currValue;
                    break;
                case 'E':
                    east += currValue;
                    break;
                case 'S':
                    north -= currValue;
                    break;
                case 'W':
                    east -= currValue;
                    break;
                case 'L':
                    angle = (angle + currValue) % 360;
                    break;
                case 'R':
                    angle = (angle - currValue) % 360;
                    break;
                case 'F':
                    north += currValue * (int) Math.sin(Math.toRadians(angle));
                    east += currValue * (int) Math.cos(Math.toRadians(angle));
                    break;
                default:
                    break;
            }
        }
        return Math.abs(east) + Math.abs(north);
    }

    public static int partTwo(List<Navigation> navs) {
        int wayEast = 10;
        int wayNorth = 1;
        int shipEast = 0;
        int shipNorth = 0;
        int wayAngle = 0;
        for (Navigation nav : navs) {
            char currChar = nav.getAction();
            int currValue = nav.getValue();
            switch (currChar) {
                case 'N':
                    wayNorth += currValue;
                    break;
                case 'E':
                    wayEast += currValue;
                    break;
                case 'S':
                    wayNorth -= currValue;
                    break;
                case 'W':
                    wayEast -= currValue;
                    break;
                case 'L':
                    int cos = (int) Math.cos(Math.toRadians(currValue));
                    int sin = (int) Math.sin(Math.toRadians(currValue));
                    int dx = wayEast - shipEast;
                    int dy = wayNorth - shipNorth;
                    wayEast = (cos * dx) - (sin * dy) + shipEast;
                    wayNorth = (sin * dx) + (cos * dy) + shipNorth;
                    break;
                case 'R':
                    cos = (int) Math.cos(Math.toRadians(-currValue));
                    sin = (int) Math.sin(Math.toRadians(-currValue));
                    dx = wayEast - shipEast;
                    dy = wayNorth - shipNorth;
                    wayEast = (cos * dx) - (sin * dy) + shipEast;
                    wayNorth = (sin * dx) + (cos * dy) + shipNorth;
                    break;
                case 'F':
                    dx = wayEast - shipEast;
                    dy = wayNorth - shipNorth;
                    shipEast += dx * currValue;
                    shipNorth += dy * currValue;
                    wayEast += dx * currValue;
                    wayNorth += dy * currValue;
                    break;
                default:
                    break;
            }
        }
        return Math.abs(shipEast) + Math.abs(shipNorth);
    }
}
