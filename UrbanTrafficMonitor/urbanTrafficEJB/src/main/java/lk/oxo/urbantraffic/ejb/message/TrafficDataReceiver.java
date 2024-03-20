package lk.oxo.urbantraffic.ejb.message;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.EJB;
import jakarta.ejb.MessageDriven;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.ObjectMessage;
import lk.oxo.urbantraffic.ejb.impl.TrafficAnalyseBean;
import lk.oxo.urbantraffic.ejb.remote.TrafficAnalyse;
import lk.oxo.urbantraffic.model.TrafficData;

import java.util.List;

@MessageDriven(
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "urbanTrafficQueue")
        }
)
public class TrafficDataReceiver implements MessageListener {
    @EJB
    TrafficAnalyse analyse;

    @Override
    public void onMessage(Message message) {
        try {
//            TrafficData trafficData = message.getBody(List<TrafficData>.);
//            System.out.println(trafficData.getVehicleSpeed());
            ObjectMessage objectMessage = (ObjectMessage) message;

            List<TrafficData> trafficData = (List<TrafficData>) objectMessage.getObject();
            System.out.println(trafficData);
            analyse.storeTrafficData(trafficData);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
