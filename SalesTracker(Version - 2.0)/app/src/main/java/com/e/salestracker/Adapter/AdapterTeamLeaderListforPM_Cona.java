package com.e.salestracker.Adapter;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.e.salestracker.Modal.Model_cona_cateogry;
import com.e.salestracker.Modal.Model_cona_value;
import com.e.salestracker.Modal.Model_project_visit;
import com.e.salestracker.Modal.PersonClientModel;
import com.e.salestracker.Modal.PersonModal;
import com.e.salestracker.R;
import com.e.salestracker.Utility.Tools;
import com.e.salestracker.helper.SwipeItemTouchHelper;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterTeamLeaderListforPM_Cona extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private List<Model_cona_cateogry> cona_cateogry_list123 = new ArrayList<>();
    //private List<Model_cona_value> cona_value_list = new ArrayList<>();
    private ArrayList<PersonClientModel > personClientModels = new ArrayList<>();


    private Context ctx;
    //private AdapterTeamLeaderListforPM_Cona.OnItemClickListener mOnItemClickListener;
    private OnItemClickListener mOnItemClickListener;


    public interface OnItemClickListener {
        void onItemClick(View view, PersonClientModel obj, int position);
    }

    public void setOnItemClickListener(final AdapterTeamLeaderListforPM_Cona.OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AdapterTeamLeaderListforPM_Cona(Context context, ArrayList<PersonClientModel> personClientModels,List<Model_cona_cateogry> cona_cateogry_list123) {
        this.personClientModels = personClientModels;
        this.cona_cateogry_list123 = cona_cateogry_list123;
       // this.cona_value_list = cona_value_list;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder implements SwipeItemTouchHelper.TouchViewHolder {
        public ImageView image;
        public TextView name,first_checkIn,last_checkOut,location,total_visit,call,address,project,project_visit;
        public LinearLayout llSrNo, llClient_name, llOrder_value, llCategory, llCollection_value;
        public ImageButton bt_move;
        public Button bt_undo;
        public View lyt_parent;
        public TextView tvCollection,tvOrder;

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

            tvCollection= v.findViewById(R.id.tvCollection);
            tvOrder= v.findViewById(R.id.tvOrder);

            llSrNo = v.findViewById(R.id.llSrNo);
            llClient_name = v.findViewById(R.id.llClient_name);
            llOrder_value = v.findViewById(R.id.llOrder_value);
            llCategory =v.findViewById(R.id.llCategory);
            llCollection_value =v.findViewById(R.id.llCollection_value);

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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_person_teamleaderlist_for_pmcona, parent, false);
        vh = new AdapterTeamLeaderListforPM_Cona.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof AdapterTeamLeaderListforPM_Cona.OriginalViewHolder) {
            final AdapterTeamLeaderListforPM_Cona.OriginalViewHolder view = (AdapterTeamLeaderListforPM_Cona.OriginalViewHolder) holder;

            final PersonClientModel p = personClientModels.get(position);
            view.name.setText(p.getAgent_name());
            view.first_checkIn.setText(p.getFirst_checkin());
            view.last_checkOut.setText(p.getLast_checkout());

            view.total_visit.setText(""+p.getVisit_total());


         //  Model_cona_value model_cona_value = cona_value_list.get(position);
            view.tvCollection.setText(""+p.getTotal_collection_value());
            view.tvOrder.setText(""+p.getTotal_order_value());

            TextView tvSrno1 = new TextView(ctx);
            tvSrno1.setText("Sno");
            tvSrno1.setGravity(Gravity.CENTER);
            tvSrno1.setTextSize(12);
            tvSrno1.setMaxLines(1);

            tvSrno1.setBackground(ctx.getResources().getDrawable(R.drawable.border_shape));
            view.llSrNo.addView(tvSrno1);

            TextView tvClient_name1= new TextView(ctx);
            tvClient_name1.setText("Client Name");
            tvClient_name1.setGravity(Gravity.CENTER);
            tvClient_name1.setMaxLines(1);
            tvClient_name1.setTextSize(12);
            tvClient_name1.setBackground(ctx.getResources().getDrawable(R.drawable.border_shape));
            view.llClient_name.addView(tvClient_name1);

            TextView tvOrder_value1= new TextView(ctx);
            tvOrder_value1.setText("Order Value");
            tvOrder_value1.setGravity(Gravity.CENTER);
            tvOrder_value1.setMaxLines(1);
            tvOrder_value1.setTextSize(12);
            tvOrder_value1.setBackground(ctx.getResources().getDrawable(R.drawable.border_shape));
            view.llOrder_value.addView(tvOrder_value1);

            TextView tvCategory1= new TextView(ctx);
            tvCategory1.setText("Category");
            tvCategory1.setGravity(Gravity.CENTER);
            tvCategory1.setMaxLines(1);
            tvCategory1.setTextSize(12);
            tvCategory1.setBackground(ctx.getResources().getDrawable(R.drawable.border_shape));
            view.llCategory.addView(tvCategory1);

            TextView tvCollection_value1= new TextView(ctx);
            tvCollection_value1.setText("Collection");
            tvCollection_value1.setGravity(Gravity.CENTER);
            tvCollection_value1.setMaxLines(1);
            tvCollection_value1.setTextSize(12);
            tvCollection_value1.setBackground(ctx.getResources().getDrawable(R.drawable.border_shape));
            view.llCollection_value.addView(tvCollection_value1);
            List<Model_cona_cateogry>catList = new ArrayList<>();
            catList= p.getItems_cona_cateogry();
            for(int i=0; i<catList.size();i++){
                Model_cona_cateogry model_project_visit11 = catList.get(i);

                //Model_cona_cateogry model_project_visit11 = cona_cateogry_list123.get(i);
                // Set Sr no
                if(cona_cateogry_list123.size()>0) {
                    TextView tvSrno = new TextView(ctx);
                    tvSrno.setText("" + (i + 1));
                    tvSrno.setGravity(Gravity.CENTER);
                    tvSrno.setTextSize(12);
                    tvSrno.setMaxLines(1);
                    tvSrno.setBackground(ctx.getResources().getDrawable(R.drawable.border_shape));
                    view.llSrNo.addView(tvSrno);
                }

                // Set Client name
                TextView tvClient_name= new TextView(ctx);
                tvClient_name.setText(""+model_project_visit11.getClient_name());
                tvClient_name.setGravity(Gravity.CENTER);
                tvClient_name.setMaxLines(1);
                tvClient_name.setTextSize(12);
                tvClient_name.setBackground(ctx.getResources().getDrawable(R.drawable.border_shape));
                view.llClient_name.addView(tvClient_name);

                // set order value
                TextView tvOrder_value= new TextView(ctx);
                tvOrder_value.setText(""+model_project_visit11.getOrder_value());
                tvOrder_value.setGravity(Gravity.CENTER);
                tvOrder_value.setMaxLines(1);
                tvOrder_value.setTextSize(12);
                tvOrder_value.setBackground(ctx.getResources().getDrawable(R.drawable.border_shape));
                view.llOrder_value.addView(tvOrder_value);

                // Set category
                TextView tvCategory= new TextView(ctx);
                tvCategory.setText(""+model_project_visit11.getCategory());
                tvCategory.setGravity(Gravity.CENTER);
                tvCategory.setMaxLines(1);
                tvCategory.setTextSize(12);
                tvCategory.setBackground(ctx.getResources().getDrawable(R.drawable.border_shape));
                view.llCategory.addView(tvCategory);

                // set collection value
                TextView tvCollection_value= new TextView(ctx);
                tvCollection_value.setText(""+model_project_visit11.getCollection_value());
                tvCollection_value.setGravity(Gravity.CENTER);
                tvCollection_value.setMaxLines(1);
                tvCollection_value.setTextSize(12);
                tvCollection_value.setBackground(ctx.getResources().getDrawable(R.drawable.border_shape));
                view.llCollection_value.addView(tvCollection_value);

            }

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
