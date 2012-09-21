package messaging;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.Topic;

import models.Location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import play.Logger;
import play.libs.Json;

@Component
public class LocationProducer {

	@Autowired
	private JmsTemplate template;

	@Autowired
	private Topic locationTopic;

	public void publishLocation(final Location aLocation) {

		template.send(locationTopic, new MessageCreator() {

			@Override
			public Message createMessage(final Session session)
					throws JMSException {

				String asText = Json.stringify(Json.toJson(aLocation));

				Logger.debug("Sending a location message: " + asText);

				return session.createTextMessage(asText);
			}
		});
	}
}
