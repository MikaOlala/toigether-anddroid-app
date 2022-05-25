package com.example.toigether.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toigether.R;
import com.example.toigether.items.Organization;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CardAdapterReserve extends RecyclerView.Adapter<CardAdapterReserve.CardViewHolder> {
    private ArrayList<Organization> organizations;
    private onItemClickListener listener;
    private String variant;

    public interface onItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView title;
        public TextView text;
        public TextView rating;

        public CardViewHolder(@NonNull View itemView, onItemClickListener listener) {
            super(itemView);
            image = itemView.findViewById(R.id.organizationImg);
            title = itemView.findViewById(R.id.organizationTitle);
            text = itemView.findViewById(R.id.organizationText);
            rating = itemView.findViewById(R.id.rating);

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

    public CardAdapterReserve(ArrayList<Organization> organizations, String variant) {
        this.organizations = organizations;
        this.variant = variant;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        switch (variant) {
            case "favourite":
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
                break;
            case "generation":
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_generation_results, parent, false);
                break;
            case "main":
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_main_page, parent, false);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + variant); // Add here cards from main menu
        }

        CardViewHolder cardViewHolder = new CardViewHolder(v, listener);
        return cardViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        Organization organization = organizations.get(position);

        Picasso.get().load(organization.getImage()).into(holder.image);
        holder.title.setText(organization.getName());
        holder.text.setText(organization.getDescription());
        if (!variant.equals("favourite"));
            holder.rating.setText(String.valueOf(organization.getRating()));
    }

    @Override
    public int getItemCount() {
        return organizations.size();
    }

}
