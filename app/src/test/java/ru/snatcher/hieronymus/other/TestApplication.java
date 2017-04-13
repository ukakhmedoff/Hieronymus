package ru.snatcher.hieronymus.other;

import ru.snatcher.hieronymus.other.di.AppComponent;
import ru.snatcher.hieronymus.other.di.DaggerTestComponent;

/**
 * {@link TestApplication}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */

public class TestApplication extends App {

	@Override
	protected AppComponent buildComponent() {
		return DaggerTestComponent.builder()
				.build();
	}
}