package com.aob.aobsalesman.activities.activities;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.aob.aobsalesman.R;
import com.aob.aobsalesman.activities.Fragements.CompleteRegistrationFragment;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=manager.beginTransaction();
        fragmentTransaction.add(R.id.frame,new CompleteRegistrationFragment());
        fragmentTransaction.commit();

        try {
            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }catch (Exception e){}
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}


