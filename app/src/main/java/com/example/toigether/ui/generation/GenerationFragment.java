package com.example.toigether.ui.generation;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.toigether.R;
import com.example.toigether.ui.generation.GenerationViewModel;

import java.util.ArrayList;

public class GenerationFragment extends Fragment {

    private GenerationViewModel mViewModel;

    public static GenerationFragment newInstance() {
        return new GenerationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_generation, container, false);

        final ListView list = view.findViewById(R.id.categoryList);
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(GenerationViewModel.class);
        // TODO: Use the ViewModel
    }

}