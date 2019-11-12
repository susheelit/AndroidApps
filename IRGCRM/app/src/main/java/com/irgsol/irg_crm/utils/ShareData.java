package com.irgsol.irg_crm.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.irgsol.irg_crm.BuildConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

public class ShareData extends FileProvider {

    public static void shareItem(final Context context, String url) {
        Glide.with(context)
                .asBitmap()
                .load(url)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("*/*");
                        i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(context,resource));
                        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        context.startActivity(Intent.createChooser(i, "Choose App"));
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
    }
    public static void shareItem(final Context context, String url, final String text) {
        Glide.with(context)
                .asBitmap()
                .load(url)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("*/*");
                        i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(context,resource));
                        i.putExtra(Intent.EXTRA_TEXT,text);
                        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        context.startActivity(Intent.createChooser(i, "Choose App"));
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });

    }

    public static Uri getLocalBitmapUri(Context context, Bitmap bmp) {
        Uri contentUri=null;
        try {
            File cachePath = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "images.jpeg");
            FileOutputStream stream = new FileOutputStream(cachePath); // overwrites this image every time
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.close();
           contentUri = FileProvider.getUriForFile(context,
                    BuildConfig.APPLICATION_ID + ".provider",
                    cachePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentUri;
    }

    public static Uri getLocalBitmapUri(Context context, @DrawableRes int drawable) {
        Uri bmpUri = null;
        try {
            File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "images.jpeg");
            FileOutputStream stream = new FileOutputStream(file );
            Bitmap bmp = BitmapFactory.decodeResource(context.getResources(),drawable);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.close();

            bmpUri = FileProvider.getUriForFile(context,
                    BuildConfig.APPLICATION_ID + ".provider",
                    file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    public static void shareItem(final Context context, @DrawableRes int drawable) {
        try {
            Uri bmpuri = getLocalBitmapUri(context, drawable);
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("*/*");
            i.putExtra(Intent.EXTRA_STREAM, bmpuri);
            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(Intent.createChooser(i, "Share Image"));
        }catch (Exception e){}

    }

    public static void shareItem(final Context context, @DrawableRes int drawable, String text) {
        Uri bmpuri=getLocalBitmapUri(context,drawable);
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("*/*");
        i.putExtra(Intent.EXTRA_STREAM, bmpuri);
        i.putExtra(Intent.EXTRA_TEXT,text);
        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(Intent.createChooser(i, "Share Via"));
    }

}
