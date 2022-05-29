package com.example.toigether.events;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.example.toigether.FirebaseData;
import com.example.toigether.R;
import com.example.toigether.adapters.ImageAdapter;
import com.example.toigether.items.Event;

public class EventGalleryFragment extends Fragment {

    private final FirebaseData db = new FirebaseData();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_gallery_fragment, container, false);

        Context context = getContext();
        TextView noImages = view.findViewById(R.id.noImages);
        GridView grid = view.findViewById(R.id.grid);
        String id = getArguments().getString("id");

        if (id!=null) {
            db.getEvent(id, new FirebaseData.OnGetEventListener() {
                @Override
                public void onStart() {db.openDialog(getContext());}

                @Override
                public void onSuccess(Event event) {
                    if (event.getImages().size()!=0)
                        grid.setAdapter(new ImageAdapter(event.getImages(), context));
                    else
                        noImages.setVisibility(View.VISIBLE);
                    db.closeDialog();
                }
            });
        }

        return view;
    }
}