package com.landtanin.studentattendancecheck.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.landtanin.studentattendancecheck.R;
import com.landtanin.studentattendancecheck.databinding.FragmentMainBinding;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class MainFragment extends Fragment {

    ImageView plusSignImg;
    FragmentMainBinding b;

    public MainFragment() {
        super();
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
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
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        View rootView = b.getRoot();
        initInstances(rootView);
        return rootView;
    }

    @SuppressWarnings("UnusedParameters")
    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        b.plusSignImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MainFragment mainFragment = (MainFragment)
                        getFragmentManager().findFragmentByTag("MainFragment");
                AddModuleFragment addModuleFragment = (AddModuleFragment)
                        getFragmentManager().findFragmentByTag("AddModuleFragment");
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                R.anim.from_right, R.anim.to_left,
                                R.anim.from_left, R.anim.to_right)
                        .attach(addModuleFragment)
                        .detach(mainFragment)
                        .commit();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance (Fragment level's variables) State here
    }
}
