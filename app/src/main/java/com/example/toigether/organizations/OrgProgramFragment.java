package com.example.toigether.organizations;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.toigether.R;

import java.util.ArrayList;
import java.util.Arrays;

public class OrgProgramFragment extends Fragment {

    private ArrayList<String> services;
    private ArrayList<String> subItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.org_program_fragment, container, false);

        ListView programList = view.findViewById(R.id.programList);
        services = new ArrayList<>(Arrays.asList("Оформление", "Кухня",
                "Ведущий", "Фотосессия", "Видеосъемка", "Шоу-программа",
                "Развлекательная программа для гостей", "Официальные церемония"));

        subItem = new ArrayList<>(Arrays.asList("Пример 1", "Пример 2", "Пример 3",
                "Пример 4", "Пример 5", "Пример 6", "Пример 7",
                "Пример 8", "Пример 9"));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_multiple_choice, services);
        programList.setAdapter(adapter);


        return view;
    }
}