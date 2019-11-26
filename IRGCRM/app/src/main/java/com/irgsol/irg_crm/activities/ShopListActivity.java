package com.irgsol.irg_crm.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import com.irgsol.irg_crm.R;
import com.irgsol.irg_crm.adapters.AdapterShopList;
import com.irgsol.irg_crm.common.OprActivity;
import com.irgsol.irg_crm.models.ModelShopList;
import java.util.ArrayList;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ShopListActivity extends AppCompatActivity {

    Context context;
    Toolbar toolbar;
    public static RecyclerView recyclerView;
    public static RecyclerView.LayoutManager layoutManager;
    public static LinearLayout llEmptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);
        initView();
    }

    private void initView() {
        context = ShopListActivity.this;
        toolbar = findViewById(R.id.toolbar);
        OprActivity.setUpToolbarWithTitle(toolbar, "Our Shops",context );

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        llEmptyView = findViewById(R.id.llEmptyView);
        setupDasboard();
    }

    private void setupDasboard() {
        int [] shopImg = {R.drawable.app_logo,R.drawable.app_logo,R.drawable.app_logo,R.drawable.app_logo,R.drawable.app_logo,R.drawable.app_logo,R.drawable.app_logo,R.drawable.app_logo,R.drawable.app_logo,R.drawable.app_logo};
        String [] shopName= {"Durga Agarbatti Store", "Pawan Agarbatti Comp.", "Jaiswal Kirana Store",
                "Star Agarbatti Collection", "Rajesh Agarbatti", "Shubh Mangal Agarbatti",
                "Shri Sai Agarbatti", "Mangal Agarbatti","Shiv Agarbatti Shop", "Rajdeep Agarbatti"};
        String [] shopId= {"PW001", "PW002", "PW003", "PW004", "PW005", "PW006","PW007", "PW008",
                "PW009","PW0010"};
        String [] shopOwnerName= {"Pawan Jaiswal", "Susheel Kumar", "Sanjeev Kumar", "ShivShankar Singh",
                "Arjun Gupta", "Nitesh Yadav","Amit Jaiswal", "Ankit Tiwari", "Ravi Singh","Dharmendra"};

        String [] shopAddress= {"Indore", "Delhi", "Kanpur", "Lucjnow",
                "Gwaliar", "Gaziabad","Varansi", "Noida", "Allahabad","Nagda"};


        List<ModelShopList> shopList = new ArrayList<ModelShopList>();
        for(int i=0;i<shopId.length;i++){
            ModelShopList modelShopList= new ModelShopList();
            modelShopList.setShopImg(shopImg[i]);
            modelShopList.setShopName(shopName[i]);
            modelShopList.setShopId(shopId[i]);
            modelShopList.setShopOwnerName(shopOwnerName[i]);
            modelShopList.setShopAddress(shopAddress[i]);
            shopList.add(modelShopList);
        }

        AdapterShopList adapter = new AdapterShopList(context, shopList);
        recyclerView.setVisibility(View.VISIBLE);
       // recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }


    @Override
    public boolean onSupportNavigateUp() {
        OprActivity.finishActivity(context);
        return true;
    }

    @Override
    public void onBackPressed() {
        OprActivity.finishActivity(context);
    }

}
