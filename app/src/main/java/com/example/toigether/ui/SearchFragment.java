package com.example.toigether.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toigether.FirebaseData;
import com.example.toigether.R;
import com.example.toigether.adapters.CardAdapter;
import com.example.toigether.items.Organization;

import java.util.ArrayList;
import java.util.Collections;

public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;
    private final FirebaseData db = new FirebaseData();
    private TextView notFound;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewSearch);
        notFound = view.findViewById(R.id.notFound);
        SearchView search = view.findViewById(R.id.search);

        if (getArguments()!=null) {
            String category = getArguments().getString("category");
            search.setQuery(category, true);
        }

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                doSearch(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return view;
    }

    private void doSearch(String str) {
        db.getOrganizationByName(str, new FirebaseData.OnGetDataListener() {
            @Override
            public void onStart() {
                db.openDialog(getContext());
            }

            @Override
            public void onSuccess(ArrayList<Organization> dataByName) {
                db.getOrganizationByCategory(str, new FirebaseData.OnGetDataListener() {
                    @Override
                    public void onStart() {}

                    @Override
                    public void onSuccess(ArrayList<Organization> dataByCategory) {
                        dataByName.addAll(dataByCategory);
                        if (dataByName.isEmpty())
                            notFound.setVisibility(View.VISIBLE);
                        else
                            setAdapter(dataByName);
                        db.closeDialog();
                    }
                });
            }
        });
    }

    private void setAdapter(ArrayList<Organization> organizations) {
        RecyclerView.LayoutManager layoutManager;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getContext());
        CardAdapter adapter = new CardAdapter(organizations, "generation");

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        Log.i("CardAdapter", String.valueOf(organizations.size()));
    }

}
