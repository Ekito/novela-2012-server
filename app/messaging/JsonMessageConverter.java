package messaging;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import play.libs.Json;

/**
 * Serialize an object to Json.
 * 
 * @author ndeverge
 * 
 */
public class JsonMessageConverter implements MessageConverter {

	@Override
	public Message toMessage(final Object object, final Session session)
			throws JMSException, MessageConversionException {

		String asText = Json.stringify(Json.toJson(object));

		return session.createTextMessage(asText);
	}

	@Override
	public Object fromMessage(final Message message) throws JMSException,
			MessageConversionException {

		return message;
	}

}
