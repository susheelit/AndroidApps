package com.aob.aobsalesman.activities.Fragements;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
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
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.aob.aobsalesman.R;
import com.aob.aobsalesman.activities.activities.ActivityFilter;
import com.aob.aobsalesman.activities.activities.HomeActivity;
import com.aob.aobsalesman.activities.activities.KYCActivity;
import com.aob.aobsalesman.activities.activities.NotificationsActivity;
import com.aob.aobsalesman.activities.activities.ProjectDescriptionActivity;
import com.aob.aobsalesman.activities.activities.RegistrationActivity;
import com.aob.aobsalesman.activities.model.Company_Data;
import com.aob.aobsalesman.activities.utility.InternetConnection;
import com.aob.aobsalesman.activities.utility.MySingleton;
import com.aob.aobsalesman.activities.utility.ShareData;
import com.aob.aobsalesman.activities.utility.ShareprefreancesUtility;
import com.aob.aobsalesman.activities.utility.Tools;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

public class FragmentAllProjects extends Fragment {

    String tag = "aobsalesman";

    private FragmentAllProjects.OnFragmentInteractionListener mListener;
    View view;
    RecyclerView recyclerView_all;
    private List<Company_Data> company_data=new ArrayList<>();
    private List<Company_Data> filteredList = new ArrayList<>();
    ArrayList<Company_Data> sortArrayList=new ArrayList<>();
    ArrayList<Company_Data> filterArrayList = new ArrayList<>();
    AllProjectAdapter companyDataAdapter;
    TextView tv_register_status;

    private Gson gson;
    Boolean active = false;

    ProgressBar  progressBar;

    String[] descriptionData = {"KYC Sumbit", "Verification", "Approved"};
    String[] descriptionData1 = {"KYC Sumbit", "Verification", "Approved"};
    String[] descriptionData2 = {"KYC Sumbit", "Verification", "Approved"};

    long mLastClickTime1 = 0;
    long mLastClickTime2 = 0;
    long mLastClickTime3 = 0;
    long mLastClickTime11 =0;
    long mLastClickTime13 =0;
    long mLastClickTime14 =0;

    LinearLayout main_linear,filter_linear;

    String filter_state = "";
    String filter_industry = "";

    private List<String> fstatedata = new ArrayList<>();
    private List<String> findustrydata = new ArrayList<>();

    String CheckSort = "none";
    String ChecKFilter = "inactive";

    CardView card_fragall;

    EditText searchBox;

    private ProgressDialog progressDialog1;

    @SuppressLint("HardwareIds")
    String android_id = "";

    SwipeRefreshLayout swiperefresh_allprojects;

    TextView tv_no_data_lead;

    public FragmentAllProjects() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_all_projects, container, false);
        recyclerView_all = view.findViewById(R.id.recyclerView_all);
        main_linear = view.findViewById(R.id.main_linear);
        tv_register_status = view.findViewById(R.id.tv_register_status);
        tv_no_data_lead = view.findViewById(R.id.tv_no_data_lead);

        //card_fragall = view.findViewById(R.id.card_fragall);
        //swiperefresh_all = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh_all);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        /* Log.e("aobsales","allproject called");

        Company_Data ap = new Company_Data("Samsung","New Delhi","Rs 300", false,0);
        company_data.add(ap);

        Company_Data ap1 = new Company_Data("Panasonic","Gurgaon","Rs 500",false,1);
        company_data.add(ap1);

        Company_Data ap2 = new Company_Data("MyKahani","Allahbad","Rs 3000",false,2);
        company_data.add(ap2);

        Company_Data ap3 = new Company_Data("MyDataGator","Jaipur","Rs 700",false,3);
        company_data.add(ap3);  */


     Bundle bundle = this.getArguments();
        if (bundle != null) {

            String response1 = bundle.getString("response1");

            try {
                if(!TextUtils.isEmpty(response1) && response1 != null) {
                    JSONArray jsonArray = new JSONArray(response1);

                    if (jsonArray.length() > 0) {
                        company_data = new ArrayList<Company_Data>(Arrays.asList(gson.fromJson(jsonArray.toString(), Company_Data[].class)));

                        recyclerView_all.setVisibility(View.VISIBLE);
                        tv_no_data_lead.setVisibility(View.INVISIBLE);

                    } else {
                        recyclerView_all.setVisibility(View.INVISIBLE);
                        tv_no_data_lead.setVisibility(View.VISIBLE);
                    }

                }else
                {
                    recyclerView_all.setVisibility(View.INVISIBLE);
                    tv_no_data_lead.setVisibility(View.VISIBLE);
                }
            }catch (Exception e){
                Log.e("aobsales"," is "+e.toString());
            }

        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView_all.setLayoutManager(layoutManager);
        recyclerView_all.setHasFixedSize(true);
        //getProject();
        filteredList.addAll(company_data);
        sortArrayList.addAll(company_data);
        companyDataAdapter = new AllProjectAdapter(getActivity(), filteredList);
        recyclerView_all.setAdapter(companyDataAdapter);

        searchBox = view.findViewById(R.id.search_box);
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(company_data.size() > 0) {

                    companyDataAdapter.getFilter().filter(s.toString());

                }

            }
            @Override
            public void afterTextChanged(Editable s) {
                if (companyDataAdapter.getItemCount() == 0){
                    ((TextView)view.findViewById(R.id.tv_no_data_lead)).setText("No result found.");
                    view.findViewById(R.id.tv_no_data_lead).setVisibility(View.VISIBLE);
                }
                else {
                    view.findViewById(R.id.tv_no_data_lead).setVisibility(View.GONE);
                }

            }
        });



        //progressBar = (ProgressBar) view.findViewById(R.id.progressBarHorizontal);

       /* new Thread(new Runnable() {
            @Override
            public void run() {
                while (pStatus <= 100) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(pStatus);
                            txtProgress.setText(pStatus + " %");
                        }
                    });
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    pStatus++;
                }
            }
        }).start();*/


