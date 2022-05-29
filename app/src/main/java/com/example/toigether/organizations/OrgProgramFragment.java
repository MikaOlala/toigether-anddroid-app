package com.example.toigether.organizations;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.RadioButton;

import com.example.toigether.FirebaseData;
import com.example.toigether.R;
import com.example.toigether.adapters.ListAdapter;

import com.example.toigether.items.Organization;
import com.example.toigether.items.Service;
import com.google.gson.Gson;
import java.util.ArrayList;


public class OrgProgramFragment extends Fragment {

    private final FirebaseData db = new FirebaseData();
    private ArrayList<Service> serviceList;
    private ArrayList<String> choice = new ArrayList<>();
    private SharedPreferences prefs;
    private ListAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.org_program_fragment, container, false);

        prefs = this.getActivity().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);

        SearchView search = view.findViewById(R.id.search);
        recyclerView = view.findViewById(R.id.recyclerViewProgram);
        String value = getActivity().getIntent().getExtras().getString("id");

        setServices(value);

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

    private void setServices(String id) {
        db.getOrganization(id, new FirebaseData.OnGetOneListener() {
            @Override
            public void onStart() {
                db.openDialog(getContext());
            }

            @Override
            public void onSuccess(Organization data) {
                db.getOrgServicesById(data.getOrganizator_id(), new FirebaseData.OnGetServicesListener() {
                    @Override
                    public void onStart() {}

                    @Override
                    public void onSuccess(ArrayList<Service> services) {
                        if (services==null || services.size()==0)
                            setStandardServices();
                        else
                            serviceList = services;
                        setAdapter();
                        db.closeDialog();

                        adapter.setOnItemClickListener(new ListAdapter.onItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                chooseService(view, position);
                            }
                        });
                    }
                });
            }
        });
    }

    private void setAdapter() {
        RecyclerView.LayoutManager layoutManager;
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        layoutManager = new LinearLayoutManager(this.getContext());
        adapter = new ListAdapter(serviceList, "organization");

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void chooseService(View view, int position) {
        RadioButton radioButton = view.findViewById(R.id.radio);
        radioButton.setChecked(!radioButton.isChecked());

        if (radioButton.isChecked())
            choice.add(serviceList.get(position).getName());
        else
            choice.remove(serviceList.get(position).getName());

        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(choice);
        editor.putString("servicesToContact", json).apply();
    }

    private void setStandardServices() {
        serviceList = new ArrayList<>();
        serviceList.add(new Service("Оформление", "some text some text"));
        serviceList.add(new Service("Кухня", "some text some text"));
        serviceList.add(new Service("Ведущий", "some text some text"));
        serviceList.add(new Service("Фотосессия", "some text some text"));
        serviceList.add(new Service("Видеосъемка", "some text some text"));
        serviceList.add(new Service("Шоу-программа", "some text some text"));
        serviceList.add(new Service("Развлекательные программы", "some text some text"));
        serviceList.add(new Service("Официальные церемонии", "some text some text"));
    }

    public ArrayList<String> getChoice () {
        return choice;
    }
}