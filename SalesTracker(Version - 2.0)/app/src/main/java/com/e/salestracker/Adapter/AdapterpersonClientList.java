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
import com.e.salestracker.Modal.Model_cona_cateogry;
import com.e.salestracker.Modal.Model_cona_value;
import com.e.salestracker.Modal.PersonClientModel;
import com.e.salestracker.Modal.PersonClientModel;
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

public class AdapterpersonClientList  extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private List<Model_cona_value> model_cona_values = new ArrayList<>();
    private List<Model_cona_cateogry> model_cona_cateogries = new ArrayList<>();
    private ArrayList<PersonClientModel> personClientModels = new ArrayList<>();

    private Context ctx;
    private AdapterpersonClientList.OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, PersonClientModel obj, int position);
    }

    public void setOnItemClickListener(final AdapterpersonClientList.OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AdapterpersonClientList(Context context, ArrayList<PersonClientModel> personClientModels,
                                   List<Model_cona_value> model_cona_values,List<Model_cona_cateogry> model_cona_cateogries) {
        this.personClientModels = personClientModels;
        this.model_cona_values = model_cona_values;
        this.model_cona_cateogries = model_cona_cateogries;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder implements SwipeItemTouchHelper.TouchViewHolder {
        public ImageView image;
        public TextView name,first_checkIn,last_checkOut,location,total_visit,call,address,project,project_visit;

       public TextView tvArchitect,tvBuilder,tvCompany,tvContractor,tvDealer,tvElectrician,tvRetailer, tvCollection
        ,tvOrder;
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

            tvArchitect = v.findViewById(R.id.tvArchitect);
            tvBuilder = v.findViewById(R.id.tvBuilder);
            tvCompany = v.findViewById(R.id.tvCompany);
            tvContractor = v.findViewById(R.id.tvContractor);
            tvDealer = v.findViewById(R.id.tvDealer);
            tvElectrician = v.findViewById(R.id.tvElectrician);
            tvRetailer = v.findViewById(R.id.tvRetailer);
            tvCollection = v.findViewById(R.id.tvCollection);
            tvOrder = v.findViewById(R.id.tvOrder);

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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_personclient, parent, false);
        vh = new AdapterpersonClientList.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof AdapterpersonClientList.OriginalViewHolder) {
            final AdapterpersonClientList.OriginalViewHolder view = (AdapterpersonClientList.OriginalViewHolder) holder;

            final PersonClientModel p = personClientModels.get(position);
            view.name.setText(p.getAgent_name());
            view.first_checkIn.setText(p.getFirst_checkin());
            view.last_checkOut.setText(p.getLast_checkout());

            Log.e("p.getVisit_total() ", ""+p.getVisit_total());
            view.total_visit.setText(""+p.getVisit_total());

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

            view.tvArchitect.setText(""+model_cona_cateogries.get(0).getArchitect());
            view.tvBuilder.setText(""+model_cona_cateogries.get(0).getBuilder());
            view.tvCompany.setText(""+model_cona_cateogries.get(0).getCompany());
            view.tvContractor.setText(""+model_cona_cateogries.get(0).getContractor());
            view.tvDealer.setText(""+model_cona_cateogries.get(0).getDealer());
            view.tvElectrician.setText(""+model_cona_cateogries.get(0).getElectrician());
            view.tvRetailer.setText(""+model_cona_cateogries.get(0).getRetailer());

            view.tvCollection.setText(""+model_cona_values.get(0).getTotal_collection_value());
            view.tvOrder.setText(""+model_cona_values.get(0).getTotal_order_value());




            Tools.displayImageRound(ctx, view.image, p.getAgent_profile());
            view.call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, personClientModels.get(position), position);
                    }
                }
            });

          //  view.project.setText(p.getProject_list());
           // view.project_visit.setText(p.getProject_visit());

        }
    }

    @Override
    public int getItemCount() {
        return personClientModels.size();
    }
}
