package lk.oxo.urbantraffic;

import lk.oxo.urbantraffic.generator.TrafficDataGenerator;
import lk.oxo.urbantraffic.model.TrafficData;
import lk.oxo.urbantraffic.model.TrafficLightStatus;

import java.util.Random;

public class Main {
   static Random random = new Random();
    public static void main(String[] args) {
//        System.out.println(Math.round((random.nextDouble() *100)*1e2)/1e2);
//        System.out.println(6.8+random.nextDouble() * (6.95 - 6.8));
//        TrafficDataGenerator generator = new TrafficDataGenerator();
//        TrafficData trafficData = generator.generateTrafficData();
        TrafficData trafficData = TrafficDataGenerator.generateTrafficData();
        System.out.println(trafficData);
    }
}
