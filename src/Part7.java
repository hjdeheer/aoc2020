import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;


class Bag {
    String color;
    int amount;

    public Bag(String color, int amount){
        this.color = color;
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bag bag = (Bag) o;
        return color.equals(bag.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }

    @Override
    public String toString() {
        return "Bag{" +
                "color='" + color + '\'' +
                ", amount=" + amount +
                '}';
    }
}


public class Part7 {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("resources/part7.txt");
        Scanner sc = new Scanner(file);
        List<String> str = preProcessing(sc);
        sc.close();
        System.out.println(partOne(str));
        System.out.println(partTwo(str));

    }

    public static List<String> preProcessing(Scanner sc) {
        ArrayList<String> lines = new ArrayList<>();
        while(sc.hasNextLine()) {
            lines.add(sc.nextLine());
        }
        return lines;
    }

    public static int partOne(List<String> lines) {
        int nColours = 0;
        HashMap<Bag, Bag[]> map = new HashMap<>();
        HashSet<String> hasGold = new HashSet<>();
        for(String line : lines) {
            String[] bags = line.split(" contain");
            String currBag = bags[0].split(" bags")[0];
            Bag curr = new Bag(currBag, 1);
            String[] otherBags = bags[1].split(",");
            Bag[] colorBags = new Bag[otherBags.length];
            outer : for(int i = 0; i < otherBags.length; i++) {
                String[] toDo = otherBags[i].split(" ");
                try {
                    Integer.parseInt(toDo[1]);
                } catch (NumberFormatException e) {
                    colorBags = null;
                    break outer;

                }
                colorBags[i] = new Bag(toDo[2] + " " + toDo[3], Integer.parseInt(toDo[1]));
                if(colorBags[i].color.equals("shiny gold")) {
                    hasGold.add(curr.color);
                }
            }
            map.put(curr, colorBags);
        }
        int sizeBefore = 0;
        int sizeAfter = -1;
        while(sizeBefore != sizeAfter) {
            sizeBefore = hasGold.size();
            int counter = 0;
            for(Map.Entry<Bag, Bag[]> entry : map.entrySet()) {
                Bag[] bags = entry.getValue();
               if(bags == null){
                   continue;
               }
               counter++;
               for(int i = 0; i < bags.length; i++) {
                   if(hasGold.contains(bags[i].color)) {
                       hasGold.add(entry.getKey().color);
                       break;
                   }
               }
            }
            sizeAfter = hasGold.size();
        }

        return sizeAfter;
    }

    public static int partTwo(List<String> lines) {
        HashMap<Bag, Bag[]> map = new HashMap<>();
        Bag[] shiny = new Bag[2];
        for(String line : lines) {
            String[] bags = line.split(" contain");
            String currBag = bags[0].split(" bags")[0];
            Bag curr = new Bag(currBag, 1);
            String[] otherBags = bags[1].split(",");
            Bag[] colorBags = new Bag[otherBags.length];
            outer : for(int i = 0; i < otherBags.length; i++) {
                String[] toDo = otherBags[i].split(" ");
                try {
                    Integer.parseInt(toDo[1]);
                } catch (NumberFormatException e) {
                    colorBags = null;
                    break outer;

                }
                Bag toAdd = new Bag(toDo[2] + " " + toDo[3], Integer.parseInt(toDo[1]));
                colorBags[i] = toAdd;
                if(currBag.equals("shiny gold")) {
                    shiny[i] = toAdd;
                }

            }
            map.put(curr, colorBags);

        }

        return recursive(shiny, map, 1);
    }

    public static int recursive(Bag[] curr, HashMap<Bag, Bag[]> map, int depth) {
        if(curr == null) {
            return 0;
        }
        int counter = 0;
        for(Bag bag : curr) {
            Bag[] bags = map.get(bag);
            int currRes = bag.amount * recursive(bags, map, depth) + bag.amount;
            counter += currRes;
        }
        return counter;
    }
}
