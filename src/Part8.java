import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

abstract class Instruction {
    int argument;

    public Instruction(int argument) {
        this.argument = argument;
    }

}

class NoOperation extends Instruction {

    public NoOperation(int argument) {
        super(argument);
    }

}

class Accumulator extends Instruction {

    public Accumulator(int argument) {
        super(argument);
    }
}

class Jump extends Instruction {

    public Jump(int argument) {
        super(argument);
    }
}


public class Part8 {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("resources/part8.txt");
        Scanner sc = new Scanner(file);
        List<Instruction> instructions = preProcessing(sc);

        int partOne = partOne(instructions);
        int partTwo = partTwo(instructions);
        System.out.println("Part 1:" + partOne);
        System.out.println("Part 2:" + partTwo);
    }


    public static List<Instruction> preProcessing(Scanner sc) {
        List<Instruction> instructions = new ArrayList<>();
        while (sc.hasNextLine()) {
            int argument = 0;
            String nextLine = sc.nextLine();
            String[] parts = nextLine.split(" ");
            String firstPart = parts[0];
            String secondPart = parts[1];
            if (secondPart.charAt(0) == '-') {
                argument = -Integer.parseInt(secondPart.substring(1));
            } else {
                argument = Integer.parseInt(secondPart.substring(1));
            }
            switch (firstPart) {
                case "nop":
                    instructions.add(new NoOperation(argument));
                    break;
                case "acc":
                    instructions.add(new Accumulator(argument));
                    break;
                case "jmp":
                    instructions.add(new Jump(argument));
                    break;
                default:
                    System.out.println("Something wrong happened");

            }
        }
        return instructions;
    }

    public static int partOne(List<Instruction> instructions) {
        HashSet<Integer> visitedIndex = new HashSet<>();
        int index = 0;
        int acc = 0;
        while (!visitedIndex.contains(index)) {
            Instruction curr = instructions.get(index);
            visitedIndex.add(index);
            if (curr instanceof Jump) {
                index += curr.argument;
            } else if (curr instanceof Accumulator) {
                acc += curr.argument;
                index++;
            } else {
                index++;
            }
        }
        return acc;
    }

    public static int partTwo(List<Instruction> instructions) {
        List<List<Instruction>> all = new ArrayList<>();
        int counter = 0;

        for (Instruction i : instructions) {
            if (i instanceof NoOperation || i instanceof Jump) {
                counter++;
            }
        }
        for (int i = 0; i < counter; i++) {
            int toDo = 0;
            ArrayList<Instruction> toAdd = new ArrayList<>();
            for (int j = 0; j < instructions.size(); j++) {
                boolean added = false;
                Instruction curr = instructions.get(j);
                if (curr instanceof NoOperation) {
                    if (toDo == i) {
                        toAdd.add(new Jump(curr.argument));
                        added = true;
                    }
                    toDo++;
                } else if (curr instanceof Jump) {
                    if (toDo == i) {
                        toAdd.add(new NoOperation(curr.argument));
                        added = true;
                    }
                    toDo++;
                }
                if(added == false) {
                    toAdd.add(curr);
                }
            }
            all.add(toAdd);
        }
        for(List<Instruction> i : all) {
            HashSet<Integer> visitedIndex = new HashSet<>();
            int index = 0;
            int acc = 0;
            boolean found = false;

            while (!visitedIndex.contains(index)) {
                if(index == i.size()) {
                    return acc;
                } else if(index  > i.size()) {
                    break;
                }
                Instruction curr = i.get(index);
                visitedIndex.add(index);
                if (curr instanceof Jump) {
                    index += curr.argument;
                } else if (curr instanceof Accumulator) {
                    acc += curr.argument;
                    index++;
                } else {
                    index++;
                }
            }
        }
        //Not found
        return -1;
    }


}
