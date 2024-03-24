package lk.oxo.urbantraffic.ejb.impl;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import lk.oxo.urbantraffic.ejb.remote.TrafficDataAnalysis;
import lk.oxo.urbantraffic.ejb.remote.TrafficDataStorage;
import lk.oxo.urbantraffic.ejb.util.*;
import lk.oxo.urbantraffic.model.TrafficData;
import lk.oxo.urbantraffic.model.TrafficZone;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class TrafficDataAnalysisBean implements TrafficDataAnalysis {
    @EJB
    TrafficDataStorage dataStorage;

    @Override
    public double calculateAverageSpeed() {
        if (dataStorage.getTrafficDataList().isEmpty())
            return 0;

        long totalSpeed = 0;
        int totalCount = 0;

        for (TrafficData trafficData : dataStorage.getTrafficDataList()) {
            totalSpeed += trafficData.getVehicleSpeed();
            totalCount++;
        }

        return (double) totalSpeed / totalCount;
    }

    @Override
    public Map<LocalDate, List<TrafficData>> getTrafficDataByDate() {
        Map<LocalDate, List<TrafficData>> dataByDate = new HashMap<>();

        for (TrafficData trafficData : dataStorage.getTrafficDataList()) {
            LocalDate date = trafficData.getTimeStamp().toLocalDate();

            List<TrafficData> dataList = dataByDate.computeIfAbsent(date, k -> new ArrayList<>());

            dataList.add(trafficData);
        }
        return dataByDate;
    }

    @Override
    public Map<LocalDate, Map<TrafficZone, List<TrafficData>>> getTrafficDataByDateAndZone() {
        Map<LocalDate, Map<TrafficZone, List<TrafficData>>> dataByDateAndZone = new HashMap<>();

        for (TrafficData trafficData : dataStorage.getTrafficDataList()) {
            LocalDate date = trafficData.getTimeStamp().toLocalDate();
            TrafficZone trafficZone = trafficData.getTrafficZone();

            Map<TrafficZone, List<TrafficData>> trafficZoneData = dataByDateAndZone.computeIfAbsent(date, k -> new HashMap<>());
            List<TrafficData> dataList = trafficZoneData.computeIfAbsent(trafficZone, k -> new ArrayList<>());

            dataList.add(trafficData);
        }
        return dataByDateAndZone;
    }

    @Override
    public Map<LocalDate, Map<RushHour, List<TrafficData>>> filterTrafficDataByRushHour() {
        Map<LocalDate, List<TrafficData>> trafficDataByDate = getTrafficDataByDate();
        Map<LocalDate, Map<RushHour, List<TrafficData>>> rushHourTrafficData = new HashMap<>();

        for (LocalDate date : trafficDataByDate.keySet()) {
            List<TrafficData> dataList = trafficDataByDate.get(date);

            List<TrafficData> morningList = new ArrayList<>();
            List<TrafficData> eveningList = new ArrayList<>();

            for (TrafficData trafficData : dataList) {
                LocalTime time = trafficData.getTimeStamp().toLocalTime();
                if (checkRushHourRange(time, TrafficUtil.MORNING_RUSH_HOUR_START, TrafficUtil.MORNING_RUSH_HOUR_END))
                    morningList.add(trafficData);
                else if (checkRushHourRange(time, TrafficUtil.EVENING_RUSH_HOUR_START, TrafficUtil.EVENING_RUSH_HOUR_END))
                    eveningList.add(trafficData);
            }

            Map<RushHour, List<TrafficData>> rushHourMap = new HashMap<>();
            rushHourMap.put(RushHour.MORNING, morningList);
            rushHourMap.put(RushHour.EVENING, eveningList);

            rushHourTrafficData.put(date, rushHourMap);
        }
        return rushHourTrafficData;
    }

    private boolean checkRushHourRange(LocalTime time, LocalTime start, LocalTime end) {
        return !time.isBefore(start) && !time.isAfter(end);
    }

    public Map<LocalDate, Map<RushHour, Double>> calculateAverageSpeedRushHour() {
        Map<LocalDate, Map<RushHour, List<TrafficData>>> rushHourData = filterTrafficDataByRushHour();
        Map<LocalDate, Map<RushHour, Double>> averageSpeeds = new HashMap<>();

        for (LocalDate date : rushHourData.keySet()) {
            Map<RushHour, List<TrafficData>> rushHourMap = rushHourData.get(date);
            Map<RushHour, Double> averageSpeedMap = new HashMap<>();

            for (RushHour rushHour : rushHourMap.keySet()) {
                List<TrafficData> trafficDataList = rushHourMap.get(rushHour);
                double totalSpeed = 0, averageSpeed = 0;

                for (TrafficData trafficData : trafficDataList) {
                    totalSpeed += trafficData.getVehicleSpeed();
                }

                if (!trafficDataList.isEmpty()) {
                    averageSpeed = totalSpeed / trafficDataList.size();
                }
                averageSpeedMap.put(rushHour, averageSpeed);
            }
            averageSpeeds.put(date, averageSpeedMap);
        }
        return averageSpeeds;
    }

    public Map<LocalDate, Map<RushHour, AnalyzedLevel>> analyzeTrafficLevelOnRushHour() {
        Map<LocalDate, Map<RushHour, Double>> rushHourSpeeds = calculateAverageSpeedRushHour();

        Map<LocalDate, Map<RushHour, AnalyzedLevel>> result = new HashMap<>();

        for (LocalDate date : rushHourSpeeds.keySet()) {
            Map<RushHour, Double> rushHourSpeedMap = rushHourSpeeds.get(date);
            Map<RushHour, AnalyzedLevel> dateTrafficLevelMap = new HashMap<>();

            for (RushHour rushHour : rushHourSpeedMap.keySet()) {
                Double averageSpeed = rushHourSpeedMap.get(rushHour);
                TrafficLevel trafficLevel;

                if (averageSpeed > TrafficUtil.LOW_TRAFFIC) {
                    trafficLevel = TrafficLevel.LOW_TRAFFIC;
                } else if (averageSpeed > TrafficUtil.HIGH_TRAFFIC) {
                    trafficLevel = TrafficLevel.MODERATE_TRAFFIC;
                } else {
                    trafficLevel = TrafficLevel.HIGH_TRAFFIC;
                }

                AnalyzedLevel speedAndLevel = new AnalyzedLevel(averageSpeed, trafficLevel);
                dateTrafficLevelMap.put(rushHour, speedAndLevel);
            }
            result.put(date, dateTrafficLevelMap);
        }

        return result;
    }



    public Map<LocalDate, Map<TrafficZone, Efficiency>> calculateUrbanMobilityEfficiency() {
        Map<LocalDate, Map<TrafficZone, List<TrafficData>>> trafficDataByDateAndZone = getTrafficDataByDateAndZone();

        Map<LocalDate, Map<TrafficZone, Efficiency>> efficiencyMap = new HashMap<>();

        for (LocalDate date : trafficDataByDateAndZone.keySet()) {
            Map<TrafficZone, List<TrafficData>> zoneListMap = trafficDataByDateAndZone.get(date);

            HashMap<TrafficZone, Efficiency> dayEfficiency = new HashMap<>();

            for (TrafficZone trafficZone : zoneListMap.keySet()) {
                List<TrafficData> trafficDataList = zoneListMap.get(trafficZone);

                double totalSpeed = 0, averageSpeed = 0;
                int vehicleCount = 0;

                for (TrafficData trafficData : trafficDataList) {
                    totalSpeed += trafficData.getVehicleSpeed();
                    vehicleCount++;
                }

                if (vehicleCount > 0)
                    averageSpeed = totalSpeed / vehicleCount;

                Efficiency efficiencyCategory = getEfficiencyCategory(averageSpeed);
                dayEfficiency.put(trafficZone, efficiencyCategory);
            }
            efficiencyMap.put(date, dayEfficiency);
        }

        return efficiencyMap;
    }

    private Efficiency getEfficiencyCategory(double averageSpeed) {
        if (averageSpeed >= TrafficUtil.HIGH_THRESHOLD)
            return Efficiency.HIGH_EFFICIENCY;
        else if (averageSpeed >= TrafficUtil.MEDIUM_THRESHOLD)
            return Efficiency.MEDIUM_EFFICIENCY;
        else
            return Efficiency.LOW_EFFICIENCY;
    }
}
