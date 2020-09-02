package com.ceodelhi.filesystemapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ceodelhi.filesystemapp.R;
import com.ceodelhi.filesystemapp.activities.PhotoFullPopupWindow;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


//http://www.tutorialsface.com/2017/10/whatsapp-like-full-screen-imageview-android-with-zoom-blur-bg-in-popup-window-example-tutorial/
public class FileImagesAdapter extends RecyclerView.Adapter<FileImagesAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<String> fileImages;


    public FileImagesAdapter(ArrayList<String> fileImages, Context mContext) {
        this.fileImages = fileImages;
        this.mContext = mContext;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gridimage_item, null);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final String fileImage = fileImages.get(position);
//        byte[] decodedString = Base64.decode(fileImage, Base64.DEFAULT);
//        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//        holder.singleImageView.setImageBitmap(decodedByte);

        Glide.with(mContext).asBitmap().load("http://" + fileImage)
                .placeholder(R.mipmap.home_page3)
                .error(R.drawable.error)
                .into(holder.singleImageView);

//        Picasso.get().load("http://" + fileImage).placeholder(R.mipmap.home_page3)
//                .error(R.drawable.error)
//                .into(holder.singleImageView);

        holder.singleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PhotoFullPopupWindow(mContext, R.layout.popup_photo_full, v,
                        "http://" + fileImage, null);

            }
        });
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView singleImageView;


        public MyViewHolder(View view) {
            super(view);
            singleImageView = (ImageView) view.findViewById(R.id.singleImageView);

        }
    }


    @Override
    public int getItemCount() {
        return fileImages.size();
    }

}