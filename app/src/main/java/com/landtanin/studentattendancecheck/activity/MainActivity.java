package com.landtanin.studentattendancecheck.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.landtanin.studentattendancecheck.R;
import com.landtanin.studentattendancecheck.databinding.ActivityMainBinding;
import com.landtanin.studentattendancecheck.fragment.FragmentHome;
import com.landtanin.studentattendancecheck.fragment.MainFragment;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding b;
    private boolean fakeBol = true;
    private Realm realm;

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
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_logout) {

            realm = Realm.getDefaultInstance();
            RealmConfiguration realmConfig = realm.getConfiguration();
            Realm.deleteRealm(realmConfig);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {

        // TODO: check if there's data in the database, delete fakeBol
        String add_or_not = getIntent().getStringExtra("add_or_not");

        if (add_or_not.equals("add")) {

            // TODO: go to add module

            super.onResume();

        } else if (add_or_not.equals("not_add")) {

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
