package com.example.toigether.events;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.GridView;
import android.widget.TextView;

import com.example.toigether.FirebaseData;
import com.example.toigether.R;
import com.example.toigether.adapters.ImageAdapter;
import com.example.toigether.items.Event;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class EventGalleryFragment extends Fragment {

    private FirebaseData db = new FirebaseData();
    private Dialog dialog;

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
                public void onStart() {openDialog();}

                @Override
                public void onSuccess(Event event) {
                    if (event.getImages().size()!=0)
                        grid.setAdapter(new ImageAdapter(event.getImages(), context));
                    else
                        noImages.setVisibility(View.VISIBLE);
                    dialog.cancel();
                }
            });
        }

        return view;
    }

    private void openDialog() {
        dialog = new Dialog(this.getContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.setContentView(R.layout.dialog_loading);

        dialog.show();
    }
}