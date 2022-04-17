package com.example.toigether.generation;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.toigether.R;
import com.example.toigether.adapters.TLGenerationAdapter;
import com.google.android.material.tabs.TabLayout;


public class TabLayoutFragment extends Fragment {

    private ViewPager viewPager;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_layout, container, false);

        Button next = view.findViewById(R.id.next);
        Button makeGen = view.findViewById(R.id.makeGen);
        Button change = view.findViewById(R.id.change);
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
        tlGenerationAdapter.addFragment(new com.example.toigether.generation.Fragment(), "✓");
        tlGenerationAdapter.addFragment(new ResultGenerationFragment(), "6");

        viewPager.setAdapter(tlGenerationAdapter);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewPager.getCurrentItem() == 4) {
                    viewPager.setCurrentItem(6);
                }
                else
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 6) {
                    next.setVisibility(ViewGroup.INVISIBLE);
                    makeGen.setVisibility(View.VISIBLE);
                    change.setVisibility(View.VISIBLE);
                }
                else {
                    next.setVisibility(ViewGroup.VISIBLE);
                    makeGen.setVisibility(View.INVISIBLE);
                    change.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
            }
        });

        makeGen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_tabLayoutFragment_to_generationResultsFragment);
            }
        });

        return view;
    }
}