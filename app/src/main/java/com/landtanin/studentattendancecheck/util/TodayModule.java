package com.landtanin.studentattendancecheck.util;

import com.landtanin.studentattendancecheck.dao.StudentModuleDao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by landtanin on 4/15/2017 AD.
 */

public class TodayModule {

    public TodayModule() {

    }

    public RealmResults<StudentModuleDao> getTodayModule() {

        String weekDay = dayOfWeek();

        Realm realm = Realm.getDefaultInstance();
        RealmResults<StudentModuleDao> studentModuleDao = realm.getDefaultInstance()
                .where(StudentModuleDao.class)
                .equalTo("day",weekDay.trim(), Case.SENSITIVE).findAll();

        return studentModuleDao;
    }

    public String dayOfWeek() {

        String weekDay;
        SimpleDateFormat dayFormat = new SimpleDateFormat("E", Locale.UK);

        Calendar calendar = Calendar.getInstance();
        weekDay = dayFormat.format(calendar.getTime());

        return weekDay;

    }
}
