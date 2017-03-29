package ru.snatcher.hieronymus.view;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import ru.snatcher.hieronymus.R;
import ru.snatcher.hieronymus.view.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {

    private static final String PAGER_POSITION = "PAGER_POSITION";

    private BottomNavigationView fNavigation;

    private BottomNavigationView.OnNavigationItemSelectedListener fOnNavigationItemSelectedListener
            = item -> {

        switch (item.getItemId()) {
            case R.id.navigation_main:
                showFragment(new MainFragment());
                return true;
            case R.id.navigation_dashboard:
                showFragment(new MainFragment());
                return true;
            case R.id.navigation_settings:
                showFragment(new MainFragment());
                return true;
        }
        return false;
    };

    private void showFragment(Fragment pFragment) {
        final FragmentTransaction lvFragmentTransaction = getSupportFragmentManager().beginTransaction();
        lvFragmentTransaction.replace(R.id.content, pFragment).commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showFragment(new MainFragment());

        fNavigation = (BottomNavigationView) findViewById(R.id.navigation);
        fNavigation.setOnNavigationItemSelectedListener(fOnNavigationItemSelectedListener);
        fNavigation.setSelected(true);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(PAGER_POSITION, fNavigation.getSelectedItemId());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        fNavigation.setSelectedItemId(savedInstanceState.getInt(PAGER_POSITION, 0));
    }
}
