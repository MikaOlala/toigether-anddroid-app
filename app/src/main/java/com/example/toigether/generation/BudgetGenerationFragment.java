package com.example.toigether.generation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.toigether.R;
import com.google.android.material.slider.Slider;

public class BudgetGenerationFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.generation_budget, container, false);

        SharedPreferences prefs = this.getActivity().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);

        TextView textView = view.findViewById(R.id.sum);
        Slider slider = view.findViewById(R.id.slider);
        EditText editText = view.findViewById(R.id.input);



        slider.addOnChangeListener(new Slider.OnChangeListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                int calculatedValue = (int) (slider.getValue() * 10000000 / 100);
                String result = String.format("%,d", calculatedValue);
                textView.setText(result + " KZT");
            }
        });

        slider.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {

            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {
                int calculatedValue =  ((int) slider.getValue() * 10000000 / 100);
                String result = String.format("%,d", calculatedValue);
                editText.setText(result);
                textView.setText(result + " KZT");
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String editableString = editable.toString();
                String editableClear = editableString.replace(",", "");
                if (!editableClear.equals("")) {
                    long number = Long.parseLong(editableClear);
                    if (number <= 0) {
                        slider.setValue(0);
                        textView.setText(0 + " KZT");
                    } else if (number >= 10000000) {
                        slider.setValue(100);
                        textView.setText("10,000,000" + " KZT");
                    } else {
                        float percents = number * 100 / 10000000;
                        slider.setValue(percents);
//                        textView.setText(editText.getText() + " KZT");
                    }
                }

                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("budget", editableString + " KZT").apply();
            }

        });

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
            }
        });

        return view;
    }
}