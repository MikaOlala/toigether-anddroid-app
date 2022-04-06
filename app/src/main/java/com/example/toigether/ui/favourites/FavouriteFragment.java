package com.example.toigether.ui.favourites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.toigether.R;
import com.example.toigether.ui.favourites.FavouriteViewModel;

public class FavouriteFragment extends Fragment {

    private FavouriteViewModel mViewModel;

    public static com.example.toigether.ui.favourites.FavouriteFragment newInstance() {
        return new com.example.toigether.ui.favourites.FavouriteFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favourite, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FavouriteViewModel.class);
        // TODO: Use the ViewModel
    }

}