package com.example.toigether.ui.meetings;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.toigether.R;
import com.example.toigether.ui.generation.GenerationViewModel;

public class MeetingFragment extends Fragment {

    private MeetingViewModel mViewModel;

    public static MeetingFragment newInstance() {
        return new MeetingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meeting, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MeetingViewModel.class);
        // TODO: Use the ViewModel
    }

}