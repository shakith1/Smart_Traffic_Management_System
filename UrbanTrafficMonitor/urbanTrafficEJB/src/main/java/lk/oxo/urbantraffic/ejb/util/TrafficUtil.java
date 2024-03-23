package lk.oxo.urbantraffic.ejb.util;

import java.time.LocalTime;

public class TrafficUtil {
    public static final LocalTime MORNING_RUSH_HOUR_START = LocalTime.of(7, 0);
    public static final LocalTime MORNING_RUSH_HOUR_END = LocalTime.of(9, 59);
    public static final LocalTime EVENING_RUSH_HOUR_START = LocalTime.of(16, 0);
    public static final LocalTime EVENING_RUSH_HOUR_END = LocalTime.of(18, 59);
    public static final double LOW_TRAFFIC = 60.0;
    public static final double HIGH_TRAFFIC = 30.0;
    public static final double HIGH_THRESHOLD = 50.0;
    public static final double MEDIUM_THRESHOLD = 30.0;
}
