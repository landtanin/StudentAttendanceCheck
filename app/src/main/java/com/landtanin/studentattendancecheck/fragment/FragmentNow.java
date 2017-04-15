package com.landtanin.studentattendancecheck.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.landtanin.studentattendancecheck.R;
import com.landtanin.studentattendancecheck.dao.StudentModuleDao;
import com.landtanin.studentattendancecheck.databinding.FragmentNowBinding;
import com.landtanin.studentattendancecheck.util.TodayModule;

import io.realm.RealmResults;

import static android.os.SystemClock.currentThreadTimeMillis;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class FragmentNow extends Fragment {

    FragmentNowBinding b;

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
        TodayModule todayModule = new TodayModule();
        RealmResults<StudentModuleDao> studentModuleDao = todayModule.getTodayModule();

        Log.w("WEEKDAY", todayModule.dayOfWeek());

        Log.e("todayModule", String.valueOf(studentModuleDao));

        Log.e("todayModule", String.valueOf(studentModuleDao.size()));
//        Log.e("todayModule", String.valueOf(studentModuleDao.get(0).getName()));

        if (!(studentModuleDao.size()==0)) {
            b.moduleNameTxt.setText(studentModuleDao.get(0).getName());
            b.moduleIdTxt.setText(studentModuleDao.get(0).getModuleId());

            String startTime = studentModuleDao.get(0).getCheckInStart().substring(0, 5);
            String endTime = studentModuleDao.get(0).getCheckInEnd().substring(0, 5);
            b.startTimeTxt.setText(startTime);
            b.endTimeTxt.setText(endTime);
            b.lecturerTxt.setText(studentModuleDao.get(0).getDescription());
            b.locationTxt.setText(studentModuleDao.get(0).getRoom());
        }else {

            Log.e("todayModule", "empty");

//            b.moduleNameTxt.setVisibility(View.GONE);
            b.moduleNameTxt.setText("There is no class today :)");
            b.moduleIdTxt.setVisibility(View.GONE);
            b.startTimeTxt.setVisibility(View.GONE);
            b.toTimeTxt.setVisibility(View.GONE);
            b.endTimeTxt.setVisibility(View.GONE);
            b.lecturerTxt.setVisibility(View.GONE);
            b.locationTxt.setVisibility(View.GONE);
            b.statusBtn.setVisibility(View.GONE);
            b.statusTxt.setVisibility(View.GONE);

        }

        b.statusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long currentClock = currentThreadTimeMillis();
                Log.w("currentClock", String.valueOf(currentClock));

//                Log.w("todayModuleClick", String.valueOf(studentModuleDao));
//                Intent intent = new Intent(getActivity(), CheckInActivity.class);
//                startActivity(intent);

            }
        });

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