/*

        if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("register_status", ""))
                && !ShareprefreancesUtility.getInstance().getString("register_status", "").equalsIgnoreCase("null")
                && ShareprefreancesUtility.getInstance().getString("register_status", "") != null
        ) {

            if(ShareprefreancesUtility.getInstance().getString("register_status", "").equalsIgnoreCase("pending"))
            {
                tv_register_status.setText("40% Registration Complete");
                progressBar.setProgress(40);

            } else  if(ShareprefreancesUtility.getInstance().getString("register_status", "").equalsIgnoreCase("complete"))
            {
                tv_register_status.setText("100% Registration Complete");
                progressBar.setProgress(100);
            }


        }

        StateProgressBar stateProgressBar = (StateProgressBar) view.findViewById(R.id.state_progress);



        if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("kyc_status", ""))
                && !ShareprefreancesUtility.getInstance().getString("kyc_status", "").equalsIgnoreCase("null")
                && ShareprefreancesUtility.getInstance().getString("kyc_status", "") != null
        ) {

           if(ShareprefreancesUtility.getInstance().getString("kyc_status", "").equalsIgnoreCase("pending"))
            {
                Log.e("aobsales","test "+ShareprefreancesUtility.getInstance().getString("kyc_status", ""));
                stateProgressBar.setStateDescriptionData(descriptionData);
                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
            }
            else if(ShareprefreancesUtility.getInstance().getString("kyc_status", "").equalsIgnoreCase("complete")){

               Log.e("aobsales","test "+ShareprefreancesUtility.getInstance().getString("kyc_status", ""));
               stateProgressBar.setStateDescriptionData(descriptionData1);
               stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
            }
            else if(ShareprefreancesUtility.getInstance().getString("kyc_status", "").equalsIgnoreCase("approved"))
           {
               Log.e("aobsales","test "+ShareprefreancesUtility.getInstance().getString("kyc_status", ""));
               stateProgressBar.setStateDescriptionData(descriptionData2);
               stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
           }


        }
*/


       /* LinearLayout linear_register = view.findViewById(R.id.linear_register);
        linear_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SystemClock.elapsedRealtime() - mLastClickTime3 < 1000) {
                    return;
                }
                mLastClickTime3 = SystemClock.elapsedRealtime();
                Intent i = new Intent(getActivity(), RegistrationActivity.class);
                startActivity(i);

            }
        });

        LinearLayout kyc_linear = view.findViewById(R.id.kyc_linear);
        kyc_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SystemClock.elapsedRealtime() - mLastClickTime2 < 1000) {
                    return;
                }
                mLastClickTime2 = SystemClock.elapsedRealtime();
                Intent i = new Intent(getActivity(), KYCActivity.class);
                startActivity(i);

            }
        });*/

        try {
            getActivity().findViewById(R.id.sort).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((ViewPager) getActivity().findViewById(R.id.viewpager)).getCurrentItem() == 0) {


                        Log.e("aobsales","1st clicked");

                        if (SystemClock.elapsedRealtime() - mLastClickTime14 < 1000) {
                            return;
                        }
                        mLastClickTime14 = SystemClock.elapsedRealtime();
                        showSortDialog();

                    } else {

                    }
                }
            });
        }catch (Exception e){}
