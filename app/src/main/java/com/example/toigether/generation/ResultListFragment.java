package com.example.toigether.generation;

import android.app.Dialog;
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
import android.view.Window;
import android.widget.TextView;

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
    private Dialog dialog;
    private TextView header;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewGeneration);
        TextView sortBy = view.findViewById(R.id.sortBy);
        header = view.findViewById(R.id.resultHeader);
        db.makeTextUnderlined(sortBy);

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
        String json = prefs.getString("services", null);
        if(json!=null) {
            Type type = new TypeToken<ArrayList<String>>() {}.getType();
            services = gson.fromJson(json, type);
        }
        ArrayList<String> finalServices = services;

        db.getOrganizationsByParameter("categories", city, categories, new FirebaseData.OnGetDataListener() {
            ArrayList<Organization> organizationsByServices = new ArrayList<>();

            @Override
            public void onStart() {
                openDialog();
            }

            @Override
            public void onSuccess(ArrayList<Organization> data) {
                organizationsByCategory = data;
                db.getOrganizationsByParameter("gen_services", city, finalServices, new FirebaseData.OnGetDataListener() {
                    @Override
                    public void onStart() {}

                    @Override
                    public void onSuccess(ArrayList<Organization> data) {
                        organizationsByServices = data;

                        if(organizationsByCategory.size()!=0 || organizationsByServices.size()!=0) {
                            for(Organization o : organizationsByCategory)
                                organizationsByServices.removeIf(o::equals);
                        }

                        organizationsByCategory.addAll(organizationsByServices);
                        organizationsByCategory.sort(Comparator.comparing(Organization::getRating).reversed());

                        if (organizationsByCategory.size()==0)
                            changeHeader();

                        else {
                            setAdapter(organizationsByCategory);
                            adapter.setOnItemClickListener(new CardAdapter.onItemClickListener() {
                                @Override
                                public void onItemClick(int position) {
                                    openActivityOrganization(organizationsByCategory.get(position).getId());
                                }
                            });
                        }
                        dialog.cancel();
                    }
                });
            }
        });
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

    private void openDialog() {
        dialog = new Dialog(this.getContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.setContentView(R.layout.dialog_loading);

        dialog.show();
    }

    private void changeHeader() {
        header.setText("К сожалению ничего подходящего не нашлось.");
    }
}