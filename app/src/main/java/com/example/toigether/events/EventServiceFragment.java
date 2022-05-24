package com.example.toigether.events;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.toigether.FirebaseData;
import com.example.toigether.R;
import com.example.toigether.items.Event;

public class EventServiceFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_service_fragment, container, false);

        TextView services = view.findViewById(R.id.services);
        TextView noServices = view.findViewById(R.id.noService);
        String id = getArguments().getString("id");
        FirebaseData db = new FirebaseData();

        db.getEvent(id, new FirebaseData.OnGetEventListener() {
            @Override
            public void onStart() {}

            @Override
            public void onSuccess(Event event) {
                if (event.getServices()!=null)
                    services.setText(TextUtils.join("\n", event.getServices()));
                else
                    noServices.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }
}