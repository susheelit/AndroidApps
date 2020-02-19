package com.e.salestracker.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.e.salestracker.R;
import com.e.salestracker.Utility.ShareprefreancesUtility;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.mikhaellopez.circularimageview.CircularImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initToolbar();
        initprofileImage();
        initDetail();
    }
    private void initDetail() {

         TextView Name = findViewById(R.id.Name);
         TextView Email = findViewById(R.id.Email);
         TextView Mobile = findViewById(R.id.Mobile);

        Name.setText(ShareprefreancesUtility.getInstance().getString("name",null));
        Email.setText(ShareprefreancesUtility.getInstance().getString("email_id",null));
        Mobile.setText(ShareprefreancesUtility.getInstance().getString("mobile",null ));
    }
    private void initToolbar() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
    private void initprofileImage() {
        final CircularImageView image = findViewById(R.id.image);
        final CollapsingToolbarLayout collapsing_toolbar = findViewById(R.id.collapsing_toolbar);
        ((AppBarLayout) findViewById(R.id.app_bar_layout)).addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int min_height = ViewCompat.getMinimumHeight(collapsing_toolbar) * 2;
                float scale = (float) (min_height + verticalOffset) / min_height;
                image.setScaleX(scale >= 0 ? scale : 0);
                image.setScaleY(scale >= 0 ? scale : 0);
            }
        });
    }

}
