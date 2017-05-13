package com.landtanin.studentattendancecheck.fragment.day;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.landtanin.studentattendancecheck.R;
import com.landtanin.studentattendancecheck.adapter.TimeTableListAdapter;
import com.landtanin.studentattendancecheck.adapter.TimeTableListItem;
import com.landtanin.studentattendancecheck.dao.StudentModuleDao;
import com.landtanin.studentattendancecheck.databinding.FragmentThursdayBinding;

import java.util.ArrayList;
import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class ThursdayFragment extends Fragment {

    private TimeTableListAdapter mTimeTableListAdapter;
    private List<TimeTableListItem> mTimeTableListItems = new ArrayList<>();
    private FragmentThursdayBinding b;
    private Realm realm;


    public ThursdayFragment() {
        super();
    }

    public static ThursdayFragment newInstance() {
        ThursdayFragment fragment = new ThursdayFragment();
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
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_thursday, container, false);
        View rootView = b.getRoot();
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    @SuppressWarnings("UnusedParameters")
    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        // Note: State of variable initialized here could not be saved
        //       in onSavedInstanceState
        realm = Realm.getDefaultInstance();
        RealmResults<StudentModuleDao> studentModuleDao = realm.getDefaultInstance().where(StudentModuleDao.class).equalTo("day","Thu", Case.SENSITIVE).findAll();

        if (studentModuleDao.size()!=0) {

            LinearLayoutManager rvLayoutManager = new LinearLayoutManager(getContext());
            b.rvThursdayTimeTable.setLayoutManager(rvLayoutManager);
            mTimeTableListAdapter = new TimeTableListAdapter(getContext(), studentModuleDao, true);
            b.rvThursdayTimeTable.setAdapter(mTimeTableListAdapter);
            b.rvThursdayTimeTable.setHasFixedSize(true);

        } else {

            b.rvThursdayTimeTable.setVisibility(View.GONE);
            b.thuNoModuleText.setText("You are free today");
            b.thuNoModuleText.setVisibility(View.VISIBLE);

        }
//        connectToDataBase();

    }

    private void connectToDataBase() {

        // hardcoded item to RecyclerView
        for (int i = 0; i < 100; i++) {

//            AddModuleItem addModuleItem = new AddModuleItem("item " + i, "item2 " + i, false);
            TimeTableListItem timeTableListItem = new TimeTableListItem("Tue module " + i, "A000" + i, i % 2 == 0 ? "active" : "inactive", "9-12", "School of Engineering");
            mTimeTableListItems.add(timeTableListItem);

        }

        mTimeTableListAdapter.notifyDataSetChanged();

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

    @Override
    public void onStop() {
        super.onStop();
        realm.close();
    }
}
