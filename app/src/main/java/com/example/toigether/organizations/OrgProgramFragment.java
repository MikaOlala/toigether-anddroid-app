package com.example.toigether.organizations;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.RadioButton;

import com.example.toigether.R;
import com.example.toigether.adapters.ListAdapter;

import com.example.toigether.items.Service;
import com.google.gson.Gson;
import java.util.ArrayList;


public class OrgProgramFragment extends Fragment {

    private ArrayList<Service> services;
    private ArrayList<String> choice = new ArrayList<>();
    private SharedPreferences prefs;
    private ListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.org_program_fragment, container, false);

        prefs = this.getActivity().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);

        SearchView search = view.findViewById(R.id.search);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewProgram);
        RecyclerView.LayoutManager layoutManager;

        services = new ArrayList<>();
        services.add(new Service(1L, "Оформление", "some text some text"));
        services.add(new Service(2L, "Кухня", "some text some text"));
        services.add(new Service(3L, "Ведущий", "some text some text"));
        services.add(new Service(4L, "Фотосессия", "some text some text"));
        services.add(new Service(5L, "Видеосъемка", "some text some text"));
        services.add(new Service(6L, "Шоу-программа", "some text some text"));
        services.add(new Service(7L, "Развлекательные программы", "some text some text"));
        services.add(new Service(8L, "Официальные церемонии", "some text some text"));

        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        layoutManager = new LinearLayoutManager(this.getContext());
        adapter = new ListAdapter(services, "organization");

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ListAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                chooseService(view, position);
            }
        });

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });

        return view;
    }

    private void chooseService(View view, int position) {
        RadioButton radioButton = view.findViewById(R.id.radio);
        radioButton.setChecked(!radioButton.isChecked());

        if (radioButton.isChecked())
            choice.add(services.get(position).getName());
        else
            choice.remove(services.get(position).getName());

        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(choice);
        editor.putString("servicesToContact", json).apply();
    }

    public ArrayList<String> getChoice () {
        return choice;
    }
}