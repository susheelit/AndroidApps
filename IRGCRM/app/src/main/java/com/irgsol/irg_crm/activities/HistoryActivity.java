package com.irgsol.irg_crm.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import com.irgsol.irg_crm.R;
import com.irgsol.irg_crm.adapters.AdapterHistory;
import com.irgsol.irg_crm.common.OprActivity;
import com.irgsol.irg_crm.models.ModelHistoryList;
import java.util.ArrayList;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryActivity extends AppCompatActivity {

    Context context;
    Toolbar toolbar;
    public static RecyclerView recyclerView;
    public static RecyclerView.LayoutManager layoutManager;
    public static LinearLayout llEmptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initView();
    }

    private void initView() {
        context = HistoryActivity.this;
        toolbar = findViewById(R.id.toolbar);
        OprActivity.setUpToolbarWithTitle(toolbar, "Order History", context);

        recyclerView = findViewById(R.id.recycler_view);
        llEmptyView = findViewById(R.id.llEmptyView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        setupDasboard();
    }

    private void setupDasboard() {

        int[] shopImg = {R.drawable.app_logo, R.drawable.app_logo, R.drawable.app_logo, R.drawable.app_logo, R.drawable.app_logo, R.drawable.app_logo, R.drawable.app_logo, R.drawable.app_logo, R.drawable.app_logo, R.drawable.app_logo};
        String[] shopName = {"Durga Agarbatti Store", "Pawan Agarbatti Comp.", "Jaiswal Kirana Store",
                "Star Agarbatti Collection", "Rajesh Agarbatti", "Shubh Mangal Agarbatti",
                "Shri Sai Agarbatti", "Mangal Agarbatti", "Shiv Agarbatti Shop", "Rajdeep Agarbatti"};
        String[] shopId = {"PW001", "PW002", "PW003", "PW004", "PW005", "PW006", "PW007", "PW008",
                "PW009", "PW0010"};
        String[] ownerName = {"Pawan Jaiswal", "Susheel Kumar", "Sanjeev Kumar", "ShivShankar Singh",
                "Arjun Gupta", "Nitesh Yadav", "Amit Jaiswal", "Ankit Tiwari", "Ravi Singh", "Dharmendra"};
        String[] shopAddress = {"Indore", "Delhi", "Kanpur", "Lucjnow",
                "Gwaliar", "Gaziabad", "Varansi", "Noida", "Allahabad", "Nagda"};
        String[] orderDate = {"13/03/2019", "11/03/2019", "09/03/2019", "05/03/2019", "01/03/2019", "28/02/2019", "27/02/2019", "25/02/2019", "25/02/2019", "24/02/2019"};
        String[] orderStatus = {"1", "0", "2", "1", "0", "2", "1", "0", "2", "1"};
        String[] paymentStatus = {"1", "0", "2", "1", "0", "2", "1", "0", "2", "1"};
        String[] totalItem = {"10", "3", "5", "2", "6", "8", "3", "5", "4", "1"};
        String[] totalPrice = {"10000", "3400", "5600", "2550", "6290", "8700", "3343", "5890", "4320", "1100"};

        List<ModelHistoryList> historyLists = new ArrayList<ModelHistoryList>();
        for (int i = 0; i < shopId.length; i++) {
            ModelHistoryList modelhistoryList = new ModelHistoryList();

            modelhistoryList.setShopImg(shopImg[i]);
            modelhistoryList.setShopName(shopName[i]);
            modelhistoryList.setShopId(shopId[i]);
            modelhistoryList.setOwnerName(ownerName[i]);
            modelhistoryList.setShopAddress(shopAddress[i]);
            modelhistoryList.setOrderDate(orderDate[i]);
            modelhistoryList.setOrderStatus(orderStatus[i]);
            modelhistoryList.setPaymentStatus(paymentStatus[i]);
            modelhistoryList.setTotalItem(totalItem[i]);
            modelhistoryList.setTotalPrice(totalPrice[i]);
            historyLists.add(modelhistoryList);
        }

        AdapterHistory adapter = new AdapterHistory(context, historyLists);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
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
