package messaging;

import javax.jms.Topic;

import models.Location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class LocationProducer {

	@Autowired
	private JmsTemplate template;

	@Autowired
	private Topic locationTopic;

	public void publishLocation(final Location aLocation) {

		// it uses messaging.JsonMessageConverter to serialize the object to
		// Json
		template.convertAndSend(locationTopic, aLocation);

	}
}
