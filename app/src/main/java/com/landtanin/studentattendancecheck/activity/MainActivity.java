package com.landtanin.studentattendancecheck.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.landtanin.studentattendancecheck.R;
import com.landtanin.studentattendancecheck.databinding.ActivityMainBinding;
import com.landtanin.studentattendancecheck.fragment.FragmentHome;
import com.landtanin.studentattendancecheck.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding b;
    private boolean fakeBol = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initInstance();

        if (savedInstanceState==null) {

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer,
                            MainFragment.newInstance(),
                            "MainFragment")
                    .commit();

            FragmentHome fragmentHome = FragmentHome.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer,
                            fragmentHome,
                            "FragmentHome")
                    .detach(fragmentHome)
                    .commit();

        }

    }

    private void initInstance() {

        setSupportActionBar(b.mainActivityToolbar);

    }

    @Override
    protected void onResume() {

        // TODO: check if there's data in the database, delete fakeBol
        if (fakeBol) {
            fakeBol = false;
            super.onResume();
        } else {

            MainFragment mainFragment = (MainFragment)
                    getSupportFragmentManager().findFragmentByTag("MainFragment");
            FragmentHome fragmentHome = (FragmentHome)
                    getSupportFragmentManager().findFragmentByTag("FragmentHome");
            getSupportFragmentManager().beginTransaction()
                    .attach(fragmentHome)
                    .detach(mainFragment)
                    .commit();

        }
        super.onResume();
    }
}
