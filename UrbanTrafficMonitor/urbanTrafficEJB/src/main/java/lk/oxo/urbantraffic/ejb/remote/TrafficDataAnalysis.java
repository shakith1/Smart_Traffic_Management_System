package lk.oxo.urbantraffic.ejb.remote;

import lk.oxo.urbantraffic.ejb.util.TrafficLevel;
import lk.oxo.urbantraffic.model.TrafficData;
import lk.oxo.urbantraffic.model.TrafficZone;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface TrafficDataAnalysis {
    double calculateAverageSpeed();
    Map<LocalDate, List<TrafficData>> getTrafficDataByDate();
    Map<LocalDateTime, List<TrafficData>> filterTrafficDataByRushHour();
    Map<LocalDateTime, Double>  calculateAverageSpeedRushHour();
    HashMap<LocalDateTime, TrafficLevel> analyzeTrafficLevelOnRushHour();
    Map<LocalDate, Map<TrafficZone, List<TrafficData>>> getTrafficDataByDateAndZone();
}
