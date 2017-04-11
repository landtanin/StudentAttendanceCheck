package com.landtanin.studentattendancecheck.manager;

import android.content.Context;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class SingletonTemplate {

    private static SingletonTemplate instance;

    public static SingletonTemplate getInstance() {
        if (instance == null)
            instance = new SingletonTemplate();
        return instance;
    }

    private Context mContext;

    private SingletonTemplate() {
        mContext = Contextor.getInstance().getContext();
    }

}
