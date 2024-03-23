package lk.oxo.urbantraffic.generator;

import lk.oxo.urbantraffic.jms.JMSClient;
import lk.oxo.urbantraffic.model.TrafficData;
import lk.oxo.urbantraffic.model.TrafficLightStatus;
import lk.oxo.urbantraffic.model.TrafficZone;
import lk.oxo.urbantraffic.util.TrafficUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TrafficDataGenerator {
    private final Random random;
    private final JMSClient jmsClient;

    public TrafficDataGenerator() {
        random = new Random();
        jmsClient = JMSClient.getInstance();
    }

    public void generateData(int dataCount) {
        List<TrafficData> dataList = new ArrayList<>();
        LocalDateTime currentTime = TrafficUtils.START_TIME;

        for (int i = 0; i < dataCount; i++) {
            TrafficData trafficData = generateTrafficData();
            trafficData.setTimeStamp(currentTime);
            System.out.println(trafficData);
            dataList.add(trafficData);

            if (dataList.size() >= TrafficUtils.LIST_MAX_SIZE) {
                currentTime = currentTime.plusHours(2);
                if(currentTime.toLocalTime().isAfter(TrafficUtils.END_TIME.toLocalTime()))
                    currentTime = LocalDateTime.of(currentTime.toLocalDate().plusDays(1),
                            TrafficUtils.START_TIME.toLocalTime());
                jmsClient.sendData(dataList);
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private TrafficData generateTrafficData() {
        double latitude = generateLatitude();
        double longitude = generateLongitude();
        TrafficZone trafficZone = generateZone();
        TrafficLightStatus lightStatus = generateTrafficLightStatus();
        long vehicleSpeed = generateVehicleSpeed(lightStatus);
        return new TrafficData(vehicleSpeed, lightStatus, latitude, longitude, trafficZone);
    }

    private long generateVehicleSpeed(TrafficLightStatus lightStatus) {
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

    private TrafficLightStatus generateTrafficLightStatus() {
        double randomNumber = random.nextDouble();
        if(randomNumber < TrafficUtils.RED_PROBABILITY)
            return TrafficLightStatus.RED;
        else if(randomNumber < TrafficUtils.RED_PROBABILITY + TrafficUtils.GREEN_PROBABILITY)
            return TrafficLightStatus.GREEN;
        else
            return TrafficLightStatus.YELLOW;
    }

    private double generateLatitude() {
        // Generate random latitude within Colombo
        double latitude = TrafficUtils.CITY_LATITUDE_START +
                (random.nextDouble() * (TrafficUtils.CITY_LATITUDE_END - TrafficUtils.CITY_LATITUDE_START));
        return Math.round(latitude * 1e6) / 1e6;
    }

    private double generateLongitude() {
        // Generate random longitude within Colombo
        double longitude = TrafficUtils.CITY_LONGITUDE_START +
                (random.nextDouble() * (TrafficUtils.CITY_LONGITUDE_END - TrafficUtils.CITY_LONGITUDE_START));
        return Math.round(longitude * 1e6) / 1e6;
    }

    private TrafficZone generateZone() {
        int index = random.nextInt(TrafficZone.values().length);
        return TrafficZone.values()[index];
    }
}
