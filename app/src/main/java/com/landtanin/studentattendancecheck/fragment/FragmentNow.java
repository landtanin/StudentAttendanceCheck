package com.landtanin.studentattendancecheck.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.landtanin.studentattendancecheck.R;
import com.landtanin.studentattendancecheck.activity.CheckInActivity;
import com.landtanin.studentattendancecheck.dao.StudentModuleDao;
import com.landtanin.studentattendancecheck.databinding.FragmentNowBinding;
import com.landtanin.studentattendancecheck.util.TodayModule;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

import static android.os.SystemClock.currentThreadTimeMillis;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class FragmentNow extends Fragment {

    private FragmentNowBinding b;
    private Calendar c;
    private Date now;
    private int firstModuleVSsecondModule;
    private SimpleDateFormat timeFormat, dateFormat;
    private int redColor, greenColor, greyColor;

    boolean run = true; //set it to false if you want to stop the timer
    Handler mHandler = new Handler();

    public FragmentNow() {
        super();
    }

    public static FragmentNow newInstance() {
        FragmentNow fragment = new FragmentNow();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_now, container, false);
        View rootView = b.getRoot();
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    @SuppressWarnings("UnusedParameters")
    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(final View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        // Note: State of variable initialized here could not be saved
        //       in onSavedInstanceState

//        String weekDay;
//        SimpleDateFormat dayFormat = new SimpleDateFormat("E", Locale.UK);
//
//        Calendar calendar = Calendar.getInstance();
//        weekDay = dayFormat.format(calendar.getTime());
//
//
//        Realm realm = Realm.getDefaultInstance();
//        RealmResults<StudentModuleDao> studentModuleDao = realm.getDefaultInstance()
//                .where(StudentModuleDao.class)
//                .equalTo("day",weekDay.trim(), Case.SENSITIVE).findAll();
//                .equalTo("day","Wed", Case.SENSITIVE).findAll();

        c = Calendar.getInstance();
        now = c.getTime();
        timeFormat = new SimpleDateFormat("HH:mm:ss");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        redColor = ContextCompat.getColor(getActivity(), R.color.colorRed500);
        greenColor = ContextCompat.getColor(getActivity(), R.color.colorGreen500);
        greyColor = ContextCompat.getColor(getActivity(), R.color.colorGrey500);


        int targetingModule = 0;

        // get today module
        TodayModule todayModule = new TodayModule();
        RealmResults<StudentModuleDao> studentModuleDao = todayModule.getTodayModule();

        Log.w("WEEKDAY", todayModule.dayOfWeek());
        Log.d("todayModule", String.valueOf(studentModuleDao));
        Log.d("todayModule SIZE", String.valueOf(studentModuleDao.size()));

        // if there is any module today
        if (!(studentModuleDao.size()==0)) {

            // if there is more than one module
            if (studentModuleDao.size()>1) {

                // find the first one
                for (int i = 1; i<studentModuleDao.size(); i++) {

                    Date firstModuleStart = studentModuleDao.get(targetingModule).getCheckInStart();
                    Date secondModuleStart = studentModuleDao.get(i).getCheckInStart();
                    // Return value is 0 if both dates are equal.
                    // Return value is greater than 0 , if Date is after the date argument.
                    // Return value is less than 0, if Date is before the date argument.

                    firstModuleVSsecondModule = timeFormat.format(firstModuleStart)
                            .compareTo(timeFormat.format(secondModuleStart));

                    // if second is before first
                    if (firstModuleVSsecondModule>0) {

                        targetingModule = i;

                    }

                } // end find the first one

            }

            // compare with now if it is the last module of the day whether it's end yet
            if (targetingModule == (studentModuleDao.size() - 1)) {

                Date nextModuleEnd = studentModuleDao.get(targetingModule).getEndDate();
                int nowVSnextModule = timeFormat.format(now)
                        .compareTo(timeFormat.format(nextModuleEnd));

                // if now is before nextModule end and the today is in module period, showCurrentStatus, else you're free
                if ( (nowVSnextModule <= 0) && ((dateFormat.format(studentModuleDao.get(targetingModule).getStartDate())
                        .compareTo(dateFormat.format(now))) <= 0 ) &&
                        ((dateFormat.format(studentModuleDao.get(targetingModule).getEndDate())
                                .compareTo(dateFormat.format(now))) > 0 ) ) {

                        showCurrentStatus(studentModuleDao, targetingModule);

                } else {

                    youAreFreeStatus();
                    realmUpdateModStatus(targetingModule, studentModuleDao);

                }
//                (nowVSnextModule <=0) ? showCurrentStatus(studentModuleDao, targetingModule) : youAreFreeStatus();

            } else  if ( ((dateFormat.format(studentModuleDao.get(targetingModule).getStartDate())
                    .compareTo(dateFormat.format(now))) <= 0 ) &&
                    ((dateFormat.format(studentModuleDao.get(targetingModule).getEndDate())
                            .compareTo(dateFormat.format(now))) > 0 )) {

                // if this module date is correct
                showCurrentStatus(studentModuleDao, targetingModule);


            } else {

                youAreFreeStatus();
                realmUpdateModStatus(targetingModule, studentModuleDao);

            }

        } else {

            youAreFreeStatus();

        }

        // TODO disable this if checkin already
        final int finalTargetingModule = targetingModule;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (run) {
                    try {

                        Thread.sleep(20000);
                        // every 20 secs

                        mHandler.post(new Runnable() {

                            @Override
                            public void run() {
//                                Calendar updateTime =Calendar.getInstance();
//                                Date nowInButtonManager = c.getTime();
//                                Log.e("FragmentNow - nowFromThread", String.valueOf(nowInButtonManager));
                                buttonStatusColorManager(finalTargetingModule);
//                                Log.e("FragmentNow - finalTargetingModule", String.valueOf(finalTargetingModule));

                            }
                        });
                    } catch (Exception e) {
                    }
                }
            }
        }).start();

