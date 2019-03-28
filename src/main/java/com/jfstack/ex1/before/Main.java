package com.jfstack.ex1.before;


import java.util.List;
import static java.util.Arrays.asList;

public class Main {
    public static void main(String[] args) {
/*
the code below must print
--------------------
Racer: Alan
Average time: 10m
Best lap: 9m
       -----
Racer (winner): Dan
Average time: 8m
Best lap: 7m
       -----
Racer: Joe
Average time: 11m
Best lap: 11m
--------------------
*/
        new RaceReport().print(asList(
                new RaceData("Alan", asList(10, 11, 9, 13)),
                new RaceData("Dan", asList(8, 7, 9, 10)),
                new RaceData("Joe", asList(11, 11, 11, 11))));
    }
}

class RaceData {
    private String racer;
    private List lapsTime; //time in minutes (integers)

    //racer - name of the racer
    //lapsTime - time of each lap in minutes
    RaceData(String racer, List lapsTime) {
        this.racer = racer;
        this.lapsTime = lapsTime;
    }

    String getRacer() {
        return racer;
    }

    List<Integer> getLapsTime() {
        return lapsTime;
    }
}

class RaceReport {

    void print(List<RaceData> raceData) {
        System.out.print("--------------------\n");

        String w = "";
        int ws = Integer.MAX_VALUE;
        for (int i = 0; i < raceData.size(); i++) {
            RaceData r = raceData.get(i);
            int sum = 0;
            List<Integer> times = r.getLapsTime();
            for (int j = 0; j < times.size(); j++) {
                Integer time = times.get(j);
                sum += time;
            }
            if (sum < ws) {
                ws = sum;
                w = r.getRacer();
            }
        }

        for (int i = 0; i < raceData.size(); i++) {
            RaceData r = raceData.get(i);
            System.out.print("Racer" + (r.getRacer().equals(w) ? " (winner)" : "") + ": " + r.getRacer() + "\n");

            int bestLap = Integer.MAX_VALUE;
            int raceTime = 0;
            List<Integer> times = r.getLapsTime();
            for (int j = 0; j < times.size(); j++) {
                Integer time = times.get(j);
                if (time < bestLap) {
                    bestLap = time;
                }
                raceTime += time;
            }
            System.out.print("Average time: " + (raceTime / r.getLapsTime().size()) + "m\n");
            System.out.print("Best lap: " + bestLap + "m\n");
            if (i < raceData.size() - 1) {
                System.out.print("       -----         \n");
            }
        }
        System.out.print("--------------------\n");
    }
}