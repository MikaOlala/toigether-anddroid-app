package com.example.toigether.ui.favourites;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toigether.R;
import com.example.toigether.adapters.CardAdapter;
import com.example.toigether.items.Organization;
import com.example.toigether.ui.favourites.FavouriteViewModel;

import java.util.ArrayList;

public class FavouriteFragment extends Fragment {

    private FavouriteViewModel mViewModel;

    public static com.example.toigether.ui.favourites.FavouriteFragment newInstance() {
        return new com.example.toigether.ui.favourites.FavouriteFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewFavourite);
        RecyclerView.Adapter adapter;
        RecyclerView.LayoutManager layoutManager;

        ArrayList<Organization> organizations = new ArrayList<>();
        organizations.add(new Organization(1L, "BN Event organization", "BN organization - adorable organization which u would like bla bla", R.drawable.organization_bm));
        organizations.add(new Organization(2L, "Gravum Event Masters", "We are adorable organization too, u would like us either a-la-la", R.drawable.gravum));
        organizations.add(new Organization(3L, "Title Event Organizators", "We are organization with no name but we still believe that u will choose us", R.drawable.org));

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getContext());
        adapter = new CardAdapter(organizations, "favourite");

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FavouriteViewModel.class);

    }

}