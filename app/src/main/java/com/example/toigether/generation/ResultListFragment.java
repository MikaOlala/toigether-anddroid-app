package com.example.toigether.generation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.toigether.FirebaseData;
import com.example.toigether.R;
import com.example.toigether.adapters.CardAdapter;
import com.example.toigether.items.Organization;
import com.example.toigether.organizations.OrganizationActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;

public class ResultListFragment extends Fragment {

    private RecyclerView recyclerView;
    private CardAdapter adapter;
    private final FirebaseData db = new FirebaseData();
    private ArrayList<Organization> organizationsByCategory = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewGeneration);

        getOrganizations();

        return view;
    }

    public void openActivityOrganization(String id) {
        Intent intent = new Intent(getActivity(), OrganizationActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    private void getOrganizations() {
        SharedPreferences prefs = getContext().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);


        String category = getArguments().getString("categoryName");
        ArrayList<String> categories = new ArrayList<>();
        categories.add(category);

        String city = prefs.getString("city", null); // null if statement in query

        ArrayList<String> services = new ArrayList<>();
        Gson gson = new Gson();
        String json = prefs.getString("services", null); // null if statement in query
        if(json!=null) {
            Type type = new TypeToken<ArrayList<String>>() {}.getType();
            services = gson.fromJson(json, type);
        }
        ArrayList<String> finalServices = services;

        db.getOrganizationsByParameter("categories", city, categories, new FirebaseData.OnGetDataListener() {
            ArrayList<Organization> organizationsByServices = new ArrayList<>();

            @Override
            public void onStart() {}

            @Override
            public void onSuccess(ArrayList<Organization> data) {
                organizationsByCategory = data;
                Log.e("sizeArray1", organizationsByCategory.size() + " ");
                db.getOrganizationsByParameter("gen_services", city, finalServices, new FirebaseData.OnGetDataListener() {
                    @Override
                    public void onStart() {}

                    @Override
                    public void onSuccess(ArrayList<Organization> data) {
                        organizationsByServices = data;
                        Log.e("sizeArray2", organizationsByServices.size() + " ");

                        if(organizationsByCategory.size()!=0 || organizationsByServices.size()!=0) {
                            for(Organization o : organizationsByCategory)
                                organizationsByServices.removeIf(o::equals);
                        }

                        organizationsByCategory.addAll(organizationsByServices);
                        organizationsByCategory.sort(Comparator.comparing(Organization::getRating).reversed());

                        Log.e("sizeArray3", organizationsByCategory.size() + " ");
                        setAdapter(organizationsByCategory);
                    }
                });

            }
        });

        Log.e("sizeArray not inner", organizationsByCategory.size() + " ");
    }

    private void setAdapter(ArrayList<Organization> organizations) {
        RecyclerView.LayoutManager layoutManager;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getContext());
        adapter = new CardAdapter(organizations, "generation");

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        Log.e("CardAdapter", String.valueOf(organizations.size()));
    }
}