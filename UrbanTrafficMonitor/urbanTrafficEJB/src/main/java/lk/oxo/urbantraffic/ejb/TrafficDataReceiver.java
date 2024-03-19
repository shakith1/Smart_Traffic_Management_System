package lk.oxo.urbantraffic.ejb;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import lk.oxo.urbantraffic.model.TrafficData;

@MessageDriven(
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "urbanTrafficQueue")
        }
)
public class TrafficDataReceiver implements MessageListener {
    @Override
    public void onMessage(Message message) {
        try {
            TrafficData trafficData = message.getBody(TrafficData.class);
            System.out.println(trafficData.getVehicleSpeed());
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
