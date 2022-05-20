package com.example.toigether.events;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.toigether.FirebaseData;
import com.example.toigether.R;
import com.example.toigether.items.Event;

import org.w3c.dom.Text;

public class EventDecorationFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_decoration_fragment, container, false);

        TextView decorations = view.findViewById(R.id.decorations);
        String id = getArguments().getString("id");
        FirebaseData db = new FirebaseData();

        db.getEvent(id, new FirebaseData.OnGetEventListener() {
            @Override
            public void onStart() {}

            @Override
            public void onSuccess(Event event) {
                event.setDecorations(event.getDecorations().replace(", ", "\n"));
                decorations.setText(event.getDecorations());
            }
        });

        return view;
    }
}