package com.landtanin.studentattendancecheck.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.landtanin.studentattendancecheck.R;
import com.landtanin.studentattendancecheck.adapter.AddModuleAdapter;
import com.landtanin.studentattendancecheck.adapter.AddModuleItem;
import com.landtanin.studentattendancecheck.databinding.FragmentAddModuleBinding;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class FragmentAddModule extends Fragment {

    AddModuleAdapter mAddModuleAdapter;
    List<AddModuleItem> mModuleItemList = new ArrayList<>();
    FragmentAddModuleBinding b;

    public FragmentAddModule() {
        super();
    }

    public static FragmentAddModule newInstance() {
        FragmentAddModule fragment = new FragmentAddModule();
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
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_add_module, container, false);
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
        StaggeredGridLayoutManager rvLayoutManager = new StaggeredGridLayoutManager(1, 1);
        b.rvAddModule.setLayoutManager(rvLayoutManager);
        mAddModuleAdapter = new AddModuleAdapter(mModuleItemList, getContext());

        b.rvAddModule.setAdapter(mAddModuleAdapter);
        b.rvAddModule.setHasFixedSize(true);

        connectToDataBase();

    }

    private void connectToDataBase() {

        // hardcoded item to RecyclerView
        for (int i = 0; i < 100; i++) {

            AddModuleItem addModuleItem = new AddModuleItem("item " + i, "item2 " + i, false);
            mModuleItemList.add(addModuleItem);

        }

        mAddModuleAdapter.notifyDataSetChanged();

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
