package com.landtanin.studentattendancecheck.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.landtanin.studentattendancecheck.R;
import com.landtanin.studentattendancecheck.databinding.ActivityCheckInBinding;
import com.landtanin.studentattendancecheck.fragment.verifying.FaceRecogFragment;
import com.landtanin.studentattendancecheck.fragment.verifying.LocationFragment;

public class CheckInActivity extends AppCompatActivity {

    ActivityCheckInBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_check_in);

        initInstance();

        if (savedInstanceState==null) {

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.checkInContentContainer,
                            FaceRecogFragment.newInstance(),
                            "LocationFragment")
                    .commit();

            LocationFragment locationFragment = LocationFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.checkInContentContainer,
                            locationFragment,
                            "FaceRecogFragment")
                    .detach(locationFragment)
                    .commit();

        }

    }

    private void initInstance() {

        setSupportActionBar(b.checkInActivityToolbar);
    }
}
