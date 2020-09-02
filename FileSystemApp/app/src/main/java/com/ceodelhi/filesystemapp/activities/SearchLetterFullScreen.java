package com.ceodelhi.filesystemapp.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ceodelhi.filesystemapp.R;
import com.ceodelhi.filesystemapp.adapter.LetterImagesAdapter;
import com.ceodelhi.filesystemapp.model.ModelLetters;
import com.ceodelhi.filesystemapp.model.ModelOfficer;
import com.ceodelhi.filesystemapp.utility.DividerItemDecoration;

import java.util.ArrayList;

//http://android-er.blogspot.com/2016/05/implement-pinch-to-zoom-with.html

public class SearchLetterFullScreen extends AppCompatActivity {


    private TextView letter_number_tv, letter_sub_tv, source_tv, markedOfficersTv, etComments;
    private ModelLetters letter;
    private TextView etStatus, etInstructions, readOfficersTv;
    private int pos;
    private String webResponse;
    private String instructions;
    private RecyclerView letterImageGridView;
    private LetterImagesAdapter letterImagesAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<String> letterImages;
    private ImageView search;
    private String markedOfficersData = "";
    private ArrayList<ModelOfficer> selectedOfficers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_letter_full);
        letter_sub_tv = (TextView) findViewById(R.id.letter_subject_tv);
        letter_number_tv = (TextView) findViewById(R.id.letter_number_tv);
        markedOfficersTv = (TextView) findViewById(R.id.markedOfficersTv);
        readOfficersTv = (TextView) findViewById(R.id.readOfficersTv);
        source_tv = (TextView) findViewById(R.id.source_tv);
        etComments = (TextView) findViewById(R.id.etComments);
        search = findViewById(R.id.search);
        etStatus = findViewById(R.id.etStatus);
        etInstructions = findViewById(R.id.etInstructions);
        search.setVisibility(View.GONE);
        letterImageGridView = findViewById(R.id.letterImageGridView);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        letterImages = new ArrayList<>();
        Intent i = getIntent();
        letter = (ModelLetters) i.getSerializableExtra("letter");
        buildArraylistImages(letter.getPhoto());
        buildArraylistImages(letter.getPhoto1());
        buildArraylistImages(letter.getPhoto2());
        buildArraylistImages(letter.getPhoto3());
        buildArraylistImages(letter.getPhoto4());
        buildArraylistImages(letter.getPhoto5());

        letterImagesAdapter = new LetterImagesAdapter(letterImages, this);
        letterImageGridView.setAdapter(letterImagesAdapter);
        letterImageGridView.setLayoutManager(mLayoutManager);
        letterImageGridView.addItemDecoration(new DividerItemDecoration(SearchLetterFullScreen.this, DividerItemDecoration.HORIZONTAL_LIST));

        letter_number_tv.setText("Letter No. : " + (checkforNull(letter.getLetterNo())));
        letter_sub_tv.setText("Letter Subject : " + (checkforNull(letter.getLetterSubject())));
        source_tv.setText("Source : " + (checkforNull(letter.getSource_Subject())));
        etInstructions.setText("Instruction to CA : " + (checkforNull(letter.getPA_Instruction())));
        etStatus.setText("Status : " + (checkforNull(letter.getStatus())));
        etComments.setText("Comments : " + (checkforNull(letter.getComment())));
        if (!letter.getStatus().equalsIgnoreCase("pending")) {
            markedOfficersTv.setVisibility(View.VISIBLE);
            markedOfficersTv.setText("Marked To : " + (checkforNull(letter.getMarkedTo())));
            readOfficersTv.setVisibility(View.VISIBLE);
            String text = "Ready By : " + "<font color=\"blue\">" + (checkforNull(letter.getReadStatus())) + "</font>";
            readOfficersTv.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE);
        } else {
            markedOfficersTv.setVisibility(View.GONE);
            readOfficersTv.setVisibility(View.GONE);
        }
    }

    private void buildArraylistImages(String photo) {
        System.out.println("photo " + photo);
        if (photo != null) {
            if (photo.equals("") || (photo.equals("null") || photo.equalsIgnoreCase("http://ceodelhinet.nic.in/onlineErmsDept/Images/LetterDisposal/"))) {
            } else {
                letterImages.add(photo);
            }
        }
    }

    private String checkforNull(String value) {
        if (value.equalsIgnoreCase("null")) {
            return "";
        } else {
            return value;
        }
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


}