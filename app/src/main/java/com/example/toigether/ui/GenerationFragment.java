package com.example.toigether.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.toigether.R;
import java.util.ArrayList;

public class GenerationFragment extends Fragment {

    public static GenerationFragment newInstance() {
        return new GenerationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_generation, container, false);

        SharedPreferences prefs = this.getActivity().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);

        final ListView list = view.findViewById(R.id.categoryList);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String category = "";
                category = list.getItemAtPosition(i).toString();
                Bundle bundle = new Bundle();
                bundle.putString("categoryName", category);

                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("date", null).apply();
                editor.putString("city", null).apply();
                editor.putString("location", null).apply();
                editor.putString("services", null).apply();
                editor.putString("quantityOfServices", null).apply();
                editor.putString("budget", null).apply();

                Navigation.findNavController(view).navigate(R.id.action_navigation_generation_to_tabLayoutFragment, bundle);
            }
        });

        ArrayList<String> categories = new ArrayList<>();
        categories.add("День рождения");
        categories.add("Свадьба");
        categories.add("Корпоратив");
        categories.add("Тимбилдинг");
        categories.add("Детский утренник");
        categories.add("Вечеринка");
        categories.add("Открытие");
        categories.add("Фестивали, ярмарки");
        categories.add("Выставки, пресс-конференции");
        categories.add("Светские рауты");
        categories.add("Частные мероприятия");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1, categories);
        list.setAdapter(arrayAdapter);

        return view;
    }
}