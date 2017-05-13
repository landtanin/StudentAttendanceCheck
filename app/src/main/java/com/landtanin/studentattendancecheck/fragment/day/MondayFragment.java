package com.landtanin.studentattendancecheck.fragment.day;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.landtanin.studentattendancecheck.R;
import com.landtanin.studentattendancecheck.adapter.TimeTableListAdapter;
import com.landtanin.studentattendancecheck.adapter.TimeTableListItem;
import com.landtanin.studentattendancecheck.dao.StudentModuleDao;
import com.landtanin.studentattendancecheck.databinding.FragmentMondayBinding;

import java.util.ArrayList;
import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class MondayFragment extends Fragment {

    private TimeTableListAdapter mTimeTableListAdapter;
    private List<TimeTableListItem> mTimeTableListItems = new ArrayList<>();
    private FragmentMondayBinding b;

    LinearLayoutManager mLayoutManager;
    TimeTableListAdapter TimeTableListAdapter;
    private Realm realm;

    public MondayFragment() {
        super();
    }

    public static MondayFragment newInstance() {
        MondayFragment fragment = new MondayFragment();
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
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_monday, container, false);
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
//        Log.w("MONDAY_module", String.valueOf(moduleToShow.size()));

        realm = Realm.getDefaultInstance();
        RealmResults<StudentModuleDao> moduleToShow =
                realm.where(StudentModuleDao.class).equalTo("day","Mon",Case.SENSITIVE).findAll();

        if (moduleToShow.size()!=0) { // If there is any module to show

            LinearLayoutManager rvLayoutManager =
                    new LinearLayoutManager(getContext());
            b.rvMondayTimeTable.setLayoutManager(rvLayoutManager);
            TimeTableListAdapter mTimeTableListAdapter =
                    new TimeTableListAdapter(getContext(), moduleToShow, true);
            b.rvMondayTimeTable.setAdapter(mTimeTableListAdapter);
            b.rvMondayTimeTable.setHasFixedSize(true);

        } else {

            b.rvMondayTimeTable.setVisibility(View.GONE);
            b.monNoModuleText.setText("You are free today");
            b.monNoModuleText.setVisibility(View.VISIBLE);

        }


//        connectToDataBase();

    }

    private void connectToDataBase() {

        // hardcoded item to RecyclerView
//        for (int i = 0; i < 100; i++) {
//
////            AddModuleItem addModuleItem = new AddModuleItem("item " + i, "item2 " + i, false);
//            TimeTableListItem timeTableListItem = new TimeTableListItem("Mon module " + i, "A000" + i, i % 2 == 0 ? "active" : "inactive", "9-12", "School of Engineering");
//            mTimeTableListItems.add(timeTableListItem);
//
//        }

        realm = Realm.getDefaultInstance();
//        RealmResults<StudentModuleDao> student = realm.where(StudentModuleDao.class).findAll();

//        Log.w("REALM QUERY", student.get(0).getName());

        RealmResults<StudentModuleDao> student = realm.where(StudentModuleDao.class).contains("day","mon",Case.SENSITIVE).findAll();
        mLayoutManager = new LinearLayoutManager(getContext());
        b.rvMondayTimeTable.setLayoutManager(mLayoutManager); // set layout manager of recycler view
        b.rvMondayTimeTable.setHasFixedSize(true);

        Log.e("onResume: ", String.valueOf(student.size()));

        if (TimeTableListAdapter == null) {
            TimeTableListAdapter = new TimeTableListAdapter(getContext(),student ,true); // throw data from Realm into recyclerView
            b.rvMondayTimeTable.setAdapter(TimeTableListAdapter);
        }

//        mTimeTableListAdapter.notifyDataSetChanged();

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
