package com.aob.aobsalesman.activities.Fragements;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.aob.aobsalesman.activities.activities.AppliedDescriptionActivity;
import com.aob.aobsalesman.activities.activities.KYCActivity;
import com.aob.aobsalesman.activities.activities.NotificationsActivity;
import com.aob.aobsalesman.activities.activities.ProjectDescriptionActivity;
import com.aob.aobsalesman.activities.model.Company_Data;
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

public class FragmentAppliedProjects extends Fragment {


    String tag = "aobsalesman";

    private FragmentAppliedProjects.OnFragmentInteractionListener mListener;
    View view;
    RecyclerView recyclerView_applied;
    private List<Company_Data> appliedProject_data=new ArrayList<>();
    private List<Company_Data> filteredList1 = new ArrayList<>();
    ArrayList<Company_Data> sortArrayList1=new ArrayList<>();
    ArrayList<Company_Data> filterArrayList1 = new ArrayList<>();
    AppliedProjectAdapter appliedProjectadapter;

    Boolean active1 = false;

    String CheckSort1 = "none";
    String ChecKFilter1 = "inactive";
    long mLastClickTime6 = 0;


    String filter_state = "";
    String filter_industry = "";

    private List<String> fstatedata1=new ArrayList<>();
    private List<String> findustrydata1 = new ArrayList<>();

    private Gson gson;

    SwipeRefreshLayout swiperefresh_appliedprojects;

    EditText searchBoxApplied;

    TextView tv_no_data_lead;

    @SuppressLint("HardwareIds")
    String android_id = "";

