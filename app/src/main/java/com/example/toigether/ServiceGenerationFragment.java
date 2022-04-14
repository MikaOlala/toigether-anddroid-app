package com.example.toigether;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class ServiceGenerationFragment extends Fragment {

    private ArrayList<String> choice = new ArrayList<>();
    private ArrayList<String> services;
    private ListView servicesList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.generation_service, container, false);

        SharedPreferences prefs = this.getActivity().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);

        services = new ArrayList<>(Arrays.asList("Выбрать всё", "Оформление", "Кухня",
                "Ведущий", "Фотосессия", "Видеосъемка", "Шоу-программа",
                "Развлекательная программа для гостей", "Официальные церемонии"));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_multiple_choice, services);
        servicesList = view.findViewById(R.id.servicesList);
        servicesList.setAdapter(adapter);
        servicesList.getLayoutParams().height = (int) ((int)getContext().getResources().getDisplayMetrics().heightPixels * 0.45);

        Button next = view.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Checking array ", String.valueOf(servicesList.isItemChecked(2)));
                checking();



//                SharedPreferences.Editor editor = prefs.edit();
//                Gson gson = new Gson();
//                String json = gson.toJson(list);
//                editor.putString("services", String.valueOf(text)).apply();
            }
        });

        servicesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    boolean check = servicesList.isItemChecked(i);
                    for (int j = 1; j < servicesList.getCount(); j++) {
                        servicesList.setItemChecked(j, check);
                    }
                }
            }
        });

        return view;
    }

    private void checking() {
        for (int i = 1; i < servicesList.getCount(); i++) {
            if(servicesList.isItemChecked(i))
                choice.add((String) servicesList.getItemAtPosition(i));
        }
    }
}