//package net.jackbauer.activemq;
//
//import javax.jms.Connection;
//import javax.jms.DeliveryMode;
//import javax.jms.Destination;
//import javax.jms.MessageProducer;
//import javax.jms.Session;
//import javax.jms.TextMessage;
//
//import org.apache.qpid.jms.JmsConnectionFactory;
//
//class Publisher {
//	final static String TOPIC_PREFIX = "topic://";
//	final static String QUEUE_PREFIX = "queue://";
//	
//	final static String ACTIVEMQ_USER = "admin";
//	final static String ACTIVEMQ_PASSWORD = "admin";
//	
//	final static String ACTIVEMQ_HOST = "localhost";
//	final static String ACTIVEMQ_PORT = "5672";
//	
//	public static void main(String[] args) throws Exception {
//		String connectionURI = "amqp://" + ACTIVEMQ_HOST + ":" + ACTIVEMQ_PORT;
//		String destinationName = arg(args, 0, "queue://event");
//
//		int messages = 10;
//
//		JmsConnectionFactory factory = new JmsConnectionFactory(connectionURI);
//
//		Connection connection = factory.createConnection(ACTIVEMQ_USER, ACTIVEMQ_PASSWORD);
//		connection.start();
//
//		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//
//		Destination destination = null;
//		if (destinationName.startsWith(TOPIC_PREFIX)) {
//			destination = session.createTopic(destinationName.substring(TOPIC_PREFIX.length()));
//		} else {
//			destination = session.createQueue(destinationName);
//		}
//
//		MessageProducer producer = session.createProducer(destination);
//		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
//
//		for (int i = 1; i <= messages; i++) {
//			TextMessage msg = session.createTextMessage("#:" + i);
//			msg.setIntProperty("id", i);
//			producer.send(msg);
//			if ((i % 10) == 0) {
//				System.out.println(String.format("Sent %d messages", i));
//			}
//		}
//
//		producer.send(session.createTextMessage("SHUTDOWN"));
//
//		Thread.sleep(1000 * 3);
//		connection.close();
//		System.exit(0);
//	}
//
//	private static String arg(String[] args, int index, String defaultValue) {
//		if (index < args.length)
//			return args[index];
//		else
//			return defaultValue;
//	}
//
//}