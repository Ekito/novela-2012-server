package messaging;

import javax.jms.Message;
import javax.jms.MessageListener;

import play.Logger;

public class EchoMessageListener implements MessageListener {

	@Override
	public void onMessage(final Message message) {
		Logger.info("Received a message");

	}

}
