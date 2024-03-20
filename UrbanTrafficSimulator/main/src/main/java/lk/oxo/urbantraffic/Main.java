package lk.oxo.urbantraffic;

import lk.oxo.urbantraffic.generator.TrafficDataGenerator;
import lk.oxo.urbantraffic.jms.JMSClient;
import lk.oxo.urbantraffic.model.TrafficData;
import java.util.Random;

public class Main {
   static Random random = new Random();
    public static void main(String[] args) {
//        System.out.println(Math.round((random.nextDouble() *100)*1e2)/1e2);
//        System.out.println(6.8+random.nextDouble() * (6.95 - 6.8));
//        TrafficDataGenerator generator = new TrafficDataGenerator();
//        TrafficData trafficData = generator.generateTrafficData();

//        JMSClient jmsClient = JMSClient.getInstance();
//        for (int i = 0; i < 20; i++) {
//            TrafficData trafficData = TrafficDataGenerator.generateTrafficData();
//            System.out.println(trafficData);
//            jmsClient.addTrafficData(trafficData);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }

        TrafficDataGenerator generator = new TrafficDataGenerator();
        generator.generateData(20);
//        JMSClient jmsClient = new JMSClient();
//        jmsClient.sendTrafficData(trafficData);
    }
}
