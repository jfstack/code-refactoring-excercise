package com.jfstack.ex1.after;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.*;

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

/**   Here is my way of refactoring */

class ResultData {
    String racer;
    Integer avgTime;
    Integer bestLap;

    public ResultData(String racer, Double average, Integer bestLap) {
        this.racer = racer;
        this.avgTime = average.intValue();
        this.bestLap = bestLap;

    }

    public String getRacer() {
        return racer;
    }

    public Integer getAvgTime() {
        return avgTime;
    }

    public Integer getBestLap() {
        return bestLap;
    }

    @Override
    public String toString() {
        return "ResultData{" +
                "racer='" + racer + '\'' +
                ", avgTime=" + avgTime +
                ", bestLap=" + bestLap +
                '}';
    }
}

@FunctionalInterface
interface Printer {
    void print(List<ResultData> data);
}

class RaceReport {

    void print(List<RaceData> raceData) {

        Objects.requireNonNull(raceData, "raceData is null");

        List<ResultData> resultDataList = new ArrayList<>();
        for (RaceData rd : raceData) {
            resultDataList.add(new ResultData(
                    rd.getRacer(),
                    average(rd.getLapsTime()),
                    bestLap(rd.getLapsTime())));
        }

        List<ResultData> sortedList = resultDataList.stream()
                .sorted(Comparator.comparing(ResultData::getAvgTime))
                .collect(toList());

        Printer consolePrinter = (data) -> {
            System.out.print("--------------------\n");
            System.out.print("Racer (winner) :" +  data.get(0).getRacer() + "\n");
            System.out.print("Average time: " + data.get(0).getAvgTime() + "m\n");
            System.out.print("Best lap: " + data.get(0).getBestLap() + "m\n");
            System.out.print("       -----         \n");

            data.stream().skip(1).forEach(v -> {
                System.out.print("Racer: " + v.getRacer() + "\n");
                System.out.print("Average time: " + v.getAvgTime() + "m\n");
                System.out.print("Best lap: " + v.getBestLap() + "m\n");
                System.out.print("       -----         \n");
            });
            System.out.print("--------------------\n");
        };

        consolePrinter.print(sortedList);

    }

    private Double average(List<Integer> times) {
        return times.stream().collect(averagingInt(Integer::intValue));
    }

    private Integer bestLap(List<Integer> times) {
        return times.stream().max(Integer::compareTo).get();
    }

    private Integer sum(List<Integer> times) {
        return times.stream().collect(summingInt(Integer::intValue));
    }
}