//        timer();

        b.statusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long currentClock = currentThreadTimeMillis();
                Log.w("currentClock", String.valueOf(currentClock));

//                Log.w("todayModuleClick", String.valueOf(studentModuleDao));
                Intent intent = new Intent(getActivity(), CheckInActivity.class);
                startActivity(intent);

            }
        });

    }

    private void realmUpdateModStatus(int targetingModule, RealmResults<StudentModuleDao> studentModuleDao) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(studentModuleDao).get(targetingModule).setModStatus("no more class");
        realm.commitTransaction();
    }

    private void youAreFreeStatus() {
        Log.e("todayModule", "empty");

//            b.moduleNameTxt.setVisibility(View.GONE);
        b.moduleNameTxt.setText("It's free time :)");
        b.moduleIdTxt.setVisibility(View.GONE);
        b.startTimeTxt.setVisibility(View.GONE);
        b.toTimeTxt.setVisibility(View.GONE);
        b.endTimeTxt.setVisibility(View.GONE);
        b.lecturerTxt.setVisibility(View.GONE);
        b.locationTxt.setVisibility(View.GONE);
        b.statusBtn.setVisibility(View.GONE);
//            b.statusTxt.setVisibility(View.GONE);
    }

    private void showCurrentStatus(RealmResults<StudentModuleDao> studentModuleDao, int targetingModule) {

        b.moduleNameTxt.setText(studentModuleDao.get(targetingModule).getName());
        b.moduleIdTxt.setText(studentModuleDao.get(targetingModule).getModuleId());

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        b.startTimeTxt.setText(dateFormat.format(studentModuleDao.get(targetingModule).getCheckInStart()));
        b.endTimeTxt.setText(dateFormat.format(studentModuleDao.get(targetingModule).getCheckInEnd()));

        b.lecturerTxt.setText(studentModuleDao.get(targetingModule).getDescription());
        b.locationTxt.setText(studentModuleDao.get(targetingModule).getRoom());
        buttonStatusColorManager(targetingModule);

    }

    private void buttonStatusColorManager(int buttonTargetingModule) {

        int min = c.get(Calendar.MINUTE);
        int hour=c.get(Calendar.HOUR);
        Calendar calendar = Calendar.getInstance();
        Date nowInButtonManager = calendar.getTime();

//        Resources res = getResources();

//        int redColor = res.getColor(R.color.colorRed500);
//        int greenColor = getResources().getColor(R.color.colorGreen500);


        Log.w("timer now", String.valueOf(c.getTime()));
//                                b.statusTxt.setText("do");

        TodayModule todayModule = new TodayModule();
        RealmResults<StudentModuleDao> studentModuleDao = todayModule.getTodayModule();

        // get now and checkInStart ready
//        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        // TODO : is the check in is available?
        // case 1: next module is not yet available, now is before check in start - grey
        // case 2: first module is end, second module is not yet available - grey
        // case 3: they're already check in - blue
        // case 4: avalable, now is after checkin start but before checkin enc

        Date checkInStart = studentModuleDao.get(buttonTargetingModule).getCheckInStart();
        Date checkInEnd = studentModuleDao.get(buttonTargetingModule).getCheckInEnd();
        Date startDate = studentModuleDao.get(buttonTargetingModule).getStartDate();
        Date endDate = studentModuleDao.get(buttonTargetingModule).getEndDate();

        Log.d("FragmentNow now time", String.valueOf(nowInButtonManager));
        Log.d("FragmentNow checkin time", String.valueOf(checkInStart));

        // compare time by only consider the date
        // Return value is 0 if both dates are equal.
        // Return value is greater than 0 , if Date is after the date argument.
        // Return value is less than 0, if Date is before the date argument.
        // result format note (less than, greater than 0 or equal)
        int checkInCompareBeforeResult = timeFormat.format(nowInButtonManager).compareTo(timeFormat.format(checkInStart)); // ( it's not checkin time yet - GREY, it's checkin time - GREEN )
        int checkInCompareAfterResult = timeFormat.format(nowInButtonManager).compareTo(timeFormat.format(checkInEnd)); // ( it's still checkin time - GREEN, you're late but it's not end yet - RED)
        int dateCompareBeforeResult = timeFormat.format(nowInButtonManager).compareTo(timeFormat.format(startDate)); // (, )
        int dateCompareAfterResult = timeFormat.format(nowInButtonManager).compareTo(timeFormat.format(endDate)); // (you're late but it's not end yet - RED, the module is ended - show new module detail GREY)

        Log.w("Fragment now - checkInCompareBeforeResult", String.valueOf(checkInCompareBeforeResult));
        Log.w("Fragment now - checkInCompareAfterResult", String.valueOf(checkInCompareAfterResult));
        Log.w("Fragment now - dateCompareBeforeResult", String.valueOf(dateCompareBeforeResult));
        Log.w("Fragment now - dateCompareAfterResult", String.valueOf(dateCompareAfterResult));

        // before checkinstart
        if (checkInCompareBeforeResult < 0) {

//            b.statusBtn.setBackgroundColor(greyColor);
            b.statusBtn.setBackgroundColor(greyColor);
            b.statusTxt.setTextColor(greyColor);
            b.statusTxt.setText("check in is not yet available");

        }
        // after checkin start, before checkin end
        else if (checkInCompareBeforeResult >=0 && checkInCompareAfterResult < 0) {

            b.statusBtn.setBackgroundColor(greenColor);
            b.statusTxt.setTextColor(greenColor);
            b.statusTxt.setText("check in is available");

        }
        // late but the module is not end yet
        else if (checkInCompareAfterResult >=0 && dateCompareAfterResult < 0) {

            b.statusBtn.setBackgroundColor(redColor);
            b.statusTxt.setTextColor(redColor);
            b.statusTxt.setText("check in is available");

        }
//        b.statusTxt.setText(String.valueOf(hour)+":"+String.valueOf(min));
    }

//    public void timer() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (run) {
//                    try {
//                        Thread.sleep(20000);
//                        mHandler.post(new Runnable() {
//
//                            @Override
//                            public void run() {
//                                Calendar c = Calendar.getInstance();
//                                int min = c.get(Calendar.MINUTE);
//                                int hour=c.get(Calendar.HOUR);
//                                Log.w("timer now", String.valueOf(c));
//
////                                b.statusTxt.setText(String.valueOf(hour)+":"+String.valueOf(min));
//                            }
//                        });
//                    } catch (Exception e) {
//                    }
//                }
//            }
//        }).start();}

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance (Fragment level's variables) State here
    }

    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance (Fragment level's variables) State here
    }

}
