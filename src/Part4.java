import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class Part4 {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("resources/part4.txt");
        Scanner sc = new Scanner(file);

        Scanner sc2 = new Scanner(file);
        System.out.println("Part 1: " + partOne(sc));
        System.out.println("Part 2: " + partTwo(sc2));

    }

    public static int partOne(Scanner sc) {
        int valids = 0;
        while (sc.hasNextLine()) {
            HashSet<String> currSet = new HashSet<>();
            while (sc.hasNextLine()) {
                String x = sc.nextLine();
                if (x.isBlank()) {
                    break;
                }
                String[] split = x.split(" ");
                for (String curr : split) {
                    String substring = curr.substring(0, 3);
                    if (!currSet.contains(substring)) {
                        currSet.add(substring);
                    }
                }
            }
            if (currSet.size() == 8 || (currSet.size() == 7 && !currSet.contains("cid"))) {
                valids++;
            }

        }
        return valids;
    }

    public static int partTwo(Scanner sc) {
        int valids = 0;
        while (sc.hasNextLine()) {
            HashSet<String> currSet = new HashSet<>();
            while (sc.hasNextLine()) {
                String x = sc.nextLine();
                if (x.isBlank()) {
                    break;
                }
                String[] split = x.split(" ");
                for (String curr : split) {
                    String substring = curr.substring(0, 3);
                    System.out.println("curr + " + curr);
                    System.out.println(validation(curr));
                    if (!currSet.contains(substring) && validation(curr)) {
                        currSet.add(substring);
                    }
                }
            }
            if (currSet.size() == 8 || (currSet.size() == 7 && !currSet.contains("cid"))) {
                valids++;
            }

        }
        return valids;
    }

    public static boolean validation(String str) {
        try {
            int n = str.length();
            String first = str.substring(0, 3);
            String last = str.substring(4, n);
            if (first.equals("byr")) {
                int curr = Integer.parseInt(last);
                if ((curr < 1920 || curr > 2002) || last.length() != 4) {
                    return false;
                }
            } else if (first.equals("iyr")) {
                int curr = Integer.parseInt(last);
                if (curr < 2010 || curr > 2020 || last.length() != 4) {
                    return false;
                }
            } else if (first.equals("eyr")) {
                int curr = Integer.parseInt(last);
                if (curr < 2020 || curr > 2030 || last.length() != 4) {
                    return false;
                }
            } else if (first.equals("hgt")) {
                String length = last.substring(0, last.length() - 2);
                String measuring = last.substring(last.length() - 2, last.length());
                int len = Integer.parseInt(length);
                if (measuring.equals("cm") && (len < 150 || len > 193)) {
                    return false;
                } else if (measuring.equals("in") && (len < 59 || len > 76)) {
                    return false;
                }
            } else if (first.equals("hcl")) {
                if (last.charAt(0) != '#') {
                    return false;
                }
                if (!last.substring(1).matches("[a-f0-9]{6}")) {
                    return false;
                }
            } else if (first.equals("ecl")) {
                if(!last.matches("amb|blu|brn|gry|grn|hzl|oth")) {
                    return false;
                }
            } else if (first.equals("pid")) {
                if(!last.matches("[0-9]{9}")) {
                    return false;
                }
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}