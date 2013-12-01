package edu.sjsu.cmpe.bigdata.stomp;

import java.net.URL;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.fusesource.stomp.jms.StompJmsConnectionFactory;
import org.fusesource.stomp.jms.StompJmsDestination;

public class ApolloSTOMP {
        private String apolloUser;
        private String apolloPassword;
        private String apolloHost;
        private int apolloPort;
        private String queueName;
        private String topicName;
        
        /**
         * ApolloSTOMP constructor
         */
        public ApolloSTOMP(String apolloUser, String apolloPassword,
                        String apolloHost, int apolloPort,  String queueName, String topicName) {
                this.apolloUser = apolloUser;
                this.apolloPassword = apolloPassword;
                this.apolloHost = apolloHost;
                this.apolloPort = apolloPort;
                this.queueName = queueName;
                this.topicName = topicName;
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
         * Method to send Message to the queue
         */
        public void sendQueueMessage(Connection connection, long ISBN) throws Exception {
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination dest = new StompJmsDestination(queueName);
        MessageProducer producer = session.createProducer(dest);
        TextMessage msg = session.createTextMessage("");
        msg.setLongProperty("id", System.currentTimeMillis());
        producer.send(msg);
        }
        
        /**
         * Method to subscribe to the Topic
         */
        public void subscribeTopic(Connection connection) throws Exception {
                connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination dest = new StompJmsDestination(topicName);
        MessageConsumer consumer = session.createConsumer(dest);
        
        while (true) {
            Message msg = consumer.receive();
            System.out.println("Received Book------->"+ ((TextMessage)msg).getText());
            }
        }
        
        /**
         * Method to close connection
         */
        public void endConnection(Connection connection) throws Exception {
                connection.close();
        }
}