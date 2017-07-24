package com.landtanin.studentattendancecheck.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.landtanin.studentattendancecheck.R;
import com.landtanin.studentattendancecheck.activity.CheckInActivity;
import com.landtanin.studentattendancecheck.dao.StudentModuleDao;
import com.landtanin.studentattendancecheck.databinding.FragmentNowBinding;
import com.landtanin.studentattendancecheck.manager.TodayModule;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

import static android.app.Activity.RESULT_OK;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class FragmentNow extends Fragment {

    private FragmentNowBinding b;
    private Calendar c;
    private Date now;
    private int firstModuleVSsecondModule, targetingModule;
    private SimpleDateFormat timeFormat, dateFormat;
    private int redColor, greenColor, greyColor, indegoColor;
    private int buttonStatus = 0;
    private boolean fromCheckInAct = false;
    boolean run = true; //set it to false to stop the timer
    Handler mHandler = new Handler();
    private SharedPreferences prefs;
    private static final int TODAY_IS_END = 1000;
    private static final int STATUS_ACTIVE = 1;
    private static final int STATUS_INACTIVE = 2;
    private static final int STATUS_NO_MORE = 3;
    private static final int STATUS_CHECKED = 4;
    private static final String TAG = "FragmentNow";

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

//        fromCheckInAct = getActivity().getIntent().getBooleanExtra("checked", false);
        prefs = getActivity().getSharedPreferences("login_state", Context.MODE_PRIVATE);

//        Log.i("FragmentNow boolean", String.valueOf(prefs.getBoolean("checked_state", false)));
//        if (prefs.getBoolean("checked_state", false)) {
//
//            fromCheckInAct = true;
//            Log.e("FragmentNow init if", String.valueOf(fromCheckInAct));
//
//        } else {
//            fromCheckInAct = false;
//            Log.e("FragmentNow init else", String.valueOf(fromCheckInAct));
//        }

        redColor = ContextCompat.getColor(getActivity(), R.color.colorRed500);
        greenColor = ContextCompat.getColor(getActivity(), R.color.colorGreen500);
        greyColor = ContextCompat.getColor(getActivity(), R.color.colorGrey500);
        indegoColor = ContextCompat.getColor(getActivity(), R.color.colorPrimary);

        updateNow();
//        int initTargetingModule = updateNow();

//        if (initTargetingModule != TODAY_IS_END) {

            buttonStatusUpdate();
            // buttonStatusUpdate -> updateNow -> youAreFree/showCurrent -> buttonStatusColorManager

            if (targetingModule!=TODAY_IS_END) {

                Log.i("FragmentNow", "clock is running");
                b.statusBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        switch (buttonStatus) {
                            case STATUS_ACTIVE:
                                Intent intent = new Intent(getActivity(), CheckInActivity.class);
                                intent.putExtra("moduleItem", targetingModule);
                                startActivityForResult(intent, 11111);

                                //TODO intentOnActivityResult
                                break;
                            case STATUS_INACTIVE:
                                Toast.makeText(getContext(), "check in is not available", Toast.LENGTH_SHORT).show();
                                break;
                            case STATUS_CHECKED:
                                Toast.makeText(getContext(), "you are in", Toast.LENGTH_SHORT).show();
                                break;

                            default:
                                break;
                        }

                    }
                });

            }

//        } else {
//
//            Log.i("FragmentNow", "clock is stopped");
//
//        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 11111) {
            if (resultCode == RESULT_OK) {

                fromCheckInAct = data.getBooleanExtra("checked", false);
                Log.e("FragmentNow onActivityResult fromCheckInAct", String.valueOf(fromCheckInAct));
                updateNow();

            }
        }
    }

    private void buttonStatusUpdate() {

//        if (targetingModule != TODAY_IS_END) {

//            final int finalTargetingModule = targetingModule;
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

                                    updateNow();
                                    boolean show = prefs.getBoolean("checked_state", false);
                                    Log.e("FragmentNow prefs track", String.valueOf(show));
                                    Log.e("FragmentNow fromCheckInAct track", String.valueOf(fromCheckInAct));

                                    buttonStatusColorManager(targetingModule);
                                    // this could be delete as it's already declared in updateNow()

                                    Log.e("FragmentNow - finalTargetingModule", String.valueOf(targetingModule));

                                }
                            });
                        } catch (Exception e) {
                        }
                    }
                }
            }).start();

