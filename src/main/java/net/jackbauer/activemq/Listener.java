package net.jackbauer.activemq;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.qpid.jms.JmsConnectionFactory;

//import org.apache.qpid.jms.*;
//import javax.jms.*;

class Listener {
	public static void main(String[] args) throws JMSException {
		final String TOPIC_PREFIX = "topic://";
		final String QUEUE_PREFIX = "queue://";

		String user = env("ACTIVEMQ_USER", "admin");
		String password = env("ACTIVEMQ_PASSWORD", "admin");
		String host = env("ACTIVEMQ_HOST", "localhost");
		int port = Integer.parseInt(env("ACTIVEMQ_PORT", "5672"));

		String connectionURI = "amqp://" + host + ":" + port;
		String destinationName = arg(args, 0, "queue://event");

		JmsConnectionFactory factory = new JmsConnectionFactory(connectionURI);

		Connection connection = factory.createConnection(user, password);
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		Destination destination = null;
		if (destinationName.startsWith(TOPIC_PREFIX)) {
			destination = session.createTopic(destinationName.substring(TOPIC_PREFIX.length()));
		} else {
			destination = session.createQueue(destinationName);
		}

		MessageConsumer consumer = session.createConsumer(destination);
		long start = System.currentTimeMillis();
		long count = 1;
		System.out.println("Waiting for messages...");
		while (true) {
			Message msg = consumer.receive();
			if (msg instanceof TextMessage) {
				String body = ((TextMessage) msg).getText();
				if ("SHUTDOWN".equals(body)) {
					long diff = System.currentTimeMillis() - start;
					System.out.println(String.format("Received %d in %.2f seconds", count, (1.0 * diff / 1000.0)));
					connection.close();
					try {
						Thread.sleep(10);
					} catch (Exception e) {
					}
					System.exit(1);
				} else {
					try {
						if (count != msg.getIntProperty("id")) {
							System.out.println("mismatch: " + count + "!=" + msg.getIntProperty("id"));
						}
					} catch (NumberFormatException ignore) {
					}

					System.out.println(((TextMessage) msg).getText());
					
					if (count == 1) {
						start = System.currentTimeMillis();
					} else if (count % 10 == 0) {
						System.out.println(String.format("Received %d messages.", count));
					}
					count++;
				}

			} else {
				System.out.println("Unexpected message type: " + msg.getClass());
			}
		}
	}

	private static String env(String key, String defaultValue) {
		String rc = System.getenv(key);
		if (rc == null)
			return defaultValue;
		return rc;
	}

	private static String arg(String[] args, int index, String defaultValue) {
		if (index < args.length)
			return args[index];
		else
			return defaultValue;
	}
}