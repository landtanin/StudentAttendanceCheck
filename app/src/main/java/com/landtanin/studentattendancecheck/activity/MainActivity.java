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

//            AddModuleFragment addModuleFragment = AddModuleFragment.newInstance();
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.contentContainer,
//                            addModuleFragment,
//                            "AddModuleFragment")
//                    .detach(addModuleFragment)
//                    .commit();

        }

    }

    private void initInstance() {

        setSupportActionBar(b.mainActivityToolbar);

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        return super.onOptionsItemSelected(item);
//    }

//    @Override
//    public void onBackPressed() {
//        MainFragment mainFragment = (MainFragment)
//                getSupportFragmentManager().findFragmentByTag("MainFragment");
//        AddModuleFragment addModuleFragment = (AddModuleFragment)
//                getSupportFragmentManager().findFragmentByTag("AddModuleFragment");
//        getFragmentManager().beginTransaction()
////                .setCustomAnimations(
////                        R.anim.from_right, R.anim.to_left,
////                        R.anim.from_left, R.anim.to_right)
////                .attach(mainFragment)
//                .detach(addModuleFragment)
//                .commit();
//    }
}
