package lk.oxo.urbantraffic.generator;

import lk.oxo.urbantraffic.model.TrafficData;
import lk.oxo.urbantraffic.model.TrafficLightStatus;
import lk.oxo.urbantraffic.model.TrafficZone;
import lk.oxo.urbantraffic.util.TrafficUtils;

import java.util.Random;

public class TrafficDataGenerator {
    private static final Random random = new Random();

    public static TrafficData generateTrafficData() {
        double latitude = generateLatitude();
        double longitude = generateLongitude();
        TrafficZone trafficZone = generateZone();
        TrafficLightStatus lightStatus = generateTrafficLightStatus();
        long vehicleSpeed = generateVehicleSpeed(lightStatus);
        return new TrafficData(vehicleSpeed, lightStatus, latitude, longitude, trafficZone);
    }

    private static long generateVehicleSpeed(TrafficLightStatus lightStatus) {
        long vehicleSpeed = 0;

        switch (lightStatus) {
            case RED:
                vehicleSpeed = Math.round(random.nextDouble() * TrafficUtils.RED_SPEED);
                break;
            case YELLOW:
                vehicleSpeed = Math.round(random.nextDouble() * TrafficUtils.YELLOW_SPEED);
                break;
            case GREEN:
                vehicleSpeed = Math.round(random.nextDouble() * TrafficUtils.MAX_SPEED);
                break;
        }
        return vehicleSpeed;
    }

    private static TrafficLightStatus generateTrafficLightStatus() {
        int index = random.nextInt(TrafficLightStatus.values().length);
        return TrafficLightStatus.values()[index];
    }

    private static double generateLatitude() {
        // Generate random latitude within Colombo
        double latitude = TrafficUtils.CITY_LATITUDE_START +
                (random.nextDouble() * (TrafficUtils.CITY_LATITUDE_END - TrafficUtils.CITY_LATITUDE_START));
        return Math.round(latitude * 1e6) / 1e6;
    }

    private static double generateLongitude() {
        // Generate random longitude within Colombo
        double longitude = TrafficUtils.CITY_LONGITUDE_START +
                (random.nextDouble() * (TrafficUtils.CITY_LONGITUDE_END - TrafficUtils.CITY_LONGITUDE_START));
        return Math.round(longitude * 1e6) / 1e6;
    }

    private static TrafficZone generateZone() {
        int index = random.nextInt(TrafficZone.values().length);
        return TrafficZone.values()[index];
    }
}
