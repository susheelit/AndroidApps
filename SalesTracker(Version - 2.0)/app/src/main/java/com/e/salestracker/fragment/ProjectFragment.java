package com.e.salestracker.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.e.salestracker.Activity.ProjectViseLocationActivty;
import com.e.salestracker.Adapter.AdminProjectAdapter;
import com.e.salestracker.Modal.PageViewModel;
import com.e.salestracker.Modal.ProjectModel;
import com.e.salestracker.R;
import com.e.salestracker.Utility.InternetConnection;
import com.e.salestracker.Utility.MySingleton;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class ProjectFragment extends Fragment {

    private TextView tv_no_data_lead;
    private RecyclerView recyclerView;
    private AdminProjectAdapter mAdapter;
    private ItemTouchHelper mItemTouchHelper;
    private ImageView profile;
    List<ProjectModel> items = new ArrayList<>();
    SwipeRefreshLayout swiperefresh_myleads;
    private PageViewModel pageViewModel;
    Context context;

    public ProjectFragment() {}

    public static ProjectFragment newInstance() {
        return new ProjectFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        pageViewModel.setIndex("Project");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.fragment_project, container, false);
        context = getActivity().getApplicationContext();
        initToolbar(root);
        initComponent(root);
        swiperefresh_myleads.setRefreshing(true);
        swiperefresh_myleads.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myUpdateOperation(root);
            }
        });
        return root;
    }

    private void initToolbar(View root) {
        tv_no_data_lead = root.findViewById(R.id.tv_no_data_lead);
        swiperefresh_myleads = root.findViewById(R.id.swiperefresh_myleads);
    }

    private void myUpdateOperation(View root) {
        initComponent(root);
    }

    private void initComponent(final View root) {

        recyclerView = root.findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
        gridLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(gridLayoutManager);

        String locUrl = getResources().getString(R.string.host_name) + "/sales_tracker/new_api/project_location_api.php";

        /*if (ShareprefreancesUtility.getInstance().getString("role").compareToIgnoreCase("client") == 0) {

            if(Config.getProjectName().compareToIgnoreCase("PM Cona")==0) {

                locUrl = getResources().getString(R.string.host_name)+"/sales_tracker/project_city_list_for_testing_cona.php?project_name=pm+cona&agent_type=dedicated";
            }else{
                locUrl = getResources().getString(R.string.host_name)+"/sales_tracker/project_city_list_for_testing_cona.php?project_name=none&agent_type=shared";
            }
        }else{
            locUrl = getResources().getString(R.string.host_name)+"/sales_tracker/project_city_list.php" ;
        }*/
        // locUrl = getResources().getString(R.string.host_name)+"/sales_tracker/project_city_list.php" ;

        Log.e("locUrl ", "" + locUrl);

        if (InternetConnection.checkInternetConnectivity()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    locUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            swiperefresh_myleads.setRefreshing(false);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("Data");
                                    if (items != null)
                                        items.clear();
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                        JSONArray locationjsonArray1 = jsonObject1.getJSONArray("location");

                                        ProjectModel projectModel = new ProjectModel(jsonObject1.getString("project_id"),
                                                jsonObject1.getString("project_name"), locationjsonArray1);
                                        items.add(projectModel);
                                    }
                                    mAdapter = new AdminProjectAdapter(context, items);
                                    recyclerView.setAdapter(mAdapter);
                                    mAdapter.setOnItemClickListener(new AdminProjectAdapter.OnItemClickListener() {

                                        @Override
                                        public void onItemClick(View view, ProjectModel obj, int position) {

                                                String loc = obj.getLocation().toString();
                                                Log.e("loc", "loc "+loc);

                                                Intent intent = new Intent(context, ProjectViseLocationActivty.class);
                                                intent.putExtra("Project", obj.getProject_name());
                                                intent.putExtra("Location", obj.getLocation().toString());
                                                startActivity(intent);
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
                                    Toast.makeText(context, jsonObject.getString("status"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    swiperefresh_myleads.setRefreshing(false);
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                    builder1.setMessage("Check your internet connection");
                    builder1.setCancelable(false);
                    builder1.setPositiveButton(
                            "Retry",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    myUpdateOperation(root);
                                    dialog.cancel();
                                }
                            });
                    builder1.setNegativeButton(
                            "Close",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    ((AppCompatActivity) context).finish();
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
            MySingleton.getInstance(context).addToRequestQueue(stringRequest);
        } else {
            swiperefresh_myleads.setRefreshing(false);
            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
            builder1.setMessage("Retry with Internet connection");
            builder1.setCancelable(false);
            builder1.setPositiveButton(
                    "Retry",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            myUpdateOperation(root);
                            dialog.cancel();
                        }
                    });
            builder1.setNegativeButton(
                    "Close",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ((AppCompatActivity) context).finish();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
    }
}