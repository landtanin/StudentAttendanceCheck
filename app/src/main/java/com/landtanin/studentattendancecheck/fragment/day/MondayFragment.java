package com.landtanin.studentattendancecheck.fragment.day;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
    TimeTableListAdapter TimeTableAdapter;

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
        RealmResults<StudentModuleDao> studentModuleDao = Realm.getDefaultInstance().where(StudentModuleDao.class).equalTo("day","mon",Case.SENSITIVE).findAll();
        StaggeredGridLayoutManager rvLayoutManager = new StaggeredGridLayoutManager(1, 1);
        b.rvMondayTimeTable.setLayoutManager(rvLayoutManager);
        mTimeTableListAdapter = new TimeTableListAdapter(getContext(),studentModuleDao ,true);

        b.rvMondayTimeTable.setAdapter(mTimeTableListAdapter);
        b.rvMondayTimeTable.setHasFixedSize(true);

        connectToDataBase();

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

        Realm realm = Realm.getDefaultInstance();
//        RealmResults<StudentModuleDao> student = realm.where(StudentModuleDao.class).findAll();

//        Log.w("REALM QUERY", student.get(0).getName());

        RealmResults<StudentModuleDao> student = realm.where(StudentModuleDao.class).contains("day","mon",Case.SENSITIVE).findAll();
        mLayoutManager=new LinearLayoutManager(getContext());
        b.rvMondayTimeTable.setLayoutManager(mLayoutManager);
        b.rvMondayTimeTable.setHasFixedSize(true);

        Log.e("onResume: ", String.valueOf(student.size()));
        if (TimeTableAdapter == null) {
            TimeTableAdapter = new TimeTableListAdapter(getContext(),student ,true);
            b.rvMondayTimeTable.setAdapter(TimeTableAdapter);
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

}
