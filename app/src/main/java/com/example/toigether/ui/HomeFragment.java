package com.example.toigether.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerRating;
    private RecyclerView recyclerRecently;
    private CardAdapter adapterRating;
    private CardAdapter adapterRecently;
    private final FirebaseData db = new FirebaseData();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerRating = view.findViewById(R.id.recyclerViewTopRating);
        recyclerRecently = view.findViewById(R.id.recyclerViewRecentlyWatched);

        setMainMenuData();

        return view;
    }

    public void openActivityOrganization(String id) {
        Intent intent = new Intent(getActivity(), OrganizationActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    private void setMainMenuData() {
        db.getTopRating(new FirebaseData.OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ArrayList<Organization> data) {
                setAdapter(data, recyclerRating, "adapterRating");
                adapterRating.setOnItemClickListener(new CardAdapter.onItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        openActivityOrganization(data.get(position).getId());
                    }
                });
            }
        });

        db.getOrganizationByCategory("Вечеринка", new FirebaseData.OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ArrayList<Organization> data) {
                setAdapter(data, recyclerRecently, "adapterRecently");
                adapterRecently.setOnItemClickListener(new CardAdapter.onItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        openActivityOrganization(data.get(position).getId());
                    }
                });
            }
        });
    }

    private void setAdapter(ArrayList<Organization> organizations, RecyclerView recyclerView, String recyclerName) {
        RecyclerView.LayoutManager layoutManager;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false);
        if (recyclerName.equals("adapterRating")) {
            adapterRating = new CardAdapter(organizations, "main");
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapterRating);
        } else {
            adapterRecently = new CardAdapter(organizations, "main");
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapterRecently);
        }


        Log.e("CardAdapter", String.valueOf(organizations.size()));
    }
}