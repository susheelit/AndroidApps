package com.irgsol.irg_crm.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import com.irgsol.irg_crm.R;
import com.irgsol.irg_crm.adapters.AdapterDasboard;
import com.irgsol.irg_crm.common.OprActivity;
import com.irgsol.irg_crm.models.ModelDasboard;
import com.irgsol.irg_crm.utils.Config;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DasboardActivity extends AppCompatActivity {

    Context context;
    Toolbar toolbar;
    public static RecyclerView recyclerView;
    public static RecyclerView.LayoutManager layoutManager;
    public static LinearLayout llEmptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dasboard);
        initView();
    }

    private void initView() {
        context = DasboardActivity.this;
        toolbar = findViewById(R.id.toolbar);
        OprActivity.setUpToolbarWithoutBack(toolbar, context);

        recyclerView = findViewById(R.id.recycler_view);
        llEmptyView = findViewById(R.id.llEmptyView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(layoutManager);
        setupDasboard();
    }

    private void setupDasboard() {
        String [] titlel= {"Add Shop", "Book Order", "History",  "Change Password", "Contact Us", "About Us", "Rate us","Logout"};
        int [] icons= {R.drawable.ic_addshop, R.drawable.ic_bookorder, R.drawable.ic_history, R.drawable.ic_password,  R.drawable.ic_contactus, R.drawable.ic_aboutus, R.drawable.ic_rateus, R.drawable.ic_logout};
        List<ModelDasboard> titlelist = new ArrayList<ModelDasboard>();
        for(int i=0;i<titlel.length;i++){
            ModelDasboard modelDasboard= new ModelDasboard();
            modelDasboard.setTitleName(titlel[i]);
            modelDasboard.setIcons(icons[i]);
            titlelist.add(modelDasboard);
        }

        AdapterDasboard adapter = new AdapterDasboard(context, titlelist);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        Config.alertClose("Do you want to close", context);
    }
}