//        } else {
//            Log.i("FragmentNow else buttonStatusUpdate", " " + TODAY_IS_END);
//        }
    }

    private int updateNow() {
        c = Calendar.getInstance();
        now = c.getTime();
        timeFormat = new SimpleDateFormat("HH:mm:ss");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        targetingModule = 0;

        // get today module
        TodayModule todayModule = TodayModule.getInstance();
        RealmResults<StudentModuleDao> studentModuleDao = todayModule.getTodayModule();

        Log.d("FragmentNow WEEKDAY", todayModule.dayOfWeek());
        Log.d("FragmentNow todayModule", String.valueOf(studentModuleDao));
        Log.d("FragmentNow todayModule SIZE", String.valueOf(studentModuleDao.size()));

        // if there is any module today
        if (!(studentModuleDao.size()==0)) {

            if ((targetingModule+1) > studentModuleDao.size()) {

                targetingModule = 0;

            }

            Date ModuleStartCheckIn = studentModuleDao.get(targetingModule).getCheckInStart();
            Date ModuleEndCheckIn = studentModuleDao.get(targetingModule).getCheckInEnd();
            Date ModuleStartDate = studentModuleDao.get(targetingModule).getStartDate();
            Date ModuleEndDate = studentModuleDao.get(targetingModule).getEndDate();

            Log.i("FragmentNow ModuleEndDate", String.valueOf(studentModuleDao.get(targetingModule).getEndDate()));

            int nowVSmoduleEndDate = timeFormat.format(now).compareTo(timeFormat.format(ModuleEndDate));

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

                    int nowVSfirst = timeFormat.format(now).compareTo(timeFormat.format(firstModuleEndDate));
                    int nowVSsecond = timeFormat.format(now).compareTo(timeFormat.format(secondModuleEndDate));

                    int nowVSfirstStartDay = dateFormat.format(now).compareTo(dateFormat.format(firstModuleStartDate));
                    int nowVSfirstEndDay = dateFormat.format(now).compareTo(dateFormat.format(firstModuleEndDate));
                    int nowVSsecondStartDay = dateFormat.format(now).compareTo(dateFormat.format(secondModuleStartDate));
                    int nowVSsecondEndDay = dateFormat.format(now).compareTo(dateFormat.format(secondModuleEndDate));

                    // 1 first not, second not end
                    if (((nowVSfirst <= 0) && ((nowVSfirstStartDay > 0) && (nowVSfirstEndDay <= 0)))
                            && ((nowVSsecond <= 0) && (nowVSsecondStartDay > 0) && (nowVSsecondEndDay <= 0))) {

                        // can use both time of startCheckin or startDate
                        firstModuleVSsecondModule = timeFormat.format(firstModuleStartDate)
                                .compareTo(timeFormat.format(secondModuleStartDate));

                        // if second is less or before first
                        if (firstModuleVSsecondModule > 0) {

                            realmUpdateModStatus(targetingModule, studentModuleDao, STATUS_INACTIVE);
                            targetingModule = i;

                        } // else targetingModule remain the same

                    }
                    // 2 first not, second end
                    else if (((nowVSfirst <= 0) && ((nowVSfirstStartDay > 0) && (nowVSfirstEndDay <= 0)))
                            && ((nowVSsecond > 0) || (nowVSsecondStartDay <= 0) || (nowVSsecondEndDay > 0))) {

                        realmUpdateModStatus(i, studentModuleDao, STATUS_INACTIVE);

                        Log.i("FragmentNow", "targetingModule remain the same = " + targetingModule);
                        if ((nowVSsecondStartDay <= 0) || (nowVSsecondEndDay > 0)) {
                            realmUpdateModStatus(targetingModule, studentModuleDao, STATUS_NO_MORE);
                        }

                    }
                    // 3 first end, second not
                    else if (((nowVSfirst > 0) || ((nowVSfirstStartDay <= 0) || (nowVSfirstEndDay > 0)))
                            && ((nowVSsecond <= 0) && (nowVSsecondStartDay > 0) && (nowVSsecondEndDay <= 0))) {

                        realmUpdateModStatus(targetingModule, studentModuleDao, STATUS_INACTIVE);
                        targetingModule = i;
                        Log.i("FragmentNow", "targetingModule is i = " + targetingModule);
                        if ((nowVSfirstStartDay <= 0) || (nowVSfirstEndDay > 0)) {
                            realmUpdateModStatus(i, studentModuleDao, STATUS_NO_MORE);
                        }

                    }// 4 first end, second end
                    else if ((nowVSfirst > 0) && ((nowVSsecond > 0))) {

                        realmUpdateModStatus(targetingModule, studentModuleDao, STATUS_INACTIVE);
                        realmUpdateModStatus(i, studentModuleDao, STATUS_INACTIVE);
                        targetingModule = TODAY_IS_END;
                        Log.i("FragmentNow", "both end, targetingModule = " + targetingModule);

                    }// 5 unknown case
                    else {

                        // TODO : then do something not just toast!!
                        Toast.makeText(getContext(), "FragmentNow, Unknown Case", Toast.LENGTH_SHORT).show();

                    }

                } // end find the first one

                if (targetingModule == TODAY_IS_END) {

                    Log.i("FragmentNow", "youAreFree if TODAY_IS_END");

                    youAreFreeStatus();

                } else {

                    Log.i("FragmentNow", "The first module is " + targetingModule);
                    showCurrentStatus(studentModuleDao, targetingModule);

                }

                Log.i("FragmentNow", "leave updateNow() with many module, targetingModule = " + targetingModule);
                // TODO put youAreFreeStatus() here?
                return targetingModule;

            }

            // there's only one module and it's not end
            else if ((nowVSmoduleEndDate <= 0) && ((nowVSstartDay > 0) && (nowVSendDay <= 0))) {

                Log.i("FragmentNow", String.valueOf(nowVSmoduleEndDate) + nowVSstartDay + nowVSendDay);
                Log.i("FragmentNow", "there's only one module and it's not end");
                showCurrentStatus(studentModuleDao, targetingModule);
                Log.i("FragmentNow", "leave updateNow() with one module not end, targetingModule = " + targetingModule);
                // TODO put youAreFreeStatus() here?
                return targetingModule;

            } else {

                Log.i("FragmentNow", "youAreFree the only one module is ended");
                realmUpdateModStatus(targetingModule, studentModuleDao, STATUS_INACTIVE);
                targetingModule = TODAY_IS_END;
                youAreFreeStatus();
                Log.i("FragmentNow", "leave updateNow() with one module end, targetingModule = " + targetingModule);
                // TODO put youAreFreeStatus() here?
                return targetingModule;

            }


        }
        // if there's no module
        else {

            Log.i("FragmentNow", "youAreFree today has no module");
            targetingModule = TODAY_IS_END;
            youAreFreeStatus();
            Log.i("FragmentNow", "leave updateNow() with no module, targetingModule = " + targetingModule);
            // TODO put youAreFreeStatus() here?
            return targetingModule;

        }

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

        Log.i("FragmentNow ", "youAreFreeStatus method");

        b.moduleNameTxt.setText("It's free time :)");
        b.moduleIdTxt.setVisibility(View.GONE);
        b.moduleTimeTxt.setVisibility(View.GONE);
        b.lecturerTxt.setVisibility(View.GONE);
        b.locationTxt.setVisibility(View.GONE);
        b.statusBtn.setVisibility(View.GONE);
        b.statusTxt.setVisibility(View.GONE);

    }

    private void showCurrentStatus(RealmResults<StudentModuleDao> studentModuleDao, int targetingModule) {

        b.moduleIdTxt.setVisibility(View.VISIBLE);
        b.moduleTimeTxt.setVisibility(View.VISIBLE);
        b.lecturerTxt.setVisibility(View.VISIBLE);
        b.locationTxt.setVisibility(View.VISIBLE);
        b.statusBtn.setVisibility(View.VISIBLE);
        b.statusTxt.setVisibility(View.VISIBLE);

        b.moduleNameTxt.setText(studentModuleDao.get(targetingModule).getName());
        b.moduleIdTxt.setText(studentModuleDao.get(targetingModule).getModuleId());

        SimpleDateFormat currentTimeFormat = new SimpleDateFormat("HH:mm");
        String timeStr = "From " + currentTimeFormat.format(studentModuleDao.get(targetingModule).getStartDate())
                + " to " + currentTimeFormat.format(studentModuleDao.get(targetingModule).getEndDate());

        b.moduleTimeTxt.setText(timeStr);
        b.lecturerTxt.setText("with " + studentModuleDao.get(targetingModule).getLecturer());
        b.locationTxt.setText("at " + studentModuleDao.get(targetingModule).getRoom());
        buttonStatusColorManager(targetingModule);

    }

    private void buttonStatusColorManager(int buttonTargetingModule) {

        TodayModule todayModule = TodayModule.getInstance();
        RealmResults<StudentModuleDao> studentModuleDao = todayModule.getTodayModule();

        if (buttonTargetingModule == TODAY_IS_END) {

//            if (fromCheckInAct) {
            if(prefs.getBoolean("checked_state",false)){
                Log.i("FragmentNow", "prefs set to false");
//                SharedPreferences prefs = getActivity().getSharedPreferences("login_state", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.remove("checked_state");
                editor.remove("checked_or_late");
                editor.apply();

            }
//            fromCheckInAct = false;

            buttonStatus = STATUS_INACTIVE;
            return;

        }

        int min = c.get(Calendar.MINUTE);
        int hour=c.get(Calendar.HOUR);
        Calendar calendar = Calendar.getInstance();
        Date nowInButtonManager = calendar.getTime();

        // case 1: next module is not yet available, now is before check in start - grey
        // case 2: first module is end, second module is not yet available - grey
        // case 3: they're already check in - blue
        // case 4: avalable, now is after checkin start but before checkin enc

        Log.i("FragmentNow buttonStatusColorManager: targetingModule ", String.valueOf(buttonTargetingModule));
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
        int checkInCompareBeforeResult = timeFormat.format(nowInButtonManager)
                .compareTo(timeFormat.format(checkInStart)); // ( it's not checkin time yet - GREY, it's checkin time - GREEN )
        int checkInCompareAfterResult = timeFormat.format(nowInButtonManager)
                .compareTo(timeFormat.format(checkInEnd)); // ( it's still checkin time - GREEN, you're late but it's not end yet - RED)
        int dateCompareBeforeResult = timeFormat.format(nowInButtonManager)
                .compareTo(timeFormat.format(startDate)); // (, )
        int dateCompareAfterResult = timeFormat.format(nowInButtonManager)
                .compareTo(timeFormat.format(endDate)); // (you're late but it's not end yet - RED, the module is ended - show new module detail GREY)

        Log.w("Fragment now - checkInCompareBeforeResult", String.valueOf(checkInCompareBeforeResult));
        Log.w("Fragment now - checkInCompareAfterResult", String.valueOf(checkInCompareAfterResult));
        Log.w("Fragment now - dateCompareBeforeResult", String.valueOf(dateCompareBeforeResult));
        Log.w("Fragment now - dateCompareAfterResult", String.valueOf(dateCompareAfterResult));

        // before checkinstart
        if (checkInCompareBeforeResult < 0) {

            Log.i("FragmentNow", "prefs set to false");
//                SharedPreferences prefs = getActivity().getSharedPreferences("login_state", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.remove("checked_state");
            editor.remove("checked_or_late");
            editor.apply();
            Log.v("FragmentNow buttonStatusColorManager", "set grey, pref track" + prefs.getBoolean("checked_state", false));

            b.statusBtn.setBackgroundColor(greyColor);
            b.statusTxt.setTextColor(greyColor);
            b.statusBtn.setText("check-in");
            b.statusTxt.setText("check in will start at " +
                    timeFormat.format(studentModuleDao.get(buttonTargetingModule).getCheckInStart()));
            realmUpdateModStatus(buttonTargetingModule, studentModuleDao, STATUS_INACTIVE);
            buttonStatus = STATUS_INACTIVE;

        }
        // after checkin start, before checkin end
        else if (checkInCompareBeforeResult >=0 && checkInCompareAfterResult < 0) {

//            if (fromCheckInAct) {

            if(prefs.getBoolean("checked_state",false)){

                b.statusBtn.setBackgroundColor(indegoColor);
                b.statusTxt.setTextColor(indegoColor);
                b.statusBtn.setText("checked");
                b.statusTxt.setText("you are in");
                buttonStatus = STATUS_CHECKED;

            } else {

                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("checked_or_late", "checked");
                editor.apply();
                b.statusBtn.setBackgroundColor(greenColor);
                b.statusTxt.setTextColor(greenColor);
                b.statusBtn.setText("check-in");
                b.statusTxt.setText("check in will end at " +
                        timeFormat.format(studentModuleDao.get(buttonTargetingModule).getCheckInEnd()));
                buttonStatus = STATUS_ACTIVE;

            }

            realmUpdateModStatus(buttonTargetingModule, studentModuleDao, STATUS_ACTIVE);

        }
        // late but the module is not end yet
        else if (checkInCompareAfterResult >=0 && dateCompareAfterResult < 0) {

            //            if (fromCheckInAct) {
            if (prefs.getBoolean("checked_state",false)) {

                b.statusBtn.setBackgroundColor(indegoColor);
                b.statusTxt.setTextColor(indegoColor);
                b.statusBtn.setText("checked");
                b.statusTxt.setText("you are in");
                buttonStatus = STATUS_CHECKED;

            } else {

                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("checked_or_late", "late");
                editor.apply();
                b.statusBtn.setBackgroundColor(redColor);
                b.statusTxt.setTextColor(redColor);
                b.statusBtn.setText("check-in");
                b.statusTxt.setText("better late than never");
                buttonStatus = STATUS_ACTIVE;

            }

            realmUpdateModStatus(buttonTargetingModule, studentModuleDao, STATUS_ACTIVE);


        }
        // module is end, refresh for next module, set to grey
        else if (dateCompareAfterResult >= 0) {

//            if (fromCheckInAct) {
            if(prefs.getBoolean("checked_state",false)){
                Log.i("FragmentNow", "prefs set to false");
//                SharedPreferences prefs = getActivity().getSharedPreferences("login_state", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.remove("checked_or_late");
                editor.remove("checked_state");
                editor.apply();

            }
//            fromCheckInAct = false;

            buttonStatus = STATUS_INACTIVE;
            realmUpdateModStatus(buttonTargetingModule, studentModuleDao, STATUS_INACTIVE);
            if (targetingModule!=TODAY_IS_END) {
                Log.i("FragmentNow", "updateNow in buttonStatusColorManager");
                updateNow();
            }

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
