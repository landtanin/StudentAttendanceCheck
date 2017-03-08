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

        // Sliding TabLayout
//        Resources res = getResources();
//        final String[] tabString = res.getStringArray(R.array.date);
//
//        FragmentStatePagerAdapter fragmentTimeTablePagerAdapter = new FragmentStatePagerAdapter(getChildFragmentManager()) {
//            @Override
//            public Fragment getItem(int position) {
//                switch (position) {
//                    case 0:
//                        return MondayFragment.newInstance();
//                    case 1:
//                        return TuesdayFragment.newInstance();
//                    case 2:
//                        return WednesdayFragment.newInstance();
//                    case 3:
//                        return ThursdayFragment.newInstance();
//                    case 4:
//                        return FridayFragment.newInstance();
//                    case 5:
//                        return SaturdayFragment.newInstance();
//                    case 6:
//                        return SundayFragment.newInstance();
//                    default:
//                        return FragmentTimeTable.newInstance();
//                }
//            }
//
//            @Override
//            public int getCount() {
//                return tabString.length;
//            }
//
//            @Override
//            public CharSequence getPageTitle(int position) {
//                switch (position) {
//                    case 0:
//                        return tabString[position];
//                    case 1:
//                        return tabString[position];
//                    case 2:
//                        return tabString[position];
//                    case 3:
//                        return tabString[position];
//                    case 4:
//                        return tabString[position];
//                    case 5:
//                        return tabString[position];
//                    case 6:
//                        return tabString[position];
//                    default:
//                        return null;
//                }
//            }
//        };
//
//        b.fragmentTimeTableViewPager.setAdapter(fragmentTimeTablePagerAdapter);
//        b.timeTableFragmentSlidingTabLayout.setDistributeEvenly(true);
//        b.timeTableFragmentSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
//            @Override
//            public int getIndicatorColor(int position) {
//                return getResources().getColor(R.color.colorPrimary);
//            }
//        });
//        b.timeTableFragmentSlidingTabLayout.setViewPager(b.fragmentTimeTableViewPager);

        FragmentTimeTablePagerAdapter fragmentTimeTablePagerAdapter = new FragmentTimeTablePagerAdapter(getChildFragmentManager());

        b.fragmentTimeTableViewPager.setAdapter(fragmentTimeTablePagerAdapter);

        b.fragTimeTableTabLayout.setupWithViewPager(b.fragmentTimeTableViewPager);
//        b.fragTimeTableTabLayout.setClipToPadding(true);

        for(int i = 0; i < b.fragTimeTableTabLayout.getTabCount(); i++) {

            TabLayout.Tab tab = b.fragTimeTableTabLayout.getTabAt(i);
            tab.setText(fragmentTimeTablePagerAdapter.tabString[i]);

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
