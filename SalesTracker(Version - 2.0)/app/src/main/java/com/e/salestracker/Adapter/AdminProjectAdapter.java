package com.e.salestracker.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.e.salestracker.Modal.PositionModal;
import com.e.salestracker.Modal.ProjectModel;
import com.e.salestracker.R;
import com.e.salestracker.helper.SwipeItemTouchHelper;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class AdminProjectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<ProjectModel> items = new ArrayList<>();
    private List<ProjectModel> items_swiped = new ArrayList<>();

    private Context ctx;
    private AdminProjectAdapter.OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, ProjectModel obj, int position);
    }

    public void setOnItemClickListener(final AdminProjectAdapter.OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AdminProjectAdapter(Context context, List<ProjectModel> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder implements SwipeItemTouchHelper.TouchViewHolder {
        public ImageView image;
        public TextView name;
        public ImageButton bt_move;
        public Button bt_undo;
        public View lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);
            name=v.findViewById(R.id.name);
            lyt_parent=v.findViewById(R.id.lyt_parent);
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.position_adapter, parent, false);
        vh = new AdminProjectAdapter.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof AdminProjectAdapter.OriginalViewHolder) {
            final AdminProjectAdapter.OriginalViewHolder view = (AdminProjectAdapter.OriginalViewHolder) holder;

            final ProjectModel p = items.get(position);

            Log.e("ProjectLocation", ""+p.getLocation());
            view.name.setText(p.getProject_name());
            //  Tools.displayImageOriginal(ctx, view.image, p.image);
            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, items.get(position), position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
