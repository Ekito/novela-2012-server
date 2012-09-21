package messaging;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import models.Location;

import org.springframework.stereotype.Component;

@Component
public class LocationProducer {

	// @Autowired
	// private JmsTemplate template;
	//
	// @Autowired
	// private Topic locationTopic;
	//
	// public void publishLocation(final Location aLocation) {
	//
	// template.send(locationTopic, new MessageCreator() {
	//
	// @Override
	// public Message createMessage(final Session session)
	// throws JMSException {
	//
	// Logger.debug("Sending a location Message !");
	//
	// return session.createObjectMessage(aLocation);
	// }
	// });
	// }

	private MessageProducer producer;

	private Session session;

	public LocationProducer() throws NamingException, JMSException {

		Properties props = new Properties();
		props.put(InitialContext.INITIAL_CONTEXT_FACTORY,
				"com.kaazing.gateway.jms.client.stomp.StompInitialContextFactory");

		props.put(Context.PROVIDER_URL,
				"ws://ec2-46-137-8-173.eu-west-1.compute.amazonaws.com:8001/jms");
		InitialContext ctx = new InitialContext(props);
		ConnectionFactory connectionFactory = (ConnectionFactory) ctx
				.lookup("ConnectionFactory");

		Connection connection = connectionFactory.createConnection(null, null);

		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		Topic topic = (Topic) ctx.lookup("/topic/LocationTopic");

		producer = session.createProducer(topic);

	}

	public void publishLocation(final Location aLocation) {
		try {
			producer.send(session.createTextMessage("Hello World"));
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
