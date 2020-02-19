package com.e.salestracker.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.e.salestracker.Modal.SalesManModal;
import com.e.salestracker.R;
import com.e.salestracker.helper.SwipeItemTouchHelper;
import java.util.ArrayList;
import java.util.List;
import androidx.recyclerview.widget.RecyclerView;

public class SalesmanHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private List<SalesManModal> items = new ArrayList<>();
    private List<SalesManModal> items_swiped = new ArrayList<>();

    private Context ctx;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, SalesManModal obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public SalesmanHistoryAdapter(Context context, List<SalesManModal> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder implements SwipeItemTouchHelper.TouchViewHolder {
        public ImageView image;
        public TextView email;
        public TextView date;
        public TextView count;
        public TextView Name;
       // public TextView Status;
        public ImageButton bt_move;
        public Button bt_undo;
        public View lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);
            email = v.findViewById(R.id.email);
            date = v.findViewById(R.id.date);
            count = v.findViewById(R.id.count);
            Name = v.findViewById(R.id.name);
          //  Status = (TextView) v.findViewById(R.id.status);

            lyt_parent = v.findViewById(R.id.lyt_parent);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(ctx.getResources().getColor(R.color.grey_5));
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_salesman, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            final OriginalViewHolder view = (OriginalViewHolder) holder;
            view.count.setText(Integer.toString(position+1));
            final SalesManModal p = items.get(position);
            view.email.setText(p.getEmailId());
            view.date.setText(p.getDate());
            view.Name.setText(p.getName());
          //  view.Status.setText(p.getStatus());

           /* view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, items.get(position), position);
                    }
                }
            });*/


        }
    }
    @Override
    public int getItemCount() {
        return items.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                for (SalesManModal s : items_swiped) {
                    int index_removed = items.indexOf(s);
                    if (index_removed != -1) {
                        items.remove(index_removed);
                        notifyItemRemoved(index_removed);
                    }
                }
                items_swiped.clear();
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        super.onAttachedToRecyclerView(recyclerView);
    }


}