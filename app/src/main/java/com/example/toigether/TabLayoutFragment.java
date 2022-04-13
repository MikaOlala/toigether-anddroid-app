package com.example.toigether;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.toigether.adapters.TLGenerationAdapter;
import com.google.android.material.tabs.TabLayout;

import devmike.jade.com.PageStepIndicator;

public class TabLayoutFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_layout, container, false);

        TabLayout tabLayout = view.findViewById(R.id.tabLayoutGeneration);
//        PageStepIndicator indicator = view.findViewById(R.id.page_stepper);
        ViewPager viewPager = view.findViewById(R.id.pagerGeneration);
        tabLayout.setupWithViewPager(viewPager);

        TLGenerationAdapter tlGenerationAdapter = new TLGenerationAdapter(getParentFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        tlGenerationAdapter.addFragment(new DateGenerationFragment(), "1");
        tlGenerationAdapter.addFragment(new CityGenerationFragment(), "2");
        tlGenerationAdapter.addFragment(new LocationGenerationFragment(), "3");

        viewPager.setAdapter(tlGenerationAdapter);

        return view;
    }
}