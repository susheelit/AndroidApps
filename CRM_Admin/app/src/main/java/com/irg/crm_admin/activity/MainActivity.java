package com.irg.crm_admin.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.irg.crm_admin.R;
import com.irg.crm_admin.common.Config;
import com.irg.crm_admin.fragment.MyProductFragment;
import com.irg.crm_admin.fragment.MySalesmanFragment;
import com.irg.crm_admin.fragment.OrderHistoryFragment;
import com.irg.crm_admin.fragment.PaymentHistoryFragment;
import com.irg.crm_admin.fragment.ShopListFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // private AppBarConfiguration mAppBarConfiguration;
    private static Context mContext;
    boolean doubleBackToExitPressedOnce = false;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mContext = MainActivity.this;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle mActionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mActionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        // Bundle bundle = new Bundle();

        if (id == R.id.nav_home) {
            fragment = new OrderHistoryFragment();
        } else if (id == R.id.nav_shoplist) {
            fragment = new ShopListFragment();
        } else if (id == R.id.nav_orderhistory) {
            fragment = new OrderHistoryFragment();
        } else if (id == R.id.nav_paymenthistory) {
            fragment = new PaymentHistoryFragment();
        } else if (id == R.id.nav_myproduct) {
            fragment = new MyProductFragment();
        } else if (id == R.id.nav_mysaleaman) {
            fragment = new MySalesmanFragment();
        } else if (id == R.id.nav_logout) {
            Config.logoutUser(mContext);
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.nav_host_fragment, fragment);
            ft.commit();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Config.toastShow( "Please click BACK again to exit", mContext);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
