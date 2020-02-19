package com.aob.aobsalesman.activities.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.aob.aobsalesman.R;
import com.aob.aobsalesman.activities.model.Earning_Data;
import com.aob.aobsalesman.activities.utility.InternetConnection;
import com.aob.aobsalesman.activities.utility.MySingleton;
import com.aob.aobsalesman.activities.utility.ShareprefreancesUtility;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.aob.aobsalesman.activities.utility.Myapp.getContext;

public class TransactionActivity extends AppCompatActivity implements View.OnClickListener {

    @SuppressLint("HardwareIds")
    String android_id = "";
    RecyclerView recyclerView_trans;
    TextView tv_no_data_lead;
    LinearLayout linear;
    long mLastClickTime = 0;
    TransactionAdapter tadapter;
    private List<Earning_Data> leads = new ArrayList();
    private List<Earning_Data> items_filtered = new ArrayList();
    private List<Earning_Data> company_data=new ArrayList<>();
    SwipeRefreshLayout swiperefresh_mysales;
    EditText searchBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        ImageView back_image = findViewById(R.id.back_image);
        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        recyclerView_trans = findViewById(R.id.recyclerView_trans);
        tv_no_data_lead = findViewById(R.id.tv_no_data_lead);
        linear = findViewById(R.id.linear);

        /*tv_no_data_lead.setVisibility(View.VISIBLE);
        recyclerView_trans.setVisibility(View.GONE);
        linear.setVisibility(View.GONE);*/

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView_trans.setLayoutManager(layoutManager);
        recyclerView_trans.setHasFixedSize(true);
        tadapter = new TransactionAdapter(this, items_filtered);
        recyclerView_trans.setAdapter(tadapter);

        swiperefresh_mysales =findViewById(R.id.swiperefresh_mysales);
        swiperefresh_mysales.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myUpdateOperationSwipe();
            }
        });
        getSales();
        findViewById(R.id.mylead).setOnClickListener(this);
        findViewById(R.id.mysales).setOnClickListener(this);

        searchBox = findViewById(R.id.search_box_mysale);
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(leads.size() >0) {
                    tadapter.getFilter().filter(s.toString());
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    private void myUpdateOperationSwipe() {
        if( leads != null) {
            searchBox.setText("");
            leads.clear();
            items_filtered.clear();

            tadapter.notifyDataSetChanged();
            getSales();
        }
    }
    private void getSales()
    {
        try {
            android_id = Settings.Secure.getString(getContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
        }
        if (InternetConnection.checkInternetConnectivity()){

            swiperefresh_mysales.setRefreshing(true);
            StringRequest stringRequest=new StringRequest(Request.Method.POST,getResources().getString(R.string.hostName)+"/app/sales/v1_0/my_earnings_ap_i.php?", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    swiperefresh_mysales.setRefreshing(false);
                    try {
                        JSONObject jsonObject1=new JSONObject(response);
                        Log.e("aobsales","sale response "+response);
                        if (jsonObject1.getString("status").equalsIgnoreCase("0")) {
                            ((TextView)findViewById(R.id.label)).setText("Hi "+ShareprefreancesUtility.getInstance().getString("name")+"! "+jsonObject1.getString("message"));
                            ((TextView)findViewById(R.id.label1)).setText(" \u20B9 "+jsonObject1.getJSONArray("total_earned").getJSONObject(0).getString("total"));
                            JSONArray jsonArray = jsonObject1.getJSONArray("my_earning");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Earning_Data modal = new Earning_Data(
                                        jsonObject.getString("heading"),
                                        jsonObject.getString("body_text"),
                                        jsonObject.getString("amount"),
                                        jsonObject.getString("date"),
                                        jsonObject.getString("time"),
                                        jsonObject.getString("user_name"),
                                        jsonObject.getString("user_email"),
                                        jsonObject.getString("status"));
                                leads.add(modal);
                            }

                            items_filtered.clear();
                            items_filtered.addAll(leads);
                            tadapter.notifyDataSetChanged();

                            if(!leads.isEmpty()) {

                                recyclerView_trans.setVisibility(View.VISIBLE);
                                tv_no_data_lead.setVisibility(View.INVISIBLE);

                            }else {
                                recyclerView_trans.setVisibility(View.INVISIBLE);
                                tv_no_data_lead.setVisibility(View.VISIBLE);
                            }
                        }else if(jsonObject1.getString("status").equalsIgnoreCase("1"))
                        {
                            if(!leads.isEmpty()) {

                                recyclerView_trans.setVisibility(View.VISIBLE);
                                tv_no_data_lead.setVisibility(View.INVISIBLE);

                            }else {
                                recyclerView_trans.setVisibility(View.INVISIBLE);
                                tv_no_data_lead.setVisibility(View.VISIBLE);
                            }

                        }
                    } catch (JSONException e) {
                        swiperefresh_mysales.setRefreshing(false);
                        final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(TransactionActivity.this);
                        alertDialog.setMessage("Retry with Internet connection");
                        alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        alertDialog.show();
                        e.printStackTrace();
                    }
                }
                }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    swiperefresh_mysales.setRefreshing(false);
                    final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(TransactionActivity.this);
                    alertDialog.setMessage("Something went wrong!");
                    alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
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
                    return params;
                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    100000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MySingleton.getInstance(this).addToRequestQueue(stringRequest);
        }
        else {
            final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(this);
            alertDialog.setMessage("Retry with Internet connection");
            alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alertDialog.show();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mylead:
                if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                startActivity(new Intent(TransactionActivity.this,LeadActivity.class));
                finish();
                break;
            case R.id.mysales:
                if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                startActivity(new Intent(TransactionActivity.this,MySaleActivity.class));
                finish();
                break;
        }
    }
    private class TransactionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
        private List<Earning_Data> items = new ArrayList<>();
        private Context ctx;
        public View v;
        private long mLastClickTime = 0;
        Earning_Data earning_data;

        public TransactionAdapter(Context context, List<Earning_Data> items) {
            ctx = context;
            this.items = items;
        }
        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {

                    String charString = charSequence.toString();
                    items_filtered.clear();

                    if (charString.isEmpty()) {
                        items_filtered.addAll(leads);
                    } else {

                        for (Earning_Data row : leads) {

                            if (row.getUser_name().toLowerCase().contains(charString.toLowerCase()) ) {
                                items_filtered.add(row);
                            }
                        }
                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values = items_filtered;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    notifyDataSetChanged();
                }
            };
        }
        public class OriginalViewHolder extends RecyclerView.ViewHolder {
            TextView heading,body,time,date,amount,status;

            public OriginalViewHolder(View v) {
                super(v);
                heading=itemView.findViewById(R.id.heading);
                body=itemView.findViewById(R.id.body);
                time=itemView.findViewById(R.id.time);
                date=itemView.findViewById(R.id.date);
                amount=itemView.findViewById(R.id.amount);
                status=itemView.findViewById(R.id.status);
            }
        }
        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            if (holder instanceof OriginalViewHolder) {
                OriginalViewHolder view = (OriginalViewHolder) holder;
                earning_data=items.get(position);
                view.date.setText(earning_data.getDate());
                view.time.setText(earning_data.getTime());
                view.amount.setText("+"+earning_data.getAmount());
                view.heading.setText(earning_data.getHeading());
                view.body.setText(earning_data.getBody_text());
                view.status.setText(earning_data.getStatus());
            }
        }
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder vh;
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false);
            vh = new OriginalViewHolder(v);
            return vh;
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }
    }


