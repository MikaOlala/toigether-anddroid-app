package com.example.toigether.organizations;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toigether.FirebaseData;
import com.example.toigether.Login;
import com.example.toigether.R;
import com.example.toigether.adapters.TLGenerationAdapter;
import com.example.toigether.items.Organization;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class OrganizationActivity extends AppCompatActivity {

    private final FirebaseData db = new FirebaseData();
    private ArrayList<String> choice;
    private ImageView pic;
    private TextView name, content, rating;
    private Dialog dialog;
    private SharedPreferences prefs;
    private String orgEmailId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization);

        prefs = this.getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        String value = getIntent().getExtras().getString("id");

        pic = findViewById(R.id.orgPic);
        name = findViewById(R.id.orgName);
        content = findViewById(R.id.orgContent);
        rating = findViewById(R.id.rating);
        Button meeting = findViewById(R.id.makeMeeting);

        setTab();
        setOrganization(value);

        meeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                OrgProgramFragment fragment = (OrgProgramFragment)fm.findFragmentById(R.id.orgProgramFragment);
                Log.e("fragment program null", String.valueOf(fragment==null));
                if (fragment != null) {
                    choice = fragment.getChoice();
                }

                if (db.isAuthenticated())
                    openDialog();
                else
                    openActivityLogin();
            }
        });
    }

    private void openActivityLogin() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    private void setOrganization(String id) {
        db.getOrganization(id, new FirebaseData.OnGetOneListener() {
            @Override
            public void onStart() {}

            @Override
            public void onSuccess(Organization data) {
                Picasso.get().load(data.getImage()).into(pic);
                name.setText(data.getName());
                content.setText(data.getDescription());
                rating.setText(String.valueOf(data.getRating()));
                orgEmailId = data.getOrganizator_id();
            }
        });
    }

    private void setTab() {
        TabLayout tabLayout = findViewById(R.id.tabLayoutOrganization);
        ViewPager viewPager = findViewById(R.id.pagerOrganization);
        tabLayout.setupWithViewPager(viewPager);

        TLGenerationAdapter tlGenerationAdapter = new TLGenerationAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        tlGenerationAdapter.addFragment(new OrgProgramFragment(), "Программы");
        tlGenerationAdapter.addFragment(new OrgPortfolioFragment(), "Портфолио");
        tlGenerationAdapter.addFragment(new OrgReviewFragment(), "Отзыв");
        tlGenerationAdapter.addFragment(new OrgTeamFragment(), "Команда");

        viewPager.setAdapter(tlGenerationAdapter);
    }

    private void openDialog() {
        dialog = new Dialog(OrganizationActivity.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.black_trans);
        dialog.setContentView(R.layout.dialog_make_meeting);

        TextView cancel = dialog.findViewById(R.id.cancel);
        Button call = dialog.findViewById(R.id.call);
        ListView list = dialog.findViewById(R.id.servicesList);

        Gson gson = new Gson();
        String json = prefs.getString("servicesToContact", "");
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        choice = gson.fromJson(json, type);

        if(choice.size() > 6){
            list.getLayoutParams().height = (int) ((int) dialog.getContext().getResources().getDisplayMetrics().heightPixels * 0.2);
        }

        db.makeTextUnderlined(cancel);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(dialog.getContext(), R.layout.custom_list_view_dialog, choice);
        list.setAdapter(arrayAdapter);

        dialog.show();

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.doRequest(choice, orgEmailId);

                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast,
                        (ViewGroup) findViewById(R.id.toast));

                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.TOP, 0, 30);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();

                dialog.cancel();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }
}