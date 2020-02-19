package com.e.salestracker.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.e.salestracker.Modal.People;
import com.e.salestracker.Utility.ItemAnimation;
import com.e.salestracker.R;
import com.e.salestracker.Utility.Tools;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<People> items = new ArrayList<>();
    public boolean isClickable = true;
    private Context ctx;
    private OnItemClickListener mOnItemClickListener;
    private int animation_type = 4;
    public View v;

    public interface OnItemClickListener {
        void onItemClick(View view, People obj, int position, RecyclerView.ViewHolder holder);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public MainAdapter(Context context, List<People> items ) {
        ctx = context;
        this.items=items;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public ImageView action;
        public TextView name;
        public TextView date;
        public TextView intime;
        public TextView outtime;
        public View lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);
            image = v.findViewById(R.id.image);
            action = v.findViewById(R.id.action);
            name = v.findViewById(R.id.name);
            date = v.findViewById(R.id.date);
            intime = v.findViewById(R.id.intime);
            outtime = v.findViewById(R.id.outtime);
            lyt_parent = v.findViewById(R.id.lyt_parent);
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_people_chat, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        Log.e("onBindViewHolder", "onBindViewHolder : " + position);
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;
            People p = items.get(position);

            if (p.getVisited().equalsIgnoreCase("office")){
                view.name.setText("Office");}
            else{
                view.name.setText(p.getClient_name());}
            if (p.getCheckin_date().equalsIgnoreCase("null")){
                view.date.setText("");}
            else{
                view.date.setText(getDate(p.getCheckin_date()));}
            if (p.getCheckin_time()==null){
                view.intime.setText("");}
            else{
                view.intime.setText(p.getCheckin_time());}
            if (p.getCheckout_time().equalsIgnoreCase("null")){
                view.outtime.setText("");}
            else{
                view.outtime.setText(p.getCheckout_time());
            }
            Tools.displayImageRound(ctx,view.image,p.getImage());
            if (p.getDsr_status().equalsIgnoreCase("complete")){
                view.action.setImageResource(R.drawable.button_edit);
            }else{
               // if (p.getAgent_cc_status().equalsIgnoreCase("pending")){
                      // view.action.setImageResource(R.drawable.button_7);
                /*} else {
                  view.action.setImageResource(R.drawable.dsr_panding);
                }*/

                // new 7 oct 2019
                if (p.getAgent_cc_status().equalsIgnoreCase("pending")){
                    view.action.setImageResource(R.drawable.button_7);
                } else {
                  view.action.setImageResource(R.drawable.dsr_panding);
                }

            }
            view.action.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                            if (mOnItemClickListener != null) {
                                mOnItemClickListener.onItemClick(view, items.get(position), position, holder);
                                view.setClickable(false);
                            }
                    }
            });
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                on_attach = false;
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private int lastPosition = -1;
    private boolean on_attach = true;

    private void setAnimation(View view, int position) {
        if (position > lastPosition) {
            ItemAnimation.animate(view, on_attach ? position : -1, animation_type);
            lastPosition = position;
        }
    }

    private String getDate(String date){
        StringTokenizer st = new StringTokenizer(date, "-");
        String Year=st.nextToken();
        String month=st.nextToken();
        String Date=st.nextToken();

        String newDate=Date+"/"+month+"/"+Year;
        return  newDate;
    }
}