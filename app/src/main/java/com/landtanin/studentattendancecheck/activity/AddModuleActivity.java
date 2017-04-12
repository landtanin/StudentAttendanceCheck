package com.landtanin.studentattendancecheck.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.landtanin.studentattendancecheck.R;
import com.landtanin.studentattendancecheck.databinding.ActivityAddModuleBinding;
import com.landtanin.studentattendancecheck.fragment.FragmentAddModule;


public class AddModuleActivity extends AppCompatActivity {

//    ActivityAddModuleBinding b;
    ActivityAddModuleBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_add_module);

        initInstance();

        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.addModuleFragmentContentContainer,
                            FragmentAddModule.newInstance(),
                            "AddModuleFragment")
                    .commit();

        }
        
    }

    private void initInstance() {

        // **** toolbar
        setSupportActionBar(b.addModuleActivityToolbar);

        // **** done button
        b.btnDoneAddModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", "test");
                setResult(RESULT_OK, returnIntent);

                finish();

                // TODO: Save selected item into local database

            }
        });

    }

}
