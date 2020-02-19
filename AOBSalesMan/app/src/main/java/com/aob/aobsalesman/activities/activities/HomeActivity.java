package com.aob.aobsalesman.activities.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import com.aob.aobsalesman.BuildConfig;
import com.aob.aobsalesman.activities.Fragements.HintFragement;
import com.aob.aobsalesman.activities.MyAnalytics;
import com.aob.aobsalesman.activities.utility.ShareData;
import com.aob.aobsalesman.activities.utility.Tools;
import com.google.android.material.tabs.TabLayout;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.aob.aobsalesman.R;
import com.aob.aobsalesman.activities.Fragements.FragmentAllProjects;
import com.aob.aobsalesman.activities.Fragements.FragmentAppliedProjects;
import com.aob.aobsalesman.activities.utility.InternetConnection;
import com.aob.aobsalesman.activities.utility.MySingleton;
import com.aob.aobsalesman.activities.utility.ShareprefreancesUtility;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.aob.aobsalesman.activities.model.Company_Data;
import com.google.firebase.analytics.FirebaseAnalytics;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import static com.aob.aobsalesman.activities.utility.Myapp.getContext;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener  {

    private List<Company_Data> values=new ArrayList<>();
    private TabLayout tabLayout;
    private ViewPager viewPager;
    long mLastClickTime = 0;

    public List<Company_Data> allprojectlist1;
    public List<Company_Data> allprojectlist2;

    String response1 = "";
    String response2 = "";

    private ProgressDialog progressDialog;

    @SuppressLint("HardwareIds")
    String android_id = "";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        getInit();

        try {
           Bundle bundle = new Bundle();
           bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "4");
           bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "AOB Salesman");
           bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "text");
           MyAnalytics.getInstance(this).getFirebaseAnalytics().logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        }catch (Exception e){
            e.printStackTrace();
        }

        Tools.setSystemBarColor(this);

        allprojectlist1 = new ArrayList<Company_Data>();
        allprojectlist2 = new ArrayList<Company_Data>();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = findViewById(R.id.viewpager);
        progressDialog=new ProgressDialog(this);

        /*setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);*/

        findViewById(R.id.mylead).setOnClickListener(this);
        findViewById(R.id.mysales).setOnClickListener(this);
        findViewById(R.id.myearning).setOnClickListener(this);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        /*View headerView = navigationView.inflateHeaderView(R.layout.nav_header_home);
        ((TextView)headerView.findViewById(R.id.name)).setText(ShareprefreancesUtility.getInstance().getString("name",""));
        ((TextView)headerView.findViewById(R.id.email_id)).setText(ShareprefreancesUtility.getInstance().getString("email",""));
        ((TextView)findViewById(R.id.version)).setText("Version: "+BuildConfig.VERSION_NAME);*/
        navigationView.setNavigationItemSelectedListener(this);

        try {
            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    if (viewPager.getCurrentItem() > 0) {
                        findViewById(R.id.filter).setVisibility(View.GONE);
                        findViewById(R.id.filter1).setVisibility(View.VISIBLE);
                        findViewById(R.id.notification_icon).setVisibility(View.GONE);
                        findViewById(R.id.notification_icon1).setVisibility(View.VISIBLE);
                        if (ShareprefreancesUtility.getInstance().getString("notification_data").equalsIgnoreCase("0")){
                            findViewById(R.id.notification_count1).setVisibility(View.GONE);
                            findViewById(R.id.notification_count).setVisibility(View.GONE);
                        }else {
                            findViewById(R.id.notification_count1).setVisibility(View.VISIBLE);
                            findViewById(R.id.notification_count).setVisibility(View.GONE);
                            ((TextView) findViewById(R.id.notification_count1)).setText(ShareprefreancesUtility.getInstance().getString("notification_data"));
                        }
                        findViewById(R.id.sort).setVisibility(View.GONE);
                        findViewById(R.id.sort1).setVisibility(View.VISIBLE);
                    } else {
                        findViewById(R.id.filter).setVisibility(View.VISIBLE);
                        findViewById(R.id.filter1).setVisibility(View.GONE);
                        findViewById(R.id.notification_icon).setVisibility(View.VISIBLE);
                        findViewById(R.id.notification_icon1).setVisibility(View.GONE);
                        if (ShareprefreancesUtility.getInstance().getString("notification_data").equalsIgnoreCase("0")){
                            findViewById(R.id.notification_count1).setVisibility(View.GONE);
                            findViewById(R.id.notification_count).setVisibility(View.GONE);
                        }else {
                            findViewById(R.id.notification_count).setVisibility(View.VISIBLE);
                            findViewById(R.id.notification_count1).setVisibility(View.GONE);
                            ((TextView) findViewById(R.id.notification_count)).setText(ShareprefreancesUtility.getInstance().getString("notification_data"));
                        }
                        findViewById(R.id.sort).setVisibility(View.VISIBLE);
                        findViewById(R.id.sort1).setVisibility(View.GONE);
                    }
                }
                @Override
                public void onPageSelected(int position) {
                }
                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
        }catch (Exception e){}
        View navHeader = navigationView.getHeaderView(0);
        TextView name_main = navHeader.findViewById(R.id.name_main);
        TextView email_main = navHeader.findViewById(R.id.email_main);
        TextView version = navHeader.findViewById(R.id.version);

        name_main.setText(ShareprefreancesUtility.getInstance().getString("name", ""));
        email_main.setText(ShareprefreancesUtility.getInstance().getString("email", ""));
        version.setText("Version: "+ BuildConfig.VERSION_NAME);
        try {
            android_id = Settings.Secure.getString(getContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        } catch (Exception e) {}
        try {
            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }catch (Exception e){}

        if (InternetConnection.checkInternetConnectivity()) {
            progressDialog=new ProgressDialog(this);
            getProject();
        } else {
            final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(HomeActivity.this);
            alertDialog.setMessage("Retry with Internet connection");
            alertDialog.setCancelable(false);
            alertDialog.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    finish();
                }
            });
            alertDialog.setPositiveButton(
                    "Retry",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            myUpdateOperation();
                            dialog.cancel();
                        }
                    });
            alertDialog.show();
        }
    }
    @Override
    public void onBackPressed() {
        try{
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (viewPager.getCurrentItem() != 0) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
            } else {

                showExitDialog();
            }
        }
    }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getInit() {
        SharedPreferences preferences = getSharedPreferences("my_preferences", MODE_PRIVATE);
        if (!preferences.getBoolean("onboarding_complete", false)) {
            // animation = AnimationUtils.loadAnimation(this, R.anim.animation_tweening);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            // fragmentTransaction.setCustomAnimations(R.anim.animation_tweening1,R.anim.animation_tweening);
            fragmentTransaction.add(R.id.mainFrame, new HintFragement(),"hint_frag");
            fragmentTransaction.commit();
       }
    }

    private void showExitDialog() {
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
    private void showLogOutDialog() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Do you want to Log out?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (InternetConnection.checkInternetConnectivity()){
                    logouRequest();
                }
                else {
                    final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(HomeActivity.this);
                    alertDialog.setMessage("Retry with Internet connection");
                    alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alertDialog.show();
                }
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }
    private void logouRequest(){
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                getResources().getString(R.string.hostName)+"/app/sales/v1_0/firebase_notification_salesman_logout.php?phone_id="+android_id+"&email="+ShareprefreancesUtility.getInstance().getString("email"),
                new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String success=jsonObject.getString("status");


                            if (success.equalsIgnoreCase("0")) {
                                ShareprefreancesUtility.getInstance().saveString("userlogin","");
                                Intent intent=new Intent(HomeActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                        Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                            else {
                                final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(HomeActivity.this);
                                alertDialog.setMessage("Please try again");
                                alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                alertDialog.show();
                            }
                        } catch (JSONException e) {

                            final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(HomeActivity.this);
                            alertDialog.setMessage("Retry with Internet connection");
                            alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            alertDialog.show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(HomeActivity.this);
                alertDialog.setTitle("Oops! Something went wrong.");
                alertDialog.setMessage("This page didn't load correctly.\nPlease try again.");
                alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.show();
            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

     @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.logout) {

            showLogOutDialog();
        }
        else if(id == R.id.profile)
        {
            if ((ShareprefreancesUtility.getInstance().getString("register_status")).equalsIgnoreCase("complete")) {
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
            }
            else
            {
                startActivity(new Intent(HomeActivity.this, RegistrationActivity.class));
            }
        }
        else if(id == R.id.nav_share)
        {
            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
            ShareData.shareItem(this,R.drawable.app_share_image,
                    "\n\nMake a choice and live your dreams with verified sales opportunities. Bring flexibility and freedom while you earn some extra cash. "
                            +"\nDownload the app and get Rs.500 as your Registration Bonus."
                            +"\n\nTo install the AOB Salesman App click the link below \n"
                    + "https://play.google.com/store/apps/details?id=" + appPackageName
            +"\n\nReferral Code: "+ShareprefreancesUtility.getInstance().getString("mobile"));
        }
        else if(id == R.id.nav_watsapp)
        {
            whatsapp(this, "+919873034671");
        }
        else if(id == R.id.nav_rate)
        {
            rate();
        }
        else if(id == R.id.nav_call)
        {
           makeCall();
        }
        else if(id == R.id.feedback)
        {
            rate();
        }
        else if(id == R.id.faq)
        {
           startActivity(new Intent(HomeActivity.this,FAQActivity.class));
        }

        else if(id == R.id.privacy_polocy)
        {
            Intent viewIntent =
                    new Intent("android.intent.action.VIEW",
                            Uri.parse("https://aobindia.com/privacy-policy/"));
            startActivity(viewIntent);
        }

        else if(id == R.id.about_us)
        {
            Intent viewIntent =
                    new Intent("android.intent.action.VIEW",
                            Uri.parse("https://aobindia.com/about-us/"));
            startActivity(viewIntent);
        }
        else if(id == R.id.nav_mail)
        {
           mail();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void rate() {
        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }
    private void makeCall() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        } else {
            Intent intent = new Intent("android.intent.action.CALL");
            intent.setData(Uri.parse("tel:+919873034671"));
            startActivity(intent);
        }
    }
        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            } else {
                Intent intent = new Intent("android.intent.action.CALL");
                intent.setData(Uri.parse("tel:+919873034671"));
                startActivity(intent);
            }
        }
    private void mail() {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("message/rfc822");
        intent.putExtra("android.intent.extra.EMAIL", new String[]{"support@aobsalesman.com"});
        intent.putExtra("android.intent.extra.SUBJECT", "Enquiry for AOB Salesman App");
        intent.setPackage("com.google.android.gm");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    private void whatsapp(Activity activity, String s) {

        try {
            Intent sendIntent = new Intent("android.intent.action.MAIN");
            sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
            sendIntent.setType("text/plain");
            sendIntent.putExtra("jid", "919873034671" + "@s.whatsapp.net");
            sendIntent.setPackage("com.whatsapp");
            sendIntent.setAction(Intent.ACTION_SEND);
            startActivity(sendIntent);
        } catch (Exception e) {

        }
    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentAllProjects(), "All Projects");
        adapter.addFragment(new FragmentAppliedProjects(), "My Projects");

       // getSupportFragmentManager().beginTransaction().replace(R.id.applied_project_fragment, adapter.getItem(1),"AppliedProjects").commit();

        viewPager.setAdapter(adapter);
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
            Bundle bundle = new Bundle();
            if(position == 0) {
               // Bundle bundle = new Bundle();
                bundle.putString("response1", response1);
                f.setArguments(bundle);
            }else {

               // Bundle bundle = new Bundle();
                bundle.putString("response2", response2);
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
    public void  getProject(){
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                getResources().getString(R.string.hostName)+"/app/sales/v1_0/project_list_my_all.php?",
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {

                        Log.e("aobsales","reesponse "+ response);

                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String success=jsonObject.getString("status");

                            if (success.equals("0")){

                                ShareprefreancesUtility.getInstance().saveString("kyc_status", jsonObject.getString("kyc_status"));
                                ShareprefreancesUtility.getInstance().saveString("register_status", jsonObject.getString("register_status"));
                                ShareprefreancesUtility.getInstance().saveString("notification_data", jsonObject.getString("notification_data"));

                                JSONArray jsonArrayall = jsonObject.getJSONArray("all_project");
                                if(jsonArrayall.length() >0) {
                                    response1 = jsonArrayall.toString();
                                }

                                JSONArray jsonArraymy = jsonObject.getJSONArray("my_project");
                                if(jsonArraymy.length() >0) {
                                    response2 = jsonArraymy.toString();
                                }

                                Log.e("aobsales","test "+response1);

                                setupViewPager(viewPager);

                                tabLayout = findViewById(R.id.tabs);
                                tabLayout.setupWithViewPager(viewPager);
                                setupTabIcons();

                                JSONArray register = jsonObject.getJSONArray("register_data");
                                {
                                    if(register.length() > 0)
                                    {
                                        for (int i = 0; i < register.length(); i++) {
                                            JSONObject jsonObject1 = register.getJSONObject(i);
                                            ShareprefreancesUtility.getInstance().saveString("name", jsonObject1.getString("name"));
                                            ShareprefreancesUtility.getInstance().saveString("password", jsonObject1.getString("password"));
                                            ShareprefreancesUtility.getInstance().saveString("email", jsonObject1.getString("email"));
                                            ShareprefreancesUtility.getInstance().saveString("age", jsonObject1.getString("age"));
                                            ShareprefreancesUtility.getInstance().saveString("gender", jsonObject1.getString("gender"));
                                            ShareprefreancesUtility.getInstance().saveString("vehicle", jsonObject1.getString("vehicle"));
                                            ShareprefreancesUtility.getInstance().saveString("qualification", jsonObject1.getString("qualification"));
                                            ShareprefreancesUtility.getInstance().saveString("city", jsonObject1.getString("city"));
                                            ShareprefreancesUtility.getInstance().saveString("highest_qualification", jsonObject1.getString("highest_qualification"));
                                            ShareprefreancesUtility.getInstance().saveString("experience_type", jsonObject1.getString("experience_type"));
                                            ShareprefreancesUtility.getInstance().saveString("year_of_experience", jsonObject1.getString("year_of_experience"));
                                            ShareprefreancesUtility.getInstance().saveString("industry_of_interest", jsonObject1.getString("industry_of_interest"));

                                            ShareprefreancesUtility.getInstance().saveString("company_name_1", jsonObject1.getString("company_name_1"));
                                            ShareprefreancesUtility.getInstance().saveString("duration_of_experience_1", jsonObject1.getString("duration_of_experience_1"));
                                            ShareprefreancesUtility.getInstance().saveString("what_sold_1", jsonObject1.getString("what_sold_1"));

                                            ShareprefreancesUtility.getInstance().saveString("company_name_2", jsonObject1.getString("company_name_2"));
                                            ShareprefreancesUtility.getInstance().saveString("duration_of_experience_2", jsonObject1.getString("duration_of_experience_2"));
                                            ShareprefreancesUtility.getInstance().saveString("what_sold_2", jsonObject1.getString("what_sold_2"));

                                            ShareprefreancesUtility.getInstance().saveString("company_name_3", jsonObject1.getString("company_name_3"));
                                            ShareprefreancesUtility.getInstance().saveString("duration_of_experience_3", jsonObject1.getString("duration_of_experience_3"));
                                            ShareprefreancesUtility.getInstance().saveString("what_sold_3", jsonObject1.getString("what_sold_3"));

                                        }
                                    }
                                }

                                JSONArray kyc_data = jsonObject.getJSONArray("kyc_data");
                                {
                                    if(kyc_data.length() > 0)
                                    {
                                        for (int i = 0; i < kyc_data.length(); i++) {
                                            JSONObject jsonObject2 = kyc_data.getJSONObject(i);

                                            ShareprefreancesUtility.getInstance().saveString("kyc_status", jsonObject2.getString("kyc_status"));
                                            ShareprefreancesUtility.getInstance().saveString("pan_number", jsonObject2.getString("pan_number"));
                                            ShareprefreancesUtility.getInstance().saveString("aadhaar_number", jsonObject2.getString("aadhaar_number"));
                                            ShareprefreancesUtility.getInstance().saveString("beneficiary_name", jsonObject2.getString("beneficiary_name"));
                                            ShareprefreancesUtility.getInstance().saveString("bank_account_number", jsonObject2.getString("bank_account_number"));
                                            ShareprefreancesUtility.getInstance().saveString("bank_name", jsonObject2.getString("bank_name"));
                                            ShareprefreancesUtility.getInstance().saveString("branch", jsonObject2.getString("branch"));
                                            ShareprefreancesUtility.getInstance().saveString("ifsc_code", jsonObject2.getString("ifsc_code"));
                                        }

                                    }
                                }

                            }
                            else if (success.equals("1")){

                                setupViewPager(viewPager);

                                tabLayout = findViewById(R.id.tabs);
                                tabLayout.setupWithViewPager(viewPager);
                                setupTabIcons();
                            }

                        } catch (JSONException e) {
                            final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(HomeActivity.this);
                            alertDialog.setMessage("Retry with Internet connection");
                            alertDialog.setCancelable(false);
                            alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    finish();
                                }
                            });
                            alertDialog.setPositiveButton(
                                    "Retry",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            myUpdateOperation();
                                            dialog.cancel();
                                        }
                                    });
                            alertDialog.show();
                        }

                        progressDialog.dismiss();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(HomeActivity.this);
                alertDialog.setTitle("Oops! Something went wrong.");
                alertDialog.setMessage("This page didn't load correctly.\nPlease try again.");
                alertDialog.setCancelable(false);
                alertDialog.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        finish();
                    }
                });
                alertDialog.setPositiveButton(
                        "Retry",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                myUpdateOperation();
                                dialog.cancel();
                            }
                        });
                alertDialog.show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", ShareprefreancesUtility.getInstance().getString("email", ""));
                params.put("name", ShareprefreancesUtility.getInstance().getString("name", ""));
                params.put("password", ShareprefreancesUtility.getInstance().getString("password", ""));
                params.put("android_id", android_id);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(HomeActivity.this).addToRequestQueue(stringRequest);

    }

    private void myUpdateOperation() {
            getProject();
        }
        @Override
        public void onClick(View v) {
        switch (v.getId()){
            case R.id.mylead:

                if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                startActivity(new Intent(HomeActivity.this,LeadActivity.class));
                break;
            case R.id.mysales:
                if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                startActivity(new Intent(HomeActivity.this,MySaleActivity.class));
                break;
            case R.id.myearning:
                if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                startActivity(new Intent(HomeActivity.this,TransactionActivity.class));
                break;
        }
    }

    private void setupTabIcons() {

        try {
            tabLayout = findViewById(R.id.tabs);
            tabLayout.setVisibility(View.VISIBLE);
            tabLayout.setupWithViewPager(viewPager);
            tabLayout.getTabAt(0).setIcon(R.drawable.allprojects);
            tabLayout.getTabAt(1).setIcon(R.drawable.myprojects);
            tabLayout.setSelectedTabIndicatorHeight(0);
            // set icon color pre-selected
            tabLayout.getTabAt(0).getIcon().setColorFilter(getResources().getColor(R.color.colorlabel), PorterDuff.Mode.SRC_IN);
            tabLayout.getTabAt(1).getIcon().setColorFilter(getResources().getColor(R.color.colorlabel), PorterDuff.Mode.SRC_IN);

            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    tab.getIcon().setColorFilter(getResources().getColor(R.color.colorlabel), PorterDuff.Mode.SRC_IN);
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                    tab.getIcon().setColorFilter(getResources().getColor(R.color.colorlabel), PorterDuff.Mode.SRC_IN);
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }catch (Exception e){}

    }

}
