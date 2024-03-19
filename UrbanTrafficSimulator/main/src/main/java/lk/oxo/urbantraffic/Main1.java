package lk.oxo.urbantraffic;

import jakarta.jms.*;
import lk.oxo.urbantraffic.model.TrafficData;
import lk.oxo.urbantraffic.util.JMSUtil;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Main1 {
    public static void main(String[] args) {
        try {
            InitialContext context = new InitialContext();

            QueueConnectionFactory factory = (QueueConnectionFactory) context.lookup(JMSUtil.JMS_CONNECTION_FACTORY);
            Queue queue = (Queue) context.lookup(JMSUtil.JMS_DESTINATION_QUEUE);

            QueueConnection connection = factory.createQueueConnection();
            connection.start();
            QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

            QueueReceiver receiver = session.createReceiver(queue);

    receiver.setMessageListener(new MessageListener() {
        @Override
        public void onMessage(Message message) {
            try {
                System.out.println(message.getBody(TrafficData.class));
            } catch (JMSException e) {
                throw new RuntimeException(e);
            }
        }
    });

            connection.close();
        } catch (NamingException | JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
