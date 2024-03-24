package lk.oxo.urbantraffic.ejb.remote;

import lk.oxo.urbantraffic.ejb.util.Efficiency;
import lk.oxo.urbantraffic.ejb.util.AnalyzedLevel;
import lk.oxo.urbantraffic.ejb.util.RushHour;
import lk.oxo.urbantraffic.ejb.util.TrafficLevel;
import lk.oxo.urbantraffic.model.TrafficData;
import lk.oxo.urbantraffic.model.TrafficZone;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface TrafficDataAnalysis {
    double calculateAverageSpeed();

    Map<LocalDate, Map<RushHour, AnalyzedLevel>> analyzeTrafficLevelOnRushHour();

    Map<LocalDate, Map<TrafficZone, List<TrafficData>>> getTrafficDataByDateAndZone();

    Map<LocalDate, Map<TrafficZone, Efficiency>> calculateUrbanMobilityEfficiency();
}
