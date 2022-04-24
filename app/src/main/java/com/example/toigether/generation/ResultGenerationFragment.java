package com.example.toigether.generation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.toigether.R;

public class ResultGenerationFragment extends Fragment {

    private SharedPreferences prefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.generation_result, container, false);

        prefs = this.getActivity().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);

        TextView date = view.findViewById(R.id.date);
        TextView city = view.findViewById(R.id.city);
        TextView location = view.findViewById(R.id.location);
        TextView service = view.findViewById(R.id.service);
        TextView budget = view.findViewById(R.id.budget);

        setText("date", date);
        setText("city", city);
        setText("location", location);
        setText("quantityOfServices", service);
        setText("budget", budget);

        SpannableString content = new SpannableString(service.getText().toString());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        service.setText(content);

        budget.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }

    public void setText(String variable, TextView textView) {
        String data = prefs.getString(variable, null);
        String result = "";
        if(data == null) {
            result = "Не выбрано";
        }
        else {
            if (variable.equals("quantityOfServices")) {
                if(data.equals("1"))
                    result = "Выбрана " + data + " опция";
                else if (data.equals("2") || data.equals("3") || data.equals("4"))
                    result = "Выбрано " + data + " опции";
                else
                    result = "Выбрано " + data + " опций";
            }
            else if(variable.equals("budget"))
                result = data + " KZT";
            else
                result = data;
        }

        textView.setText(result);
    }

}