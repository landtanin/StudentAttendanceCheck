package com.landtanin.studentattendancecheck.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.landtanin.studentattendancecheck.R;
import com.landtanin.studentattendancecheck.databinding.FragmentTimeTableBinding;
import com.landtanin.studentattendancecheck.fragment.day.FridayFragment;
import com.landtanin.studentattendancecheck.fragment.day.MondayFragment;
import com.landtanin.studentattendancecheck.fragment.day.SaturdayFragment;
import com.landtanin.studentattendancecheck.fragment.day.SundayFragment;
import com.landtanin.studentattendancecheck.fragment.day.ThursdayFragment;
import com.landtanin.studentattendancecheck.fragment.day.TuesdayFragment;
import com.landtanin.studentattendancecheck.fragment.day.WednesdayFragment;
import com.landtanin.studentattendancecheck.manager.SmartFragmentStatePagerAdapter;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class FragmentTimeTable extends Fragment {

    FragmentTimeTableBinding b;

    public FragmentTimeTable() {
        super();
    }

    public static FragmentTimeTable newInstance() {
        FragmentTimeTable fragment = new FragmentTimeTable();
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
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_time_table, container, false);
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


        FragmentTimeTablePagerAdapter fragmentTimeTablePagerAdapter =
                new FragmentTimeTablePagerAdapter(getChildFragmentManager());
        b.fragmentTimeTableViewPager.setAdapter(fragmentTimeTablePagerAdapter);
        b.fragTimeTableTabLayout.setupWithViewPager(b.fragmentTimeTableViewPager);

        for(int i = 0; i < b.fragTimeTableTabLayout.getTabCount(); i++) {

            TabLayout.Tab tab = b.fragTimeTableTabLayout.getTabAt(i);
            tab.setText(fragmentTimeTablePagerAdapter.tabString[i] + "   ");


        }

        b.fragTimeTableTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

//        Call<StudentModuleCollectionDao> call = HttpManager.getInstance().getService().loadStudentModule();
//        call.enqueue(new Callback<StudentModuleCollectionDao>() {
//            @Override
//            public void onResponse(Call<StudentModuleCollectionDao> call,
//                                   Response<StudentModuleCollectionDao> response) {
//
//                if (response.isSuccessful()) {
//                    StudentModuleCollectionDao dao = response.body();
//                    Toast.makeText(getActivity(), dao.getData().get(0).getDay(), Toast.LENGTH_SHORT).show();
//                } else {
//                    try {
//                        Toast.makeText(getActivity(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<StudentModuleCollectionDao> call, Throwable t) {
//                Toast.makeText(getActivity(), "Fail" + t.toString(), Toast.LENGTH_SHORT).show();
//            }
//        });



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

    public class FragmentTimeTablePagerAdapter extends SmartFragmentStatePagerAdapter {

        private SmartFragmentStatePagerAdapter adapterViewPager;

        public String[] tabString = getResources().getStringArray(R.array.date);
        public FragmentTimeTablePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                    case 0:
                        return MondayFragment.newInstance();
                    case 1:
                        return TuesdayFragment.newInstance();
                    case 2:
                        return WednesdayFragment.newInstance();
                    case 3:
                        return ThursdayFragment.newInstance();
                    case 4:
                        return FridayFragment.newInstance();
                    case 5:
                        return SaturdayFragment.newInstance();
                    case 6:
                        return SundayFragment.newInstance();
                    default:
                        return FragmentTimeTable.newInstance();
            }
        }

        @Override
        public int getCount() {

            return tabString.length;

        }
    }



}
