import org.apache.activemq.xbean.XBeanBrokerService;
import org.springframework.context.support.GenericXmlApplicationContext;

import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.Play;

public class Global extends GlobalSettings {

	private GenericXmlApplicationContext springContext;

	@Override
	public void onStart(final Application app) {

		springContext = new GenericXmlApplicationContext();

		String context = new String();
		// add the embedded JMS broker in dev mode
		if (Play.isDev()) {
			context = "/spring/spring-dev.xml";
		} else if (Play.isProd()) {
			context = "/spring/spring-prod.xml";
		}

		// start the Spring context
		springContext.load(context);
		springContext.refresh();

		super.onStart(app);
	}

	@Override
	public void onStop(final Application app) {
		// stop the embedded broker
		XBeanBrokerService brokerService = springContext
				.getBean(XBeanBrokerService.class);

		try {
			brokerService.stop();
		} catch (Exception e) {
			Logger.error("Error stopping the JMS broker", e);
		}

		// close the spring context
		springContext.close();

		super.onStop(app);
	}

}
