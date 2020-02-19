package com.aob.aobsalesman.activities.Fragements;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.aob.aobsalesman.R;
import com.aob.aobsalesman.activities.activities.HomeActivity;
import com.aob.aobsalesman.activities.activities.KYCActivity;
import com.aob.aobsalesman.activities.activities.ProfileActivity;
import com.aob.aobsalesman.activities.utility.InternetConnection;
import com.aob.aobsalesman.activities.utility.MySingleton;
import com.aob.aobsalesman.activities.utility.ShareprefreancesUtility;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompleteRegistrationFragment extends Fragment {
    View view;
    LinearLayout age;
    LinearLayout experience_full, add_experience,industry_of_interest;

    CardView exp_linear1, exp_linear2, exp_linear3;


    String city_val, age_val, gender_val, vehicle_val, qualification_val, highest_qual_val, fresher_val, company2, company3,
            exp_dur2, exp_dur3, sold2, sold3;

    String industry_val = "";
    String no_exp_val = "";
    String company1 = "";
    String exp_dur1 = "";
    String sold1 = "";



    TextView city1, city2, city3, city4, city5, city6, city7, city8, city9, city10, city11, city12, city13, city14, city15, city16,
            city17, city18, city19, city20, city21;

    TextView qual1, qual2, qual3;

    TextView exp1, exp2, exp3, exp4, exp5;

    androidx.appcompat.widget.AppCompatEditText cityother, highest_qualification, et_company1, et_company2, et_company3, et_exp1, et_exp2, et_exp3, et_dur1, et_dur2, et_dur3;


    String[] age_list = {"18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53"
            , "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "70+"};


    String[] industry_list = {"Accounting","Airlines/Aviation","Alternative Dispute Resolution","Alternative Medicine","Animation","Apparel/Fashion","Architecture/Planning","Arts/Crafts","Automotive","Aviation/Aerospace","Banking/Mortgage","Biotechnology/Greentech","Broadcast Media","Building Materials","Business Supplies/Equipment","Capital Markets/Hedge Fund/Private Equity","Chemicals","Civic/Social Organization","Civil Engineering","Commercial Real Estate","Computer Games","Computer Hardware","Computer Networking","Computer Software/Engineering","Computer/Network Security","Construction","Consumer Electronics","Consumer Goods","Consumer Services","Cosmetics","Dairy","Defense/Space","Design","E-Learning","Education Management","Electrical/Electronic Manufacturing","Entertainment/Movie Production","Environmental Services","Events Services","Executive Office","Facilities Services","Farming","Financial Services","Fine Art","Fishery","Food Production","Food/Beverages","Fundraising","Furniture","Gambling/Casinos","Glass/Ceramics/Concrete","Government Administration","Government Relations","Graphic Design/Web Design","Health/Fitness","Higher Education/Acadamia","Hospital/Health Care","Hospitality","Human Resources/HR","Import/Export","Individual/Family Services","Industrial Automation","Information Services","Information Technology/IT","Insurance","International Affairs","International Trade/Development","Internet","Investment Banking/Venture","Investment Management/Hedge Fund/Private Equity","Judiciary","Law Enforcement","Law Practice/Law Firms","Legal Services","Legislative Office","Leisure/Travel","Library","Logistics/Procurement","Luxury Goods/Jewelry","Machinery","Management Consulting","Maritime","Market Research","Marketing/Advertising/Sales","Mechanical or Industrial Engineering","Media Production","Medical Equipment","Medical Practice","Mental Health Care","Military Industry","Mining/Metals","Motion Pictures/Film","Museums/Institutions","Music","Nanotechnology","Newspapers/Journalism","Non-Profit/Volunteering","Oil/Energy/Solar/Greentech","Online Publishing","Other Industry","Outsourcing/Offshoring","Package/Freight Delivery","Packaging/Containers","Paper/Forest Products","Performing Arts","Pharmaceuticals","Philanthropy","Photography","Plastics","Political Organization","Primary/Secondary Education","Printing","Professional Training","Program Development","Public Relations/PR","Public Safety","Publishing Industry","Railroad Manufacture","Ranching","Real Estate/Mortgage","Recreational Facilities/Services","Religious Institutions","Renewables/Environment","Research Industry","Restaurants","Retail Industry","Security/Investigations","Semiconductors","Shipbuilding","Sporting Goods","Sports","Staffing/Recruiting","Supermarkets","Telecommunications","Textiles","Think Tanks","Tobacco","Translation/Localization","Transportation","Utilities","Venture Capital/VC","Veterinary","Warehousing","Wholesale","Wine/Spirits","Wireless","Writing/Editing"};


    TextView fresher;
    TextView experience;
    TextView btn_2wheeler, btn_4wheeler, btn_novehicle, btn_male, btn_female;

    TextView tv_no_experience, tv_age, tv_qualification, tv_city;

    TextView qual_error, fresher_error, age_error, gender_error, city_error, vehicle_error, experience_years_error, experience_details_error,industry_error;

    TextView head_qual, head_fresher, head_age, head_gender, head_city, head_vehicle, head_experience_years, head_experience_details,head_industry;


    TextView main_name;

    ImageView remove_exp2, remove_exp3, back_image;

    @SuppressLint("HardwareIds")
    String android_id;

    ChipGroup chipgroup_main;


    private ProgressDialog progressDialog;

    List<String> itemname;


    public CompleteRegistrationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.complete_registration, container, false);


        initviews();
        setFunctionality();
        setValueAfterRegsitration();


        ((Button) view.findViewById(R.id.login)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validation();
                checkValidation();
            }
        });


        Log.e("aobsales","val "+ShareprefreancesUtility.getInstance().getString("experience_type", ""));

        return view;
    }


    private void initviews() {

        /* area_of_intrest = view.findViewById(R.id.area_of_intrest);*/
        /*company1 = view.findViewById(R.id.company1);
        company2 = view.findViewById(R.id.company2);
        company3 = view.findViewById(R.id.company3);
        experience1 = view.findViewById(R.id.experience1);
        experience2 = view.findViewById(R.id.experience2);
        experience3 = view.findViewById(R.id.experience3);
        field_work1 = view.findViewById(R.id.field_work1);
        field_work2 = view.findViewById(R.id.field_work2);
        field_work3 = view.findViewById(R.id.field_work3);*/

        //radio = view.findViewById(R.id.radio);

        /*qualification =  view.findViewById(R.id.qualification);
        no_of_experience =  view.findViewById(R.id.no_of_experience);
        age =  view.findViewById(R.id.age);
        city = view.findViewById(R.id.city);*/

        experience_full = view.findViewById(R.id.experience_full);
        exp_linear1 = view.findViewById(R.id.exp_linear1);
        exp_linear2 = view.findViewById(R.id.exp_linear2);
        exp_linear3 = view.findViewById(R.id.exp_linear3);
        add_experience = view.findViewById(R.id.add_experience);
        age = view.findViewById(R.id.age);
        industry_of_interest = view.findViewById(R.id.industry_of_interest);


        fresher = view.findViewById(R.id.btn_fresher);
        experience = view.findViewById(R.id.btn_experience);
        btn_2wheeler = view.findViewById(R.id.btn_2wheeler);
        btn_4wheeler = view.findViewById(R.id.btn_4wheeler);
        btn_novehicle = view.findViewById(R.id.btn_novehicle);

        btn_male = view.findViewById(R.id.btn_male);
        btn_female = view.findViewById(R.id.btn_female);

        tv_age = view.findViewById(R.id.tv_age);

        qual_error = view.findViewById(R.id.qual_error);
        fresher_error = view.findViewById(R.id.fresher_error);
        age_error = view.findViewById(R.id.age_error);
        gender_error = view.findViewById(R.id.gender_error);
        city_error = view.findViewById(R.id.city_error);
        vehicle_error = view.findViewById(R.id.vehicle_error);
        experience_years_error = view.findViewById(R.id.experience_years_error);
        experience_details_error = view.findViewById(R.id.experience_details_error);
        industry_error = view.findViewById(R.id.industry_error);


        head_qual = view.findViewById(R.id.head_qual);
        head_fresher = view.findViewById(R.id.head_fresher);
        head_age = view.findViewById(R.id.head_age);
        head_gender = view.findViewById(R.id.head_gender);
        head_city = view.findViewById(R.id.head_city);
        head_vehicle = view.findViewById(R.id.head_vehicle);
        head_experience_years = view.findViewById(R.id.head_experience_years);
        head_experience_details = view.findViewById(R.id.head_experience_details);
        head_industry = view.findViewById(R.id.head_industry);


        city1 = view.findViewById(R.id.city1);
        city2 = view.findViewById(R.id.city2);
        city3 = view.findViewById(R.id.city3);
        city4 = view.findViewById(R.id.city4);
        city5 = view.findViewById(R.id.city5);
        city6 = view.findViewById(R.id.city6);
        city7 = view.findViewById(R.id.city7);
        city8 = view.findViewById(R.id.city8);
        city9 = view.findViewById(R.id.city9);
        city10 = view.findViewById(R.id.city10);
        city11 = view.findViewById(R.id.city11);
        city12 = view.findViewById(R.id.city12);
        city13 = view.findViewById(R.id.city13);
        city14 = view.findViewById(R.id.city14);
        city15 = view.findViewById(R.id.city15);
        city16 = view.findViewById(R.id.city16);
        city17 = view.findViewById(R.id.city17);
        city18 = view.findViewById(R.id.city18);
        city19 = view.findViewById(R.id.city19);
        //city20 = view.findViewById(R.id.city20);
        city21 = view.findViewById(R.id.city21);

        cityother = view.findViewById(R.id.city_other);

        qual1 = view.findViewById(R.id.qual1);
        qual2 = view.findViewById(R.id.qual2);
        qual3 = view.findViewById(R.id.qual3);

        exp1 = view.findViewById(R.id.exp1);
        exp2 = view.findViewById(R.id.exp2);
        exp3 = view.findViewById(R.id.exp3);
        exp4 = view.findViewById(R.id.exp4);
        exp5 = view.findViewById(R.id.exp5);

        highest_qualification = view.findViewById(R.id.highest_qualification);

        et_company1 = view.findViewById(R.id.et_company1);
        et_dur1 = view.findViewById(R.id.et_dur1);
        et_exp1 = view.findViewById(R.id.et_exp1);

        et_company2 = view.findViewById(R.id.et_company2);
        et_dur2 = view.findViewById(R.id.et_dur2);
        et_exp2 = view.findViewById(R.id.et_exp2);

        et_company3 = view.findViewById(R.id.et_company3);
        et_dur3 = view.findViewById(R.id.et_dur3);
        et_exp3 = view.findViewById(R.id.et_exp3);

        main_name = view.findViewById(R.id.main_name);

        remove_exp2 = view.findViewById(R.id.remove_exp2);
        remove_exp3 = view.findViewById(R.id.remove_exp3);

        back_image = view.findViewById(R.id.back_image);

        chipgroup_main = view.findViewById(R.id.chipgroup_main);

        itemname = new ArrayList<>();


    }

    private void setFunctionality() {

        progressDialog = new ProgressDialog(getActivity());

        main_name.setText("Hi " + ShareprefreancesUtility.getInstance().getString("name", ""));

        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        /*ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_dropdown_list, qualification_list);
        qualification.setAdapter(arrayAdapter);

        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(getActivity(), R.layout.simple_dropdown_list, no_years_list);
        no_of_experience.setAdapter(arrayAdapter1);

        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(getActivity(), R.layout.simple_dropdown_list, age_list);
        age.setAdapter(arrayAdapter2);

        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(getActivity(), R.layout.simple_dropdown_list, city_list);
        city.setAdapter(arrayAdapter3);
*/
        //ArrayAdapter<String> arrayAdapter4 = new ArrayAdapter<String>(getActivity(), R.layout.simple_dropdown_list, vehicle_list);
        //vehicle.setAdapter(arrayAdapter4);

        /*radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...

                if(checkedRadioButton.getText().toString().equalsIgnoreCase("Experienced"))
                {
                    experience_full.setVisibility(View.VISIBLE);
                }
                else
                {
                    experience_full.setVisibility(View.GONE);
                }

            }
        });*/


        age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Log.e("aobsales", "he;llo");

                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.fragment_dialog);
                final ListView listView = dialog.findViewById(R.id.listview);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_dropdown_list, age_list);
                listView.setAdapter(arrayAdapter);
                listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String item = (String) listView.getItemAtPosition(position);
                        age_val = item;
                        tv_age.setText(item);
                        dialog.dismiss();
                    }
                });


                dialog.show();

            }
        });



        industry_of_interest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final Dialog dialog1 = new Dialog(getActivity());
                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog1.setContentView(R.layout.filter_dialog_1);
                RecyclerView recyclerView_industry = dialog1.findViewById(R.id.recyclerView_industry);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                layoutManager.setOrientation(RecyclerView.VERTICAL);
                recyclerView_industry.setHasFixedSize(true);
                recyclerView_industry.setLayoutManager(layoutManager);




                final ChipGroup chipgroup = dialog1.findViewById(R.id.chipGroup_dialog);


                IndustryAdapter indadapter = new IndustryAdapter(getActivity(), industry_list,itemname);
                recyclerView_industry.setAdapter(indadapter);



                indadapter.setOnItemClickListener(new IndustryAdapter.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, String industry, int position, RecyclerView.ViewHolder holder) {


                        Boolean temp = itemname.contains(industry) ? true : false;
                        if(temp)
                        {

                            int ind1 = itemname.indexOf(industry);
                           // chipgroup.removeViewAt(ind1);
                            itemname.remove(ind1);


                        }
                        else
                        {
                           /* Chip chip = new Chip(getActivity());
                            chip.setText(industry);
                            chip.setChipBackgroundColorResource(R.color.colorTransparent);
                            chip.setChipStrokeColorResource(R.color.colorPrimary);
                            chip.setChipCornerRadius(20);
                            chip.setChipStrokeWidth(1);
                            chipgroup.addView(chip);*/

                            itemname.add(industry);

                        }



                    }
                });

                Button submit_industry = dialog1.findViewById(R.id.submit_industry);
                submit_industry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        chipgroup_main.removeAllViews();

                        for (int i = 0 ;i<itemname.size();i++)
                        {
                            Chip chip = new Chip(getActivity());
                            chip.setText(itemname.get(i));
                            chip.setChipBackgroundColorResource(R.color.colorTransparent);
                            chip.setChipStrokeColorResource(R.color.colorPrimary);
                            chip.setChipCornerRadius(20);
                            chip.setChipStrokeWidth(1);
                            chipgroup_main.addView(chip);
                        }

                        industry_val = android.text.TextUtils.join(",", itemname);

                        dialog1.dismiss();
                    }
                });


                dialog1.setOnCancelListener(
                        new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {

                                chipgroup_main.removeAllViews();

                                for (int i = 0 ;i<itemname.size();i++)
                                {
                                    Chip chip = new Chip(getActivity());
                                    chip.setText(itemname.get(i));
                                    chip.setChipBackgroundColorResource(R.color.colorTransparent);
                                    chip.setChipStrokeColorResource(R.color.colorPrimary);
                                    chip.setChipCornerRadius(20);
                                    chip.setChipStrokeWidth(1);
                                    chipgroup_main.addView(chip);
                                }
                            }
                        }
                );




                dialog1.show();






