package com.example.toigether.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toigether.R;
import com.example.toigether.items.Service;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {
    private ArrayList<Service> services;
    private onItemClickListener listener;
    private String variant;

    public interface onItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView text;

        public ListViewHolder(@NonNull View itemView, onItemClickListener listener) {
            super(itemView);
            title = itemView.findViewById(R.id.nameService);
            text = itemView.findViewById(R.id.contentService);

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

    public ListAdapter(ArrayList<Service> services, String variant) {
        this.services = services;
        this.variant = variant;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        switch (variant) {
            case "organization":
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.program_list, parent, false);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + variant); // Add here cards from main menu
        }

        ListViewHolder listViewHolder = new ListViewHolder(v, listener);
        return listViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        Service service = services.get(position);

        holder.title.setText(service.getName());
        holder.text.setText(service.getDescription());
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

}
