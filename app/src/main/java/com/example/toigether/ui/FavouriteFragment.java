package com.example.toigether.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toigether.FirebaseData;
import com.example.toigether.R;
import com.example.toigether.WelcomePage;
import com.example.toigether.adapters.CardAdapter;
import com.example.toigether.items.Organization;
import com.example.toigether.organizations.OrganizationActivity;

import java.util.ArrayList;

public class FavouriteFragment extends Fragment {

    private ArrayList<Organization> organizations = new ArrayList<>();
    private RecyclerView recyclerView;
    private CardAdapter adapter;
    private FirebaseData db = new FirebaseData();
    private Dialog dialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewFavourite);
        setFavourite();

        return view;
    }

    public void openActivityOrganization(String id) {
        Intent intent = new Intent(getActivity(), OrganizationActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    private void setFavourite() {
        db.getTopRating(new FirebaseData.OnGetDataListener() {
            @Override
            public void onStart() {
                openDialog();
            }

            @Override
            public void onSuccess(ArrayList<Organization> data) {
                setAdapter(data);

                adapter.setOnItemClickListener(new CardAdapter.onItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        openActivityOrganization(data.get(position).getId());
                    }
                });

                dialog.cancel();
            }
        });
    }

    private void setAdapter(ArrayList<Organization> organizations) {
        RecyclerView.LayoutManager layoutManager;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getContext());
        adapter = new CardAdapter(organizations, "favourite");

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void openDialog() {
        dialog = new Dialog(this.getContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.setContentView(R.layout.dialog_loading);

        dialog.show();
    }
}