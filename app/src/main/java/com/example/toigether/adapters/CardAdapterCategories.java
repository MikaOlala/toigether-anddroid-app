package com.example.toigether.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toigether.R;
import com.example.toigether.items.Category;
import com.example.toigether.items.Event;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CardAdapterCategories extends RecyclerView.Adapter<CardAdapterCategories.CardViewHolder> {
    private ArrayList<Category> categories;
    private onItemClickListener listener;

    public interface onItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView title;

        public CardViewHolder(@NonNull View itemView, onItemClickListener listener) {
            super(itemView);
            image = itemView.findViewById(R.id.categoryPic);
            title = itemView.findViewById(R.id.categoryTitle);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public CardAdapterCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_home_category, parent, false);

        CardViewHolder cardViewHolder = new CardViewHolder(v, listener);
        return cardViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        Category category = categories.get(position);

        Picasso.get().load(category.getImage()).into(holder.image);
        holder.title.setText(category.getName());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

}
