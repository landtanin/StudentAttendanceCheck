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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;
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

        String weekDay;
        SimpleDateFormat dayFormat = new SimpleDateFormat("E", Locale.UK);

        Calendar calendar = Calendar.getInstance();
        weekDay = dayFormat.format(calendar.getTime());


        Realm realm = Realm.getDefaultInstance();
        RealmResults<StudentModuleDao> studentModuleDao = realm.getDefaultInstance()
                .where(StudentModuleDao.class)
                .equalTo("day",weekDay.trim(), Case.SENSITIVE).findAll();
//                .equalTo("day","Wed", Case.SENSITIVE).findAll();

        Log.w("WEEKDAY", weekDay.toString());
        Log.w("todayModule", String.valueOf(studentModuleDao));

        b.moduleNameTxt.setText(studentModuleDao.get(0).getName());
        b.moduleIdTxt.setText(studentModuleDao.get(0).getId());

        String startTime = studentModuleDao.get(0).getStartDate().substring(11,16);
        String endTime = studentModuleDao.get(0).getEndDate().substring(11,16);
        b.startTimeTxt.setText(startTime);
        b.endTimeTxt.setText(endTime);
        b.lecturerTxt.setText(studentModuleDao.get(0).getDescription());

//        b.lecturerTxt.setText(studentModuleDao.get(0).get);


        b.statusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
