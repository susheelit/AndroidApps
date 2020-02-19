package com.aob.aobsalesman.activities.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aob.aobsalesman.R;
import com.aob.aobsalesman.activities.utility.ShareprefreancesUtility;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActivityFilter extends AppCompatActivity {

    ChipGroup chipgroup_industry;
    ChipGroup chipgroup_city;
    LinearLayout filter_city,filter_industry;

    String findustry = "";
    String fcity = "";


    List<String> filternames;
    List<String> filterlistcity;

    String[] industry_list = {};

    String [] city_list = {"Delhi NCR","Mumbai","Navi Mumbai","Ajmer","Amritsar","Banaras","Bhopal","Chandigarh","Dehradun","Indore","Jaipur","Kanpur","Karnal","Kota","Lucknow","Ludhiana","Pune","Sonipat","Thane"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        ImageView back_image = findViewById(R.id.back_image);
        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        initViews();
        initFunctionality();

    }

    private void initViews()
    {
        chipgroup_industry = findViewById(R.id.chipgroup_industry);
        chipgroup_city = findViewById(R.id.chipgroup_city);
        filter_city = findViewById(R.id.filter_city);
        filter_industry = findViewById(R.id.filter_industry);
        filternames = new ArrayList<>();
        filterlistcity = new ArrayList<>();
    }

    private void initFunctionality()
    {

        try {

            if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("industry_of_interest", ""))
                    && !ShareprefreancesUtility.getInstance().getString("industry_of_interest", "").equalsIgnoreCase("null")
                    && ShareprefreancesUtility.getInstance().getString("industry_of_interest", "") != null
            ) {
                industry_list = (ShareprefreancesUtility.getInstance().getString("industry_of_interest", "").split("\\s*,\\s*"));
                Log.e("aobsales","jo "+industry_list);

            }


            Bundle extras = getIntent().getExtras();
            if (extras != null) {

                findustry = extras.getString("resultindustry");
                fcity = extras.getString("resultcity");
            }

            if (!TextUtils.isEmpty(findustry)) {
                filternames = new ArrayList<String>(Arrays.asList(findustry.split("\\s*,\\s*")));

                for (int i = 0; i < filternames.size(); i++) {
                    Chip chip = new Chip(ActivityFilter.this);
                    chip.setText(filternames.get(i));
                    chip.setChipBackgroundColorResource(R.color.colorTransparent);
                    chip.setChipStrokeColorResource(R.color.colorPrimary);
                    chip.setChipCornerRadius(20);
                    chip.setChipStrokeWidth(1);
                    chipgroup_industry.addView(chip);

                }
            }

            if (!TextUtils.isEmpty(fcity)) {
                filterlistcity = new ArrayList<String>(Arrays.asList(fcity.split("\\s*,\\s*")));
                for (int i = 0; i < filterlistcity.size(); i++) {
                    Chip chip = new Chip(ActivityFilter.this);
                    chip.setText(filterlistcity.get(i));
                    chip.setChipBackgroundColorResource(R.color.colorTransparent);
                    chip.setChipStrokeColorResource(R.color.colorPrimary);
                    chip.setChipCornerRadius(20);
                    chip.setChipStrokeWidth(1);
                    chipgroup_city.addView(chip);

                }
            }


            filter_industry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                  if(industry_list.length >0) {

                      final Dialog dialog1 = new Dialog(ActivityFilter.this);
                      dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                      dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                      dialog1.setContentView(R.layout.dialog_industry);
                      RecyclerView recyclerView_industry = dialog1.findViewById(R.id.recyclerView_industry);
                      LinearLayoutManager layoutManager = new LinearLayoutManager(ActivityFilter.this);
                      layoutManager.setOrientation(RecyclerView.VERTICAL);
                      recyclerView_industry.setHasFixedSize(true);
                      recyclerView_industry.setLayoutManager(layoutManager);


                      final ChipGroup chipgroup = dialog1.findViewById(R.id.chipGroup_dialog);

/*
                for (int i = 0 ;i<filternames.size();i++)
                {
                    Chip chip = new Chip(ActivityFilter.this);
                    chip.setText(filternames.get(i));
                    chip.setChipBackgroundColorResource(R.color.colorTransparent);
                    chip.setChipStrokeColorResource(R.color.colorPrimary);
                    chip.setChipCornerRadius(20);
                    chip.setChipStrokeWidth(1);
                    chipgroup.addView(chip);


                }*/


                      IndustryAdapter1 indadapter = new IndustryAdapter1(ActivityFilter.this, industry_list, filternames);
                      recyclerView_industry.setAdapter(indadapter);


                      indadapter.setOnItemClickListener(new IndustryAdapter1.OnItemClickListener() {

                          @Override
                          public void onItemClick(View view, String industry, int position, RecyclerView.ViewHolder holder) {


                              Boolean temp = filternames.contains(industry) ? true : false;
                              if (temp) {

                                  int ind1 = filternames.indexOf(industry);
                                  // chipgroup.removeViewAt(ind1);
                                  filternames.remove(ind1);


                              } else {
                           /* Chip chip = new Chip(ActivityFilter.this);
                            chip.setText(industry);
                            chip.setChipBackgroundColorResource(R.color.colorTransparent);
                            chip.setChipStrokeColorResource(R.color.colorPrimary);
                            chip.setChipCornerRadius(20);
                            chip.setChipStrokeWidth(1);
                            chipgroup.addView(chip);*/

                                  filternames.add(industry);

                              }


                          }
                      });

                      Button submit_industry = dialog1.findViewById(R.id.submit_industry);
                      submit_industry.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View v) {

                              chipgroup_industry.removeAllViews();

                              for (int i = 0; i < filternames.size(); i++) {
                                  Chip chip = new Chip(ActivityFilter.this);
                                  chip.setText(filternames.get(i));
                                  chip.setChipBackgroundColorResource(R.color.colorTransparent);
                                  chip.setChipStrokeColorResource(R.color.colorPrimary);
                                  chip.setChipCornerRadius(20);
                                  chip.setChipStrokeWidth(1);
                                  chipgroup_industry.addView(chip);
                              }


                              dialog1.dismiss();
                          }
                      });

                      dialog1.setOnCancelListener(
                              new DialogInterface.OnCancelListener() {
                                  @Override
                                  public void onCancel(DialogInterface dialog) {

                                      chipgroup_industry.removeAllViews();

                                      for (int i = 0; i < filternames.size(); i++) {
                                          Chip chip = new Chip(ActivityFilter.this);
                                          chip.setText(filternames.get(i));
                                          chip.setChipBackgroundColorResource(R.color.colorTransparent);
                                          chip.setChipStrokeColorResource(R.color.colorPrimary);
                                          chip.setChipCornerRadius(20);
                                          chip.setChipStrokeWidth(1);
                                          chipgroup_industry.addView(chip);
                                      }
                                  }
                              }
                      );

                      dialog1.show();
                  }else
                  {
                      Toast.makeText(ActivityFilter.this, "Please Complete Registration first",
                              Toast.LENGTH_SHORT).show();
                  }

                }
            });


            filter_city.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    final Dialog dialog2 = new Dialog(ActivityFilter.this);
                    dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dialog2.setContentView(R.layout.filter_dialog_1);
                    RecyclerView recyclerView_industry = dialog2.findViewById(R.id.recyclerView_industry);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(ActivityFilter.this);
                    layoutManager.setOrientation(RecyclerView.VERTICAL);
                    recyclerView_industry.setHasFixedSize(true);
                    recyclerView_industry.setLayoutManager(layoutManager);


                    TextView heading_dialog = dialog2.findViewById(R.id.heading_dialog);
                    heading_dialog.setText("City");

                    final ChipGroup chipgroup = dialog2.findViewById(R.id.chipGroup_dialog);


             /*   for (int i = 0 ;i<filterlistcity.size();i++)
                {
                    Chip chip = new Chip(ActivityFilter.this);
                    chip.setText(filterlistcity.get(i));
                    chip.setChipBackgroundColorResource(R.color.colorTransparent);
                    chip.setChipStrokeColorResource(R.color.colorPrimary);
                    chip.setChipCornerRadius(20);
                    chip.setChipStrokeWidth(1);
                    chipgroup.addView(chip);


                }*/


                    Adapter1 indadapter = new Adapter1(ActivityFilter.this, city_list, filterlistcity);
                    recyclerView_industry.setAdapter(indadapter);


                    indadapter.setOnItemClickListener(new Adapter1.OnItemClickListener() {

                        @Override
                        public void onItemClick(View view, String industry, int position, RecyclerView.ViewHolder holder) {


                            Boolean temp = filterlistcity.contains(industry) ? true : false;
                            if (temp) {

                                int ind1 = filterlistcity.indexOf(industry);
                                //chipgroup.removeViewAt(ind1);
                                filterlistcity.remove(ind1);


                            } else {
                          /*  Chip chip = new Chip(ActivityFilter.this);
                            chip.setText(industry);
                            chip.setChipBackgroundColorResource(R.color.colorTransparent);
                            chip.setChipStrokeColorResource(R.color.colorPrimary);
                            chip.setChipCornerRadius(20);
                            chip.setChipStrokeWidth(1);
                            chipgroup.addView(chip);*/

                                filterlistcity.add(industry);

                            }


                        }
                    });

                    Button submit_industry = dialog2.findViewById(R.id.submit_industry);
                    submit_industry.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            chipgroup_city.removeAllViews();

                            for (int i = 0; i < filterlistcity.size(); i++) {
                                Chip chip = new Chip(ActivityFilter.this);
                                chip.setText(filterlistcity.get(i));
                                chip.setChipBackgroundColorResource(R.color.colorTransparent);
                                chip.setChipStrokeColorResource(R.color.colorPrimary);
                                chip.setChipCornerRadius(20);
                                chip.setChipStrokeWidth(1);
                                chipgroup_city.addView(chip);
                            }


                            dialog2.dismiss();
                        }
                    });

                    dialog2.setOnCancelListener(
                            new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {

                                    chipgroup_city.removeAllViews();

                                    for (int i = 0; i < filterlistcity.size(); i++) {
                                        Chip chip = new Chip(ActivityFilter.this);
                                        chip.setText(filterlistcity.get(i));
                                        chip.setChipBackgroundColorResource(R.color.colorTransparent);
                                        chip.setChipStrokeColorResource(R.color.colorPrimary);
                                        chip.setChipCornerRadius(20);
                                        chip.setChipStrokeWidth(1);
                                        chipgroup_city.addView(chip);
                                    }
                                }
                            }
                    );

                    dialog2.show();

                }
            });


            ((Button) findViewById(R.id.filter_apply)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String resultindustry = android.text.TextUtils.join(",", filternames);
                    String resultcity = android.text.TextUtils.join(",", filterlistcity);

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("resultindustry", resultindustry);
                    resultIntent.putExtra("resultcity", resultcity);

                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();


                }
            });

            ((Button) findViewById(R.id.filter_reset)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String resultindustry = "";
                    String resultcity = "";

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("resultindustry", resultindustry);
                    resultIntent.putExtra("resultcity", resultcity);

                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();


                }
            });

        }catch (Exception e){}

    }




    public static class IndustryAdapter1 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private String[] items;
        public boolean isClickable = true;
        List<String> allchecked;
        private Context ctx;
        public View v;
        private OnItemClickListener mOnItemClickListener;

        public interface OnItemClickListener {
            void onItemClick(View view,String industry ,int position, RecyclerView.ViewHolder holder);
        }

        public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
            this.mOnItemClickListener = mItemClickListener;
        }


        public IndustryAdapter1(Context context,String[] items, List<String> allchecked1 ) {

            ctx = context;
            this.items=items;
            allchecked = allchecked1;

        }

        public class OriginalViewHolder extends RecyclerView.ViewHolder {
            public CheckBox industry_check;
            public TextView name_industry;
            public LinearLayout lyt_parent;



            public OriginalViewHolder(View v) {
                super(v);

                name_industry = v.findViewById(R.id.name_industry);
                industry_check = v.findViewById(R.id.industry_check);
                lyt_parent = v.findViewById(R.id.lyt_parent);
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder vh;
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.industry_item, parent, false);
            vh = new OriginalViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            if (holder instanceof OriginalViewHolder) {
                final OriginalViewHolder view = (OriginalViewHolder) holder;

                view.name_industry.setText(items[position]);


                Boolean test1 = allchecked.contains(items[position]);

               // Log.e("aobsales","hello "+test1+" value "+items[position]+" pos "+position);

                if(test1)
                {
                    view.industry_check.setChecked(true);
                }
                else
                {
                    view.industry_check.setChecked(false);
                }

                view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(view.industry_check.isChecked()) {
                            view.industry_check.setChecked(false);
                        }else
                        {
                            view.industry_check.setChecked(true);


                        }

                        if (mOnItemClickListener != null) {
                            mOnItemClickListener.onItemClick(v, items[position], position, holder);
                            v.setClickable(false);
                        }


                    }
                });

            }
        }


        @Override
        public int getItemCount() {
            return items.length;

        }
        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }




    }


    public static class Adapter1 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private String[] items;
        public boolean isClickable = true;
        List<String> allchecked;
        private Context ctx;
        public View v;
        private OnItemClickListener mOnItemClickListener;

        public interface OnItemClickListener {
            void onItemClick(View view,String industry ,int position, RecyclerView.ViewHolder holder);
        }

        public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
            this.mOnItemClickListener = mItemClickListener;
        }


        public Adapter1(Context context,String[] items, List<String> allchecked1 ) {

            ctx = context;
            this.items=items;
            allchecked = allchecked1;

        }

        public class OriginalViewHolder extends RecyclerView.ViewHolder {
            public CheckBox industry_check;
            public TextView name_industry;
            public LinearLayout lyt_parent;



            public OriginalViewHolder(View v) {
                super(v);

                name_industry = v.findViewById(R.id.name_industry);
                industry_check = v.findViewById(R.id.industry_check);
                lyt_parent = v.findViewById(R.id.lyt_parent);
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder vh;
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.industry_item, parent, false);
            vh = new OriginalViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            //Log.e("onBindViewHolder", "onBindViewHolder : " + position);
            if (holder instanceof OriginalViewHolder) {
                final OriginalViewHolder view = (OriginalViewHolder) holder;

                view.name_industry.setText(items[position]);

                //Log.e("aobsales","hello "+items[position]);
                //Log.e("aobsales","hello1 "+allchecked.size());

                Boolean test1 = allchecked.contains(items[position]);

               // Log.e("aobsales","hello "+test1+" value "+items[position]+" pos "+position);

                if(test1)
                {
                    view.industry_check.setChecked(true);
                }
                else
                {
                    view.industry_check.setChecked(false);
                }

                view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(view.industry_check.isChecked()) {
                            view.industry_check.setChecked(false);
                        }else
                        {
                            view.industry_check.setChecked(true);


                        }

                        if (mOnItemClickListener != null) {
                            mOnItemClickListener.onItemClick(v, items[position], position, holder);
                            v.setClickable(false);
                        }


                    }
                });

            }
        }


        @Override
        public int getItemCount() {
            return items.length;

        }
        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }




    }


}
