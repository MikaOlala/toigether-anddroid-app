package com.example.toigether.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toigether.R;
import com.example.toigether.items.Event;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CardAdapterEvents extends RecyclerView.Adapter<CardAdapterEvents.CardViewHolder> {
    private ArrayList<Event> events;
    private onItemClickListener listener;
    private final String variant;

    public interface onItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView title;
        public TextView date;

        public CardViewHolder(@NonNull View itemView, onItemClickListener listener) {
            super(itemView);
            image = itemView.findViewById(R.id.eventPic);
            title = itemView.findViewById(R.id.eventTitle);
            date = itemView.findViewById(R.id.eventDate);

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

    public CardAdapterEvents(ArrayList<Event> events, String variant) {
        this.events = events;
        this.variant = variant;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        switch (variant) {
            case "portfolio":
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_portfolio, parent, false);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + variant); // Add here cards from main menu
        }

        CardViewHolder cardViewHolder = new CardViewHolder(v, listener);
        return cardViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        Event event = events.get(position);

        if (event.getImages().size()!=0)
            Picasso.get().load(event.getImages().get(0)).into(holder.image);
        holder.title.setText(event.getName());
        holder.date.setText(R.string.ex_date);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

}
