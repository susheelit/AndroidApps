package com.aob.aobsalesman.activities.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.aob.aobsalesman.R;
import com.aob.aobsalesman.activities.Adapter.NotificationAdapter;
import com.aob.aobsalesman.activities.model.NotificationModal;
import com.aob.aobsalesman.activities.utility.InternetConnection;
import com.aob.aobsalesman.activities.utility.MySingleton;
import com.aob.aobsalesman.activities.utility.ShareprefreancesUtility;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NotificationsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NotificationAdapter mAdapter;

    private ProgressDialog progressDialog;
    ArrayList<NotificationModal> items=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        // parent_view = findViewById(android.R.id.content);
        progressDialog=new ProgressDialog(this);
        initToolbar();
        initComponent();
    }
    private void initToolbar() {
        ImageView back_image = findViewById(R.id.back_image);
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
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        if(InternetConnection.checkInternetConnectivity()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    getResources().getString(R.string.hostName)+"/app/sales/v1_0/notified_logs.php?email="+ShareprefreancesUtility.getInstance().getString("email"),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                if (jsonObject.getString("status").equalsIgnoreCase("0")){

                                    JSONArray jsonArray=jsonObject.getJSONArray("data");
                                    if (items!=null)
                                        items.clear();
                                    for (int i=0;i<jsonArray.length();i++){
                                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                        NotificationModal social=new NotificationModal(jsonObject1.getString("header"),
                                                jsonObject1.getString("body"),
                                                jsonObject1.getString("date")+"/"+jsonObject1.getString("time"));
                                        items.add(social);
                                    }
                                    mAdapter = new NotificationAdapter(NotificationsActivity.this, items);
                                    recyclerView.setAdapter(mAdapter);
                                }
                                else {
                                    //  Toast.makeText(NotificationsActivity.this,jsonObject.getString("status") , Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(NotificationsActivity.this);
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
            MySingleton.getInstance(NotificationsActivity.this).addToRequestQueue(stringRequest);
        }
        else {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(NotificationsActivity.this);
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
    private void myUpdateOperation() {
        initComponent();
    }
    @Override
    public void onBackPressed() {
        if (ShareprefreancesUtility.getInstance().getString("notification_data").equalsIgnoreCase("0")){
            super.onBackPressed();
        }else {
            ShareprefreancesUtility.getInstance().saveString("notification_data","0");
            Intent intent = new Intent(NotificationsActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_CLEAR_TASK |
                    Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
