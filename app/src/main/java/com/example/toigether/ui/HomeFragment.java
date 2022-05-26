package com.example.toigether.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toigether.FirebaseData;
import com.example.toigether.Profile;
import com.example.toigether.Login;
import com.example.toigether.R;
import com.example.toigether.adapters.CardAdapter;
import com.example.toigether.adapters.CardAdapterCategories;
import com.example.toigether.items.Category;
import com.example.toigether.items.Organization;
import com.example.toigether.items.User;
import com.example.toigether.organizations.OrgProgramFragment;
import com.example.toigether.organizations.OrganizationActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerRating;
    private RecyclerView recyclerRecently;
    private RecyclerView recyclerCategory;
    private CardAdapter adapterRating;
    private CardAdapter adapterRecently;
    private ImageView avatar;
    private final FirebaseData db = new FirebaseData();
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private User currentUser;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        View profile = view.findViewById(R.id.profile);
        avatar = view.findViewById(R.id.profileIcon);
        TextView all = view.findViewById(R.id.all);
        recyclerRating = view.findViewById(R.id.recyclerViewTopRating);
        recyclerRecently = view.findViewById(R.id.recyclerViewRecentlyWatched);
        recyclerCategory = view.findViewById(R.id.recyclerViewCategories);

        setMainMenuData();
        if (db.isAuthenticated())
            setAvatar();

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (db.isAuthenticated())
                    openActivityProfile();
                else
                    openActivityLogin();
            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.navigation_generation);
            }
        });

        return view;
    }

    public void openActivityOrganization(String id) {
        Intent intent = new Intent(getActivity(), OrganizationActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    public void openActivityProfile() {
        Intent intent = new Intent(getActivity(), Profile.class);
        startActivity(intent);
    }

    public void openActivityLogin() {
        Intent intent = new Intent(getActivity(), Login.class);
        startActivity(intent);
    }

    private void setAvatar() {
        db.getUser(auth.getCurrentUser().getEmail(), new FirebaseData.OnGetUserListener() {
            @Override
            public void onStart() {}

            @Override
            public void onSuccess(User user) {
                if (user.getAvatar()!=null) {
                    if(user.getAvatar().length()!=0)
                        Picasso.get().load(Uri.parse(user.getAvatar())).into(avatar);
                }
            }
        });
    }

    private void setMainMenuData() {
        db.getTopRating(new FirebaseData.OnGetDataListener() {
            @Override
            public void onStart() {}

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
                public void onStart() {}

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

            db.getCategories(new FirebaseData.OnGetCategoriesListener() {
                @Override
                public void onStart() {
                }

                @Override
                public void onSuccess(ArrayList<Category> categories) {
                    setAdapterCategory(categories);
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
        }
        else if (recyclerName.equals("adapterRecently")){
            adapterRecently = new CardAdapter(organizations, "main");
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapterRecently);
        }

        Log.i(recyclerName + " CardAdapter: list size", String.valueOf(organizations.size()));
    }

    private void setAdapterCategory(ArrayList<Category> categories) {
        RecyclerView.LayoutManager layoutManager;
        recyclerCategory.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false);
        CardAdapterCategories adapterCategories = new CardAdapterCategories(categories);
        recyclerCategory.setLayoutManager(layoutManager);
        recyclerCategory.setAdapter(adapterCategories);

        Log.i("CardAdapterCategory: list size", String.valueOf(categories.size()));
    }

}