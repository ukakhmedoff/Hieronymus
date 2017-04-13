package ru.snatcher.hieronymus.integration.other;

import ru.snatcher.hieronymus.integration.other.di.DaggerIntegrationTestComponent;
import ru.snatcher.hieronymus.other.App;
import ru.snatcher.hieronymus.other.di.AppComponent;

/**
 * {@link IntegrationTestApp}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */

public class IntegrationTestApp extends App {

	@Override
	protected AppComponent buildComponent() {
		return DaggerIntegrationTestComponent.builder()
				.build();
	}
}
