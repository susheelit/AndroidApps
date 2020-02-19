package com.e.salestracker.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.e.salestracker.Adapter.AdminLocationAdapter;
import com.e.salestracker.Modal.PositionModal;
import com.e.salestracker.R;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class ProjectViseLocationActivty extends AppCompatActivity {

    private TextView tv_no_data_lead;
    private RecyclerView recyclerView;
    private AdminLocationAdapter mAdapter;
    private ProgressDialog progressDialog;
    private ImageView back_image;
    private TextView main_name;
    List<PositionModal> items = new ArrayList<>();
    SwipeRefreshLayout swiperefresh_myleads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_vise_location_activty);

        initToolbar();
        //initComponent();
        swiperefresh_myleads.setRefreshing(true);
        swiperefresh_myleads.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myUpdateOperation();
            }
        });

       // initToolbar();
        initComponent();
    }

    private void initToolbar() {
        tv_no_data_lead = findViewById(R.id.tv_no_data_lead);
        swiperefresh_myleads = findViewById(R.id.swiperefresh_myleads);
        progressDialog = new ProgressDialog(this);
        back_image = findViewById(R.id.back_image);
        main_name = findViewById(R.id.main_name);
        String projectName = getIntent().getExtras().getString("Project");
        main_name.setText(projectName);

        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void myUpdateOperation() {
        initComponent();
    }

    private void initComponent() {
        items.clear();
        recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        gridLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        if(swiperefresh_myleads.isRefreshing()) {
            swiperefresh_myleads.setRefreshing(false);
        }
        String location = getIntent().getExtras().getString("Location");
        JSONArray jsonArraylocation = null;
        try {
            jsonArraylocation = new JSONArray(location);

            if(jsonArraylocation.getString(0).isEmpty()){
                items.clear();
            }else {

                for (int i = 0; i < jsonArraylocation.length(); i++) {
                    PositionModal positionModal = new PositionModal(jsonArraylocation.getString(i));
                    items.add(positionModal);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        mAdapter = new AdminLocationAdapter(ProjectViseLocationActivty.this, items);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new AdminLocationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, PositionModal obj, int position) {

                Log.e("Location", ""+obj.getName());
                Intent intent = new Intent(ProjectViseLocationActivty.this, PersonListActivty.class);
                intent.putExtra("Location", obj.getName());
                intent.putExtra("Project", getIntent().getExtras().getString("Project"));
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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}