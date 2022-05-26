package com.example.toigether.ui;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toigether.FirebaseData;
import com.example.toigether.Login;
import com.example.toigether.Profile;
import com.example.toigether.R;
import com.example.toigether.WelcomePage;
import com.example.toigether.adapters.CardAdapter;
import com.example.toigether.items.Organization;
import com.example.toigether.items.User;
import com.example.toigether.organizations.OrganizationActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FavouriteFragment extends Fragment {

    private RecyclerView recyclerView;
    private CardAdapter adapter;
    private final FirebaseData db = new FirebaseData();
    private Dialog dialog;
    private ImageView avatar;
    private TextView noFavourite;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewFavourite);
        avatar = view.findViewById(R.id.profileIcon);
        View profile = view.findViewById(R.id.profile);
        noFavourite = view.findViewById(R.id.noFavourite);

        if (db.isAuthenticated())
            setUser();

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityProfile();
            }
        });

        return view;
    }

    public void openActivityOrganization(String id) {
        Intent intent = new Intent(getActivity(), OrganizationActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    private void openActivityProfile() {
        Intent intent;
        if (db.isAuthenticated())
            intent = new Intent(getActivity(), Profile.class);
        else
            intent = new Intent(getActivity(), Login.class);
        startActivity(intent);
    }

    private void setFavourite(User user) {
        db.getFavouriteList(user, new FirebaseData.OnGetDataListener() {
            @Override
            public void onStart() {}

            @Override
            public void onSuccess(ArrayList<Organization> data) {
                if (data.size()==0)
                    noFavourite.setVisibility(View.VISIBLE);
                else {
                    setAdapter(data);
                    adapter.setOnItemClickListener(new CardAdapter.onItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            openActivityOrganization(data.get(position).getId());
                        }
                    });
                }
                dialog.cancel();
            }
        });
    }

    // change this code with getArguments Parcelable
    private void setUser() {
        db.getUser(db.getCurrentUserEmail(), new FirebaseData.OnGetUserListener() {
            @Override
            public void onStart() {
                openDialog();
            }

            @Override
            public void onSuccess(User user) {
                if (user.getAvatar()!=null) {
                    if(user.getAvatar().length()!=0)
                        Picasso.get().load(Uri.parse(user.getAvatar())).into(avatar);
                }

                if (user.getFavourite() != null || user.getFavourite().size()>0)
                    setFavourite(user);
                else
                    noFavourite.setVisibility(View.VISIBLE);
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