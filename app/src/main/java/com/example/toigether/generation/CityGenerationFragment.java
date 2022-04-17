package com.example.toigether.generation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.toigether.R;

import java.util.ArrayList;

public class CityGenerationFragment extends Fragment {

    private View pastView = null;
    private ArrayAdapter<String> arrayAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.generation_city, container, false);

        SharedPreferences prefs = this.getActivity().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);

        SearchView search = view.findViewById(R.id.search);
        ListView list = view.findViewById(R.id.cityList);
        ArrayList<String> cities = new ArrayList<>();
        cities.add("Нур-султан");
        cities.add("Алматы");
        cities.add("Шымкент");
        cities.add("Костанай");
        cities.add("Кокшетау");
        cities.add("Кызылорда");
        cities.add("Усть-Каменогорск");
        cities.add("Петропавловск");
        cities.add("Павлодар");
        cities.add("Тараз");
        cities.add("Караганда");
        arrayAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1, cities);
        list.setAdapter(arrayAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String city = "";
                city = list.getItemAtPosition(i).toString();
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("city", city).apply();

//                TextView textChosen = (TextView)view.getTag();
//                textChosen.setTextColor(getResources().getColor(R.color.white));
                view.setBackgroundColor(getResources().getColor(R.color.orange));
                if(pastView != null && pastView != view) {
//                    TextView textPastChosen = (TextView)pastView.getTag();
//                    textPastChosen.setTextColor(getResources().getColor(R.color.black));
                    pastView.setBackgroundColor(getResources().getColor(R.color.transparent));
                }
                pastView = view;
            }
        });

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                CityGenerationFragment.this.arrayAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                CityGenerationFragment.this.arrayAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return view;
    }
}