/*
                String regex = "\\w+";
                Pattern p = Pattern.compile(regex);
                Matcher matcher = p.matcher("delhiindia");
                SpannableStringBuilder sb = new SpannableStringBuilder("delhiindia");
                while (matcher.find()) {

                    final int begin = matcher.start();
                    final int end = matcher.end();
                    float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 15, getResources().getDisplayMetrics());
                    head_industry.setTextSize(pixels);
                    BitmapDrawable bd = (BitmapDrawable) Tools.convertViewToDrawable(head_industry);
                    bd.setBounds(0, 0, bd.getIntrinsicWidth(),bd.getIntrinsicHeight());
                    sb.setSpan(new ImageSpan(bd), begin, end, 0);
                }
                head_industry.setText(sb);*/



            }
        });

/*

        city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Log.e("aobsales","he;llo");

                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.dialog_city);
               */
/* final ListView listView = dialog.findViewById(R.id.listview);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_dropdown_list, city_list);
                listView.setAdapter(arrayAdapter);
                listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                        String item = (String) listView.getItemAtPosition(position);
                        tv_city.setText(item);
                        dialog.dismiss();
                    }
                });*//*


                final Button btn_1 =  dialog.findViewById(R.id.btn_1);
                final Button btn_2 =  dialog.findViewById(R.id.btn_2);
                final Button btn_3 =  dialog.findViewById(R.id.btn_3);
                final Button btn_4 =  dialog.findViewById(R.id.btn_4);
                final Button btn_5 =  dialog.findViewById(R.id.btn_5);

                btn_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btn_1.setBackgroundResource(R.drawable.btn_bg_highlight);
                       */
/* btn_2.setBackgroundResource(R.drawable.btn_bg);
                        btn_3.setBackgroundResource(R.drawable.btn_bg);
                        btn_4.setBackgroundResource(R.drawable.btn_bg);
                        btn_5.setBackgroundResource(R.drawable.btn_bg);*//*


                        tv_city.setText(btn_1.getText().toString());

                        dialog.dismiss();

                    }
                });

                btn_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btn_2.setBackgroundResource(R.drawable.btn_bg_highlight);
                       */
/* btn_1.setBackgroundResource(R.drawable.btn_bg);
                        btn_3.setBackgroundResource(R.drawable.btn_bg);
                        btn_4.setBackgroundResource(R.drawable.btn_bg);
                        btn_5.setBackgroundResource(R.drawable.btn_bg);*//*

                        tv_city.setText(btn_2.getText().toString());
                        dialog.dismiss();

                    }
                });

                btn_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btn_3.setBackgroundResource(R.drawable.btn_bg_highlight);
                        */
/*btn_2.setBackgroundResource(R.drawable.btn_bg);
                        btn_1.setBackgroundResource(R.drawable.btn_bg);
                        btn_4.setBackgroundResource(R.drawable.btn_bg);
                        btn_5.setBackgroundResource(R.drawable.btn_bg);*//*

                        tv_city.setText(btn_3.getText().toString());
                        dialog.dismiss();

                    }
                });

                btn_4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btn_4.setBackgroundResource(R.drawable.btn_bg_highlight);
                        */
/*btn_2.setBackgroundResource(R.drawable.btn_bg);
                        btn_3.setBackgroundResource(R.drawable.btn_bg);
                        btn_1.setBackgroundResource(R.drawable.btn_bg);
                        btn_5.setBackgroundResource(R.drawable.btn_bg);*//*

                        tv_city.setText(btn_4.getText().toString());
                        dialog.dismiss();

                    }
                });


                btn_5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       */
