package com.aob.aobsalesman.activities.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aob.aobsalesman.R;

public class FAQActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        ((LinearLayout)findViewById(R.id.q1)).setOnClickListener(this);
        ((LinearLayout)findViewById(R.id.q2)).setOnClickListener(this);
        ((LinearLayout)findViewById(R.id.q3)).setOnClickListener(this);
        ((LinearLayout)findViewById(R.id.q4)).setOnClickListener(this);
        ((LinearLayout)findViewById(R.id.q5)).setOnClickListener(this);
        ((LinearLayout)findViewById(R.id.q6)).setOnClickListener(this);
        ((LinearLayout)findViewById(R.id.q7)).setOnClickListener(this);
        ((LinearLayout)findViewById(R.id.q8)).setOnClickListener(this);
        ((LinearLayout)findViewById(R.id.q9)).setOnClickListener(this);
        ((LinearLayout)findViewById(R.id.q10)).setOnClickListener(this);
        ((LinearLayout)findViewById(R.id.q11)).setOnClickListener(this);
        ((LinearLayout)findViewById(R.id.q12)).setOnClickListener(this);

        ((ImageView)findViewById(R.id.back_image)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.q1:
                if (((TextView)findViewById(R.id.ans1)).getVisibility() == View.VISIBLE) {
                    ((TextView) findViewById(R.id.ans1)).setVisibility(View.GONE);
                    ((ImageView) findViewById(R.id.image1)).setImageResource(R.drawable.ic_expand_more_black_24dp);
                }
                else{ ((TextView)findViewById(R.id.ans1)).setVisibility(View.VISIBLE);
                        ((ImageView)findViewById(R.id.image1)).setImageResource(R.drawable.ic_expand_less_black_24dp);}
                break;
            case R.id.q2:
                if (((TextView)findViewById(R.id.ans2)).getVisibility() == View.VISIBLE) {
                    ((TextView) findViewById(R.id.ans2)).setVisibility(View.GONE);
                    ((ImageView) findViewById(R.id.image2)).setImageResource(R.drawable.ic_expand_more_black_24dp);
                }
                else{ ((TextView)findViewById(R.id.ans2)).setVisibility(View.VISIBLE);
                    ((ImageView)findViewById(R.id.image2)).setImageResource(R.drawable.ic_expand_less_black_24dp);}
                break;
            case R.id.q3:
                if (((TextView)findViewById(R.id.ans3)).getVisibility() == View.VISIBLE) {
                    ((TextView) findViewById(R.id.ans3)).setVisibility(View.GONE);
                    ((ImageView) findViewById(R.id.image3)).setImageResource(R.drawable.ic_expand_more_black_24dp);
                }
                else{ ((TextView)findViewById(R.id.ans3)).setVisibility(View.VISIBLE);
                    ((ImageView)findViewById(R.id.image3)).setImageResource(R.drawable.ic_expand_less_black_24dp);}
                break;
            case R.id.q4:
                if (((TextView)findViewById(R.id.ans4)).getVisibility() == View.VISIBLE) {
                    ((TextView) findViewById(R.id.ans4)).setVisibility(View.GONE);
                    ((ImageView) findViewById(R.id.image4)).setImageResource(R.drawable.ic_expand_more_black_24dp);
                }
                else{ ((TextView)findViewById(R.id.ans4)).setVisibility(View.VISIBLE);
                    ((ImageView)findViewById(R.id.image4)).setImageResource(R.drawable.ic_expand_less_black_24dp);}
                break;
            case R.id.q5:
                if (((TextView)findViewById(R.id.ans5)).getVisibility() == View.VISIBLE) {
                    ((TextView) findViewById(R.id.ans5)).setVisibility(View.GONE);
                    ((ImageView) findViewById(R.id.image5)).setImageResource(R.drawable.ic_expand_more_black_24dp);
                }
                else{ ((TextView)findViewById(R.id.ans5)).setVisibility(View.VISIBLE);
                    ((ImageView)findViewById(R.id.image5)).setImageResource(R.drawable.ic_expand_less_black_24dp);}
                break;
            case R.id.q6:
                if (((TextView)findViewById(R.id.ans6)).getVisibility() == View.VISIBLE) {
                    ((TextView) findViewById(R.id.ans6)).setVisibility(View.GONE);
                    ((ImageView) findViewById(R.id.image6)).setImageResource(R.drawable.ic_expand_more_black_24dp);
                }
                else{ ((TextView)findViewById(R.id.ans6)).setVisibility(View.VISIBLE);
                    ((ImageView)findViewById(R.id.image6)).setImageResource(R.drawable.ic_expand_less_black_24dp);}
                break;
            case R.id.q7:
                if (((TextView)findViewById(R.id.ans7)).getVisibility() == View.VISIBLE) {
                    ((TextView) findViewById(R.id.ans7)).setVisibility(View.GONE);
                    ((ImageView) findViewById(R.id.image7)).setImageResource(R.drawable.ic_expand_more_black_24dp);
                }
                else{ ((TextView)findViewById(R.id.ans7)).setVisibility(View.VISIBLE);
                    ((ImageView)findViewById(R.id.image7)).setImageResource(R.drawable.ic_expand_less_black_24dp);}
                break;
            case R.id.q8:
                if (((TextView)findViewById(R.id.ans8)).getVisibility() == View.VISIBLE) {
                    ((TextView) findViewById(R.id.ans8)).setVisibility(View.GONE);
                    ((ImageView) findViewById(R.id.image8)).setImageResource(R.drawable.ic_expand_more_black_24dp);
                }
                else{ ((TextView)findViewById(R.id.ans8)).setVisibility(View.VISIBLE);
                    ((ImageView)findViewById(R.id.image8)).setImageResource(R.drawable.ic_expand_less_black_24dp);}
                break;
            case R.id.q9:
                if (((TextView)findViewById(R.id.ans9)).getVisibility() == View.VISIBLE) {
                    ((TextView) findViewById(R.id.ans9)).setVisibility(View.GONE);
                    ((ImageView) findViewById(R.id.image9)).setImageResource(R.drawable.ic_expand_more_black_24dp);
                }
                else{ ((TextView)findViewById(R.id.ans9)).setVisibility(View.VISIBLE);
                    ((ImageView)findViewById(R.id.image9)).setImageResource(R.drawable.ic_expand_less_black_24dp);}
                break;
            case R.id.q10:
                if (((TextView)findViewById(R.id.ans10)).getVisibility() == View.VISIBLE) {
                    ((TextView) findViewById(R.id.ans10)).setVisibility(View.GONE);
                    ((ImageView) findViewById(R.id.image10)).setImageResource(R.drawable.ic_expand_more_black_24dp);
                }
                else{ ((TextView)findViewById(R.id.ans10)).setVisibility(View.VISIBLE);
                    ((ImageView)findViewById(R.id.image10)).setImageResource(R.drawable.ic_expand_less_black_24dp);}
                break;
            case R.id.q11:
                if (((TextView)findViewById(R.id.ans11)).getVisibility() == View.VISIBLE) {
                    ((TextView) findViewById(R.id.ans11)).setVisibility(View.GONE);
                    ((ImageView) findViewById(R.id.image11)).setImageResource(R.drawable.ic_expand_more_black_24dp);
                }
                else{ ((TextView)findViewById(R.id.ans11)).setVisibility(View.VISIBLE);
                    ((ImageView)findViewById(R.id.image11)).setImageResource(R.drawable.ic_expand_less_black_24dp);}
                break;
            case R.id.q12:
                if (((TextView)findViewById(R.id.ans12)).getVisibility() == View.VISIBLE) {
                    ((TextView) findViewById(R.id.ans12)).setVisibility(View.GONE);
                    ((ImageView) findViewById(R.id.image12)).setImageResource(R.drawable.ic_expand_more_black_24dp);
                }
                else{ ((TextView)findViewById(R.id.ans12)).setVisibility(View.VISIBLE);
                    ((ImageView)findViewById(R.id.image12)).setImageResource(R.drawable.ic_expand_less_black_24dp);}
                break;

        }
    }
}
