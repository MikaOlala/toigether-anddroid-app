package com.example.toigether.organizations;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toigether.R;
import com.example.toigether.adapters.TLGenerationAdapter;
import com.example.toigether.items.Organization;
import com.google.android.material.tabs.TabLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class OrganizationActivity extends AppCompatActivity {

    private ArrayList<Organization> organizations;
    private ImageView pic;
    private TextView name, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization);

        organizations = new ArrayList<>();
        organizations.add(new Organization(1L, "BN Event organization", "BN organization - adorable organization which u would like bla bla", R.drawable.organization_bm));
        organizations.add(new Organization(2L, "Gravum Event Masters", "We are adorable organization too, u would like us either a-la-la", R.drawable.gravum));
        organizations.add(new Organization(3L, "Title Event Organizators", "We are organization with no name but we still believe that u will choose us", R.drawable.org));
        organizations.add(new Organization(4L, "Title Event Organizators", "We are organization with no name but we still believe that u will choose us", R.drawable.organization_wedding));
        String value = getIntent().getExtras().getString("id");

        pic = findViewById(R.id.orgPic);
        name = findViewById(R.id.orgName);
        content = findViewById(R.id.orgContent);
        TabLayout tabLayout = findViewById(R.id.tabLayoutOrganization);
        ViewPager viewPager = findViewById(R.id.pagerOrganization);
        tabLayout.setupWithViewPager(viewPager);

        TLGenerationAdapter tlGenerationAdapter = new TLGenerationAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        tlGenerationAdapter.addFragment(new OrgProgramFragment(), "Программы");
        tlGenerationAdapter.addFragment(new OrgPortfolioFragment(), "Портфолио");
        tlGenerationAdapter.addFragment(new OrgReviewFragment(), "Отзыв");
        tlGenerationAdapter.addFragment(new OrgTeamFragment(), "Команда");

        viewPager.setAdapter(tlGenerationAdapter);

        setOrganization(Long.valueOf(value));
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
}