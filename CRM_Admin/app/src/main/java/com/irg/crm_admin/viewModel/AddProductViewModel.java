package com.irg.crm_admin.viewModel;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.irg.crm_admin.activity.AddProductActivity;
import com.irg.crm_admin.common.OprActivity;
import com.irg.crm_admin.model.ModelProduct;

public class AddProductViewModel extends ViewModel {

    public MutableLiveData<String> prod_id = new MutableLiveData<>();
    public MutableLiveData<String> prod_name = new MutableLiveData<>();
    public MutableLiveData<String> prod_img = new MutableLiveData<>();
    public MutableLiveData<String> prod_mrp = new MutableLiveData<>();
    public MutableLiveData<String> prod_price = new MutableLiveData<>();
    public MutableLiveData<String> prod_qty = new MutableLiveData<>();
    public MutableLiveData<String> prod_type = new MutableLiveData<>();
    public MutableLiveData<String> total_sticks = new MutableLiveData<>();
    public MutableLiveData<String> prod_weight = new MutableLiveData<>();
    public MutableLiveData<String> prod_color = new MutableLiveData<>();
    public MutableLiveData<String> prod_sent = new MutableLiveData<>();
    public MutableLiveData<String> prod_company = new MutableLiveData<>();
    public MutableLiveData<String> prod_brand = new MutableLiveData<>();
    public MutableLiveData<String> prod_code = new MutableLiveData<>();
    public MutableLiveData<String> prod_offer = new MutableLiveData<>();
    public MutableLiveData<String> prod_instock = new MutableLiveData<>();
    public MutableLiveData<String> prod_desc = new MutableLiveData<>();

    public MutableLiveData<String> mTitle = new MutableLiveData<>();

    private MutableLiveData<ModelProduct> prodMutableLiveData;

    public MutableLiveData<ModelProduct> getUser(Context context) {

        if (prodMutableLiveData == null) {
            prodMutableLiveData = new MutableLiveData<>();

            String prodType = ((AppCompatActivity) context).getIntent().getStringExtra("prodType");
            mTitle.setValue(prodType);
            if (prodType.equalsIgnoreCase("Update")) {
                setDetails(context);
            }
        }
        return prodMutableLiveData;
    }

    private void setDetails(Context context) {

        ModelProduct modelProduct = (ModelProduct) ((AppCompatActivity) context).getIntent().getSerializableExtra("productDetails");

        if (modelProduct != null) {
            prod_id.setValue(modelProduct.getProd_id());
            prod_name.setValue(modelProduct.getProd_name());
            prod_img.setValue(modelProduct.getProd_img());
            prod_mrp.setValue(modelProduct.getProd_mrp());
            prod_price.setValue(modelProduct.getProd_price());
            prod_qty.setValue(modelProduct.getProd_qty());
            prod_type.setValue(modelProduct.getProd_type());
            total_sticks.setValue(modelProduct.getTotal_sticks());
            prod_weight.setValue(modelProduct.getProd_weight());
            prod_color.setValue(modelProduct.getProd_color());
            prod_sent.setValue(modelProduct.getProd_sent());
            prod_company.setValue(modelProduct.getProd_company());
            prod_brand.setValue(modelProduct.getProd_brand());
            prod_code.setValue(modelProduct.getProd_code());
            prod_offer.setValue(modelProduct.getProd_offer());
            prod_instock.setValue(modelProduct.getProd_instock());
            prod_desc.setValue(modelProduct.getProd_desc());

        }
    }

    public void onClickBackArrow(View view) {
        Context context = ((ContextWrapper) view.getContext()).getBaseContext();
        OprActivity.finishActivity(context);
    }

    /*Login*/
    public void onClickAddProduct(View view) {
        ModelProduct modelProduct = new ModelProduct(prod_id.getValue(),prod_name.getValue(), prod_img.getValue(), prod_mrp.getValue(),
                prod_price.getValue(), prod_qty.getValue(), prod_type.getValue(), total_sticks.getValue(), prod_weight.getValue(),
                prod_color.getValue(), prod_sent.getValue(), prod_company.getValue(), prod_brand.getValue(),
                prod_code.getValue(), prod_offer.getValue(), prod_instock.getValue(), prod_desc.getValue(), "1");
        prodMutableLiveData.setValue(modelProduct);
    }

    /*Registration*/
    public void onClickUploadImage(View view) {
        AddProductActivity.showPreview(view);
    }

    @BindingAdapter("onNavigationBackClick")
    public static void onNavigationBackClick(Toolbar toolbar, int b) {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do here what you want..
                ((AppCompatActivity) v.getContext()).finish();
            }
        });
    }

}
