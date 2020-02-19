package com.e.salestracker.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.e.salestracker.Adapter.TabAdapter;
import com.e.salestracker.BuildConfig;
import com.e.salestracker.R;
import com.e.salestracker.Utility.ShareprefreancesUtility;
import com.e.salestracker.Utility.Tools;
import com.google.android.material.tabs.TabLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        findViewById(R.id.navigation_button).setOnClickListener(this);
        findViewById(R.id.logout).setOnClickListener(this);
        setValue();
        setTabs();
    }

    private void setValue() {
        profile = findViewById(R.id.profile);
        TextView name = findViewById(R.id.name);
        TextView agent_name = findViewById(R.id.agent_name);
        agent_name.setText(ShareprefreancesUtility.getInstance().getString("name"));
        ((TextView)findViewById(R.id.version1)).setText(" V "+ BuildConfig.VERSION_NAME);
        TextView MobileNo = findViewById(R.id.mobile_no);
        TextView Email = findViewById(R.id.email);
        Tools.displayImageRound(this, profile, ShareprefreancesUtility.getInstance().getString("profile"));
        name.setText(ShareprefreancesUtility.getInstance().getString("name"));
        MobileNo.setText(ShareprefreancesUtility.getInstance().getString("mobile"));
        Email.setText(ShareprefreancesUtility.getInstance().getString("email_id"));
        ((TextView)findViewById(R.id.version)).setText("Version: "+ BuildConfig.VERSION_NAME);
    }

    // add tabs here
    private void setTabs(){
        TabAdapter tabsPagerAdapter = new TabAdapter(this, getSupportFragmentManager());

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(tabsPagerAdapter);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        tabs.getTabAt(0).setIcon(R.drawable.ic_project_location);
        tabs.getTabAt(1).setIcon(R.drawable.ic_projects);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.navigation_button:
                DrawerLayout navDrawer = findViewById(R.id.drawer_layout);
                // If the navigation drawer is not open then open it, if its already open then close it.
                if (!navDrawer.isDrawerOpen(GravityCompat.START))
                    navDrawer.openDrawer(Gravity.START);
                else navDrawer.closeDrawer(Gravity.END);
                break;

            case R.id.logout:
                ShareprefreancesUtility.getInstance().saveString("userlogin", "");
                startActivity(new Intent(AdminActivity.this, LoginActivity.class));
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setMessage("Do you want to Exit?");
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alertDialog.show();
        }

    }
}
