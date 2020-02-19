package com.e.salestracker.Adapter;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.e.salestracker.Modal.PersonModal;
import com.e.salestracker.Modal.ProjectvisitModal;
import com.e.salestracker.R;
import com.e.salestracker.Utility.Tools;
import com.e.salestracker.helper.SwipeItemTouchHelper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterpersonList extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private List<PersonModal> items = new ArrayList<>();
    private List<PersonModal> items_swiped = new ArrayList<>();
    private ArrayList<ProjectvisitModal> project_visit = new ArrayList<>();

    private Context ctx;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, PersonModal obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AdapterpersonList(Context context, List<PersonModal> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder implements SwipeItemTouchHelper.TouchViewHolder {
        public ImageView image;
        public TextView name,first_checkIn,last_checkOut,location,total_visit,call,address,project,project_visit;
        public ImageButton bt_move;
        public Button bt_undo;
        public View lyt_parent;


        public OriginalViewHolder(View v) {
            super(v);
            image = v.findViewById(R.id.image);
            name = v.findViewById(R.id.name);
            address = v.findViewById(R.id.address);
            call = v.findViewById(R.id.call);
            first_checkIn = v.findViewById(R.id.first_checkIn);
            last_checkOut = v.findViewById(R.id.last_checkOut);
            location = v.findViewById(R.id.location);
            total_visit = v.findViewById(R.id.total_visit);
            lyt_parent = v.findViewById(R.id.lyt_parent);
            project = v.findViewById(R.id.project);
            project_visit = v.findViewById(R.id.project_visit);

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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_item_layout, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            final OriginalViewHolder view = (OriginalViewHolder) holder;

            final PersonModal p = items.get(position);
            view.name.setText(p.getAgent_name());
            view.first_checkIn.setText(p.getFirst_checkin());
            Log.e("first_checkIn", p.getFirst_checkin());
            view.last_checkOut.setText(p.getLast_checkout());
            try {
                String s = p.getLast_location();
                StringTokenizer st = new StringTokenizer(s, ",");
                String lat = st.nextToken();
                String lon = st.nextToken();

                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(ctx, Locale.getDefault());

                if (s.equalsIgnoreCase("0.0,0.0")) {
                    view.address.setText("No Address");
                } else{

                    addresses = geocoder.getFromLocation(Double.valueOf(lat), Double.valueOf(lon), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();
                view.address.setText(address);
            }
            } catch (IOException e) {
                e.printStackTrace();
            }

            view.location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String map = "http://maps.google.co.in/maps?q= "+ p.getLast_location();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
                    ctx.startActivity(intent);
                }
            });
           // view.total_visit.setText(p.getVisit_total());

            Tools.displayImageRound(ctx, view.image, p.getAgent_profile());
            view.call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, items.get(position), position);
                    }
                }
            });

            if(p.getSelectedPproject().isEmpty() || p.getSelectedPproject()=="") {
                view.project.setText(p.getProject_list());
                view.project_visit.setText(p.getProject_visit());
                view.total_visit.setText(p.getVisit_total());
            }else {
                view.project.setText(""+p.getSelectedPproject());

                String totalVisitSelProject="";
                totalVisitSelProject = p.getSelectedProjectVisit();
                if(totalVisitSelProject.isEmpty() || totalVisitSelProject==""){
                    totalVisitSelProject="0";
                }
                view.project_visit.setText(" : "+totalVisitSelProject);

                view.total_visit.setText(totalVisitSelProject);

            }

        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}