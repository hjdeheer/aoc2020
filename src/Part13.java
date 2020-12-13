import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Part13 {

    private static class Schedule {
        int departTime;
        List<Integer> busTimes;
        List<Integer> offsets;

        public Schedule(int departTime, List<Integer> busTimes, List<Integer> offsets) {
            this.departTime = departTime;
            this.busTimes = busTimes;
            this.offsets = offsets;

        }
    }

    public static void main(String[] args) throws IOException {
        String filename = "resources/part13.txt";
        Schedule schedule = preProcessing(filename);
        int partOne = partOne(schedule);
        partTwo(schedule);
        System.out.println("939490236001473");
    }

    public static Schedule preProcessing(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        ArrayList<Integer> busTimes = new ArrayList<>();
        ArrayList<Integer> offsets = new ArrayList<>();
        int departTime = Integer.parseInt(br.readLine());
        String busStr = br.readLine();
        String[] split = busStr.split(",");
        for(int i = 0; i < split.length; i++) {
            if(!split[i].equals("x")) {
                busTimes.add(Integer.parseInt(split[i]));
                offsets.add(i);
            }
        }
        return new Schedule(departTime, busTimes, offsets);
    }

    public static int partOne(Schedule schedule) {
        int minutes = Integer.MAX_VALUE;
        int bestBus = 0;
        for(int x : schedule.busTimes) {
            int distance = x - (schedule.departTime % x);
            if(distance < minutes) {
                minutes = distance;
                bestBus = x;
            }
        }
        return bestBus * minutes;
    }


    //Used a CRT Solver for this one
    //https://www.dcode.fr/chinese-remainder
    public static void partTwo(Schedule schedule) {
        for(int i = 0; i < schedule.busTimes.size(); i++) {
            int currOffset = schedule.offsets.get(i);
            int currDepart = schedule.busTimes.get(i);
            int currModulus = currOffset % currDepart;
            int remainder = currDepart - currModulus;
            if(currModulus == 0) {
                remainder = 0;
            }
            System.out.println("x = " + remainder + " mod " + currDepart);
        }
    }
}
