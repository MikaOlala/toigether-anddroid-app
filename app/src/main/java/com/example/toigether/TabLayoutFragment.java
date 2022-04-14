package com.example.toigether;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.toigether.adapters.TLGenerationAdapter;
import com.google.android.material.tabs.TabLayout;


public class TabLayoutFragment extends Fragment {

    private ViewPager viewPager;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_layout, container, false);

        TextView categoryTextView = view.findViewById(R.id.categoryName);
        categoryTextView.setText("Категория: " + getArguments().getString("categoryName"));

        TabLayout tabLayout = view.findViewById(R.id.tabLayoutGeneration);
        viewPager = view.findViewById(R.id.pagerGeneration);
        tabLayout.setupWithViewPager(viewPager);

        TLGenerationAdapter tlGenerationAdapter = new TLGenerationAdapter(getParentFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        tlGenerationAdapter.addFragment(new DateGenerationFragment(), "1");
        tlGenerationAdapter.addFragment(new CityGenerationFragment(), "2");
        tlGenerationAdapter.addFragment(new LocationGenerationFragment(), "3");
        tlGenerationAdapter.addFragment(new ServiceGenerationFragment(), "4");
        tlGenerationAdapter.addFragment(new BudgetGenerationFragment(), "5");
        tlGenerationAdapter.addFragment(new ResultGenerationFragment(), "6");

        viewPager.setAdapter(tlGenerationAdapter);

        return view;
    }

    public void setPage(int page) {
        viewPager.setCurrentItem(page);
    }
}