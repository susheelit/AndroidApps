package com.aob.aobsalesman.activities.activities;

import android.Manifest;
import android.content.Intent;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aob.aobsalesman.R;
import com.aob.aobsalesman.activities.Fragements.ProjectDescriptionDetailFragment;
import com.aob.aobsalesman.activities.Fragements.ProjectDescriptionReviewFragment;

import java.util.ArrayList;
import java.util.List;

public class ProjectDescriptionActivity extends AppCompatActivity {


    /*Company_Data obj;
    TextView company_name_pd,location_pd, company_price_pd;
    Button apply_pd;
    Boolean applied_status;*/

    String project_id = "";
    String project_name = "";

    private TabLayout tabLayout_desc;
    private ViewPager viewPager_desc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_description);

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
        tabLayout_desc =  findViewById(R.id.tabs_desc);
        tabLayout_desc.setupWithViewPager(viewPager_desc);

        //initObj();
        //initviews();
    }

    private void initObj() {
     Intent intent = getIntent();
       project_id = intent.getExtras().getString("project_id");
       project_name = intent.getExtras().getString("project_name");

        (findViewById(R.id.call)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(ProjectDescriptionActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ProjectDescriptionActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                } else {
                    Intent intent = new Intent("android.intent.action.CALL");
                    intent.setData(Uri.parse("tel:+91"+getIntent().getExtras().getString("project_manager")));
                    startActivity(intent);
                }
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            } else {
                Intent intent = new Intent("android.intent.action.CALL");
                intent.setData(Uri.parse("tel:+91" + getIntent().getExtras().getString("project_manager")));
                startActivity(intent);
            }
        }
    }
    private void initviews() {
       /* company_name_pd = findViewById(R.id.company_name_pd);
        location_pd = findViewById(R.id.location_pd);
        company_price_pd = findViewById(R.id.company_price_pd);
        //apply_pd = findViewById(R.id.apply_pd);

        company_name_pd.setText(obj.getCompany_name());
        location_pd.setText(obj.getCompany_address());
        company_price_pd.setText(obj.getPrice());

        applied_status = obj.getApplied();
        if(applied_status)
        {
            apply_pd.setEnabled(false);
            apply_pd.setText("A P P L I E D");
        }
        else
        {
            apply_pd.setEnabled(true);
            apply_pd.setText("A P P L Y");

        }

        apply_pd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



              *//*  if((ShareprefreancesUtility.getInstance().getString("registration_status")).equalsIgnoreCase("done"))
                {

                    if ((ShareprefreancesUtility.getInstance().getString("kyc_status")).equalsIgnoreCase("done"))
                    {



                        Company_Data ap12 = obj;
                        Log.e("aobsales","data is "+obj.getCompany_address()+" pos is "+obj.getPosition());
                        ap12.setApplied(true);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("MyClass", (Serializable) obj);


              *//**//*  String tag = "android:switcher:" + R.id.viewpager + ":" + 0;

                Log.e("aobsales","tag is "+tag);

                Fragment frg = getFragmentManager().findFragmentByTag(tag);

                Log.e("aobsales","frg is "+frg.toString());

                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putSerializable("MyClass", (Serializable) obj);
                frg.setArguments(bundle);
                ft.detach(frg);
                ft.attach(frg);
                ft.commit();*//**//*




                    }
                    else
                    {

                        showKYCDialog();


                    }


                }
                else{

                    showRegistrationDialog();

                }*//*


                Intent intent=new Intent(ProjectDescriptionActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);




*//*

                Company_Data ap12 = obj;
                ap12.setApplied(true);
                Bundle bundle = new Bundle();
                bundle.putSerializable("MyClass", (Serializable) obj);


              *//**//*  String tag = "android:switcher:" + R.id.viewpager + ":" + 0;

                Log.e("aobsales","tag is "+tag);

                Fragment frg = getFragmentManager().findFragmentByTag(tag);

                Log.e("aobsales","frg is "+frg.toString());

                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putSerializable("MyClass", (Serializable) obj);
                frg.setArguments(bundle);
                ft.detach(frg);
                ft.attach(frg);
                ft.commit();*//**//*

                Intent intent=new Intent(ProjectDescriptionActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtras(bundle);
                startActivity(intent);*//*



            }
        });
*/

    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ProjectDescriptionDetailFragment(), "DETAILS");
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
