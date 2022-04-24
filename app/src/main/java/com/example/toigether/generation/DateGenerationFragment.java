package com.example.toigether.generation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toigether.R;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateGenerationFragment extends Fragment {

    private SharedPreferences prefs;
    private CalendarView calendarView;
    private View toastView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.generation_date, container, false);

        prefs = this.getActivity().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);

        calendarView = view.findViewById(R.id.calendar);
        LayoutInflater inflaterToast = getLayoutInflater();
        toastView = inflaterToast.inflate(R.layout.toast, (ViewGroup) view.findViewById(R.id.toast));

        ifMadeEarlier();

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String chosenDateString = i2 + "." + (i1 + 1) + "." + i;

                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                try {
                    Date chosenDate = sdf.parse(chosenDateString);
                    if(chosenDate.before(date)) {
                        showToast();
                    }
                    else {
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("date", chosenDateString).apply();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

    private void ifMadeEarlier() {
        String data = prefs.getString("date", null);
        if (data!=null) {
            try {
                calendarView.setDate(new SimpleDateFormat("dd.MM.yyyy").parse(data).getTime(), true, true);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private void showToast() {
        TextView text1 = toastView.findViewById(R.id.text_1);
        TextView text2 = toastView.findViewById(R.id.text_2);
        CardView card = toastView.findViewById(R.id.toast);

        card.setCardBackgroundColor(getResources().getColor(R.color.red_trans));
        text1.setText("Нельзя выбрать прошедшую дату");
        text2.setText("Результаты поиска будут некорректными");

        Toast toast = new Toast(getContext());
        toast.setGravity(Gravity.TOP, 0, 30);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(toastView);
        toast.show();
    }
}