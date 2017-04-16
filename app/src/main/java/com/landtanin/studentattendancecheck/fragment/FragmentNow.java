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
    private static final int TODAY_IS_END = 1000;
    private static final int STATUS_ACTIVE = 1;
    private static final int STATUS_INACTIVE = 2;
    private static final int STATUS_NO_MORE = 3;

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

        redColor = ContextCompat.getColor(getActivity(), R.color.colorRed500);
        greenColor = ContextCompat.getColor(getActivity(), R.color.colorGreen500);
        greyColor = ContextCompat.getColor(getActivity(), R.color.colorGrey500);

        int targetingModule = updateNow();

        // TODO disable this if checkin already
        buttonStatusUpdate(targetingModule);
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

    private void buttonStatusUpdate(int targetingModule) {
        if (targetingModule!=TODAY_IS_END) {

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

                                    buttonStatusColorManager(finalTargetingModule);
//                                Log.e("FragmentNow - finalTargetingModule", String.valueOf(finalTargetingModule));

                                }
                            });
                        } catch (Exception e) {
                        }
                    }
                }
            }).start();

        }
    }

    private int updateNow() {
        c = Calendar.getInstance();
        now = c.getTime();
        timeFormat = new SimpleDateFormat("HH:mm:ss");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");


        int targetingModule = 0;

        // get today module
        TodayModule todayModule = new TodayModule();
        RealmResults<StudentModuleDao> studentModuleDao = todayModule.getTodayModule();

        Log.w("WEEKDAY", todayModule.dayOfWeek());
        Log.d("todayModule", String.valueOf(studentModuleDao));
        Log.d("todayModule SIZE", String.valueOf(studentModuleDao.size()));

        // if there is any module today
        if (!(studentModuleDao.size()==0)) {

            Date ModuleStartCheckIn = studentModuleDao.get(targetingModule).getCheckInStart();
            Date ModuleEndCheckIn = studentModuleDao.get(targetingModule).getCheckInEnd();
            Date ModuleStartDate = studentModuleDao.get(targetingModule).getStartDate();
            Date ModuleEndDate = studentModuleDao.get(targetingModule).getEndDate();

            int nowVSmodule = timeFormat.format(now).compareTo(timeFormat.format(ModuleEndCheckIn));

            int nowVSstartDay = dateFormat.format(now).compareTo(dateFormat.format(ModuleStartDate));
            int nowVSendDay = dateFormat.format(now).compareTo(dateFormat.format(ModuleEndDate));

            // if there is more than one module
            if (studentModuleDao.size() > 1) {
                Log.i("FragmentNow", "more than one module");

                // find the first one
                for (int i = 1; i < studentModuleDao.size(); i++) {

                    Date firstModuleStartCheckIn = studentModuleDao.get(targetingModule).getCheckInStart();
                    Date firstModuleEndCheckIn = studentModuleDao.get(targetingModule).getCheckInEnd();
                    Date secondModuleStartCheckIn = studentModuleDao.get(i).getCheckInStart();
                    Date secondModuleEndCheckIn = studentModuleDao.get(i).getCheckInEnd();

                    Date firstModuleStartDate = studentModuleDao.get(targetingModule).getStartDate();
                    Date firstModuleEndDate = studentModuleDao.get(targetingModule).getEndDate();
                    Date secondModuleStartDate = studentModuleDao.get(i).getStartDate();
                    Date secondModuleEndDate = studentModuleDao.get(i).getEndDate();
                    // Return value is 0 if both dates are equal.
                    // Return value is greater than 0 , if Date is after the date argument.
                    // Return value is less than 0, if Date is before the date argument.

                    int nowVSfirst = timeFormat.format(now).compareTo(timeFormat.format(firstModuleEndCheckIn));
                    int nowVSsecond = timeFormat.format(now).compareTo(timeFormat.format(secondModuleEndCheckIn));

                    int nowVSfirstStartDay = dateFormat.format(now).compareTo(dateFormat.format(firstModuleStartDate));
                    int nowVSfirstEndDay = dateFormat.format(now).compareTo(dateFormat.format(firstModuleEndDate));
                    int nowVSsecondStartDay = dateFormat.format(now).compareTo(dateFormat.format(secondModuleStartDate));
                    int nowVSsecondEndDay = dateFormat.format(now).compareTo(dateFormat.format(secondModuleEndDate));

                    // 1
                    if (((nowVSfirst <= 0) && ((nowVSfirstStartDay > 0) && (nowVSfirstEndDay <= 0)))
                            && ((nowVSsecond <= 0) && (nowVSsecondStartDay > 0) && (nowVSsecondEndDay <= 0))) {

                        // can use both time of startCheckin or startDate
                        firstModuleVSsecondModule = timeFormat.format(firstModuleStartCheckIn)
                                .compareTo(timeFormat.format(secondModuleStartCheckIn));

                        // if second is less or before first
                        if (firstModuleVSsecondModule > 0) {

                            targetingModule = i;

                        } // else targetingModule remain the same


                    }
                    // 2
                    else if (((nowVSfirst <= 0) && ((nowVSfirstStartDay > 0) && (nowVSfirstEndDay <= 0)))
                            && ((nowVSsecond > 0) || (nowVSsecondStartDay <= 0) || (nowVSsecondEndDay > 0))) {

                        Log.i("FragmentNow", "targetingModule remain the same = " + targetingModule);
                        if ((nowVSsecondStartDay <= 0) || (nowVSsecondEndDay > 0)) {
                            realmUpdateModStatus(targetingModule, studentModuleDao, STATUS_NO_MORE);
                        }

                    }
                    // 3
                    else if (((nowVSfirst > 0) || ((nowVSfirstStartDay <= 0) || (nowVSfirstEndDay > 0)))
                            && ((nowVSsecond <= 0) && (nowVSsecondStartDay > 0) && (nowVSsecondEndDay <= 0))) {

                        targetingModule = i;
                        Log.i("FragmentNow", "targetingModule is i = " + targetingModule);
                        if ((nowVSfirstStartDay <= 0) || (nowVSfirstEndDay > 0)) {
                            realmUpdateModStatus(i, studentModuleDao, STATUS_NO_MORE);
                        }

                    } else {

                        targetingModule = TODAY_IS_END;

                    }

                } // end find the first one

                if (targetingModule == TODAY_IS_END) {

                    youAreFreeStatus();

                } else {

                    Log.i("FragmentNow", "The first module is " + targetingModule);
                    showCurrentStatus(studentModuleDao, targetingModule);

                }


            }
            // there's only one module and it's not end
            else if ((nowVSmodule <= 0) && ((nowVSstartDay > 0) && (nowVSendDay <= 0))) {

                showCurrentStatus(studentModuleDao, targetingModule);

            } else {

                youAreFreeStatus();
                targetingModule = TODAY_IS_END;
//                realmUpdateModStatus(targetingModule, studentModuleDao);

            }

        } else {

            youAreFreeStatus();
            targetingModule = TODAY_IS_END;

        }
        return targetingModule;
    }

    private void realmUpdateModStatus(int targetingModule, RealmResults<StudentModuleDao> studentModuleDao, int status) {

        String modStatus = null;
        switch (status) {
            case STATUS_ACTIVE:
                modStatus = "active";
                break;
            case STATUS_INACTIVE:
                modStatus = "inactive";
                break;
            case STATUS_NO_MORE:
                modStatus = "no more class";
                break;
            default:
                break;
        }

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(studentModuleDao).get(targetingModule).setModStatus(modStatus);
        realm.commitTransaction();

    }

    private void youAreFreeStatus() {

        Log.e("FragmentNow ", "youAreFreeStatus");

        b.moduleNameTxt.setText("It's free time :)");
        b.moduleIdTxt.setVisibility(View.GONE);
        b.startTimeTxt.setVisibility(View.GONE);
        b.toTimeTxt.setVisibility(View.GONE);
        b.endTimeTxt.setVisibility(View.GONE);
        b.lecturerTxt.setVisibility(View.GONE);
        b.locationTxt.setVisibility(View.GONE);
        b.statusBtn.setVisibility(View.GONE);
        b.statusTxt.setVisibility(View.GONE);

    }

    private void showCurrentStatus(RealmResults<StudentModuleDao> studentModuleDao, int targetingModule) {

        b.moduleNameTxt.setText(studentModuleDao.get(targetingModule).getName());
        b.moduleIdTxt.setText(studentModuleDao.get(targetingModule).getModuleId());

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        b.startTimeTxt.setText(dateFormat.format(studentModuleDao.get(targetingModule).getStartDate()));
        b.endTimeTxt.setText(dateFormat.format(studentModuleDao.get(targetingModule).getEndDate()));

        b.lecturerTxt.setText(studentModuleDao.get(targetingModule).getDescription());
        b.locationTxt.setText(studentModuleDao.get(targetingModule).getRoom());
        buttonStatusColorManager(targetingModule);

    }

    private void buttonStatusColorManager(int buttonTargetingModule) {

        int min = c.get(Calendar.MINUTE);
        int hour=c.get(Calendar.HOUR);
        Calendar calendar = Calendar.getInstance();
        Date nowInButtonManager = calendar.getTime();

        TodayModule todayModule = new TodayModule();
        RealmResults<StudentModuleDao> studentModuleDao = todayModule.getTodayModule();

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
            realmUpdateModStatus(buttonTargetingModule, studentModuleDao, STATUS_INACTIVE);

        }
        // after checkin start, before checkin end
        else if (checkInCompareBeforeResult >=0 && checkInCompareAfterResult < 0) {

            b.statusBtn.setBackgroundColor(greenColor);
            b.statusTxt.setTextColor(greenColor);
            b.statusTxt.setText("check in is available");
            realmUpdateModStatus(buttonTargetingModule, studentModuleDao, STATUS_ACTIVE);

        }
        // late but the module is not end yet
        else if (checkInCompareAfterResult >=0 && dateCompareAfterResult < 0) {

            b.statusBtn.setBackgroundColor(redColor);
            b.statusTxt.setTextColor(redColor);
            b.statusTxt.setText("better late than never");
            realmUpdateModStatus(buttonTargetingModule, studentModuleDao, STATUS_ACTIVE);

        }
        // module is end, refresh for next module, set to grey
        else if (dateCompareAfterResult >= 0) {

            updateNow();
            realmUpdateModStatus(buttonTargetingModule, studentModuleDao, STATUS_INACTIVE);

        }
//        b.statusTxt.setText(String.valueOf(hour)+":"+String.valueOf(min));
    }

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
