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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {
    private ArrayList<Organization> organizations;

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView title;
        public TextView text;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.organizationImg);
            title = itemView.findViewById(R.id.organizationTitle);
            text = itemView.findViewById(R.id.organizationText);
        }
    }

    public CardAdapter(ArrayList<Organization> organizations) {
        this.organizations = organizations;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        CardViewHolder cardViewHolder = new CardViewHolder(v);
        return cardViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        Organization organization = organizations.get(position);

        holder.image.setImageResource(organization.getImage());
        holder.title.setText(organization.getName());
        holder.text.setText(organization.getDescription());
    }

    @Override
    public int getItemCount() {
        return organizations.size();
    }
}
