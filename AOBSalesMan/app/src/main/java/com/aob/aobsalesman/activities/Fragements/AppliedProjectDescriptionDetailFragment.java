package com.aob.aobsalesman.activities.Fragements;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.aob.aobsalesman.R;
import com.aob.aobsalesman.activities.activities.ActivityRegisterLead;
import com.aob.aobsalesman.activities.activities.ActivitySalesLead;
import com.aob.aobsalesman.activities.activities.AppliedDescriptionActivity;
import com.aob.aobsalesman.activities.activities.VideoPlayerActivity;
import com.aob.aobsalesman.activities.activities.ViewerActivity;
import com.aob.aobsalesman.activities.utility.InternetConnection;
import com.aob.aobsalesman.activities.utility.MySingleton;
import com.aob.aobsalesman.activities.utility.ShareData;
import com.aob.aobsalesman.activities.utility.ShareprefreancesUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class AppliedProjectDescriptionDetailFragment extends Fragment {

    View view;
    JSONObject jsonObject1;

    TextView project_description, project_incentive, total_incentive, project_criteria, project_status,
            project_no_salesman, project_cities, project_industries, head_project;

    TextView product_name, price_project, project_offers, businesstype_b2b, b2b_category, b2b_industries,
            age_group, marital_status, b2c_gender, b2c_interested, sales_criteria,
            lead_criteria, sales_Commission, lead_Commission, in_30_days, in_60_days, in_90_days, in_30_days1, in_60_days1, in_90_days1, product_link;

    LinearLayout b2b_linear, b2c_linear, sales_criteria_linear, lead_criteria_linear, SalesCommission_layout, LeadCommission_layout, Expected_Sales_linear, Expected_Lead_linear,
            mcm_criteria_linear, sm_linear, training_linear, product_link_linear;

    TextView ppt, brochure, video, fb, linkedin, insta, train_ppt, train_brochure, train_video;


    private ProgressDialog progressDialog;

    SwipeRefreshLayout swiperefresh_desc3;


    @SuppressLint("HardwareIds")
    String android_id;

    String id_project = "";

    Button desc_apply;
    private ProgressDialog progressdialog1;

    long mLastClickTime20 = 0;
    long mLastClickTime21 = 0;
    private long mLastClickTime13 = 0;

    TextView register_sale;
    TextView register_lead;
    TextView register_close;
    RelativeLayout Application_form;

    String send1 = "";
    String send2 = "";

    LinearLayout desc3_full;

    public AppliedProjectDescriptionDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_description3, container, false);
        initViews();


        try {

            android_id = Settings.Secure.getString(getContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);

        } catch (Exception e) {
        }


        if (InternetConnection.checkInternetConnectivity()) {

            progressDialog = new ProgressDialog(getActivity());


            getProject();

        } else {
            final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(getActivity());
            alertDialog.setMessage("Retry with Internet connection");
            alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
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


        initFunctionality();
        return view;
    }

    private void initViews() {

        desc3_full = view.findViewById(R.id.desc3_full);
        project_description = view.findViewById(R.id.project_description);

        project_incentive = view.findViewById(R.id.project_incentive);
        total_incentive = view.findViewById(R.id.total_incentive);
        project_status = view.findViewById(R.id.project_status);
        project_no_salesman = view.findViewById(R.id.project_no_salesman);
        project_cities = view.findViewById(R.id.project_cities);
        project_industries = view.findViewById(R.id.project_industries);
        head_project = view.findViewById(R.id.head_project);


        product_name = view.findViewById(R.id.product_name);
        price_project = view.findViewById(R.id.price_project);
        project_offers = view.findViewById(R.id.project_offers);
        businesstype_b2b = view.findViewById(R.id.businesstype_b2b);
        b2b_category = view.findViewById(R.id.b2b_category);
        b2b_industries = view.findViewById(R.id.b2b_industries);
        age_group = view.findViewById(R.id.age_group);
        marital_status = view.findViewById(R.id.marital_status);

        b2c_interested = view.findViewById(R.id.b2c_interested);


        product_link = view.findViewById(R.id.product_link);

        sales_criteria = view.findViewById(R.id.sales_criteria);
        lead_criteria = view.findViewById(R.id.lead_criteria);
        sales_Commission = view.findViewById(R.id.sales_Commission);
        lead_Commission = view.findViewById(R.id.lead_Commission);
        in_30_days = view.findViewById(R.id.in_30_days);
        in_60_days = view.findViewById(R.id.in_60_days);
        in_90_days = view.findViewById(R.id.in_90_days);
        in_30_days1 = view.findViewById(R.id.in_30_days1);
        in_60_days1 = view.findViewById(R.id.in_60_days1);
        in_90_days1 = view.findViewById(R.id.in_90_days1);

        b2c_gender = view.findViewById(R.id.b2c_gender);


        b2b_linear = view.findViewById(R.id.b2b_linear);
        b2c_linear = view.findViewById(R.id.b2c_linear);

        sales_criteria_linear = view.findViewById(R.id.SalesCriteria_layout);
        lead_criteria_linear = view.findViewById(R.id.LeadCriteria_layout);
        SalesCommission_layout = view.findViewById(R.id.SalesCommission_layout);
        LeadCommission_layout = view.findViewById(R.id.LeadCommission_layout);
        Expected_Sales_linear = view.findViewById(R.id.Expected_Sales_linear);
        Expected_Lead_linear = view.findViewById(R.id.Expected_Lead_linear);


        mcm_criteria_linear = view.findViewById(R.id.mcm_criteria_linear);
        sm_linear = view.findViewById(R.id.sm_linear);
        training_linear = view.findViewById(R.id.training_linear);

        ppt = view.findViewById(R.id.ppt);
        brochure = view.findViewById(R.id.brochure);
        fb = view.findViewById(R.id.fb);
        video = view.findViewById(R.id.video);
        linkedin = view.findViewById(R.id.linkedin);
        insta = view.findViewById(R.id.insta);

        train_ppt = view.findViewById(R.id.train_ppt);
        train_video = view.findViewById(R.id.train_video);
        train_brochure = view.findViewById(R.id.train_brochure);

        product_link_linear = view.findViewById(R.id.product_link_linear);


        progressDialog = new ProgressDialog(getActivity());


        Bundle bundle = this.getArguments();
        if (bundle != null) {


            id_project = bundle.getString("project_id");

        }


        register_lead = view.findViewById(R.id.register_lead);
        register_sale = view.findViewById(R.id.register_sale);
        register_close = view.findViewById(R.id.register_close);
        Application_form = view.findViewById(R.id.Application_form);

        swiperefresh_desc3 = view.findViewById(R.id.swiperefresh_desc3);

    }


    private void initFunctionality() {

        ((TextView) view.findViewById(R.id.register_sale)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime20 < 1000) {
                    return;
                }
                mLastClickTime20 = SystemClock.elapsedRealtime();

                Intent myIntent = new Intent(getActivity(), ActivitySalesLead.class);
                myIntent.putExtra("send1", send1);
                myIntent.putExtra("send2", send2);
                myIntent.putExtra("project_id", id_project);
                startActivity(myIntent);
            }
        });

        ((TextView) view.findViewById(R.id.register_lead)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SystemClock.elapsedRealtime() - mLastClickTime21 < 1000) {
                    return;
                }
                mLastClickTime21 = SystemClock.elapsedRealtime();

                Intent myIntent = new Intent(getActivity(), ActivityRegisterLead.class);
                myIntent.putExtra("project_id", id_project);
                startActivity(myIntent);
            }
        });


        ImageView share_image_apply1 = view.findViewById(R.id.share_image_apply1);

        share_image_apply1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SystemClock.elapsedRealtime() - mLastClickTime13 < 3000) {
                    return;
                }
                mLastClickTime13 = SystemClock.elapsedRealtime();

                // ShareData.shareItem(EventsDetailActivity.this, getIntent().getIntExtra("image", 0),((TextView)findViewById(R.id.name)).getText().toString()+"\n"+((TextView)findViewById(R.id.datentime)).getText().toString()+"\n\nVenue\n"+((TextView)findViewById(R.id.address)).getText().toString()+"\nContact No.\t"+Mobile.get(contactRecyclerAdapter.getItemCount()-1)+"\n\n"+((TextView)findViewById(R.id.about)).getText().toString()+"\n\nTo install MyKahani app click below link\n"+"https://play.google.com/store/apps/details?id="+ getPackageName());
                try {
                    final String appPackageName = getActivity().getPackageName(); // getPackageName() from Context or Activity object
                    JSONArray arraysalestype = jsonObject1.getJSONArray("sales_type");
                    if (arraysalestype.length() > 0) {
                        if (arraysalestype.length() == 1) {
                            String type = "" + arraysalestype.get(0);
                            if (type.equalsIgnoreCase("sale")) {
                                ShareData.shareItem(getActivity(), jsonObject1.getString("logo_url"), "\n\n"
                                        + jsonObject1.getString("product_name") + "\n" + jsonObject1.getString("project_title")
                                        + "\n\nPrice of product: " + jsonObject1.getString("price") + "\nCommission: " + jsonObject1.getString("sale_commission_freelancer")
                                        + "\n\nViews: " + jsonObject1.getString("views") + "\nActive Salesmen: " + jsonObject1.getString("salesman_working_on")
                                        + "\nTotal Incentive Earned: " + "Rs " + jsonObject1.getString("incentive_earned_total") + "\nNature of project: " + type
                                        + "\n\nTo install the AOB Salesman App click the link below & get \u20B9 500 as registration bonus. \n"
                                        + "https://play.google.com/store/apps/details?id=" + appPackageName
                                        + "\n\nReferral Code: " + ShareprefreancesUtility.getInstance().getString("mobile"));
                            } else {
                                ShareData.shareItem(getActivity(), jsonObject1.getString("logo_url"), "\n\n"
                                        + jsonObject1.getString("product_name") + "\n" + jsonObject1.getString("project_title")
                                        + "\n\nPrice of product: " + jsonObject1.getString("price") + "\nCommission: " + jsonObject1.getString("lead_commission_freelancer")
                                        + "\n\nViews: " + jsonObject1.getString("views") + "\nActive Salesmen: " + jsonObject1.getString("salesman_working_on")
                                        + "\nTotal Incentive Earned: " + "Rs " + jsonObject1.getString("incentive_earned_total") + "\nNature of project: " + type
                                        + "\n\nTo install the AOB Salesman App click the link below & get \u20B9 500 as registration bonus. \n"
                                        + "https://play.google.com/store/apps/details?id=" + appPackageName
                                        + "\n\nReferral Code: " + ShareprefreancesUtility.getInstance().getString("mobile"));

                            }

                        } else if (arraysalestype.length() == 2) {
                            String type = "" + arraysalestype.get(0) + "\n" + arraysalestype.get(1);
                            ShareData.shareItem(getActivity(), jsonObject1.getString("logo_url"), "\n\n"
                                    + jsonObject1.getString("product_name") + "\n" + jsonObject1.getString("project_title")
                                    + "\n\nPrice of product: " + jsonObject1.getString("price") + "\nCommission sale: " + jsonObject1.getString("sale_commission_freelancer") + "\nCommission Lead: " + jsonObject1.getString("lead_commission_freelancer")
                                    + "\n\nViews: " + jsonObject1.getString("views") + "\nActive Salesmen: " + jsonObject1.getString("salesman_working_on")
                                    + "\nTotal Incentive Earned: " + "Rs " + jsonObject1.getString("incentive_earned_total") + "\nNature of project: " + type
                                    + "\n\nTo install the AOB Salesman App click the link below & get \u20B9 500 as registration bonus. \n"
                                    + "https://play.google.com/store/apps/details?id=" + appPackageName
                                    + "\n\nReferral Code: " + ShareprefreancesUtility.getInstance().getString("mobile"));

                        }
                    }
                } catch (Exception e) {
                }

            }
        });


        swiperefresh_desc3.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myUpdateOperationSwipe();
            }
        });
    }

    private void getProject() {
        //progressDialog.setMessage("Please wait...");
        // progressDialog.setCancelable(false);
        //progressDialog.show();

        desc3_full.setVisibility(View.GONE);
        swiperefresh_desc3.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                getResources().getString(R.string.hostName) + "/app/sales/v1_0/project_details_data.php?",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("aobsales", "response_123 " + response.toString());


                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("status");

                            if (success.equals("0")) {


                                JSONArray jsonArrayall = jsonObject.getJSONArray("data");
                                if (jsonArrayall.length() > 0) {

                                    jsonObject1 = jsonArrayall.getJSONObject(0);
                                    head_project.setText(jsonObject1.getString("project_title"));
                                    project_description.setText(jsonObject1.getString("description"));

                                    if (project_description.getLineCount() > 3) {
                                        makeTextViewResizable(project_description, 4, "\n\nSee More", true);
                                    }
                                   /* JSONArray  arraycity = jsonObject1.getJSONArray("city");

                                    Log.e("aobsales","city "+arraycity);

                                    String city = ""+arraycity.get(0);
                                    if(arraycity.length() >1) {
                                        for (int i = 1; i < arraycity.length(); i++) {

                                            city = city + ", " + arraycity.get(i);
                                        }
                                    }*/

                                    product_name.setText(jsonObject1.getString("product_name"));
                                    price_project.setText(jsonObject1.getString("price"));
                                    project_offers.setText(jsonObject1.getString("offers"));
                                    project_industries.setText(jsonObject1.getString("website_link"));


                                    project_cities.setText(jsonObject1.getString("where_sell"));


                                    //project_incentive.setText("Rs "+jsonObject1.getString("incentive"));
                                    total_incentive.setText("Rs " + jsonObject1.getString("incentive_earned_total") + " earned");
                                    //project_criteria.setText(jsonObject1.getString("sales_criteria"));
                                    project_status.setText(jsonObject1.getString("project_status"));
                                    project_no_salesman.setText(jsonObject1.getString("salesman_working_on"));
                                    if (!TextUtils.isEmpty(jsonObject1.getString("app_form"))
                                            && !jsonObject1.getString("app_form").equalsIgnoreCase("null")
                                            && jsonObject1.getString("app_form") != null) {
                                        ((RelativeLayout) view.findViewById(R.id.Application_form)).setVisibility(View.VISIBLE);
                                        Application_form.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                try {
                                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(jsonObject1.getString("app_form"))));
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });

                                    } else {
                                        ((RelativeLayout) view.findViewById(R.id.Application_form)).setVisibility(View.GONE);
                                    }
                                    JSONArray arraysalestype = jsonObject1.getJSONArray("sales_type");
                                    if (arraysalestype.length() > 0) {
                                        if (arraysalestype.length() == 1) {
                                            String type = "" + arraysalestype.get(0);

                                            if (type.equalsIgnoreCase("Sale")) {
                                                sales_criteria_linear.setVisibility(View.VISIBLE);
                                                lead_criteria_linear.setVisibility(View.GONE);
                                                SalesCommission_layout.setVisibility(View.VISIBLE);
                                                LeadCommission_layout.setVisibility(View.GONE);
                                                Expected_Sales_linear.setVisibility(View.VISIBLE);
                                                Expected_Lead_linear.setVisibility(View.GONE);

                                                if (jsonObject1.getString("project_status").equalsIgnoreCase("active")) {
                                                    register_sale.setVisibility(View.VISIBLE);
                                                    register_lead.setVisibility(View.GONE);
                                                    register_close.setVisibility(View.GONE);
                                                } else {
                                                    register_sale.setVisibility(View.GONE);
                                                    register_lead.setVisibility(View.GONE);
                                                    register_close.setVisibility(View.VISIBLE);

                                                }


                                            } else if (type.equalsIgnoreCase("Lead Generation")) {
                                                lead_criteria_linear.setVisibility(View.VISIBLE);
                                                sales_criteria_linear.setVisibility(View.GONE);
                                                SalesCommission_layout.setVisibility(View.GONE);
                                                LeadCommission_layout.setVisibility(View.VISIBLE);
                                                Expected_Sales_linear.setVisibility(View.GONE);
                                                Expected_Lead_linear.setVisibility(View.VISIBLE);
                                                if (jsonObject1.getString("project_status").equalsIgnoreCase("active")) {
                                                    register_lead.setVisibility(View.VISIBLE);
                                                    register_sale.setVisibility(View.GONE);
                                                    register_close.setVisibility(View.GONE);
                                                } else {
                                                    register_lead.setVisibility(View.GONE);
                                                    register_sale.setVisibility(View.GONE);
                                                    register_close.setVisibility(View.VISIBLE);

                                                }

                                            }
                                        } else if (arraysalestype.length() == 2) {
                                            lead_criteria_linear.setVisibility(View.VISIBLE);
                                            sales_criteria_linear.setVisibility(View.VISIBLE);

                                            SalesCommission_layout.setVisibility(View.VISIBLE);
                                            LeadCommission_layout.setVisibility(View.VISIBLE);

                                            Expected_Sales_linear.setVisibility(View.VISIBLE);
                                            Expected_Lead_linear.setVisibility(View.VISIBLE);
                                            if (jsonObject1.getString("project_status").equalsIgnoreCase("active")) {
                                                register_lead.setVisibility(View.VISIBLE);
                                                register_sale.setVisibility(View.VISIBLE);
                                                register_sale.setVisibility(View.GONE);
                                            } else {
                                                register_lead.setVisibility(View.GONE);
                                                register_sale.setVisibility(View.GONE);
                                                register_sale.setVisibility(View.VISIBLE);

                                            }

                                        }

                                    }

                                    try {
                                        if (!TextUtils.isEmpty(jsonObject1.getString("demo_link"))
                                                && !jsonObject1.getString("demo_link").equalsIgnoreCase("null")
                                                && jsonObject1.getString("demo_link") != null
                                        ) {
                                            product_link_linear.setVisibility(View.VISIBLE);
                                            product_link.setText(jsonObject1.getString("demo_link"));

                                        } else {
                                            product_link_linear.setVisibility(View.GONE);
                                        }
                                    } catch (Exception e) {
                                    }

                                    sales_criteria.setText(jsonObject1.getString("sale_criteria"));

                                    /*commision_sale_money.setText(jsonObject1.getString("sale_com_money"));
                                    commision_sale_volume.setText(jsonObject1.getString("sale_com_volume"));*/
                                    lead_criteria.setText(jsonObject1.getString("lead_criteria"));

                                    sales_Commission.setText(jsonObject1.getString("sale_commission_freelancer"));
                                    lead_Commission.setText(jsonObject1.getString("lead_commission_freelancer"));
                                    in_30_days.setText(jsonObject1.getString("expected_sale_freelancer_30"));
                                    in_60_days.setText(jsonObject1.getString("expected_sale_freelancer_60"));
                                    in_90_days.setText(jsonObject1.getString("expected_sale_freelancer_90"));
                                    in_30_days1.setText(jsonObject1.getString("expected_lead_freelancer_30"));
                                    in_60_days1.setText(jsonObject1.getString("expected_lead_freelancer_60"));
                                    in_90_days1.setText(jsonObject1.getString("expected_lead_freelancer_90"));

                                  /*commision_lead_money.setText(jsonObject1.getString("lead_com_money"));
                                    commision_lead_volume.setText(jsonObject1.getString("lead_com_volume"));*/


                                    JSONArray businessmodel = jsonObject1.getJSONArray("business_model");
                                    if (businessmodel.length() > 0) {

                                        if (businessmodel.length() == 1) {
                                            String type1 = "" + businessmodel.get(0);

                                            if (type1.equalsIgnoreCase("B2C")) {
                                                b2c_linear.setVisibility(View.VISIBLE);
                                                b2b_linear.setVisibility(View.GONE);
                                                send1 = type1;
                                            } else {
                                                b2b_linear.setVisibility(View.VISIBLE);
                                                b2c_linear.setVisibility(View.GONE);
                                                send1 = type1;
                                            }
                                        } else if (businessmodel.length() == 2) {
                                            b2b_linear.setVisibility(View.VISIBLE);
                                            b2c_linear.setVisibility(View.GONE);

                                            send1 = "" + businessmodel.get(0);
                                            send2 = "" + businessmodel.get(1);

                                        }

                                    }


                                    businesstype_b2b.setText(jsonObject1.getString("business_category"));
                                    JSONArray arrayindustries = jsonObject1.getJSONArray("industries");
                                    String industries = "" + arrayindustries.get(0);
                                    if (arrayindustries.length() > 1) {
                                        for (int i = 1; i < arrayindustries.length(); i++) {

                                            industries = industries + ", " + arrayindustries.get(i);
                                        }
                                    }

                                    b2b_industries.setText(industries);

                                    b2b_category.setText(jsonObject1.getString("business_category"));


                                    age_group.setText(jsonObject1.getString("age_group"));
                                    marital_status.setText(jsonObject1.getString("marital_status"));
                                    b2c_gender.setText(jsonObject1.getString("gender"));
                                    b2c_interested.setText(jsonObject1.getString("interested_in"));


                                    if (!TextUtils.isEmpty(jsonObject1.getString("facebook"))
                                            && !jsonObject1.getString("facebook").equalsIgnoreCase("null")
                                            && jsonObject1.getString("facebook") != null
                                    ) {

                                        final String link = jsonObject1.getString("facebook");
                                        fb.setVisibility(View.VISIBLE);
                                        fb.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
                                               /* Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                                                String facebookUrl = getFacebookPageURL(getActivity(),link);
                                                facebookIntent.setData(Uri.parse(facebookUrl));
                                                startActivity(facebookIntent);*/


                                            }
                                        });
                                    } else {
                                        fb.setVisibility(View.GONE);
                                    }


                                    if (!TextUtils.isEmpty(jsonObject1.getString("instagram"))
                                            && !jsonObject1.getString("instagram").equalsIgnoreCase("null")
                                            && jsonObject1.getString("instagram") != null
                                    ) {

                                        final String link1 = jsonObject1.getString("instagram");
                                        insta.setVisibility(View.VISIBLE);
                                        insta.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                Uri uri = Uri.parse(link1);
                                                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                                                likeIng.setPackage("com.instagram.android");


                                                try {
                                                    startActivity(likeIng);
                                                } catch (ActivityNotFoundException e) {
                                                    try {
                                                        startActivity(new Intent(Intent.ACTION_VIEW,
                                                                Uri.parse(link1)));
                                                    } catch (Exception e1) {
                                                    }
                                                }


                                                // startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link1)));

                                            }
                                        });
                                    } else {
                                        insta.setVisibility(View.GONE);
                                    }


                                    if (!TextUtils.isEmpty(jsonObject1.getString("linkedin"))
                                            && !jsonObject1.getString("linkedin").equalsIgnoreCase("null")
                                            && jsonObject1.getString("linkedin") != null
                                    ) {

                                        final String link3 = jsonObject1.getString("linkedin");


                                        linkedin.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                try {
                                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link3)));
                                                } catch (Exception e) {
                                                }
                                            }
                                        });

                                        linkedin.setVisibility(View.VISIBLE);
                                    } else {
                                        linkedin.setVisibility(View.GONE);
                                    }

                                    if (fb.getVisibility() == View.VISIBLE || insta.getVisibility() == View.VISIBLE ||
                                            linkedin.getVisibility() == View.VISIBLE
                                    ) {
                                        sm_linear.setVisibility(View.VISIBLE);
                                    } else {
                                        sm_linear.setVisibility(View.GONE);
                                    }


                                    if (!TextUtils.isEmpty(jsonObject1.getString("brochure"))
                                            && !jsonObject1.getString("brochure").equalsIgnoreCase("null")
                                            && jsonObject1.getString("brochure") != null
                                    ) {

                                        final String link4 = jsonObject1.getString("brochure");
                                        brochure.setVisibility(View.VISIBLE);
                                        brochure.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Bundle bundle = new Bundle();
                                                bundle.putString("url", link4);
                                                bundle.putString("header", "Brochure");
                                                Intent intent = new Intent(getActivity(), ViewerActivity.class);
                                                intent.putExtras(bundle);
                                                startActivity(intent);
                                                // startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link4)));
                                            }
                                        });
                                    } else {
                                        brochure.setVisibility(View.GONE);
                                    }


                                    if (!TextUtils.isEmpty(jsonObject1.getString("video"))
                                            && !jsonObject1.getString("video").equalsIgnoreCase("null")
                                            && jsonObject1.getString("video") != null
                                    ) {

                                        final String link5 = jsonObject1.getString("video");
                                        video.setVisibility(View.VISIBLE);
                                        video.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                Bundle bundle = new Bundle();
                                                bundle.putString("url", link5);
                                                Intent intent = new Intent(getActivity(), VideoPlayerActivity.class);
                                                intent.putExtras(bundle);
                                                startActivity(intent);
                                                // startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link5)));

                                            }
                                        });
                                    } else {
                                        video.setVisibility(View.GONE);
                                    }


                                    if (!TextUtils.isEmpty(jsonObject1.getString("ppt"))
                                            && !jsonObject1.getString("ppt").equalsIgnoreCase("null")
                                            && jsonObject1.getString("ppt") != null
                                    ) {

                                        final String link6 = jsonObject1.getString("ppt");


                                        ppt.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Bundle bundle = new Bundle();
                                                bundle.putString("url", link6);
                                                bundle.putString("header", "PPT");
                                                Intent intent = new Intent(getActivity(), ViewerActivity.class);
                                                intent.putExtras(bundle);
                                                startActivity(intent);
                                            }
                                        });

                                        ppt.setVisibility(View.VISIBLE);
                                    } else {
                                        ppt.setVisibility(View.GONE);
                                    }


                                    if (ppt.getVisibility() == View.VISIBLE || brochure.getVisibility() == View.VISIBLE ||
                                            video.getVisibility() == View.VISIBLE
                                    ) {
                                        mcm_criteria_linear.setVisibility(View.VISIBLE);
                                    } else {
                                        mcm_criteria_linear.setVisibility(View.GONE);
                                    }


                                    if (!TextUtils.isEmpty(jsonObject1.getString("training_ppt"))
                                            && !jsonObject1.getString("training_ppt").equalsIgnoreCase("null")
                                            && jsonObject1.getString("training_ppt") != null
                                    ) {

                                        final String link7 = jsonObject1.getString("training_ppt");
                                        train_ppt.setVisibility(View.VISIBLE);
                                        train_ppt.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Bundle bundle = new Bundle();
                                                bundle.putString("url", link7);
                                                bundle.putString("header", "Training PPT");
                                                Intent intent = new Intent(getActivity(), ViewerActivity.class);
                                                intent.putExtras(bundle);
                                                startActivity(intent);
                                                //  startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link7)));
                                            }
                                        });
                                    } else {
                                        train_ppt.setVisibility(View.GONE);
                                    }


                                    if (!TextUtils.isEmpty(jsonObject1.getString("training_material"))
                                            && !jsonObject1.getString("training_material").equalsIgnoreCase("null")
                                            && jsonObject1.getString("training_material") != null
                                    ) {

                                        final String link8 = jsonObject1.getString("training_material");
                                        train_brochure.setVisibility(View.VISIBLE);
                                        train_brochure.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Bundle bundle = new Bundle();
                                                bundle.putString("url", link8);
                                                bundle.putString("header", "Training Brochure");
                                                Intent intent = new Intent(getActivity(), ViewerActivity.class);
                                                intent.putExtras(bundle);
                                                startActivity(intent);
                                                // startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link8)));

                                            }
                                        });
                                    } else {
                                        train_brochure.setVisibility(View.GONE);
                                    }


                                    if (!TextUtils.isEmpty(jsonObject1.getString("training_video"))
                                            && !jsonObject1.getString("training_video").equalsIgnoreCase("null")
                                            && jsonObject1.getString("training_video") != null
                                    ) {

                                        final String link9 = jsonObject1.getString("training_video");


                                        train_video.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Bundle bundle = new Bundle();
                                                bundle.putString("url", link9);
                                                Intent intent = new Intent(getActivity(), VideoPlayerActivity.class);
                                                intent.putExtras(bundle);
                                                startActivity(intent);
                                                //  startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link9)));
                                            }
                                        });

                                        train_video.setVisibility(View.VISIBLE);
                                    } else {
                                        train_video.setVisibility(View.GONE);
                                    }


                                    if (train_brochure.getVisibility() == View.VISIBLE || train_video.getVisibility() == View.VISIBLE ||
                                            train_ppt.getVisibility() == View.VISIBLE
                                    ) {
                                        training_linear.setVisibility(View.VISIBLE);
                                    } else {
                                        training_linear.setVisibility(View.GONE);
                                    }


                                }


                            } else if (success.equals("1")) {
                            }

                            desc3_full.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(getActivity());
                            alertDialog.setMessage("Retry with Internet connection");
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
                                            myUpdateOperation();
                                            dialog.cancel();
                                        }
                                    });
                            alertDialog.show();
                        }

                        //progressDialog.dismiss();
                        swiperefresh_desc3.setRefreshing(false);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //progressDialog.dismiss();
                swiperefresh_desc3.setRefreshing(false);
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
                                myUpdateOperation();
                                dialog.cancel();
                            }
                        });
                alertDialog.show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", ShareprefreancesUtility.getInstance().getString("email", ""));
                params.put("name", ShareprefreancesUtility.getInstance().getString("name", ""));
                params.put("password", ShareprefreancesUtility.getInstance().getString("password", ""));
                if (!TextUtils.isEmpty(id_project)) {
                    params.put("project_id", id_project);
                }
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

    private void myUpdateOperation() {
        getProject();
    }


    private String getFacebookPageURL(Context context, String FACEBOOK_URL) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;

            boolean activated = packageManager.getApplicationInfo("com.facebook.katana", 0).enabled;
            if (activated) {

                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;

            } else {
                return FACEBOOK_URL;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL;
        }
    }

    private static void makeTextViewResizable(final TextView tv,
                                              final int maxLine, final String expandText, final boolean viewMore) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                try {
                    ViewTreeObserver obs = tv.getViewTreeObserver();
                    obs.removeGlobalOnLayoutListener(this);
                    if (maxLine == 0) {
                        int lineEndIndex = tv.getLayout().getLineEnd(0);
                        String text = tv.getText().subSequence(0,
                                lineEndIndex - expandText.length() + 1)
                                + " " + expandText;
                        tv.setText(text);
                        tv.setMovementMethod(LinkMovementMethod.getInstance());
                        tv.setText(
                                addClickablePartTextViewResizable(tv.getText()
                                                .toString(), tv, maxLine, expandText,
                                        viewMore), TextView.BufferType.SPANNABLE);
                    } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                        int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                        String text = tv.getText().subSequence(0,
                                lineEndIndex - expandText.length() + 1)
                                + " " + expandText;
                        tv.setText(text);
                        tv.setMovementMethod(LinkMovementMethod.getInstance());
                        tv.setText(
                                addClickablePartTextViewResizable(tv.getText()
                                                .toString(), tv, maxLine, expandText,
                                        viewMore), TextView.BufferType.SPANNABLE);
                    } else {
                        int lineEndIndex = tv.getLayout().getLineEnd(
                                tv.getLayout().getLineCount() - 1);
                        String text = tv.getText().subSequence(0, lineEndIndex)
                                + " " + expandText;
                        tv.setText(text);
                        tv.setMovementMethod(LinkMovementMethod.getInstance());
                        tv.setText(
                                addClickablePartTextViewResizable(tv.getText()
                                                .toString(), tv, lineEndIndex, expandText,
                                        viewMore), TextView.BufferType.SPANNABLE);
                    }
                } catch (Exception e) {
                }
            }
        });

    }

    private static SpannableStringBuilder addClickablePartTextViewResizable(
            final String strSpanned, final TextView tv, final int maxLine,
            final String spanableText, final boolean viewMore) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (strSpanned.contains(spanableText)) {
            ssb.setSpan(
                    new ClickableSpan() {

                        @Override
                        public void onClick(View widget) {

                            try {
                                if (viewMore) {
                                    tv.setLayoutParams(tv.getLayoutParams());
                                    tv.setText(tv.getTag().toString(),
                                            TextView.BufferType.SPANNABLE);
                                    tv.invalidate();
                                    makeTextViewResizable(tv, -4, "\n\nSee Less",
                                            false);
                                    tv.setTextColor(Color.BLACK);
                                } else {
                                    tv.setLayoutParams(tv.getLayoutParams());
                                    tv.setText(tv.getTag().toString(),
                                            TextView.BufferType.SPANNABLE);
                                    tv.invalidate();
                                    makeTextViewResizable(tv, 4, "\n\nSee More",
                                            true);
                                    tv.setTextColor(Color.BLACK);
                                }
                            } catch (Exception e) {
                            }

                        }

                    }, strSpanned.indexOf(spanableText),
                    strSpanned.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;

    }

    private void myUpdateOperationSwipe() {
        getProject();
    }


}
