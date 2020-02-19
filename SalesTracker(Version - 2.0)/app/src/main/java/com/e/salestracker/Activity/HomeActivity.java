package com.e.salestracker.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import androidx.annotation.NonNull;
import com.e.salestracker.Utility.Config;
import com.e.salestracker.Utility.ShareData;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.e.salestracker.Adapter.MainAdapter;
import com.e.salestracker.BuildConfig;
import com.e.salestracker.Modal.DSRDetail;
import com.e.salestracker.Utility.InternetConnection;
import com.e.salestracker.Utility.MySingleton;
import com.e.salestracker.Modal.People;
import com.e.salestracker.R;
import com.e.salestracker.Utility.ShareprefreancesUtility;
import com.e.salestracker.Utility.Tools;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.google.android.libraries.places.api.Places;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.e.salestracker.Utility.Myapp.context;
import static com.e.salestracker.Utility.Myapp.getContext;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private MainAdapter mAdapter;
    @SuppressLint("HardwareIds") String android_id;
    private View parent_view;
    private ImageButton imageButton;
    private ImageView profile;
    private ProgressDialog progressDialog;
    private SwipeRefreshLayout mySwipeRefreshLayout;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private androidx.appcompat.widget.AppCompatEditText name;
    private int count = 0;
    private double lat = 0;
    private double longi = 0;
    private String imageString = "";
    private String ClientName = "";
    private String projectName = "";
    private String Description = "";
    private String type = "";
    private String pmConaClientName="", pmConaCategoryName="";
    private LocationManager lm;
    private boolean checkGPS = false;
    private boolean canGetLocation = false;
    private boolean CanGetLocationNew = false;
    private Location loc;
    private String TAG="spoton";
    //new code
    private FusedLocationProviderClient mGoogleApiClient;
    private SettingsClient mSettingsClient;
    private Location mLocation;
    private LocationManager mLocationManager;
    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;
    private String mLastUpdateTime;
    // location updates interval - 10sec
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    // fastest updates interval - 5 sec
    // location updates will be received if another app is requesting the locations
    // than your app can handle
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 5000;
    private static final int REQUEST_CHECK_SETTINGS = 100;
    private LocationSettingsRequest mLocationSettingsRequest;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private long UPDATE_INTERVAL = 2 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */
    int AUTOCOMPLETE_REQUEST_CODE_OFFICE = 1;
    int AUTOCOMPLETE_REQUEST_CODE_CLIENT = 2;
    int AUTOCOMPLETE_REQUEST_CODE_ONDUTY = 3;
    int AUTOCOMPLETE_REQUEST_CODE_CHECKOUT =4;
    int AUTOCOMPLETE_REQUEST_CODE_PM_CONA =5;
    private List<People> values = new ArrayList<>();
    private List<String> client_name = new ArrayList<>();
    ArrayList<String> project = new ArrayList<>();
    ArrayList<String> locations = new ArrayList<>();
    private DSRDetail dsrDetail;
    private Button ProjectName;
    Dialog dialog, name_dialog, office_description_dialog, selection_dialog, pmConafeature_dialog, pmConaNameDiolog;
    private ItemTouchHelper mItemTouchHelper;

    private Bitmap imageBitmap;
    //Request code
    int REQUEST_IMAGE_CAPTURE_OFFICE = 200;
    int REQUEST_IMAGE_CAPTURE_ONDUTY = 100;
    int REQUEST_IMAGE_CAPTURE_CLIENT = 300;
    int REQUEST_IMAGE_CAPTURE_PM_CONA = 400;

    private People obj;
    String[] cities;
    int position=0;

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        android_id = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

       // checkProjectName();
        initView();
        // setValue();
        findViewById(R.id.navigation).setOnClickListener(this);
        findViewById(R.id.checkIn).setOnClickListener(this);
        findViewById(R.id.logout1).setOnClickListener(this);

        mGoogleApiClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // location is received
                mLocation = locationResult.getLastLocation();
                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
                updateLocationUI();
            }
        };
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();

        startLocationButtonClick();
        try {
            FirebaseInstanceId.getInstance().getInstanceId()
            .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                @Override
                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                    if (!task.isSuccessful()) {
                        Log.w("Spoton", "getInstanceId failed", task.getException());
                        return;
                    }
                    // Get new Instance ID token
                    String token = task.getResult().getToken();
                    ShareprefreancesUtility.getInstance().saveString("token", token);
                    // Log and toast
                    Log.d("Tag123", token);
                    // Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
        }
        //////////

        NavigationView navigationView = findViewById(R.id.nav_view);
        LinearLayout relative_layout = findViewById(R.id.relative_layout);
        View navHeader = findViewById(R.id.nev_header);
        TextView name_main = navHeader.findViewById(R.id.name_main);
        TextView email_main =navHeader. findViewById(R.id.email_main);
        TextView version = navHeader.findViewById(R.id.version);
        TextView referral_count = findViewById(R.id.referral_count);
        referral_count.setText(ShareprefreancesUtility.getInstance().getString("referral_count"));

        com.balysv.materialripple.MaterialRippleLayout lyt_myteam, lyt_salesman_download,lyt_share_salesmanapp, lyt_upcoming_meeting;

        LinearLayout ll_myteam, ll_salesman_download, ll_share_salesmanapp, ll_upcoming_meeting;

        lyt_myteam= findViewById(R.id.lyt_myteam);
        lyt_salesman_download= findViewById(R.id.lyt_salesman_download);
        lyt_share_salesmanapp= findViewById(R.id.lyt_share_salesmanapp);
        lyt_upcoming_meeting = findViewById(R.id.lyt_upcoming_meeting);

        ll_myteam = findViewById(R.id.ll_myteam);
        ll_salesman_download = findViewById(R.id.ll_salesman_download);
        ll_share_salesmanapp = findViewById(R.id.ll_share_salesmanapp);
        ll_upcoming_meeting = findViewById(R.id.ll_upcoming_meeting);

        ll_myteam.setOnClickListener(this);
        ll_salesman_download.setOnClickListener(this);
        ll_share_salesmanapp.setOnClickListener(this);
        ll_upcoming_meeting.setOnClickListener(this);

        // hide , show in future
        lyt_upcoming_meeting.setVisibility(View.GONE);

     // Hide drawer menu if project is PM Cona
        if(Config.getProjectName().trim().compareToIgnoreCase("PM Cona")==0){

            lyt_myteam.setVisibility(View.GONE);
            lyt_salesman_download.setVisibility(View.GONE);
            lyt_share_salesmanapp.setVisibility(View.GONE);
        }else {
            addLocationForTeamLeader();
            relative_layout.setVisibility(View.VISIBLE);
        }

        if (ShareprefreancesUtility.getInstance().getString("user").equalsIgnoreCase("Team Leader")
                || ShareprefreancesUtility.getInstance().getString("role").equalsIgnoreCase("Supervisor")) {

           // show my team in all condition when user is team leader.
            lyt_myteam.setVisibility(View.VISIBLE);
            relative_layout.setVisibility(View.VISIBLE);
            addLocationForTeamLeader();
        } else{
            lyt_myteam.setVisibility(View.GONE);
            relative_layout.setVisibility(View.GONE);
        }

        Tools.displayImageRound(this,((ImageView)navHeader.findViewById(R.id.imageView)),ShareprefreancesUtility.getInstance().getString("profile"));
        name_main.setText(ShareprefreancesUtility.getInstance().getString("name", ""));
        email_main.setText(ShareprefreancesUtility.getInstance().getString("email_id", ""));
        version.setText("Version: " + BuildConfig.VERSION_NAME);
        updateCounter(navigationView);
        navigationView.setNavigationItemSelectedListener(this);
      //  checkProjectName();
    }

    private void updateCounter(NavigationView nav) {
        Menu m = nav.getMenu();
        m.findItem(R.id.nav_all_inbox).setTitle("      25");
    }

    private void addLocationForTeamLeader(){
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        if (formattedDate.equalsIgnoreCase(ShareprefreancesUtility.getInstance().getString("date"))) {
            if (!ShareprefreancesUtility.getInstance().getString("TeamLeaderLocation").equalsIgnoreCase("")) {
                ((TextView) findViewById(R.id.location)).setText(ShareprefreancesUtility.getInstance().getString("TeamLeaderLocation"));
            } else {
                ((TextView) findViewById(R.id.location)).setText("Select Location");
            }
        }
        else {
            ((TextView) findViewById(R.id.location)).setText("Select Location");
            final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(HomeActivity.this);
            alertDialog.setCancelable(true);
            alertDialog.setMessage("Before checkIn you select your location.");
            alertDialog.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.show();
        }

        findViewById(R.id.relative_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setCancelable(true);
                if (!ShareprefreancesUtility.getInstance().getString("TeamLeaderLocationPosition").equalsIgnoreCase(""))
                {
                    builder.setSingleChoiceItems(cities, Integer.parseInt(ShareprefreancesUtility.getInstance().getString("TeamLeaderLocationPosition")), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ((TextView)findViewById(R.id.location)).setText(cities[which]);
                        ((TextView)findViewById(R.id.location)).setTextColor(getResources().getColor(R.color.colorBlack));
                        Date c = Calendar.getInstance().getTime();
                        System.out.println("Current time => " + c);
                        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                        String formattedDate = df.format(c);
                        ShareprefreancesUtility.getInstance().saveString("date",formattedDate);
                        ShareprefreancesUtility.getInstance().saveString("TeamLeaderLocation",((TextView)findViewById(R.id.location)).getText().toString());
                        ShareprefreancesUtility.getInstance().saveString("TeamLeaderLocationPosition",Integer.toString(which));
                        position=which;
                        if (values != null) {
                            values.clear();
                        }
                        initComponent();
                    }
                    });
                }
                else {
                    builder.setSingleChoiceItems(cities, position, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            ((TextView) findViewById(R.id.location)).setText(cities[which]);
                            ((TextView) findViewById(R.id.location)).setTextColor(getResources().getColor(R.color.colorBlack));
                            Date c = Calendar.getInstance().getTime();
                            System.out.println("Current time => " + c);
                            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                            String formattedDate = df.format(c);
                            ShareprefreancesUtility.getInstance().saveString("date",formattedDate);
                            ShareprefreancesUtility.getInstance().saveString("TeamLeaderLocation", ((TextView) findViewById(R.id.location)).getText().toString());
                            ShareprefreancesUtility.getInstance().saveString("TeamLeaderLocationPosition", Integer.toString(which));
                            position = which;
                            if (values != null) {
                                values.clear();
                            }
                            initComponent();
                        }
                    });
                }
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
    }

    private void getProject() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                getResources().getString(R.string.host_name)+"/sales_tracker/project_name_list.php?agent_id=" + ShareprefreancesUtility.getInstance().getString("userId"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equalsIgnoreCase("ok")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("Data");
                                if (project != null) {
                                    project.clear();
                                }
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    project.add(jsonObject1.getString("project_name"));
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    },
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(HomeActivity.this).addToRequestQueue(stringRequest);
    }

    private void getLocationForTeamleader() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                getResources().getString(R.string.host_name)+"/sales_tracker/project_city_list.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equalsIgnoreCase("ok")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("Data");
                                cities=new String[jsonArray.length()];
                                if (cities != null) {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        cities[i]="";
                                    }
                                }
                                for (int i = 0; i <jsonArray.length(); i++) {
                                    Log.e("langth",Integer.toString(jsonArray.length()));
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    cities[i]=jsonObject1.getString("location_name");
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(HomeActivity.this).addToRequestQueue(stringRequest);
    }

    private void initView() {
        progressDialog = new ProgressDialog(this);
        mySwipeRefreshLayout = findViewById(R.id.swiperefresh);
        parent_view = findViewById(android.R.id.content);
        TextView agent_name = findViewById(R.id.agent_name);
        agent_name.setText(ShareprefreancesUtility.getInstance().getString("name"));
        ((TextView)findViewById(R.id.version1)).setText(" V "+ BuildConfig.VERSION_NAME);
        try {
            Places.initialize(getApplicationContext(), getResources().getString(R.string.place_api_key));
        }catch (Exception e){ }
    }

    private void initComponent() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        setAdapter();
    }

    private void setAdapter() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, getResources().getString(R.string.host_name)+"/sales_tracker/testing_folder/detail_data_test.php?agent_id="
                + ShareprefreancesUtility.getInstance().getString("userId"), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    Log.d("Tag",response);
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("Tag","lpll "+jsonObject.toString());
                    ShareprefreancesUtility.getInstance().saveString("domain",jsonObject.getString("domain"));
                    ShareprefreancesUtility.getInstance().saveString("mobile",jsonObject.getString("mobile_no"));
                    ShareprefreancesUtility.getInstance().saveString("referral_count",jsonObject.getString("referral_count"));
                    TextView referral_count =(TextView)findViewById(R.id.referral_count);
                    referral_count.setText(""+ShareprefreancesUtility.getInstance().getString("referral_count"));

                   // ((TextView)findViewById(R.id.referral_count1)).setText(ShareprefreancesUtility.getInstance().getString("referral_count"));
                    String success = jsonObject.getString("status");
                    if (success.equalsIgnoreCase("ok")) {
                        if(Config.getProjectName().equalsIgnoreCase("PM Cona")){
                        if(!ShareprefreancesUtility.getInstance().getString("role").equalsIgnoreCase("Supervisor")) {
                            if (jsonObject.getString("activation_status").equalsIgnoreCase("locked")) {
                                final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(HomeActivity.this);
                                alertDialog.setCancelable(false);
                                alertDialog.setMessage("Hello smarty, you screwed up. You were too busy to fill your DSR. It’s time to call your Boss. Only on his request can the system unlock.");
                                alertDialog.setNegativeButton("Call now", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(Intent.ACTION_DIAL);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                                alertDialog.setPositiveButton("Request Unlock", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        sendRequestForUnlockApp();
                                    }
                                });
                                alertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                                    @Override
                                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                                        if (keyCode == KeyEvent.KEYCODE_BACK) {
                                            finish();
                                        }
                                        return true;
                                    }
                                });
                                alertDialog.show();
                            }
                        }
                        }else {

                            if (jsonObject.getString("activation_status").equalsIgnoreCase("locked")) {
                                final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(HomeActivity.this);
                                alertDialog.setCancelable(false);
                                alertDialog.setMessage("Hello smarty, you screwed up. You were too busy to fill your DSR. It’s time to call your Boss. Only on his request can the system unlock.");
                                alertDialog.setNegativeButton("Call now", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(Intent.ACTION_DIAL);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                                alertDialog.setPositiveButton("Request Unlock", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        sendRequestForUnlockApp();
                                    }
                                });
                                alertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                                    @Override
                                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                                        if (keyCode == KeyEvent.KEYCODE_BACK) {
                                            finish();
                                        }
                                        return true;
                                    }
                                });
                                alertDialog.show();
                            }
                        }

                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            People people = new People(jsonObject1.getString("agent_id"),
                                    jsonObject1.getString("ccid"),
                                    jsonObject1.getString("checkin_time"),
                                    jsonObject1.getString("checkin_date"),
                                    jsonObject1.getString("checkout_time"),
                                    jsonObject1.getString("checkout_date"),
                                    jsonObject1.getString("visited"),
                                    jsonObject1.getString("client_name"),
                                    jsonObject1.getString("agent_cc_status"),
                                    jsonObject1.getString("image"),
                                    jsonObject1.getString("dsr_status"),
                                    jsonObject1.getString("project_name_form"),
                                    jsonObject1.getString("department"));
                            values.add(people);
                        }
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            if (jsonObject1.getString("agent_cc_status").equalsIgnoreCase("pending")) {
                                findViewById(R.id.checkIn).setEnabled(false);
                                findViewById(R.id.checkIn).setBackgroundResource(R.drawable.btn_rounded_primary_disable);
                                ((Button) findViewById(R.id.checkIn)).setTextColor(getResources().getColor(R.color.grey_40));
                                break;

                            } else if (jsonObject1.getString("dsr_status").equalsIgnoreCase("pending")) {
                                findViewById(R.id.checkIn).setEnabled(false);
                                findViewById(R.id.checkIn).setBackgroundResource(R.drawable.btn_rounded_primary_disable);
                                ((Button) findViewById(R.id.checkIn)).setTextColor(getResources().getColor(R.color.grey_40));

                                /*if (!jsonObject1.getString("checkin_date").equalsIgnoreCase(jsonObject1.getString("current_date"))) {
                                    findViewById(R.id.checkIn).setEnabled(false);
                                    findViewById(R.id.checkIn).setBackgroundResource(R.drawable.btn_rounded_primary_disable);
                                    ((Button) findViewById(R.id.checkIn)).setTextColor(getResources().getColor(R.color.grey_40));

                                 }*/
                                break;
                            }else {
                                if (ShareprefreancesUtility.getInstance().getString("user").equalsIgnoreCase("Team Leader")
                                        || ShareprefreancesUtility.getInstance().getString("role").equalsIgnoreCase("Supervisor")){
                                    if (((TextView) findViewById(R.id.location)).getText().toString().equalsIgnoreCase("Select Location")){
                                        findViewById(R.id.checkIn).setEnabled(false);
                                        findViewById(R.id.checkIn).setBackgroundResource(R.drawable.btn_rounded_primary_disable);
                                        ((Button) findViewById(R.id.checkIn)).setTextColor(getResources().getColor(R.color.grey_40));
                                    }
                                    else {
                                        findViewById(R.id.checkIn).setEnabled(true);
                                        findViewById(R.id.checkIn).setBackgroundResource(R.drawable.btn_rounded_primary);
                                        ((Button) findViewById(R.id.checkIn)).setTextColor(getResources().getColor(R.color.colorWhite));
                                    }
                                }else {
                                    findViewById(R.id.checkIn).setEnabled(true);
                                    findViewById(R.id.checkIn).setBackgroundResource(R.drawable.btn_rounded_primary);
                                    ((Button) findViewById(R.id.checkIn)).setTextColor(getResources().getColor(R.color.colorWhite));
                                }
                            }
                        }
                        if (ShareprefreancesUtility.getInstance().getString("user").equalsIgnoreCase("Team Leader")
                                || ShareprefreancesUtility.getInstance().getString("role").equalsIgnoreCase("Supervisor")){
                            ShareprefreancesUtility.getInstance().saveString("UserLocation",((TextView) findViewById(R.id.location)).getText().toString());
                        }else {
                            ShareprefreancesUtility.getInstance().saveString("UserLocation",jsonObject.getString("employee_location"));
                        }

                        if (jsonObject.getString("agent_status").equalsIgnoreCase("inactive")){
                            ShareprefreancesUtility.getInstance().saveString("userlogin", "");
                            Intent intent=new Intent(HomeActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                    Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }

                        /*if (ShareprefreancesUtility.getInstance().getString("user").equalsIgnoreCase("Team Leader")
                        || ShareprefreancesUtility.getInstance().getString("role").equalsIgnoreCase("Supervisor")){
                            if (((TextView) findViewById(R.id.location)).getText().toString().equalsIgnoreCase("Select Location")){
                                findViewById(R.id.checkIn).setEnabled(false);
                                findViewById(R.id.checkIn).setBackgroundResource(R.drawable.btn_rounded_primary_disable);
                                ((Button) findViewById(R.id.checkIn)).setTextColor(getResources().getColor(R.color.grey_40));
                            }
                            else {
                                findViewById(R.id.checkIn).setEnabled(true);
                                findViewById(R.id.checkIn).setBackgroundResource(R.drawable.btn_rounded_primary);
                                ((Button) findViewById(R.id.checkIn)).setTextColor(getResources().getColor(R.color.colorWhite));
                            }
                        }else {
                            findViewById(R.id.checkIn).setEnabled(true);
                            findViewById(R.id.checkIn).setBackgroundResource(R.drawable.btn_rounded_primary);
                            ((Button) findViewById(R.id.checkIn)).setTextColor(getResources().getColor(R.color.colorWhite));
                        }*/

                        mySwipeRefreshLayout.setRefreshing(false);
                        //set data and list adapter
                        mAdapter = new MainAdapter(HomeActivity.this, values);
                        recyclerView.setAdapter(mAdapter);
                        mAdapter.setOnItemClickListener(new MainAdapter.OnItemClickListener() {

                            @Override
                            public void onItemClick(View view, final People obj, int position, RecyclerView.ViewHolder holder) {
                                HomeActivity.this.obj=obj;
                                if (obj.getAgent_cc_status().equalsIgnoreCase("pending")) {
                                    checkLocationNew();
                                    if (canGetLocation) {
                                        if (obj.getVisited() == "office") {
                                            checkOut(obj, obj.getVisited());
                                            view.setClickable(true);
                                        } else {
                                            checkOut(obj, obj.getVisited());
                                            view.setClickable(true);
                                        }
                                    } else {
                                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeActivity.this);
                                        alertDialog.setMessage("Sorry can't get your location.\n Please try again.");
                                        alertDialog.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                           dialog.dismiss();
                                            }
                                        });

                                        alertDialog.show();
                                        myUpdateOperation();
                                        view.setClickable(true);
                                    }
                                } else {
                                    if (obj.getVisited().equalsIgnoreCase("office")) {
                                        getDescription(obj, holder);
                                    }
                                    else if (obj.getVisited().equalsIgnoreCase("OnDuty")) {
                                       getDescriptionForOnDuty(obj, holder);
                                      //  Toast.makeText(HomeActivity.this, "Sorry! it's pending", Toast.LENGTH_SHORT).show();

                                    }else if (obj.getVisited().equalsIgnoreCase("client")) {

                                        if (InternetConnection.checkInternetConnectivity()) {

                                            Intent intent = new Intent(HomeActivity.this, DSRDetailActivityNew.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putSerializable("MyClass", obj);
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                            finish();

                                        } else {
                                            AlertDialog.Builder builder1 = new AlertDialog.Builder(HomeActivity.this);
                                            builder1.setMessage("Please check your internet connection..");
                                            builder1.setCancelable(true);
                                            builder1.setPositiveButton(
                                                    "Retry",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            myUpdateOperation();
                                                            dialog.cancel();
                                                        }
                                                    });
                                            builder1.setNegativeButton(
                                                    "Cancel",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            dialog.cancel();
                                                        }
                                                    });

                                            AlertDialog alert11 = builder1.create();
                                            alert11.show();
                                        }
                                    }
                                }
                            }
                        });
                    } else {

                        mySwipeRefreshLayout.setRefreshing(false);
                        if (ShareprefreancesUtility.getInstance().getString("user").equalsIgnoreCase("Team Leader")
                                || ShareprefreancesUtility.getInstance().getString("role").equalsIgnoreCase("Supervisor")){
                            ShareprefreancesUtility.getInstance().saveString("UserLocation",((TextView) findViewById(R.id.location)).getText().toString());
                        }else {
                            ShareprefreancesUtility.getInstance().saveString("UserLocation",jsonObject.getString("employee_location"));
                        }
                        if (jsonObject.getString("agent_status").equalsIgnoreCase("inactive")){
                            ShareprefreancesUtility.getInstance().saveString("userlogin", "");
                            Intent intent=new Intent(HomeActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                    Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                        if (ShareprefreancesUtility.getInstance().getString("user").equalsIgnoreCase("Team Leader")
                                || ShareprefreancesUtility.getInstance().getString("role").equalsIgnoreCase("Supervisor")){
                                if (((TextView) findViewById(R.id.location)).getText().toString().equalsIgnoreCase("Select Location")){
                                    findViewById(R.id.checkIn).setEnabled(false);
                                    findViewById(R.id.checkIn).setBackgroundResource(R.drawable.btn_rounded_primary_disable);
                                    ((Button) findViewById(R.id.checkIn)).setTextColor(getResources().getColor(R.color.grey_40));
                                }
                                else {
                                    findViewById(R.id.checkIn).setEnabled(true);
                                    findViewById(R.id.checkIn).setBackgroundResource(R.drawable.btn_rounded_primary);
                                    ((Button) findViewById(R.id.checkIn)).setTextColor(getResources().getColor(R.color.colorWhite));
                                }
                            }else {
                                findViewById(R.id.checkIn).setEnabled(true);
                                findViewById(R.id.checkIn).setBackgroundResource(R.drawable.btn_rounded_primary);
                                ((Button) findViewById(R.id.checkIn)).setTextColor(getResources().getColor(R.color.colorWhite));
                            }
                        }
                } catch (JSONException e) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(HomeActivity.this);
                    builder1.setMessage(e.toString());
                    builder1.setCancelable(false);
                    builder1.setPositiveButton(
                            "Retry",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    myUpdateOperation();
                                    dialog.cancel();
                                }
                            });

                    builder1.setNegativeButton(
                            "Close",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    finish();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                    mySwipeRefreshLayout.setRefreshing(false);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(HomeActivity.this);
                builder1.setMessage("Check your internet connection");
                builder1.setCancelable(false);
                builder1.setPositiveButton(
                        "Retry",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                myUpdateOperation();
                                dialog.cancel();
                            }
                        });
                builder1.setNegativeButton(
                        "Close",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
                mySwipeRefreshLayout.setRefreshing(false);
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
    private void getDescriptionForOnDuty(final People obj, RecyclerView.ViewHolder holder) {
        Log.e("salestracker", "url is " + getResources().getString(R.string.host_name)+"/sales_tracker/new_checkin_checkout_insert.php?status=dsr&agent_id=" + ShareprefreancesUtility.getInstance().getString("userId") + "&ccid=" + obj.getCcid() + "&visited=office");
        //String ss = "http://aobindia.in/sales_tracker/new_checkin_checkout_insert.php?status=dsr&visit_type=office&agent_id="+ShareprefreancesUtility.getInstance().getString("userId")+"&ccid="+obj.getCcid()+"&visited=office";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, getResources().getString(R.string.host_name)+"/sales_tracker/new_checkin_checkout_insert.php?status=dsr&visit_type=OnDuty&agent_id=" + ShareprefreancesUtility.getInstance().getString("userId") + "&ccid=" + obj.getCcid() + "&visited=OnDuty", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("salestracker", "1stresponse is " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("status");
                    if (success.equalsIgnoreCase("ok")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            Log.e("salestracker", "2ndresponse is " + jsonObject1);
                            dsrDetail = new DSRDetail(jsonObject1.getString("Description"));
                            Log.e("salestracker", "3rdresponse is " + jsonObject1.getString("Description"));
                        }
                        office_description_dialog = new Dialog(HomeActivity.this);
                        office_description_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        office_description_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        office_description_dialog.setCanceledOnTouchOutside(false);
                        office_description_dialog.setContentView(R.layout.office_description_dialog_layout);
                        name = office_description_dialog.findViewById(R.id.description);
                        name.setText(dsrDetail.getDescription());
                        office_description_dialog.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (name.getText().toString().isEmpty()) {
                                    name.setError("please enter description");
                                } else {
                                    Description = name.getText().toString();
                                    sendDSRDetailforOnDuty(obj, Description, office_description_dialog);
                                }
                            }


                        });
                        office_description_dialog.show();
                        myUpdateOperation();
                    }
                } catch (JSONException e) {
                    Log.d("Exception:", e.toString());
                    myUpdateOperation();
                    e.printStackTrace();
                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(HomeActivity.this);
                builder1.setMessage("Please check your internet connection..");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Retry",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                myUpdateOperation();
                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
    private void sendDSRDetailforOnDuty(People obj, String description, final Dialog office_description_dialog) {
        if (InternetConnection.checkInternetConnectivity()) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            StringRequest stringRequest = null;
            try {
                stringRequest = new StringRequest(Request.Method.GET, getResources().getString(R.string.host_name)+"/sales_tracker/new_update_dscr_dis.php?agent_id=" + ShareprefreancesUtility.getInstance().getString("userId")
                        + "&ccid=" + obj.getCcid()
                        + "&Description=" + URLEncoder.encode(Description, "UTF-8")
                        + "&dsr_status=complete&visited_type=OnDuty", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equalsIgnoreCase(" update successful Dsr ")) {
                                Toast.makeText(HomeActivity.this, "Description Uploaded", Toast.LENGTH_SHORT).show();

                                office_description_dialog.dismiss();
                                myUpdateOperation();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                    }
                });
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    100000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MySingleton.getInstance(HomeActivity.this).addToRequestQueue(stringRequest);
        } else {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(HomeActivity.this);
            builder1.setMessage("Please check your internet connection.");
            builder1.setCancelable(true);
            builder1.setPositiveButton(
                    "Retry",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();

                        }
                    });


            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
    }
    private void checkOut( final People obj, final String visited) {
         progressDialog.setMessage("Please wait...");
         progressDialog.setCancelable(false);
         progressDialog.show();
         if(InternetConnection.checkInternetConnectivity()) {
             StringRequest stringRequest = new StringRequest(Request.Method.POST,
                     getResources().getString(R.string.host_name)+"/sales_tracker/new_checkin_checkout_insert.php",
                     new Response.Listener<String>() {
                         @Override
                         public void onResponse(String response) {
                             Log.e("checkout", " response"+response);
                          progressDialog.dismiss();
                             mAdapter.isClickable = true;
                                               /* findViewById(R.id.checkIn).setEnabled(true);
                                                findViewById(R.id.checkIn).setBackgroundResource(R.drawable.btn_rounded_primary);
                                                ((Button) findViewById(R.id.checkIn)).setTextColor(getResources().getColor(R.color.colorWhite));*/
                             //myUpdateOperation();

                             Intent intent = new Intent(HomeActivity.this, DSRDetailActivityNew.class);
                             Bundle bundle = new Bundle();
                             bundle.putSerializable("MyClass", obj);
                             intent.putExtras(bundle);
                             startActivity(intent);
                             finish();
                         }
                     }, new Response.ErrorListener() {
                 @Override
                 public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                     AlertDialog.Builder builder1 = new AlertDialog.Builder(HomeActivity.this);
                     builder1.setMessage("Check your internet connection");
                     builder1.setCancelable(false);
                     builder1.setPositiveButton(
                             "Retry",
                             new DialogInterface.OnClickListener() {
                                 public void onClick(DialogInterface dialog, int id) {
                                     myUpdateOperation();
                                     dialog.cancel();
                                 }
                             });
                     builder1.setNegativeButton(
                             "Close",
                             new DialogInterface.OnClickListener() {
                                 public void onClick(DialogInterface dialog, int id) {
                                     finish();
                                 }
                             });

                     AlertDialog alert11 = builder1.create();
                     alert11.show();
                 }
             }) {
                 @Override
                 protected Map<String, String> getParams() throws AuthFailureError {
                     Map<String, String> params = new HashMap<String, String>();
                     params.put("image", "_");
                     params.put("checkin_lat", "_");
                     params.put("checkin_lng", "_");
                     params.put("checkout_lat", Double.toString(lat));
                     params.put("checkout_lng", Double.toString(longi));
                     params.put("ccid", obj.getCcid());
                     params.put("client_name", "_");
                     params.put("agent_id", ShareprefreancesUtility.getInstance().getString("userId"));
                     params.put("status", "checkout");
                     params.put("visited", visited);
                     return params;
                 }
             };

            /* stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                     100000,
                     DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                     DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));*/

             stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000,
                     0,
                     DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

             MySingleton.getInstance(HomeActivity.this).addToRequestQueue(stringRequest);
         }
         else {
             showDialog("Please check your internet connection.");
         }
    }

    private void sendRequestForUnlockApp() {
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getResources().getString(R.string.host_name)+"/sales_tracker/push_notification/send_unlock_request.php?", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            progressDialog.dismiss();

            Log.e("Unlock", " response "+response);
            try {
                    JSONObject jsonObject = new JSONObject(response);

                    String success = jsonObject.getString("status");

                    if (success.equals("0")){
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(HomeActivity.this);
                        builder1.setMessage("Done");
                        builder1.setCancelable(false);
                        builder1.setPositiveButton(
                                "Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        finish();
                                    }
                                });
                        builder1.setOnKeyListener(new DialogInterface.OnKeyListener() {
                            @Override
                            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                                if (keyCode == KeyEvent.KEYCODE_BACK) {
                                    finish();
                                }
                                return true;
                            }
                        });
                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    } else {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(HomeActivity.this);
                        builder1.setMessage("Please try again");
                        builder1.setCancelable(true);
                        builder1.setPositiveButton(
                                "Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                       sendRequestForUnlockApp();
                                    }
                                });
                        builder1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                           finish();
                            }
                        });
                        builder1.setOnKeyListener(new DialogInterface.OnKeyListener() {
                            @Override
                            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                                if (keyCode == KeyEvent.KEYCODE_BACK) {
                                    finish();
                                }
                                return true;
                            }
                        });
                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    }

                } catch (JSONException e) {
                    Log.d("Exception:", e.toString());
                    myUpdateOperation();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                AlertDialog.Builder builder1 = new AlertDialog.Builder(HomeActivity.this);
                builder1.setMessage("Please check internet connection..");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Retry",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                myUpdateOperation();
                                dialog.cancel();
                            }
                        });
                builder1.setNegativeButton(
                        "Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                myUpdateOperation();
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("agent_id", ShareprefreancesUtility.getInstance().getString("userId"));
                params.put("phone_id",android_id);
                params.put("email", ShareprefreancesUtility.getInstance().getString("email_id"));
                params.put("token",ShareprefreancesUtility.getInstance().getString("token"));
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void getDescription(final People obj, RecyclerView.ViewHolder holder) {
        Log.e("salestracker", "url is " + getResources().getString(R.string.host_name)+"/sales_tracker/new_checkin_checkout_insert.php?status=dsr&agent_id=" + ShareprefreancesUtility.getInstance().getString("userId") + "&ccid=" + obj.getCcid() + "&visited=office");
        //String ss = "http://aobindia.in/sales_tracker/new_checkin_checkout_insert.php?status=dsr&visit_type=office&agent_id="+ShareprefreancesUtility.getInstance().getString("userId")+"&ccid="+obj.getCcid()+"&visited=office";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, getResources().getString(R.string.host_name)+"/sales_tracker/new_checkin_checkout_insert.php?status=dsr&visit_type=office&agent_id=" + ShareprefreancesUtility.getInstance().getString("userId") + "&ccid=" + obj.getCcid() + "&visited=office", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("salestracker", "1stresponse is " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("status");

                    if (success.equalsIgnoreCase("ok")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            Log.e("salestracker", "2ndresponse is " + jsonObject1);
                            dsrDetail = new DSRDetail(jsonObject1.getString("Description"));
                            Log.e("salestracker", "3rdresponse is " + jsonObject1.getString("Description"));
                        }

                        office_description_dialog = new Dialog(HomeActivity.this);
                        office_description_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        office_description_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        office_description_dialog.setCanceledOnTouchOutside(false);
                        office_description_dialog.setContentView(R.layout.office_description_dialog_layout);
                        name = office_description_dialog.findViewById(R.id.description);
                        name.setText(dsrDetail.getDescription());
                        office_description_dialog.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (name.getText().toString().isEmpty()) {
                                    name.setError("please enter description");
                                } else {
                                    Description = name.getText().toString();
                                    sendDSRDetail(obj, Description, office_description_dialog);
                                }
                            }
                        });
                        office_description_dialog.show();
                        myUpdateOperation();

                    }
                } catch (JSONException e) {
                    Log.d("Exception:", e.toString());
                    myUpdateOperation();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(HomeActivity.this);
                builder1.setMessage("Please check your internet connection..");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Retry",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                myUpdateOperation();
                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void sendDSRDetail(People obj, String description, final Dialog office_description_dialog) {
        if (InternetConnection.checkInternetConnectivity()) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            StringRequest stringRequest = null;
            try {
                stringRequest = new StringRequest(Request.Method.GET, getResources().getString(R.string.host_name)+"/sales_tracker/new_update_dscr_dis.php?agent_id=" + ShareprefreancesUtility.getInstance().getString("userId")
                        + "&ccid=" + obj.getCcid()
                        + "&Description=" + URLEncoder.encode(Description, "UTF-8")
                        + "&dsr_status=complete&visited_type=office", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equalsIgnoreCase(" update successful Dsr ")) {
                                Toast.makeText(HomeActivity.this, "Description Uploaded", Toast.LENGTH_SHORT).show();

                                office_description_dialog.dismiss();
                                myUpdateOperation();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                    }
                });
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    100000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MySingleton.getInstance(HomeActivity.this).addToRequestQueue(stringRequest);
        } else {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(HomeActivity.this);
            builder1.setMessage("Please check your internet connection.");
            builder1.setCancelable(true);
            builder1.setPositiveButton(
                    "Retry",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();

                        }
                    });


            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
    }
    @SuppressLint("WrongConstant")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.navigation:
                DrawerLayout navDrawer = findViewById(R.id.drawer_layout1);
                // If the navigation drawer is not open then open it, if its already open then close it.
                if (!navDrawer.isDrawerOpen(GravityCompat.START))
                    navDrawer.openDrawer(Gravity.START);
                else navDrawer.closeDrawer(Gravity.END);
                break;

                /* case R.id.logout:
                ShareprefreancesUtility.getInstance().saveString("userlogin", "");
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                finish();
                break;*/

                case R.id.checkIn:
                if (InternetConnection.checkInternetConnectivity())
                    // get Project name
                    if(Config.getProjectName().compareToIgnoreCase("PM Cona")==0){
                        showDialogFor_PMCona();
                    }else {
                        showDialog();
                    }else {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(HomeActivity.this);
                    builder1.setMessage("Please check your internet connection.");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "Retry",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    myUpdateOperation();
                                }
                            });

                    builder1.setNegativeButton(
                            "Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
                break;

            /*case R.id.parent:
                startActivity(new Intent(HomeActivity.this,SalesManHistory.class));
                DrawerLayout drawer = findViewById(R.id.drawer_layout1);
                drawer.closeDrawer(GravityCompat.START);
                break;*/

            case R.id.ll_salesman_download:
                startActivity(new Intent(HomeActivity.this,SalesManHistory.class));
                DrawerLayout drawer1 = findViewById(R.id.drawer_layout1);
                drawer1.closeDrawer(GravityCompat.START);
                break;

            case R.id.ll_myteam:
                startActivity(new Intent(HomeActivity.this, LocationActivity.class));
                DrawerLayout drawer2 = findViewById(R.id.drawer_layout1);
                drawer2.closeDrawer(GravityCompat.START);
                break;


            case R.id.ll_share_salesmanapp:
                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                ShareData.shareItem(this,R.drawable.app_share_image,
                        "\n\nMake a choice and live your dreams with verified sales opportunities. Bring flexibility and freedom while you earn some extra cash. "
                                +"\n\nTo install the AOB Salesman App click the link below \n"
                                + "https://play.google.com/store/apps/details?id=com.aob.aobsalesman"
                                +"\n\nReferral Code: "+ShareprefreancesUtility.getInstance().getString("mobile"));
                break;
          /*  case R.id.parent4:
                final String appPackageName1 = getPackageName(); // getPackageName() from Context or Activity object
                ShareData.shareItem(this,R.drawable.app_share_image,
                        "\n\nMake a choice and live your dreams with verified sales opportunities. Bring flexibility and freedom while you earn some extra cash. "
                                +"\n\nTo install the AOB Salesman App click the link below \n"
                                + "https://play.google.com/store/apps/details?id=com.aob.aobsalesman"
                                +"\n\nReferral Code: "+ShareprefreancesUtility.getInstance().getString("mobile"));
                break;*/

            case R.id.ll_upcoming_meeting:
                startActivity(new Intent(HomeActivity.this, UpcomingMeetingActivity.class));
                DrawerLayout drawer3 = findViewById(R.id.drawer_layout1);
                drawer3.closeDrawer(GravityCompat.START);
                break;

            case R.id.logout1:

                ShareprefreancesUtility.getInstance().saveString("userlogin", "");
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                finish();
                break;
        }

    }

    private void showDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.diolog_layout);
        dialog.findViewById(R.id.OnDuty).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.findViewById(R.id.OnDuty).setEnabled(false);
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE_ONDUTY);
                }
            }
        });
        dialog.findViewById(R.id.office).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.findViewById(R.id.office).setEnabled(false);
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE_OFFICE);
                }
            }
        });
        dialog.findViewById(R.id.client).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialog.findViewById(R.id.office).setEnabled(false);
                selection_dialog = new Dialog(HomeActivity.this);
                selection_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                selection_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                selection_dialog.setContentView(R.layout.selection_dialog);
                radioGroup = selection_dialog.findViewById(R.id.radio);
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (((RadioButton) selection_dialog.findViewById(checkedId)).getText().toString().equalsIgnoreCase("1st Visit")) {
                            selection_dialog.dismiss();
                            name_dialog = new Dialog(HomeActivity.this);
                            name_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            name_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            name_dialog.setContentView(R.layout.name_dialog_layout);

                            name_dialog.findViewById(R.id.name).setVisibility(View.VISIBLE);
                            final TextInputEditText name = name_dialog.findViewById(R.id.name);
                             name.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

                                }

                                @Override
                                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                                    if (charSequence.toString().startsWith(" ")) {
                                        //disableButton(...)
                                        name.setError("Do not use space in the beginning.");
                                        name_dialog.findViewById(R.id.submit).setEnabled(false);
                                    } else {
                                        //enableButton(...)
                                        name_dialog. findViewById(R.id.submit).setEnabled(true);
                                    }
                                }
                                @Override
                                public void afterTextChanged(Editable editable) {

                                }
                            });

                            name_dialog.findViewById(R.id.autoCompleteTextView).setVisibility(View.GONE);
                            ProjectName = name_dialog.findViewById(R.id.ProjectName);
                            ProjectName.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showDialog();
                                }
                            });
                            name_dialog.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if (name.getText().toString().isEmpty()) {
                                        name.setError("please enter name");
                                    } else if (ProjectName.getText().toString().equalsIgnoreCase("Select Project")) {

                                        final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(HomeActivity.this);
                                        alertDialog.setMessage("Please select project");
                                        alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });
                                        alertDialog.show();
                                    } else {
                                        ClientName = name.getText().toString();
                                        type = "1st Visit";
                                        projectName = ProjectName.getText().toString();

                                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                                            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE_CLIENT);
                                        }
                                    }
                                }
                            });
                            name_dialog.show();
                        } else {
                            getName();
                        }
                    }

                    private void showDialog() {
                        if (project.size() > 0) {
                            final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(HomeActivity.this);
                            builder.setCancelable(true);
                            final ArrayAdapter<String> arrayAdapterItems = new ArrayAdapter<String>(
                                    HomeActivity.this, android.R.layout.simple_list_item_1, project);

                            builder.setSingleChoiceItems(arrayAdapterItems, 0, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ProjectName.setText(project.get(which));
                                    ProjectName.setTextColor(getResources().getColor(R.color.colorBlack));
                                    dialog.dismiss();
                                }
                            });
                            builder.show();
                        } else {
                            getProject();
                            getLocationForTeamleader();
                        }
                    }

                    private void getName() {
                        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                                getResources().getString(R.string.host_name)+"/sales_tracker/client_name_list.php?agent_id=" + ShareprefreancesUtility.getInstance().getString("userId"),
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            if (jsonObject.getString("status").equalsIgnoreCase("ok")) {
                                                JSONArray jsonArray = jsonObject.getJSONArray("Data");
                                                if (client_name != null) {
                                                    client_name.clear();
                                                }
                                                for (int i = 0; i < jsonArray.length(); i++) {
                                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                                    client_name.add(jsonObject1.getString("client_name"));
                                                }
                                                selection_dialog.dismiss();
                                                name_dialog = new Dialog(HomeActivity.this);
                                                name_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                name_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                                name_dialog.setContentView(R.layout.name_dialog_layout);
                                                name_dialog.findViewById(R.id.name).setVisibility(View.GONE);
                                                ProjectName = name_dialog.findViewById(R.id.ProjectName);
                                                ProjectName.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        showDialog();
                                                    }
                                                });
                                                name_dialog.findViewById(R.id.autoCompleteTextView).setVisibility(View.VISIBLE);

                                                ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                                        (HomeActivity.this, android.R.layout.select_dialog_item, client_name);
                                                //Getting the instance of AutoCompleteTextView
                                                final AutoCompleteTextView actv = name_dialog.findViewById(R.id.autoCompleteTextView);
                                                actv.setThreshold(1);//will start working from first character
                                                actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
                                                actv.addTextChangedListener(new TextWatcher() {
                                                    @Override
                                                    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

                                                    }

                                                    @Override
                                                    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                                                        if (charSequence.toString().startsWith(" ")) {
                                                            //disableButton(...)
                                                            actv.setError("Do not use space in the beginning");
                                                            name_dialog. findViewById(R.id.submit).setEnabled(false);
                                                        } else {
                                                            //enableButton(...)
                                                            name_dialog. findViewById(R.id.submit).setEnabled(true);
                                                        }
                                                    }
                                                    @Override
                                                    public void afterTextChanged(Editable editable) {

                                                    }
                                                });

                                                name_dialog.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {

                                                        if (actv.getText().toString().equalsIgnoreCase("")) {
                                                            actv.setError("please enter name");
                                                        } else if (ProjectName.getText().toString().equalsIgnoreCase("Select Project")) {

                                                            final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(HomeActivity.this);
                                                            alertDialog.setMessage("Please select project.");
                                                            alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    dialog.cancel();
                                                                }
                                                            });
                                                            alertDialog.show();
                                                        } else {
                                                            ClientName = actv.getText().toString();
                                                            type = "Follow Up";
                                                            projectName = ProjectName.getText().toString();

                                                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                                            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                                                                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE_CLIENT);
                                                            }
                                                        }

                                                    }
                                                });
                                                name_dialog.show();
                                            } else {
                                                selection_dialog.dismiss();
                                                name_dialog = new Dialog(HomeActivity.this);
                                                name_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                name_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                                name_dialog.setContentView(R.layout.name_dialog_layout);
                                                name_dialog.findViewById(R.id.name).setVisibility(View.VISIBLE);
                                                name_dialog.findViewById(R.id.autoCompleteTextView).setVisibility(View.GONE);
                                                ProjectName = name_dialog.findViewById(R.id.ProjectName);
                                                ProjectName.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        showDialog();
                                                    }
                                                });
                                                name_dialog.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        TextInputEditText name = name_dialog.findViewById(R.id.name);

                                                        if (name.getText().toString().isEmpty()) {
                                                            name.setError("please enter name");
                                                        } else if (ProjectName.getText().toString().equalsIgnoreCase("Select Project")) {

                                                            final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(HomeActivity.this);
                                                            alertDialog.setMessage("Please select project.");
                                                            alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    dialog.cancel();
                                                                }
                                                            });
                                                            alertDialog.show();
                                                        } else {
                                                            ClientName = name.getText().toString();
                                                            projectName = ProjectName.getText().toString();

                                                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                                            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                                                                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE_CLIENT);
                                                            }
                                                        }
                                                    }
                                                });
                                                name_dialog.show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(HomeActivity.this);
                                builder1.setMessage("Please check your internet connection.");
                                builder1.setCancelable(true);
                                builder1.setPositiveButton(
                                        "Retry",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                myUpdateOperation();

                                            }
                                        });

                                builder1.setNegativeButton(
                                        "Cancel",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });

                                AlertDialog alert11 = builder1.create();
                                alert11.show();
                            }
                        });

                        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                                100000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                        MySingleton.getInstance(HomeActivity.this).addToRequestQueue(stringRequest);
                    }
                });
                selection_dialog.show();

            }
        });
        dialog.show();
    }
    private void sendOfficeInformation(Bitmap photo) {
        if (InternetConnection.checkInternetConnectivity()) {
            this.imageString = convertBitmapToBase64String(photo);
            progressDialog.setMessage("Uploading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    getResources().getString(R.string.host_name)+"/sales_tracker/new_checkin_checkout_insert.php",
                     new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            dialog.dismiss();
                            dialog.findViewById(R.id.office).setEnabled(true);
                            findViewById(R.id.checkIn).setEnabled(false);
                            findViewById(R.id.checkIn).setBackgroundResource(R.drawable.btn_rounded_primary_disable);
                            myUpdateOperation();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    showDialog("Please check your internet connection");
                    progressDialog.dismiss();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("image", imageString);
                    params.put("checkin_lat", Double.toString(lat));
                    params.put("checkin_lng", Double.toString(longi));
                    params.put("checkout_lat", "_");
                    params.put("checkout_lng", "_");
                    params.put("visited", "office");
                    params.put("client_name", "inoffice");
                    params.put("agent_id", ShareprefreancesUtility.getInstance().getString("userId"));
                    params.put("status", "checkin");
                    params.put("pro_name_form", "_");
                    params.put("visit_type", "_");
                    params.put("user_location", ShareprefreancesUtility.getInstance().getString("UserLocation"));
                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    100000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MySingleton.getInstance(this).addToRequestQueue(stringRequest);
        } else {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

            alertDialog.setCancelable(false);
            alertDialog.setMessage("Please check your internet connection");


            alertDialog.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alertDialog.show();
        }
    }
    private void sendDutyInformation(Bitmap photo) {
        if (InternetConnection.checkInternetConnectivity()) {
            this.imageString = convertBitmapToBase64String(photo);
            progressDialog.setMessage("Uploading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    getResources().getString(R.string.host_name)+"/sales_tracker/new_checkin_checkout_insert.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            dialog.dismiss();
                            dialog.findViewById(R.id.OnDuty).setEnabled(true);
                            findViewById(R.id.checkIn).setEnabled(false);
                            findViewById(R.id.checkIn).setBackgroundResource(R.drawable.btn_rounded_primary_disable);
                            myUpdateOperation();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    showDialog("Please check your internet connection");
                    progressDialog.dismiss();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("image", imageString);
                    params.put("checkin_lat", Double.toString(lat));
                    params.put("checkin_lng", Double.toString(longi));
                    params.put("checkout_lat", "_");
                    params.put("checkout_lng", "_");
                    params.put("visited", "OnDuty");
                    params.put("client_name", "OnDuty");
                    params.put("agent_id", ShareprefreancesUtility.getInstance().getString("userId"));
                    params.put("status", "checkin");
                    params.put("pro_name_form", "_");
                    params.put("visit_type", "_");
                    params.put("user_location", ShareprefreancesUtility.getInstance().getString("UserLocation"));
                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    100000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MySingleton.getInstance(this).addToRequestQueue(stringRequest);
        } else {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

            alertDialog.setCancelable(false);
            alertDialog.setMessage("Please check your internet connection");


            alertDialog.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alertDialog.show();
        }
    }
    private void sendClientInformation(Bitmap imageBitmap) {
        if (InternetConnection.checkInternetConnectivity()) {
            this.imageString = convertBitmapToBase64String(imageBitmap);
            progressDialog.setMessage("Uploading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    getResources().getString(R.string.host_name)+"/sales_tracker/new_checkin_checkout_insert.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            //name_dialog.dismiss();
                            findViewById(R.id.checkIn).setEnabled(false);
                            findViewById(R.id.checkIn).setBackgroundResource(R.drawable.btn_rounded_primary_disable);
                            myUpdateOperation();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                   // name_dialog.dismiss();
                    showDialog("Please check your internet connection");
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("image", imageString);
                    params.put("checkin_lat", Double.toString(lat));
                    params.put("checkin_lng", Double.toString(longi));
                    params.put("checkout_lat", "_");
                    params.put("checkout_lng", "_");
                    params.put("visited", "client");
                    if (!ClientName.equals(""))
                        params.put("client_name", ClientName);
                    params.put("agent_id", ShareprefreancesUtility.getInstance().getString("userId"));
                    params.put("status", "checkin");
                    if (!projectName.equals(""))
                        params.put("pro_name_form", projectName);
                    if (!type.equals(""))
                        params.put("visit_type", type);
                    params.put("user_location", ShareprefreancesUtility.getInstance().getString("UserLocation"));
                    return params;
                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    100000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MySingleton.getInstance(this).addToRequestQueue(stringRequest);
        } else {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

            alertDialog.setCancelable(false);
            alertDialog.setMessage("Please check your internet connection");

            alertDialog.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alertDialog.show();
        }

    }
    private void myUpdateOperation() {
        mySwipeRefreshLayout.setRefreshing(true);
        if (values != null) {
            values.clear();
            initComponent();
            getProject();
            getLocationForTeamleader();
        }
    }

    private String convertBitmapToBase64String(Bitmap image) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String imageString = Base64.encodeToString(byteArray, Base64.DEFAULT);
      //  Log.d("base64String", imageString);
        return imageString;
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout1);
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
    private void showDialog(String s) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(s);
        alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }

    private boolean checkLocationNew() {
        if (!isLocationEnabled()) {
            updateLocationUI();
           // stopLocationUpdates();
            startLocationButtonClick();
            return false;
        }else {
           updateLocationUI();
            return true;
        }
    }
    private boolean isLocationEnabled() {
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
    private void updateLocationUI()  {
        if (mLocation != null) {

            Log.e("salestracker","test "+mLocation);
            lat = mLocation.getLatitude();
            longi = mLocation.getLongitude();
            canGetLocation = true;

        }else {
            canGetLocation = false;
        }
    }

    private void startLocationUpdates() {
        mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.e("salestracker", "All location settings are satisfied.");

                        //noinspection MissingPermission
                        mGoogleApiClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper());
                        updateUIFirst();

                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.e("salestracker", "Location settings are not satisfied. Attempting to upgrade " +
                                        "location settings ");
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(HomeActivity.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.e("salestracker", "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(HomeActivity.this);
                                builder1.setMessage("Something wrong in your location setting");
                                builder1.setCancelable(false);
                                builder1.setPositiveButton(
                                        "Retry",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                recreate();
                                                dialog.cancel();
                                            }
                                        });
                                builder1.setNegativeButton(
                                        "Close",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                finish();
                                            }
                                        });
                                AlertDialog alert11 = builder1.create();
                                alert11.show();
                                mySwipeRefreshLayout.setRefreshing(false);

                                Log.e("salestracker", errorMessage);
                                break;
                                // Toast.makeText(HomeActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }
    public void startLocationButtonClick(){
        // Requesting ACCESS_FINE_LOCATION using Dexter library

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        startLocationUpdates();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            // open device settings when the permission is
                            // denied permanently
                            openSettings();
                        }else {
                            startLocationButtonClick();
                        }
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })

                .withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Log.e("Location get error ", error.toString());
                        Toast.makeText(getApplicationContext(), "Error occurred! " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }).check();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.e("SALESTRACKER", "TEST reqc " + requestCode + " resc " + resultCode);

        if (requestCode == AUTOCOMPLETE_REQUEST_CODE_OFFICE) {
            if (resultCode == RESULT_OK) {
                try {
                    Place place = Autocomplete.getPlaceFromIntent(data);
                    Log.i(TAG, "Place: " + place.toString());
                    Geocoder coder = new Geocoder(this);

                    ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName(place.getName(), 1);
                    for (Address add : adresses) {
                        longi = add.getLongitude();
                        lat = add.getLatitude();
                        sendOfficeInformation(imageBitmap);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }

            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE_CLIENT) {

            if (resultCode == RESULT_OK) {
                try {
                    Place place = Autocomplete.getPlaceFromIntent(data);
                    Log.i(TAG, "Place: " + place.toString());
                    Geocoder coder = new Geocoder(this);

                    ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName(place.getName(), 1);
                    for (Address add : adresses) {
                        longi = add.getLongitude();
                        lat = add.getLatitude();
                        sendClientInformation(imageBitmap);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }

            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE_ONDUTY) {
            if (resultCode == RESULT_OK) {
                try {
                    Place place = Autocomplete.getPlaceFromIntent(data);
                    Log.i(TAG, "Place: " + place.toString());
                    Geocoder coder = new Geocoder(this);

                    ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName(place.getName(), 1);
                    for (Address add : adresses) {
                        longi = add.getLongitude();
                        lat = add.getLatitude();
                        sendDutyInformation(imageBitmap);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }

            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE_CHECKOUT) {
            if (resultCode == RESULT_OK) {
                try {
                    Place place = Autocomplete.getPlaceFromIntent(data);
                    Log.i(TAG, "Place: " + place.toString());
                    Geocoder coder = new Geocoder(this);

                    ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName(place.getName(), 1);
                    for (Address add : adresses) {
                        longi = add.getLongitude();
                        lat = add.getLatitude();
                        if (obj.getVisited() == "office") {
                            checkOut(obj, obj.getVisited());

                        } else {
                            checkOut(obj, obj.getVisited());

                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }

            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }

        if (requestCode == REQUEST_IMAGE_CAPTURE_OFFICE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            checkLocationNew();
            if (canGetLocation) {
                sendOfficeInformation(imageBitmap);
            }else {
                showLocationDialog(AUTOCOMPLETE_REQUEST_CODE_OFFICE);
                myUpdateOperation();
            }
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE_ONDUTY && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            checkLocationNew();
            if (canGetLocation) {
                sendDutyInformation(imageBitmap);
            } else {
                showLocationDialog(AUTOCOMPLETE_REQUEST_CODE_ONDUTY);
                myUpdateOperation();
            }
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE_CLIENT && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            checkLocationNew();
            if (canGetLocation) {
                sendClientInformation(imageBitmap);
            }else {
                showLocationDialog(AUTOCOMPLETE_REQUEST_CODE_CLIENT);
                myUpdateOperation();
            }
        } else if (requestCode == REQUEST_CHECK_SETTINGS && resultCode == RESULT_OK) {
            Log.e("SALESTRACKER", "User agreed to make required location settings changes.");
            // Nothing to do. startLocationupdates() gets called in onResume again.
            startLocationButtonClick();
        } else if (requestCode == REQUEST_CHECK_SETTINGS && resultCode == RESULT_CANCELED) {
            Log.e("SALESTRACKER", "User chose not to make required location settings changes.");
            startLocationButtonClick();
        }

        if (requestCode == REQUEST_IMAGE_CAPTURE_PM_CONA && resultCode == RESULT_OK) {

            //REQUEST_IMAGE_CAPTURE_PM_CONA = 400;
            //AUTOCOMPLETE_REQUEST_CODE_PM_CONA =5;
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            checkLocationNew();
            if (canGetLocation) {
                sendPM_ConaClientInformation(imageBitmap);
                //sendOfficeInformation(imageBitmap);

            } else {
                showLocationDialog(AUTOCOMPLETE_REQUEST_CODE_PM_CONA);
                myUpdateOperation();
            }
        }

        if (dialog != null)
            dialog.dismiss();

    }
    private void showLocationDialog(final int AUTOCOMPLETE_REQUEST_CODE) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Sorry can't get your location.\n Please try again.");
        alertDialog.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
           dialog.dismiss();
            }
        });
      /*  alertDialog.setPositiveButton("Select Manually", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.FULLSCREEN, fields)
                        .setTypeFilter(TypeFilter.ADDRESS)
                        .build(HomeActivity.this);
                startActivityForResult(intent,AUTOCOMPLETE_REQUEST_CODE);
                dialog.cancel();
            }
        });*/
        alertDialog.show();
    }
    private void openSettings() {
        Intent intent = new Intent();
        intent.setAction(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",
                BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }
    public void stopLocationUpdates() {
        // Removing location updates
        try {
            if (mGoogleApiClient != null) {
                mGoogleApiClient
                        .removeLocationUpdates(mLocationCallback)
                        .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                            }
                        });
            }
        }catch (Exception e){}
    }
    public void updateUIFirst() {

        Log.e("salestracker","working ");

        //stopLocationUpdates();

        mySwipeRefreshLayout.setRefreshing(true);
        initComponent();
        getProject();
        getLocationForTeamleader();
        mySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mySwipeRefreshLayout.setRefreshing(true);
                myUpdateOperation();
            }
        });

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id){

            case R.id.MYTeam:
                  startActivity(new Intent(HomeActivity.this, LocationActivity.class));
                break;
            case R.id.nav_all_inbox2:
                startActivity(new Intent(HomeActivity.this,SalesManHistory.class));
                break;


        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout1);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*  if Project name is PM_Cona */

    // hide views if project is PM cona

    // show dialog if project is PM_Cona
    private void showDialogFor_PMCona() {

        if (InternetConnection.checkInternetConnectivity()) {
            pmConafeature_dialog = new Dialog(this);
            pmConafeature_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            pmConafeature_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            pmConafeature_dialog.setContentView(R.layout.dialog_layout_cona);
            final LinearLayout llbutton = pmConafeature_dialog.findViewById(R.id.llbutton);

            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    getResources().getString(R.string.host_name) + "/sales_tracker/pmcona_category_list.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String status= jsonObject.get("status").toString();
                                Log.e("status", status);
                                if(status.compareToIgnoreCase("ok")==0){

                                    JSONArray jsonArray = jsonObject.getJSONArray("Data");
                                    for(int i=0;i<jsonArray.length();i++){

                                        JSONObject jsonObject1 = new JSONObject();
                                        jsonObject1 = jsonArray.getJSONObject(i);
                                       final Button myButton = new Button(context);
                                        myButton.setTextColor(Color.WHITE);
                                        myButton.setTextSize(22);
                                        myButton.setBackground(getResources().getDrawable(R.drawable.btn_rounded_primary));
                                        myButton.setText(""+jsonObject1.get("category").toString());

                                        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                                        llbutton.addView(myButton, lp);

                                        myButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                pmConaCategoryName = ""+myButton.getText().toString();
                                                pmConafeature_dialog.dismiss();
                                                pmConaNameDialog();
                                            }
                                        });
                                    }
                                }else {
                                    Config.toast(context, "No Record found !");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    showDialog("Please check your internet connection");
                    progressDialog.dismiss();
                }
            });

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    100000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MySingleton.getInstance(this).addToRequestQueue(stringRequest);
        } else {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

            alertDialog.setCancelable(false);
            alertDialog.setMessage("Please check your internet connection");

            alertDialog.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
        }
            pmConafeature_dialog.show();

    }

    private void pmConaNameDialog(){
       // pmConafeature_dialog
        pmConaNameDiolog = new Dialog(this);
        pmConaNameDiolog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pmConaNameDiolog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pmConaNameDiolog.setContentView(R.layout.pmcona_name_diolog);

        final AutoCompleteTextView autoCompleteTextView = pmConaNameDiolog.findViewById(R.id.autoCompleteTextView);
      //  final TextInputEditText tv_project_name = dialog.findViewById(R.id.tv_project_name);
        Button btn_submit = pmConaNameDiolog.findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pmConaClientName="";
                pmConaClientName =  autoCompleteTextView.getText().toString();
                if(pmConaClientName.isEmpty()){
                    autoCompleteTextView.setError("Please enter Client name...");
                }else {
                    //String projName =  "PM Cona";
                    pmConaNameDiolog.dismiss();
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE_PM_CONA);
                    }
                }
            }
        });
        pmConaNameDiolog.show();
    }

    private void sendPM_ConaClientInformation(Bitmap imageBitmap) {

        if (InternetConnection.checkInternetConnectivity()) {
            this.imageString = convertBitmapToBase64String(imageBitmap);
            progressDialog.setMessage("Uploading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    getResources().getString(R.string.host_name)+"/sales_tracker/new_checkin_checkout_insert.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                           // dialog.dismiss();
                           // dialog.findViewById(R.id.office).setEnabled(true);
                            findViewById(R.id.checkIn).setEnabled(false);
                            findViewById(R.id.checkIn).setBackgroundResource(R.drawable.btn_rounded_primary_disable);
                            myUpdateOperation();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    showDialog("Please check your internet connection");
                    progressDialog.dismiss();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("image", imageString);
                    params.put("checkin_lat", Double.toString(lat));
                    params.put("checkin_lng", Double.toString(longi));
                    params.put("checkout_lat", "_");
                    params.put("checkout_lng", "_");
                    params.put("visited", "client");
                    params.put("pmcona_category", ""+pmConaCategoryName);
                    params.put("client_name", ""+pmConaClientName);
                    params.put("agent_id", ShareprefreancesUtility.getInstance().getString("userId"));
                    params.put("status", "checkin");
                    params.put("pro_name_form", "PM Cona");
                    params.put("visit_type", "visit_type");
                    params.put("user_location", ShareprefreancesUtility.getInstance().getString("UserLocation"));

                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    100000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MySingleton.getInstance(this).addToRequestQueue(stringRequest);
        } else {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

            alertDialog.setCancelable(false);
            alertDialog.setMessage("Please check your internet connection");


            alertDialog.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alertDialog.show();
        }

    }
}
