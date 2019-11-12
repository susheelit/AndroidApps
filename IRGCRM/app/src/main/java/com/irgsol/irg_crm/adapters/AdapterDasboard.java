package com.irgsol.irg_crm.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.irgsol.irg_crm.R;
import com.irgsol.irg_crm.activities.AboutUsActivity;
import com.irgsol.irg_crm.activities.AddShopActivity_1;
import com.irgsol.irg_crm.activities.DasboardActivity;
import com.irgsol.irg_crm.activities.ChangePassActivity;
import com.irgsol.irg_crm.activities.ContactUsActivity;
import com.irgsol.irg_crm.activities.HistoryActivity;
import com.irgsol.irg_crm.activities.ShopListActivity;
import com.irgsol.irg_crm.common.OprActivity;
import com.irgsol.irg_crm.models.ModelDasboard;
import java.util.ArrayList;
import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by hdi on 1/2/18.
 */
public class AdapterDasboard extends RecyclerView.Adapter<AdapterDasboard.MyViewHolder> {

    List<ModelDasboard> modelDasboardList = new ArrayList<ModelDasboard>();
    Context context;

    public AdapterDasboard(Context context, List<ModelDasboard> modelDasboardList) {
        this.context = context;
        this.modelDasboardList = modelDasboardList;
    }

    @Override
    public AdapterDasboard.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_dasboard, parent, false);
        AdapterDasboard.MyViewHolder myViewHolder = new AdapterDasboard.MyViewHolder(view);
        //return myViewHolder;

        int height = parent.getMeasuredHeight() / 4;
        height = height-8;
        view.setMinimumHeight(height);
        return new AdapterDasboard.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterDasboard.MyViewHolder holder, final int position) {

        ModelDasboard modelDasboard = modelDasboardList.get(position);

        final String subTitleName = modelDasboard.getTitleName();

       // final String serviceId = modelSubService.getServiceCategoryID();

        int imgUrl = modelDasboard.getIcons();
        int imageResource = context.getResources().getIdentifier(""+imgUrl, null, context.getPackageName());

        Drawable res = context.getResources().getDrawable(imageResource);
        holder.ivTitleImg.setImageDrawable(res);

        holder.tvTitleName.setText(subTitleName);

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               gotoActivity(position);
            }
        });
    }

    private void gotoActivity(int position) {
        if(position==0){
            OprActivity.openActivity(context, new AddShopActivity_1());
        }
        if(position==1){
            OprActivity.openActivity(context, new ShopListActivity());
        }
        if(position==2){
            OprActivity.openActivity(context, new HistoryActivity());
        }

        if(position==3){
            OprActivity.openActivity(context, new ChangePassActivity());
        }

        if(position==4){
            OprActivity.openActivity(context, new ContactUsActivity());
        }
        if(position==5){
            OprActivity.openActivity(context, new AboutUsActivity());
        }

        if(position==6){
           // OprActivity.openActivity(context, new RateUsActivity());
        }

        if(position==7){
            OprActivity.logoutUser(context);
            OprActivity.finishAllOpenNewActivity(context,new DasboardActivity());
        }

    }

    @Override
    public int getItemCount() {
        return modelDasboardList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        CardView card_view;
        TextView tvTitleName;
        ImageView ivTitleImg;

        MyViewHolder(View itemView) {
            super(itemView);
            card_view = itemView.findViewById(R.id.card_view);
            ivTitleImg = itemView.findViewById(R.id.ivTitleImg);
            tvTitleName = itemView.findViewById(R.id.tvTitleName);
        }
    }

}
