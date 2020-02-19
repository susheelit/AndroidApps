package com.e.salestracker.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.e.salestracker.Adapter.SalesmanHistoryAdapter;
import com.e.salestracker.Modal.SalesManModal;
import com.e.salestracker.R;
import com.e.salestracker.Utility.InternetConnection;
import com.e.salestracker.Utility.MySingleton;
import com.e.salestracker.Utility.ShareprefreancesUtility;
import com.e.salestracker.Utility.Tools;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SalesManHistory extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SalesmanHistoryAdapter mAdapter;
    private ItemTouchHelper mItemTouchHelper;
    private ProgressDialog progressDialog;
    ArrayList<SalesManModal> items=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_man_history);
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
        ((TextView) findViewById(R.id.ref_id)).setText(ShareprefreancesUtility.getInstance().getString("mobile"));
    }
    private void initComponent() {
       progressDialog.setMessage("Please wait...");
       progressDialog.show();
       recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
       recyclerView.setLayoutManager(new LinearLayoutManager(this));
       recyclerView.setHasFixedSize(true);

       if(InternetConnection.checkInternetConnectivity()) {
           StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    getResources().getString(R.string.host_name1)+"/app/sales/v1_0/refer_for_spoton/index.php?mobile="+ShareprefreancesUtility.getInstance().getString("mobile")+"&key="+ Tools.md5("spoton"+ShareprefreancesUtility.getInstance().getString("mobile")+"spoton"),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                Log.e("json:",jsonObject.toString());
                                if (jsonObject.getString("status").equalsIgnoreCase("0")){
                                    JSONArray jsonArray=jsonObject.getJSONArray("referral_data");
                                    if (items!=null)
                                        items.clear();
                                    for (int i=0;i<jsonArray.length();i++){
                                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                        SalesManModal social=new SalesManModal( jsonObject1.getString("email"),
                                                                                jsonObject1.getString("date_of_register"),
                                                                                jsonObject1.getString("referral_name"),
                                                                                jsonObject1.getString("referral_status"));
                                        items.add(social);
                                    }
                                    mAdapter = new SalesmanHistoryAdapter(SalesManHistory.this, items);
                                    recyclerView.setAdapter(mAdapter);
                                }
                                else {
                                    Toast.makeText(SalesManHistory.this,"no Data" , Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(SalesManHistory.this, e.toString(), Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(SalesManHistory.this);
                    builder1.setMessage("Something went wrong!");
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
            MySingleton.getInstance(SalesManHistory.this).addToRequestQueue(stringRequest);
        }
        else {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(SalesManHistory.this);
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
}
