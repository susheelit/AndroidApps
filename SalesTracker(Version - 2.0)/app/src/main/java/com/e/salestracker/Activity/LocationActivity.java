package com.e.salestracker.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.e.salestracker.Adapter.LocationAdapter;
import com.e.salestracker.Modal.Social;
import com.e.salestracker.R;
import com.e.salestracker.Utility.InternetConnection;
import com.e.salestracker.Utility.MySingleton;
import com.e.salestracker.Utility.ShareprefreancesUtility;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LocationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LocationAdapter mAdapter;
    private ItemTouchHelper mItemTouchHelper;
    private ProgressDialog progressDialog;
    List<Social> items = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_team_leader);
     // parent_view = findViewById(android.R.id.content);
        progressDialog=new ProgressDialog(this);
        initToolbar();
        initComponent();
    }
    private void initToolbar() {
        ImageView back_image = findViewById(R.id.back_image);
        TextView main_name = findViewById(R.id.main_name);
        main_name.setText("Location");
        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initComponent() {
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        if(InternetConnection.checkInternetConnectivity()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    getResources().getString(R.string.host_name)+"/sales_tracker/team_leader_emp_location.php?team_leader_email=" + ShareprefreancesUtility.getInstance().getString("email_id"),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                if (jsonObject.getString("status").equalsIgnoreCase("ok")){
                                    JSONArray jsonArray=jsonObject.getJSONArray("Data");
                                    if (items!=null)
                                        items.clear();
                                    for (int i=0;i<jsonArray.length();i++){
                                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                                           Social social=new Social();
                                                           social.name=jsonObject1.getString("location");
                                                          items.add(social);
                                    }
                                    mAdapter = new LocationAdapter(LocationActivity.this, items);
                                    recyclerView.setAdapter(mAdapter);

                                    mAdapter.setOnItemClickListener(new LocationAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, Social obj, int position) {
                                            Intent intent=new Intent(LocationActivity.this,PersonListActivty.class);
                                            intent.putExtra("Location",obj.name);
                                            startActivity(intent);
                                        }
                                    });
                                }
                                else {
                                    Toast.makeText(LocationActivity.this,jsonObject.getString("status") , Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(LocationActivity.this);
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
            MySingleton.getInstance(LocationActivity.this).addToRequestQueue(stringRequest);
        }
        else {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(LocationActivity.this);
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
    }
    private void myUpdateOperation() {
              initComponent();
    }
}
