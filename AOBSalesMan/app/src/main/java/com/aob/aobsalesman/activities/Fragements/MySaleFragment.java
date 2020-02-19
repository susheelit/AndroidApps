package com.aob.aobsalesman.activities.Fragements;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.aob.aobsalesman.R;
import com.aob.aobsalesman.activities.activities.KYCActivity;
import com.aob.aobsalesman.activities.model.LeadModal;
import com.aob.aobsalesman.activities.model.SalesModal;
import com.aob.aobsalesman.activities.utility.InternetConnection;
import com.aob.aobsalesman.activities.utility.MySingleton;
import com.aob.aobsalesman.activities.utility.ShareprefreancesUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MySaleFragment extends Fragment {
    private RecyclerView recyclerView;
    RequestQueue mRequestQueue;
    private ProgressBar progressBar;
    private List<SalesModal> leads = new ArrayList();
    private List<SalesModal> items_filtered = new ArrayList();
    private View view;
    LinearLayout empity_layout;
    String jsonResponse=null;
    SalesAdapter leadAdapter;
    EditText searchBox;

    TextView tv_no_data_lead;

    private ProgressDialog progressDialog;

    SwipeRefreshLayout swiperefresh_mysales;

    @SuppressLint("HardwareIds")
    String android_id = "";

    public MySaleFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_mysale_frag, container, false);
        recyclerView=view.findViewById(R.id.lead_recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        leadAdapter = new SalesAdapter(getActivity(), items_filtered);
        recyclerView.setAdapter(leadAdapter);

        tv_no_data_lead = view.findViewById(R.id.tv_no_data_lead);

        swiperefresh_mysales = view.findViewById(R.id.swiperefresh_mysales);
        swiperefresh_mysales.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myUpdateOperationSwipe();
            }
        });

        getSales();

        ((TextView)getActivity().findViewById(R.id.main_name)).setText("My Sales");

        searchBox = view.findViewById(R.id.search_box_mysale);
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(leads.size() >0) {
                    leadAdapter.getFilter().filter(s.toString());
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (leadAdapter.getItemCount() == 0){
                    ((TextView)view.findViewById(R.id.tv_no_data_lead)).setText("No result found.");
                    ((TextView)view.findViewById(R.id.tv_no_data_lead)).setVisibility(View.VISIBLE);
                }
                else {
                    ((TextView)view.findViewById(R.id.tv_no_data_lead)).setVisibility(View.GONE);
                }
            }
        });


        return view;
    }

    private void getSales()
    {

        try {

            android_id = Settings.Secure.getString(getContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);

        } catch (Exception e) {
        }

        if (InternetConnection.checkInternetConnectivity()){

            //progressDialog=new ProgressDialog(getActivity());
            //progressDialog.setMessage("Please wait...");
            //progressDialog.setCancelable(false);
            //progressDialog.show();

            swiperefresh_mysales.setRefreshing(true);

            StringRequest stringRequest=new StringRequest(Request.Method.POST,getResources().getString(R.string.hostName)+"/app/sales/v1_0/salesman_view_sales.php?", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    swiperefresh_mysales.setRefreshing(false);
                    try {
                        JSONObject jsonObject1=new JSONObject(response);

                        Log.e("aobsales","sale response "+response);

                        if (jsonObject1.getString("status").equalsIgnoreCase("0")) {
                            JSONArray jsonArray = jsonObject1.getJSONArray("sales_logs");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                SalesModal modal = new SalesModal(jsonObject.getString("lead_id"),
                                        jsonObject.getString("agent_email"),
                                        jsonObject.getString("project_name"),
                                        jsonObject.getString("company_name"),
                                        jsonObject.getString("contact_person"),
                                        jsonObject.getString("designation"),
                                        jsonObject.getString("contact_no"),
                                        jsonObject.getString("email_id"),
                                        jsonObject.getString("address"),
                                        jsonObject.getString("city"),
                                        jsonObject.getString("state"),
                                        jsonObject.getString("pincode"),
                                        jsonObject.getString("gst_no"),
                                        jsonObject.getString("pan_no"),
                                        jsonObject.getString("order_detail"),
                                        jsonObject.getString("date"),
                                        jsonObject.getString("time"),
                                        jsonObject.getString("lead_status"),
                                        jsonObject.getString("type"),
                                        jsonObject.getString("payment_detail"));
                                leads.add(modal);
                            }

                            items_filtered.clear();
                            items_filtered.addAll(leads);
                            leadAdapter.notifyDataSetChanged();

                            if(!leads.isEmpty()) {

                                recyclerView.setVisibility(View.VISIBLE);
                                tv_no_data_lead.setVisibility(View.INVISIBLE);

                            }else
                            {
                                recyclerView.setVisibility(View.INVISIBLE);
                                tv_no_data_lead.setVisibility(View.VISIBLE);

                            }


                        }else if(jsonObject1.getString("status").equalsIgnoreCase("1"))
                        {


                            if(!leads.isEmpty()) {

                                recyclerView.setVisibility(View.VISIBLE);
                                tv_no_data_lead.setVisibility(View.INVISIBLE);

                            }else
                            {
                                recyclerView.setVisibility(View.INVISIBLE);
                                tv_no_data_lead.setVisibility(View.VISIBLE);

                            }

                        }



                    } catch (JSONException e) {
                        swiperefresh_mysales.setRefreshing(false);
                        final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(getActivity());
                        alertDialog.setMessage("Retry with Internet connection");
                        alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        alertDialog.show();
                        e.printStackTrace();
                    }
                    //  Toast.makeText(getActivity(),response, Toast.LENGTH_SHORT).show();
                }


            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    swiperefresh_mysales.setRefreshing(false);
                    final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(getActivity());
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
            MySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
        }
        else {

            final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(getActivity());
            alertDialog.setMessage("Retry with Internet connection");
            alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alertDialog.show();

        }

    }




    private class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.ViewHolder> implements Filterable {
        private Context context;
        private List<SalesModal> itoms = new ArrayList();
        private long mLastClickTime = 0;
        SalesModal historyModal;

        public SalesAdapter(Context context, List<SalesModal> itoms) {
            this.context = context;
            this.itoms = itoms;
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView company_name, person_name,  date, status, project_name;
            LinearLayout lyt_parent;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                company_name = itemView.findViewById(R.id.company_name);
                person_name = itemView.findViewById(R.id.person_name);
                date = itemView.findViewById(R.id.date);
                status = itemView.findViewById(R.id.status);
                lyt_parent = itemView.findViewById(R.id.lyt_parent);
                project_name = itemView.findViewById(R.id.project_name);
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.sales_item, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

            historyModal = itoms.get(i);

            viewHolder.company_name.setText(historyModal.getCompany_name());
            viewHolder.person_name.setText(historyModal.getContact_person());
            viewHolder.date.setText(historyModal.getDate());
            viewHolder.status.setText(historyModal.getLead_status());
            viewHolder.project_name.setText(historyModal.getProject_id());

            viewHolder.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = manager.beginTransaction();
                    MySaleDetailFragment leadDetailFragement = new MySaleDetailFragment();

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("MyClass", (Serializable) itoms.get(i));
                    leadDetailFragement.setArguments(bundle);

                    //fragmentTransaction.replace(R.id.main_lead, leadDetailFragement);
                    fragmentTransaction.hide(getFragmentManager().findFragmentByTag("mysale_frag"));
                    fragmentTransaction.add(R.id.main_lead, leadDetailFragement);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });

        }

        @Override
        public int getItemCount() {
            return itoms.size();
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

                        for (SalesModal row : leads) {


                            if (row.getProject_id().toLowerCase().contains(charString.toLowerCase()) ) {
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


    }


    private void myUpdateOperationSwipe() {
        if( leads != null) {
            searchBox.setText("");
            leads.clear();
            items_filtered.clear();
            leadAdapter.notifyDataSetChanged();
            getSales();
        }
    }
}
