package ru.snatcher.hieronymus.di;

import ru.snatcher.hieronymus.other.App;

/**
 * {@link TestApp}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
public class TestApp extends App {

	@Override
	protected TestComponent buildComponent() {
		return DaggerTestComponent.builder().build();
	}
}