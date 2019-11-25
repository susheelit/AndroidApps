package com.irgsol.irg_crm.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.irgsol.irg_crm.R;
import com.irgsol.irg_crm.utils.Config;
import com.irgsol.irg_crm.common.OprActivity;
import com.irgsol.irg_crm.utils.UserPermission;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

public class AddShopActivity_1 extends AppCompatActivity {

    private Button btnSubmit, btnUpload;
    Context context;
    Toolbar toolbar;
    private static final int CAMERA_REQUEST = 1888;
    private ImageView ivShopImg;
    private static final int GALLARY_REQUEST = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop_1);
        initView();
    }

    private void initView() {
        context = AddShopActivity_1.this;
        toolbar = findViewById(R.id.toolbar);
        OprActivity.setUpToolbarWithTitle(toolbar, "Basic Information", context);

        ivShopImg = findViewById(R.id.ivShopImg);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnUpload = findViewById(R.id.btnUpload);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OprActivity.openActivity(context, new AddShopActivity_2());
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoDialog(context);
            }
        });
    }

    private void photoDialog(Context context) {

        final TextView tvCamera, tvGallery;
        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_photo_diag);

        dialog.setTitle("Select...");

        tvCamera = dialog.findViewById(R.id.tvCamera);
        tvGallery = dialog.findViewById(R.id.tvGallery);

        tvCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                openCamera();
            }
        });

        tvGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                openGallery();
            }
        });

        dialog.show();

    }

    private void openGallery() {
        if (UserPermission.getGallaryPermission(AddShopActivity_1.this)) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            String[] mimeTypes = {"image/jpeg", "image/png"};
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            startActivityForResult(intent, GALLARY_REQUEST);
        }

    }

    private void openCamera() {
        if (UserPermission.getCameraPermission(AddShopActivity_1.this)) {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            Config.setCircularBitmap(context, ivShopImg, photo);
        }

        if (requestCode == GALLARY_REQUEST && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            //Bitmap photo = (Bitmap) data.getExtras().get("data");
            Config.setCircularImage(context, ivShopImg, selectedImage);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        OprActivity.finishActivity(context);
        return true;
    }

    @Override
    public void onBackPressed() {
        OprActivity.finishActivity(context);
    }
}
