package bootstrap;

import org.apache.activemq.xbean.XBeanBrokerService;
import org.springframework.context.support.GenericXmlApplicationContext;

import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.Play;

public class Global extends GlobalSettings {

	private static GenericXmlApplicationContext springContext;

	@Override
	public void onStart(final Application app) {

		//loadSpringContext();

		super.onStart(app);
	}

	protected void loadSpringContext() {
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
	}

	@Override
	public void onStop(final Application app) {
		//destroySpringContext();

		super.onStop(app);
	}

	protected void destroySpringContext() {
		XBeanBrokerService brokerService = springContext
				.getBean(XBeanBrokerService.class);

		if (brokerService != null) {
			try {
				brokerService.stop();
			} catch (Exception e) {
				Logger.error("Error stopping the JMS broker", e);
			}
		}

		// close the spring context
		springContext.close();
	}

	public static <T> T getBean(final Class<T> clazz) {
		return springContext.getBean(clazz);
	}
}
