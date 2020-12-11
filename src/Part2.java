import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Part2 {


    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("resources/part2.txt");
        Scanner scanner = new Scanner(file);
        Scanner scanner2 = new Scanner(file);

        int starOne = partOne(scanner);
        int starTwo = partTwo(scanner2);

        System.out.println(starOne);
        System.out.println(starTwo);

        scanner.close();
        scanner2.close();
    }


    public static int partOne(Scanner sc) {
        int correct = 0;
        while(sc.hasNextLine()) {
            String currLine = sc.nextLine();
            String[] split = currLine.split(" ");
            String[] numbers = split[0].split("-");
            int min = Integer.parseInt(numbers[0]);
            int max = Integer.parseInt(numbers[1]);
            char currChar = split[1].charAt(0);
            char[] password = split[2].toCharArray();
            int counter = 0;
            for(int i = 0; i < password.length; i++) {
                if(currChar == password[i]) {
                    counter++;
                }
            }
            if(counter >= min && counter <= max) {
                correct++;
            }
        }
        return correct;
    }

    public static int partTwo(Scanner sc) {
        int correct = 0;
        while(sc.hasNextLine()) {
            String currLine = sc.nextLine();
            String[] split = currLine.split(" ");
            String[] numbers = split[0].split("-");
            int min = Integer.parseInt(numbers[0]) - 1;
            int max = Integer.parseInt(numbers[1]) - 1;
            char currChar = split[1].charAt(0);
            char[] password = split[2].toCharArray();
            int counter = 0;
            if(password[min] == currChar) {
                counter++;
            }
            if(password[max] == currChar) {
                counter++;
            }
            if(counter == 1){
                correct++;
            }
        }
        return correct;

    }
}
