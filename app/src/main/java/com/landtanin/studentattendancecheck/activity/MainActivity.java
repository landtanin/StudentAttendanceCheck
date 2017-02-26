package com.landtanin.studentattendancecheck.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.landtanin.studentattendancecheck.R;
import com.landtanin.studentattendancecheck.fragment.AddModuleFragment;
import com.landtanin.studentattendancecheck.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initInstance();

        if (savedInstanceState==null) {

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, MainFragment.newInstance())
                    .commit();

            AddModuleFragment addModuleFragment = AddModuleFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, addModuleFragment)
                    .detach(addModuleFragment)
                    .commit();

        }


    }

    private void initInstance() {



    }
}
