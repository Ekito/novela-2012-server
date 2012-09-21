package bootstrap;

import org.springframework.context.support.GenericXmlApplicationContext;

import play.Application;
import play.GlobalSettings;

public class Global extends GlobalSettings {

	private static GenericXmlApplicationContext springContext;

	@Override
	public void onStart(final Application app) {

		loadSpringContext();

		super.onStart(app);
	}

	protected void loadSpringContext() {
		springContext = new GenericXmlApplicationContext();

		// start the Spring context
		springContext.load("/spring/springContext.xml");
		springContext.refresh();
	}

	@Override
	public void onStop(final Application app) {
		destroySpringContext();

		super.onStop(app);
	}

	protected void destroySpringContext() {

		// close the spring context
		springContext.close();
	}

	public static <T> T getBean(final Class<T> clazz) {
		return springContext.getBean(clazz);
	}
}
