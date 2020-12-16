package Util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ReadInputUtil {

    public static List<Integer> readToInt(Scanner sc){
        ArrayList<Integer> numbers = new ArrayList<>();
        while(sc.hasNextLine()){
            numbers.add(Integer.parseInt(sc.nextLine()));
        }
        return numbers;
    }

    public static List<String> readToString(Scanner sc){
        ArrayList<String> strings = new ArrayList<>();
        while(sc.hasNextLine()){
            strings.add(sc.nextLine());
        }
        return strings;
    }

    public static char[][] readToGrid(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        BufferedReader br2 = new BufferedReader(new FileReader(filename));
        String curr;
        int columns = 0;
        int rows = 0;
        while((curr = br.readLine()) != null) {
            columns = curr.length();
            rows++;
        }
        char[][] grid = new char[rows][columns];
        int i = 0;
        while((curr = br2.readLine()) != null) {
            grid[i] = curr.toCharArray();
            i++;
        }
        br.close();
        br2.close();
        return grid;
    }
}
