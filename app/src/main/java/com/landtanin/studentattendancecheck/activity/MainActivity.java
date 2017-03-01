package com.landtanin.studentattendancecheck.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.landtanin.studentattendancecheck.R;
import com.landtanin.studentattendancecheck.databinding.ActivityMainBinding;
import com.landtanin.studentattendancecheck.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding b;

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



        }

    }

    private void initInstance() {

        setSupportActionBar(b.mainActivityToolbar);

    }


}
