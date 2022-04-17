package com.example.toigether.generation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.toigether.R;

public class LocationGenerationFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.generation_location, container, false);

        SharedPreferences prefs = this.getActivity().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);

        RadioGroup radioGroup = view.findViewById(R.id.groupLocation);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                TextView text = view.findViewById(i);

                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("location", String.valueOf(text.getText())).apply();
            }
        });

        return view;
    }
}