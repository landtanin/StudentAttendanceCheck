package com.landtanin.studentattendancecheck.util;

import android.content.Context;
import android.widget.Toast;

import com.landtanin.studentattendancecheck.manager.Contextor;

import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by landtanin on 4/11/2017 AD.
 */

public class Utils {

    private static Utils ourInstance;
    private Context mContext;
    private Scheduler defaultSubscribeScheduler;

    private Utils() {
        mContext = Contextor.getInstance().getContext();
    }

    public static Utils getInstance() {
        if (ourInstance == null) {
            ourInstance = new Utils();
        }
        return ourInstance;
    }

    public Scheduler defaultSubscribeScheduler() {
        if (defaultSubscribeScheduler == null) {
            defaultSubscribeScheduler = Schedulers.io();
        }
        return defaultSubscribeScheduler;
    }

    public void onHoneyToast(String s) {
        Toast.makeText(mContext, s, Toast.LENGTH_LONG).show();
    }

}
