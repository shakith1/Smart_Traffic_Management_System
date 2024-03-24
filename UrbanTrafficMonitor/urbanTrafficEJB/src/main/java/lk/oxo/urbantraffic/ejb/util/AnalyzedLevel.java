package lk.oxo.urbantraffic.ejb.util;

public class AnalyzedLevel {
    private final Double averageSpeed;
    private final TrafficLevel trafficLevel;

    public AnalyzedLevel(Double averageSpeed, TrafficLevel trafficLevel) {
        this.averageSpeed = averageSpeed;
        this.trafficLevel = trafficLevel;
    }

    public Double getAverageSpeed() {
        return averageSpeed;
    }

    public TrafficLevel getTrafficLevel() {
        return trafficLevel;
    }
}
