package lk.oxo.urbantraffic.ejb.remote;

import lk.oxo.urbantraffic.ejb.util.Efficiency;
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
    Map<LocalDateTime, TrafficLevel> analyzeTrafficLevelOnRushHour();
    Map<LocalDate, Map<TrafficZone, List<TrafficData>>> getTrafficDataByDateAndZone();
    Map<LocalDate, Map<TrafficZone, Efficiency>> calculateUrbanMobilityEfficiency();
}
