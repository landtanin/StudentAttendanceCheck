package com.landtanin.studentattendancecheck.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.landtanin.studentattendancecheck.R;
import com.landtanin.studentattendancecheck.databinding.ActivityLoginBinding;
import com.landtanin.studentattendancecheck.fragment.FragmentLogin;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_login);

        initInstance();

        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.loginFragmentContentContainer,
                            FragmentLogin.newInstance(),
                            "LoginFragment")
                    .commit();

        }

    }

    private void initInstance() {



    }

    @Override
    protected void onStop() {

        finish();
        super.onStop();

    }
}
