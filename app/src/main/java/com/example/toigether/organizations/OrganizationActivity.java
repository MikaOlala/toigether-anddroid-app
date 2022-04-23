package com.example.toigether.organizations;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
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
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toigether.R;
import com.example.toigether.adapters.CustomPager;
import com.example.toigether.adapters.TLGenerationAdapter;
import com.example.toigether.generation.CityGenerationFragment;
import com.example.toigether.items.Organization;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class OrganizationActivity extends AppCompatActivity {

    private ArrayList<Organization> organizations;
    private ArrayList<String> choice;
    private ImageView pic;
    private TextView name, content;
    private Button meeting;
    private Dialog dialog;
    private SharedPreferences prefs;
    private TLGenerationAdapter tlGenerationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization);

        prefs = this.getSharedPreferences("shared preferences", Context.MODE_PRIVATE);

        organizations = new ArrayList<>();
        organizations.add(new Organization(1L, "BN Event organization", "BN organization - adorable organization which u would like bla bla", R.drawable.organization_bm));
        organizations.add(new Organization(2L, "Gravum Event Masters", "We are adorable organization too, u would like us either a-la-la", R.drawable.gravum));
        organizations.add(new Organization(3L, "Title Event Organizators", "We are organization with no name but we still believe that u will choose us", R.drawable.org));
        organizations.add(new Organization(4L, "Title Event Organizators", "We are organization with no name but we still believe that u will choose us", R.drawable.organization_wedding));
        String value = getIntent().getExtras().getString("id");

        pic = findViewById(R.id.orgPic);
        name = findViewById(R.id.orgName);
        content = findViewById(R.id.orgContent);
        meeting = findViewById(R.id.makeMeeting);

        TabLayout tabLayout = findViewById(R.id.tabLayoutOrganization);
        ViewPager viewPager = findViewById(R.id.pagerOrganization);
//        CustomPager viewPager = new CustomPager(OrganizationActivity.this);
//        viewPager.findViewById(R.id.pagerOrganization);
        tabLayout.setupWithViewPager(viewPager);

        tlGenerationAdapter = new TLGenerationAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        tlGenerationAdapter.addFragment(new OrgProgramFragment(), "Программы");
        tlGenerationAdapter.addFragment(new OrgPortfolioFragment(), "Портфолио");
        tlGenerationAdapter.addFragment(new OrgReviewFragment(), "Отзыв");
        tlGenerationAdapter.addFragment(new OrgTeamFragment(), "Команда");

        viewPager.setAdapter(tlGenerationAdapter);

        setOrganization(Long.valueOf(value));

        meeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                OrgProgramFragment fragment = (OrgProgramFragment)fm.findFragmentById(R.id.orgProgramFragment);

                if (fragment != null) {
                    choice = fragment.getChoice();
                }
                Log.e("NULL OR WHAT", String.valueOf(fragment==null));
                openDialog();
            }
        });
    }

    private void setOrganization(Long id) {
        Organization organization = new Organization();
        for (Organization org : organizations) {
            if(org.getId().equals(id)) {
                organization = org;
                break;
            }
        }
        pic.setImageResource(organization.getImage());
        name.setText(organization.getName());
        content.setText(organization.getDescription());
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

        SpannableString content = new SpannableString(cancel.getText().toString());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        cancel.setText(content);

        Log.e("Array choice", String.valueOf(choice));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(dialog.getContext(), R.layout.custom_list_view_dialog, choice);
        list.setAdapter(arrayAdapter);

        dialog.show();

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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