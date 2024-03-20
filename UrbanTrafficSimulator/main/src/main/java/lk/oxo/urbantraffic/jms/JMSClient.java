package lk.oxo.urbantraffic.jms;

import jakarta.jms.*;
import lk.oxo.urbantraffic.generator.TrafficDataGenerator;
import lk.oxo.urbantraffic.model.TrafficData;
import lk.oxo.urbantraffic.util.JMSUtil;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class JMSClient {
    private static JMSClient instance;
    private final QueueConnectionFactory factory;
    private final Queue queue;
//    private final List<TrafficData> dataList;

    private JMSClient() {
//        dataList = new ArrayList<>();
        try {
            InitialContext context = new InitialContext();
            factory = (QueueConnectionFactory) context.lookup(JMSUtil.JMS_CONNECTION_FACTORY);
            queue = (Queue) context.lookup(JMSUtil.JMS_DESTINATION_QUEUE);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    public static JMSClient getInstance() {
        if (instance == null) {
            instance = new JMSClient();
        }
        return instance;
    }

//    public void addTrafficData(TrafficData trafficData){
//        dataList.add(trafficData);
//        if(dataList.size()>=JMSUtil.LIST_MAX_SIZE)
//            sendData();
//
//    }

    public void sendData(List<TrafficData> dataList) {
        if (!dataList.isEmpty()) {
            try (QueueConnection connection = factory.createQueueConnection();
                 QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE)) {
                QueueSender sender = session.createSender(queue);
                ObjectMessage message = session.createObjectMessage((Serializable) dataList);
                sender.send(message);
                dataList.clear();
            } catch (JMSException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
