package com.example.toigether.events;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.toigether.FirebaseData;
import com.example.toigether.R;
import com.example.toigether.adapters.CardAdapterEvents;
import com.example.toigether.adapters.ImageAdapter;
import com.example.toigether.adapters.ListAdapterMenu;
import com.example.toigether.items.Event;

import java.util.ArrayList;
import java.util.Map;

public class EventMenuFragment extends Fragment {

    private final FirebaseData db = new FirebaseData();
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_menu_fragment, container, false);

        String id = getArguments().getString("id");
        recyclerView = view.findViewById(R.id.recyclerViewMenu);

        if (id!=null) {
            db.getEvent(id, new FirebaseData.OnGetEventListener() {
                @Override
                public void onStart() {}

                @Override
                public void onSuccess(Event event) {
                    if (event.getMenu()!=null)
                        setAdapter(event.getMenu());
                }
            });
        }

        return view;
    }

    private void setAdapter(Map<String, String> menu) {
        RecyclerView.LayoutManager layoutManager;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getContext());
        ListAdapterMenu adapter = new ListAdapterMenu(menu);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}