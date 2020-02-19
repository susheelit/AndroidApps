package com.aob.aobsalesman.activities.Fragements;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.aob.aobsalesman.R;
import com.aob.aobsalesman.activities.activities.KYCActivity;
import com.aob.aobsalesman.activities.activities.RegistrationActivity;
import com.aob.aobsalesman.activities.model.Review_Data;
import com.aob.aobsalesman.activities.utility.InternetConnection;
import com.aob.aobsalesman.activities.utility.MySingleton;
import com.aob.aobsalesman.activities.utility.ShareprefreancesUtility;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




public class ProjectDescriptionReviewFragment extends Fragment {

    View view;
    @SuppressLint("HardwareIds")
    String android_id;

    private ProgressDialog progressDialog;
    private ProgressDialog progressDialog1;

    RecyclerView recyclerView_review;

    public List<Review_Data> myReviewsList = new ArrayList<>();
    public List<Review_Data> myReviewsListnew = new ArrayList<>();
    ReviewsAdapter reviewsAdapter;

    String id_project1 = "";
    TextView tv_no_data_lead;


    private Gson gson1;

    TextView add_review;
    String comment = "";

    long mLastClickTime7 = 0;
    long mLastClickTime8 = 0;

    SwipeRefreshLayout swiperefresh_desc2;



