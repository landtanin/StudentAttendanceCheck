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
import android.widget.Toast;

import com.landtanin.studentattendancecheck.R;
import com.landtanin.studentattendancecheck.databinding.FragmentHomeBinding;
import com.landtanin.studentattendancecheck.manager.SmartFragmentStatePagerAdapter;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class FragmentHome extends Fragment {

    FragmentHomeBinding b;


    public FragmentHome() {
        super();
    }

    public static FragmentHome newInstance() {
        FragmentHome fragment = new FragmentHome();
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

        b = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
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

        // slidingTablayout
//        b.homeFragmentViewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
//            @Override
//            public Fragment getItem(int position) {
//
//                switch (position) {
//                    case 0:
//                        return FragmentNow.newInstance();
//                    case 1:
//                        return FragmentTimeTable.newInstance();
//                    default:
//                        return null;
//                }
//
//            }
//
//            @Override
//            public int getCount() {
//                return 2;
//            }
//
//            @Override
//            public CharSequence getPageTitle(int position) {
//                switch (position) {
//                    case 0:
//                        return "NOW";
//                    case 1:
//                        return "TIME TABLE";
//                    default:
//                        return null;
//                }
//            }
//        });
//
//        b.homeFragmentSlidingTabLayout.setDistributeEvenly(true);
//
//        // slideable bar color
//        b.homeFragmentSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
//            @Override
//            public int getIndicatorColor(int position) {
//                return getResources().getColor(R.color.colorPrimary);
//            }
//        });
//
//        b.homeFragmentSlidingTabLayout.setViewPager(b.homeFragmentViewPager);

        FragmentHomePagerAdapter fragmentHomePagerAdapter = new FragmentHomePagerAdapter(getChildFragmentManager());

        b.fragmentHomeNonSwipViewPager.setAdapter(fragmentHomePagerAdapter);
        b.fragmentHomeNonSwipViewPager.setOffscreenPageLimit(2);

        b.fragHomeTabLayout.setupWithViewPager(b.fragmentHomeNonSwipViewPager);
        b.fragHomeTabLayout.setClipToPadding(true);
//        b.fragHomeTabLayout.setTabTextColors(R.color.colorPrimary, R.color.colorWhite);

        for(int i = 0; i < b.fragHomeTabLayout.getTabCount(); i++) {

            TabLayout.Tab tab = b.fragHomeTabLayout.getTabAt(i);
            tab.setText(fragmentHomePagerAdapter.tabString[i]);

        }

        b.fragHomeTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Toast.makeText(getContext(), "Tab has been unselected", Toast.LENGTH_SHORT).show();
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

    public class FragmentHomePagerAdapter extends SmartFragmentStatePagerAdapter {

        private SmartFragmentStatePagerAdapter adapterViewPager;


        public String[] tabString = {"NOW", "TIME TABLE"};

        public FragmentHomePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return FragmentNow.newInstance();
                case 1:
                    return FragmentTimeTable.newInstance();

                default:
                    return MainFragment.newInstance();
            }
        }

        @Override
        public int getCount() {

            return tabString.length;

        }
    }

}
