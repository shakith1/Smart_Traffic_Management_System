package lk.oxo.urbantraffic.ejb.impl;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import lk.oxo.urbantraffic.ejb.remote.TrafficDataAnalysis;
import lk.oxo.urbantraffic.ejb.remote.TrafficDataStorage;
import lk.oxo.urbantraffic.ejb.util.TrafficLevel;
import lk.oxo.urbantraffic.ejb.util.TrafficUtil;
import lk.oxo.urbantraffic.model.TrafficData;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public Map<LocalDateTime, List<TrafficData>> filterTrafficDataByRushHour() {
        Map<LocalDate, List<TrafficData>> trafficDataByDate = getTrafficDataByDate();
        Map<LocalDateTime, List<TrafficData>> rushHourTrafficData = new HashMap<>();

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

            rushHourTrafficData.put(date.atTime(7, 0), morningList);
            rushHourTrafficData.put(date.atTime(16, 0), eveningList);
        }
        return rushHourTrafficData;
    }

    private boolean checkRushHourRange(LocalTime time, LocalTime start, LocalTime end) {
        return !time.isBefore(start) && !time.isAfter(end);
    }

    public Map<LocalDateTime, Double>  calculateAverageSpeedRushHour() {
        Map<LocalDateTime, List<TrafficData>> rushHourData = filterTrafficDataByRushHour();
        Map<LocalDateTime, Double> averageSpeeds = new HashMap<>();

        for (LocalDateTime dateTime : rushHourData.keySet()) {
            List<TrafficData> trafficDataList = rushHourData.get(dateTime);

            double totalSpeed = 0, averageSpeed = 0;

            for (TrafficData trafficData : trafficDataList) {
                totalSpeed += trafficData.getVehicleSpeed();
            }

            if (!trafficDataList.isEmpty()) {
                averageSpeed = totalSpeed / trafficDataList.size();
            }
            averageSpeeds.put(dateTime, averageSpeed);
        }
        return averageSpeeds;
    }

    public HashMap<LocalDateTime, TrafficLevel> analyzeTrafficLevelOnRushHour(){
        Map<LocalDateTime, Double> rushHourData = calculateAverageSpeedRushHour();

        HashMap<LocalDateTime, TrafficLevel> analyzedData = new HashMap<>();

        for (LocalDateTime dateTime: rushHourData.keySet()){
            Double averageSpeed = rushHourData.get(dateTime);
            if(averageSpeed >TrafficUtil.LOW_TRAFFIC)
                analyzedData.put(dateTime, TrafficLevel.LOW_TRAFFIC);
            else if(averageSpeed > TrafficUtil.HIGH_TRAFFIC)
                analyzedData.put(dateTime, TrafficLevel.MODERATE_TRAFFIC);
            else
                analyzedData.put(dateTime, TrafficLevel.HIGH_TRAFFIC);
        }
        return analyzedData;
    }
}
