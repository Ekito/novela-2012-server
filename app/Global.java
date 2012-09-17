import java.util.ArrayList;
import java.util.List;

import org.apache.activemq.xbean.XBeanBrokerService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.Play;

public class Global extends GlobalSettings {

	private ClassPathXmlApplicationContext springContext;

	@Override
	public void onStart(final Application app) {

		List<String> contexts = new ArrayList<String>();
		contexts.add("/spring/spring.xml");

		// add the embedded JMS broker in dev mode
		if (Play.isDev()) {
			contexts.add("/spring/jms-embedded.xml");
		}

		// start the Spring context
		springContext = new ClassPathXmlApplicationContext(
				contexts.toArray(new String[contexts.size()]), true);

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
