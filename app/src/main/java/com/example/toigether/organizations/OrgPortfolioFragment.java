package com.example.toigether.organizations;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.toigether.FirebaseData;
import com.example.toigether.R;
import com.example.toigether.adapters.CardAdapterEvents;
import com.example.toigether.events.EventActivity;
import com.example.toigether.items.Event;
import com.example.toigether.items.Organization;

import java.util.ArrayList;

public class OrgPortfolioFragment extends Fragment {

    private RecyclerView recyclerView;
    private CardAdapterEvents adapter;
    private final FirebaseData db = new FirebaseData();
    private TextView noPortfolio;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.org_portfolio_fragment, container, false);

        String value = getActivity().getIntent().getExtras().getString("id");
        recyclerView = view.findViewById(R.id.recyclerViewPortfolio);
        noPortfolio = view.findViewById(R.id.noPortfolio);
        setPortfolio(value);

        return view;
    }

    public void openActivityEvent(String id) {
        Intent intent = new Intent(getActivity(), EventActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    private void setPortfolio(String id) { // didn't realize passing organizator_id through fragmentManager....
        db.getOrganization(id, new FirebaseData.OnGetOneListener() {
            @Override
            public void onStart() {}

            @Override
            public void onSuccess(Organization data) {
                db.getPortfolioByOrganization(data.getOrganizator_id(), new FirebaseData.OnGetEventsListener() {
                    @Override
                    public void onStart() {}

                    @Override
                    public void onSuccess(ArrayList<Event> events) {
                        if (events.size()==0)
                            noPortfolio.setVisibility(View.VISIBLE);
                        else {
                            setAdapter(events);
                            adapter.setOnItemClickListener(new CardAdapterEvents.onItemClickListener() {
                                @Override
                                public void onItemClick(int position) {
                                    openActivityEvent(events.get(position).getId());
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    private void setAdapter(ArrayList<Event> events) {
        RecyclerView.LayoutManager layoutManager;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getContext());
        adapter = new CardAdapterEvents(events, "portfolio");

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}