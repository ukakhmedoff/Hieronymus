package ru.snatcher.hieronymus.tools;

import android.app.Application;
import android.content.Context;
import android.support.test.runner.AndroidJUnitRunner;

import ru.snatcher.hieronymus.di.TestApp;

/**
 * {@link MockTestRunner}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
public class MockTestRunner extends AndroidJUnitRunner {
	@Override
	public Application newApplication(
			ClassLoader cl, String className, Context context)
			throws InstantiationException,
			IllegalAccessException,
			ClassNotFoundException {
		return super.newApplication(
				cl, TestApp.class.getName(), context);
	}
}