/* btn_5.setBackgroundResource(R.drawable.btn_bg_highlight);
                        btn_2.setBackgroundResource(R.drawable.btn_bg);
                        btn_3.setBackgroundResource(R.drawable.btn_bg);
                        btn_4.setBackgroundResource(R.drawable.btn_bg);
                        btn_1.setBackgroundResource(R.drawable.btn_bg);*//*



                       final Dialog dialognew = new Dialog(getActivity());
                        dialognew.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialognew.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        dialognew.setContentView(R.layout.fragment_dialog);
                        final ListView listView = dialognew.findViewById(R.id.listview);
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_dropdown_list, city_list);
                        listView.setAdapter(arrayAdapter);
                        listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                                String item = (String) listView.getItemAtPosition(position);
                                tv_city.setText(item);
                                dialognew.dismiss();
                                dialog.dismiss();
                            }
                        });
                        dialognew.show();





                    }
                });




                dialog.show();

            }
        });


*/


        add_experience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* if(experiencecount == 0) {
                    exp_linear2.setVisibility(View.VISIBLE);
                    experiencecount++;
                }else if(experiencecount == 1)
                {
                    exp_linear2.setVisibility(View.VISIBLE);
                    exp_linear3.setVisibility(View.VISIBLE);
                    experiencecount++;
                }*/

                if (exp_linear2.getVisibility() == View.GONE) {
                    exp_linear2.setVisibility(View.VISIBLE);
                } else {

                    if (exp_linear3.getVisibility() == View.GONE) {
                        exp_linear3.setVisibility(View.VISIBLE);
                    }

                }

            }
        });

        remove_exp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                exp_linear2.setVisibility(View.GONE);

            }
        });

        remove_exp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                exp_linear3.setVisibility(View.GONE);

            }
        });

        fresher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fresher_val = "Fresher";
                fresher.setBackgroundResource(R.drawable.btn_bg_highlight);
                experience.setBackgroundResource(R.drawable.btn_bg);
                experience_full.setVisibility(View.GONE);

                et_company1.setText("");
                et_company2.setText("");
                et_company3.setText("");
                et_dur1.setText("");
                et_dur2.setText("");
                et_dur3.setText("");
                et_exp1.setText("");
                et_exp2.setText("");
                et_exp3.setText("");


            }
        });

        experience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fresher_val = "Experienced";
                experience.setBackgroundResource(R.drawable.btn_bg_highlight);
                fresher.setBackgroundResource(R.drawable.btn_bg);
                experience_full.setVisibility(View.VISIBLE);

            }
        });


        btn_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gender_val = "Male";
                btn_male.setBackgroundResource(R.drawable.btn_bg_highlight);
                btn_female.setBackgroundResource(R.drawable.btn_bg);
            }
        });

        btn_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gender_val = "Female";
                btn_female.setBackgroundResource(R.drawable.btn_bg_highlight);
                btn_male.setBackgroundResource(R.drawable.btn_bg);

            }
        });


        btn_2wheeler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                vehicle_val = "2 Wheeler";
                btn_2wheeler.setBackgroundResource(R.drawable.btn_bg_highlight);
                btn_4wheeler.setBackgroundResource(R.drawable.btn_bg);
                btn_novehicle.setBackgroundResource(R.drawable.btn_bg);

            }
        });

        btn_4wheeler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                vehicle_val = "4 Wheeler";
                btn_4wheeler.setBackgroundResource(R.drawable.btn_bg_highlight);
                btn_2wheeler.setBackgroundResource(R.drawable.btn_bg);
                btn_novehicle.setBackgroundResource(R.drawable.btn_bg);

            }
        });

        btn_novehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                vehicle_val = "No Vehicle";
                btn_novehicle.setBackgroundResource(R.drawable.btn_bg_highlight);
                btn_2wheeler.setBackgroundResource(R.drawable.btn_bg);
                btn_4wheeler.setBackgroundResource(R.drawable.btn_bg);

            }
        });


        city1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                city_val = city1.getText().toString();
                city1.setBackgroundResource(R.drawable.btn_bg_highlight);
                city2.setBackgroundResource(R.drawable.btn_bg);
                city3.setBackgroundResource(R.drawable.btn_bg);
                city4.setBackgroundResource(R.drawable.btn_bg);
                city5.setBackgroundResource(R.drawable.btn_bg);
                city6.setBackgroundResource(R.drawable.btn_bg);
                city7.setBackgroundResource(R.drawable.btn_bg);
                city8.setBackgroundResource(R.drawable.btn_bg);
                city9.setBackgroundResource(R.drawable.btn_bg);
                city10.setBackgroundResource(R.drawable.btn_bg);
                city11.setBackgroundResource(R.drawable.btn_bg);
                city12.setBackgroundResource(R.drawable.btn_bg);
                city13.setBackgroundResource(R.drawable.btn_bg);
                city14.setBackgroundResource(R.drawable.btn_bg);
                city15.setBackgroundResource(R.drawable.btn_bg);
                city16.setBackgroundResource(R.drawable.btn_bg);
                city17.setBackgroundResource(R.drawable.btn_bg);
                city18.setBackgroundResource(R.drawable.btn_bg);
                city19.setBackgroundResource(R.drawable.btn_bg);
                //city20.setBackgroundResource(R.drawable.btn_bg);
                city21.setBackgroundResource(R.drawable.btn_bg);
                cityother.setVisibility(View.GONE);

            }
        });

        city2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                city_val = city2.getText().toString();

                city2.setBackgroundResource(R.drawable.btn_bg_highlight);
                city1.setBackgroundResource(R.drawable.btn_bg);
                city3.setBackgroundResource(R.drawable.btn_bg);
                city4.setBackgroundResource(R.drawable.btn_bg);
                city5.setBackgroundResource(R.drawable.btn_bg);
                city6.setBackgroundResource(R.drawable.btn_bg);
                city7.setBackgroundResource(R.drawable.btn_bg);
                city8.setBackgroundResource(R.drawable.btn_bg);
                city9.setBackgroundResource(R.drawable.btn_bg);
                city10.setBackgroundResource(R.drawable.btn_bg);
                city11.setBackgroundResource(R.drawable.btn_bg);
                city12.setBackgroundResource(R.drawable.btn_bg);
                city13.setBackgroundResource(R.drawable.btn_bg);
                city14.setBackgroundResource(R.drawable.btn_bg);
                city15.setBackgroundResource(R.drawable.btn_bg);
                city16.setBackgroundResource(R.drawable.btn_bg);
                city17.setBackgroundResource(R.drawable.btn_bg);
                city18.setBackgroundResource(R.drawable.btn_bg);
                city19.setBackgroundResource(R.drawable.btn_bg);
                //city20.setBackgroundResource(R.drawable.btn_bg);
                city21.setBackgroundResource(R.drawable.btn_bg);
                cityother.setVisibility(View.GONE);

            }
        });


        city3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                city_val = city3.getText().toString();

                city3.setBackgroundResource(R.drawable.btn_bg_highlight);
                city1.setBackgroundResource(R.drawable.btn_bg);
                city2.setBackgroundResource(R.drawable.btn_bg);
                city4.setBackgroundResource(R.drawable.btn_bg);
                city5.setBackgroundResource(R.drawable.btn_bg);
                city6.setBackgroundResource(R.drawable.btn_bg);
                city7.setBackgroundResource(R.drawable.btn_bg);
                city8.setBackgroundResource(R.drawable.btn_bg);
                city9.setBackgroundResource(R.drawable.btn_bg);
                city10.setBackgroundResource(R.drawable.btn_bg);
                city11.setBackgroundResource(R.drawable.btn_bg);
                city12.setBackgroundResource(R.drawable.btn_bg);
                city13.setBackgroundResource(R.drawable.btn_bg);
                city14.setBackgroundResource(R.drawable.btn_bg);
                city15.setBackgroundResource(R.drawable.btn_bg);
                city16.setBackgroundResource(R.drawable.btn_bg);
                city17.setBackgroundResource(R.drawable.btn_bg);
                city18.setBackgroundResource(R.drawable.btn_bg);
                city19.setBackgroundResource(R.drawable.btn_bg);
                //city20.setBackgroundResource(R.drawable.btn_bg);
                city21.setBackgroundResource(R.drawable.btn_bg);
                cityother.setVisibility(View.GONE);

            }
        });


        city4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                city_val = city4.getText().toString();

                city4.setBackgroundResource(R.drawable.btn_bg_highlight);
                city2.setBackgroundResource(R.drawable.btn_bg);
                city3.setBackgroundResource(R.drawable.btn_bg);
                city1.setBackgroundResource(R.drawable.btn_bg);
                city5.setBackgroundResource(R.drawable.btn_bg);
                city6.setBackgroundResource(R.drawable.btn_bg);
                city7.setBackgroundResource(R.drawable.btn_bg);
                city8.setBackgroundResource(R.drawable.btn_bg);
                city9.setBackgroundResource(R.drawable.btn_bg);
                city10.setBackgroundResource(R.drawable.btn_bg);
                city11.setBackgroundResource(R.drawable.btn_bg);
                city12.setBackgroundResource(R.drawable.btn_bg);
                city13.setBackgroundResource(R.drawable.btn_bg);
                city14.setBackgroundResource(R.drawable.btn_bg);
                city15.setBackgroundResource(R.drawable.btn_bg);
                city16.setBackgroundResource(R.drawable.btn_bg);
                city17.setBackgroundResource(R.drawable.btn_bg);
                city18.setBackgroundResource(R.drawable.btn_bg);
                city19.setBackgroundResource(R.drawable.btn_bg);
                //city20.setBackgroundResource(R.drawable.btn_bg);
                city21.setBackgroundResource(R.drawable.btn_bg);
                cityother.setVisibility(View.GONE);

            }
        });


        city5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                city_val = city5.getText().toString();

                city5.setBackgroundResource(R.drawable.btn_bg_highlight);
                city2.setBackgroundResource(R.drawable.btn_bg);
                city3.setBackgroundResource(R.drawable.btn_bg);
                city4.setBackgroundResource(R.drawable.btn_bg);
                city1.setBackgroundResource(R.drawable.btn_bg);
                city6.setBackgroundResource(R.drawable.btn_bg);
                city7.setBackgroundResource(R.drawable.btn_bg);
                city8.setBackgroundResource(R.drawable.btn_bg);
                city9.setBackgroundResource(R.drawable.btn_bg);
                city10.setBackgroundResource(R.drawable.btn_bg);
                city11.setBackgroundResource(R.drawable.btn_bg);
                city12.setBackgroundResource(R.drawable.btn_bg);
                city13.setBackgroundResource(R.drawable.btn_bg);
                city14.setBackgroundResource(R.drawable.btn_bg);
                city15.setBackgroundResource(R.drawable.btn_bg);
                city16.setBackgroundResource(R.drawable.btn_bg);
                city17.setBackgroundResource(R.drawable.btn_bg);
                city18.setBackgroundResource(R.drawable.btn_bg);
                city19.setBackgroundResource(R.drawable.btn_bg);
                //city20.setBackgroundResource(R.drawable.btn_bg);
                city21.setBackgroundResource(R.drawable.btn_bg);
                cityother.setVisibility(View.GONE);

            }
        });


        city6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                city_val = city6.getText().toString();

                city6.setBackgroundResource(R.drawable.btn_bg_highlight);
                city2.setBackgroundResource(R.drawable.btn_bg);
                city3.setBackgroundResource(R.drawable.btn_bg);
                city4.setBackgroundResource(R.drawable.btn_bg);
                city5.setBackgroundResource(R.drawable.btn_bg);
                city1.setBackgroundResource(R.drawable.btn_bg);
                city7.setBackgroundResource(R.drawable.btn_bg);
                city8.setBackgroundResource(R.drawable.btn_bg);
                city9.setBackgroundResource(R.drawable.btn_bg);
                city10.setBackgroundResource(R.drawable.btn_bg);
                city11.setBackgroundResource(R.drawable.btn_bg);
                city12.setBackgroundResource(R.drawable.btn_bg);
                city13.setBackgroundResource(R.drawable.btn_bg);
                city14.setBackgroundResource(R.drawable.btn_bg);
                city15.setBackgroundResource(R.drawable.btn_bg);
                city16.setBackgroundResource(R.drawable.btn_bg);
                city17.setBackgroundResource(R.drawable.btn_bg);
                city18.setBackgroundResource(R.drawable.btn_bg);
                city19.setBackgroundResource(R.drawable.btn_bg);
                //city20.setBackgroundResource(R.drawable.btn_bg);
                city21.setBackgroundResource(R.drawable.btn_bg);
                cityother.setVisibility(View.GONE);

            }
        });

        city7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                city_val = city7.getText().toString();

                city7.setBackgroundResource(R.drawable.btn_bg_highlight);
                city2.setBackgroundResource(R.drawable.btn_bg);
                city3.setBackgroundResource(R.drawable.btn_bg);
                city4.setBackgroundResource(R.drawable.btn_bg);
                city5.setBackgroundResource(R.drawable.btn_bg);
                city6.setBackgroundResource(R.drawable.btn_bg);
                city1.setBackgroundResource(R.drawable.btn_bg);
                city8.setBackgroundResource(R.drawable.btn_bg);
                city9.setBackgroundResource(R.drawable.btn_bg);
                city10.setBackgroundResource(R.drawable.btn_bg);
                city11.setBackgroundResource(R.drawable.btn_bg);
                city12.setBackgroundResource(R.drawable.btn_bg);
                city13.setBackgroundResource(R.drawable.btn_bg);
                city14.setBackgroundResource(R.drawable.btn_bg);
                city15.setBackgroundResource(R.drawable.btn_bg);
                city16.setBackgroundResource(R.drawable.btn_bg);
                city17.setBackgroundResource(R.drawable.btn_bg);
                city18.setBackgroundResource(R.drawable.btn_bg);
                city19.setBackgroundResource(R.drawable.btn_bg);
                //city20.setBackgroundResource(R.drawable.btn_bg);
                city21.setBackgroundResource(R.drawable.btn_bg);
                cityother.setVisibility(View.GONE);

            }
        });


        city8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                city_val = city8.getText().toString();

                city8.setBackgroundResource(R.drawable.btn_bg_highlight);
                city2.setBackgroundResource(R.drawable.btn_bg);
                city3.setBackgroundResource(R.drawable.btn_bg);
                city4.setBackgroundResource(R.drawable.btn_bg);
                city5.setBackgroundResource(R.drawable.btn_bg);
                city6.setBackgroundResource(R.drawable.btn_bg);
                city7.setBackgroundResource(R.drawable.btn_bg);
                city1.setBackgroundResource(R.drawable.btn_bg);
                city9.setBackgroundResource(R.drawable.btn_bg);
                city10.setBackgroundResource(R.drawable.btn_bg);
                city11.setBackgroundResource(R.drawable.btn_bg);
                city12.setBackgroundResource(R.drawable.btn_bg);
                city13.setBackgroundResource(R.drawable.btn_bg);
                city14.setBackgroundResource(R.drawable.btn_bg);
                city15.setBackgroundResource(R.drawable.btn_bg);
                city16.setBackgroundResource(R.drawable.btn_bg);
                city17.setBackgroundResource(R.drawable.btn_bg);
                city18.setBackgroundResource(R.drawable.btn_bg);
                city19.setBackgroundResource(R.drawable.btn_bg);
                //city20.setBackgroundResource(R.drawable.btn_bg);
                city21.setBackgroundResource(R.drawable.btn_bg);
                cityother.setVisibility(View.GONE);

            }
        });


        city9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                city_val = city9.getText().toString();

                city9.setBackgroundResource(R.drawable.btn_bg_highlight);
                city2.setBackgroundResource(R.drawable.btn_bg);
                city3.setBackgroundResource(R.drawable.btn_bg);
                city4.setBackgroundResource(R.drawable.btn_bg);
                city5.setBackgroundResource(R.drawable.btn_bg);
                city6.setBackgroundResource(R.drawable.btn_bg);
                city7.setBackgroundResource(R.drawable.btn_bg);
                city8.setBackgroundResource(R.drawable.btn_bg);
                city1.setBackgroundResource(R.drawable.btn_bg);
                city10.setBackgroundResource(R.drawable.btn_bg);
                city11.setBackgroundResource(R.drawable.btn_bg);
                city12.setBackgroundResource(R.drawable.btn_bg);
                city13.setBackgroundResource(R.drawable.btn_bg);
                city14.setBackgroundResource(R.drawable.btn_bg);
                city15.setBackgroundResource(R.drawable.btn_bg);
                city16.setBackgroundResource(R.drawable.btn_bg);
                city17.setBackgroundResource(R.drawable.btn_bg);
                city18.setBackgroundResource(R.drawable.btn_bg);
                city19.setBackgroundResource(R.drawable.btn_bg);
                //city20.setBackgroundResource(R.drawable.btn_bg);
                city21.setBackgroundResource(R.drawable.btn_bg);
                cityother.setVisibility(View.GONE);

            }
        });


        city10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                city_val = city10.getText().toString();

                city10.setBackgroundResource(R.drawable.btn_bg_highlight);
                city2.setBackgroundResource(R.drawable.btn_bg);
                city3.setBackgroundResource(R.drawable.btn_bg);
                city4.setBackgroundResource(R.drawable.btn_bg);
                city5.setBackgroundResource(R.drawable.btn_bg);
                city6.setBackgroundResource(R.drawable.btn_bg);
                city7.setBackgroundResource(R.drawable.btn_bg);
                city8.setBackgroundResource(R.drawable.btn_bg);
                city9.setBackgroundResource(R.drawable.btn_bg);
                city1.setBackgroundResource(R.drawable.btn_bg);
                city11.setBackgroundResource(R.drawable.btn_bg);
                city12.setBackgroundResource(R.drawable.btn_bg);
                city13.setBackgroundResource(R.drawable.btn_bg);
                city14.setBackgroundResource(R.drawable.btn_bg);
                city15.setBackgroundResource(R.drawable.btn_bg);
                city16.setBackgroundResource(R.drawable.btn_bg);
                city17.setBackgroundResource(R.drawable.btn_bg);
                city18.setBackgroundResource(R.drawable.btn_bg);
                city19.setBackgroundResource(R.drawable.btn_bg);
                //city20.setBackgroundResource(R.drawable.btn_bg);
                city21.setBackgroundResource(R.drawable.btn_bg);
                cityother.setVisibility(View.GONE);

            }
        });


        city11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                city_val = city11.getText().toString();

                city11.setBackgroundResource(R.drawable.btn_bg_highlight);
                city2.setBackgroundResource(R.drawable.btn_bg);
                city3.setBackgroundResource(R.drawable.btn_bg);
                city4.setBackgroundResource(R.drawable.btn_bg);
                city5.setBackgroundResource(R.drawable.btn_bg);
                city6.setBackgroundResource(R.drawable.btn_bg);
                city7.setBackgroundResource(R.drawable.btn_bg);
                city8.setBackgroundResource(R.drawable.btn_bg);
                city9.setBackgroundResource(R.drawable.btn_bg);
                city10.setBackgroundResource(R.drawable.btn_bg);
                city1.setBackgroundResource(R.drawable.btn_bg);
                city12.setBackgroundResource(R.drawable.btn_bg);
                city13.setBackgroundResource(R.drawable.btn_bg);
                city14.setBackgroundResource(R.drawable.btn_bg);
                city15.setBackgroundResource(R.drawable.btn_bg);
                city16.setBackgroundResource(R.drawable.btn_bg);
                city17.setBackgroundResource(R.drawable.btn_bg);
                city18.setBackgroundResource(R.drawable.btn_bg);
                city19.setBackgroundResource(R.drawable.btn_bg);
                //city20.setBackgroundResource(R.drawable.btn_bg);
                city21.setBackgroundResource(R.drawable.btn_bg);
                cityother.setVisibility(View.GONE);

            }
        });

        city12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                city_val = city12.getText().toString();

                city12.setBackgroundResource(R.drawable.btn_bg_highlight);
                city2.setBackgroundResource(R.drawable.btn_bg);
                city3.setBackgroundResource(R.drawable.btn_bg);
                city4.setBackgroundResource(R.drawable.btn_bg);
                city5.setBackgroundResource(R.drawable.btn_bg);
                city6.setBackgroundResource(R.drawable.btn_bg);
                city7.setBackgroundResource(R.drawable.btn_bg);
                city8.setBackgroundResource(R.drawable.btn_bg);
                city9.setBackgroundResource(R.drawable.btn_bg);
                city10.setBackgroundResource(R.drawable.btn_bg);
                city11.setBackgroundResource(R.drawable.btn_bg);
                city1.setBackgroundResource(R.drawable.btn_bg);
                city13.setBackgroundResource(R.drawable.btn_bg);
                city14.setBackgroundResource(R.drawable.btn_bg);
                city15.setBackgroundResource(R.drawable.btn_bg);
                city16.setBackgroundResource(R.drawable.btn_bg);
                city17.setBackgroundResource(R.drawable.btn_bg);
                city18.setBackgroundResource(R.drawable.btn_bg);
                city19.setBackgroundResource(R.drawable.btn_bg);
               // city20.setBackgroundResource(R.drawable.btn_bg);
                city21.setBackgroundResource(R.drawable.btn_bg);
                cityother.setVisibility(View.GONE);

            }
        });

        city13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                city_val = city13.getText().toString();

                city13.setBackgroundResource(R.drawable.btn_bg_highlight);
                city2.setBackgroundResource(R.drawable.btn_bg);
                city3.setBackgroundResource(R.drawable.btn_bg);
                city4.setBackgroundResource(R.drawable.btn_bg);
                city5.setBackgroundResource(R.drawable.btn_bg);
                city6.setBackgroundResource(R.drawable.btn_bg);
                city7.setBackgroundResource(R.drawable.btn_bg);
                city8.setBackgroundResource(R.drawable.btn_bg);
                city9.setBackgroundResource(R.drawable.btn_bg);
                city10.setBackgroundResource(R.drawable.btn_bg);
                city11.setBackgroundResource(R.drawable.btn_bg);
                city12.setBackgroundResource(R.drawable.btn_bg);
                city1.setBackgroundResource(R.drawable.btn_bg);
                city14.setBackgroundResource(R.drawable.btn_bg);
                city15.setBackgroundResource(R.drawable.btn_bg);
                city16.setBackgroundResource(R.drawable.btn_bg);
                city17.setBackgroundResource(R.drawable.btn_bg);
                city18.setBackgroundResource(R.drawable.btn_bg);
                city19.setBackgroundResource(R.drawable.btn_bg);
                //city20.setBackgroundResource(R.drawable.btn_bg);
                city21.setBackgroundResource(R.drawable.btn_bg);
                cityother.setVisibility(View.GONE);

            }
        });

        city14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                city_val = city14.getText().toString();

                city14.setBackgroundResource(R.drawable.btn_bg_highlight);
                city2.setBackgroundResource(R.drawable.btn_bg);
                city3.setBackgroundResource(R.drawable.btn_bg);
                city4.setBackgroundResource(R.drawable.btn_bg);
                city5.setBackgroundResource(R.drawable.btn_bg);
                city6.setBackgroundResource(R.drawable.btn_bg);
                city7.setBackgroundResource(R.drawable.btn_bg);
                city8.setBackgroundResource(R.drawable.btn_bg);
                city9.setBackgroundResource(R.drawable.btn_bg);
                city10.setBackgroundResource(R.drawable.btn_bg);
                city11.setBackgroundResource(R.drawable.btn_bg);
                city12.setBackgroundResource(R.drawable.btn_bg);
                city13.setBackgroundResource(R.drawable.btn_bg);
                city1.setBackgroundResource(R.drawable.btn_bg);
                city15.setBackgroundResource(R.drawable.btn_bg);
                city16.setBackgroundResource(R.drawable.btn_bg);
                city17.setBackgroundResource(R.drawable.btn_bg);
                city18.setBackgroundResource(R.drawable.btn_bg);
                city19.setBackgroundResource(R.drawable.btn_bg);
               // city20.setBackgroundResource(R.drawable.btn_bg);
                city21.setBackgroundResource(R.drawable.btn_bg);
                cityother.setVisibility(View.GONE);

            }
        });

        city15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                city_val = city15.getText().toString();

                city15.setBackgroundResource(R.drawable.btn_bg_highlight);
                city2.setBackgroundResource(R.drawable.btn_bg);
                city3.setBackgroundResource(R.drawable.btn_bg);
                city4.setBackgroundResource(R.drawable.btn_bg);
                city5.setBackgroundResource(R.drawable.btn_bg);
                city6.setBackgroundResource(R.drawable.btn_bg);
                city7.setBackgroundResource(R.drawable.btn_bg);
                city8.setBackgroundResource(R.drawable.btn_bg);
                city9.setBackgroundResource(R.drawable.btn_bg);
                city10.setBackgroundResource(R.drawable.btn_bg);
                city11.setBackgroundResource(R.drawable.btn_bg);
                city12.setBackgroundResource(R.drawable.btn_bg);
                city13.setBackgroundResource(R.drawable.btn_bg);
                city14.setBackgroundResource(R.drawable.btn_bg);
                city1.setBackgroundResource(R.drawable.btn_bg);
                city16.setBackgroundResource(R.drawable.btn_bg);
                city17.setBackgroundResource(R.drawable.btn_bg);
                city18.setBackgroundResource(R.drawable.btn_bg);
                city19.setBackgroundResource(R.drawable.btn_bg);
                //city20.setBackgroundResource(R.drawable.btn_bg);
                city21.setBackgroundResource(R.drawable.btn_bg);
                cityother.setVisibility(View.GONE);

            }
        });


        city16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                city_val = city16.getText().toString();

                city16.setBackgroundResource(R.drawable.btn_bg_highlight);
                city2.setBackgroundResource(R.drawable.btn_bg);
                city3.setBackgroundResource(R.drawable.btn_bg);
                city4.setBackgroundResource(R.drawable.btn_bg);
                city5.setBackgroundResource(R.drawable.btn_bg);
                city6.setBackgroundResource(R.drawable.btn_bg);
                city7.setBackgroundResource(R.drawable.btn_bg);
                city8.setBackgroundResource(R.drawable.btn_bg);
                city9.setBackgroundResource(R.drawable.btn_bg);
                city10.setBackgroundResource(R.drawable.btn_bg);
                city11.setBackgroundResource(R.drawable.btn_bg);
                city12.setBackgroundResource(R.drawable.btn_bg);
                city13.setBackgroundResource(R.drawable.btn_bg);
                city14.setBackgroundResource(R.drawable.btn_bg);
                city15.setBackgroundResource(R.drawable.btn_bg);
                city1.setBackgroundResource(R.drawable.btn_bg);
                city17.setBackgroundResource(R.drawable.btn_bg);
                city18.setBackgroundResource(R.drawable.btn_bg);
                city19.setBackgroundResource(R.drawable.btn_bg);
                //city20.setBackgroundResource(R.drawable.btn_bg);
                city21.setBackgroundResource(R.drawable.btn_bg);
                cityother.setVisibility(View.GONE);

            }
        });

        city17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                city_val = city17.getText().toString();

                city17.setBackgroundResource(R.drawable.btn_bg_highlight);
                city2.setBackgroundResource(R.drawable.btn_bg);
                city3.setBackgroundResource(R.drawable.btn_bg);
                city4.setBackgroundResource(R.drawable.btn_bg);
                city5.setBackgroundResource(R.drawable.btn_bg);
                city6.setBackgroundResource(R.drawable.btn_bg);
                city7.setBackgroundResource(R.drawable.btn_bg);
                city8.setBackgroundResource(R.drawable.btn_bg);
                city9.setBackgroundResource(R.drawable.btn_bg);
                city10.setBackgroundResource(R.drawable.btn_bg);
                city11.setBackgroundResource(R.drawable.btn_bg);
                city12.setBackgroundResource(R.drawable.btn_bg);
                city13.setBackgroundResource(R.drawable.btn_bg);
                city14.setBackgroundResource(R.drawable.btn_bg);
                city15.setBackgroundResource(R.drawable.btn_bg);
                city16.setBackgroundResource(R.drawable.btn_bg);
                city1.setBackgroundResource(R.drawable.btn_bg);
                city18.setBackgroundResource(R.drawable.btn_bg);
                city19.setBackgroundResource(R.drawable.btn_bg);
                //city20.setBackgroundResource(R.drawable.btn_bg);
                city21.setBackgroundResource(R.drawable.btn_bg);
                cityother.setVisibility(View.GONE);

            }
        });

        city18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                city_val = city18.getText().toString();

                city18.setBackgroundResource(R.drawable.btn_bg_highlight);
                city2.setBackgroundResource(R.drawable.btn_bg);
                city3.setBackgroundResource(R.drawable.btn_bg);
                city4.setBackgroundResource(R.drawable.btn_bg);
                city5.setBackgroundResource(R.drawable.btn_bg);
                city6.setBackgroundResource(R.drawable.btn_bg);
                city7.setBackgroundResource(R.drawable.btn_bg);
                city8.setBackgroundResource(R.drawable.btn_bg);
                city9.setBackgroundResource(R.drawable.btn_bg);
                city10.setBackgroundResource(R.drawable.btn_bg);
                city11.setBackgroundResource(R.drawable.btn_bg);
                city12.setBackgroundResource(R.drawable.btn_bg);
                city13.setBackgroundResource(R.drawable.btn_bg);
                city14.setBackgroundResource(R.drawable.btn_bg);
                city15.setBackgroundResource(R.drawable.btn_bg);
                city16.setBackgroundResource(R.drawable.btn_bg);
                city17.setBackgroundResource(R.drawable.btn_bg);
                city1.setBackgroundResource(R.drawable.btn_bg);
                city19.setBackgroundResource(R.drawable.btn_bg);
                //city20.setBackgroundResource(R.drawable.btn_bg);
                city21.setBackgroundResource(R.drawable.btn_bg);
                cityother.setVisibility(View.GONE);

            }
        });

       city19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                city_val = city19.getText().toString();

                city19.setBackgroundResource(R.drawable.btn_bg_highlight);
                city2.setBackgroundResource(R.drawable.btn_bg);
                city3.setBackgroundResource(R.drawable.btn_bg);
                city4.setBackgroundResource(R.drawable.btn_bg);
                city5.setBackgroundResource(R.drawable.btn_bg);
                city6.setBackgroundResource(R.drawable.btn_bg);
                city7.setBackgroundResource(R.drawable.btn_bg);
                city8.setBackgroundResource(R.drawable.btn_bg);
                city9.setBackgroundResource(R.drawable.btn_bg);
                city10.setBackgroundResource(R.drawable.btn_bg);
                city11.setBackgroundResource(R.drawable.btn_bg);
                city12.setBackgroundResource(R.drawable.btn_bg);
                city13.setBackgroundResource(R.drawable.btn_bg);
                city14.setBackgroundResource(R.drawable.btn_bg);
                city15.setBackgroundResource(R.drawable.btn_bg);
                city16.setBackgroundResource(R.drawable.btn_bg);
                city17.setBackgroundResource(R.drawable.btn_bg);
                city18.setBackgroundResource(R.drawable.btn_bg);
                city1.setBackgroundResource(R.drawable.btn_bg);
               // city20.setBackgroundResource(R.drawable.btn_bg);
                city21.setBackgroundResource(R.drawable.btn_bg);
                cityother.setVisibility(View.GONE);

            }
        });
 /*
        city20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                city_val = city20.getText().toString();

                city20.setBackgroundResource(R.drawable.btn_bg_highlight);
                city2.setBackgroundResource(R.drawable.btn_bg);
                city3.setBackgroundResource(R.drawable.btn_bg);
                city4.setBackgroundResource(R.drawable.btn_bg);
                city5.setBackgroundResource(R.drawable.btn_bg);
                city6.setBackgroundResource(R.drawable.btn_bg);
                city7.setBackgroundResource(R.drawable.btn_bg);
                city8.setBackgroundResource(R.drawable.btn_bg);
                city9.setBackgroundResource(R.drawable.btn_bg);
                city10.setBackgroundResource(R.drawable.btn_bg);
                city11.setBackgroundResource(R.drawable.btn_bg);
                city12.setBackgroundResource(R.drawable.btn_bg);
                city13.setBackgroundResource(R.drawable.btn_bg);
                city14.setBackgroundResource(R.drawable.btn_bg);
                city15.setBackgroundResource(R.drawable.btn_bg);
                city16.setBackgroundResource(R.drawable.btn_bg);
                city17.setBackgroundResource(R.drawable.btn_bg);
                city18.setBackgroundResource(R.drawable.btn_bg);
                city19.setBackgroundResource(R.drawable.btn_bg);
                city1.setBackgroundResource(R.drawable.btn_bg);
                city21.setBackgroundResource(R.drawable.btn_bg);
                cityother.setVisibility(View.INVISIBLE);

            }
        });*/

        city21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                city_val = city21.getText().toString();

                city21.setBackgroundResource(R.drawable.btn_bg_highlight);
                city2.setBackgroundResource(R.drawable.btn_bg);
                city3.setBackgroundResource(R.drawable.btn_bg);
                city4.setBackgroundResource(R.drawable.btn_bg);
                city5.setBackgroundResource(R.drawable.btn_bg);
                city6.setBackgroundResource(R.drawable.btn_bg);
                city7.setBackgroundResource(R.drawable.btn_bg);
                city8.setBackgroundResource(R.drawable.btn_bg);
                city9.setBackgroundResource(R.drawable.btn_bg);
                city10.setBackgroundResource(R.drawable.btn_bg);
                city11.setBackgroundResource(R.drawable.btn_bg);
                city12.setBackgroundResource(R.drawable.btn_bg);
                city13.setBackgroundResource(R.drawable.btn_bg);
                city14.setBackgroundResource(R.drawable.btn_bg);
                city15.setBackgroundResource(R.drawable.btn_bg);
                city16.setBackgroundResource(R.drawable.btn_bg);
                city17.setBackgroundResource(R.drawable.btn_bg);
                city18.setBackgroundResource(R.drawable.btn_bg);
                //city19.setBackgroundResource(R.drawable.btn_bg);
                //city20.setBackgroundResource(R.drawable.btn_bg);
                city1.setBackgroundResource(R.drawable.btn_bg);
                cityother.setVisibility(View.VISIBLE);

            }
        });


        qual1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                qualification_val = qual1.getText().toString();
                qual1.setBackgroundResource(R.drawable.btn_bg_highlight);
                qual2.setBackgroundResource(R.drawable.btn_bg);
                qual3.setBackgroundResource(R.drawable.btn_bg);

            }
        });

        qual2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                qualification_val = qual2.getText().toString();
                qual2.setBackgroundResource(R.drawable.btn_bg_highlight);
                qual1.setBackgroundResource(R.drawable.btn_bg);
                qual3.setBackgroundResource(R.drawable.btn_bg);

            }
        });

        qual3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                qualification_val = qual3.getText().toString();
                qual3.setBackgroundResource(R.drawable.btn_bg_highlight);
                qual2.setBackgroundResource(R.drawable.btn_bg);
                qual1.setBackgroundResource(R.drawable.btn_bg);

            }
        });


        exp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                no_exp_val = exp1.getText().toString();
                exp1.setBackgroundResource(R.drawable.btn_bg_highlight);
                exp2.setBackgroundResource(R.drawable.btn_bg);
                exp3.setBackgroundResource(R.drawable.btn_bg);
                exp4.setBackgroundResource(R.drawable.btn_bg);
                exp5.setBackgroundResource(R.drawable.btn_bg);

            }
        });

        exp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                no_exp_val = exp1.getText().toString();
                exp1.setBackgroundResource(R.drawable.btn_bg_highlight);
                exp2.setBackgroundResource(R.drawable.btn_bg);
                exp3.setBackgroundResource(R.drawable.btn_bg);
                exp4.setBackgroundResource(R.drawable.btn_bg);
                exp5.setBackgroundResource(R.drawable.btn_bg);

            }
        });
        exp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                no_exp_val = exp2.getText().toString();
                exp2.setBackgroundResource(R.drawable.btn_bg_highlight);
                exp1.setBackgroundResource(R.drawable.btn_bg);
                exp3.setBackgroundResource(R.drawable.btn_bg);
                exp4.setBackgroundResource(R.drawable.btn_bg);
                exp5.setBackgroundResource(R.drawable.btn_bg);

            }
        });

        exp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                no_exp_val = exp3.getText().toString();
                exp3.setBackgroundResource(R.drawable.btn_bg_highlight);
                exp2.setBackgroundResource(R.drawable.btn_bg);
                exp1.setBackgroundResource(R.drawable.btn_bg);
                exp4.setBackgroundResource(R.drawable.btn_bg);
                exp5.setBackgroundResource(R.drawable.btn_bg);

            }
        });

        exp4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                no_exp_val = exp4.getText().toString();
                exp4.setBackgroundResource(R.drawable.btn_bg_highlight);
                exp2.setBackgroundResource(R.drawable.btn_bg);
                exp3.setBackgroundResource(R.drawable.btn_bg);
                exp1.setBackgroundResource(R.drawable.btn_bg);
                exp5.setBackgroundResource(R.drawable.btn_bg);

            }
        });

        exp5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                no_exp_val = exp5.getText().toString();
                exp5.setBackgroundResource(R.drawable.btn_bg_highlight);
                exp2.setBackgroundResource(R.drawable.btn_bg);
                exp3.setBackgroundResource(R.drawable.btn_bg);
                exp4.setBackgroundResource(R.drawable.btn_bg);
                exp1.setBackgroundResource(R.drawable.btn_bg);

            }
        });


    }

    private void validation() {
       /* //qualification = view.findViewById(R.id.qualification);
        no_of_experience = view.findViewById(R.id.no_of_experience);
        radio = view.findViewById(R.id.radio);
        address = view.findViewById(R.id.address);


        // Reset errors.
        qualification.setError(null);
        sales_details.setError(null);
        no_of_experience.setError(null);
        address.setError(null);


        // Store values at the time of the login attempt.
        Qualification = qualification.getText().toString();
        Sales_details = sales_details.getText().toString();
        No_of_experience = no_of_experience.getText().toString();
        int chechId = radio.getCheckedRadioButtonId();
        Experience=((RadioButton)view.findViewById(chechId)).getText().toString();
        Address = address.getText().toString();


        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.

        if (TextUtils.isEmpty(No_of_experience)) {
            no_of_experience.setError("Please enter experience");
            focusView = no_of_experience;
            cancel = true;
        }


        if (TextUtils.isEmpty(Qualification)) {
            qualification.setError("Please enter your highest qualification");
            focusView = qualification;
            cancel = true;
        }


        if (TextUtils.isEmpty(Address)) {
            address.setError("Please enter your address");
            focusView = address;
            cancel = true;
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else{

            if (InternetConnection.checkInternetConnectivity()) {
                getData();
            } else {
                final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(getActivity());
                alertDialog.setMessage("Retry with internet connection.");
                alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.show();
            }
        }*/
    }


    private void checkValidation() {

        qual_error.setVisibility(View.GONE);
        fresher_error.setVisibility(View.GONE);
        age_error.setVisibility(View.GONE);
        gender_error.setVisibility(View.GONE);
        city_error.setVisibility(View.GONE);
        vehicle_error.setVisibility(View.GONE);
        experience_years_error.setVisibility(View.GONE);
        industry_error.setVisibility(View.GONE);
        experience_details_error.setVisibility(View.GONE);

        company1 = et_company1.getText().toString();
        exp_dur1 = et_dur1.getText().toString();
        sold1 = et_exp1.getText().toString();

        cityother.setError(null);



      /*  qual_error.setVisibility(View.VISIBLE);
        head_qual.setFocusableInTouchMode(true);
        head_qual.requestFocus();*/


       /* qual_error.setVisibility(View.VISIBLE);
        head_qual.setFocusableInTouchMode(true);
        head_qual.requestFocus()*/


        boolean cancel = false;
        View focusView = null;

        Log.e("aobsales ", "company1 " + industry_val);
        Log.e("aobsales ", "company2 " + exp_dur1);
        Log.e("aobsales ", "company3 " + sold1);


        if (experience_full.getVisibility() == View.VISIBLE) {
            if (TextUtils.isEmpty(company1)) {
                experience_details_error.setVisibility(View.VISIBLE);
                head_experience_details.setFocusableInTouchMode(true);
                focusView = head_experience_details;
                cancel = true;
            }

            if (TextUtils.isEmpty(exp_dur1)) {
                experience_details_error.setVisibility(View.VISIBLE);
                head_experience_details.setFocusableInTouchMode(true);
                focusView = head_experience_details;
                cancel = true;
            }
            if (TextUtils.isEmpty(sold1)) {
                experience_details_error.setVisibility(View.VISIBLE);
                head_experience_details.setFocusableInTouchMode(true);
                focusView = head_experience_details;
                cancel = true;
            }

            if (TextUtils.isEmpty(industry_val)) {
                industry_error.setVisibility(View.VISIBLE);
                head_industry.setFocusableInTouchMode(true);
                focusView = head_industry;
                cancel = true;
                Log.e("aobsales","gere");
            }

            if (TextUtils.isEmpty(no_exp_val)) {
                experience_years_error.setVisibility(View.VISIBLE);
                head_experience_years.setFocusableInTouchMode(true);
                focusView = head_experience_years;
                cancel = true;
            }

        }

        if (TextUtils.isEmpty(fresher_val)) {
            fresher_error.setVisibility(View.VISIBLE);
            head_fresher.setFocusableInTouchMode(true);
            focusView = head_fresher;
            cancel = true;
        }


        if (TextUtils.isEmpty(qualification_val)) {

            qual_error.setVisibility(View.VISIBLE);
            head_qual.setFocusableInTouchMode(true);
            focusView = head_qual;
            cancel = true;
        }


        if (TextUtils.isEmpty(vehicle_val)) {

            vehicle_error.setVisibility(View.VISIBLE);
            head_vehicle.setFocusableInTouchMode(true);
            focusView = head_vehicle;
            cancel = true;

        }

        if (TextUtils.isEmpty(gender_val)) {

            gender_error.setVisibility(View.VISIBLE);
            head_gender.setFocusableInTouchMode(true);
            focusView = head_gender;
            cancel = true;

        }

        if (TextUtils.isEmpty(age_val)) {

            age_error.setVisibility(View.VISIBLE);
            head_age.setFocusableInTouchMode(true);
            focusView = head_age;
            cancel = true;

        }

        if (TextUtils.isEmpty(city_val)) {


            city_error.setVisibility(View.VISIBLE);
            head_city.setFocusableInTouchMode(true);
            focusView = head_city;
            cancel = true;


        } else if (city_val.equalsIgnoreCase("Other")) {

            Log.e("aobsales", "i am here");

            if (cityother.getVisibility() == View.VISIBLE) {
                String text = cityother.getText().toString();
                if (TextUtils.isEmpty(text)) {
                    cityother.setError("Please enter your city");

                    city_error.setVisibility(View.VISIBLE);
                    head_city.setFocusableInTouchMode(true);
                    focusView = head_city;
                    cancel = true;
                }


            }

        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.

            Log.e("aobsales", "rest " + focusView);
            focusView.requestFocus();

        } else {

            if (InternetConnection.checkInternetConnectivity()) {

                try {

                    android_id = Settings.Secure.getString(getContext().getContentResolver(),
                            Settings.Secure.ANDROID_ID);

                } catch (Exception e) {
                }

                jsonParseForLogin();
            } else {
                final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(getActivity());
                alertDialog.setMessage("Retry with Internet connection");
                alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.show();
            }
        }


    }


    private void getData() {
        /*Bundle bundle=new Bundle();
        bundle.putString("Name",getArguments().getString("Name"));
        bundle.putString("Email",getArguments().getString("Email"));
        bundle.putString("Password",getArguments().getString("Password"));
        bundle.putString("Address",getArguments().getString("Address"));
        bundle.putString("Mobile",getArguments().getString("Mobile"));

        bundle.putString("Qualification",Qualification);
        bundle.putString("Sales_details",Sales_details);
        bundle.putString("Experience",Experience);
        bundle.putString("No_of_experience",No_of_experience);

        Fragement3 fragement3=new Fragement3();
        fragement3.setArguments(bundle);
        FragmentManager manager=getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=manager.beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragement3);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();*/

    }

    private void jsonParseForLogin() {

        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                getResources().getString(R.string.hostName)+"/app/sales/v1_0/registration_profile_api.php?",
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("status");

                            Log.e("aobsales", "response " + response);

                            if (success.equalsIgnoreCase("0")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                               //    Toast.makeText(getActivity(),  jsonObject1.getString("register_status"), Toast.LENGTH_SHORT).show();
                                  ShareprefreancesUtility.getInstance().saveString("register_status", jsonObject1.getString("register_status"));
                                    ShareprefreancesUtility.getInstance().saveString("name", jsonObject1.getString("name"));
                                    ShareprefreancesUtility.getInstance().saveString("password", jsonObject1.getString("password"));
                                    ShareprefreancesUtility.getInstance().saveString("email", jsonObject1.getString("email"));
                                    ShareprefreancesUtility.getInstance().saveString("age", jsonObject1.getString("age"));
                                    ShareprefreancesUtility.getInstance().saveString("gender", jsonObject1.getString("gender"));
                                    ShareprefreancesUtility.getInstance().saveString("vehicle", jsonObject1.getString("vehicle"));
                                    ShareprefreancesUtility.getInstance().saveString("qualification", jsonObject1.getString("qualification"));
                                    ShareprefreancesUtility.getInstance().saveString("city", jsonObject1.getString("city"));
                                    ShareprefreancesUtility.getInstance().saveString("highest_qualification", jsonObject1.getString("highest_qualification"));
                                    ShareprefreancesUtility.getInstance().saveString("experience_type", jsonObject1.getString("experience_type"));
                                    ShareprefreancesUtility.getInstance().saveString("year_of_experience", jsonObject1.getString("year_of_experience"));
                                    ShareprefreancesUtility.getInstance().saveString("industry_of_interest", jsonObject1.getString("industry_of_interest"));

                                    ShareprefreancesUtility.getInstance().saveString("company_name_1", jsonObject1.getString("company_name_1"));
                                    ShareprefreancesUtility.getInstance().saveString("duration_of_experience_1", jsonObject1.getString("duration_of_experience_1"));
                                    ShareprefreancesUtility.getInstance().saveString("what_sold_1", jsonObject1.getString("what_sold_1"));

                                    ShareprefreancesUtility.getInstance().saveString("company_name_2", jsonObject1.getString("company_name_2"));
                                    ShareprefreancesUtility.getInstance().saveString("duration_of_experience_2", jsonObject1.getString("duration_of_experience_2"));
                                    ShareprefreancesUtility.getInstance().saveString("what_sold_2", jsonObject1.getString("what_sold_2"));

                                    ShareprefreancesUtility.getInstance().saveString("company_name_3", jsonObject1.getString("company_name_3"));
                                    ShareprefreancesUtility.getInstance().saveString("duration_of_experience_3", jsonObject1.getString("duration_of_experience_3"));
                                    ShareprefreancesUtility.getInstance().saveString("what_sold_3", jsonObject1.getString("what_sold_3"));

                                }

                                Intent intent=new Intent(getActivity(), ProfileActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                        Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                //getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                            }
                            else if (success.equals("1")){
                                Toast.makeText(getActivity(), "Registration failed", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(getActivity());
                            alertDialog.setMessage("Please check your Internet connection");
                            alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            alertDialog.show();
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(getActivity());
                alertDialog.setTitle("Oops! Something went wrong.");
                alertDialog.setMessage("This page didn't load correctly.\nPlease try again.");
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("email", ShareprefreancesUtility.getInstance().getString("email", ""));
                params.put("name", ShareprefreancesUtility.getInstance().getString("name", ""));
                params.put("password", ShareprefreancesUtility.getInstance().getString("password", ""));

                if(cityother.getVisibility() == View.VISIBLE)
                {
                    params.put("city", cityother.getText().toString());
                }
                else
                {
                    params.put("city", city_val);
                }


                params.put("age", age_val);
                params.put("gender", gender_val);
                params.put("vehicle", vehicle_val);
                params.put("qualification", qualification_val);
                params.put("highest_qualification", highest_qualification.getText().toString());
                params.put("experience_type", fresher_val);
                params.put("year_of_experience", no_exp_val);

                params.put("industry_of_interest",industry_val);

                params.put("company_name_1", company1);
                params.put("duration_of_experience_1", exp_dur1);
                params.put("what_sold_1", sold1);

                params.put("company_name_2", et_company2.getText().toString());
                params.put("duration_of_experience_2", et_dur2.getText().toString());
                params.put("what_sold_2", et_exp2.getText().toString());

                params.put("company_name_3", et_company3.getText().toString());
                params.put("duration_of_experience_3", et_dur3.getText().toString());
                params.put("what_sold_3", et_exp3.getText().toString());

                params.put("android_id", android_id);


                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);

    }

    public void setValueAfterRegsitration() {

try {


    if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("age", ""))
            && !ShareprefreancesUtility.getInstance().getString("age", "").equalsIgnoreCase("null")
            && ShareprefreancesUtility.getInstance().getString("age", "") != null
    ) {

        age_val = ShareprefreancesUtility.getInstance().getString("age", "");
        tv_age.setText(ShareprefreancesUtility.getInstance().getString("age", ""));


    }

    if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("city", ""))
            && !ShareprefreancesUtility.getInstance().getString("city", "").equalsIgnoreCase("null")
            && ShareprefreancesUtility.getInstance().getString("city", "") != null
    ) {

        if (ShareprefreancesUtility.getInstance().getString("city", "").equalsIgnoreCase(city1.getText().toString())) {
            city_val = city1.getText().toString();
            city1.setBackgroundResource(R.drawable.btn_bg_highlight);

        } else if (ShareprefreancesUtility.getInstance().getString("city", "").equalsIgnoreCase(city2.getText().toString())) {
            city_val = city2.getText().toString();
            city2.setBackgroundResource(R.drawable.btn_bg_highlight);


        } else if (ShareprefreancesUtility.getInstance().getString("city", "").equalsIgnoreCase(city3.getText().toString())) {
            city_val = city3.getText().toString();
            city3.setBackgroundResource(R.drawable.btn_bg_highlight);


        } else if (ShareprefreancesUtility.getInstance().getString("city", "").equalsIgnoreCase(city4.getText().toString())) {
            city_val = city4.getText().toString();
            city4.setBackgroundResource(R.drawable.btn_bg_highlight);


        } else if (ShareprefreancesUtility.getInstance().getString("city", "").equalsIgnoreCase(city5.getText().toString())) {
            city_val = city5.getText().toString();
            city5.setBackgroundResource(R.drawable.btn_bg_highlight);


        } else if (ShareprefreancesUtility.getInstance().getString("city", "").equalsIgnoreCase(city6.getText().toString())) {
            city_val = city6.getText().toString();
            city6.setBackgroundResource(R.drawable.btn_bg_highlight);


        } else if (ShareprefreancesUtility.getInstance().getString("city", "").equalsIgnoreCase(city7.getText().toString())) {
            city_val = city7.getText().toString();
            city7.setBackgroundResource(R.drawable.btn_bg_highlight);


        } else if (ShareprefreancesUtility.getInstance().getString("city", "").equalsIgnoreCase(city8.getText().toString())) {
            city_val = city8.getText().toString();
            city8.setBackgroundResource(R.drawable.btn_bg_highlight);


        } else if (ShareprefreancesUtility.getInstance().getString("city", "").equalsIgnoreCase(city9.getText().toString())) {
            city_val = city9.getText().toString();
            city9.setBackgroundResource(R.drawable.btn_bg_highlight);


        } else if (ShareprefreancesUtility.getInstance().getString("city", "").equalsIgnoreCase(city10.getText().toString())) {
            city_val = city10.getText().toString();
            city10.setBackgroundResource(R.drawable.btn_bg_highlight);


        } else if (ShareprefreancesUtility.getInstance().getString("city", "").equalsIgnoreCase(city11.getText().toString())) {
            city_val = city11.getText().toString();
            city11.setBackgroundResource(R.drawable.btn_bg_highlight);


        } else if (ShareprefreancesUtility.getInstance().getString("city", "").equalsIgnoreCase(city12.getText().toString())) {
            city_val = city12.getText().toString();
            city12.setBackgroundResource(R.drawable.btn_bg_highlight);


        } else if (ShareprefreancesUtility.getInstance().getString("city", "").equalsIgnoreCase(city13.getText().toString())) {
            city_val = city13.getText().toString();
            city13.setBackgroundResource(R.drawable.btn_bg_highlight);


        } else if (ShareprefreancesUtility.getInstance().getString("city", "").equalsIgnoreCase(city14.getText().toString())) {
            city_val = city14.getText().toString();
            city14.setBackgroundResource(R.drawable.btn_bg_highlight);


        } else if (ShareprefreancesUtility.getInstance().getString("city", "").equalsIgnoreCase(city15.getText().toString())) {
            city_val = city15.getText().toString();
            city15.setBackgroundResource(R.drawable.btn_bg_highlight);


        } else if (ShareprefreancesUtility.getInstance().getString("city", "").equalsIgnoreCase(city16.getText().toString())) {
            city_val = city16.getText().toString();
            city16.setBackgroundResource(R.drawable.btn_bg_highlight);


        } else if (ShareprefreancesUtility.getInstance().getString("city", "").equalsIgnoreCase(city17.getText().toString())) {
            city_val = city17.getText().toString();
            city17.setBackgroundResource(R.drawable.btn_bg_highlight);


        } else if (ShareprefreancesUtility.getInstance().getString("city", "").equalsIgnoreCase(city18.getText().toString())) {
            city_val = city18.getText().toString();
            city18.setBackgroundResource(R.drawable.btn_bg_highlight);


        } else if (ShareprefreancesUtility.getInstance().getString("city", "").equalsIgnoreCase(city19.getText().toString())) {
            city_val = city19.getText().toString();
            city19.setBackgroundResource(R.drawable.btn_bg_highlight);


        } /*else if (ShareprefreancesUtility.getInstance().getString("city", "").equalsIgnoreCase(city20.getText().toString())) {
            city_val = city20.getText().toString();
            city20.setBackgroundResource(R.drawable.btn_bg_highlight);


        }*/ else {

            city_val = city21.getText().toString();
            city21.setBackgroundResource(R.drawable.btn_bg_highlight);
            cityother.setVisibility(View.VISIBLE);
            cityother.setText(ShareprefreancesUtility.getInstance().getString("city", ""));

        }
    }

    if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("gender", ""))
            && !ShareprefreancesUtility.getInstance().getString("gender", "").equalsIgnoreCase("null")
            && ShareprefreancesUtility.getInstance().getString("gender", "") != null
    ) {

        if (ShareprefreancesUtility.getInstance().getString("gender", "").equalsIgnoreCase(btn_male.getText().toString())) {
            gender_val = btn_male.getText().toString();
            btn_male.setBackgroundResource(R.drawable.btn_bg_highlight);

        } else {
            gender_val = btn_female.getText().toString();
            btn_female.setBackgroundResource(R.drawable.btn_bg_highlight);


        }

    }

    if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("vehicle", ""))
            && !ShareprefreancesUtility.getInstance().getString("vehicle", "").equalsIgnoreCase("null")
            && ShareprefreancesUtility.getInstance().getString("vehicle", "") != null
    ) {

        if (ShareprefreancesUtility.getInstance().getString("vehicle", "").equalsIgnoreCase(btn_2wheeler.getText().toString())) {
            vehicle_val = btn_2wheeler.getText().toString();
            btn_2wheeler.setBackgroundResource(R.drawable.btn_bg_highlight);

        } else if (ShareprefreancesUtility.getInstance().getString("vehicle", "").equalsIgnoreCase(btn_4wheeler.getText().toString())) {
            vehicle_val = btn_4wheeler.getText().toString();
            btn_4wheeler.setBackgroundResource(R.drawable.btn_bg_highlight);


        } else {
            vehicle_val = btn_novehicle.getText().toString();
            btn_novehicle.setBackgroundResource(R.drawable.btn_bg_highlight);
        }

    }

    if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("qualification", ""))
            && !ShareprefreancesUtility.getInstance().getString("qualification", "").equalsIgnoreCase("null")
            && ShareprefreancesUtility.getInstance().getString("qualification", "") != null
    ) {

        if (ShareprefreancesUtility.getInstance().getString("qualification", "").equalsIgnoreCase(qual1.getText().toString())) {
            qualification_val = qual1.getText().toString();
            qual1.setBackgroundResource(R.drawable.btn_bg_highlight);

        } else if (ShareprefreancesUtility.getInstance().getString("qualification", "").equalsIgnoreCase(qual2.getText().toString())) {
            qualification_val = qual2.getText().toString();
            qual2.setBackgroundResource(R.drawable.btn_bg_highlight);


        } else {
            qualification_val = qual3.getText().toString();
            qual3.setBackgroundResource(R.drawable.btn_bg_highlight);
        }

    }

    if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("highest_qualification", ""))
            && !ShareprefreancesUtility.getInstance().getString("highest_qualification", "").equalsIgnoreCase("null")
            && ShareprefreancesUtility.getInstance().getString("highest_qualification", "") != null
    ) {

        highest_qual_val = ShareprefreancesUtility.getInstance().getString("highest_qualification", "");
        highest_qualification.setText(ShareprefreancesUtility.getInstance().getString("highest_qualification", ""));


    }




    if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("experience_type", ""))
            && !ShareprefreancesUtility.getInstance().getString("experience_type", "").equalsIgnoreCase("null")
            && ShareprefreancesUtility.getInstance().getString("experience_type", "") != null
    ) {

        if (ShareprefreancesUtility.getInstance().getString("experience_type", "").equalsIgnoreCase(fresher.getText().toString())) {
            fresher_val = fresher.getText().toString();
            fresher.setBackgroundResource(R.drawable.btn_bg_highlight);

        } else {
            fresher_val = experience.getText().toString();
            experience.setBackgroundResource(R.drawable.btn_bg_highlight);
            experience_full.setVisibility(View.VISIBLE);


        }

    }

    if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("year_of_experience", ""))
            && !ShareprefreancesUtility.getInstance().getString("year_of_experience", "").equalsIgnoreCase("null")
            && ShareprefreancesUtility.getInstance().getString("year_of_experience", "") != null
    ) {

        if (ShareprefreancesUtility.getInstance().getString("year_of_experience", "").equalsIgnoreCase(exp1.getText().toString())) {
            no_exp_val = exp1.getText().toString();
            exp1.setBackgroundResource(R.drawable.btn_bg_highlight);

        } else if (ShareprefreancesUtility.getInstance().getString("year_of_experience", "").equalsIgnoreCase(exp2.getText().toString())) {
            no_exp_val = exp2.getText().toString();
            exp2.setBackgroundResource(R.drawable.btn_bg_highlight);


        } else if (ShareprefreancesUtility.getInstance().getString("year_of_experience", "").equalsIgnoreCase(exp3.getText().toString())) {
            no_exp_val = exp3.getText().toString();
            exp3.setBackgroundResource(R.drawable.btn_bg_highlight);


        } else if (ShareprefreancesUtility.getInstance().getString("year_of_experience", "").equalsIgnoreCase(exp4.getText().toString())) {
            no_exp_val = exp4.getText().toString();
            exp4.setBackgroundResource(R.drawable.btn_bg_highlight);


        } else {
            no_exp_val = exp5.getText().toString();
            exp5.setBackgroundResource(R.drawable.btn_bg_highlight);
        }

    }

    Log.e("aobsales", "test " + ShareprefreancesUtility.getInstance().getString("industry_of_interest", "") );
    if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("industry_of_interest", ""))
            && !ShareprefreancesUtility.getInstance().getString("industry_of_interest", "").equalsIgnoreCase("null")
            && ShareprefreancesUtility.getInstance().getString("industry_of_interest", "") != null
    ) {


        industry_val =ShareprefreancesUtility.getInstance().getString("industry_of_interest", "");
        Log.e("aobsales", "test " + industry_val);
        itemname = new ArrayList(Arrays.asList(industry_val.split("\\s*,\\s*")));


        for (int i = 0; i < itemname.size(); i++) {
            Chip chip = new Chip(getActivity());
            chip.setText(itemname.get(i));
            chip.setChipBackgroundColorResource(R.color.colorTransparent);
            chip.setChipStrokeColorResource(R.color.colorPrimary);
            chip.setChipCornerRadius(20);
            chip.setChipStrokeWidth(1);
            chipgroup_main.addView(chip);

        }


    }


    if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("company_name_1", ""))
            && !ShareprefreancesUtility.getInstance().getString("company_name_1", "").equalsIgnoreCase("null")
            && ShareprefreancesUtility.getInstance().getString("company_name_1", "") != null
    ) {

        company1 = ShareprefreancesUtility.getInstance().getString("company_name_1", "");
        et_company1.setText(ShareprefreancesUtility.getInstance().getString("company_name_1", ""));


    }

    if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("duration_of_experience_1", ""))
            && !ShareprefreancesUtility.getInstance().getString("duration_of_experience_1", "").equalsIgnoreCase("null")
            && ShareprefreancesUtility.getInstance().getString("duration_of_experience_1", "") != null
    ) {

        exp_dur1 = ShareprefreancesUtility.getInstance().getString("duration_of_experience_1", "");
        et_dur1.setText(ShareprefreancesUtility.getInstance().getString("duration_of_experience_1", ""));


    }

    if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("what_sold_1", ""))
            && !ShareprefreancesUtility.getInstance().getString("what_sold_1", "").equalsIgnoreCase("null")
            && ShareprefreancesUtility.getInstance().getString("what_sold_1", "") != null
    ) {

        sold1 = ShareprefreancesUtility.getInstance().getString("what_sold_1", "");
        et_exp1.setText(ShareprefreancesUtility.getInstance().getString("what_sold_1", ""));


    }


    if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("company_name_2", ""))
            && !ShareprefreancesUtility.getInstance().getString("company_name_2", "").equalsIgnoreCase("null")
            && ShareprefreancesUtility.getInstance().getString("company_name_2", "") != null
    ) {

        exp_linear2.setVisibility(View.VISIBLE);
        et_company2.setText(ShareprefreancesUtility.getInstance().getString("company_name_2", ""));


    }

    if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("duration_of_experience_2", ""))
            && !ShareprefreancesUtility.getInstance().getString("duration_of_experience_2", "").equalsIgnoreCase("null")
            && ShareprefreancesUtility.getInstance().getString("duration_of_experience_2", "") != null
    ) {

        exp_linear2.setVisibility(View.VISIBLE);
        et_dur2.setText(ShareprefreancesUtility.getInstance().getString("duration_of_experience_2", ""));


    }

    if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("what_sold_2", ""))
            && !ShareprefreancesUtility.getInstance().getString("what_sold_2", "").equalsIgnoreCase("null")
            && ShareprefreancesUtility.getInstance().getString("what_sold_2", "") != null
    ) {

        exp_linear2.setVisibility(View.VISIBLE);
        et_exp2.setText(ShareprefreancesUtility.getInstance().getString("what_sold_2", ""));


    }


    if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("company_name_3", ""))
            && !ShareprefreancesUtility.getInstance().getString("company_name_3", "").equalsIgnoreCase("null")
            && ShareprefreancesUtility.getInstance().getString("company_name_3", "") != null
    ) {

        exp_linear3.setVisibility(View.VISIBLE);
        et_company3.setText(ShareprefreancesUtility.getInstance().getString("company_name_3", ""));


    }

    if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("duration_of_experience_3", ""))
            && !ShareprefreancesUtility.getInstance().getString("duration_of_experience_3", "").equalsIgnoreCase("null")
            && ShareprefreancesUtility.getInstance().getString("duration_of_experience_3", "") != null
    ) {

        exp_linear3.setVisibility(View.VISIBLE);
        et_dur3.setText(ShareprefreancesUtility.getInstance().getString("duration_of_experience_3", ""));


    }

    if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("what_sold_3", ""))
            && !ShareprefreancesUtility.getInstance().getString("what_sold_3", "").equalsIgnoreCase("null")
            && ShareprefreancesUtility.getInstance().getString("what_sold_3", "") != null
    ) {

        exp_linear3.setVisibility(View.VISIBLE);
        et_exp3.setText(ShareprefreancesUtility.getInstance().getString("what_sold_3", ""));


    }


}catch (Exception e){}
    }


    public static class IndustryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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


        public IndustryAdapter(Context context,String[] items, List<String> allchecked1 ) {

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
            vh = new IndustryAdapter.OriginalViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            Log.e("onBindViewHolder", "onBindViewHolder : " + position);
            if (holder instanceof OriginalViewHolder) {
                final OriginalViewHolder view = (OriginalViewHolder) holder;

                view.name_industry.setText(items[position]);

                Log.e("aobsales","hello "+items[position]);
                Log.e("aobsales","hello1 "+allchecked.size());

                Boolean test1 = allchecked.contains(items[position]);

                Log.e("aobsales","hello "+test1+" value "+items[position]+" pos "+position);

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