    public ProjectDescriptionReviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_description2, container, false);
        initViews();
        initFunctionality();


        return view;
    }

    private void initViews()
    {
        recyclerView_review = (RecyclerView) view.findViewById(R.id.recyclerView_review);
        recyclerView_review.setLayoutManager(new LinearLayoutManager(getActivity()));
        reviewsAdapter = new ReviewsAdapter(getActivity(), myReviewsList);
        recyclerView_review.setAdapter(reviewsAdapter);

        add_review = view.findViewById(R.id.add_review);

        Bundle bundle = this.getArguments();
        if (bundle != null) {



            id_project1 = bundle.getString("project_id");

        }

        swiperefresh_desc2 = view.findViewById(R.id.swiperefresh_desc2);
        tv_no_data_lead = view.findViewById(R.id.tv_no_data_lead);


    }

    private void initFunctionality()
    {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson1 = gsonBuilder.create();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView_review.setLayoutManager(layoutManager);
        recyclerView_review.setHasFixedSize(true);




        try {
            getActivity().getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }catch (Exception e){}

        try {

            android_id = Settings.Secure.getString(getContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);

        } catch (Exception e) {
        }

        if (InternetConnection.checkInternetConnectivity()) {

            progressDialog=new ProgressDialog(getActivity());


            getProject();

        } else {
            final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(getActivity());
            alertDialog.setMessage("Retry with internet connection.");
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

        add_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SystemClock.elapsedRealtime() - mLastClickTime7 < 1000) {
                    return;
                }
                mLastClickTime7 = SystemClock.elapsedRealtime();


            /*    if (InternetConnection.checkInternetConnectivity()) {

                    progressDialog=new ProgressDialog(getActivity());


                    getProject();

                } else {
                    final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(getActivity());
                    alertDialog.setMessage("Retry with internet connection.");
                    alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alertDialog.show();
                }
*/





                try {
                    if ((ShareprefreancesUtility.getInstance().getString("register_status")).equalsIgnoreCase("complete")) {

                        if ((ShareprefreancesUtility.getInstance().getString("kyc_status")).equalsIgnoreCase("approved")) {


                            if (InternetConnection.checkInternetConnectivity()) {

                                final Dialog dialog = new Dialog(getActivity());
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                dialog.setContentView(R.layout.fragment_addreview);
                                final AppCompatEditText review_full  = dialog.findViewById(R.id.review_full);

                                Button review_submit = dialog.findViewById(R.id.review_submit);
                                review_submit.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {


                                        if (SystemClock.elapsedRealtime() - mLastClickTime8 < 1000) {
                                            return;
                                        }
                                        mLastClickTime8 = SystemClock.elapsedRealtime();

                                        review_full.setError(null);
                                        if(TextUtils.isEmpty(review_full.getText().toString().trim()))
                                        {
                                            review_full.setError("Please enter the review");
                                        }else
                                        {
                                            review_full.setError(null);
                                            comment = review_full.getText().toString();

                                            if (InternetConnection.checkInternetConnectivity()) {

                                                progressDialog1=new ProgressDialog(getActivity());

                                                addReview();
                                                dialog.dismiss();

                                            } else {
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





                                    }
                                });



                                dialog.show();



                            } else {
                                final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(getActivity());
                                alertDialog.setMessage("Please check your Internet Connection");
                                alertDialog.setCancelable(false);
                                alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        getActivity().finish();
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



            }
        });

        swiperefresh_desc2.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myUpdateOperationSwipe();
            }
        });



    }
    public void showRegistrationDialog()
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage("Please fill complete Registration details to post reviews.");
        builder1.setCancelable(true);
        builder1.setTitle("Complete your profile");
        builder1.setPositiveButton(
                "Register Now",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(getActivity(), RegistrationActivity.class);
                        startActivity(i);
                        getActivity().finish();
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

    public void showKYCDialogRejected()
    {

        AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
        builder2.setMessage("Please update your KYC details to post reviews.");
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

    public void showKYCDialog()
    {

        AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
        builder2.setMessage("Profile is incomplete. Please update your KYC details to post reviews.");
        builder2.setCancelable(true);
        builder2.setTitle("KYC is pending");
        builder2.setPositiveButton(
                "Now",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(getActivity(), KYCActivity.class);
                        startActivity(i);
                        getActivity().finish();
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




    private void getProject(){
        //progressDialog.setMessage("Please wait...");
       // progressDialog.setCancelable(false);
        //progressDialog.show();
        swiperefresh_desc2.setRefreshing(true);

        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                getResources().getString(R.string.hostName)+"/app/sales/v1_0/review_list.php?",
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {

                        Log.e("aobsales","response reviews "+response.toString());

                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String success=jsonObject.getString("status");

                            if (success.equals("0")) {


                                JSONArray jsonreview = jsonObject.getJSONArray("review");
                                if (jsonreview.length() > 0) {

                                    myReviewsListnew = new ArrayList<Review_Data>(Arrays.asList(gson1.fromJson(jsonreview.toString(), Review_Data[].class)));
                                    if (myReviewsList!=null){
                                        myReviewsList.clear();
                                    }
                                    assert myReviewsList != null;
                                    myReviewsList.addAll(myReviewsListnew);
                                    reviewsAdapter.notifyDataSetChanged();

                                    if(!myReviewsList.isEmpty()) {

                                        recyclerView_review.setVisibility(View.VISIBLE);
                                        tv_no_data_lead.setVisibility(View.INVISIBLE);

                                    }else
                                    {
                                        recyclerView_review.setVisibility(View.INVISIBLE);
                                        tv_no_data_lead.setVisibility(View.VISIBLE);

                                    }

                                }
                            }
                            else if (success.equals("1")){

                                if(!myReviewsList.isEmpty()) {

                                    recyclerView_review.setVisibility(View.VISIBLE);
                                    tv_no_data_lead.setVisibility(View.INVISIBLE);

                                }else
                                {
                                    recyclerView_review.setVisibility(View.INVISIBLE);
                                    tv_no_data_lead.setVisibility(View.VISIBLE);

                                }
                            }


                        } catch (JSONException e) {
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

                       // progressDialog.dismiss();

                        swiperefresh_desc2.setRefreshing(false);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //progressDialog.dismiss();

                swiperefresh_desc2.setRefreshing(false);

                final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(getActivity());
                alertDialog.setTitle("Oops! Something went wrong.");
                alertDialog.setMessage("This page didn't load correctly.\nPlease try again.");
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
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", ShareprefreancesUtility.getInstance().getString("email", ""));
                params.put("name", ShareprefreancesUtility.getInstance().getString("name", ""));
                params.put("password", ShareprefreancesUtility.getInstance().getString("password", ""));
                params.put("android_id", android_id);
                params.put("project_id",id_project1);
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


    public class ReviewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<Review_Data> items = new ArrayList<>();
        private Context ctx;
        public View v;
        private long mLastClickTime = 0;

        public ReviewsAdapter(Context context, List<Review_Data> items ) {

            ctx = context;
            this.items=items;


        }

        public class OriginalViewHolder extends RecyclerView.ViewHolder {

            public TextView user_name;
            public TextView user_review;
            public TextView review_status;
            public TextView review_time;


            public OriginalViewHolder(View v) {
                super(v);
                user_name =  v.findViewById(R.id.user_name);
                user_review =  v.findViewById(R.id.user_review);
                review_status = v.findViewById(R.id.review_status);
                review_time = v.findViewById(R.id.review_time);
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder vh;
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
            vh = new OriginalViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder,  final int position) {
            Log.e("onBindViewHolder", "onBindViewHolder : " + position);
            if (holder instanceof OriginalViewHolder) {
                OriginalViewHolder view = (OriginalViewHolder) holder;

                Log.e("aobsales","oe "+items.get(position).getName());
                view.user_name.setText(items.get(position).getName());
                view.user_review.setText(items.get(position).getComments());
               view.review_time.setText(items.get(position).getTime());
             view.review_status.setText(items.get(position).getDate());

                if(items.get(position).getReview_status().equalsIgnoreCase("pending"))
                {
                            view.review_status.setTextColor(getResources().getColor(R.color.red_600));
                            view.review_time.setTextColor(getResources().getColor(R.color.red_600));
                }
                else if(items.get(position).getReview_status().equalsIgnoreCase("approved"))
                {
                    view.review_status.setTextColor(getResources().getColor(R.color.green_600));
                    view.review_time.setTextColor(getResources().getColor(R.color.green_600));
                }




            }

        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }



    }



    private void addReview(){
        progressDialog1.setMessage("Please wait...");
        progressDialog1.setCancelable(false);
        progressDialog1.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                getResources().getString(R.string.hostName)+"/app/sales/v1_0/review_add.php?",
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {

                        Log.e("aobsales","response success "+response.toString());

                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String success=jsonObject.getString("status");

                            if (success.equals("0")) {

                                final android.app.AlertDialog.Builder alertDialog3 = new android.app.AlertDialog.Builder(getActivity());
                                alertDialog3.setMessage("Thank you for your Review!");
                                alertDialog3.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                alertDialog3.show();

                             getProject();


                            }
                            else if (success.equals("1")){
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
                params.put("project_id",id_project1);
                params.put("comments",comment);

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);

    }
    private void myUpdateOperationSwipe() {
        if(myReviewsList != null) {
            myReviewsList.clear();
            myReviewsListnew.clear();
            reviewsAdapter.notifyDataSetChanged();
            getProject();
        }
    }


}
