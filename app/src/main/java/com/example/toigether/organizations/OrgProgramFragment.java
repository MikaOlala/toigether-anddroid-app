package com.example.toigether.organizations;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.toigether.R;
import com.example.toigether.adapters.ListAdapter;
import com.example.toigether.items.Service;

import java.util.ArrayList;
import java.util.Arrays;

public class OrgProgramFragment extends Fragment {

    private ArrayList<String> services;
    private ArrayList<String> subItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.org_program_fragment, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewProgram);
        ListAdapter adapter;
        RecyclerView.LayoutManager layoutManager;

        ArrayList<Service> services = new ArrayList<>();
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
            public void onItemClick(int position) {

            }
        });

        return view;
    }
}