import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class Part6 {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("resources/part6.txt");
        Scanner sc = new Scanner(file);

        Scanner sc2 = new Scanner(file);
        int partOne = partOne(sc);
        System.out.println("Part 1: " + partOne);
        int partTwo = partTwo(sc2);
        System.out.println("Part 2: " + partTwo);
    }


    public static int partOne(Scanner sc) {
        int counter = 0;
        while(sc.hasNextLine()) {
            HashSet<Character> uniqueChars = new HashSet<>();
            while(sc.hasNextLine()) {
                String curr = sc.nextLine();
                if(curr.isBlank()) {
                    break;
                }
                for(char c : curr.toCharArray()) {
                    uniqueChars.add(c);
                }
            }
            counter+= uniqueChars.size();
        }
        return counter;
    }

    public static int partTwo(Scanner sc) {
        int counter = 0;

        while(sc.hasNextLine()) {
            HashSet<Character> result = new HashSet<>();
            int i = 0;
            while(sc.hasNextLine()) {
                String curr = sc.nextLine();
                if(curr.isBlank()) {
                    break;
                }
                HashSet<Character> intersection = new HashSet<>();
                for(char c : curr.toCharArray()) {
                    intersection.add(c);
                    if(i == 0) {
                        result.add(c);
                    }
                }
                i++;
                result.retainAll(intersection);
            }
            counter += result.size();
        }
        return counter;
    }

}
