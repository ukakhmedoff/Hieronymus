package ru.snatcher.hieronymus.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import ru.snatcher.hieronymus.R;
import ru.snatcher.hieronymus.view.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {

	private Fragment fMainFragment;

	private FragmentManager fFragmentManager;
	private FragmentTransaction fFragmentTransaction;

	private BottomNavigationView.OnNavigationItemSelectedListener fOnNavigationItemSelectedListener
			= new BottomNavigationView.OnNavigationItemSelectedListener() {

		@Override
		public boolean onNavigationItemSelected(@NonNull MenuItem item) {

			switch (item.getItemId()) {
				case R.id.navigation_main:
					fFragmentTransaction = fFragmentManager.beginTransaction();
					fFragmentTransaction.replace(R.id.content, fMainFragment).commit();
					return true;
				case R.id.navigation_dashboard:
					fFragmentTransaction = fFragmentManager.beginTransaction();
					fFragmentTransaction.replace(R.id.content, fMainFragment).commit();
					return true;
				case R.id.navigation_settings:
					fFragmentTransaction = fFragmentManager.beginTransaction();
					fFragmentTransaction.replace(R.id.content, fMainFragment).commit();
					return true;
			}
			return false;
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		fFragmentManager = getSupportFragmentManager();
		fMainFragment = new MainFragment();
		fFragmentTransaction = fFragmentManager.beginTransaction();
		fFragmentTransaction.add(R.id.content, fMainFragment).commit();

		BottomNavigationView lvNavigation = (BottomNavigationView) findViewById(R.id.navigation);
		lvNavigation.setOnNavigationItemSelectedListener(fOnNavigationItemSelectedListener);
		lvNavigation.setSelected(true);
	}

}
