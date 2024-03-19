package lk.oxo.urbantraffic.jms;

import jakarta.jms.*;
import lk.oxo.urbantraffic.model.TrafficData;
import lk.oxo.urbantraffic.util.JMSUtil;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JMSClient {
    public void sendTrafficData(TrafficData trafficData){
        try {
            InitialContext context = new InitialContext();

            QueueConnectionFactory factory = (QueueConnectionFactory) context.lookup(JMSUtil.JMS_CONNECTION_FACTORY);
            Queue queue = (Queue) context.lookup(JMSUtil.JMS_DESTINATION_QUEUE);

            QueueConnection connection = factory.createQueueConnection();
            QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

            QueueSender sender = session.createSender(queue);

            ObjectMessage message = session.createObjectMessage(trafficData);

            sender.send(message);

            connection.close();
        } catch (NamingException | JMSException e) {
            throw new RuntimeException(e);
        }

    }
}
