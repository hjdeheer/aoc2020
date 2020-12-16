import Util.Pair;
import Util.ReadInputUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Part16 {
    private static class Field {
        String name;
        Set<Integer> range;

        public Field(String name, Set<Integer> range) {
            this.name = name;
            this.range = range;
        }


        @Override
        public String toString() {
            return "Field{" +
                    "name='" + name + '\'' +
                    ", range=" + range +
                    '}';
        }
    }

    private static class LengthComparator implements Comparator<HashSet<Pair>> {
        public int compare(HashSet<Pair> c1, HashSet<Pair> c2) {
            return c1.size() - c2.size();
        }
    }

    private static class PairComparator implements Comparator<Pair> {
        public int compare(Pair p1, Pair p2) {
            return p1.getY() - p2.getY();
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("resources/part16.txt");
        List<String> strings = ReadInputUtil.readToString(new Scanner(file));
        int partOne = partOne(strings);
        System.out.println("Ticket scanning error rate: " + partOne);
        long partTwo = partTwo(strings);
        System.out.println("Multiply 6 values together: " + partTwo);
    }

    public static int partOne(List<String> input) {
        int error = 0;
        int index = 0;
        String curr = "";
        HashSet<Integer> ranges = new HashSet<>();
        while (!(curr = input.get(index)).equals("")) {
            String[] split = curr.split(" ");
            String[] firstRange = split[split.length - 3].split("-");
            String[] secondRange = split[split.length - 1].split("-");
            int firstX = Integer.parseInt(firstRange[0]);
            int firstY = Integer.parseInt(firstRange[1]);
            for (int i = firstX; i <= firstY; i++) {
                ranges.add(i);
            }
            int secondX = Integer.parseInt(secondRange[0]);
            int secondY = Integer.parseInt(secondRange[1]);
            for (int i = secondX; i <= secondY; i++) {
                ranges.add(i);
            }
            index++;
        }

        while (!(input.get(index)).equals("nearby tickets:")) {
            index++;
        }
        index++;

        while (index < input.size()) {
            String ticket = input.get(index);
            String tickets[] = ticket.split(",");
            for (int i = 0; i < tickets.length; i++) {
                int value = Integer.parseInt(tickets[i]);
                if (!ranges.contains(value)) {
                    error += value;
                }
            }
            index++;
        }
        return error;
    }


    public static long partTwo(List<String> input) {
        List<Field> fields = new ArrayList<>();
        List<Integer> myTicket = new ArrayList<>();
        List<List<Integer>> nearbyTickets = new ArrayList<>();
        Set<Integer> totalRanges = new HashSet<>();
        int index = 0;
        String curr = "";

        while (!(curr = input.get(index)).equals("")) {
            Set<Integer> ranges = new HashSet<>();

            String[] split = curr.split(" ");
            String[] firstRange = split[split.length - 3].split("-");

            String[] secondRange = split[split.length - 1].split("-");
            int firstX = Integer.parseInt(firstRange[0]);
            int firstY = Integer.parseInt(firstRange[1]);
            for (int i = firstX; i <= firstY; i++) {
                ranges.add(i);
                totalRanges.add(i);
            }
            int secondX = Integer.parseInt(secondRange[0]);
            int secondY = Integer.parseInt(secondRange[1]);
            for (int i = secondX; i <= secondY; i++) {
                ranges.add(i);
                totalRanges.add(i);
            }
            fields.add(new Field(split[0], ranges));
            index++;
        }
        index += 2;

        curr = input.get(index);
        String numbers[] = curr.split(",");
        for (String x : numbers) {
            myTicket.add(Integer.parseInt(x));
        }

        index += 3;
        while (index < input.size()) {
            List<Integer> ticket = new ArrayList<>();
            curr = input.get(index);

            String tickets[] = curr.split(",");
            boolean toAdd = true;
            for (int i = 0; i < tickets.length; i++) {
                int value = Integer.parseInt(tickets[i]);
                if (!totalRanges.contains(value)) {
                    toAdd = false;
                    break;
                }
                ticket.add(Integer.parseInt(tickets[i]));
            }
            if (toAdd) {
                nearbyTickets.add(ticket);
            }

            index++;
        }

        List<List<List<Boolean>>> ofAllTickets = new ArrayList<>();
        for (List<Integer> ticket : nearbyTickets) {
            List<List<Boolean>> ofTickets = new ArrayList<>();
            for (int number : ticket) {
                List<Boolean> indices = new ArrayList<>();
                for (int i = 0; i < fields.size(); i++) {
                    Field currField = fields.get(i);
                    indices.add(currField.range.contains(number));
                }
                ofTickets.add(indices);

            }
            ofAllTickets.add(ofTickets);
        }
        List<HashSet<Pair>> possibilities = new ArrayList<>();

        for (int i = 0; i < fields.size(); i++) {
            HashSet<Pair> check = new HashSet<>();
            for (int j = 0; j < fields.size(); j++) {
                boolean toCheck = true;
                for (int k = 0; k < ofAllTickets.size(); k++) {
                    toCheck = toCheck && ofAllTickets.get(k).get(i).get(j);
                }
                if (toCheck) {
                    check.add(new Pair(j, i));
                }

            }
            possibilities.add(check);
        }

        Collections.sort(possibilities, new LengthComparator());
        List<Pair> correctMappings = new ArrayList<>();
        for (int i = 0; i < possibilities.size(); i++) {
            Pair pair = new Pair(0, 0);
            for (Pair x : possibilities.get(i)) {
                pair = x;
            }
            correctMappings.add(pair);
            for (int j = i + 1; j < possibilities.size(); j++) {
                for (Pair x : possibilities.get(j)) {
                    if (x.getX() == pair.getX()) {
                        possibilities.get(j).remove(x);
                        break;
                    }
                }
            }
        }
        Collections.sort(correctMappings, new PairComparator());
        long multiplication = 1;
        for (int i = 0; i < myTicket.size(); i++) {
            if (fields.get(correctMappings.get(i).getX()).name.contains("departure")) {
                multiplication *= myTicket.get(i);
            }
        }
        return multiplication;


    }

}
