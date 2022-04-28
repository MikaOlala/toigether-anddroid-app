package com.example.toigether.generation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.toigether.R;
import com.example.toigether.adapters.CardAdapter;
import com.example.toigether.items.Org;
import com.example.toigether.items.Organization;
import com.example.toigether.organizations.OrganizationActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class ResultListFragment extends Fragment {

    private RecyclerView recyclerView;
    private CardAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewGeneration);

        getOrganizations();



//        organizations.add(new Organization(1L, "BN Event organization", "BN organization - adorable organization which u would like bla bla", R.drawable.organization_bm));
//        organizations.add(new Organization(2L, "Gravum Event Masters", "We are adorable organization too, u would like us either a-la-la", R.drawable.gravum));
//        organizations.add(new Organization(3L, "Title Event Organizators", "We are organization with no name but we still believe that u will choose us", R.drawable.org));
//        organizations.add(new Organization(4L, "Title Event Organizators", "We are organization with no name but we still believe that u will choose us", R.drawable.organization_wedding));


        adapter.setOnItemClickListener(new CardAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                openActivityOrganization(organizations.get(position).getId());
            }
        });

        return view;
    }

    public void openActivityOrganization(Long id) {
        Intent intent = new Intent(getActivity(), OrganizationActivity.class);
        intent.putExtra("id", id.toString());
        startActivity(intent);
    }

    private void getOrganizations() {
        CountDownLatch done = new CountDownLatch(1);
        SharedPreferences prefs = getContext().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        ArrayList<Organization> organizations = new ArrayList<>();

        String category = getArguments().getString("categoryName");
        ArrayList<String> categories = new ArrayList<>();
        categories.add(category);

        String city = prefs.getString("city", null);

        ArrayList<String> services = new ArrayList<>();
        Gson gson = new Gson();
        String json = prefs.getString("services", null);
        if(json!=null) {
            Type type = new TypeToken<ArrayList<String>>() {}.getType();
            services = gson.fromJson(json, type);
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("organizations")
                .whereEqualTo("town", city).whereArrayContainsAny("gen_services", services)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.e("ResultFragment getOrganizations", String.valueOf(document.getData()));
                        Organization organization = document.toObject(Organization.class);
                        organizations.add(organization);
                    }
                    done.countDown();
                } else {
                    Log.d("ResultFragment getOrganizations", "Error getting documents: ", task.getException());
                }
            }
        });

        try {
            done.await();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }


        setAdapter(organizations);
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