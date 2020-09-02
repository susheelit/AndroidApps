package com.irg.crm_admin.common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

public class ImageUtil {

    public static String imageToString(Bitmap thumbnail, ImageView ivImage){
        int nh = (int) (thumbnail.getHeight() * (512.0 / thumbnail.getWidth()));
        Bitmap scaled = Bitmap.createScaledBitmap(thumbnail, 512, nh, true);
        byte[] picByteArray = getByteArray(scaled);
        String encodedImg = Base64.encodeToString(picByteArray, Base64.DEFAULT);
        ivImage.setImageBitmap(scaled);

        return encodedImg;
    }

    public static byte[] getByteArray(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return byteArray;
    }

    public static Bitmap getBitmap(String path) {
        Bitmap bitmap = null;
        try {

            File f = new File(path);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;

            bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, options);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

}
