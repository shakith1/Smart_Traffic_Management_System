package lk.oxo.urbantraffic.ejb.impl;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import lk.oxo.urbantraffic.ejb.remote.TrafficDataAnalysis;
import lk.oxo.urbantraffic.ejb.remote.TrafficDataStorage;
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
    private static final LocalTime MORNING_RUSH_HOUR_START = LocalTime.of(7, 0);
    private static final LocalTime MORNING_RUSH_HOUR_END = LocalTime.of(9, 59);
    private static final LocalTime EVENING_RUSH_HOUR_START = LocalTime.of(16, 0);
    private static final LocalTime EVENING_RUSH_HOUR_END = LocalTime.of(18, 59);
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
                if (checkRushHourRange(time, MORNING_RUSH_HOUR_START, MORNING_RUSH_HOUR_END))
                    morningList.add(trafficData);
                else if (checkRushHourRange(time, EVENING_RUSH_HOUR_START, EVENING_RUSH_HOUR_END))
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
}
