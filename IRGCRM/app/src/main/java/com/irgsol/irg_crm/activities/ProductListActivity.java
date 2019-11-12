package com.irgsol.irg_crm.activities;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.irgsol.irg_crm.R;
import com.irgsol.irg_crm.adapters.AdapterProductList;
import com.irgsol.irg_crm.common.OprActivity;
import com.irgsol.irg_crm.models.ModelProductList;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ProductListActivity extends AppCompatActivity {

    Context context;
    Toolbar toolbar;
    public static RecyclerView recyclerView;
    public static RecyclerView.LayoutManager layoutManager;
    public static LinearLayout llEmptyView;
    private float count=1;
    public static TextView tvTotalAmt;
    public static androidx.appcompat.widget.AppCompatButton btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productlist);
        initView();
    }

    private void initView() {
        context = ProductListActivity.this;
        toolbar = findViewById(R.id.toolbar);
        OprActivity.setUpToolbarWithTitle(toolbar, "Product",context );

        recyclerView = findViewById(R.id.recycler_view);
        llEmptyView = findViewById(R.id.llEmptyView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        tvTotalAmt = findViewById(R.id.tvTotalAmt);
        btnNext = findViewById(R.id.btnNext);
        setupDasboard();
    }

    private void setupDasboard() {
        int [] itemImg = {R.drawable.app_logo,R.drawable.app_logo,R.drawable.app_logo,R.drawable.app_logo,R.drawable.app_logo,R.drawable.app_logo,R.drawable.app_logo,R.drawable.app_logo,R.drawable.app_logo,R.drawable.app_logo};
        String [] title= {"Agarbatti 1", "Agarbatti 2", "Agrbatti 3", "Agarbatti 4", "Agarbatti 5", "Agarbatti 6","Agarbatti 7", "Agarbatti 8", "Agarbatti 9","Agarbatti 10"};
        String [] qty= {"200 gm", "300 gm", "40 gm", "100 gm", "200 gm", "100 gm","30 gm", "400 gm", "200 gm","1 kg"};
        String [] price= {"50", "100", "30", "55", "35", "60","40", "100", "200","500"};
        List<ModelProductList> titlelist = new ArrayList<ModelProductList>();
        for(int i=0;i<title.length;i++){
            ModelProductList modelProductList= new ModelProductList();
            modelProductList.setItemImg(itemImg[i]);
            modelProductList.setTitle(title[i]);
            modelProductList.setQty(qty[i]);
            modelProductList.setPrice(price[i]);
            titlelist.add(modelProductList);
        }

        AdapterProductList adapter = new AdapterProductList(context, titlelist);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }



    public void addItemDialog( final ModelProductList modelProductList) {

        // custom dialog
        final TextView item_name, userInput, unit, rate, totalAmt;
        androidx.appcompat.widget.AppCompatButton btnOk, cancel;

        final Dialog dialog = new Dialog(context);

        // dialog.setTitle("Title...");
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_layout);


        item_name = dialog.findViewById(R.id.item_name);
        userInput = dialog.findViewById(R.id.userInput);
        unit = dialog.findViewById(R.id.unit);
        rate = dialog.findViewById(R.id.rate);
        totalAmt = dialog.findViewById(R.id.totalAmt);
        btnOk = dialog.findViewById(R.id.ok);
        cancel= dialog.findViewById(R.id.cancel);

        item_name.setText(modelProductList.getTitle());
       // unit.setText(modelProductList.getQty());
        rate.setText(modelProductList.getPrice());
        item_name.setText(modelProductList.getTitle());
        item_name.setText(modelProductList.getTitle());


        // ontext change qty
        userInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count1) {
                // checkDetails(s, modelProductList);

                if (count1 > 0) {
                    if(s.toString().compareTo(".")==0){
                        userInput.setText(""+0.);
                        s= "0.";
                    }

                    count = Float.parseFloat(String.valueOf(s));

                    String qty = userInput.getText().toString();
                    String amt = modelProductList.getPrice().toString();
                    totalAmt.setText(""+ totalAmt(qty, amt));
                  //  Toast.makeText(context, ""+ totalAmt(qty, amt), Toast.LENGTH_LONG).show();
                } else {
                    count = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        // if androidx.appcompat.widget.AppCompatButton is clicked, close the custom dialog
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }


    public double totalAmt(String qty1, String amt1){

        double qty = Double.parseDouble(qty1);
        double amt,total;
        amt = Double.parseDouble(amt1);
        total = qty *amt;
        total =Double.parseDouble(new DecimalFormat("##.##").format(total));
        return total;
    }

    private void checkDetails(CharSequence s, ModelProductList modelProductList) {


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
