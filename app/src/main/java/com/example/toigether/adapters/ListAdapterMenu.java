package com.example.toigether.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toigether.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ListAdapterMenu extends RecyclerView.Adapter<ListAdapterMenu.CardViewHolder> {
    private final ArrayList<String> keys;
    private final ArrayList<String> values;

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView text;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.foodType);
            text = itemView.findViewById(R.id.foodName);
        }
    }

    public ListAdapterMenu(Map<String, String> menu) {
        keys = new ArrayList<>(menu.keySet());
        values = new ArrayList<>(menu.values());
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_menu, parent, false);

        return new CardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        holder.title.setText(keys.get(position));
        holder.text.setText(values.get(position).replace(", ", "\n"));
    }

    @Override
    public int getItemCount() {
        return keys.size();
    }

}
