package com.e.salestracker.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.e.salestracker.Adapter.AdapterTeamLeaderListforPM_Cona;
import com.e.salestracker.Adapter.AdapterpersonList;
import com.e.salestracker.Modal.Model_cona_cateogry;
import com.e.salestracker.Modal.PersonClientModel;
import com.e.salestracker.Modal.PersonModal;
import com.e.salestracker.Modal.ProjectvisitModal;
import com.e.salestracker.R;
import com.e.salestracker.Utility.Config;
import com.e.salestracker.Utility.InternetConnection;
import com.e.salestracker.Utility.MySingleton;
import com.e.salestracker.Utility.ShareprefreancesUtility;
import com.e.salestracker.Utility.Tools;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class PersonListActivty extends AppCompatActivity {

    private TextView tv_no_data_lead, tvDate;
    private RecyclerView recyclerView;
    private AdapterpersonList mAdapter;
    private ItemTouchHelper mItemTouchHelper;
    private ProgressDialog progressDialog;
    private String mobile_no;
    private TextView ivDateFilter;
    private LinearLayout llDate;

    List<PersonModal> items = new ArrayList<>();
    SwipeRefreshLayout swiperefresh_myleads;
    private List<ProjectvisitModal> project_visit = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_list_activty);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        initToolbar();
        initComponent();
        swiperefresh_myleads.setRefreshing(true);
        swiperefresh_myleads.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initComponent();
            }
        });
    }

    private void initToolbar() {
        tv_no_data_lead = findViewById(R.id.tv_no_data_lead);
        swiperefresh_myleads = findViewById(R.id.swiperefresh_myleads);
        TextView title = findViewById(R.id.main_name);
        tvDate = findViewById(R.id.date);
        ImageView back = findViewById(R.id.back_image);
        ivDateFilter = findViewById(R.id.ivDateFilter);
        llDate = findViewById(R.id.llDate);
        String location = getIntent().getStringExtra("Location");
        title.setText(location);
        tvDate.setText(Tools.getFormattedDateSimple1(System.currentTimeMillis()));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        llDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDatePickerLight();
            }
        });
    }

    private void initComponent() {
        progressDialog.show();

        String selDate = tvDate.getText().toString();
        Log.e("selDate", ""+selDate);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        if(InternetConnection.checkInternetConnectivity()) {

            String role = ShareprefreancesUtility.getInstance().getString("role");

            if(Config.getProjectName().equalsIgnoreCase("PM Cona")){

                if(role.equalsIgnoreCase("Client")){
                    //getClientList();
                    getTeamLeaderListForPM_Cona("Client");
                }else if(role.equalsIgnoreCase("Team Leader")
                        || ShareprefreancesUtility.getInstance().getString("role").equalsIgnoreCase("Supervisor")){
                    getTeamLeaderListForPM_Cona("Team Leader");
                }else{
                    getTeamLeaderList();
                }
            }else{
                ///old view
                getTeamLeaderList();
            }
           // getTeamLeaderList();
        } else {
            swiperefresh_myleads.setRefreshing(false);
            AlertDialog.Builder builder1 = new AlertDialog.Builder(PersonListActivty.this);
            builder1.setMessage("Retry with Internet connection");
            builder1.setCancelable(false);
            builder1.setPositiveButton(
                    "Retry",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            initComponent();
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

    ///old view
    private void getTeamLeaderList() {

        String apiUrl = "", apiUrlwithDateFilter="";
        //Project = "", List come location vise,
        //Project = Project_Name, List come with Project Name vise and Project Location vise
       final String selectedPproject = getIntent().getExtras().getString("Project");
        String selectedDate = tvDate.getText().toString();

        if(selectedPproject.isEmpty() || selectedPproject == ""){

            // api for filter list by date
            try {
                //String selectedDate = tvDate.getText().toString();
                apiUrlwithDateFilter = getResources().getString(R.string.host_name) + "/sales_tracker/new_api/team_leader_employee_list.php?city=" + URLEncoder.encode(getIntent().getStringExtra("Location").trim(), "UTF-8") + "&team_leader_email=" + ShareprefreancesUtility.getInstance().getString("email_id") + "&user_type=" + URLEncoder.encode(ShareprefreancesUtility.getInstance().getString("user"), "UTF-8") + "&date=" + selectedDate;

                if (apiUrlwithDateFilter.contains("D Jaipur")) {
                    apiUrlwithDateFilter = apiUrlwithDateFilter.replace("D Jaipur", "D+Jaipur");
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }else {

             // list of member on project and their location vise;
            try {
                // https://aobindia.in/sales_tracker/new_api/filter_by_project_name_with_date_loc.php?city=Chandigarh&team_leader_email=developer@aob.com&user_type=Admin&date=10-Oct-2019&project_name=OyeSpace%20Service
                apiUrlwithDateFilter = getResources().getString(R.string.host_name) + "/sales_tracker/new_api/filter_by_project_name_with_date_loc.php?city=" + URLEncoder.encode(getIntent().getStringExtra("Location").trim(), "UTF-8") + "&team_leader_email=" + ShareprefreancesUtility.getInstance().getString("email_id") + "&user_type=" + URLEncoder.encode(ShareprefreancesUtility.getInstance().getString("user"), "UTF-8") + "&date=" + selectedDate + "&project_name=" + URLEncoder.encode(selectedPproject, "UTF-8");

                Log.e("member list", "project & location"+apiUrlwithDateFilter);
            }catch (UnsupportedEncodingException e){
                e.printStackTrace();
            }
        }

        //old Url
        try {
             apiUrl = getResources().getString(R.string.host_name) + "/sales_tracker/team_leader_employee_list.php?city=" + URLEncoder.encode(getIntent().getStringExtra("Location").trim(), "UTF-8") + "&team_leader_email=" + ShareprefreancesUtility.getInstance().getString("email_id") + "&user_type=" + URLEncoder.encode(ShareprefreancesUtility.getInstance().getString("user"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

      /*
       // api for filter list by date
       try {
            String selectedDate = tvDate.getText().toString();
            apiUrlwithDateFilter =  getResources().getString(R.string.host_name) + "/sales_tracker/new_api/team_leader_employee_list.php?city="+URLEncoder.encode(getIntent().getStringExtra("Location").trim(), "UTF-8") + "&team_leader_email="+ShareprefreancesUtility.getInstance().getString("email_id") + "&user_type="+URLEncoder.encode(ShareprefreancesUtility.getInstance().getString("user"),"UTF-8")+"&date="+selectedDate ;

            if(apiUrlwithDateFilter.contains("D Jaipur")){
                apiUrlwithDateFilter = apiUrlwithDateFilter.replace("D Jaipur", "D+Jaipur");
            }

    } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
    } */

        Log.e("getTeamLeaderList","url "+apiUrlwithDateFilter);
            //  if(InternetConnection.checkInternetConnectivity()) {
            StringRequest stringRequest = null;
            try {
                stringRequest = new StringRequest(Request.Method.POST,
                        apiUrlwithDateFilter,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.e("getTeamLeaderList",""+response);
                                swiperefresh_myleads.setRefreshing(false);
                                if (progressDialog.isShowing())
                                progressDialog.dismiss();
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject.getString("status").equalsIgnoreCase("ok")) {
                                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                                        if (items != null)
                                            items.clear();

                                        if (project_visit != null) {
                                            project_visit.clear();
                                        }

                                        if (jsonArray.length() > 0) {

                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                            JSONArray jsonArray1 = jsonObject1.getJSONArray("project_name");

                                            String projects = "";
                                            String projects_visit = "";
                                            String selectedProjectVisit = "";


                                            for (int j = 0; j < jsonArray1.length(); j++) {
                                                projects = projects + jsonArray1.getJSONObject(j).getString("project_name") + " :\n";
                                                projects_visit = projects_visit + jsonArray1.getJSONObject(j).getString("visit_count") + "\n";
                                               if(selectedPproject.equalsIgnoreCase(jsonArray1.getJSONObject(j).getString("project_name"))){
                                                 selectedProjectVisit = jsonArray1.getJSONObject(j).getString("visit_count");
                                                }

                                            }

                                            PersonModal personModal = new PersonModal(selectedPproject,selectedProjectVisit,jsonObject1.getString("agent_id"),
                                                    jsonObject1.getString("agent_name"),
                                                    //jsonObject1.getString("first_checkin") + "/" + jsonObject1.getString("date_of_checkin"),
                                                    jsonObject1.getString("first_checkin"), //+ "/" + jsonObject1.getString("date_of_checkin"),
                                                    jsonObject1.getString("last_checkout"),
                                                    jsonObject1.getString("visit_total"),
                                                    jsonObject1.getString("last_location"),
                                                    jsonObject1.getString("mobile_no"),
                                                    projects,
                                                    projects_visit,
                                                    jsonObject1.getString("agent_profile")


                                            );

                                            items.add(personModal);
                                        }
                                    }
                                        mAdapter = new AdapterpersonList(PersonListActivty.this, items);
                                        recyclerView.setAdapter(mAdapter);
                                        mAdapter.setOnItemClickListener(new AdapterpersonList.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(View view, PersonModal obj, int position) {
                                                mobile_no = obj.getMobile_no();
                                                makeCall(mobile_no);

                                            }
                                        });
                                        if (!items.isEmpty()) {
                                            recyclerView.setVisibility(View.VISIBLE);
                                            tv_no_data_lead.setVisibility(View.INVISIBLE);

                                        } else {
                                            recyclerView.setVisibility(View.INVISIBLE);
                                            tv_no_data_lead.setVisibility(View.VISIBLE);

                                        }
                                    } else {
                                        Toast.makeText(PersonListActivty.this, jsonObject.getString("status"), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    Toast.makeText(PersonListActivty.this, e.toString(), Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.e("error1","error is"+error.toString());
                        swiperefresh_myleads.setRefreshing(false);
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(PersonListActivty.this);
                        builder1.setMessage("Check your internet connection");
                        builder1.setCancelable(false);
                        builder1.setPositiveButton(
                                "Retry",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        initComponent();
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
            } catch (Exception e) {
                e.printStackTrace();
            }
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    100000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MySingleton.getInstance(PersonListActivty.this).addToRequestQueue(stringRequest);
    }

    //get Pm Cona Team Leader List
    private void getTeamLeaderListForPM_Cona(String role){

        String apiUrl = "";
        if(role.equalsIgnoreCase("Team Leader")
                || ShareprefreancesUtility.getInstance().getString("role").equalsIgnoreCase("Supervisor")){

            // for Team Leader in PM Cona Project
            try {
                apiUrl = getResources().getString(R.string.host_name)+"/sales_tracker/team_leader_employee_list_for_testing_cona_new.php?user_type=Team+Leader&city="+URLEncoder.encode(getIntent().getStringExtra("Location"), "UTF-8")+"&project_name=none&team_leader_email="+URLEncoder.encode(ShareprefreancesUtility.getInstance().getString("email_id"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }else {
            // for client in PM Cona Project
            try {
            apiUrl = getResources().getString(R.string.host_name)+"/sales_tracker/team_leader_employee_list_for_testing_cona_new.php?user_type=client&city="+URLEncoder.encode(getIntent().getStringExtra("Location"),"UTF-8")+"&project_name=PM+Cona&team_leader_email=none"  ;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        }

        StringRequest stringRequest = null;
      //  Log.e("response123456 url", ""+getResources().getString(R.string.host_name)+"/sales_tracker/team_leader_employee_list_for_testing_cona_new.php?user_type=Team+Leader&city="+URLEncoder.encode(getIntent().getStringExtra("Location"),"UTF-8")+"&project_name=none&team_leader_email="+URLEncoder.encode(ShareprefreancesUtility.getInstance().getString("email_id")));
        try {
            stringRequest = new StringRequest(Request.Method.POST, apiUrl,

                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            swiperefresh_myleads.setRefreshing(false);

                            Log.e("response123456", ""+response);
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                            try {

                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("Data");

                                    ArrayList<PersonClientModel> itemsClientData = new ArrayList<>();
                                    List<Model_cona_cateogry> items_cona_cateogry = new ArrayList<>();
                                    itemsClientData.clear();

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                        JSONArray jsonArray_projectvisit = jsonObject1.getJSONArray("cona_cateogry");

                                        // get collection & order value
                                        String total_collection_value = "";
                                        String total_order_value = "";
                                        JSONObject jsontotal_cona_value = jsonObject1.getJSONObject("total_cona_value");
                                        total_collection_value = jsontotal_cona_value.getString("total_collection_value");
                                        total_order_value = jsontotal_cona_value.getString("total_order_value");

                                        items_cona_cateogry = new ArrayList<>();

                                           for (int j = 0; j < jsonArray_projectvisit.length(); j++) {

                                               JSONObject jsonObject11 = jsonArray_projectvisit.getJSONObject(j);

                                               Model_cona_cateogry model_cona_cateogry = new Model_cona_cateogry();
                                               model_cona_cateogry.setCollection_value("" + jsonObject11.getString("collection_value"));
                                               model_cona_cateogry.setOrder_value(jsonObject11.getString("order_value"));
                                               model_cona_cateogry.setClient_name("" + jsonObject11.getString("client_name"));
                                               model_cona_cateogry.setCategory("" + jsonObject11.getString("category"));

                                             items_cona_cateogry.add(model_cona_cateogry);
                                         }

                                        PersonClientModel personClientModal = new PersonClientModel(

                                                (jsonObject1.getString("agent_id")),
                                                jsonObject1.getString("agent_name"),
                                                jsonObject1.getString("first_checkin"),
                                                jsonObject1.getString("last_checkout"),
                                                jsonObject1.getString("last_location"),
                                                jsonObject1.getString("date_of_checkin"),
                                                jsonObject1.getString("visit_total"),
                                                jsonObject1.getString("mobile_no"),
                                                jsonObject1.getString("agent_profile"),
                                                total_collection_value,
                                                total_order_value,

                                                items_cona_cateogry);

                                        itemsClientData.add(personClientModal);

                                        Log.e("test_field_1 size 2", ""+itemsClientData.size());
                                    }
                                    AdapterTeamLeaderListforPM_Cona adapterTeamLeaderListforPM_Cona = new AdapterTeamLeaderListforPM_Cona(PersonListActivty.this, itemsClientData, items_cona_cateogry);
                                    recyclerView.setAdapter(adapterTeamLeaderListforPM_Cona);

                                    adapterTeamLeaderListforPM_Cona.setOnItemClickListener(new AdapterTeamLeaderListforPM_Cona.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, PersonClientModel obj, int position) {
                                            mobile_no = obj.getMobile_no();
                                            makeCall(mobile_no);

                                        }
                                    });

                                    if (!itemsClientData.isEmpty()) {
                                        recyclerView.setVisibility(View.VISIBLE);
                                        tv_no_data_lead.setVisibility(View.INVISIBLE);

                                    } else {
                                        recyclerView.setVisibility(View.INVISIBLE);
                                        tv_no_data_lead.setVisibility(View.VISIBLE);

                                    }
                                } else {
                                    Toast.makeText(PersonListActivty.this, jsonObject.getString("status"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(PersonListActivty.this, e.toString(), Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    swiperefresh_myleads.setRefreshing(false);
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(PersonListActivty.this);
                    builder1.setMessage("Check your internet connection");
                    builder1.setCancelable(false);
                    builder1.setPositiveButton(
                            "Retry",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    initComponent();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(PersonListActivty.this).addToRequestQueue(stringRequest);


    }


    private void makeCall(String mobile_no) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        } else {
            Intent intent = new Intent("android.intent.action.CALL");
            intent.setData(Uri.parse("tel:+91"+mobile_no));
            startActivity(intent);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
        } else {
            if (mobile_no!=null){
                makeCall(mobile_no);
            }

        }
    }

    // Filter list by date
    private void dialogDatePickerLight() {
        Calendar cur_calender = Calendar.getInstance();
        DatePickerDialog datePicker = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        long date_ship_millis = calendar.getTimeInMillis();
                        tvDate.setText(Tools.getFormattedDateSimple1(date_ship_millis));
                        initComponent();
                        }
                },
                cur_calender.get(Calendar.YEAR),
                cur_calender.get(Calendar.MONTH),
                cur_calender.get(Calendar.DAY_OF_MONTH)
        );
        //set dark light
        datePicker.setThemeDark(false);
        datePicker.setAccentColor(getResources().getColor(R.color.colorPrimary));
        datePicker.setMaxDate(cur_calender);
        datePicker.show(getFragmentManager(), "Datepickerdialog");
    }

}
