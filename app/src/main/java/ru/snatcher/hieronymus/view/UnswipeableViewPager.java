package ru.snatcher.hieronymus.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * {@link UnswipeableViewPager}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */

public class UnswipeableViewPager extends ViewPager {
	public UnswipeableViewPager(Context context) {
		super(context);
	}

	public UnswipeableViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		return false;
	}
}