package lk.oxo.urbantraffic.ejb.remote;

import lk.oxo.urbantraffic.model.TrafficData;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface TrafficDataAnalysis {
    double calculateAverageSpeed();
    Map<LocalDate, List<TrafficData>> getTrafficDataByDate();
    Map<LocalDateTime, List<TrafficData>> filterTrafficDataByRushHour();
}