/*
        ImageView sort_image = view.findViewById(R.id.sort_image);
        sort_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime14 < 1000) {
                    return;
                }
                mLastClickTime14 = SystemClock.elapsedRealtime();
                showSortDialog();

            }
        });*/


        getActivity().findViewById(R.id.notification_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((ViewPager) getActivity().findViewById(R.id.viewpager)).getCurrentItem() == 0) {
                    startActivity(new Intent(getActivity(), NotificationsActivity.class));
                }
                else {

                }
            }
        });
        try {
            getActivity().findViewById(R.id.filter).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((ViewPager) getActivity().findViewById(R.id.viewpager)).getCurrentItem() == 0) {

                        if (SystemClock.elapsedRealtime() - mLastClickTime1 < 1000) {
                            return;
                        }
                        mLastClickTime14 = SystemClock.elapsedRealtime();


                            searchBox.setText("");
                             filteredList.clear();
                             sortArrayList.clear();
                             filteredList.addAll(company_data);
                             sortArrayList.addAll(company_data);
                             companyDataAdapter.notifyDataSetChanged();
                        CheckSort = "none";
                        Intent intent = new Intent(getActivity(), ActivityFilter.class);
                        intent.putExtra("resultindustry", filter_industry);
                        intent.putExtra("resultcity", filter_state);
                        startActivityForResult(intent, 1000);
                        getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.fade_out);


                    } else {

                    }
                }
            });
        }catch (Exception e){}

        swiperefresh_allprojects = view.findViewById(R.id.swiperefresh_allprojects);


        swiperefresh_allprojects = view.findViewById(R.id.swiperefresh_allprojects);
        swiperefresh_allprojects.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myUpdateOperationSwipe();
            }
        });

        /*ImageView filter_image = view.findViewById(R.id.filter_image);
        filter_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime14 < 1000) {
                    return;
                }
                mLastClickTime14 = SystemClock.elapsedRealtime();


                searchBox.setText("");
               *//* filteredList.clear();
                sortArrayList.clear();
                filteredList.addAll(company_data);
                sortArrayList.addAll(company_data);
                companyDataAdapter.notifyDataSetChanged();*//*
                CheckSort = "none";
                Intent intent = new Intent(getActivity(), ActivityFilter.class);
                intent.putExtra("resultindustry",filter_industry);
                intent.putExtra("resultcity",filter_state);
                startActivityForResult(intent, 1000);
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        });*/

        try {
            android_id = Settings.Secure.getString(getContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);

        } catch (Exception e) {
        }

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void showRegistrationDialog() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage("Please fill complete Registration details to apply for the project.");
        builder1.setCancelable(true);
        builder1.setTitle("Complete your profile");
        builder1.setPositiveButton(
                "Register Now",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(getActivity(), RegistrationActivity.class);
                        startActivity(i);
                    }
                });
        builder1.setNegativeButton(
                "Later",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void showKYCDialog() {

        AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
        builder2.setMessage("Profile is incomplete. Please update your KYC details to apply for the project.");
        builder2.setCancelable(true);
        builder2.setTitle("KYC is pending");
        builder2.setPositiveButton(
                "Now",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(getActivity(), KYCActivity.class);
                        startActivity(i);
                    }
                });
        builder2.setNegativeButton(
                "Later",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();

                    }
                });

        AlertDialog alert2 = builder2.create();
        alert2.show();

    }

    public void showKYCDialogRejected() {

        AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
        builder2.setMessage("Please update your KYC details to apply for the project.");
        builder2.setCancelable(true);
        builder2.setTitle("Your KYC is Rejected.");
        builder2.setPositiveButton(
                "Now",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(getActivity(), KYCActivity.class);
                        startActivity(i);
                    }
                });
        builder2.setNegativeButton(
                "Later",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();

                    }
                });

        AlertDialog alert2 = builder2.create();
        alert2.show();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void getProject() {
        //swiperefresh_all.setRefreshing(true);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "https://api.androidhive.info/json/inbox.json",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray jsonArray = new JSONArray(response);

                            Intent intent = new Intent(getActivity(), HomeActivity.class);
                            startActivity(intent);
                            getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                        } catch (JSONException e) {
                            Log.e("aobsales",e.toString());
                        }

                        //swiperefresh_all.setRefreshing(false);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //swiperefresh_all.setRefreshing(false);

            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    public class AllProjectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

        private List<Company_Data> items = new ArrayList<>();
        private Context ctx;
        public View v;
        private CustomFilter mFilter;
        private List<Company_Data> ListFiltered;
        private long mLastClickTime = 0;

        public AllProjectAdapter(Context context, List<Company_Data> items ) {

            ctx = context;
            this.items=items;
            mFilter = new CustomFilter(AllProjectAdapter.this);
        }

        public class OriginalViewHolder extends RecyclerView.ViewHolder {
            public ImageView image;
            public TextView company_name;
            public TextView title;
            public View lyt_parent;
            public TextView apply_rv;
            public TextView views;
            public TextView nature_of_project;
            public TextView no_of_salesman;
            public TextView incentive_earned_total;
            public ImageView share_image;
            public ImageView call;

            public OriginalViewHolder(View v) {
                super(v);
                image = v.findViewById(R.id.image);
                share_image = v.findViewById(R.id.share_image);
                call = v.findViewById(R.id.call);
                lyt_parent = v.findViewById(R.id.lyt_parent);
                company_name =  v.findViewById(R.id.company_name);
                title =  v.findViewById(R.id.title);
                apply_rv = v.findViewById(R.id.apply_rv);
                views = v.findViewById(R.id.views);
                nature_of_project = v.findViewById(R.id.nature_of_project);
                no_of_salesman = v.findViewById(R.id.no_of_salesman);
                incentive_earned_total = v.findViewById(R.id.incentive_earned_total);

            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder vh;
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
            vh = new OriginalViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder,  final int position) {
            Log.e("onBindViewHolder", "onBindViewHolder : " + position);
            if (holder instanceof OriginalViewHolder) {
                OriginalViewHolder view = (OriginalViewHolder) holder;

                try {

                    view.company_name.setText(items.get(position).getProject_name());
                    view.title.setText(items.get(position).getProjectTitle());
                    view.views.setText("Views: "+items.get(position).getViews());

                    view.no_of_salesman.setText("Active Salesmen: "+items.get(position).getSalesman_working_on());

                    Tools.displayImageOriginal(ctx, view.image, items.get(position).getLogo_url());

                    final String cityList = android.text.TextUtils.join(",", items.get(position).getSales_type());
                    view.nature_of_project.setText("Nature of Project: "+cityList);

                    view.incentive_earned_total.setText("Total Incentive Earned: Rs. "+items.get(position).getIncentive_earned_total());

                /*if(items.get(position).getApplied() == false) {

                    view.apply_rv.setEnabled(true);

                    view.apply_rv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Log.e("aobsales", "full clicked");
                        }
                    });

                }
                else
                {
                    view.apply_rv.setEnabled(false);
                    view.apply_rv.setText("Applied");
                }*/

                    if (items.get(position).getProject_status().equalsIgnoreCase("active")) {
                        view.apply_rv.setEnabled(true);
                        view.apply_rv.setText("Apply");

                        view.apply_rv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if (SystemClock.elapsedRealtime() - mLastClickTime11 < 1000) {
                                    return;
                                }
                                mLastClickTime11 = SystemClock.elapsedRealtime();

                                try {
                                    if ((ShareprefreancesUtility.getInstance().getString("register_status")).equalsIgnoreCase("complete")) {

                                        if ((ShareprefreancesUtility.getInstance().getString("kyc_status")).equalsIgnoreCase("approved")) {

                                            if (InternetConnection.checkInternetConnectivity()) {

                                                progressDialog1 = new ProgressDialog(getActivity());
                                                addProject(items.get(position).getProject_id());

                                            } else {
                                                final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(getActivity());
                                                alertDialog.setMessage("Please check your Internet Connection");
                                                alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.cancel();
                                                    }
                                                });
                                                alertDialog.show();
                                            }

                                        }else if ((ShareprefreancesUtility.getInstance().getString("kyc_status")).equalsIgnoreCase("complete"))
                                        {
                                            showKYCDialogNew();
                                        }
                                        else if ((ShareprefreancesUtility.getInstance().getString("kyc_status")).equalsIgnoreCase("complete")){
                                             showKYCDialogRejected();
                                        }
                                        else
                                        {
                                            showKYCDialog();
                                        }
                                    }
                                    else
                                    {
                                        showRegistrationDialog();
                                    }
                                }catch (Exception e){}

                                /*if (InternetConnection.checkInternetConnectivity()) {

                                    progressDialog1 = new ProgressDialog(getActivity());
                                    addProject(items.get(position).getProject_id());

                                } else {
                                    final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(getActivity());
                                    alertDialog.setMessage("You Dont Have An Active Internet Connection");
                                    alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                                    alertDialog.show();
                                }
*/

                            }
                        });


                    } else {
                        view.apply_rv.setEnabled(false);
                        view.apply_rv.setText("Closed");
                    }

                    view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Log.e("aobsales", "i am here");

                            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                                return;
                            }
                            mLastClickTime = SystemClock.elapsedRealtime();
                            Intent intent = new Intent(ctx, ProjectDescriptionActivity.class);

                            intent.putExtra("project_id", items.get(position).getProject_id());
                            intent.putExtra("project_name", items.get(position).getProject_name());
                            intent.putExtra("project_manager", items.get(position).getProject_manager());
                            ctx.startActivity(intent);

                        }

                    });
                    view.call.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 1);
                            } else {
                                Intent intent = new Intent("android.intent.action.CALL");
                                intent.setData(Uri.parse("tel:+91"+items.get(position).getProject_manager()));
                                startActivity(intent);
                            }
                        }
                    });

                    view.share_image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (SystemClock.elapsedRealtime() - mLastClickTime13 < 3000) {
                                return;
                            }
                            mLastClickTime13 = SystemClock.elapsedRealtime();

                            // ShareData.shareItem(EventsDetailActivity.this, getIntent().getIntExtra("image", 0),((TextView)findViewById(R.id.name)).getText().toString()+"\n"+((TextView)findViewById(R.id.datentime)).getText().toString()+"\n\nVenue\n"+((TextView)findViewById(R.id.address)).getText().toString()+"\nContact No.\t"+Mobile.get(contactRecyclerAdapter.getItemCount()-1)+"\n\n"+((TextView)findViewById(R.id.about)).getText().toString()+"\n\nTo install MyKahani app click below link\n"+"https://play.google.com/store/apps/details?id="+ getPackageName());
                            try {
                                final String appPackageName = getActivity().getPackageName(); // getPackageName() from Context or Activity object
                                if (items.get(position).getSales_type().size() == 1) {
                                    if (items.get(position).getSales_type().get(0).equalsIgnoreCase("sale")){

                                        String cityList = items.get(position).getSales_type().get(0);
                                        ShareData.shareItem(getActivity(), items.get(position).getLogo_url(), "\n\n"
                                                + items.get(position).getProject_name() + "\n" + items.get(position).getProjectTitle()
                                                + "\n\nPrice of product: " + items.get(position).getPrice() + "\nCommission: " + items.get(position).getSale_commission_freelancer()
                                                + "\n\nViews: " + items.get(position).getViews() + "\nActive Salesmen: " + items.get(position).getSalesman_working_on()
                                                + "\nTotal Incentive Earned: " + items.get(position).getIncentive_earned_total() + "\nNature of project: " + cityList
                                                + "\n\nTo install the AOB Salesman App click the link below & get \u20B9 500 as registration bonus. \n"
                                                + "https://play.google.com/store/apps/details?id=" + appPackageName
                                                +"\n\nReferral Code: "+ShareprefreancesUtility.getInstance().getString("mobile"));
                                    }else {
                                        String cityList = items.get(position).getSales_type().get(0);
                                        ShareData.shareItem(getActivity(), items.get(position).getLogo_url(), "\n\n"
                                                + items.get(position).getProject_name() + "\n" + items.get(position).getProjectTitle()
                                                + "\n\nPrice of product: " + items.get(position).getPrice() + "\nCommission: " + items.get(position).getLead_commission_freelancer()
                                                + "\n\nViews: " + items.get(position).getViews() + "\nActive Salesmen: " + items.get(position).getSalesman_working_on()
                                                + "\nTotal Incentive Earned: " + items.get(position).getIncentive_earned_total() + "\nNature of project: " + cityList
                                                + "\n\nTo install the AOB Salesman App click the link below & get \u20B9 500 as registration bonus. \n"
                                                + "https://play.google.com/store/apps/details?id=" + appPackageName
                                                +"\n\nReferral Code: "+ShareprefreancesUtility.getInstance().getString("mobile"));

                                    }
                                       } else if (items.get(position).getSales_type().size() == 2){
                                          String cityList = items.get(position).getSales_type().get(0)+"\n"+ items.get(position).getSales_type().get(0);
                                          ShareData.shareItem(getActivity(), items.get(position).getLogo_url(), "\n\n"
                                            + items.get(position).getProject_name() + "\n" + items.get(position).getProjectTitle()
                                            + "\n\nPrice of product: " + items.get(position).getPrice() + "\nCommission Sale: " + items.get(position).getSale_commission_freelancer()+ "\nCommission Lead: " + items.get(position).getLead_commission_freelancer()
                                            + "\n\nViews: " + items.get(position).getViews() + "\nActive Salesmen: " + items.get(position).getSalesman_working_on()
                                            + "\nTotal Incentive Earned: " + items.get(position).getIncentive_earned_total() + "\nNature of project: " + cityList
                                            + "\n\nTo install the AOB Salesman App click the link below & get \u20B9 500 as registration bonus. \n"
                                            + "https://play.google.com/store/apps/details?id=" + appPackageName
                                            +"\n\nReferral Code: "+ShareprefreancesUtility.getInstance().getString("mobile"));

                                }
                            }catch (Exception e){}

                        }
                    });

                }catch (Exception e){

                    Log.e("aobsales",e.toString());
                }
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        @Override
        public Filter getFilter() {
            return mFilter;
        }

        public class CustomFilter extends Filter {
            private AllProjectAdapter mAdapter;
            private CustomFilter(AllProjectAdapter mAdapter) {
                super();
                this.mAdapter = mAdapter;
            }
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                Log.e("aobsales","checkfilter "+ChecKFilter);

                if(ChecKFilter.equalsIgnoreCase("inactive"))
                {
                filteredList.clear();
                final FilterResults results = new FilterResults();

                if(CheckSort.equalsIgnoreCase("none")) {
                    sortArrayList.clear();
                    if (constraint.length() == 0) {
                        filteredList.addAll(company_data);
                        sortArrayList.addAll(company_data);
                    } else {
                        final String filterPattern = constraint.toString().toLowerCase().trim();
                        for (final Company_Data mWords : company_data) {
                            if (mWords.getProject_name().toLowerCase().startsWith(filterPattern)) {
                                filteredList.add(mWords);
                                sortArrayList.add(mWords);
                            }
                        }
                    }
                }
                else   if(CheckSort.equalsIgnoreCase("name"))
                {

                    if (constraint.length() == 0) {

                        sortArrayList.clear();
                        sortArrayList.addAll(company_data);
                        Collections.sort(sortArrayList, new Comparator<Company_Data>() {
                            @Override
                            public int compare(Company_Data lhs, Company_Data rhs) {
                                return lhs.getProject_name().compareTo(rhs.getProject_name());

                            }
                        });
                        filteredList.addAll(sortArrayList);
                    } else {

                        sortArrayList.clear();
                        sortArrayList.addAll(company_data);
                        Collections.sort(sortArrayList, new Comparator<Company_Data>() {
                            @Override
                            public int compare(Company_Data lhs, Company_Data rhs) {
                                return lhs.getProject_name().compareTo(rhs.getProject_name());

                            }
                        });
                        final String filterPattern = constraint.toString().toLowerCase().trim();
                        for (final Company_Data mWords : sortArrayList) {
                            if (mWords.getProject_name().toLowerCase().startsWith(filterPattern)) {
                                filteredList.add(mWords);
                            }
                        }
                        sortArrayList.clear();
                        sortArrayList.addAll(filteredList);
                    }

                } else   if(CheckSort.equalsIgnoreCase("pricehightolow")) {

                    if (constraint.length() == 0) {

                        sortArrayList.clear();
                        sortArrayList.addAll(company_data);
                        Collections.sort(sortArrayList, new Comparator<Company_Data>() {
                            @Override
                            public int compare(Company_Data lhs, Company_Data rhs) {
                                return rhs.getIncentive()-(lhs.getIncentive());

                            }
                        });
                        filteredList.addAll(sortArrayList);
                    } else {

                        sortArrayList.clear();
                        sortArrayList.addAll(company_data);
                        Collections.sort(sortArrayList, new Comparator<Company_Data>() {
                            @Override
                            public int compare(Company_Data lhs, Company_Data rhs) {
                                 return rhs.getIncentive()-(lhs.getIncentive());

                            }
                        });
                        final String filterPattern = constraint.toString().toLowerCase().trim();
                        for (final Company_Data mWords : sortArrayList) {
                            if (mWords.getProject_name().toLowerCase().startsWith(filterPattern)) {
                                filteredList.add(mWords);
                            }
                        }
                        sortArrayList.clear();
                        sortArrayList.addAll(filteredList);
                    }

                }
                else  if(CheckSort.equalsIgnoreCase("pricelowtohigh"))
                {

                    if (constraint.length() == 0) {

                        sortArrayList.clear();
                        sortArrayList.addAll(company_data);
                        Collections.sort(sortArrayList, new Comparator<Company_Data>() {
                            @Override
                            public int compare(Company_Data lhs, Company_Data rhs) {
                                return lhs.getIncentive()-(rhs.getIncentive());

                            }
                        });
                        filteredList.addAll(sortArrayList);
                    } else {

                        sortArrayList.clear();
                        sortArrayList.addAll(company_data);
                        Collections.sort(sortArrayList, new Comparator<Company_Data>() {
                            @Override
                            public int compare(Company_Data lhs, Company_Data rhs) {
                                return lhs.getIncentive()-(rhs.getIncentive());

                            }
                        });
                        final String filterPattern = constraint.toString().toLowerCase().trim();
                        for (final Company_Data mWords : sortArrayList) {
                            if (mWords.getProject_name().toLowerCase().startsWith(filterPattern)) {
                                filteredList.add(mWords);
                            }
                        }
                        sortArrayList.clear();
                        sortArrayList.addAll(filteredList);
                    }

                }

                results.values = filteredList;
                results.count = filteredList.size();
                    Log.e("aobsales","hello "+filteredList.size());
                return results;

                }
                else
                {

                    filteredList.clear();
                    final FilterResults results = new FilterResults();

                    if(CheckSort.equalsIgnoreCase("none")) {
                        sortArrayList.clear();
                        if (constraint.length() == 0) {
                            filteredList.addAll(filterArrayList);
                            sortArrayList.addAll(filterArrayList);
                        } else {
                            final String filterPattern = constraint.toString().toLowerCase().trim();
                            for (final Company_Data mWords : filterArrayList) {
                                if (mWords.getProject_name().toLowerCase().startsWith(filterPattern)) {
                                    filteredList.add(mWords);
                                    sortArrayList.add(mWords);
                                }
                            }
                        }
                    }else   if(CheckSort.equalsIgnoreCase("name"))
                    {


                        if (constraint.length() == 0) {

                            sortArrayList.clear();
                            sortArrayList.addAll(company_data);
                            Collections.sort(sortArrayList, new Comparator<Company_Data>() {
                                @Override
                                public int compare(Company_Data lhs, Company_Data rhs) {
                                    return lhs.getProject_name().compareTo(rhs.getProject_name());

                                }
                            });
                            filteredList.addAll(sortArrayList);
                        } else {

                            sortArrayList.clear();
                            sortArrayList.addAll(company_data);
                            Collections.sort(sortArrayList, new Comparator<Company_Data>() {
                                @Override
                                public int compare(Company_Data lhs, Company_Data rhs) {
                                    return lhs.getProject_name().compareTo(rhs.getProject_name());

                                }
                            });
                            final String filterPattern = constraint.toString().toLowerCase().trim();
                            for (final Company_Data mWords : sortArrayList) {
                                if (mWords.getProject_name().toLowerCase().startsWith(filterPattern)) {
                                    filteredList.add(mWords);
                                }
                            }
                            sortArrayList.clear();
                            sortArrayList.addAll(filteredList);
                        }

                    }
                    else   if(CheckSort.equalsIgnoreCase("pricehightolow"))
                    {


                        if (constraint.length() == 0) {

                            sortArrayList.clear();
                            sortArrayList.addAll(filterArrayList);
                            Collections.sort(sortArrayList, new Comparator<Company_Data>() {
                                @Override
                                public int compare(Company_Data lhs, Company_Data rhs) {
                                    return rhs.getIncentive()-(lhs.getIncentive());

                                }
                            });
                            filteredList.addAll(sortArrayList);
                        } else {

                            sortArrayList.clear();
                            sortArrayList.addAll(filterArrayList);
                            Collections.sort(sortArrayList, new Comparator<Company_Data>() {
                                @Override
                                public int compare(Company_Data lhs, Company_Data rhs) {
                                    return rhs.getIncentive()-(lhs.getIncentive());

                                }
                            });
                            final String filterPattern = constraint.toString().toLowerCase().trim();
                            for (final Company_Data mWords : sortArrayList) {
                                if (mWords.getProject_name().toLowerCase().startsWith(filterPattern)) {
                                    filteredList.add(mWords);
                                }
                            }
                            sortArrayList.clear();
                            sortArrayList.addAll(filteredList);
                        }

                    }
                    else  if(CheckSort.equalsIgnoreCase("pricelowtohigh"))
                    {


                        if (constraint.length() == 0) {

                            sortArrayList.clear();
                            sortArrayList.addAll(filterArrayList);
                            Collections.sort(sortArrayList, new Comparator<Company_Data>() {
                                @Override
                                public int compare(Company_Data lhs, Company_Data rhs) {
                                    return lhs.getIncentive()-(rhs.getIncentive());

                                }
                            });
                            filteredList.addAll(sortArrayList);
                        } else {

                            sortArrayList.clear();
                            sortArrayList.addAll(filterArrayList);
                            Collections.sort(sortArrayList, new Comparator<Company_Data>() {
                                @Override
                                public int compare(Company_Data lhs, Company_Data rhs) {
                                    return lhs.getIncentive()-(rhs.getIncentive());

                                }
                            });
                            final String filterPattern = constraint.toString().toLowerCase().trim();
                            for (final Company_Data mWords : sortArrayList) {
                                if (mWords.getProject_name().toLowerCase().startsWith(filterPattern)) {
                                    filteredList.add(mWords);
                                }
                            }
                            sortArrayList.clear();
                            sortArrayList.addAll(filteredList);
                        }

                    }
                    results.values = filteredList;
                    results.count = filteredList.size();
                    return results;

                }

            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                active = constraint.length() != 0;
                this.mAdapter.notifyDataSetChanged();
            }
        }



    }



    private void showSortDialog()
    {

        Log.e("aobsales","sort all ");
     /*   android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.sort_dialog, null);
        dialogBuilder.setView(dialogView);

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setTitle("Sort By");


        alertDialog.show();
*/

        final AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.sort_dialog, null);
        builder1.setView(dialogView);
        builder1.setTitle("Sort By");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Done",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "Clear",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if (CheckSort.equalsIgnoreCase("none")) {

                            dialog.cancel();

                        }else if(CheckSort.equalsIgnoreCase("name"))
                        {


                            Log.e("aobsales","i am here1");

                            if(active == true) {

                                Log.e("aobsales","i am here2");
                                filteredList.clear();
                                Collections.reverse(sortArrayList);
                                filteredList.addAll(sortArrayList);
                                CheckSort = "none";
                                companyDataAdapter.notifyDataSetChanged();
                                dialog.cancel();

                            }else {

                                Log.e("aobsales","i am here3");

                                filteredList.clear();
                                filteredList.addAll(company_data);
                                CheckSort = "none";
                                companyDataAdapter.notifyDataSetChanged();
                                dialog.cancel();

                            }

                        }else if(CheckSort.equalsIgnoreCase("pricehightolow"))
                        {

                            if(active == true) {

                                filteredList.clear();
                                //Collections.reverse(sortArrayList);

                                Collections.sort(sortArrayList, new Comparator<Company_Data>() {
                                    @Override
                                    public int compare(Company_Data lhs, Company_Data rhs) {
                                        return lhs.getProject_name().compareTo(rhs.getProject_name());

                                    }
                                });

                                filteredList.addAll(sortArrayList);
                                CheckSort = "none";
                                companyDataAdapter.notifyDataSetChanged();
                                dialog.cancel();

                            }else {


                                filteredList.clear();
                                filteredList.addAll(company_data);
                                CheckSort = "none";
                                companyDataAdapter.notifyDataSetChanged();
                                dialog.cancel();

                            }


                        }else if(CheckSort.equalsIgnoreCase("pricelowtohigh"))
                        {

                            if(active == true) {

                                filteredList.clear();
                                //Collections.reverse(sortArrayList);

                                Collections.sort(sortArrayList, new Comparator<Company_Data>() {
                                    @Override
                                    public int compare(Company_Data lhs, Company_Data rhs) {
                                        return lhs.getProject_name().compareTo(rhs.getProject_name());

                                    }
                                });

                                filteredList.addAll(sortArrayList);
                                CheckSort = "none";
                                companyDataAdapter.notifyDataSetChanged();
                                dialog.cancel();

                            }else {



                                filteredList.clear();
                                filteredList.addAll(company_data);
                                CheckSort = "none";
                                companyDataAdapter.notifyDataSetChanged();
                                dialog.cancel();

                            }
                        }



                    }
                });


        RadioGroup radioGroup = dialogView.findViewById(R.id.radio);


        if(CheckSort.equalsIgnoreCase("name"))
        {
            radioGroup.check(R.id.radio_name);

        }else if(CheckSort.equalsIgnoreCase("pricehightolow"))
        {
            radioGroup.check(R.id.radio_price);

        }
        else if(CheckSort.equalsIgnoreCase("pricelowtohigh"))
        {
            radioGroup.check(R.id.radio_pricelowtohigh);

        }else if(CheckSort.equalsIgnoreCase("none"))
        {

        }



        final AlertDialog alert11 = builder1.create();
        alert11.show();



        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...

                if(checkedRadioButton.getText().toString().equalsIgnoreCase("Incentive Earned (High to Low)"))
                {
                   /* Collections.sort(filteredList, new Comparator<Company_Data>() {
                        @Override
                        public int compare(Company_Data lhs, Company_Data rhs) {
                            // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                            return lhs.getIncentive() > rhs.getIncentive() ? -1 : (lhs.customInt < rhs.customInt ) ? 1 : 0;
                        }
                    });*/


                    Collections.sort(sortArrayList, new Comparator<Company_Data>() {
                        @Override
                        public int compare(Company_Data lhs, Company_Data rhs) {
                            return rhs.getIncentive_earned_total()- lhs.getIncentive_earned_total();

                        }
                    });


                    filteredList.clear();
                    filteredList.addAll(sortArrayList);
                    CheckSort = "pricehightolow";
                    companyDataAdapter.notifyDataSetChanged();
                    alert11.dismiss();


                }
                if(checkedRadioButton.getText().toString().equalsIgnoreCase("Incentive Earned (Low to High)"))
                {

  /* Collections.sort(filteredList, new Comparator<Company_Data>() {
                        @Override
                        public int compare(Company_Data lhs, Company_Data rhs) {
                            // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                            return lhs.getIncentive() > rhs.getIncentive() ? -1 : (lhs.customInt < rhs.customInt ) ? 1 : 0;
                        }
                    });*/


                    Collections.sort(sortArrayList, new Comparator<Company_Data>() {
                        @Override
                        public int compare(Company_Data lhs, Company_Data rhs) {
                            return lhs.getIncentive_earned_total()- rhs.getIncentive_earned_total();

                        }
                    });

                   // Log.e("aobsales","hello "+sortArrayList.get(0).getIncentive());
//                    Log.e("aobsales","hello "+sortArrayList.get(1).getIncentive());

                    filteredList.clear();
                    filteredList.addAll(sortArrayList);
                    CheckSort = "pricelowtohigh";
                    companyDataAdapter.notifyDataSetChanged();
                    alert11.dismiss();

                }
                else if(checkedRadioButton.getText().toString().equalsIgnoreCase("Name (Alphabetically A - Z)"))
                {


                    Collections.sort(sortArrayList, new Comparator<Company_Data>() {
                        @Override
                        public int compare(Company_Data lhs, Company_Data rhs) {
                            return lhs.getProject_name().compareTo(rhs.getProject_name());

                        }
                    });
                    filteredList.clear();
                    filteredList.addAll(sortArrayList);
                    CheckSort = "name";
                    companyDataAdapter.notifyDataSetChanged();
                    alert11.dismiss();


                }


            }
        });



    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000 && resultCode == Activity.RESULT_OK) {
            try {
                Bundle extras = data.getExtras();

                filter_industry = extras.getString("resultindustry");
                filter_state = extras.getString("resultcity");


                if(TextUtils.isEmpty(filter_industry) && TextUtils.isEmpty(filter_state)) {


                    findustrydata.clear();
                    fstatedata.clear();
                    ChecKFilter = "inactive";
                    filteredList.clear();
                    filteredList.addAll(company_data);
                    sortArrayList.clear();
                    sortArrayList.addAll(company_data);
                    filterArrayList.clear();
                    CheckSort = "none";
                    companyDataAdapter.notifyDataSetChanged();

                }
                else {
                        findustrydata.clear();
                        fstatedata.clear();

                        if (!TextUtils.isEmpty(filter_industry)) {
                            findustrydata = new ArrayList(Arrays.asList(filter_industry.split(",")));
                        }


                        if (!TextUtils.isEmpty(filter_state)) {
                            fstatedata = new ArrayList(Arrays.asList(filter_state.split(",")));
                        }


                        filterData();
                    }

            }catch (Exception e)
                {}
        }
    }

    public void filterData()
    {

        Log.e("aobsales","lp "+fstatedata);

        ChecKFilter = "active";

        if(CheckSort.equalsIgnoreCase("none")) {


            try {
                if (findustrydata.size() > 0) {
                    for (String c : findustrydata) {

                        for (Company_Data obj : company_data) {

                            for (String obj1 : obj.getIndustries()) {

                                if (c.trim().equalsIgnoreCase(obj1.trim())) {

                                    if(!filterArrayList.contains(obj)) {
                                        filterArrayList.add(obj);
                                    }

                                }

                            }
                        }
                    }
                }

                if (fstatedata.size() > 0) {
                    for (String c : fstatedata) {

                        for (Company_Data obj : company_data) {

                            for (String obj1 : obj.getCity()) {

                               // Log.e("aobsales","city "+obj.getProject_name());
                               // Log.e("aobsales","city "+obj1);
                              //  Log.e("aobsales","city2 "+c);

                                if (c.trim().equalsIgnoreCase(obj1.trim())) {

                                    //Log.e("aobsales","true"+obj1 );
                                    //Log.e("aobsales","true"+obj1 );
                                        if(!filterArrayList.contains(obj)) {
                                            filterArrayList.add(obj);
                                        }

                                }

                            }
                        }
                    }
                }



                    Log.e("aobsales","yo man "+filterArrayList.size());
                    Log.e("aobsales","yo man "+filterArrayList.size());
                    filteredList.clear();
                    sortArrayList.clear();
                    filteredList.addAll(filterArrayList);
                    sortArrayList.addAll(filterArrayList);
                    companyDataAdapter.notifyDataSetChanged();

            }catch (Exception e){}
        }
       /* else
        {

            try {
                if (findustrydata.size() > 0) {
                    for (String c : findustrydata) {

                        for (Company_Data obj : company_data) {

                            for (String obj1 : obj.getIndustries()) {

                                if (c.equalsIgnoreCase(obj1)) {

                                    Log.e("aobsales", "pop " + obj1);
                                    Log.e("aobsales", "pop " + obj.getProject_name());

                                    filterArrayList.add(obj);

                                }

                            }
                        }
                    }
                }

                if (fstatedata.size() > 0) {
                    for (String c : fstatedata) {

                        for (Company_Data obj : company_data) {

                            for (String obj1 : obj.getState()) {

                                if (c.equalsIgnoreCase(obj1)) {

                                    filterArrayList.add(obj);

                                }

                            }
                        }
                    }
                }

                if(filterArrayList.size() >0)
                {
                    filteredList.clear();
                    filteredList.addAll(filterArrayList);
                    companyDataAdapter.notifyDataSetChanged();
                }

            }catch (Exception e){}
        }*/

    }



    private void addProject(final String projectid){
        progressDialog1.setMessage("Please wait...");
        progressDialog1.setCancelable(false);
        progressDialog1.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                getResources().getString(R.string.hostName)+"/app/sales/v1_0/apply_project_add.php?",
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {

                        Log.e("aobsales","response success "+ response);

                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String success=jsonObject.getString("status");

                            if (success.equals("0")) {
                                final AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                                LayoutInflater inflater = (getActivity()).getLayoutInflater();
                                View dialogView = inflater.inflate(R.layout.sucess_dialog, null);
                                builder1.setView(dialogView);
                                builder1.setCancelable(false);
                                builder1.setPositiveButton(
                                        "Ok",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                                dialog.cancel();
                                                Intent intent=new Intent(getActivity(), HomeActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                                        Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                                 }
                                        });
                                AlertDialog alert2 = builder1.create();
                                alert2.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                                alert2.show();


                            }
                            else if (success.equals("1")){
                                AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                                builder2.setMessage("Something went wrong!");
                                builder2.setCancelable(true);

                                builder2.setNegativeButton(
                                        "Cancel",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                                dialog.cancel();

                                            }
                                        });

                                AlertDialog alert2 = builder2.create();
                                alert2.show();

                            }


                        } catch (JSONException e) {
                            final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(getActivity());
                            alertDialog.setMessage("Retry with Internet connection");
                            alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            alertDialog.show();
                        }

                        progressDialog1.dismiss();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog1.dismiss();
                final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(getActivity());
                alertDialog.setTitle("Oops! Something went wrong.");
                alertDialog.setMessage("This page didn't load correctly.\nPlease try again.");
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
                    params.put("project_id",projectid);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);

    }

    public void showKYCDialogNew()
    {

        AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
        builder2.setMessage("Verification in process. Please wait.");
        builder2.setCancelable(true);
        builder2.setTitle("KYC is pending for Approval");
        builder2.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        /*Intent i = new Intent(ProjectDescriptionActivity.this, KYCActivity.class);
                        startActivity(i);*/

                        dialog.cancel();

                    }
                });
        builder2.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();

                    }
                });

        AlertDialog alert2 = builder2.create();
        alert2.show();

    }


    private void myUpdateOperationSwipe() {
        if( company_data != null) {

            searchBox.setText("");
            company_data.clear();
            filteredList.clear();
            sortArrayList.clear();
            filterArrayList.clear();
            filter_state = "";
            filter_industry = "";

            CheckSort = "none";
            ChecKFilter = "inactive";

            companyDataAdapter.notifyDataSetChanged();
            getProjectMain();

        }
    }




    private void getProjectMain(){

        swiperefresh_allprojects.setRefreshing(true);

        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                getResources().getString(R.string.hostName)+"/app/sales/v1_0/refresh_all_project.php?",
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {

                        Log.e("aobsales","reesponserefresh "+ response);

                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String success=jsonObject.getString("status");

                            if (success.equals("0")){

                                JSONArray jsonArrayall = jsonObject.getJSONArray("all_project");
                                if(jsonArrayall.length() >0)
                                {
                                       String  response1 = jsonArrayall.toString();
                                       company_data = new ArrayList<Company_Data>(Arrays.asList(gson.fromJson(response1, Company_Data[].class)));
                                       filteredList.addAll(company_data);
                                       sortArrayList.addAll(company_data);
                                       companyDataAdapter.notifyDataSetChanged();

                                    recyclerView_all.setVisibility(View.VISIBLE);
                                    tv_no_data_lead.setVisibility(View.INVISIBLE);

                                }
                                else
                                {
                                    recyclerView_all.setVisibility(View.INVISIBLE);
                                    tv_no_data_lead.setVisibility(View.VISIBLE);

                                }




                            }
                            else if (success.equals("1")){



                            }


                        } catch (JSONException e) {
                            final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(getActivity());
                            alertDialog.setMessage("Retry with Internet connection");
                            alertDialog.setCancelable(false);
                            alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    getActivity().finish();
                                }
                            });
                            alertDialog.setPositiveButton(
                                    "Retry",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            myUpdateOperationSwipe();
                                            dialog.cancel();
                                        }
                                    });
                            alertDialog.show();
                        }

                        swiperefresh_allprojects.setRefreshing(false);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swiperefresh_allprojects.setRefreshing(false);
                final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(getActivity());
                alertDialog.setTitle("Oops! Something went wrong.");
                alertDialog.setMessage("This page didn't load correctly.\nPlease try again.");
                alertDialog.setCancelable(false);
                alertDialog.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        getActivity().finish();
                    }
                });
                alertDialog.setPositiveButton(
                        "Retry",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                myUpdateOperationSwipe();
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

}
