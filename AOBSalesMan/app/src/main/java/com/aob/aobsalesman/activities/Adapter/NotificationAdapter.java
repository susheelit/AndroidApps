package com.aob.aobsalesman.activities.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.aob.aobsalesman.R;
import com.aob.aobsalesman.activities.model.NotificationModal;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private List<NotificationModal> items = new ArrayList<>();


    private Context ctx;
    private OnItemClickListener mOnItemClickListener;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OriginalViewHolder) {
            final OriginalViewHolder view = (OriginalViewHolder) holder;
            NotificationModal p = items.get(position);

            view.header.setText(p.getHeader());
            view.body.setText(p.getBody());
            view.date.setText(p.getDate());
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, NotificationModal obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public NotificationAdapter(Context context, List<NotificationModal> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView header;
        public TextView body;
        public TextView date;

        public ImageButton bt_move;
        public Button bt_undo;
        public View lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);

            date = (TextView) v.findViewById(R.id.date);
            header = (TextView) v.findViewById(R.id.header);
            body = (TextView) v.findViewById(R.id.body);


            lyt_parent = (View) v.findViewById(R.id.lyt_parent);
        }

    }

}