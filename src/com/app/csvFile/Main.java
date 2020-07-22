package com.app.csvFile;

import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) {
        List<Match> matches = getMatchData();
        List<Delivery> deliveries = getDeliveriesData();
        System.out.println("*******PROBLEM 1 SOLUTION************");
        findMatchesPlayedPerYear(matches);
        System.out.println("*******PROBLEM 2 SOLUTION************");
        matchesWonOfAllTeams(matches);
        System.out.println("*******PROBLEM 3 SOLUTION************");
        for2016GetTheExtraRunsConcededPerTeam(matches);
        System.out.println("*******PROBLEM 4 SOLUTION************");
        doSomething(matches, deliveries);

    }

    private static void for2016GetTheExtraRunsConcededPerTeam(List<Match> matches) {
        Map<String, Integer> collectionOfYear = new HashMap<String, Integer>();
        for (Match matchObject : matches) {
            String winner=matchObject.getWinner();
            if(matchObject.getSeason()==2016) {
                if (collectionOfYear.containsKey(winner)) {
                    collectionOfYear.put(winner, collectionOfYear.get(winner) + matchObject.getWinByRuns());
                } else {
                    collectionOfYear.put(winner, matchObject.getWinByRuns());
                }
            }
        }
        System.out.println("Winner Team name"+"              "+"Extra runs");
        for(String mapElement:collectionOfYear.keySet())
            System.out.println((mapElement+"                   "+collectionOfYear.get(mapElement)));
        System.out.println();
        System.out.println();
    }


    private static void matchesWonOfAllTeams(List<Match> matches) {
        Map<String, Integer> collectionOfYear = new HashMap<String, Integer>();
        for (Match matchObject : matches) {
            String winner=matchObject.getWinner();
            if(winner.equals(""))
                continue;
            if (collectionOfYear.containsKey(winner)) {
                collectionOfYear.put(winner, collectionOfYear.get(winner) + 1);
            } else {
                collectionOfYear.put(winner, 1);
            }
        }
        System.out.println("Team name"+"              "+"Total winning in all year");
        for(String mapElement:collectionOfYear.keySet())
            System.out.println((mapElement+"                   "+collectionOfYear.get(mapElement)));
        System.out.println();
        System.out.println();
    }

    private static void findMatchesPlayedPerYear(List<Match> matches) {
        Map<Integer, Integer> collectionOfYear = new HashMap<Integer, Integer>();
        for (Match matchObject : matches) {
            int season = matchObject.getSeason();
            if (collectionOfYear.containsKey(season)) {
                collectionOfYear.put(season, collectionOfYear.get(season) + 1);
            } else {
                collectionOfYear.put(season, 1);
            }
        }
        System.out.println("Season/Year"+"              "+"Total participation");
        for(Integer mapElement:collectionOfYear.keySet())
            System.out.println((mapElement+"      "+collectionOfYear.get(mapElement)));
        System.out.println();System.out.println();
    }

    private static void doSomething(List<Match> matches, List<Delivery> deliveries) {
        Map<String,Integer> overuse=new HashMap<String,Integer>();
        Map<String,Integer> totalrunuse=new HashMap<String,Integer>();
        Set<String> storeUniqueWinner=new HashSet<String>();
        int r=0;
        for (Match matchObject : matches) {
            if (matchObject.getSeason()==2015)
                storeUniqueWinner.add(matchObject.getWinner());
        }
        for (Delivery deliveriesElement : deliveries) {
            String s = deliveriesElement.getBattingTeam();
            if (overuse.containsKey(s) && storeUniqueWinner.contains(s))
                overuse.put(deliveriesElement.getBowlerName(), overuse.get(deliveriesElement.getBowlerName()) + deliveriesElement.getOver());
            else
                overuse.put(deliveriesElement.getBowlerName(), deliveriesElement.getOver());
        }
        for (Delivery deliveriesElement : deliveries) {
            if (totalrunuse.containsKey(deliveriesElement.getBattingTeam()) && storeUniqueWinner.contains(deliveriesElement.getBattingTeam()))
                totalrunuse.put(deliveriesElement.getBowlerName(), totalrunuse.get(deliveriesElement.getBowlerName()) +deliveriesElement.getTotalRuns());
            else
                totalrunuse.put(deliveriesElement.getBowlerName(), deliveriesElement.getTotalRuns());
        }
        for(String find:totalrunuse.keySet())
        {
            if(overuse.get(find)==0)
                continue;
            totalrunuse.put(find,totalrunuse.get(find)/overuse.get(find));
        }
        int max=0,min=0;
        for(String find:totalrunuse.keySet())
            max=Math.max(max,totalrunuse.get(find));
        for(String find:totalrunuse.keySet())
        {
            if(max==totalrunuse.get(find))
                System.out.println(find);
        }
    }

    private static List<Delivery> getDeliveriesData() {
        List<Delivery> deliveries =new ArrayList<>();
        String csvFile = "/home/quree/IdeaProjects/iplProjectWithSetterGetter/src/com/app/csvFile/deliveries.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        int firstRow = 0;
        try
        {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null)
            {
                if (firstRow == 0)
                {
                    firstRow += 1;
                    continue;
                }
                String[] data = line.split(cvsSplitBy);
                Delivery delivery = new Delivery();
                delivery.setMatchId(Integer.parseInt(data[0]));
                delivery.setInnings(Integer.parseInt(data[1]));
                delivery.setBattingTeam(data[2]);
                delivery.setBowlingTeam(data[3]);
                delivery.setOver(Integer.parseInt(data[4]));
                delivery.setBall(Integer.parseInt(data[5]));
                delivery.setBatsmanName(data[6]);
                delivery.setBowlerName(data[8]);
                delivery.setWideRuns(Integer.parseInt(data[10]));
                delivery.setByeRuns(Integer.parseInt(data[11]));
                delivery.setLegByeRuns(Integer.parseInt(data[12]));
                delivery.setNoballRuns(Integer.parseInt(data[13]));
                delivery.setPenaltyRuns(Integer.parseInt(data[14]));
                delivery.setBatsmanRuns(Integer.parseInt(data[15]));
                delivery.setExtraRuns(Integer.parseInt(data[16]));
                delivery.setTotalRuns(Integer.parseInt(data[17]));
                deliveries.add(delivery);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return deliveries;
    }

    private static List<Match> getMatchData()
    {
        List<Match> matches =new ArrayList<>();
        String csvFile = "/home/quree/IdeaProjects/iplProjectWithSetterGetter/src/com/app/csvFile/matches.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        int firstRow = 0;
        try
        {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null)
            {
                if (firstRow == 0)
                {
                    firstRow += 1;
                    continue;
                }
                String[] data = line.split(cvsSplitBy);
                Match match = new Match();
                match.setMatchId(Integer.parseInt((data[0])));
                match.setSeason(Integer.parseInt((data[1])));
                match.setCity(data[2]);
                match.setDate(data[3]);
                match.setTeam1(data[4]);
                match.setTeam2(data[5]);
                match.setTossWinner(data[6]);
                match.setTossDecision(data[7]);
                match.setResult(data[8]);
                match.setWinner(data[10]);
                match.setWinByRuns(Integer.parseInt((data[11])));
                matches.add(match);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return matches;
    }
}