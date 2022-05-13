package com.example.toigether.events;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.toigether.FirebaseData;
import com.example.toigether.R;
import com.example.toigether.adapters.TLGenerationAdapter;
import com.example.toigether.items.Event;
import com.google.android.material.tabs.TabLayout;

public class EventActivity extends AppCompatActivity {

    private TextView title;
    private FirebaseData db = new FirebaseData();
    private Event ev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        String id = getIntent().getExtras().getString("id");

        View back = findViewById(R.id.back);
        title = findViewById(R.id.eventTitle);

        setTab();
        setEvent(id);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setTab() {
        TabLayout tabLayout = findViewById(R.id.tabLayoutEvent);
        ViewPager viewPager = findViewById(R.id.pagerEvent);
        tabLayout.setupWithViewPager(viewPager);

        TLGenerationAdapter tlGenerationAdapter = new TLGenerationAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        tlGenerationAdapter.addFragment(new EventGalleryFragment(), "Галерея");
        tlGenerationAdapter.addFragment(new EventMenuFragment(), "Меню");
        tlGenerationAdapter.addFragment(new EventServiceFragment(), "Услуги");
        tlGenerationAdapter.addFragment(new EventDecorationFragment(), "Декорации");

        viewPager.setAdapter(tlGenerationAdapter);
    }

    private void setEvent(String id) {
        db.getEvent(id, new FirebaseData.OnGetEventListener() {
            @Override
            public void onStart() {}

            @Override
            public void onSuccess(Event event) {
                ev = event;
                title.setText(event.getName());
            }
        });
    }
}