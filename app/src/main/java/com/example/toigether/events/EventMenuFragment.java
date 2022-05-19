package com.example.toigether.events;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.toigether.FirebaseData;
import com.example.toigether.R;
import com.example.toigether.adapters.ImageAdapter;
import com.example.toigether.items.Event;

public class EventMenuFragment extends Fragment {

    private final FirebaseData db = new FirebaseData();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_menu_fragment, container, false);

        String id = getArguments().getString("id");

        if (id!=null) {
            db.getEvent(id, new FirebaseData.OnGetEventListener() {
                @Override
                public void onStart() {}

                @Override
                public void onSuccess(Event event) {

                }
            });
        }

        return view;
    }
}