package com.ceodelhi.filesystemapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ceodelhi.filesystemapp.activities.FileFullScreen;
import com.ceodelhi.filesystemapp.model.ModelFiles;
import com.ceodelhi.filesystemapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.graphics.Color.RED;
import static com.ceodelhi.filesystemapp.utility.MyConstants.FILE_DISPOSE;
import static com.ceodelhi.filesystemapp.utility.UserContext.dateFormat1;


//http://www.tutorialsface.com/2017/10/whatsapp-like-full-screen-imageview-android-with-zoom-blur-bg-in-popup-window-example-tutorial/
public class FileAdapter extends RecyclerView.Adapter<FileAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<ModelFiles> files;


    public FileAdapter(ArrayList<ModelFiles> files, Context mContext) {
        this.files = files;
        this.mContext = mContext;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.file_view, null);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ModelFiles file = files.get(position);
        holder.file_id_tv.setText(checkforNull(file.getFileNo()));
        holder.file_name_tv.setText(checkforNull(file.getFileSubject()));
        String status;
        if (file.getStatus().equalsIgnoreCase("null") || file.getStatus().equalsIgnoreCase("")
                || file.getStatus().equalsIgnoreCase("Pending")) {
            status = "Pending";
            holder.status_tv.setText(status);
            holder.status_tv.setTextColor(RED);
            holder.status_tv.setTypeface(holder.status_tv.getTypeface(), Typeface.BOLD_ITALIC);
        } else {
            holder.status_tv.setText(file.getStatus());
            holder.status_tv.setTextColor(Color.DKGRAY);
            holder.status_tv.setTypeface(holder.status_tv.getTypeface(), Typeface.BOLD);
        }

        String entrydate = checkforNull(file.getEntryDate());
        holder.date_tv.setText("" + dateFormat1(entrydate.substring(entrydate.indexOf("(") + 1, entrydate.indexOf(")"))));

//        byte[] decodedString = Base64.decode(file.getPhoto(), Base64.DEFAULT);
//        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//        holder.imageView.setImageBitmap(decodedByte);

//        Picasso.get().load("http://" + file.getPhoto()).placeholder(R.mipmap.home_page3)
//                .error(R.drawable.error)
//                .into(holder.imageView);

        Glide.with(mContext).asBitmap().load("http://" + file.getPhoto())
                .placeholder(R.mipmap.home_page3)
                .error(R.drawable.error)
                .into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity origin = (Activity) mContext;
                origin.startActivityForResult(new Intent(mContext, FileFullScreen.class).putExtra("file", file)
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), FILE_DISPOSE);
            }
        });

        holder.innerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity origin = (Activity) mContext;
                origin.startActivityForResult(new Intent(mContext, FileFullScreen.class).putExtra("file", file)
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), FILE_DISPOSE);

            }
        });
    }

    private String checkforNull(String value) {
        if (value.equalsIgnoreCase("null")) {
            return "";
        } else {
            return value;
        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView file_id_tv, file_name_tv, status_tv, date_tv;
        public ImageView imageView;
        public LinearLayout innerLayout;


        public MyViewHolder(View view) {
            super(view);
            file_id_tv = (TextView) view.findViewById(R.id.file_id_tv);
            file_name_tv = (TextView) view.findViewById(R.id.file_name_tv);
            status_tv = (TextView) view.findViewById(R.id.status_tv);
            date_tv = (TextView) view.findViewById(R.id.date_tv);
            imageView = (ImageView) view.findViewById(R.id.imageView);
            innerLayout = (LinearLayout) view.findViewById(R.id.innerLayout);

        }
    }


    @Override
    public int getItemCount() {
        return files.size();
    }

}