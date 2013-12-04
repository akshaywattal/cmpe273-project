package edu.sjsu.cmpe.bigdata.stomp;

import java.net.URL;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.fusesource.stomp.jms.StompJmsConnectionFactory;
import org.fusesource.stomp.jms.StompJmsDestination;

import edu.sjsu.cmpe.bigdata.config.BigDataServiceConfiguration;

public class ApolloSTOMP {
	BigDataServiceConfiguration configuration = new BigDataServiceConfiguration();
	
	/*
     * Pulling Configurations from config file
     */
    String queueName = configuration.getStompQueueName();
    String topicName = configuration.getStompTopicName();
    String apolloUser = configuration.getApolloUser();
    String apolloPassword = configuration.getApolloPassword();
    String apolloHost = configuration.getApolloHost();
    int apolloPort = configuration.getApolloPort();
       
        /**
         * ApolloSTOMP constructor
         */
        public ApolloSTOMP() {
        }
        
        /**
         * Method to create a factory connection for Apollo Server
         */
        public Connection makeConnection() throws Exception {
                StompJmsConnectionFactory factory = new StompJmsConnectionFactory();
        factory.setBrokerURI("tcp://" + apolloHost + ":" + apolloPort);
        Connection connection = factory.createConnection(apolloUser, apolloPassword);
        return connection;
        }
        
        
        /**
         * Method to Publish Message to Topic
         */
        public void publishTopicMessage(Connection connection) throws Exception {
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                Destination dest = new StompJmsDestination(topicName);
                MessageProducer producer = session.createProducer(dest);
                producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
                TextMessage msg = session.createTextMessage("sentimentAnalysisDone");
                producer.send(msg);
        }
        
        /**
         * Method to close connection
         */
        public void endConnection(Connection connection) throws Exception {
                connection.close();
        }
}