    public FragmentAppliedProjects() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_applied_projects, container, false);
        recyclerView_applied = (RecyclerView) view.findViewById(R.id.recyclerView_applied);

        tv_no_data_lead = view.findViewById(R.id.tv_no_data_lead);


        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        Bundle bundle = this.getArguments();
        if (bundle != null) {



            String response1 = bundle.getString("response2");
            Log.e("aobsales","op "+response1);
            try {

                if(!TextUtils.isEmpty(response1) && response1 != null) {
                    JSONArray jsonArray = new JSONArray(response1);

                    if (jsonArray.length() > 0) {
                        appliedProject_data = new ArrayList<Company_Data>(Arrays.asList(gson.fromJson(jsonArray.toString(), Company_Data[].class)));

                        recyclerView_applied.setVisibility(View.VISIBLE);
                        tv_no_data_lead.setVisibility(View.INVISIBLE);

                    }
                    else
                    {
                        recyclerView_applied.setVisibility(View.INVISIBLE);
                        tv_no_data_lead.setVisibility(View.VISIBLE);
                    }

                }else
                {
                    recyclerView_applied.setVisibility(View.INVISIBLE);
                    tv_no_data_lead.setVisibility(View.VISIBLE);
                }
            }catch (Exception e){
                Log.e("aobsales"," is "+e.toString());
            }

        }



        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView_applied.setLayoutManager(layoutManager);
        recyclerView_applied.setHasFixedSize(true);

        filteredList1.addAll(appliedProject_data);
        sortArrayList1.addAll(appliedProject_data);

        appliedProjectadapter = new AppliedProjectAdapter(getActivity(), filteredList1);
        recyclerView_applied.setAdapter(appliedProjectadapter);
        /*appliedProjectadapter.setOnItemClickListener(new AppliedProjectAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view,  int position, RecyclerView.ViewHolder holder) {


                if ((ShareprefreancesUtility.getInstance().getString("KYC_status")).equalsIgnoreCase("done"))
                {

                }
                else
                {
                    showKYCDialog();

                }


            }
        });*/


        searchBoxApplied = view.findViewById(R.id.search_box_applied);
        searchBoxApplied.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(appliedProject_data.size() > 0)
                {
                appliedProjectadapter.getFilter().filter(s.toString());
                }

            }
            @Override
            public void afterTextChanged(Editable s) {
                if (appliedProjectadapter.getItemCount() == 0){
                    ((TextView)view.findViewById(R.id.tv_no_data_lead)).setText("No result found.");
                    ((TextView)view.findViewById(R.id.tv_no_data_lead)).setVisibility(View.VISIBLE);
                }
                else {
                    ((TextView)view.findViewById(R.id.tv_no_data_lead)).setVisibility(View.GONE);
                }
            }
        });

        ((androidx.coordinatorlayout.widget.CoordinatorLayout)getActivity().findViewById(R.id.notification_icon1)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((ViewPager) getActivity().findViewById(R.id.viewpager)).getCurrentItem() == 0) {
                } else {
                    startActivity(new Intent(getActivity(), NotificationsActivity.class));
                }
            }
        });
        try {

            ((ImageView) getActivity().findViewById(R.id.sort1)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((ViewPager) getActivity().findViewById(R.id.viewpager)).getCurrentItem() == 0) {

                    } else {
                        Log.e("aobsales","2nd clicked");

                        if (SystemClock.elapsedRealtime() - mLastClickTime6 < 1000) {
                            return;
                        }
                        mLastClickTime6 = SystemClock.elapsedRealtime();
                        showSortDialog1();

                    }
                }
            });
        }catch (Exception e){}


        try {
            ((ImageView) getActivity().findViewById(R.id.filter1)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((ViewPager) getActivity().findViewById(R.id.viewpager)).getCurrentItem() == 0) {


                    } else {


                        if (SystemClock.elapsedRealtime() - mLastClickTime6 < 1000) {
                            return;
                        }
                        mLastClickTime6 = SystemClock.elapsedRealtime();


                        searchBoxApplied.setText("");
                        filteredList1.clear();
                        sortArrayList1.clear();
                        filteredList1.addAll(appliedProject_data);
                        sortArrayList1.addAll(appliedProject_data);
                        appliedProjectadapter.notifyDataSetChanged();
                        CheckSort1 = "none";
                        Intent intent = new Intent(getActivity(), ActivityFilter.class);
                        intent.putExtra("resultindustry", filter_industry);
                        intent.putExtra("resultcity", filter_state);
                        startActivityForResult(intent, 1000);
                        getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.fade_out);


                    }
                }
            });
        }catch (Exception e){}


       /* ImageView sort_image1 = view.findViewById(R.id.sort_image1);
        sort_image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime6 = SystemClock.elapsedRealtime();


            }
        });*/


        swiperefresh_appliedprojects = view.findViewById(R.id.swiperefresh_appliedprojects);

        swiperefresh_appliedprojects = view.findViewById(R.id.swiperefresh_appliedprojects);
        swiperefresh_appliedprojects.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myUpdateOperationSwipe();
            }
        });


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
    public void showRegistrationDialog()
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage("Please fill Registration details to apply to the projects.");
        builder1.setCancelable(true);
        builder1.setTitle("Registratio0n is pending");
        builder1.setPositiveButton(
                "KYC",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(getActivity(), KYCActivity.class);
                        startActivity(i);
                    }
                });
        builder1.setNegativeButton(
                "Registration",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void showKYCDialog()
    {

        AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
        builder2.setMessage("Please fill your KYC details to apply to the projects.");
        builder2.setCancelable(true);
        builder2.setTitle("KYC is pending");
        builder2.setPositiveButton(
                "KYC",
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


    public class AppliedProjectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

        private List<Company_Data> items = new ArrayList<>();
        private Context ctx;
        public View v;
        private CustomFilter mFilter;
        private long mLastClickTime4 = 0;

        public AppliedProjectAdapter(Context context, List<Company_Data> items ) {

            ctx = context;
            this.items=items;
            mFilter = new CustomFilter(AppliedProjectAdapter.this);

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
                image = (ImageView) v.findViewById(R.id.image);
                share_image = (ImageView) v.findViewById(R.id.share_image);
                call = (ImageView) v.findViewById(R.id.call);
                lyt_parent = (View) v.findViewById(R.id.lyt_parent);
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
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_applied, parent, false);
            vh = new OriginalViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder,  final int position) {
            if (holder instanceof OriginalViewHolder) {
                OriginalViewHolder view = (OriginalViewHolder) holder;

                try {

                    view.company_name.setText(items.get(position).getProject_name());
                    view.title.setText(items.get(position).getProjectTitle());
                    view.views.setText("Views: "+items.get(position).getViews());

                    view.no_of_salesman.setText("Active Salesmen: "+items.get(position).getSalesman_working_on());

                    Tools.displayImageOriginal(ctx, view.image, items.get(position).getLogo_url());


                    String cityList = android.text.TextUtils.join(",", items.get(position).getSales_type());
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
                        view.apply_rv.setText("Applied");
                    }else {
                        view.apply_rv.setEnabled(true);
                        view.apply_rv.setText("Closed");
                    }
                    view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            Log.e("aobsales", "i am here");

                            if (SystemClock.elapsedRealtime() - mLastClickTime6 < 1000) {
                                return;
                            }
                            mLastClickTime6 = SystemClock.elapsedRealtime();

                            Intent intent = new Intent(ctx, AppliedDescriptionActivity.class);
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


                            if (SystemClock.elapsedRealtime() - mLastClickTime6 < 3000) {
                                return;
                            }
                            mLastClickTime6 = SystemClock.elapsedRealtime();

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
                                }

                                else if (items.get(position).getSales_type().size() == 2){
                                    String cityList = items.get(position).getSales_type().get(0)+"\n"+ items.get(position).getSales_type().get(0);
                                    ShareData.shareItem(getActivity(), items.get(position).getLogo_url(), "\n\n"
                                            + items.get(position).getProject_name() + "\n" + items.get(position).getProjectTitle()
                                            + "\n\nPrice of product: " + items.get(position).getPrice() + "\nCommission Sale: " + items.get(position).getSale_commission_freelancer()+ "\nCommission Lead: " + items.get(position).getLead_commission_freelancer()
                                            + "\n\nViews: " + items.get(position).getViews() + "\nActive Salesmen: " + items.get(position).getSalesman_working_on()
                                            + "\nTotal Incentive Earned: " + items.get(position).getIncentive_earned_total() + "\nNature of project: " + cityList
                                            + "\n\nTo install the AOB Salesman App click the link below & get \u20B9 500 as registration bonus. \n"
                                            + "https://play.google.com/store/apps/details?id=" + appPackageName
                                            +"\n\nReferral Code: "+ShareprefreancesUtility.getInstance().getString("mobile"));

                                }             }catch (Exception e){}


                        }
                    });


                }catch (Exception e){}

            }

        }

        @Override
        public int getItemCount() {
            return items.size();
        }


        @Override
        public Filter getFilter() {
            return mFilter;
        }

         @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }


        public class CustomFilter extends Filter {
            private AppliedProjectAdapter mAdapter;
            private CustomFilter(AppliedProjectAdapter mAdapter) {
                super();
                this.mAdapter = mAdapter;
            }
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                Log.e("aobsales","checkfilter1 "+ChecKFilter1);

                if(ChecKFilter1.equalsIgnoreCase("inactive"))
                {
                    filteredList1.clear();
                    final FilterResults results = new FilterResults();


                    if(CheckSort1.equalsIgnoreCase("none")) {
                        sortArrayList1.clear();
                        if (constraint.length() == 0) {
                            filteredList1.addAll(appliedProject_data);
                            sortArrayList1.addAll(appliedProject_data);

                        } else {
                            final String filterPattern = constraint.toString().toLowerCase().trim();
                            for (final Company_Data mWords : appliedProject_data) {
                                if (mWords.getProject_name().toLowerCase().startsWith(filterPattern)) {
                                    filteredList1.add(mWords);
                                    sortArrayList1.add(mWords);
                                }
                            }
                        }
                    }
                    else   if(CheckSort1.equalsIgnoreCase("name"))
                    {


                        if (constraint.length() == 0) {

                            sortArrayList1.clear();
                            sortArrayList1.addAll(appliedProject_data);
                            Collections.sort(sortArrayList1, new Comparator<Company_Data>() {
                                @Override
                                public int compare(Company_Data lhs, Company_Data rhs) {
                                    return lhs.getProject_name().compareTo(rhs.getProject_name());

                                }
                            });
                            filteredList1.addAll(sortArrayList1);
                        } else {

                            sortArrayList1.clear();
                            sortArrayList1.addAll(appliedProject_data);
                            Collections.sort(sortArrayList1, new Comparator<Company_Data>() {
                                @Override
                                public int compare(Company_Data lhs, Company_Data rhs) {
                                    return lhs.getProject_name().compareTo(rhs.getProject_name());

                                }
                            });
                            final String filterPattern = constraint.toString().toLowerCase().trim();
                            for (final Company_Data mWords : sortArrayList1) {
                                if (mWords.getProject_name().toLowerCase().startsWith(filterPattern)) {
                                    filteredList1.add(mWords);
                                }
                            }
                            sortArrayList1.clear();
                            sortArrayList1.addAll(filteredList1);
                        }

                    }
                    else   if(CheckSort1.equalsIgnoreCase("pricehightolow"))
                    {


                        if (constraint.length() == 0) {

                            sortArrayList1.clear();
                            sortArrayList1.addAll(appliedProject_data);
                            Collections.sort(sortArrayList1, new Comparator<Company_Data>() {
                                @Override
                                public int compare(Company_Data lhs, Company_Data rhs) {
                                    return rhs.getIncentive()-(lhs.getIncentive());

                                }
                            });
                            filteredList1.addAll(sortArrayList1);
                        } else {

                            sortArrayList1.clear();
                            sortArrayList1.addAll(appliedProject_data);
                            Collections.sort(sortArrayList1, new Comparator<Company_Data>() {
                                @Override
                                public int compare(Company_Data lhs, Company_Data rhs) {
                                    return rhs.getIncentive()-(lhs.getIncentive());

                                }
                            });
                            final String filterPattern = constraint.toString().toLowerCase().trim();
                            for (final Company_Data mWords : sortArrayList1) {
                                if (mWords.getProject_name().toLowerCase().startsWith(filterPattern)) {
                                    filteredList1.add(mWords);
                                }
                            }
                            sortArrayList1.clear();
                            sortArrayList1.addAll(filteredList1);
                        }

                    }
                    else  if(CheckSort1.equalsIgnoreCase("pricelowtohigh"))
                    {


                        if (constraint.length() == 0) {

                            sortArrayList1.clear();
                            sortArrayList1.addAll(appliedProject_data);
                            Collections.sort(sortArrayList1, new Comparator<Company_Data>() {
                                @Override
                                public int compare(Company_Data lhs, Company_Data rhs) {
                                    return lhs.getIncentive()-(rhs.getIncentive());

                                }
                            });
                            filteredList1.addAll(sortArrayList1);
                        } else {

                            sortArrayList1.clear();
                            sortArrayList1.addAll(appliedProject_data);
                            Collections.sort(sortArrayList1, new Comparator<Company_Data>() {
                                @Override
                                public int compare(Company_Data lhs, Company_Data rhs) {
                                    return lhs.getIncentive()-(rhs.getIncentive());

                                }
                            });
                            final String filterPattern = constraint.toString().toLowerCase().trim();
                            for (final Company_Data mWords : sortArrayList1) {
                                if (mWords.getProject_name().toLowerCase().startsWith(filterPattern)) {
                                    filteredList1.add(mWords);
                                }
                            }
                            sortArrayList1.clear();
                            sortArrayList1.addAll(filteredList1);
                        }

                    }


                    results.values = filteredList1;
                    results.count = filteredList1.size();
                    Log.e("aobsales","hello "+filteredList1.size());
                    return results;

                }
                else
                {

                    filteredList1.clear();
                    final FilterResults results = new FilterResults();

                    if(CheckSort1.equalsIgnoreCase("none")) {
                        sortArrayList1.clear();
                        if (constraint.length() == 0) {
                            filteredList1.addAll(filterArrayList1);
                            sortArrayList1.addAll(filterArrayList1);
                        } else {
                            final String filterPattern = constraint.toString().toLowerCase().trim();
                            for (final Company_Data mWords : filterArrayList1) {
                                if (mWords.getProject_name().toLowerCase().startsWith(filterPattern)) {
                                    filteredList1.add(mWords);
                                    sortArrayList1.add(mWords);
                                }
                            }
                        }
                    }else   if(CheckSort1.equalsIgnoreCase("name"))
                    {


                        if (constraint.length() == 0) {

                            sortArrayList1.clear();
                            sortArrayList1.addAll(appliedProject_data);
                            Collections.sort(sortArrayList1, new Comparator<Company_Data>() {
                                @Override
                                public int compare(Company_Data lhs, Company_Data rhs) {
                                    return lhs.getProject_name().compareTo(rhs.getProject_name());

                                }
                            });
                            filteredList1.addAll(sortArrayList1);
                        } else {

                            sortArrayList1.clear();
                            sortArrayList1.addAll(appliedProject_data);
                            Collections.sort(sortArrayList1, new Comparator<Company_Data>() {
                                @Override
                                public int compare(Company_Data lhs, Company_Data rhs) {
                                    return lhs.getProject_name().compareTo(rhs.getProject_name());

                                }
                            });
                            final String filterPattern = constraint.toString().toLowerCase().trim();
                            for (final Company_Data mWords : sortArrayList1) {
                                if (mWords.getProject_name().toLowerCase().startsWith(filterPattern)) {
                                    filteredList1.add(mWords);
                                }
                            }
                            sortArrayList1.clear();
                            sortArrayList1.addAll(filteredList1);
                        }

                    }
                    else   if(CheckSort1.equalsIgnoreCase("pricehightolow"))
                    {


                        if (constraint.length() == 0) {

                            sortArrayList1.clear();
                            sortArrayList1.addAll(filterArrayList1);
                            Collections.sort(sortArrayList1, new Comparator<Company_Data>() {
                                @Override
                                public int compare(Company_Data lhs, Company_Data rhs) {
                                    return rhs.getIncentive()-(lhs.getIncentive());

                                }
                            });
                            filteredList1.addAll(sortArrayList1);
                        } else {

                            sortArrayList1.clear();
                            sortArrayList1.addAll(filterArrayList1);
                            Collections.sort(sortArrayList1, new Comparator<Company_Data>() {
                                @Override
                                public int compare(Company_Data lhs, Company_Data rhs) {
                                    return rhs.getIncentive()-(lhs.getIncentive());

                                }
                            });
                            final String filterPattern = constraint.toString().toLowerCase().trim();
                            for (final Company_Data mWords : sortArrayList1) {
                                if (mWords.getProject_name().toLowerCase().startsWith(filterPattern)) {
                                    filteredList1.add(mWords);
                                }
                            }
                            sortArrayList1.clear();
                            sortArrayList1.addAll(filteredList1);
                        }

                    }
                    else  if(CheckSort1.equalsIgnoreCase("pricelowtohigh"))
                    {


                        if (constraint.length() == 0) {

                            sortArrayList1.clear();
                            sortArrayList1.addAll(filterArrayList1);
                            Collections.sort(sortArrayList1, new Comparator<Company_Data>() {
                                @Override
                                public int compare(Company_Data lhs, Company_Data rhs) {
                                    return lhs.getIncentive()-(rhs.getIncentive());

                                }
                            });
                            filteredList1.addAll(sortArrayList1);
                        } else {

                            sortArrayList1.clear();
                            sortArrayList1.addAll(filterArrayList1);
                            Collections.sort(sortArrayList1, new Comparator<Company_Data>() {
                                @Override
                                public int compare(Company_Data lhs, Company_Data rhs) {
                                    return lhs.getIncentive()-(rhs.getIncentive());

                                }
                            });
                            final String filterPattern = constraint.toString().toLowerCase().trim();
                            for (final Company_Data mWords : sortArrayList1) {
                                if (mWords.getProject_name().toLowerCase().startsWith(filterPattern)) {
                                    filteredList1.add(mWords);
                                }
                            }
                            sortArrayList1.clear();
                            sortArrayList1.addAll(filteredList1);
                        }

                    }
                    results.values = filteredList1;
                    results.count = filteredList1.size();
                    return results;

                }
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if(constraint.length() == 0)
                {
                    active1 = false;
                }
                else {
                    active1 = true;
                }
                this.mAdapter.notifyDataSetChanged();
            }
        }
        }





    private void showSortDialog1()
    {


        final AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater1 = this.getLayoutInflater();
        View dialogView1 = inflater1.inflate(R.layout.sort_dialog, null);
        builder1.setView(dialogView1);
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

                        if (CheckSort1.equalsIgnoreCase("none")) {

                            dialog.cancel();

                        }else if(CheckSort1.equalsIgnoreCase("name"))
                        {


                            Log.e("aobsales","i am here1");

                            if(active1 == true) {

                                Log.e("aobsales","i am here2");
                                filteredList1.clear();
                                Collections.reverse(sortArrayList1);
                                filteredList1.addAll(sortArrayList1);
                                CheckSort1 = "none";
                                appliedProjectadapter.notifyDataSetChanged();
                                dialog.cancel();

                            }else {

                                Log.e("aobsales","i am here3");

                                filteredList1.clear();
                                filteredList1.addAll(appliedProject_data);
                                CheckSort1 = "none";
                                appliedProjectadapter.notifyDataSetChanged();
                                dialog.cancel();

                            }

                        }else if(CheckSort1.equalsIgnoreCase("pricehightolow"))
                        {

                            if(active1 == true) {

                                filteredList1.clear();
                                //Collections.reverse(sortArrayList);

                                Collections.sort(sortArrayList1, new Comparator<Company_Data>() {
                                    @Override
                                    public int compare(Company_Data lhs, Company_Data rhs) {
                                        return lhs.getProject_name().compareTo(rhs.getProject_name());

                                    }
                                });

                                filteredList1.addAll(sortArrayList1);
                                CheckSort1 = "none";
                                appliedProjectadapter.notifyDataSetChanged();
                                dialog.cancel();

                            }else {


                                filteredList1.clear();
                                filteredList1.addAll(appliedProject_data);
                                CheckSort1 = "none";
                                appliedProjectadapter.notifyDataSetChanged();
                                dialog.cancel();

                            }


                        }else if(CheckSort1.equalsIgnoreCase("pricelowtohigh"))
                        {

                            if(active1 == true) {

                                filteredList1.clear();
                                //Collections.reverse(sortArrayList);

                                Collections.sort(sortArrayList1, new Comparator<Company_Data>() {
                                    @Override
                                    public int compare(Company_Data lhs, Company_Data rhs) {
                                        return lhs.getProject_name().compareTo(rhs.getProject_name());

                                    }
                                });

                                filteredList1.addAll(sortArrayList1);
                                CheckSort1 = "none";
                                appliedProjectadapter.notifyDataSetChanged();
                                dialog.cancel();

                            }else {



                                filteredList1.clear();
                                filteredList1.addAll(appliedProject_data);
                                CheckSort1 = "none";
                                appliedProjectadapter.notifyDataSetChanged();
                                dialog.cancel();

                            }
                        }



                    }
                });


        RadioGroup radioGroup = dialogView1.findViewById(R.id.radio);


        if(CheckSort1.equalsIgnoreCase("name"))
        {
            radioGroup.check(R.id.radio_name);

        }else if(CheckSort1.equalsIgnoreCase("pricehightolow"))
        {
            radioGroup.check(R.id.radio_price);

        }
        else if(CheckSort1.equalsIgnoreCase("pricelowtohigh"))
        {
            radioGroup.check(R.id.radio_pricelowtohigh);

        }else if(CheckSort1.equalsIgnoreCase("none"))
        {

        }



        final AlertDialog alert11 = builder1.create();
        alert11.show();


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
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


                    Collections.sort(sortArrayList1, new Comparator<Company_Data>() {
                        @Override
                        public int compare(Company_Data lhs, Company_Data rhs) {
                            return rhs.getIncentive_earned_total()- lhs.getIncentive_earned_total();

                        }
                    });


                    filteredList1.clear();
                    filteredList1.addAll(sortArrayList1);
                    CheckSort1 = "pricehightolow";
                    appliedProjectadapter.notifyDataSetChanged();
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


                    Collections.sort(sortArrayList1, new Comparator<Company_Data>() {
                        @Override
                        public int compare(Company_Data lhs, Company_Data rhs) {
                            return lhs.getIncentive_earned_total()- rhs.getIncentive_earned_total();

                        }
                    });

                    Log.e("aobsales","hello "+sortArrayList1.get(0).getIncentive());
                    Log.e("aobsales","hello "+sortArrayList1.get(1).getIncentive());

                    filteredList1.clear();
                    filteredList1.addAll(sortArrayList1);
                    CheckSort1 = "pricelowtohigh";
                    appliedProjectadapter.notifyDataSetChanged();
                    alert11.dismiss();

                }
                else if(checkedRadioButton.getText().toString().equalsIgnoreCase("Name (Alphabetically A - Z)"))
                {


                    Collections.sort(sortArrayList1, new Comparator<Company_Data>() {
                        @Override
                        public int compare(Company_Data lhs, Company_Data rhs) {
                            return lhs.getProject_name().compareTo(rhs.getProject_name());

                        }
                    });
                    filteredList1.clear();
                    filteredList1.addAll(sortArrayList1);
                    CheckSort1 = "name";
                    appliedProjectadapter.notifyDataSetChanged();
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



                    findustrydata1.clear();
                    fstatedata1.clear();
                    ChecKFilter1 = "inactive";
                    filteredList1.clear();
                    filteredList1.addAll(appliedProject_data);
                    sortArrayList1.clear();
                    sortArrayList1.addAll(appliedProject_data);
                    filterArrayList1.clear();
                    CheckSort1 = "none";
                    appliedProjectadapter.notifyDataSetChanged();

                }
                else {
                    findustrydata1.clear();
                    fstatedata1.clear();

                    if (!TextUtils.isEmpty(filter_industry)) {
                        findustrydata1 = new ArrayList(Arrays.asList(filter_industry.split(",")));
                    }


                    if (!TextUtils.isEmpty(filter_state)) {
                        fstatedata1 = new ArrayList(Arrays.asList(filter_state.split(",")));
                    }


                    filterData();
                }

            }catch (Exception e)
            {}
        }
    }

    public void filterData()
    {

        Log.e("aobsales","lp "+fstatedata1);

        ChecKFilter1 = "active";

        if(CheckSort1.equalsIgnoreCase("none")) {


            try {
                if (findustrydata1.size() > 0) {
                    for (String c : findustrydata1) {

                        for (Company_Data obj : appliedProject_data) {

                            for (String obj1 : obj.getIndustries()) {

                                if (c.trim().equalsIgnoreCase(obj1.trim())) {

                                    if(!filterArrayList1.contains(obj)) {
                                        filterArrayList1.add(obj);
                                    }

                                }

                            }
                        }
                    }
                }

                if (fstatedata1.size() > 0) {
                    for (String c : fstatedata1) {

                        for (Company_Data obj : appliedProject_data) {

                            for (String obj1 : obj.getCity()) {

                                // Log.e("aobsales","city "+obj.getProject_name());
                                // Log.e("aobsales","city "+obj1);
                                //  Log.e("aobsales","city2 "+c);

                                if (c.trim().equalsIgnoreCase(obj1.trim())) {

                                    //Log.e("aobsales","true"+obj1 );
                                    //Log.e("aobsales","true"+obj1 );
                                    if(!filterArrayList1.contains(obj)) {
                                        filterArrayList1.add(obj);
                                    }

                                }

                            }
                        }
                    }
                }




                filteredList1.clear();
                sortArrayList1.clear();
                filteredList1.addAll(filterArrayList1);
                sortArrayList1.addAll(filterArrayList1);
                appliedProjectadapter.notifyDataSetChanged();


            }catch (Exception e){}
        }
       /* else
        {

            try {
                if (findustrydata1.size() > 0) {
                    for (String c : findustrydata1) {

                        for (Company_Data obj : appliedProject_data) {

                            for (String obj1 : obj.getIndustries()) {

                                if (c.equalsIgnoreCase(obj1)) {

                                    Log.e("aobsales", "pop " + obj1);
                                    Log.e("aobsales", "pop " + obj.getProject_name());

                                    filterArrayList1.add(obj);

                                }

                            }
                        }
                    }
                }

                if (fstatedata1.size() > 0) {
                    for (String c : fstatedata1) {

                        for (Company_Data obj : appliedProject_data) {

                            for (String obj1 : obj.getState()) {

                                if (c.equalsIgnoreCase(obj1)) {

                                    filterArrayList1.add(obj);

                                }

                            }
                        }
                    }
                }

                if(filterArrayList1.size() >0)
                {
                    filteredList1.clear();
                    filteredList1.addAll(filterArrayList1);
                    appliedProjectadapter.notifyDataSetChanged();
                }

            }catch (Exception e){}
        }*/

    }



    private void myUpdateOperationSwipe() {
        if( appliedProject_data != null) {

            searchBoxApplied.setText("");
            appliedProject_data.clear();
            filteredList1.clear();
            sortArrayList1.clear();
            filterArrayList1.clear();
            filter_state = "";
            filter_industry = "";

            CheckSort1 = "none";
            ChecKFilter1 = "inactive";

            appliedProjectadapter.notifyDataSetChanged();
            getProjectMain();

        }
    }


    private void getProjectMain(){



        swiperefresh_appliedprojects.setRefreshing(true);

        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                getResources().getString(R.string.hostName)+"/app/sales/v1_0/refresh_my_project.php?",
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {

                        Log.e("aobsales","reesponserefresh "+response.toString());

                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String success=jsonObject.getString("status");

                            if (success.equals("0")){

                                JSONArray jsonArrayall = jsonObject.getJSONArray("my_project");
                                if(jsonArrayall.length() >0)
                                {
                                    String  response1 = jsonArrayall.toString();
                                    appliedProject_data = new ArrayList<Company_Data>(Arrays.asList(gson.fromJson(response1, Company_Data[].class)));
                                    filteredList1.addAll(appliedProject_data);
                                    sortArrayList1.addAll(appliedProject_data);
                                    appliedProjectadapter.notifyDataSetChanged();

                                    recyclerView_applied.setVisibility(View.VISIBLE);
                                    tv_no_data_lead.setVisibility(View.INVISIBLE);

                                }
                                else
                                {
                                    recyclerView_applied.setVisibility(View.INVISIBLE);
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

                        swiperefresh_appliedprojects.setRefreshing(false);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swiperefresh_appliedprojects.setRefreshing(false);
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
