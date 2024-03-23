package lk.oxo.urbantraffic.util;

import java.time.LocalDateTime;

public class TrafficUtils {
    public static final int MAX_SPEED = 100;
    public static final int RED_SPEED = 0;
    public static final int YELLOW_SPEED = 10;
    public static final double CITY_LATITUDE_START = 6.8;
    public static final double CITY_LATITUDE_END = 6.95;
    public static final double CITY_LONGITUDE_START = 79.8;
    public static final double CITY_LONGITUDE_END = 79.9;
    public static final LocalDateTime START_TIME = LocalDateTime.of(2024,1,1,7,0);
    public static final LocalDateTime END_TIME = LocalDateTime.of(2024,1,1,18,59);
    public static final long LIST_MAX_SIZE = 2;
    public static final double RED_PROBABILITY = 0.2;
    public static final double GREEN_PROBABILITY = 0.6;
}
