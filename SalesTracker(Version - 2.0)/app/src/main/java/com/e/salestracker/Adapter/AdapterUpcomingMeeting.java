package com.e.salestracker.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.e.salestracker.Modal.UpcomingMeetingModal;
import com.e.salestracker.R;
import com.e.salestracker.helper.SwipeItemTouchHelper;
import java.util.ArrayList;
import java.util.List;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterUpcomingMeeting extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private List<UpcomingMeetingModal> items = new ArrayList<>();
    private Context ctx;

    public AdapterUpcomingMeeting(Context context, List<UpcomingMeetingModal> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder implements SwipeItemTouchHelper.TouchViewHolder {

        public TextView tvProject,tvVisit_Type,tvClient,tvLastVisit,tvDescription,tvNextMeeting;

        public OriginalViewHolder(View v) {
            super(v);
           // image = v.findViewById(R.id.image);
            tvProject = v.findViewById(R.id.tvProject);
            tvVisit_Type = v.findViewById(R.id.tvVisit_Type);
            tvClient = v.findViewById(R.id.tvClient);
            tvLastVisit = v.findViewById(R.id.tvLastVisit);
            tvDescription = v.findViewById(R.id.tvDescription);
            tvNextMeeting = v.findViewById(R.id.tvNextMeeting);
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.upcoming_meeting_layout, parent, false);
        vh = new AdapterUpcomingMeeting.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof AdapterUpcomingMeeting.OriginalViewHolder) {
            final AdapterUpcomingMeeting.OriginalViewHolder view = (AdapterUpcomingMeeting.OriginalViewHolder) holder;

            final UpcomingMeetingModal p = items.get(position);
            view.tvProject.setText(p.getProject_name_form());
            view.tvVisit_Type.setText(p.getVisit_type());
            view.tvClient.setText(p.getClient_name());
            view.tvLastVisit.setText(p.getLast_visited());
            view.tvDescription.setText(p.getDescription());
            view.tvNextMeeting.setText(p.getNext_meeting());

        }
    }
            @Override
            public int getItemCount () {
                return items.size();
            }
        }
