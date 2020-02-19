package com.aob.aobsalesman.activities.activities;

import android.Manifest;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aob.aobsalesman.R;
import com.aob.aobsalesman.activities.Fragements.ProjectDescriptionReviewFragment;
import com.aob.aobsalesman.activities.Fragements.AppliedProjectDescriptionDetailFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class AppliedDescriptionActivity extends AppCompatActivity {

    String project_id = "";
    String project_name = "";

    private TabLayout tabLayout_desc;
    private ViewPager viewPager_desc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applied_description);
        initObj();
        initviews();
    }

    private void initObj() {
        Intent intent = getIntent();
        project_id = intent.getExtras().getString("project_id");
        project_name = intent.getExtras().getString("project_name");
        ((ImageView)findViewById(R.id.call)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(AppliedDescriptionActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AppliedDescriptionActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                } else {
                    Intent intent = new Intent("android.intent.action.CALL");
                    intent.setData(Uri.parse("tel:+91"+getIntent().getExtras().getString("project_manager")));
                    startActivity(intent);
                }
            }
        });
    }
    private void initviews()
    {

        initObj();
        ImageView back_image = findViewById(R.id.back_image);
        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        TextView main_name = findViewById(R.id.main_name);
        main_name.setText(project_name);

        viewPager_desc = findViewById(R.id.viewpager_desc);
        setupViewPager(viewPager_desc);

        tabLayout_desc = findViewById(R.id.tabs_desc);
        tabLayout_desc.setVisibility(View.VISIBLE);
        tabLayout_desc.setSelectedTabIndicatorHeight(0);
        tabLayout_desc = findViewById(R.id.tabs_desc);
        tabLayout_desc.setupWithViewPager(viewPager_desc);

    /*

        ((Button)findViewById(R.id.register_sale)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime5 < 1000) {
                    return;
                }
                mLastClickTime5 = SystemClock.elapsedRealtime();

                Intent myIntent = new Intent(AppliedDescriptionActivity.this, ActivitySalesLead.class);
                startActivity(myIntent);
            }
        });

        ((Button)findViewById(R.id.register_lead)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SystemClock.elapsedRealtime() - mLastClickTime6 < 1000) {
                    return;
                }
                mLastClickTime6 = SystemClock.elapsedRealtime();

                Intent myIntent = new Intent(AppliedDescriptionActivity.this, ActivityRegisterLead.class);
                startActivity(myIntent);
            }
        });*/
    }




    private void setupViewPager(ViewPager viewPager) {


        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AppliedProjectDescriptionDetailFragment(), "DETAILS");
        adapter.addFragment(new ProjectDescriptionReviewFragment(), "REVIEWS");

        // getSupportFragmentManager().beginTransaction().replace(R.id.applied_project_fragment, adapter.getItem(1),"AppliedProjects").commit();

        viewPager.setAdapter(adapter);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    class ViewPagerAdapter extends FragmentPagerAdapter implements  ViewPager.OnPageChangeListener {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();


        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);

        }

        @Override
        public Fragment getItem(int position) {



            Fragment f = mFragmentList.get(position);
            if(position == 0) {
                Bundle bundle = new Bundle();
                bundle.putString("project_id",project_id );
                f.setArguments(bundle);
            }else {

                Bundle bundle = new Bundle();
                bundle.putString("project_id",project_id);
                f.setArguments(bundle);

            }
            return f;
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            // TODO Auto-generated method stub

            Log.e("aobsales","onPageScrollStateChanged");

        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // TODO Auto-generated method stub
            Log.e("aobsales","onPageScrolled");
        }

        @Override
        public void onPageSelected(int position)
        {
            Log.e("aobsales","onPageSelected");
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
