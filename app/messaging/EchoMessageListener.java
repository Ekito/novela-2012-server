package messaging;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import play.Logger;

public class EchoMessageListener implements MessageListener {

	@Override
	public void onMessage(final Message message) {

		if (message instanceof TextMessage) {
			TextMessage textMessage = (TextMessage) message;
			try {
				Logger.info("Received a message '" + textMessage.getText()
						+ "'");
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
