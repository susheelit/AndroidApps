package com.e.salestracker.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.e.salestracker.Adapter.AdminLocationAdapter;
import com.e.salestracker.BuildConfig;
import com.e.salestracker.Modal.PositionModal;
import com.e.salestracker.R;
import com.e.salestracker.Utility.Config;
import com.e.salestracker.Utility.InternetConnection;
import com.e.salestracker.Utility.MySingleton;
import com.e.salestracker.Utility.ShareprefreancesUtility;
import com.e.salestracker.Utility.Tools;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class LocationMainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_no_data_lead;
    private RecyclerView recyclerView;
    private AdminLocationAdapter mAdapter;
    private ItemTouchHelper mItemTouchHelper;
    private ImageView profile;
    private ProgressDialog progressDialog;
    List<PositionModal> items = new ArrayList<>();
    SwipeRefreshLayout swiperefresh_myleads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_main);
        initToolbar();
        initComponent();
        setValue();
        findViewById(R.id.navigation_button).setOnClickListener(this);
        findViewById(R.id.logout).setOnClickListener(this);
        swiperefresh_myleads.setRefreshing(true);
        swiperefresh_myleads.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myUpdateOperation();
            }
        });
    }

    private void initToolbar() {
        tv_no_data_lead = findViewById(R.id.tv_no_data_lead);
        swiperefresh_myleads = findViewById(R.id.swiperefresh_myleads);
        progressDialog=new ProgressDialog(this);
    }

    private void myUpdateOperation() {
        initComponent();
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
        //Log.e("Spoton","Imageurl="+ShareprefreancesUtility.getInstance().getString("profile"));
        name.setText(ShareprefreancesUtility.getInstance().getString("name"));
        MobileNo.setText(ShareprefreancesUtility.getInstance().getString("mobile"));
        Email.setText(ShareprefreancesUtility.getInstance().getString("email_id"));
        ((TextView)findViewById(R.id.version)).setText("Version: "+ BuildConfig.VERSION_NAME);
    }

    private void initComponent() {

        recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this, 3);
        gridLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(gridLayoutManager);

        String locUrl="";

        if (ShareprefreancesUtility.getInstance().getString("role").compareToIgnoreCase("client") == 0) {

                if(Config.getProjectName().compareToIgnoreCase("PM Cona")==0) {

                    locUrl = getResources().getString(R.string.host_name)+"/sales_tracker/project_city_list_for_testing_cona.php?project_name=pm+cona&agent_type=dedicated";
            }else{
                    locUrl = getResources().getString(R.string.host_name)+"/sales_tracker/project_city_list_for_testing_cona.php?project_name=none&agent_type=shared";
                }

        }else{
                locUrl = getResources().getString(R.string.host_name)+"/sales_tracker/project_city_list.php" ;

            }

       // locUrl = getResources().getString(R.string.host_name)+"/sales_tracker/project_city_list.php" ;

        Log.e("locUrl ", ""+locUrl);

        if(InternetConnection.checkInternetConnectivity()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                   locUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            swiperefresh_myleads.setRefreshing(false);
                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                if (jsonObject.getString("status").equalsIgnoreCase("ok")){
                                    JSONArray jsonArray=jsonObject.getJSONArray("Data");
                                    if (items!=null)
                                        items.clear();
                                    for (int i=0;i<jsonArray.length();i++){
                                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                        PositionModal social=new PositionModal(jsonObject1.getString("location_name"));
                                        items.add(social);
                                    }
                                    mAdapter = new AdminLocationAdapter(LocationMainActivity.this, items);
                                    recyclerView.setAdapter(mAdapter);
                                    mAdapter.setOnItemClickListener(new AdminLocationAdapter.OnItemClickListener() {
                                     @Override
                                     public void onItemClick(View view, PositionModal obj, int position) {

                                        /* String role = ShareprefreancesUtility.getInstance().getString("role");
                                         if(role.equalsIgnoreCase("Client")){

                                         }else if(role.equalsIgnoreCase("Admin")){

                                         }else{

                                         }*/


                                         Intent intent=new Intent(LocationMainActivity.this,PersonListActivty.class);
                                         intent.putExtra("Location",obj.getName());
                                         startActivity(intent);
                                     }
                                 });
                                    if(!items.isEmpty()) {

                                        recyclerView.setVisibility(View.VISIBLE);
                                        tv_no_data_lead.setVisibility(View.INVISIBLE);

                                    }else {
                                        recyclerView.setVisibility(View.INVISIBLE);
                                        tv_no_data_lead.setVisibility(View.VISIBLE);

                                    }
                                }
                                else {
                                    Toast.makeText(LocationMainActivity.this,jsonObject.getString("status") , Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    swiperefresh_myleads.setRefreshing(false);
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(LocationMainActivity.this);
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
            });

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    100000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MySingleton.getInstance(LocationMainActivity.this).addToRequestQueue(stringRequest);
        }
        else {
            swiperefresh_myleads.setRefreshing(false);
            AlertDialog.Builder builder1 = new AlertDialog.Builder(LocationMainActivity.this);
            builder1.setMessage("Retry with Internet connection");
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

      /*  ItemTouchHelper.Callback callback = new SwipeItemTouchHelper(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);*/
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
                startActivity(new Intent(LocationMainActivity.this, LoginActivity.class));
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
