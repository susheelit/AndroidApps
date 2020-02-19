package com.aob.aobsalesman.activities.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.aob.aobsalesman.R;
import com.aob.aobsalesman.activities.utility.ShareprefreancesUtility;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.kofigyan.stateprogressbar.StateProgressBar;
import java.util.ArrayList;
import java.util.Arrays;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ProfileActivity extends AppCompatActivity {

    TextView city,age,gender, vehicle ,qualification, experience, highest_qualification, no_years_exp;
    ChipGroup chipgroup_profile;
    LinearLayout no_exp_linear, industry_interest_linear;
    CardView profile;
    TextView tv_register_status;
    ProgressBar progressBar;

    String[] descriptionData = {"KYC Pending", "Verification Pending", "Approved"};
    String[] descriptionData1 = {"KYC Submitted", "Verification Pending", "Approved"};
    String[] descriptionData2 = {"KYC Submitted", "Verification Done", "Approved"};

    private long mLastClickTime2 = 0;
    private long mLastClickTime3 = 0;
    private long mLastClickTime4 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initToolbar();
        initViews();
        setFunctionality();
        setValueAfterRegsitration();

    }
    private void initToolbar() {
        ImageView back_image = findViewById(R.id.back_image);
        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProfileActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(ProfileActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private void initViews()
    {
        city = findViewById(R.id.city_p);
        age = findViewById(R.id.age_p);
        gender = findViewById(R.id.gender_p);
        vehicle = findViewById(R.id.vehicle_p);
        qualification = findViewById(R.id.quali_P);
        experience = findViewById(R.id.experin_P);

        highest_qualification = findViewById(R.id.highest_p);
        no_years_exp = findViewById(R.id.no_exp_p);

        chipgroup_profile = findViewById(R.id.chipgroup_profile);

        no_exp_linear = findViewById(R.id.no_exp_linear);
        industry_interest_linear = findViewById(R.id.industry_interest_linear);

        profile = findViewById(R.id.card_profile);
        tv_register_status = findViewById(R.id.tv_register_status);
        progressBar = findViewById(R.id.progressBarHorizontal);
    }

    private void setFunctionality() {

        /*try {
            if ((ShareprefreancesUtility.getInstance().getString("register_status")).equalsIgnoreCase("complete")) {

                if ((ShareprefreancesUtility.getInstance().getString("kyc_status")).equalsIgnoreCase("approved")) {
                    profile.setVisibility(View.GONE);
                }else
                {
                    profile.setVisibility(View.VISIBLE);
                }
            }
            else
            {
                profile.setVisibility(View.VISIBLE);
            }
        }catch (Exception e){}*/

        if(ShareprefreancesUtility.getInstance().getString("kyc_status", "").equalsIgnoreCase("approved")){
            (findViewById(R.id.kyc_update)).setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("register_status", ""))
                && !ShareprefreancesUtility.getInstance().getString("register_status", "").equalsIgnoreCase("null")
                && ShareprefreancesUtility.getInstance().getString("register_status", "") != null
        ) {
            if(ShareprefreancesUtility.getInstance().getString("register_status", "").equalsIgnoreCase("pending"))
            {
                //tv_register_status.setText("40% Registration Complete");
                progressBar.setProgress(40);

            } else  if(ShareprefreancesUtility.getInstance().getString("register_status", "").equalsIgnoreCase("complete"))
            {
                //tv_register_status.setText("100% Registration Complete");
                progressBar.setProgress(100);
            }

        }

        StateProgressBar stateProgressBar = (StateProgressBar) findViewById(R.id.state_progress);

        if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("kyc_status", ""))
                && !ShareprefreancesUtility.getInstance().getString("kyc_status", "").equalsIgnoreCase("null")
                && ShareprefreancesUtility.getInstance().getString("kyc_status", "") != null
        ) {
            stateProgressBar.setStateDescriptionData(descriptionData);

            if(ShareprefreancesUtility.getInstance().getString("kyc_status", "").equalsIgnoreCase("pending"))
            {
                Log.e("aobsales","test "+ShareprefreancesUtility.getInstance().getString("kyc_status", ""));
                stateProgressBar.setStateDescriptionData(descriptionData);
                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
            }
            else if(ShareprefreancesUtility.getInstance().getString("kyc_status", "").equalsIgnoreCase("complete")){

                Log.e("aobsales","test "+ShareprefreancesUtility.getInstance().getString("kyc_status", ""));
                stateProgressBar.setStateDescriptionData(descriptionData1);
                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
            }
            else if(ShareprefreancesUtility.getInstance().getString("kyc_status", "").equalsIgnoreCase("approved"))
            {
                Log.e("aobsales","test "+ShareprefreancesUtility.getInstance().getString("kyc_status", ""));
                stateProgressBar.setStateDescriptionData(descriptionData2);
                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
               stateProgressBar.setAllStatesCompleted(true);
            }

        }

        LinearLayout linear_register = findViewById(R.id.linear_register);
       /* linear_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SystemClock.elapsedRealtime() - mLastClickTime3 < 1000) {
                    return;
                }
                mLastClickTime3 = SystemClock.elapsedRealtime();
                Intent i = new Intent(ProfileActivity.this, RegistrationActivity.class);
                startActivity(i);

            }
        });*/

        TextView kyc_linear = findViewById(R.id.kyc_update);
        kyc_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SystemClock.elapsedRealtime() - mLastClickTime2 < 1000) {
                    return;
                }
                mLastClickTime2 = SystemClock.elapsedRealtime();
                Intent i = new Intent(ProfileActivity.this, KYCActivity.class);
                startActivity(i);

            }
        });

        ((TextView)findViewById(R.id.login)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime4 < 1000) {
                    return;
                }
                mLastClickTime4 = SystemClock.elapsedRealtime();
                startActivity(new Intent(ProfileActivity.this,RegistrationActivity.class));

            }
        });

    }

    public void setValueAfterRegsitration() {

        try {

            if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("age", ""))
                    && !ShareprefreancesUtility.getInstance().getString("age", "").equalsIgnoreCase("null")
                    && ShareprefreancesUtility.getInstance().getString("age", "") != null
            ) {

                age.setText(ShareprefreancesUtility.getInstance().getString("age", ""));

            }

            if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("city", ""))
                    && !ShareprefreancesUtility.getInstance().getString("city", "").equalsIgnoreCase("null")
                    && ShareprefreancesUtility.getInstance().getString("city", "") != null
            ) {

                city.setText(ShareprefreancesUtility.getInstance().getString("city", ""));
            }

            if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("gender", ""))
                    && !ShareprefreancesUtility.getInstance().getString("gender", "").equalsIgnoreCase("null")
                    && ShareprefreancesUtility.getInstance().getString("gender", "") != null
            ) {

                gender.setText(ShareprefreancesUtility.getInstance().getString("gender", ""));
            }

            if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("vehicle", ""))
                    && !ShareprefreancesUtility.getInstance().getString("vehicle", "").equalsIgnoreCase("null")
                    && ShareprefreancesUtility.getInstance().getString("vehicle", "") != null
            ) {

                vehicle.setText(ShareprefreancesUtility.getInstance().getString("vehicle", ""));

            }

            if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("qualification", ""))
                    && !ShareprefreancesUtility.getInstance().getString("qualification", "").equalsIgnoreCase("null")
                    && ShareprefreancesUtility.getInstance().getString("qualification", "") != null
            ) {

                qualification.setText(ShareprefreancesUtility.getInstance().getString("qualification", ""));
            }
            if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("highest_qualification", ""))
                    && !ShareprefreancesUtility.getInstance().getString("highest_qualification", "").equalsIgnoreCase("null")
                    && ShareprefreancesUtility.getInstance().getString("highest_qualification", "") != null
            ) {

                highest_qualification.setText(ShareprefreancesUtility.getInstance().getString("highest_qualification", ""));
            }

            if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("experience_type", ""))
                    && !ShareprefreancesUtility.getInstance().getString("experience_type", "").equalsIgnoreCase("null")
                    && ShareprefreancesUtility.getInstance().getString("experience_type", "") != null
            ) {

                experience.setText(ShareprefreancesUtility.getInstance().getString("experience_type", ""));
                if(experience.getText().toString().equalsIgnoreCase("Experienced"))
                {

                    no_exp_linear.setVisibility(View.VISIBLE);
                    industry_interest_linear.setVisibility(View.VISIBLE);
                    ((LinearLayout)findViewById(R.id.experience_details_layout)).setVisibility(View.VISIBLE);
                    if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("company_name_1", ""))
                            && !ShareprefreancesUtility.getInstance().getString("company_name_1", "").equalsIgnoreCase("null")
                            && ShareprefreancesUtility.getInstance().getString("company_name_1", "") != null
                    ) {
                        ((LinearLayout)findViewById(R.id.experience_details_linear1)).setVisibility(View.VISIBLE);
                    }
                    else {
                        ((LinearLayout)findViewById(R.id.experience_details_linear1)).setVisibility(View.GONE);
                    }
                    if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("company_name_2", ""))
                            && !ShareprefreancesUtility.getInstance().getString("company_name_2", "").equalsIgnoreCase("null")
                            && ShareprefreancesUtility.getInstance().getString("company_name_2", "") != null
                    ) {
                        ((LinearLayout)findViewById(R.id.experience_details_linear2)).setVisibility(View.VISIBLE);
                    }
                    else {
                        ((LinearLayout)findViewById(R.id.experience_details_linear2)).setVisibility(View.GONE);
                    }
                    if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("company_name_3", ""))
                            && !ShareprefreancesUtility.getInstance().getString("company_name_3", "").equalsIgnoreCase("null")
                            && ShareprefreancesUtility.getInstance().getString("company_name_3", "") != null
                    ) {
                        ((LinearLayout)findViewById(R.id.experience_details_linear3)).setVisibility(View.VISIBLE);
                    }
                    else {
                        ((LinearLayout)findViewById(R.id.experience_details_linear3)).setVisibility(View.GONE);
                    }

                    ((TextView)findViewById(R.id.et_company1)).setText(ShareprefreancesUtility.getInstance().getString("company_name_1"));
                    ((TextView)findViewById(R.id.et_company2)).setText(ShareprefreancesUtility.getInstance().getString("company_name_2"));
                    ((TextView)findViewById(R.id.et_company3)).setText(ShareprefreancesUtility.getInstance().getString("company_name_3"));

                    ((TextView)findViewById(R.id.et_dur1)).setText(ShareprefreancesUtility.getInstance().getString("duration_of_experience_1"));
                    ((TextView)findViewById(R.id.et_dur2)).setText(ShareprefreancesUtility.getInstance().getString("duration_of_experience_2"));
                    ((TextView)findViewById(R.id.et_dur3)).setText(ShareprefreancesUtility.getInstance().getString("duration_of_experience_3"));

                    ((TextView)findViewById(R.id.et_exp1)).setText(ShareprefreancesUtility.getInstance().getString("what_sold_1"));
                    ((TextView)findViewById(R.id.et_exp2)).setText(ShareprefreancesUtility.getInstance().getString("what_sold_2"));
                    ((TextView)findViewById(R.id.et_exp3)).setText(ShareprefreancesUtility.getInstance().getString("what_sold_3"));

                }
            }

            if(no_exp_linear.getVisibility() == View.VISIBLE) {

                if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("year_of_experience", ""))
                        && !ShareprefreancesUtility.getInstance().getString("year_of_experience", "").equalsIgnoreCase("null")
                        && ShareprefreancesUtility.getInstance().getString("year_of_experience", "") != null
                ) {

                    no_years_exp.setText(ShareprefreancesUtility.getInstance().getString("year_of_experience", ""));

                }
            }

            if(industry_interest_linear.getVisibility() == View.VISIBLE) {
                if (!TextUtils.isEmpty(ShareprefreancesUtility.getInstance().getString("industry_of_interest", ""))
                        && !ShareprefreancesUtility.getInstance().getString("industry_of_interest", "").equalsIgnoreCase("null")
                        && ShareprefreancesUtility.getInstance().getString("industry_of_interest", "") != null
                ) {

                    String industry_val = ShareprefreancesUtility.getInstance().getString("industry_of_interest", "");
                    Log.e("aobsales", "test " + industry_val);
                    ArrayList<String> itemname = new ArrayList(Arrays.asList(industry_val.split("\\s*,\\s*")));

                    for (int i = 0; i < itemname.size(); i++) {
                        Chip chip = new Chip(ProfileActivity.this);
                        chip.setText(itemname.get(i));
                        chip.setChipBackgroundColorResource(R.color.colorTransparent);
                        chip.setChipStrokeColorResource(R.color.colorlabel);
                        chip.setTextSize(13);
                        chip.setChipCornerRadius(20);
                        chip.setChipStrokeWidth(1);
                        chipgroup_profile.addView(chip);
                    }
                }
            }
        }catch (Exception e){}
    }

}
